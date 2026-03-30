# 01-BFS
単一始点の有向グラフについて、各頂点への最短距離を求める。
各辺の長さは0または1とする。

## Bfs01クラス
本クラスは、`ShortestPath`を継承している。
### 探索
```java
public static Dist search(Graph g, int s)
public static Dist search(Graph g, int s, EdgePredicate edge1)
public static Dist search(Graph g, int[] starts, EdgePredicate edge1, EdgePredicate canPass)
```
- 引数
  - `g` : グラフ情報。
  - `s` : 開始地点 $(0 \lt s \le n)$
  - `starts` : 開始地点を複数指定する $(0 \lt s[i] \le n)$
  - `edge1` : 長さ1の辺は`true`、0の辺は`false`を返す関数。省略した場合は、`cost`が正の辺の長さを1、それ以外を0とみなす。
  - `canPass` : 辺が使用可能な場合は`true`、不可の場合`false`を返す関数。未指定の場合は全ての辺が使用可能とみなす。
- 戻り値 
  - `Dist` クラス
- 計算量
  - $O(n + m)$ ($n$ は頂点数、 $m$ は辺数)

## 検証
- [ABC246E Bishop 2 (AtCoder)](https://atcoder.jp/contests/abc246/submissions/74547748)
