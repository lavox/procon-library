# ベクトル演算
ベクトル関連の演算。

Vecのインスタンスでベクトル情報を管理し演算を行う使用方法と、long[]で表現したベクトルの演算を行うstaticメソッドを提供する。

### コンストラクタ
```java
public Vec(int N)
public Vec(long[] a)
```
- 引数
  - `N` : 長さ $N$ を指定(各要素は0で初期化)
  - `a` : 長さ $N$ の配列でベクトルの初期値を指定
- 計算量
  - $O(N)$

### 要素の取得
```java
public long get(int i)
```
位置`i`の要素を取得
- 引数
  - `i` : 取得する要素の位置
- 計算量
  - $O(1)$

### 要素の設定
```java
public void set(int i, long c)
```
位置`i`の要素を設定
- 引数
  - `i` : 設定する要素の位置
  - `v` : 設定する値
- 計算量
  - $O(1)$

### ベクトルの和
```java
public Vec add(Vec o)
public static long[] add(long[] a, long[] b)
```
このクラスが表すベクトルと、引数に指定されたベクトルの和を求める。双方のベクトルサイズが一致していること。
- 引数
  - `o` : 足すベクトル
- 計算量
  - $O(N)$

### ベクトルの差
```java
public Vec sub(Vec o)
public static long[] sub(long[] a, long[] b)
```
このクラスが表すベクトルと、引数に指定されたベクトルの差を求める。双方のベクトルサイズが一致していること。
- 引数
  - `o` : 引くベクトル
- 計算量
  - $O(N)$

### ベクトルのスカラー倍
```java
public Vec mul(long c)
public static long[] mul(long[] a, long c)
```
このクラスが表すベクトルと、引数に指定されたスカラーの積を求める。
- 引数
  - `c` : 掛けるスカラー
- 計算量
  - $O(N)$

### 内積
```java
public long dot(Vec o)
public static long dot(long[] a, long[] b)
```
このクラスが表すベクトルと、引数に指定されたベクトルの内積を求める。双方のベクトルサイズが一致していること。
- 引数
  - `o` : 掛けるベクトル
- 計算量
  - $O(N)$

### L1ノルム
```java
public long norm1()
public static long norm1(long[] a)
```
このクラスが表すベクトルのL1ノルムを求める。
- 計算量
  - $O(N)$

### L2ノルム
```java
public double norm2()
public static double norm2(long[] a)
```
このクラスが表すベクトルのL2ノルムを求める。
- 計算量
  - $O(N)$

### L2ノルムの平方
```java
public long norm2Sq()
public static long norm2Sq(long[] a)
```
このクラスが表すベクトルのL2ノルムの平方を求める。
- 計算量
  - $O(N)$

### L1距離(マンハッタン距離)
```java
public long dist1(Vec o)
public static long dist1(long[] a, long[] b)
```
このクラスが表すベクトルと引数に指定したベクトルのL1距離(マンハッタン距離)を求める。双方のベクトルサイズが一致していること。
- 引数
  - `o` : 対象となるベクトル
- 計算量
  - $O(N)$

### L2距離(ユークリッド距離)
```java
public double dist2(Vec o)
public static double dist2(long[] a, long[] b)
```
このクラスが表すベクトルと引数に指定したベクトルのL2距離(ユークリッド距離)を求める。双方のベクトルサイズが一致していること。
- 引数
  - `o` : 対象となるベクトル
- 計算量
  - $O(N)$

### L2距離(ユークリッド距離)の平方
```java
public long dist2Sq(Vec o)
public static long dist2Sq(long[] a, long[] b)
```
このクラスが表すベクトルと引数に指定したベクトルのL2距離(ユークリッド距離)の平方を求める。双方のベクトルサイズが一致していること。
- 引数
  - `o` : 対象となるベクトル
- 計算量
  - $O(N)$
