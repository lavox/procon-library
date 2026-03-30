# Dijkstra法
単一始点の有向グラフについて、各頂点への最短距離を求める。
- 負の辺を扱うことは不可

## Dijkstraクラス
本クラスは、`ShortestPath`を継承している。
### 探索
```java
public static Dist search(Graph g, int s)
public static Dist search(Graph g, int[] starts, EdgePredicate canPass)
```
- 引数
  - `g` : グラフ情報。辺の `cost` を距離とみなす
  - `s` : 開始地点 $(0 \lt s \le n)$
  - `starts` : 開始地点を複数指定する $(0 \lt s[i] \le n)$
  - `canPass` : 辺が使用可能な場合は`true`、不可の場合`false`を返す関数。未指定の場合は全ての辺が使用可能とみなす。
- 戻り値 
  - `Dist` クラス (ShortestPathクラス内で定義)
- 計算量
  - $O((n + m)\log{n})$ ($n$ は頂点数、 $m$ は辺数)

## 検証
- [典型アルゴリズム問題集 D 単一始点最短経路問題 (AtCoder)](https://atcoder.jp/contests/typical-algorithm/submissions/74546610)
- [Shortest Path (Library Checker)](https://judge.yosupo.jp/submission/363265)
