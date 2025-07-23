# 2-SAT
2-SATを解く。変数 $x_0,x_1,\dots,x_{N-1}$ に関して、
- $(x_i=f)\lor(x_j=g)$

という形の条件式を指定し、それらをすべて満たす変数の真偽値の割り当て方があるかを判定する。

本ライブラリは、`SccGraph.java`に依存しているため、使用する際は`SccGraph.java`を合わせて使用する必要がある。

ac-libraryの[twosat](https://github.com/atcoder/ac-library/blob/master/document_ja/twosat.md)の移植。

### コンストラクタ
```java
public TwoSat(int n)
```
- 引数
  - `n` : 頂点数
- 計算量
  - $O(n)$

### 条件式を追加
```java
public void addClause(int i, boolean f, int j, boolean g)
```
条件式 $(x_i=f)\lor(x_j=g)$ を追加
- 引数
  - `i`, `j` : 変数の添え字 $(0 \le i,j \lt n)$
  - `f`, `g` : 真偽値
- 計算量
  - ならし $O(1)$

### 解の存在判定
```java
public boolean isSatisfiable()
```
指定した条件式をすべて満たす解が存在するかどうかを判定する。
- 計算量 ($m$を追加した条件数とする)
  - $O(n + m)$

### 解の取得
```java
public boolean[] answer()
```
条件式をすべて満たす解(の1つ)を取得する。

事前に`isSatisfiable()`を実行しておく必要がある。
- 計算量
  - $O(1)$
