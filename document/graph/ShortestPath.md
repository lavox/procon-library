# 最短経路探索アルゴリズム
グラフに対する各種最短経路探索アルゴリズムを提供するクラス。
以下のアルゴリズムを提供する。

| アルゴリズム         | 探索対象       | 負の辺 | 計算量              |
|:---------------------|:---------------|:-------|:--------------------|
| Dijkstra法           | 単一・複数始点 | 非対応 | $O((n + m)\log{n})$ |
| BFS                  | 単一・複数始点 | 非対応 | $O(n + m)$          |
| 01-BFS               | 単一・複数始点 | 非対応 | $O(n + m)$          |
| Bellman-Ford法       | 単一・複数始点 | 対応   | $O(nm)$             |
| Warshall-Floyd法     | 全頂点間       | 非対応 | $O(n^2)$            |
| 巡回セールスマン問題 | 巡回経路       | 非対応 | $O(2^n n^2)$        |

### Dijkstra法
```java
public static <E extends CostEdge> Dist dijkstra(GenericGraph<E> g, int s)
public static <E extends CostEdge> Dist dijkstra(GenericGraph<E> g, int[] starts, Predicate<E> canPass)
```
- 引数
  - `g` : グラフ情報。辺は `CostEdge` クラスで `cost()` を距離とみなす
  - `s` : 開始地点 $(0 \lt s \le n)$
  - `starts` : 開始地点を複数指定する $(0 \lt s[i] \le n)$
  - `canPass` : 辺が使用可能な場合は`true`、不可の場合`false`を返す関数。未指定の場合は全ての辺が使用可能とみなす。
- 戻り値 
  - `Dist` クラス
- 計算量
  - $O((n + m)\log{n})$ ($n$ は頂点数、 $m$ は辺数)

### BFS(幅優先探索)
```java
public static <E extends Edge> Dist bfs(GenericGraph<E> g, int s)
public static <E extends Edge> Dist bfs(GenericGraph<E> g, int[] starts, Predicate<E> canPass)
```
- 引数
  - `g` : グラフ情報。辺の距離はすべて1とみなす
  - `s` : 開始地点 $(0 \lt s \le n)$
  - `starts` : 開始地点を複数指定する $(0 \lt s[i] \le n)$
  - `canPass` : 辺が使用可能な場合は`true`、不可の場合`false`を返す関数。未指定の場合は全ての辺が使用可能とみなす。
- 戻り値 
  - `Dist` クラス
- 計算量
  - $O(n + m)$ ($n$ は頂点数、 $m$ は辺数)

### 01-BFS
```java
public static <E extends Edge> Dist bfs01(GenericGraph<E> g, int s, Predicate<E> edge1)
public static <E extends Edge> Dist bfs01(GenericGraph<E> g, int[] starts, Predicate<E> edge1, Predicate<E> canPass)
```
- 引数
  - `g` : グラフ情報。辺の距離はすべて1とみなす
  - `s` : 開始地点 $(0 \lt s \le n)$
  - `starts` : 開始地点を複数指定する $(0 \lt s[i] \le n)$
  - `edge1` : 長さ1の辺は`true`、0の辺は`false`を返す関数。
  - `canPass` : 辺が使用可能な場合は`true`、不可の場合`false`を返す関数。未指定の場合は全ての辺が使用可能とみなす。
- 戻り値 
  - `Dist` クラス
- 計算量
  - $O(n + m)$ ($n$ は頂点数、 $m$ は辺数)

### Bellman-Ford法
```java
public static <E extends CostEdge> Dist bellmanFord(GenericGraph<E> g, int s)
public static <E extends CostEdge> Dist bellmanFord(GenericGraph<E> g, int[] starts)
```
- 引数
  - `g` : グラフ情報。辺の距離はすべて1とみなす
  - `s` : 開始地点 $(0 \lt s \le n)$
  - `starts` : 開始地点を複数指定する $(0 \lt s[i] \le n)$
- 戻り値 
  - `Dist` クラス
- 計算量
  - $O(nm)$ ($n$ は頂点数、 $m$ は辺数)

### Warshall-Floyd法
#### 初期の距離テーブル生成
```java
public static DistMap wfCreateMap(int n)
```
初期の距離テーブルを生成する。同じ頂点同士は`0`、それ以外は`INF`で初期化される。
- 引数
  - `n` : 頂点数
- 戻り値 
  - `DistMap` クラス
- 計算量
  - $O(n^2)$

#### 最短距離の算出
```java
public static DistMap warshallFloyd(DistMap d)
```
全頂点間の最短経路を求める。
- 引数
  - `d` : 距離テーブル。予め張られている辺の距離を入れておく。
- 戻り値 
  - `DistMap` クラス。引数を書き換えた結果を返す。
