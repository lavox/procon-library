#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import argparse
import re
import sys
import os
import shutil
from pathlib import Path
from collections import defaultdict, deque

try:
  import javalang
except ImportError:
  print("ERROR: Failed to import javalang. Execute `pip install javalang`.", file=sys.stderr)
  sys.exit(1)
try:
  # from pick import pick
  import pick
except ImportError:
  print("ERROR: Failed to import pick. Execute `pip install pick`.", file=sys.stderr)
  sys.exit(1)

PRIMITIVES = {'byte','short','int','long','float','double','boolean','char','void'}
JAVA_LANG = "java.lang"

# ---------- IO helpers ----------

def read_text(p: Path) -> str:
  try:
    return p.read_text(encoding="utf-8")
  except UnicodeDecodeError:
    return p.read_text(encoding="cp932")

def write_atomic(path: Path, content: str, backup: bool = True):
  if backup:
    shutil.copyfile(path, path.with_suffix(path.suffix + ".bak"))
  tmp = path.with_suffix(path.suffix + ".tmp")
  tmp.write_text(content, encoding="utf-8")
  os.replace(tmp, path)

# ---------- Parsing helpers ----------

def parse_unit(code: str):
  return javalang.parse.parse(code)

def top_level_types(unit):
  res = []
  for t in unit.types:
    if isinstance(t, (javalang.tree.ClassDeclaration, javalang.tree.InterfaceDeclaration, javalang.tree.EnumDeclaration)):
      res.append(t)
  return res

def get_package(unit) -> str:
  return unit.package.name if unit.package else ""

def collect_imports(unit):
  normals, statics = [], []
  for imp in unit.imports:
    path = imp.path + (".*" if imp.wildcard else "")
    if imp.static:
      statics.append(path)
    else:
      normals.append(path)
  return normals, statics

def collect_defined_simple_types(unit) -> set[str]:
  return {t.name for t in top_level_types(unit)}

def collect_referenced_simple_types(unit) -> set[str]:
  names = set()
  for _, node in unit.filter(javalang.tree.ReferenceType):
    if node.name:
      simple = node.name.split('.')[-1]
      if simple and simple not in PRIMITIVES:
        names.add(simple)
  for _, node in unit.filter(javalang.tree.Annotation):
    if node.name:
      simple = node.name.split('.')[-1]
      if simple and simple not in PRIMITIVES:
        names.add(simple)
  return names

# ---------- Library index ----------

class LibIndex:
  def __init__(self, root: Path):
    self.root = root
    self.fqn_to_path: dict[str, Path] = {}
    self.simple_to_fqns: dict[str, set[str]] = defaultdict(set)

  def build(self):
    for p in self.root.rglob("*.java"):
      code = read_text(p)
      try:
        unit = parse_unit(code)
      except Exception as e:
        print(f"[WARN] Failed to analyze: {p} ({e})", file=sys.stderr)
        continue
      pkg = get_package(unit)
      for t in top_level_types(unit):
        fqn = f"{pkg}.{t.name}" if pkg else t.name
        self.fqn_to_path[fqn] = p
        self.simple_to_fqns[t.name].add(fqn)

  def resolve(self, name: str, imports_normal: list[str], pkg: str) -> str | None:
    if '.' in name and name in self.fqn_to_path:
      return name
    cands = list(self.simple_to_fqns.get(name, []))
    if not cands:
      return None
    explicit = [imp for imp in imports_normal if not imp.endswith(".*")]
    for imp in explicit:
      if imp.split(".")[-1] == name and imp in self.fqn_to_path:
        return imp
    if pkg:
      fqn = f"{pkg}.{name}"
      if fqn in self.fqn_to_path:
        return fqn
    for w in [imp for imp in imports_normal if imp.endswith(".*")]:
      base = w[:-2]
      fqn = f"{base}.{name}"
      if fqn in self.fqn_to_path:
        return fqn
    if len(cands) == 1:
      return cands[0]
    print(f"[WARN] {name} is ambiguous. Candidate: {cands} → {cands[0]} is used.", file=sys.stderr)
    return cands[0]

# ---------- Code transforms ----------

RE_PACKAGE = re.compile(r'^\s*package\s+[\w\.]+;\s*', re.MULTILINE)
RE_PUBLIC_TOP = re.compile(r'^public\s+(abstract class|class|interface|enum)\s+', re.MULTILINE)
RE_IMPORT_LINE = re.compile(r'^\s*import\s+.+?;\s*$', re.MULTILINE)

