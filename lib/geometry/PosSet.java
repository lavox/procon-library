package geometry;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

public class PosSet implements Iterable<Pos> {
	private final Grid grid;
	private final BitSet mem;
	private int sz = 0;

	public PosSet(Grid grid) {
		this.grid = grid;
		this.mem = new BitSet(grid.size());
		this.sz = 0;
	}
	public PosSet(Collection<Pos> list, Grid grid) {
		this(grid);
		addAll(list);
	}
	private PosSet(BitSet mem, Grid grid) {
	  this.mem = mem;
	  this.grid = grid;
	  this.sz = mem.cardinality();
	}
	private PosSet(PosSet from) {
		this.grid = from.grid;
		this.mem = (BitSet)from.mem.clone();
		this.sz = from.sz;
	}
	public PosSet clone() {
		return new PosSet(this);
	}
	public BitSet toBitSet() {
		return (BitSet)mem.clone();
	}
	public Pos firstMem() {
		int id = mem.nextSetBit(0);
		return id >= 0 ? grid.pos(id) : null;
	}
	public Pos nextMem(Pos p) {
		int id = mem.nextSetBit(p.id + 1);
		return id >= 0 ? grid.pos(id) : null;
	}
	public void add(Pos p) {
		if (isMember(p)) return;
		mem.set(p.id);
		sz++;
	}
	public void addAll(Predicate<Pos> addMem) {
		for (Pos p: grid.pos1) {
			if (!isMember(p) && addMem.test(p)) add(p);
		}
	}
	public void addAll(PosSet ps) {
		this.mem.or(ps.mem);
		sz = this.mem.cardinality();
	}
	public void addAll(Collection<Pos> list) {
		for (Pos p: list) add(p);
	}
	public void addAll() {
		this.mem.set(0, grid.size());
		sz = grid.size();
	}
	public void remove(Pos p) {
		if (!isMember(p)) return;
		mem.clear(p.id);
		sz--;
	}
	public void removeAll(PosSet ps) {
		removeAll(ps.mem);
	}
	public void removeAll(BitSet bs) {
		this.mem.andNot(bs);
		sz = this.mem.cardinality();
	}
	public void removeAll(Predicate<Pos> removeTest) {
		for (Pos p = firstMem(); p != null; p = nextMem(p)) {
			if (removeTest.test(p)) remove(p);
		}
	}
	public boolean includes(PosSet ps) {
		return includes(ps.mem);
	}
	public boolean includes(BitSet bs) {
		BitSet t = (BitSet)bs.clone();
		t.andNot(this.mem);
		return t.isEmpty();
	}
	public void clear() {
		mem.clear();
		sz = 0;
	}
	public boolean isMember(Pos p) {
		return mem.get(p.id);
	}
	public PosSet or(PosSet ps) {
		BitSet bs = this.toBitSet();
		bs.or(ps.mem);
		return new PosSet(bs, grid);
	}
	public PosSet and(PosSet ps) {
		BitSet bs = this.toBitSet();
		bs.and(ps.mem);
		return new PosSet(bs, grid);
	}
	public PosSet not() {
		BitSet bs = new BitSet(grid.size());
		bs.set(0, grid.size());
		bs.andNot(this.mem);
		return new PosSet(bs, grid);
	}
	public int size() {
		return sz;
	}
	public ArrayList<Pos> toPosArray() {
		ArrayList<Pos> ret = new ArrayList<>(size());
		for (Pos p = firstMem(); p != null; p = nextMem(p)) ret.add(p);
		return ret;
	}
	@Override
	public Iterator<Pos> iterator() {
		return new PosSetIterator();
	}
	private class PosSetIterator implements Iterator<Pos> {
		Pos next = null;
		private PosSetIterator() {
			this.next = firstMem();
		}
		@Override
		public boolean hasNext() {
			return next != null;
		}
		@Override
		public Pos next() {
			Pos ret = next;
			next = nextMem(next);
			return ret;
		}
	}
	@Override
	public boolean equals(Object o) {
		if (o instanceof PosSet) {
			PosSet ps = (PosSet)o;
			if (sz != ps.sz) return false;
			return this.mem.equals(ps.mem);
		}
		return false;
	}
	public void debug() {
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < grid.H(); i++) {
			for (int j = 0; j < grid.W(); j++) {
				Pos p = grid.pos(i, j);
				out.append(isMember(p) ? '*': '.');
			}
			out.append('\n');
		}
		System.err.println(out.toString());
	}
}
