# Fenwick木
1つの要素の加算と、区間の要素に対する総和を高速に求めることができるデータ構造。ac-libraryの[fenwicktree](https://github.com/atcoder/ac-library/blob/master/document_ja/fenwicktree.md)の移植。

### コンストラクタ
```java
public FenwickTree(int n)
public FenwickTree(long[] data)
```
初期値を指定しなかった場合は、0が初期値となる。
- 引数
  - `n` : 要素数
  - `data` : 初期値
- 計算量
  - $O(n)$

### 1要素の加算
```java
public void add(int p, long x)
```
`p`番目の要素に`x`を加算する
- 引数
  - `p` : 位置 $(0\le p\lt n)$
  - `x` : 加算する値
- 計算量
  - $O(\log n)$

### 区間和
```java
public long sum(int l, int r)
public long sum(int r)
```
前者は $l\le p\lt r$ の範囲の、後者は $0\le p\lt r$ の範囲の要素の総和を求める。
- 引数
  - `l`, `r` : 和を求める範囲 $(0 \le l \le r \le n)$
- 計算量
  - $O(\log{n})$
