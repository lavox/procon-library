# 動的セグメント木
1つの要素の変更と、区間の要素に対する演算結果を高速に実施できるデータ構造。事前に配列を作成せずに動的にノードを生成するため、対象となる添え字の範囲が広くても、管理対象要素数が少なければ使用可能。

### コンストラクタ
```java
public DynamicSegmentTree(long L, long R, BinaryOperator<S> op, Supplier<S> e)
```
単位元が初期値となる。
- 引数
  - `L`, `R` : 添え字の範囲($[L,R)$ が対象範囲となる)
  - `op` : 二項演算
  - `e` : 単位元
- 計算量
  - $O(1)$

### 値の代入
```java
public void set(long p, S x)
```
- 引数
  - `p` : 代入する位置 $(L \le p \lt R)$
  - `x` : 代入する値
- 計算量
  - $O(\log{(R - L)})$

### 値の取得
```java
public S get(long p)
```
- 引数
  - `p` : 取得する位置 $(L \le p \lt R)$
- 計算量
  - $O(\log{(R - L)})$

### 区間演算
```java
public S query(long l, long r)
```
区間 $[l,r)$ に対する演算結果。
- 引数
  - `l`, `r` : 区間 $(L \le l \le r \le R)$
- 計算量
  - $O(\log{(R - L)})$

## 検証
- [範囲の合計 (yukicoder)](https://yukicoder.me/submissions/1104417)
