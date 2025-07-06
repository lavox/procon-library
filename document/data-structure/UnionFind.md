# Union Find木
グラフに辺を追加していった時に、頂点同士が連結しているかどうかを高速に判定することのできるデータ構造。

### コンストラクタ
```java
public UnionFind(int N)
```
- 引数
  - `N` : 頂点数
- 計算量
  - $O(N)$

### 頂点間に辺を追加
```java
public void unite(int i, int j)
```
頂点`i`と頂点`j`の間に辺を追加する。
- 引数
  - `i`, `j` : 頂点番号$(0 \le i, j \lt N)$
- 計算量
  - ならし$O(\alpha(N))$

### 連結判定
```java
public boolean isSame(int i, int j)
```
頂点`i`と頂点`j`が同一連結成分かどうかを判定する。
- 引数
  - `i`, `j` : 頂点番号$(0 \le i, j \lt N)$
- 計算量
  - ならし$O(\alpha(N))$

### 連結成分のサイズ取得
```java
public int size(int i)
```
頂点`i`を含む連結成分に含まれる頂点数を取得する。
- 引数
  - `i` : 頂点番号$(0 \le i \lt N)$
- 計算量
  - ならし$O(\alpha(N))$

### 代表頂点番号
```java
public int root(int i)
```
頂点`i`を含む連結成分の代表頂点番号を取得する。
- 引数
  - `i` : 頂点番号$(0 \le i \lt N)$
- 計算量
  - ならし$O(\alpha(N))$

