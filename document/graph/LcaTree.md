# 最近共通祖先(LCA)
木の最近共通祖先(LCA)を求める。

参考情報
- [ダブリングによる木の最近共通祖先（LCA：Lowest Common Ancestor）を求めるアルゴリズム](https://algo-logic.info/lca/)

### コンストラクタ
```java
public LcaTree(int n)
```
- 引数
  - `n` : 頂点数
- 計算量
  - $O(n)$

### 頂点間に辺を追加
```java
public void addEdge(int u, int v)
```
頂点`u`,`v`間に辺を追加する。
- 引数
  - `u`, `v` : 頂点番号$(0 \le u, v \lt n)$
- 計算量
  - ならし$O(1)$

### 祖先情報の構築(LCAを求める準備)
```java
public void build(int root)
```
`root`を根として、ダブリングにより各頂点の祖先情報を構築する。
- 引数
  - `root` : 木の根とする頂点番号$(0 \le \mathrm{root} \lt n)$
- 計算量
  - $O(n \log n)$

### 最近共通祖先(LCA)
```java
public int lca(int u, int v)
```
指定した2頂点の最近共通祖先(LCA)の頂点番号を求める。

事前に`build()`を実行しておく必要がある。

- 引数
  - `u`, `v` : 頂点番号$(0 \le u, v \lt n)$
- 計算量
  - $O(\log n)$

### 頂点の深さ
```java
public int depth(int u)
```
指定した頂点の深さを返す。

事前に`build()`を実行しておく必要がある。
- 引数
  - `u` : 頂点番号$(0 \le u \lt n)$
- 計算量
  - $O(1)$

### 祖先の取得
```java
public int ancestor(int u, int d)
```
指定した頂点から`d`代祖先にあたる頂点の頂点番号を返す。

事前に`build()`を実行しておく必要がある。
- 引数
  - `u` : 頂点番号$(0 \le u \lt n)$
  - `d` : さかのぼる世代数
- 計算量
  - $O(\log d)$