def strip_package(code: str) -> str:
  return RE_PACKAGE.sub('', code)

def demote_public_top_level(code: str) -> str:
  return RE_PUBLIC_TOP.sub(r'\1 ', code)

def remove_imports(code: str) -> str:
  return RE_IMPORT_LINE.sub('', code)

def normalize(code: str) -> str:
  code = re.sub(r'\n{3,}', '\n\n', code)
  return code.strip() + '\n'

# ---------- Import insertion into Main.java ----------

def extract_imports_from_code(code: str):
  normals, statics = set(), set()
  for m in re.finditer(r'^\s*import\s+static\s+(.+?);\s*$', code, flags=re.MULTILINE):
    statics.add(m.group(1))
  for m in re.finditer(r'^\s*import\s+(?!static)(.+?);\s*$', code, flags=re.MULTILINE):
    normals.add(m.group(1))
  return normals, statics

def insert_missing_imports(code: str, add_normals: set[str], add_statics: set[str]) -> str:
  existing_normals, existing_statics = extract_imports_from_code(code)
  to_add_normals = sorted(add_normals - existing_normals)
  to_add_statics = sorted(add_statics - existing_statics)
  if not to_add_normals and not to_add_statics:
    return code
  lines = code.splitlines(keepends=True)
  pkg_idx = -1
  first_imp = -1
  last_imp = -1
  for i, ln in enumerate(lines):
    if re.match(r'^\s*package\s+[\w\.]+;\s*$', ln):
      pkg_idx = i
    if re.match(r'^\s*import\s+.+?;\s*$', ln):
      if first_imp == -1:
        first_imp = i
      last_imp = i
  insert_pos = 0
  if last_imp != -1:
    insert_pos = last_imp + 1
  elif pkg_idx != -1:
    insert_pos = pkg_idx + 1
    while insert_pos < len(lines) and lines[insert_pos].strip() == "":
      insert_pos += 1
  else:
    insert_pos = 0
  new_import_lines = []
  if insert_pos < len(lines) and lines[insert_pos-1].strip() != "":
    new_import_lines.append("\n")
  for imp in to_add_normals:
    if not imp.startswith(JAVA_LANG + ".") or "." in imp[len(JAVA_LANG) + 1:]:
      new_import_lines.append(f"import {imp};\n")
  for imp in to_add_statics:
    new_import_lines.append(f"import static {imp};\n")
  # new_import_lines.append("\n")
  lines[insert_pos:insert_pos] = new_import_lines
  return "".join(lines)

# ---------- Merge logic ----------

