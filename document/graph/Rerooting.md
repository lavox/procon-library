# 全方位木DP
全方位木DPを行う。オートボクシングによる性能劣化を避けるため、`long`専用の以下のクラスも利用可能。
- `LongRerooting`

## 使用方法
- `Rerooting`または`LongRerooting`クラスを継承し、以下のメソッドを実装する(匿名クラスでも可)。なお、`LongRerooting`の場合は`E`の代わりに`long`型となる
  - `public abstract E e()`
  - `public abstract E merge(E x, E y)`
  - `public abstract E mergeSubtree(E x, Edge e)`
  - `public abstract E nodeValue(E x, Node n)`
  - `protected E leaf(Edge e)`
  - 各メソッドの意味は以下の通り
    - `e()`: 単位元
    - `merge(x, y)`: ある頂点配下の2辺の値`x`, `y`をマージした値を求める
    - `mergeSubtree(x, e)`: ある頂点配下の親の辺を除く全辺のマージ結果`x`から、親の辺`e`の値を求める(`e.to()`が対象頂点となっている)
    - `nodeValue(x, n)`: 頂点`n`配下の親の辺を含む全辺のマージ結果`x`から、頂点の値を求める
    - `leaf(e)`: 葉となる頂点について、`mergeSubtree(x, e)`とは異なる実装が必要な場合にオーバーライドする。オーバーライドしない場合は`mergeSubtree(e(), e)`が使用される
- 継承したクラスをインスタンス化する
- `addEdge(u, v)`により、辺を追加する
- `build()`により、全方位木DPを実行する
- `nodeValue(id)`により、各頂点の値を取得する。必要により`edgeValue(id)`も取得可能

実装例は以下参照
  - `Rerooting`: [ABC348E](https://github.com/lavox/procon-library/blob/main/test/manual/atcoder/abc348_e/src/Main.java) ([問題](https://atcoder.jp/contests/abc348/tasks/abc348_e))
  - `LongRerooting`: [ABC222F](https://github.com/lavox/procon-library/blob/main/test/manual/atcoder/abc222_f/src/Main.java) ([問題](https://atcoder.jp/contests/abc222/tasks/abc222_f))

## 全方位木DP用クラス
### コンストラクタ
```java
// 一般クラス用
public Rerooting(int n)
// long用
public LongRerooting(int n)
```
- 引数
  - `n` : 頂点数
- 計算量
  - $O(n)$

### 頂点間に辺を追加
```java
public void addEdge(int u, int v)
```
頂点`u`,`v`間に辺を追加する。
- 引数
  - `u`, `v` : 頂点番号 $(0 \le u, v \lt n)$
- 計算量
  - ならし $O(1)$

### 全方位木DPの実行
```java
public void build()
```
全方位木DPを実行する
- 計算量
  - $O(n)$

### 頂点の値の取得
```java
// 一般クラス用
public E nodeValue(int id)
// long用
public long nodeValue(int id)
```
全方位木DPで求めた頂点の値を取得する。事前に`build()`を実行しておく必要がある。
- 引数
  - `id` : 頂点番号 $(0 \le \mathrm{id} \lt n)$
- 計算量
  - $O(1)$

### 辺の値の取得
```java
// 一般クラス用
public E edgeValue(int id, boolean rev)
// long用
public long edgeValue(int id, boolean rev)
```
全方位木DPで求めた辺の値を取得する。`id`番目に追加した辺の値を取得する。`rev`が`true`の場合は`u`を親として`v`配下の部分木、`false`の場合は`v`を親として`u`配下の部分木の値となる。事前に`build()`を実行しておく必要がある。
- 引数
  - `id` : 辺番号 $(0 \le \mathrm{id} \lt n-1)$
  - `rev` : 辺の向き
- 計算量
  - $O(1)$

### 辺情報の取得
```java
public ArrayList<Edge> edges()
```
辺の情報を取得する
- 計算量
  - $O(1)$

## 頂点用クラス
```java
public class Node {
  public int id()
}
```
- `id()`: 頂点番号

## 辺用クラス
```java
public class Edge {
  public int id()
  public Node from()
  public Node to()
}
```
- `id()`: 辺番号
- `from()`: 親側の頂点
- `from()`: 子側の頂点

## 検証
- [ABC348E Minimize Sum of Distances (AtCoder)](https://atcoder.jp/contests/abc348/submissions/67996655) (Rerooting)
- [ABC222F Expensive Expense (AtCoder)](https://atcoder.jp/contests/abc222/submissions/67994485) (LongRerooting)
