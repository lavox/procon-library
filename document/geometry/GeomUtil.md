# 幾何関連ユーティリティ
以下の幾何関連機能を提供。

- 凸包(Convex Hull)
- 誤差(eps)付き一致判定

### 凸包
```java
public static ArrayList<Vec2L> convexHull(ArrayList<Vec2L> points)
```
指定した点群を囲む凸包を反時計回りに取得
- 引数
  - `points` : 対象となる点群
- 計算量
  - $O(n \log{n})$ ($n$ は点の数)

### 誤差付き一致判定
```java
public static boolean eq(double a, double b)
public static boolean isZero(double a)
```
誤差を`1e-9`として、互いに一致するか、または0と一致するかを判定する
- 引数
  - `a`, `b` : 判定対象とする値
- 計算量
  - $O(1)$

## 検証
- [Static Convex Hull (Library Checker)](https://judge.yosupo.jp/submission/352089)