def merge_into_main(main_path: Path, lib_root: Path, includes: list[str]):
  main_code = read_text(main_path)
  try:
    main_unit = parse_unit(main_code)
    main_defined = collect_defined_simple_types(main_unit)
    main_pkg = get_package(main_unit)
    main_imp_norm, main_imp_stat = collect_imports(main_unit)
  except Exception as e:
    main_defined = set(re.findall(r'^\s*(?:public\s+)?(?:class|interface|enum)\s+(\w+)', main_code, flags=re.MULTILINE))
    main_pkg = ""
    main_imp_norm, main_imp_stat = [], []

  index = LibIndex(lib_root)
  index.build()
  entry_fqns = []
  for nm in includes:
    fqn = None
    if '.' in nm and nm in index.fqn_to_path:
      fqn = nm
    else:
      fqn = index.resolve(nm, main_imp_norm, main_pkg)
    if not fqn:
      print(f"[WARN] Specified class {nm} is not found in the library.", file=sys.stderr)
    else:
      entry_fqns.append(fqn)
  if not entry_fqns:
    print("[INFO] No target to be added.", file=sys.stderr)
    return
  already_in_main = set(main_defined)
  queue = deque(entry_fqns)
  taken = []
  taken_set = set()
  need_imports_norm = set()
  need_imports_stat = set()
  while queue:
    fqn = queue.popleft()
    if fqn in taken_set:
      continue
    path = index.fqn_to_path.get(fqn)
    if not path:
      continue
    code = read_text(path)
    try:
      unit = parse_unit(code)
    except Exception as e:
      print(f"[WARN] Failed to analyze: {path} ({e})", file=sys.stderr)
      continue
    pkg = get_package(unit)
    imps_norm, imps_stat = collect_imports(unit)
    defined = collect_defined_simple_types(unit)
    referenced = collect_referenced_simple_types(unit)
    if any(d in already_in_main for d in defined):
      continue
    for imp in imps_norm:
      if not imp.startswith(JAVA_LANG + ".") or "." in imp[len(JAVA_LANG) + 1:]:
        need_imports_norm.add(imp)
    for imp in imps_stat:
      need_imports_stat.add(imp)
    for simple in (referenced - defined):
      if simple in PRIMITIVES:
        continue
      dep_fqn = index.resolve(simple, imps_norm, pkg) or index.resolve(simple, main_imp_norm, main_pkg)
      if dep_fqn and dep_fqn not in taken_set:
        simple_dep = dep_fqn.split('.')[-1]
        if simple_dep in already_in_main:
          continue
        queue.append(dep_fqn)
    taken.append((fqn, path, code, unit))
    taken_set.add(fqn)
    already_in_main.update(defined)
  if not taken:
    print("[INFO] No class to be added.", file=sys.stderr)
    return
  newly_defined_simples = set()
  for fqn, _, _, unit in taken:
    newly_defined_simples.update(collect_defined_simple_types(unit))
  def import_targets_simple(imp: str):
    return set() if imp.endswith(".*") else {imp.split(".")[-1]}
  filtered_normals = set()
  for imp in need_imports_norm:
    sims = import_targets_simple(imp)
    if sims and any(s in newly_defined_simples for s in sims):
      continue
    filtered_normals.add(imp)
  new_main_code = insert_missing_imports(main_code, filtered_normals, need_imports_stat)
  append_parts = []
  for fqn, path, code, unit in taken:
    rel = path.relative_to(lib_root) if lib_root in Path(path).parents else Path(path).name
    part = strip_package(code)
    part = remove_imports(part)
    part = demote_public_top_level(part)
    part = normalize(part)
    append_parts.append(f"// === begin: {rel} ===\n{part}// === end: {rel} ===\n")
  new_main_code = new_main_code.rstrip() + "\n\n" + "\n".join(append_parts)
  write_atomic(main_path, new_main_code, backup=True)
  print(f"[OK] Added library: {main_path}")

# ---------- Select Library File ----------

def choose_file_in_lib(lib_dir: str) -> str:
  current_dir = lib_dir
  while True:
    display_names = []
    paths = []

    # back to parent directory. (Cannot move to directory higher than lib_dir)
    if current_dir != lib_dir:
      display_names.append(".. (parent directory)")
      paths.append(os.path.dirname(current_dir))

    for entry in os.listdir(current_dir):
      full_path = os.path.join(current_dir, entry)
      if os.path.isdir(full_path):
        display_name = f"{entry}/"
      elif entry.endswith(".java"):
        display_name = f"{entry}"
      else:
        display_name = f"({entry})"
      display_names.append(display_name)
      paths.append(full_path)
    
    display_names.append("(quit)")
    paths.append("(quit)")

    option, index = pick.pick(display_names, f"Current directory: {current_dir}", indicator=">")
    chosen_path = paths[index]

    if option == "(quit)":
      return None
    elif os.path.isdir(chosen_path):
      current_dir = chosen_path
    elif option.startswith(".."):
      current_dir = chosen_path
    elif option.endswith(".java"):
      # convert to relative path from lib_dir
      rel_path = os.path.relpath(chosen_path, lib_dir)
      # remove extension .java
      rel_path = rel_path[:-5] if rel_path.endswith(".java") else rel_path
      # convert separator to "."
      fqdn = rel_path.replace(os.path.sep, ".")
      print(fqdn)
      return fqdn
    else:
      print("You can select .java file only.")

def main():
    ap = argparse.ArgumentParser(description="Java procon-library merge tool")
    ap.add_argument("--main", required=True, type=Path, help="path to destination file.", default="Main.java")
    ap.add_argument("--lib", required=True, type=Path, help="library root directory.")
    ap.add_argument("--include", nargs="+", help="class to add (FQN or class name). Multiple values allowed.", default=[])
    args = ap.parse_args()
    if not args.include:
      chosen_file = choose_file_in_lib(os.path.expanduser(args.lib))
      if chosen_file is None:
        print("No file selected.")
        sys.exit(0)
      args.include = [chosen_file]
    try:
      main_path = Path(os.path.expanduser(args.main))
      lib_path = Path(os.path.expanduser(args.lib))
      merge_into_main(main_path, lib_path, args.include)
    except javalang.parser.JavaSyntaxError as e:
      print(f"ERROR: Java parse error: {e}", file=sys.stderr)
      sys.exit(3)

if __name__ == "__main__":
    main()
