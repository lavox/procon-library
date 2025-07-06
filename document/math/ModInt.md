# 剰余演算関連

剰余に関する各種演算を実行する。
- 加減乗除(逆元)
- 累乗
- 階乗($a!$)、階乗の逆元($1/a!$)、逆元($1/a$)、順列、組み合わせ(事前計算による高速演算)

## ModOperationクラス
ModInt生成用のクラス。剰余演算を行う際には最初に本クラスのインスタンスを生成し、以降は`create(long)`メソッドを用いて`ModInt`インスタンスを生成する。

なお、逆元や除法に関するメソッドは、法`m`が素数であることを前提とする。

### 定数
```java
public static final int MOD998 = 998244353;
public static final int MOD107 = 1000000007;
```

### コンストラクタ
```java
public ModOperation(int m)
public ModOperation(int m, boolean fraction)
```
- 引数
  - `m` : 剰余の法
  - `fraction` : `true`を指定した場合、`create(long)`メソッドで`ModInt`ではなく`ModFraction`インスタンスを生成し、割り算や逆元を実数として実行した場合の値を内部に保持(主にデバッグ用途)
- 計算量
  - $O(1)$

### 階乗、階乗の逆元、逆元、順列、組み合わせのための事前計算
```java
public void prepareFactorial(int max)
```
階乗、階乗の逆元、逆元、順列、組み合わせの剰余を高速に計算するための事前計算。
- 引数
  - `max` : 事前計算対象の最大値
- 計算量
  - $O(\mathrm{max})$

### `ModInt`の生成
```java
public ModInt create(long v)
public ModInt one()
public ModInt zero()
```
引数に指定した値`v`に対応する`ModInt`インスタンスを生成。`fraction = true`の場合は`ModFraction`インスタンスを生成。

`one()`メソッドは`v = 1`、`zero()`メソッドは`v = 0`としてインスタンスを生成。
- 引数
  - `v` : 生成するインスタンスの値
- 計算量
  - $O(1)$

### 逆元の生成
```java
public ModInt createInv(long v)
```
`v`の逆元を表す`ModInt`インスタンスを生成。`fraction = true`の場合は`ModFraction`インスタンスを生成。
- 引数
  - `v` : 指定した`v`の逆元にあたるインスタンスを生成
- 計算量
  - $O(\log\mathrm{m})$

### 四則演算
```java
public int add(int a, int b)
public int sub(int a, int b)
public int mul(int a, int b)
public int div(int a, int b)
```
四則演算の結果を求める。`ModInt`クラスにて使用することを想定したメソッドだが、直接使用することも可能。
- 引数
  - `a`, `b` : 演算対象 $(0 \le a, b \lt \mathrm{m})$
- 計算量
  - `add`, `sub`, `mul`: $O(1)$
  - `div`: $O(\log\mathrm{m})$ (`prepareFactorial(int)`により、$a \le \mathrm{max}$が事前計算されている場合は$O(1))

### 累乗
```java
public int pow(int a, long n)
```
$a^n$の結果を求める。`ModInt`クラスにて使用することを想定したメソッドだが、直接使用することも可能。
- 引数
  - `a`, `n` : 演算対象 $(0 \le a \lt \mathrm{m})$
- 計算量
  - $O(\log\mathrm{m})$

### 逆元
```java
public int inv(int a)
```
`a`の逆元を求める。`ModInt`クラスにて使用することを想定したメソッドだが、直接使用することも可能。
- 引数
  - `a` : 演算対象 $(0 \lt a \lt \mathrm{m})$
- 計算量
  - $O(\log\mathrm{m})$ (`prepareFactorial(int)`により、$a \le \mathrm{max}$が事前計算されている場合は$O(1))

### 剰余
```java
public int mod(long a)
```
`a mod m`の結果を求める。
- 引数
  - `a` : 演算対象
- 計算量
  - $O(1)$

### 階乗
```java
public long fact(int a)
```
`a`の階乗を`m`で割った余りを求める。`prepareFactorial(int)`により、$a \le \mathrm{max}$が事前計算されている必要がある。
- 引数
  - `a` : 階乗する値$(0 \le a \le \mathrm{max})$
- 計算量
  - $O(1)$

### 階乗の逆元
```java
public long factInv(int a)
```
`a`の階乗の逆元を求める。`prepareFactorial(int)`により、$a \le \mathrm{max}$が事前計算されている必要がある。
- 引数
  - `a` : 階乗の逆元を求める値$(0 \le a \le \mathrm{max})$
- 計算量
  - $O(1)$

