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

### 負の閉路検出
```java
public static boolean hasNegativeLoop(long[][] d)
```
全頂点間最短経路を求めた距離テーブルに対して、負の閉路を持つか判定する。
- 引数
  - `d` : 全頂点間最短経路を求めた距離テーブル。
- 計算量
  - $O(n)$
