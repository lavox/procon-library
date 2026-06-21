# グラフ
グラフの辺の接続情報を管理する。グラフ全体を表す`Graph`クラスと、辺を表す`Edge`クラスを提供する。

辺に付加情報(重み等)がある場合は、`Edge`クラスを拡張して使用することを想定。その場合は`GenericGraph<E extends Edge>`クラスを使用する。

- `Graph`
  - 汎用的なグラフインターフェース。各頂点の辺の処理用インターフェースを提供
- `GenericGraph`
  - 一般のグラフ向けの実装クラス。
- `SimpleGraph`
  - 辺の接続関係、コストのみを簡易に扱える実装クラス。
- `GridGraph`
  - 2次元盤面をグラフとして扱うための実装クラス。
- `LayeredGraph`
  - ベースとなるグラフに対して頂点倍化したグラフを扱うための実装クラス。
- `Edge`
  - 辺を表すクラス

## `Graph`インターフェース
### 頂点数を取得
```java
public int size();
```

### 各頂点の辺に対して処理を行う
```java
public void forEachEdge(int v, EdgeConsumer action);
```
- 引数
  - `v` : 頂点番号
  - `action` : 各辺に対して実行する処理

### 各頂点の辺のイテレータ
```java
public default Iterable<? extends Edge> edges(int v)
public default PrimitiveIterator.OfInt edgesTo(int v)
```
`edges()`は`Edge`オブジェクトのイテレータ、`edgesTo()`は接続先の頂点番号のイテレータを取得する。
- 引数
  - `v` : 頂点番号

### `Graph.EdgeConsumer`インターフェース
```java
@FunctionalInterface
public interface EdgeConsumer {
  public void accept(int from, int to, int id, long cost);
}
```
- 引数
  - `from` : 辺の接続元頂点番号
  - `to` : 辺の接続先頂点番号
  - `id` : 辺のID
  - `cost` : 辺のコスト

## `GenericGraph<E extends Edge>`クラス
### コンストラクタ
```java
public GenericGraph(int n)
```
- 引数
  - `n` : 頂点数
- 計算量
  - $O(n)$

### 頂点間に有向辺を追加
```java
public void addEdge(E e)
```
`Edge`インスタンスを指定して辺を追加する
- 引数
  - `e` : 辺を表す`Edge`インスタンス
- 計算量
  - ならし $O(1)$

### 最大頂点IDを取得
```java
public int maxEdgeId()
```
- 計算量
  - $O(1)$

### 辺情報を構築
```java
public void build()
```
追加した辺情報を整理して使用できるようにする。辺の追加が終わった後、`forEachEdge()`等を実行する前に呼ぶこと。
- 計算量
  - $O(n + m)$ ($n$ は頂点数、 $m$ は辺数)

### 辺の総数
```java
public int edgeSize()
```
- 計算量
  - $O(1)$

### ある頂点から張られている辺の本数
```java
public int edgeSize(int v)
```
指定した頂点の辺の本数を返す。有向辺の場合は、指定した頂点を`from`とする辺の本数。
- 引数
  - `v` : 頂点番号 $(0 \le v \lt n)$
- 計算量
  - $O(1)$

### ある頂点から張られている辺の取得
```java
public E edge(int v, int i)
```
指定した頂点から張られている`i`番目の辺を返す。
- 引数
  - `v` : 頂点番号 $(0 \le v \lt n)$
  - `i` : その頂点から張られている何番目の辺か $(0 \le i \lt \mathrm{edgeSize(v)})$
- 計算量
  - $O(1)$

## `SimpleGraph`クラス
### コンストラクタ
```java
public SimpleGraph(int n)
```
- 引数
  - `n` : 頂点数
- 計算量
  - $O(n)$

### 頂点間に有向辺を追加
```java
public void addDirEdge(int from, int to)
public void addDirEdge(int from, int to, int id)
public void addDirEdge(int from, int to, int id, long cost)
```
頂点`from`から頂点`to`への辺を追加する。
- 引数
  - `from`, `to` : 頂点番号 $(0 \le \mathrm{from}, \mathrm{to} \lt n)$
  - `id` : 辺のID。指定しなかった場合は-1とみなす
  - `cost` : 辺のコスト。指定しなかった場合は1とみなす
- 計算量
  - ならし $O(1)$

### 頂点間に無向辺を追加
```java
public void addUndirEdge(int u, int v)
public void addUndirEdge(int u, int v, int id)
public void addUndirEdge(int u, int v, int id, long cost)
```
頂点`u`と頂点`v`を結ぶ無向辺を追加する。
- 引数
  - `u`, `v` : 頂点番号 $(0 \le u, v \lt n)$
  - `id` : 辺のID。指定しなかった場合は-1とみなす
  - `cost` : 辺のコスト。指定しなかった場合は1とみなす
- 計算量
  - ならし $O(1)$

## `Edge`クラス
### コンストラクタ
```java
public Edge(int from, int to, int id, long cost)
public Edge(int from, int to, int id)
public Edge(int from, int to)
```
- 引数
  - `from`, `to` : 頂点番号 $(0 \le \mathrm{from}, \mathrm{to} \lt n)$
  - `id` : 辺のID。省略時は-1とみなす
  - `cost` : 辺のコスト。省略時は1とみなす
- 計算量
  - $O(1)$

### 各情報の取得
```java
public int from()
public int to()
public int id()
public long cost()
```
- 計算量
  - $O(1)$

