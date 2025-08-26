# 巡回セールスマン問題
全頂点を巡る最短距離を求める。初期地点に戻る閉路の最短を求める問題と、初期地点からスタートして全地点を巡る(初期地点には戻らない)パスの最短を求める問題に対応。

それ以外の条件の場合は、戻り値に含まれるdp表を使用して求めるか、ライブラリを改造して対応する必要がある。

## Tspクラス
### 最短経路の導出
```java
public static Tsp.MinRoute search(long[][] distMap)
public static Tsp.MinRoute search(long[][] distMap, int s, boolean oneway)
public static class MinRoute {
  int g;
  long dist;
  int[] path;
  long[][] dp;
}
```
全頂点を1回ずつ巡る最短経路を求める。
- 引数
  - `distMap` : 頂点間の距離。頂点数を $n$ として $n\times n$ の配列で与える。`distMap[i][j]` は頂点`i`と頂点`j`の間の距離
  - `s` : 開始地点 $(0 \lt s \le n)$
  - `oneway` : 片道の場合`true`。閉路の場合`false`
- 戻り値 `MinRoute` クラスのメンバ変数
  - `g` : 最短ルート(の1つ)の最終頂点番号 (閉路の場合は開始地点以外の最後の頂点)
  - `dist` : 最短距離
  - `path` : 最短ルート(の1つ)が通る頂点順を表す長さ $n$ の配列。
  - `dp` : DP表。サイズは $n\times 2^n$
- 計算量
  - $O(2^n n^2)$

## 検証
- [典型アルゴリズム問題集 C 巡回セールスマン問題 (AtCoder)](https://atcoder.jp/contests/typical-algorithm/submissions/68828862)
