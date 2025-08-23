# セグメント木
1つの要素の変更と、区間の要素に対する演算結果を高速に実施できるデータ構造。ac-libraryの[Segtree](https://github.com/atcoder/ac-library/blob/master/document_ja/segtree.md)の移植。

オートボクシングによる性能劣化を避けるため、`int`, `long`専用の以下のクラスも利用可能
- `IntSegmentTree`
- `LongSegmentTree`

### コンストラクタ
```java
// 一般クラス用
public SegmentTree<S>(int n, BinaryOperator<S> op, Supplier<S> e)
public SegmentTree<S>(S[] arr, BinaryOperator<S> op, Supplier<S> e)
public SegmentTree<S>(Collection<S> arr, BinaryOperator<S> op, Supplier<S> e)
// int用
public IntSegmentTree(int n, IntBinaryOperator op, int e)
public IntSegmentTree(int[] arr, IntBinaryOperator op, int e)
public IntSegmentTree(Collection<Integer> arr, IntBinaryOperator op, int e)
// long用
public LongSegmentTree(int n, LongBinaryOperator op, long e)
public LongSegmentTree(long[] arr, LongBinaryOperator op, long e)
public LongSegmentTree(Collection<Long> arr, LongBinaryOperator op, long e)
```
初期値を指定しなかった場合は、単位元が初期値となる。
- 引数
  - `n` : 要素数
  - `arr` : 初期値
  - `op` : 二項演算
  - `e` : 単位元(一般クラス用の場合は単項演算で指定、int用・long用は値で指定)
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

### 二分探索(右側)
```java
// 一般クラス用
public int maxRight(int l, Predicate<S> f)
// int用
public int maxRight(int l, IntPredicate f)
// long用
public int maxRight(int l, LongPredicate f)
```
セグメント木上で二分探索を行い、以下の条件を満たす`r`を返す。
- `r = l` もしくは `f(op(a[l], a[l + 1], ..., a[r - 1])) = true`
- `r = n` もしくは `f(op(a[l], a[l + 1], ..., a[r])) = false`
`f`が単調であれば、`f(op(a[l], a[l + 1], ..., a[r - 1])) = true`となる最大の`r`。

- 引数
  - `l` : 起点 $(0 \le l \le N)$
  - `f` : `boolean`を返す関数。`f(e()) = true`を満たす必要がある
- 計算量
  - $O(\log{n})$

### 二分探索(左側)
```java
// 一般クラス用
public int minLeft(int r, Predicate<S> f)
// int用
public int minLeft(int r, IntPredicate f)
// long用
public int minLeft(int r, LongPredicate f)
```
セグメント木上で二分探索を行い、以下の条件を満たす`l`を返す。
- `l = r` もしくは `f(op(a[l], a[l + 1], ..., a[r - 1])) = true`
- `l = 0` もしくは `f(op(a[l - 1], a[l], ..., a[r - 1])) = false`
`f`が単調であれば、`f(op(a[l], a[l + 1], ..., a[r - 1])) = true`となる最小の`l`。

- 引数
  - `r` : 起点 $(0 \le r \le N)$
  - `f` : `boolean`を返す関数。`f(e()) = true`を満たす必要がある
- 計算量
  - $O(\log{n})$

## 検証
- [Static Range Sum (Library Checker)](https://judge.yosupo.jp/submission/307929) (SegmentTree)
- [Static Range Sum (Library Checker)](https://judge.yosupo.jp/submission/307930) (LongSegmentTree)
- [Static RMQ (Library Checker)](https://judge.yosupo.jp/submission/307931) (SegmentTree)
- [Static RMQ (Library Checker)](https://judge.yosupo.jp/submission/307932) (IntSegmentTree)
- [AtCoder Library Practice Contest-J Segment Tree (AtCoder)](https://atcoder.jp/contests/practice2/submissions/67373691) (SegmentTree)
- [AtCoder Library Practice Contest-J Segment Tree (AtCoder)](https://atcoder.jp/contests/practice2/submissions/67373724) (LongSegmentTree)
- [AtCoder Library Practice Contest-J Segment Tree (AtCoder)](https://atcoder.jp/contests/practice2/submissions/67373643) (IntSegmentTree)