## `GridGraph`クラス
### コンストラクタ
```java
public GridGraph(int height, int width)
public GridGraph(int height, int width, int[][] dir)
```
- 引数
  - `height` : 高さ
  - `width` : 幅
  - `dir` : 隣接マスを表す2次元配列。隣接マスの数を $a$ とすると、$a\times 2$ の2次元配列で、各隣接マスの方向ベクトルを指定する。省略すると辺で接する4マスを隣接とみなす。
    - 4方向の場合は`DIR4`を、8方向の場合は`DIR8`を指定する
- 計算量
  - $O(1)$

### 通行不可辺の指定
```java
public void setForbiddenEdge(EdgePredicate forbiddenEdge)
public boolean isForbiddenEdge(int r0, int c0, int r1, int c1)
```
`EdgePredicate`を使用して通行不可の辺を指定するか、本クラスを継承した上で`isForbiddenEdge()`をオーバーライドして通行不可の辺を指定する。

いずれの場合も、`(r0, c0)`から`(r1, c1)`へ向かうのが不可の場合は`true`を返すようにする。

### 辺のコストの指定
```java
public void setEdgeCostProvider(EdgeToLongFunction edgeCostProvider)
public long edgeCost(int r0, int c0, int r1, int c1)
```
`EdgeToLongFunction`を使用して辺のコストを指定するか、本クラスを継承した上で`edgeCost()`をオーバーライドして通行不可の辺を指定する。

いずれの場合も、`(r0, c0)`から`(r1, c1)`へ向かう辺のコストを指定する。
指定しなかった場合は、コストは1とみなす。

### グラフのサイズの取得
```java
public int size()
public int height()
public int width()
```
グラフのマス数、高さ、幅を取得する。

### 座標と頂点番号の変換
```java
public int r(int v)
public int c(int v)
public int v(int r, int c)
```
`(r, c)`座標と、頂点番号を変換する。`r`は高さ方向、`c`は幅方向の座標とする。
- 引数
  - `v` : 頂点番号
  - `r` : 高さ方向の座標
  - `c` : 幅方向の座標
- 計算量
  - $O(1)$

### マスが盤の範囲内かどうかを判定
```java
public boolean isValid(int r, int c)
public boolean isValid(int v)
```
- 引数
  - `v` : 頂点番号
  - `r` : 高さ方向の座標
  - `c` : 幅方向の座標
- 計算量
  - $O(1)$

## `LayeredGraph`クラス
以下のいずれかの方法で使用する。
- 以下の2メソッドをオーバーライドする
  - `forEachBaseEdge(bv0, layer0, bv1, eid, bcost, action)` : ベースグラフの辺から誘導される辺
  - `forEachLayerEdge(bv0, layer0, action)` : 上記以外の辺(特に、ベースグラフの同一頂点でLayer間に張る辺)
- `forEachEdge(v, action)` : 上記の形式で対応できない場合にオーバーライドする

### コンストラクタ
```java
public LayeredGraph(Graph baseGraph, int layerSize)
```
- 引数
  - `baseGraph` : ベースとなるグラフ
  - `layerSize` : Layerの数
- 計算量
  - $O(1)$

### グラフのサイズの取得
```java
public int size()
public int baseSize()
public int layerSize()
```
頂点倍化したグラフのサイズ、ベースグラフのサイズ、Layerの数を取得する。

### ベースグラフの頂点番号・レイヤーと、頂点番号を変換する
```java
public int bv(int v)
public int layer(int v)
public int v(int bv, int layer)
```
ベースグラフの頂点番号`bv`、レイヤー`layer`と、頂点倍化した本グラフの頂点番号`v`を変換する。
- 引数
  - `bv` : ベースグラフの頂点番号
  - `layer` : レイヤー番号
  - `v` : 本グラフの頂点番号
- 計算量
  - $O(1)$

### 頂点倍化した辺の処理を行う
```java
public void forEachEdge(int v, EdgeConsumer action)
protected void forEachBaseEdge(int bv0, int layer0, int bv1, int eid, long bcost, EdgeConsumer action)
protected void forEachLayerEdge(int bv0, int layer0, EdgeConsumer action)
```
初期実装は空実装となっているため、必要に応じてオーバーライドして実装を行う。`forEachBaseEdge()`はベースグラフの辺から誘導される辺の処理、`forEachLayerEdge()`はそれ以外の辺の処理(特に、ベースグラフの同一頂点の別レイヤーへの辺の処理)を想定している。
これらのオーバーライドで対応できない場合は、`forEachEdge()`自体をオーバーライドして処理を実装する。
- 引数
  - `v` : 本グラフの現在の頂点番号
  - `bv0` : 現在のベースグラフの頂点番号
  - `layer0` : 現在のレイヤー番号
  - `bv1` : ベースグラフの辺の遷移先頂点番号
  - `eid` : ベースグラフの辺のID
  - `bcost` : ベースグラフの辺のコスト
  - `action` : 辺の処理を行うaction

### 辺の処理を行うユーティリティメソッド
```java
protected void acceptAction(int bv0, int layer0, int bv1, int layer1, int id, long cost, EdgeConsumer action)
```
`forEachBaseEdge()`や`forEachLayerEdge()`を実装する際に使用するユーティリティメソッド。
- 引数
  - `bv0` : 現在のベースグラフの頂点番号
  - `layer0` : 現在のレイヤー番号
  - `bv1` : 遷移先のベースグラフの頂点番号
  - `layer1` : 遷移先のレイヤー番号
  - `eid` : 辺のID
  - `bcost` : 辺のコスト
  - `action` : 辺の処理を行うaction

## 検証
- `GridGraph`
  - [ABC363E Sinking Land (AtCoder)](https://atcoder.jp/contests/abc363/submissions/74570319)
- `LayeredGraph`
  - [ABC410D XOR Shortest Walk (AtCoder)](https://atcoder.jp/contests/abc410/submissions/76867859)
