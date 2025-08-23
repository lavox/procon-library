# Warshall-Floyd法
全頂点間の最短経路を求める。
- 有向グラフを扱うことが可能
- 負の辺を扱うことが可能
- 負の閉路の検出が可能

## WarshallFloydクラス
### 初期の距離テーブル生成
```java
public static long[][] createInitTable(int n)
```
初期の距離テーブルを生成する。同じ頂点同士は`0`、それ以外は`INF`で初期化される。
- 引数
  - `n` : 頂点数
- 計算量
  - $O(n^2)$

### 全頂点間最短経路
```java
public static long[][] warshallFloyd(long[][] d)
```
全頂点間の最短経路を求める。
- 引数
  - `d` : 距離テーブル。予め張られている辺の距離を入れておく。
- 計算量
  - $O(n^3)$

### 辺追加による距離更新
```java
public static long[][] updateEdge(long[][] d, int from, int to, long dist)
```
頂点`from`から頂点`to`への距離`dist`の辺を追加して距離テーブルを再計算する。
- 引数
  - `d` : 距離テーブル
  - `from`, `to` : 頂点番号 $(0\le \mathrm{from}, \mathrm{to}\lt n)$
  - dist: 距離
- 計算量
  - $O(n^2)$

### 負の閉路検出
```java
public static boolean hasNegativeLoop(long[][] d)
```
全頂点間最短経路を求めた距離テーブルに対して、負の閉路を持つか判定する。
- 引数
  - `d` : 全頂点間最短経路を求めた距離テーブル。
- 計算量
  - $O(n)$

## 検証
- [All Pairs Shortest Path (Aizu Online Judge)](https://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=10806482#2)
- [ABC416E Development (AtCoder)](https://atcoder.jp/contests/abc416/submissions/67981814)
