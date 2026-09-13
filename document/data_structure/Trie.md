# Trie木
整数列を格納し、共通接頭辞を共有して管理するデータ構造。各ノードは`int`型のノード番号で表され、根のノード番号は`Trie.ROOT`、子が存在しないことを表す値は`Trie.NONE`となる。

Trieの構造のみを管理する`DenseTrie`、`SparseTrie`と、各整数列の個数を併せて管理する`TrieMultiset`が使用可能。

- `DenseTrie`
  - 各ノードについて、すべての値に対応する子ノード番号を配列で管理する
  - アルファベットサイズが小さい場合に適している
- `SparseTrie`
  - 存在する辺のみをハッシュテーブルで管理する
  - アルファベットサイズが大きい場合や、各ノードの分岐が少ない場合に適している

アルファベットサイズとは、整数列の各要素が取り得る値の種類数を表す。以下、アルファベットサイズを $A$、Trieのノード数を $V$ とする。Trieに格納する各値は $0$ 以上 $A$ 未満である必要がある。

## Trieインターフェース および その実装クラス

### 定数
```java
public static final int NONE = -1
public static final int ROOT = 0
```
- `NONE` : 子ノードが存在しないことを表す値
- `ROOT` : 根のノード番号

### コンストラクタ
```java
public DenseTrie(int alphabetSize)
public DenseTrie(int alphabetSize, int defaultCapacity)
public SparseTrie(int alphabetSize)
public SparseTrie(int alphabetSize, int defaultSize)
```
空のTrieを生成する。生成時点では根のみが存在する。
- 引数
  - `alphabetSize` : 整数列の各要素が取り得る値の種類数 $A$
  - `defaultCapacity` : `DenseTrie`で事前に確保するノード数
  - `defaultSize` : `SparseTrie`で事前に確保する辺数
- 計算量
  - `DenseTrie(int alphabetSize)` : $O(A)$
  - `DenseTrie(int alphabetSize, int defaultCapacity)` : $O(A \cdot \mathrm{defaultCapacity})$
  - `SparseTrie(int alphabetSize)` : $O(1)$
  - `SparseTrie(int alphabetSize, int defaultSize)` : $O(\mathrm{defaultSize})$

`defaultCapacity`、`defaultSize`は初期容量であり、指定した値を超えてノードや辺を追加することも可能。あらかじめ必要数が分かっている場合は、再確保を避けるために指定することを推奨する。

### ノード数・アルファベットサイズ
```java
public int size()
public int alphabetSize()
```
`size()`は現在のノード数を、`alphabetSize()`はアルファベットサイズを返す。
- 計算量
  - $O(1)$

### 子ノードの取得
```java
public int getChild(int node, int i)
```
ノード`node`から値`i`に対応する辺をたどった子ノード番号を返す。子ノードが存在しない場合は`Trie.NONE`を返す。
- 引数
  - `node` : 遷移元のノード番号 $(0 \le node \lt V)$
  - `i` : 辺に対応する値 $(0 \le i \lt A)$
- 計算量
  - `DenseTrie` : $O(1)$
  - `SparseTrie` : ならし $O(1)$

### 子ノードの生成
```java
public int createChild(int node, int i)
```
ノード`node`から値`i`に対応する辺と子ノードを生成し、生成したノード番号を返す。対応する子ノードがまだ存在しないことを前提とする。
- 引数
  - `node` : 遷移元のノード番号 $(0 \le node \lt V)$
  - `i` : 辺に対応する値 $(0 \le i \lt A)$
- 計算量
  - `DenseTrie` : ならし $O(A)$
  - `SparseTrie` : ならし $O(1)$

### 子ノードの取得または生成
```java
public int getOrCreateChild(int node, int i)
```
ノード`node`から値`i`に対応する子ノード番号を返す。子ノードが存在しない場合は、新しく生成してそのノード番号を返す。
- 引数
  - `node` : 遷移元のノード番号 $(0 \le node \lt V)$
  - `i` : 辺に対応する値 $(0 \le i \lt A)$
- 計算量
  - `DenseTrie` : 子ノードが存在する場合は $O(1)$、生成する場合はならし $O(A)$
  - `SparseTrie` : ならし $O(1)$

### 空間計算量
- `DenseTrie` : $O(VA)$
- `SparseTrie` : $O(V)$

## TrieMultiset
Trieに格納された整数列の個数を多重集合として管理する。各ノードについて、そのノードを通過する整数列の個数と、そのノードで終端する整数列の個数を保持する。

### コンストラクタ
```java
public TrieMultiset(Trie trie)
public TrieMultiset(Trie trie, int defaultCapacity)
```
指定したTrieを構造として使用する空の多重集合を生成する。Trieに既にノードが存在する場合も、すべてのカウントは0で初期化される。

生成後に`trie`へ直接ノードを追加するとカウントを格納する配列と同期しなくなるため、整数列とノードの追加は`TrieMultiset.add`を通して行う。
- 引数
  - `trie` : 使用するTrie
  - `defaultCapacity` : カウントを格納する配列で事前に確保するノード数
