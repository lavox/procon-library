# トポロジカルソート
閉路のない有向グラフ(DAG)について、辺の向きが小さい番号から大きい番号になるように並べ替える。

### コンストラクタ
```java
public TopologicalSort(int n)
```
- 引数
  - `n` : 頂点数
- 計算量
  - $O(n)$

### 頂点間に辺を追加
```java
public void addEdge(int from, int to)
```
頂点`from`から頂点`to`への辺を追加する。
- 引数
  - `from`, `to` : 頂点番号$(0 \le \mathrm{from}, \mathrm{to} \lt n)$
- 計算量
  - ならし$O(1)$

### トポロジカルソートの実行
```java
public boolean sort()
```
トポロジカルソートを実行する。実行に成功した場合は`true`を返す。
- 計算量
  - $O(n+m)$ ($m$は辺の数)

### 頂点番号の取得
```java
public int nodeAt(int i)
```
ソート後の`i`番目の頂点番号を取得する
- 引数
  - `i` : 順序番号 $(0 \le i \lt n)$
- 計算量
  - $O(1)$

### 順序番号の取得
```java
public int idxOf(int v)
```
頂点`v`のソート後の順序番号を取得する
- 引数
  - `v` : 頂点番号 $(0 \le v \lt n)$
- 計算量
  - $O(1)$

### 頂点番号を表す配列の取得
```java
public int[] nodeArray()
```
頂点番号を表す配列を取得する。取得した配列は書き換えを行わないこと。
- 計算量
  - $O(1)$

### 位置を表す配列の取得
```java
public int[] indexArray()
```
位置を表す配列を取得する。取得した配列は書き換えを行わないこと。
- 計算量
  - $O(1)$
