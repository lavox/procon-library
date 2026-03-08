# Bellman-Ford法
単一始点の有向グラフについて、各頂点への最短距離を求める。
- 負の辺を扱うことが可能
- 負の閉路の検出が可能

## BellmanFordクラス
### 最短距離
```java
public static long[] bellmanFord(GenericGraph<? extends CostEdge> g, int s)
```
最短距離を求める。到達不能な頂点は`BellmanFord.INF`、負の閉路から到達可能な頂点(いくらでも距離を短くできる頂点)は`BellmanFord.NINF`となる。
- 引数
  - `g` : グラフを表すクラス
  - `s` : 始点
- 計算量
  - $O(|V||E|)$

### 負の閉路のチェック
```java
public static boolean hasNegativeLoop(long[] dist)
```
求めた最短距離のリストから、指定した始点から到達可能な範囲に負の閉路が存在するかチェックする。
- 引数
  - `dist` : `bellmanFord(g, s)`の結果
- 計算量
  - $O(|V|)$

## 検証
- [Single Source Shortest Path (Negative Edges) (Aizu Online Judge)](https://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=11362525#2)
