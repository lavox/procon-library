# 2次元ベクトル演算
x, y座標が整数(long)の2次元ベクトルについて、各種演算を行う。

### コンストラクタ
```java
public Vec2L(long x, long y)
```
- 引数
  - `x` : x座標
  - `y` : y座標
- 計算量
  - $O(1)$

### インスタンスのクローン
```java
public Vec2L clone()
```
- 計算量
  - $O(1)$

### ベクトルの和・差
```java
public Vec2L add(Vec2L o)
public Vec2L addAsn(Vec2L o)
public Vec2L sub(Vec2L o)
public Vec2L subAsn(Vec2L o)
```
このインスタンスが表すベクトルと、引数に指定されたベクトルの和・差を求める。`Asn`がつくメソッドは、このインスタンス自体を書き換える。
- 引数
  - `o` : 和・差の対象となるベクトル
- 計算量
  - $O(1)$

### ベクトルのスカラー倍・スカラー商
```java
public Vec2L mul(long a)
public Vec2L mulAsn(long a)
public Vec2L div(long a)
public Vec2L divAsn(long a)
```
このインスタンスが表すベクトルと、引数に指定されたスカラーの積・商を求める。`Asn`がつくメソッドは、このインスタンス自体を書き換える。
- 引数
  - `a` : スカラー
- 計算量
  - $O(1)$

### 内積
```java
public long dot(Vec2L o)
```
このインスタンスが表すベクトルと、引数に指定されたベクトルの内積を求める。
- 引数
  - `o` : 掛けるベクトル
- 計算量
  - $O(1)$

### クロス積
```java
public long cross(Vec2L o)
```
このインスタンスが表すベクトルと、引数に指定されたベクトルのクロス積(平行四辺形の面積)を求める。
- 引数
  - `o` : 掛けるベクトル
- 計算量
  - $O(1)$

### ノルム(長さ)
```java
public double norm()
```
ベクトルのノルム(長さ)を求める。
- 計算量
  - $O(1)$

### ノルム(長さ)の平方
```java
public long normSq()
```
ベクトルのノルム(長さ)の平方を求める。
- 計算量
  - $O(1)$

### 90度/180度/270度回転
```java
public Vec2L rot(int q)
public Vec2L rotAsn(int q)
public Vec2L rot90()
public Vec2L rot90Asn()
public Vec2L rot180()
public Vec2L rot180Asn()
public Vec2L rot270()
public Vec2L rot270Asn()
```
原点を中心に90度/180度/270度回転したベクトルを求める。`Asn`がつくメソッドは、このインスタンス自体を書き換える。
- 引数
  - `q` : q=0,1,2,3でそれぞれ0度/90度/180度/270度回転を表す
- 計算量
  - $O(1)$

### 距離(ユークリッド距離)
```java
public double dist(Vec2L o)
public static double dist(Vec2L a, Vec2L b)
```
ベクトル間の距離を求める
- 引数
  - `o` : 対象となるベクトル
- 計算量
  - $O(1)$

### 距離(ユークリッド距離)の平方
```java
public long distSq(Vec2L o)
public static long distSq(Vec2L a, Vec2L b)
```
ベクトル間の距離(ユークリッド距離)の平方を求める。
- 引数
  - `o` : 対象となるベクトル
- 計算量
  - $O(1)$

### 一致判定
```java
public boolean equals(Object o)
```
ベクトルとして一致するか判定する。
- 引数
  - `o` : 対象となるベクトル
- 計算量
  - $O(1)$

### 偏角ソート用の比較関数
```java
public static final Comparator<Vec2L> ARG_CMP
```
偏角ソート用の比較関数。偏角は $(-\pi, \pi]$ の範囲としてソートする。原点は便宜上0(rad)と扱う。
なお、`equals()` と互換性がないので注意。

## 検証
- [Sort Points by Argument (Library Checker)](https://judge.yosupo.jp/submission/347671)
