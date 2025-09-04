# グラフ
グラフの辺の接続情報を管理する。グラフ全体を表す`Graph`クラスと、辺を表す`Edge`クラスを提供する。

辺に付加情報(重み等)がある場合は、`Edge`クラスを拡張して使用することを想定。付加情報がない場合の記述を簡潔にする目的で`Graph`クラスはジェネリクスを使用していないため、`Edge`クラスを拡張した場合は、辺の取り出し時に適宜キャストして使用する。

## `Graph`クラス
### コンストラクタ
```java
public Graph(int n)
```
- 引数
  - `n` : 頂点数
- 計算量
  - $O(n)$

### 頂点間に有向辺を追加
```java
public void addDirEdge(Edge e)
public void addDirEdge(int from, int to)
public void addDirEdge(int from, int to, int id)
```
頂点`from`から頂点`to`への辺を追加する。`Edge`インスタンスを渡して追加することも可能(`Edge`クラスを拡張した場合を想定)
- 引数
  - `e` : 辺を表す`Edge`インスタンス
  - `from`, `to` : 頂点番号 $(0 \le \mathrm{from}, \mathrm{to} \lt n)$
  - `id` : 辺のID。指定しなかった場合は追加済みの辺の本数をIDとみなす
- 計算量
  - ならし $O(1)$

### 頂点間に無向辺を追加
```java
public void addUndirEdge(int u, int v)
public void addUndirEdge(int u, int v, int id)
```
頂点`u`と頂点`v`を結ぶ無向辺を追加する。
- 引数
  - `u`, `v` : 頂点番号 $(0 \le u, v \lt n)$
  - `id` : 辺のID。指定しなかった場合は追加済みの辺の本数をIDとみなす
- 計算量
  - ならし $O(1)$

### 辺の総数
```java
public int edgeSize()
```
全ての辺の本数を返す。無向辺は両方向で2本とみなす。
- 計算量
  - $O(1)$

### 辺のIDの最大値
```java
public int maxEdgeId()
```
辺に設定されたIDの最大値を返す。
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
public Edge edge(int v, int i)
```
指定した頂点から張られている`i`番目の辺を返す。
- 引数
  - `v` : 頂点番号 $(0 \le v \lt n)$
  - `i` : その頂点から張られている何番目の辺か $(0 \le i \lt \mathrm{edgeSize(v)})$
- 計算量
  - $O(1)$

### ある頂点から張られている辺全てを取得
```java
public ArrayList<Edge> edges(int v)
```
指定した頂点から張られている全ての辺を返す。
- 引数
  - `v` : 頂点番号 $(0 \le v \lt n)$
- 計算量
  - $O(1)$

### ある頂点から張られている辺の接続先頂点番号を全て取得
```java
public int[] edgesTo(int v)
```
指定した頂点から張られている全ての辺の接続先頂点番号を返す。
- 引数
  - `v` : 頂点番号 $(0 \le v \lt n)$
- 計算量
  - $O(1)$

### グラフの頂点の個数
```java
public int size()
```
グラフの頂点の個数を取得する。
- 計算量
  - $O(1)$

## `Edge`クラス
### コンストラクタ
```java
public Edge(int from, int to, int id)
```
- 引数
  - `from`, `to` : 頂点番号 $(0 \le \mathrm{from}, \mathrm{to} \lt n)$
  - `id` : 辺のID
- 計算量
  - $O(1)$

### 各情報の取得
```java
public int from()
public int to()
public int id()
```
- 計算量
  - $O(1)$