- 計算量
  - `TrieMultiset(Trie trie)` : $O(V)$
  - `TrieMultiset(Trie trie, int defaultCapacity)` : $O(V+\mathrm{defaultCapacity})$

### 要素数
```java
public int size()
```
格納されている整数列の個数を重複を含めて返す。
- 計算量
  - $O(1)$

### 整数列の追加
```java
public int add(int length, int[] data)
public int add(int length, int[] data, int[] path)
public int add(int length, IntUnaryOperator dataAt)
public int add(int length, IntUnaryOperator dataAt, int[] path)
```
長さ`length`の整数列を1個追加し、終端ノード番号を返す。`path`を指定した場合は、`path[0]`から`path[length]`に根から終端までのノード番号を格納する。
- 引数
  - `length` : 追加する整数列の長さ $L$
  - `data` : 追加する整数列。先頭から`length`要素を使用する
  - `dataAt` : `i`番目の値を返す`IntUnaryOperator`
  - `path` : 経路を格納する長さ $L+1$ 以上の配列。不要な場合は省略可能
- 計算量
  - `DenseTrie` : $O(L+CA)$。$C$ は新しく生成したノード数
  - `SparseTrie` : ならし $O(L)$

### 整数列の削除
```java
public int remove(int length, int[] data)
public int remove(int length, int[] data, int[] path)
public int remove(int length, IntUnaryOperator dataAt)
public int remove(int length, IntUnaryOperator dataAt, int[] path)
```
長さ`length`の整数列を1個削除し、終端ノード番号を返す。削除する整数列が1個以上格納されていることを前提とする。カウントが0になったノードもTrieの構造からは削除されない。

`path`を指定した場合は、`path[0]`から`path[length]`に根から終端までのノード番号を格納する。
- 引数
  - `length` : 削除する整数列の長さ $L$
  - `data` : 削除する整数列。先頭から`length`要素を使用する
  - `dataAt` : `i`番目の値を返す`IntUnaryOperator`
  - `path` : 経路を格納する長さ $L+1$ 以上の配列。不要な場合は省略可能
- 計算量
  - `DenseTrie` : $O(L)$
  - `SparseTrie` : ならし $O(L)$

### ノードを通過・終端する整数列の個数
```java
public int passCount(int node)
public int terminalCount(int node)
```
`passCount(node)`は、根から`node`までの整数列を接頭辞として持つ整数列の個数を返す。`node`で終端する整数列も含む。`terminalCount(node)`は、`node`で終端する整数列の個数を返す。

`node`に`Trie.NONE`を指定した場合は0を返す。また、`passCount(Trie.ROOT)`は`size()`と等しい。
- 引数
  - `node` : 個数を取得するノード番号、または`Trie.NONE`
- 計算量
  - $O(1)$

### 整数列の個数
```java
public int count(int length, int[] data)
public int count(int length, IntUnaryOperator dataAt)
```
指定した整数列と完全に一致する整数列の個数を返す。
- 引数
  - `length` : 検索する整数列の長さ $L$
  - `data` : 検索する整数列。先頭から`length`要素を使用する
  - `dataAt` : `i`番目の値を返す`IntUnaryOperator`
- 計算量
  - `DenseTrie` : $O(L)$
  - `SparseTrie` : ならし $O(L)$

### 接頭辞を持つ整数列の個数
```java
public int countPrefix(int length, int[] data)
public int countPrefix(int length, IntUnaryOperator dataAt)
```
指定した整数列を接頭辞として持つ整数列の個数を返す。指定した整数列と完全に一致するものも含む。
- 引数
  - `length` : 接頭辞の長さ $L$
  - `data` : 接頭辞となる整数列。先頭から`length`要素を使用する
  - `dataAt` : `i`番目の値を返す`IntUnaryOperator`
- 計算量
  - `DenseTrie` : $O(L)$
  - `SparseTrie` : ならし $O(L)$

### 整数列の存在判定
```java
public boolean contains(int length, int[] data)
public boolean contains(int length, IntUnaryOperator dataAt)
```
指定した整数列が1個以上格納されているかを返す。
- 引数
  - `length` : 検索する整数列の長さ $L$
  - `data` : 検索する整数列。先頭から`length`要素を使用する
  - `dataAt` : `i`番目の値を返す`IntUnaryOperator`
- 計算量
  - `DenseTrie` : $O(L)$
  - `SparseTrie` : ならし $O(L)$

### 使用例
```java
int maxNodeCount = 1 + 30 * Q;
Trie trie = new DenseTrie(2, maxNodeCount);
TrieMultiset multiset = new TrieMultiset(trie, maxNodeCount);

int x = 123;
multiset.add(30, i -> (x >>> (29 - i)) & 1);
boolean exists = multiset.contains(30, i -> (x >>> (29 - i)) & 1);
```

## 検証
- [Set Xor-Min (Library Checker)](https://judge.yosupo.jp/submission/402034)
- [ABC475E Quiz Competition: Qualifiers (AtCoder)](https://atcoder.jp/contests/abc475/submissions/79241101)