- 計算量
  - $O(n^3)$

#### 辺追加による距離更新
```java
public static DistMap wfUpdateEdge(DistMap d, int from, int to, long dist)
```
頂点`from`から頂点`to`への距離`dist`の辺を追加して距離テーブルを再計算する。
- 引数
  - `d` : 距離テーブル
  - `from`, `to` : 頂点番号 $(0\le \mathrm{from}, \mathrm{to}\lt n)$
  - dist: 距離
- 戻り値 
  - `DistMap` クラス。引数を書き換えた結果を返す。
- 計算量
  - $O(n^2)$

### 巡回セールスマン問題(TSP)
全頂点を巡る最短距離を求める。初期地点に戻る閉路の最短を求める問題と、初期地点からスタートして全地点を巡る(初期地点には戻らない)パスの最短を求める問題に対応。

それ以外の条件の場合は、戻り値に含まれるdp表を使用して求めるか、ライブラリを改造して対応する必要がある。
```java
public static TspRoute tsp(long[][] distMap)
public static TspRoute tsp(long[][] distMap, int s, boolean oneway)
```
全頂点を1回ずつ巡る最短経路を求める。
- 引数
  - `distMap` : 頂点間の距離。頂点数を $n$ として $n\times n$ の配列で与える。`distMap[i][j]` は頂点`i`と頂点`j`の間の距離
  - `s` : 開始地点 $(0 \lt s \le n)$
  - `oneway` : 片道の場合`true`。閉路の場合`false`
- 戻り値 
  - `TspRoute` クラス
- 計算量
  - $O(2^n n^2)$

## 戻り値用クラス
### `Dist`クラス
```java
public static class Dist {
  public int n = 0;
  public long[] dist = null;
  public int[] parent = null;
  public int[] path(int t)
  public boolean hasNegativeLoop()
}
```
Dijkstra法、BFS、01-BFS、Bellman-Ford法の探索結果。
- メンバ変数
  - `n` : 頂点数
  - `dist` : 各頂点への最短距離
  - `parent` : 各頂点への最短距離を実現する1つ前の頂点番号
- メソッド
  - `path(t)` : 頂点 `t` への最短距離を実現するパス。頂点番号を順に格納した配列を返す。
  - `hasNegativeLoop()` : 負の閉路を持つかを返す

### `DistMap`クラス
```java
public static class DistMap {
  public final int n;
  public final long[][] dist;
  public boolean hasNegativeLoop()
}
```
Warshall-Floyd法の距離テーブル(入力 兼 探索結果)。
- メンバ変数
  - `n` : 頂点数
  - `dist` : 各頂点間の距離テーブル
- メソッド
  - `hasNegativeLoop()` : 負の閉路を持つかを返す

### `TspRoute`クラス
```java
public static class TspRoute {
  public int n;
  public int g = -1;
  public long dist = INF;
  public long[][] dp = null;
  public long[][] distMap = null;
  public int[] path();
}
```
巡回セールスマン問題の探索結果。
- メンバ変数
  - `n` : 頂点数
  - `g` : 最短ルート(の1つ)の最終頂点番号 (閉路の場合は開始地点以外の最後の頂点)
  - `dist` : 最短距離。経路が存在しない場合は `ShortestPath.INF`
  - `dp` : DP表。サイズは $n\times 2^n$
  - `distMap` : 距離テーブル。第１引数は頂点番号、第２引数は訪問済み頂点のビット表現
- メソッド
  - `path()` : 最短ルート(の1つ)が通る頂点順を表す長さ $n$ の配列を返す。経路が存在しない場合はnull。計算量は $O(2^n n)$

## 検証
- Dijkstra
  - [典型アルゴリズム問題集 D 単一始点最短経路問題 (AtCoder)](https://atcoder.jp/contests/typical-algorithm/submissions/74014869)
  - [Shortest Path (Library Checker)](https://judge.yosupo.jp/submission/359084)
- BFS
  - [ABC160D Line++ (AtCoder)](https://atcoder.jp/contests/abc160/submissions/74031344)
- 01BFS
  - [ABC246E Bishop 2 (AtCoder)](https://atcoder.jp/contests/abc246/submissions/74032072)
- Bellman-Ford法
  - [Single Source Shortest Path (Negative Edges) (Aizu Online Judge)](https://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=11369622#2)
- Warshall-Floyd法
  - [All Pairs Shortest Path (Aizu Online Judge)](https://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=11369640#2)
  - [ABC416E Development (AtCoder)](https://atcoder.jp/contests/abc416/submissions/74049638)
- 巡回セールス問題
  - [典型アルゴリズム問題集 C 巡回セールスマン問題 (AtCoder)](https://atcoder.jp/contests/typical-algorithm/submissions/74014745)
