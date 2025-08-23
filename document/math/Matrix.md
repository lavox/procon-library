# 行列演算
行列関連の演算。一部ベクトルクラス(`Vec`)に依存しているため、使用する場合は合わせてコピーすること。

Matrixのインスタンスで行列情報を管理し演算を行う使用方法と、long[][]で表現した行列の演算を行うstaticメソッドを提供する。

### コンストラクタ
```java
public Matrix(int H, int W)
public Matrix(long[][] a)
```
- 引数
  - `H`, `W` : $H$ 行 $W$ 列のサイズを指定(各要素は0で初期化)
  - `a` : $H$ 行 $W$ 列の2次元配列で行列の初期値を指定
- 計算量
  - $O(HW)$

### 要素の取得
```java
public long get(int i, int j)
```
`i`行`j`列の要素を取得
- 引数
  - `i`, `j` : 取得する要素の位置
- 計算量
  - $O(1)$

### 要素の設定
```java
public void set(int i, int j, long v)
```
`i`行`j`列の要素を設定
- 引数
  - `i`, `j` : 設定する要素の位置
  - `v` : 設定する値
- 計算量
  - $O(1)$

### 行列の和
```java
public Matrix add(Matrix o)
public Matrix addMod(Matrix o, long p)
public static long[][] add(long[][] a, long[][] b)
public static long[][] addMod(long[][] a, long[][] b, long p)
```
このクラスが表す行列と、引数に指定された行列の和を求める。双方の行列サイズが一致していること。`p`を指定した場合は、各要素を`p`で割った余りを求める。
- 引数
  - `o` : 足す行列
  - `p` : 割る値
- 計算量
  - $O(HW)$

### 行列の積
```java
public Matrix mul(Matrix o)
public Matrix mulMod(Matrix o, long p)
public static long[][] mul(long[][] a, long[][] b)
public static long[][] mulMod(long[][] a, long[][] b, long p)
```
このクラスが表す行列と、引数に指定された行列の積を求める。この行列の列数と、`o`の行数が一致していること。`p`を指定した場合は、各要素を`p`で割った余りを求める。
- 引数
  - `o` : 掛ける行列
  - `p` : 割る値
- 計算量
  - $O(H_1 W_1 W_2)$ (この行列のサイズ: $H_1\times W_1$ 、`o`のサイズ: $H_2\times W_2$)

### 行列とスカラーの積
```java
public Matrix mul(long c)
public Matrix mulMod(long c, long p)
public static long[][] mul(long[][] a, long c)
public static long[][] mulMod(long[][] a, long c, long p)
```
このクラスが表す行列と、引数に指定されたスカラーの積を求める。`p`を指定した場合は、各要素を`p`で割った余りを求める。
- 引数
  - `c` : 掛けるスカラー
  - `p` : 割る値
- 計算量
  - $O(HW)$

### 行列とベクトルの積
```java
public Vec mul(Vec v)
public Vec mulMod(Vec v, long p)
public static long[] mul(long[][] a, long[] v)
public static long[] mulMod(long[][] a, long[] v, long p)
```
このクラスが表す行列と、引数に指定されたベクトルの積を求める(ベクトルは列ベクトルと解釈)。この行列の列数と、`v`のサイズが一致していること。`p`を指定した場合は、各要素を`p`で割った余りを求める。
- 引数
  - `v` : 掛けるベクトル
  - `p` : 割る値
- 計算量
  - $O(HW)$

### 行列累乗
```java
public Matrix pow(long n)
public Matrix powMod(long n, long p)
public static long[][] pow(long[][] a, long n)
public static long[][] powMod(long[][] a, long n, long p)
```
このクラスが表す行列を`n`乗した行列を求める。この行列が正方行列であること。`p`を指定した場合は、各要素を`p`で割った余りを求める。
- 引数
  - `n` : べき指数
  - `p` : 割る値
- 計算量
  - $O(H^3 \log n)$ (この行列のサイズ: $H\times H$)

## 検証
- [Matrix Product (Library Checker)](https://judge.yosupo.jp/submission/309310)
- [Pow of Matrix (Library Checker)](https://judge.yosupo.jp/submission/309309)
