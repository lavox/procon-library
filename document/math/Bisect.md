# 二分探索
二分探索を実行する。

### 二分探索
```java
public static int minTrueInt(int low, int high, IntPredicate condition)
public static long minTrueLong(long low, long high, LongPredicate condition)
public static double minTrueDouble(double low, double high, double eps, DoublePredicate condition)
public static int maxTrueInt(int low, int high, IntPredicate condition)
public static long maxTrueLong(long low, long high, LongPredicate condition)
public static double maxTrueDouble(double low, double high, double eps, DoublePredicate condition)
```
二分探索を実施する。
- `minTrue`系：条件を満たす最小の値を求める
  - `condition.test(low)`は`false`、`condition.test(high)`は`true`を満たすものとする
  - `double`用は誤差`eps`での最小値を求める
- `maxTrue`系：条件を満たす最大の値を求める
  - `condition.test(low)`は`true`、`condition.test(high)`は`false`を満たすものとする
  - `double`用は誤差`eps`での最小値を求める

- 引数
  - `low`, `high` : 二分探索を行う範囲
  - `condition` : 満たすべき条件
  - `eps` : `double`の場合は許容誤差
- 計算量
  - $O(d\log d)$ ($d = \mathrm{high} - \mathrm{low}$) (int, longの場合)
  - $O(d\log d)$ ($d = (\mathrm{high} - \mathrm{low}) / \mathrm{eps}$) (doubleの場合)

## 検証
- [ABC292F Regular Triangle Inside a Rectangle (AtCoder)](https://atcoder.jp/contests/abc292/submissions/72848613)
- [Binary Search (Aizu Online Judge)](https://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=11288817#1) (minTrueInt)
- [Binary Search (Aizu Online Judge)](https://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=11288819#1) (maxTrueInt)