### 組み合わせ(二項係数)
```java
public long combi(int n, int r)
```
二項係数${}_n C_r$を`m`で割った余りを求める。`prepareFactorial(int)`により、$n \le \mathrm{max}$が事前計算されている必要がある。
- 引数
  - `n`, `r` : 二項係数${}_n C_r$を求める値$(0 \le r \le n \le \mathrm{max})$
- 計算量
  - $O(1)$

### 順列
```java
public long perm(int n, int r)
```
順列${}_n P_r$を`m`で割った余りを求める。`prepareFactorial(int)`により、$n \le \mathrm{max}$が事前計算されている必要がある。
- 引数
  - `n`, `r` : 順列${}_n P_r$を求める値$(0 \le r \le n \le \mathrm{max})$
- 計算量
  - $O(1)$

## ModIntクラス
剰余を表すクラス。`ModOperation`クラスの`create(long)`メソッドで生成する。

演算に関するメソッドについて、末尾に`Asn`がつくものはインスタンス自身の値を変更し(mutable)、末尾に`Asn`がつかないものはインスタンス自身の値は変更されない(immutable)。

### 法の取得
```java
public int mod()
```
法`m`を取得する。
- 計算量
  - $O(1)$

### 値の取得
```java
public int val()
```
インスタンスが表す値を取得する。
- 計算量
  - $O(1)$

### 加算
```java
public ModInt add(ModInt o)
public ModInt add(ModInt o1, ModInt o2)
public ModInt add(ModInt o1, ModInt o2, ModInt o3)
public ModInt add(ModInt o1, ModInt... o)
public ModInt add(long a)
public ModInt add(long a1, long a2)
public ModInt add(long a1, long a2, long a3)
public ModInt add(long a1, long... a)
```
加算を実行する。本インスタンスが表す値に、引数で指定した値を足す。本インスタンスが表す値は変化せず、計算結果を表す新しいインスタンスを返す。
- 引数
  - `o`,`o1`,`o2`,`o3`,`a`,`a1`,`a2`,`a3` : 演算対象
- 計算量
  - $O(1)$ (加算対象を可変個指定した場合は、その長さ)

### 減算
```java
public ModInt sub(ModInt o)
public ModInt sub(long a)
```
減算を実行する。本インスタンスが表す値から、引数で指定した値を引く。本インスタンスが表す値は変化せず、計算結果を表す新しいインスタンスを返す。
- 引数
  - `o`,`a` : 演算対象
- 計算量
  - $O(1)$

### 乗算
```java
public ModInt mul(ModInt o)
public ModInt mul(ModInt o1, ModInt o2)
public ModInt mul(ModInt o1, ModInt o2, ModInt o3)
public ModInt mul(ModInt o1, ModInt... o)
public ModInt mul(long a)
public ModInt mul(long a1, long a2)
public ModInt mul(long a1, long a2, long a3)
public ModInt mul(long a1, long... a)
```
乗算を実行する。本インスタンスが表す値に、引数で指定した値を掛ける。本インスタンスが表す値は変化せず、計算結果を表す新しいインスタンスを返す。
- 引数
  - `o`,`o1`,`o2`,`o3`,`a`,`a1`,`a2`,`a3` : 演算対象
- 計算量
  - $O(1)$ (乗算対象を可変個指定した場合は、その長さ)

### 除算
```java
public ModInt div(ModInt o)
public ModInt div(long a)
```
除算を実行する。本インスタンスが表す値を、引数で指定した値で割る。本インスタンスが表す値は変化せず、計算結果を表す新しいインスタンスを返す。
- 引数
  - `o`,`a` : 演算対象
- 計算量
  - $O(\log\mathrm{m})$

### 逆元
```java
public ModInt inv()
```
逆元を求める。本インスタンスが表す値は変化せず、計算結果を表す新しいインスタンスを返す。
- 計算量
  - $O(\log\mathrm{m})$

### mutable演算
```java
public ModInt addAsn(ModInt o)
public ModInt addAsn(long a)
public ModInt subAsn(ModInt o)
public ModInt subAsn(long a)
public ModInt mulAsn(ModInt o)
public ModInt mulAsn(long a)
public ModInt divAsn(ModInt o)
public ModInt divAsn(long a)
```
四則演算の結果を求める。本インスタンスが表す値自身を上書きし、上書きされた本インスタンス自身が返却される。
引数や計算量は対応するimmutableメソッドを参照。

## ModFractionクラス
`ModInt`のサブクラスで、割り算を実数として実行した場合の`double`型の結果を内部で保持する。主にデバッグ用途。

`ModOperation`クラスを`fraction = true`で生成し、その`create(long)`メソッドで生成する。

本クラスのメソッドは`ModInt`と同等であるが、本クラスを使用する場合は引数の`ModInt`クラスは`ModFraction`である必要があり、戻り値も`ModFraction`クラスとなる。各メソッドの説明は省略。
