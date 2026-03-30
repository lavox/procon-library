# 巡回セールスマン問題(TSP)
全頂点を巡る最短距離を求める。初期地点に戻る閉路の最短を求める問題と、初期地点からスタートして全地点を巡る(初期地点には戻らない)パスの最短を求める問題に対応。

それ以外の条件の場合は、戻り値に含まれるdp表を使用して求めるか、ライブラリを改造して対応する必要がある。

## Tspクラス
本クラスは、`ShortestPath`を継承している。
### 探索
```java
public static TspRoute search(long[][] distMap)
public static TspRoute search(long[][] distMap, int s, boolean oneway)
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

## `TspRoute`クラス (戻り値用クラス)
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
- [典型アルゴリズム問題集 C 巡回セールスマン問題 (AtCoder)](https://atcoder.jp/contests/typical-algorithm/submissions/74548441)
