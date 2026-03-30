# 最短経路探索アルゴリズム
グラフに対する各種最短経路探索アルゴリズムを提供するクラスの親クラス。
共通の戻り値用クラスを提供する。

なお、子クラスでは以下のアルゴリズムを提供する。

| アルゴリズム         | 探索対象       | 負の辺 | 計算量              |
|:---------------------|:---------------|:-------|:--------------------|
| Dijkstra法           | 単一・複数始点 | 非対応 | $O((n + m)\log{n})$ |
| BFS                  | 単一・複数始点 | 非対応 | $O(n + m)$          |
| 01-BFS               | 単一・複数始点 | 非対応 | $O(n + m)$          |
| Bellman-Ford法       | 単一・複数始点 | 対応   | $O(nm)$             |
| Warshall-Floyd法     | 全頂点間       | 対応   | $O(n^2)$            |
| 巡回セールスマン問題 | 巡回経路       | 非対応 | $O(2^n n^2)$        |

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
