# 深さ優先探索(DFS)
深さ優先探索を実行するヘルパークラス。非再帰でDFSを実行することが可能。

- 依存ライブラリ
  - Graph.java
  - Edge.java

### コンストラクタ
```java
public Dfs(int size)
```
- 引数
  - `size` : グラフの頂点数
- 計算量
  - $O(\mathrm{size})$

### DFS順の取得
```java
// 行きがけ＋帰りがけ
public Iterable<DfsStep> dfsBothOrder(Graph g, int v0)
// 行きがけのみ
public Iterable<DfsStep> dfsPreOrder(Graph g, int v0)
// 帰りがけのみ
public Iterable<DfsStep> dfsPostOrder(Graph g, int v0)
```
DFSの頂点の実行順序を`Iterable<DfsStep>`形式で取得する。これを使用して`for`文でDFSを実行することが可能。
- 引数
  - `g` : 頂点数`size`のグラフ
  - `v0` : 開始頂点番号 $(0 \le v0 \lt n)$
- 計算量
  - $O(1)$

### `DfsStep`クラス
```java
public class DfsStep {
  public final int cur;
  public final int parent;
  public final int edgeIndex;
  public final boolean isPre;
  public final int depth;
}
```
- `cur` : 現在頂点番号
- `parent` : 遷移元の頂点番号。初期頂点の場合は-1。
- `edgeIndex` : 遷移元の頂点からたどった辺のid
- `isPre` : 行きがけの場合は`true`、帰りがけの場合は`false`
- `depth` : 初期頂点からのDFS遷移における深さ
