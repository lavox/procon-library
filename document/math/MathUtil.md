# 数論的アルゴリズム

数論的アルゴリズム。ac-libraryの[math](https://github.com/atcoder/ac-library/blob/master/document_ja/math.md)の移植。

ただし、`pow_mod()`, `inv_mod()`相当は、`ModInt.java`にて提供しており、本クラスでは実装されていない。

### 中国剰余定理
```java
public static long[] crt(ArrayList<Long> r, ArrayList<Long> m)
public static long[] crt(long[] r, long[] m)
```
同じ長さの配列 $r,m$ を渡す。この配列の長さを $n$ としたとき、
$$x\equiv r[i] \quad (\mod{m[i]}, \forall i\in {0,1,\dots,n-1})$$
を解く。答えは(存在するならば) $y,z$ $(0\le y\lt z=\mathrm{lcm}{(m[i])})$ を用いて、 $x\equiv y (\mod{z})$ の形で書けることが知られており、この $(y,z)$をペアとして返す。答えがない場合は $\{0,0\}$ を返す。 $n=0$ のときは $\{0,1\}$ を返す。

$\mathrm{lcm}(m[i])$ が`long`に収まる必要がある。
- 引数
  - `r` : 剰余の値の配列
  - `m` : 剰余の法の配列 $(1\le m[i], |r|=|m|)$
- 計算量
  - $O(n\log{\mathrm{lcm}{(m[i])}})$

### Floor Sum
```java
public static long floorSum(long n, long m, long a, long b)
```
以下を計算する。
$$\sum_{i=0}^{n-1}\Bigl\lfloor\frac{a\times i+b}{m}\Bigr\rfloor$$

- 引数
  - `n` : $(0\le n\lt 2^{32})$
  - `m` : $(1\le m\lt 2^{32})$
  - `a, b` : 上式の`a`と`b`。負の値でも可能
- 計算量
  - $O(\log m)$

### 最大公約数
```java
public static long gcd(long a, long b)
```
`a`と`b`の最大公約数を求める。一方が0の場合は他方を返す。
- 引数
  - `a,b` : $(0\le a,b)$
- 計算量
  - $O(\log \min{(a,b)})$
