# Tool
## ライブラリマージツール
### 特徴
コンテスト時の提出ファイルに、指定したライブラリファイルをマージ(取り込み)することができます。

- 指定したライブラリファイルを、指定した取り込み先ファイルの末尾に追加します
- ライブラリファイルから必要なimport文を抽出して、取り込み先ファイルの先頭に追加します
  - ライブラリファイルで定義されているimport文で、取り込み先ファイルのimportに存在しないもの
- ライブラリファイルから参照されている別のライブラリがあれば、再帰的に取り込み先ファイルに取り込みます
- ライブラリファイルのpackage文を削除します (取り込み先ファイルでは不要なため)
- ライブラリファイルの行頭のpublic class(interface, abstract class, enum)のpublic宣言を削除します (取り込み先ファイルでは通常publicではなくなるため)

なお、本ツールは生成AIで生成されたプログラムに、筆者が手を加えたものです。

### 使用方法
以下のコマンドを実行します。

```
merge_javalib [-h] --main MAIN --lib LIB [--include INCLUDE [INCLUDE ...]]
```

- -h, --help
  - ヘルプを表示します
- --main MAIN
  - 取り込み先のファイルを指定します(例: --main Main.java)
- --lib LIB
  - ライブラリが格納された起点ディレクトリを指定します(例: --lib procon-library/lib)
- --include INCLUDE
  - 取り込み対象クラスを指定します。クラス名のみでもFQDNでも指定可能です(例: --include UnionFInd 、 --include data_structure.UnionFind)
  - 拡張子.javaはつけないでください
  - 複数指定することも可能です
  - 指定しなかった場合は、CUIでディレクトリ・ファイルが一覧表示されるので、Enterキーで選択してください。この場合は1ファイルのみ選択可能です。

- 実行例1 (取り込み対象ファイルをコマンドで指定)
```
merge_javalib --main Main.java --lib procon_library/lib --include UnionFind
```

- 実行例2 (取り込み対象ファイルをCUIで選択)
```
merge_javalib --main Main.java --lib procon_library/lib
```

### インストール方法
merge_javalibディレクトリ配下で以下を実行してください。
```sh
cd merge_javalib
pip install .
```
依存ライブラリとして、[javalang](https://github.com/c2nes/javalang), [pick](https://github.com/aisk/pick)も合わせてインストールされます。

### ライセンス
本ツールは[CC0 1.0 Universal](https://creativecommons.org/publicdomain/zero/1.0/legalcode)の下で公開しています。
ただし、以下の依存ライブラリについては、それぞれのライセンスに従いますのでご留意ください。

### 依存ライブラリ
本ツールは以下のサードパーティーソフトウェアに依存しています。

1. javalang
  - [MIT License](https://github.com/c2nes/javalang/blob/master/LICENSE.txt)
  - リポジトリ: https://github.com/c2nes/javalang
2. pick
  - [MIT License](https://github.com/aisk/pick/blob/master/LICENSE)
  - リポジトリ: https://github.com/aisk/pick
