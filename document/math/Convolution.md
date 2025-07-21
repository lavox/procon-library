# 畳み込み
畳み込みを求める。長さ$N$の数列$a$と、長さ$M$の数列$b$から、長さ$N+M-1$の数列
$$c_i=\sum_{j=0}^i{a_jb_{i-j}}$$
を求める。
ac-libraryの[convolution](https://github.com/atcoder/ac-library/blob/master/document_ja/convolution.md)の移植。

### 畳み込み(剰余)
```java
public static long[] convolution(long[] a, long[] b, long mod)
```
`mod`を法とする畳み込みを求める。`a`と`b`の少なくとも一方が空配列の場合は空配列を返す。
- $2\le\mathrm{mod}\le2\times10^9$
- $\mathrm{mod}$は素数
- $2^c|(\mathrm{mod}-1)$かつ$|a|+|b|-1\le2^c$となる$c$が存在する。(例:998244353の場合は$c=24$までOK)

- 引数
  - `a`, `b` : 畳み込みを行う配列
  - `mod` : 剰余の法
- 計算量
  - $O(n\log n+\log\mathrm{mod})$

### 畳み込み
```java
public static long[] convolution_ll(long[] a, long[] b)
```
畳み込みを求める。`a`と`b`の少なくとも一方が空配列の場合は空配列を返す。
- $|a|+|b|-1\le2^{24}$
- 計算後の配列の要素がすべて`long`に収まる

- 引数
  - `a`, `b` : 畳み込みを行う配列
- 計算量
  - $O(n \log n)$
