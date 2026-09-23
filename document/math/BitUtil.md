# ビット関連アルゴリズム

### ビットチェック
```java
public static boolean check(int bit, int k)
public static boolean check(long bit, int k)
```
`bit`の`k`ビット目が`1`かどうかをチェックする。
- 引数
  - `bit` : チェック対象とする値
  - `k` : 何ビット目をチェックするか。`int`の場合は`mod 32`、`long`の場合は`mod 64`としてチェックする。
- 計算量
  - $O(1)$

### XOR基底
```java
public static int[] xorBase(int[] vec)
public static int[] xorBase(int[] vec, int maxRank)
public static long[] xorBase(long[] vec)
public static long[] xorBase(long[] vec, int maxRank)
```
`vec`内の要素のXORで生成可能な値の基底を求める。
- 引数
  - `vec` : 対象となる配列
  - `maxRank` : 最大次数。基底の個数が`maxRank`を超える場合は、先頭から`maxRank`個までを返す(したがって基底全体とはならない)
- 計算量
  - $O(N\cdot\mathrm{maxRank})$

## 検証
- [たのしい排他的論理和(HARD) (yukicoder)](https://yukicoder.me/submissions/1190324)
