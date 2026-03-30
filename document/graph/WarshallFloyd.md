# Warshall-Floyd法
全頂点間の最短経路を求める。
- 有向グラフを扱うことが可能
- 負の辺を扱うことが可能
- 負の閉路の検出が可能

探索後、`DistMap`クラスの`hasNegativeLoop()`で負の閉路の検出が可能。負の閉路がある場合は最短距離は正しく計算できないので注意。

## WarshallFloydクラス
本クラスは、`ShortestPath`を継承している。
### 初期の距離テーブル生成
```java
public static DistMap createMap(int n)
```
初期の距離テーブルを生成する。同じ頂点同士は`0`、それ以外は`INF`で初期化される。
- 引数
  - `n` : 頂点数
- 戻り値 
  - `DistMap` クラス
- 計算量
  - $O(n^2)$

### 探索
```java
public static DistMap search(DistMap d)
```
全頂点間の最短経路を求める。
- 引数
  - `d` : 距離テーブル。予め張られている辺の距離を入れておく。
- 戻り値 
  - `DistMap` クラス。引数を書き換えた結果を返す。
- 計算量
  - $O(n^3)$

### 辺追加による距離更新
```java
public static DistMap updateEdge(DistMap d, int from, int to, long dist)
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

## 検証
- [All Pairs Shortest Path (Aizu Online Judge)](https://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=11389045#2)
- [ABC416E Development (AtCoder)](https://atcoder.jp/contests/abc416/submissions/74548282)
