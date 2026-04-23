# 永続セグメント木
本クラスは、各操作（更新）ごとに状態を保持し続ける永続セグメント木の実装である。
過去の任意のバージョンを保持するインスタンスに対して、以下の操作を $O(\log{n})$ で実行可能。

- 特定の要素を変更した新しいバージョン（インスタンス）の生成
- 任意の区間に対する演算結果の取得

オートボクシングによる性能劣化を避けるため、`int`, `long`専用の以下のクラスも利用可能
- `IntPersistentSegmentTree`
- `LongPersistentSegmentTree`

### コンストラクタ
```java
// 一般クラス用
public PersistentSegmentTree(int n, BinaryOperator<S> op, Supplier<S> e)
public PersistentSegmentTree(S[] arr, BinaryOperator<S> op, Supplier<S> e)
public PersistentSegmentTree(List<S> arr, BinaryOperator<S> op, Supplier<S> e)
public PersistentSegmentTree(int n, IntFunction<S> dataProvider, BinaryOperator<S> op, Supplier<S> e)
// int用
public IntPersistentSegmentTree(int n, IntBinaryOperator op, int e)
public IntPersistentSegmentTree(int[] arr, IntBinaryOperator op, int e)
public IntPersistentSegmentTree(List<Integer> arr, IntBinaryOperator op, int e)
public IntPersistentSegmentTree(int n, IntUnaryOperator dataProvider, IntBinaryOperator op, int e)
// long用
public LongPersistentSegmentTree(int n, LongBinaryOperator op, long e)
public LongPersistentSegmentTree(long[] arr, LongBinaryOperator op, long e)
public LongPersistentSegmentTree(List<Long> arr, LongBinaryOperator op, long e)
public LongPersistentSegmentTree(int n, IntToLongFunction dataProvider, LongBinaryOperator op, long e)
```
初期値を指定しなかった場合は、単位元が初期値となる。
- 引数
  - `n` : 要素数
  - `arr` : 初期値
  - `op` : 二項演算
  - `e` : 単位元(一般クラス用の場合は単項演算で指定、int用・long用は値で指定)
  - `dataProvider` : indexに対して初期値を返す関数
- 計算量
  - $O(n)$

### 値の更新
```java
// 一般クラス用
public PersistentSegmentTree<S> update(int p, S x)
// int用
public IntPersistentSegmentTree update(int p, int x)
// long用
public LongPersistentSegmentTree update(int p, long x)
```
通常のセグメント木の`set()`に相当する。値を更新した後のセグメント木（のエントリーポイントとなるルートノード）を返却する。元のバージョンは変更されない。
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
  - $O(\log{n})$

### 区間演算
```java
// 一般クラス用
public S query(int l, int r)
// int用
public int query(int l, int r)
// long用
public long query(int l, int r)
```
区間 $[l,r)$ に対する演算結果。
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
全区間 $[0,n)$ に対する演算結果。
- 計算量
  - $O(1)$

## 検証
- [ABC453G Copy Query (AtCoder)](https://atcoder.jp/contests/abc453/submissions/75189451) (一般クラス)
- [ABC453G Copy Query (AtCoder)](https://atcoder.jp/contests/abc453/submissions/75189488) (long)
- [Range Kth Smallest (Library Checker)](https://judge.yosupo.jp/submission/368224) (int)
