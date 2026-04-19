# Union Find木 ・ ポテンシャル付きUnion Find木
グラフに辺を追加していった時に、頂点同士が連結しているかどうかを高速に判定することのできるデータ構造。

通常のUnion Find木である`UnionFind`クラスと、ポテンシャル付きUnion Find木である`WeightedUnionFind`クラスが使用可能。

- ポテンシャル(重み)
  - ポテンシャルは群構造を持つものとする(可換でなくても良い)
    - 結合律を満たす演算と、単位元が存在し、すべての元に逆元が存在すること
  - ポテンシャル $\mathrm{weight}(i)$ は、`i`のrootを単位元としたときの頂点`i`のポテンシャルを表す
  - 群の演算を $*$ で表すとき、 $\mathrm{diff}(i,j) = \mathrm{weight}(i)^{-1} * \mathrm{weight}(j)$ を算出することが可能
    - 可換群で演算を加法で記述すると、 $\mathrm{diff}(i,j) = \mathrm{weight}(j) - \mathrm{weight}(i)$
  - ポテンシャルは、`WeightedUnionFind.Weight`インターフェースを実装する
    - 演算は`Weight op(Weight o)`を実装する
    - 逆元は`Weight inv()`を実装する
    - 同一元の判定`boolean equals(Weight o)`を実装する

また、`UnionFind`, `WeightedUnionFind`とも、各連結成分に対して可換モノイドの元を対応付けて管理することが可能。

- 連結成分に紐づける値
  - 値は可換モノイドであること
    - 結合律を満たす可換演算と、単位元が存在すること
  - 連結成分同士が連結する際、各連結成分に対応する値同士を演算したものを新しい値とする
  - 値は`UnionFind.Data`または`WeightedUnionFind.Data`インターフェースを実装する
    - 演算は`void merge(Data o)`を実装する (インスタンスの値を書き換えることを想定)

### コンストラクタ
```java
public UnionFind(int N)
public UnionFind(int N, IntFunction<Data> constructor)
public WeightedUnionFind(int N, Supplier<Weight> e)
public WeightedUnionFind(int N, Supplier<Weight> e, IntFunction<Data> constructor)
```
- 引数
  - `N` : 頂点数
  - `constructor` : 各頂点に紐付ける可換モノイドの元の初期値を生成するための`IntFunction<Data>`。 `i`に対して、頂点`i`の初期値を返却する
  - `e` : ポテンシャルの単位元を生成する`Supplier<Weight>`
- 計算量
  - $O(N)$

### 頂点間に辺を追加
```java
// UnionFind
public void unite(int i, int j)
// WeightedUnionFind
public boolean unite(int i, int j, Weight d)
```
頂点`i`と頂点`j`の間に辺を追加する。`WeightedUnionFind`の場合は、頂点`i`と頂点`j`が既に同一連結成分だった場合、`d`の値がこれまで連結した際の`d`の情報と整合性があれば`true`を、なければ`false`を返す(既に同一連結成分の場合は、ポテンシャルを更新しない)
- 引数
  - `i`, `j` : 頂点番号 $(0 \le i, j \lt N)$
  - `d` : 頂点`i`と頂点`j`のポテンシャルの差。 $d = \mathrm{weight}(i)^{-1} * \mathrm{weight}(j)$
- 計算量
  - ならし $O(\alpha(N))$

### 連結判定
```java
public boolean isSame(int i, int j)
```
頂点`i`と頂点`j`が同一連結成分かどうかを判定する。
- 引数
  - `i`, `j` : 頂点番号 $(0 \le i, j \lt N)$
- 計算量
  - ならし $O(\alpha(N))$

### 連結成分に紐づいた可換モノイドの値の取得
```java
public Data data(int i)
```
頂点`i`を含む連結成分に紐づいた可換モノイドの元の値を取得する。
- 引数
  - `i` : 頂点番号 $(0 \le i \lt N)$
- 計算量
  - ならし $O(\alpha(N))$

### ポテンシャルの取得(`WeightedUnionFind`のみ)
```java
public Weight weight(int i)
```
頂点`i`のポテンシャル(頂点`i`のrootを単位元としたときの値)。
- 引数
  - `i` : 頂点番号 $(0 \le i \lt N)$
- 計算量
  - ならし $O(\alpha(N))$

### ポテンシャルの差の取得
```java
public Weight diff(int i, int j)
```
頂点`i`と頂点`j`のポテンシャルの差 $\mathrm{weight}(i)^{-1} * \mathrm{weight}(j)$ を取得する。
- 引数
  - `i`, `j` : 頂点番号 $(0 \le i, j \lt N)$
- 計算量
  - ならし $O(\alpha(N))$

### 連結成分のサイズ取得
```java
public int size(int i)
```
頂点`i`を含む連結成分に含まれる頂点数を取得する。
- 引数
  - `i` : 頂点番号 $(0 \le i \lt N)$
- 計算量
  - ならし $O(\alpha(N))$

### 代表頂点番号
```java
public int root(int i)
```
頂点`i`を含む連結成分の代表頂点番号を取得する。
- 引数
  - `i` : 頂点番号 $(0 \le i \lt N)$
- 計算量
  - ならし $O(\alpha(N))$

## 可換モノイドインターフェース
```java
public class UnionFind {
	public interface Data {
		public void merge(Data o);
	}
}
public class WeightedUnionFind {
	public interface Data {
		public void merge(Data o);
	}
}
```

## ポテンシャルインターフェース
```java
public class WeightedUnionFind {
	public interface Weight {
		public Weight inv();
		public Weight op(Weight o);
		public boolean equals(Weight o);
	}
}
```

## 検証
- [Unionfind (Library Checker)](https://judge.yosupo.jp/submission/307933)
- [Unionfind with Potential (Library Checker)](https://judge.yosupo.jp/submission/311137)
- [Unionfind with Potential (Non-Commutative Group) (Library Checker)](https://judge.yosupo.jp/submission/311135)
- [AtCoder Library Practice Contest-A Disjoint Set Union (AtCoder)](https://atcoder.jp/contests/practice2/submissions/67373477)
- [ABC183F Confluence (AtCoder)](https://atcoder.jp/contests/abc183/submissions/68894390)