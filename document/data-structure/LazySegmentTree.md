# 遅延評価セグメント木
区間に対する要素の作用と、区間の要素に対する演算結果を高速に実施できるデータ構造。ac-libraryの[Lazy Segtree](https://github.com/atcoder/ac-library/blob/master/document_ja/lazysegtree.md)の移植。

オートボクシングによる性能劣化を避けるため、`int`, `long`専用の以下のクラスも利用可能
- `IntLazySegmentTree`
- `LongLazySegmentTree`

## 遅延評価セグメント木
### コンストラクタ
```java
// 一般クラス用
public LazySegmentTree(int n, LazyOperator<S, F> op)
public LazySegmentTree(S[] arr, LazyOperator<S, F> op)
public LazySegmentTree(Collection<S> arr, LazyOperator<S, F> op)
// int用
public IntLazySegmentTree(int n, IntLazyOperator op)
public IntLazySegmentTree(int[] arr, IntLazyOperator op)
public IntLazySegmentTree(Collection<Integer> arr, IntLazyOperator op)
// long用
public LongLazySegmentTree(int n, LongLazyOperator op)
public LongLazySegmentTree(long[] arr, LongLazyOperator op)
public LongLazySegmentTree(Collection<Long> arr, LongLazyOperator op)
```
モノイド上の二項演算・単位元・作用・作用の合成・恒等作用を定義するためのインターフェースである`LazyOperator`,`IntLazyOperator`,`LongLazyOperator`については後述。

初期値を指定しなかった場合は、単位元が初期値となる。
- 引数
  - `n` : 要素数
  - `arr` : 初期値
  - `op` : モノイド上の二項演算・単位元・作用・作用の合成・恒等作用の定義
- 計算量
  - $O(n)$

### 値の初期化
```java
// 一般クラス用
public void initData(S[] arr)
public void initData(Collection<S> arr)
// int用
public void initData(int[] arr)
public void initData(Collection<Integer> arr)
// long用
public void initData(long[] arr)
public void initData(Collection<Long> arr)
```
値を初期化する。
- 引数
  - `arr` : 初期化する値
- 計算量
  - $O(n)$

### 値の代入
```java
// 一般クラス用
public void set(int p, S x)
// int用
public void set(int p, int x)
// long用
public void set(int p, long x)
```
- 引数
  - `p` : 代入する位置 $(0 \le p \lt n)$
  - `x` : 代入する値
- 計算量
  - $O(\log{n})$

### 値の取得
```java
// 一般クラス用
public S get(int p)
// int用
public int get(int p)
// long用
public long get(int p)
```
- 引数
  - `p` : 取得する位置 $(0 \le p \lt n)$
- 計算量
  - $O(1)$

### 区間演算
```java
// 一般クラス用
public S query(int l, int r)
// int用
public int query(int l, int r)
// long用
public long query(int l, int r)
```
区間 $[l,r)$ に対する演算結果。(※本家とメソッド名が異なるので注意)
- 引数
  - `l`, `r` : 区間 $(0 \le l \le r \le N)$
- 計算量
  - $O(\log{n})$

### 全区間演算
```java
// 一般クラス用
public S allQuery()
// int用
public int allQuery()
// long用
public long allQuery()
```
全区間 $[0,n)$ に対する演算結果。(※本家とメソッド名が異なるので注意)
- 計算量
  - $O(1)$

### 作用
```java
// 一般クラス用
public void apply(int p, F f)
public void apply(int l, int r, F f)
// int用
public void apply(int p, int f)
public void apply(int l, int r, int f)
// long用
public void apply(int p, long f)
public void apply(int l, int r, long f)
```
1つの要素または区間の要素に対する作用。
- 引数
  - `p` : 区間 $(0 \le p \lt N)$
  - `l`, `r` : 区間 $(0 \le l \le r \le N)$
  - `f` : 作用
- 計算量
  - $O(\log{n})$

### 二分探索(右側)
```java
// 一般クラス用
public int maxRight(int l, Predicate<S> g)
// int用
public int maxRight(int l, IntPredicate g)
// long用
public int maxRight(int l, LongPredicate g)
```
遅延評価セグメント木上で二分探索を行い、以下の条件を満たす`r`を返す。
- `r = l` もしくは `g(op(a[l], a[l + 1], ..., a[r - 1])) = true`
- `r = n` もしくは `g(op(a[l], a[l + 1], ..., a[r])) = false`
`g`が単調であれば、`g(op(a[l], a[l + 1], ..., a[r - 1])) = true`となる最大の`r`。

- 引数
  - `l` : 起点 $(0 \le l \le N)$
  - `g` : `boolean`を返す関数。`g(e()) = true`を満たす必要がある
- 計算量
  - $O(\log{n})$

### 二分探索(左側)
```java
// 一般クラス用
public int minLeft(int r, Predicate<S> g)
// int用
public int minLeft(int r, IntPredicate g)
// long用
public int minLeft(int r, LongPredicate g)
```
遅延評価セグメント木上で二分探索を行い、以下の条件を満たす`l`を返す。
- `l = r` もしくは `g(op(a[l], a[l + 1], ..., a[r - 1])) = true`
- `l = 0` もしくは `g(op(a[l - 1], a[l], ..., a[r - 1])) = false`
`g`が単調であれば、`g(op(a[l], a[l + 1], ..., a[r - 1])) = true`となる最小の`l`。

- 引数
  - `r` : 起点 $(0 \le r \le N)$
  - `g` : `boolean`を返す関数。`g(e()) = true`を満たす必要がある
- 計算量
  - $O(\log{n})$

## モノイド上の二項演算・単位元・作用・作用の合成・恒等作用定義用のインターフェース
二項演算・作用等を本インターフェースを実装して作成し、遅延評価セグメント木のインスタンス生成時に指定する。

なお、`int`用、`long`用の区間加算区間最小等用に事前実装したクラスについては後述。
### インターフェース
```java
// 一般クラス用
interface LazyOperator<S, F> {
	public S op(S a, S b);
	public S e();
	public S mapping(F x, S a, int len);
	public F composition(F x, F y);
	public F identity();
}
// int用
interface IntLazyOperator {
	public int op(int a, int b);
	public int e();
	public int mapping(int x, int a, int len);
	public int composition(int x, int y);
	public int identity();
}
// long用
interface LongLazyOperator {
	public long op(long a, long b);
	public long e();
	public long mapping(long x, long a, int len);
	public long composition(long x, long y);
	public long identity();
}
```
- `op`: 二項演算
- `e`: 二項演算の単位元
- `mapping`: 作用。パラメータ`len`は区間の要素数
- `composition`: 作用の合成
- `identity`: 恒等作用

## int用・long用の事前実装クラス
```java
// 演算：区間和、作用：区間更新
class IntLazySumUpdate implements IntLazyOperator
class LongLazySumUpdate implements LongLazyOperator
// 演算：区間和、作用：区間加算
class IntLazySumAdd implements IntLazyOperator
class LongLazySumAdd implements LongLazyOperator
// 演算：区間最小、作用：区間更新
class IntLazyMinUpdate implements IntLazyOperator
class LongLazyMinUpdate implements LongLazyOperator
// 演算：区間最小、作用：区間加算
class IntLazyMinAdd implements IntLazyOperator
class LongLazyMinAdd implements LongLazyOperator
// 演算：区間最大、作用：区間更新
class IntLazyMaxUpdate implements IntLazyOperator
class LongLazyMaxUpdate implements LongLazyOperator
// 演算：区間最大、作用：区間加算
class IntLazyMaxAdd implements IntLazyOperator
class LongLazyMaxAdd implements LongLazyOperator
```
