# 最小費用流
有向グラフの最小費用流を求める。

ac-libraryの[MinCostFlow](https://github.com/atcoder/ac-library/blob/master/document_ja/mincostflow.md)の移植。

### コンストラクタ
```java
public MinCostFlow(int n)
```
- 引数
  - `n` : 頂点数
- 計算量
  - $O(n)$

### 頂点間に辺を追加
```java
public int addEdge(int from, int to, long cap, long cost)
```
頂点`from`から頂点`to`への容量`cap`、コスト`cost`の辺を追加する。返り値は辺番号。
- 引数
  - `from`, `to` : 頂点番号$(0 \le \mathrm{from}, \mathrm{to} \lt n)$
  - `cap` : 容量$(0 \le \mathrm{cap})$
  - `cost` : コスト$(0 \le \mathrm{cost})$
- 計算量
  - ならし$O(1)$

### 辺の状態を取得
```java
public MinCostFlow.Edge getEdge(int i)
public ArrayList<MinCostFlow.Edge> edges()
public class Edge {
  int from;
  int to;
  long cap;
  long flow;
  long cost;
}
```
全ての辺の状態、または指定した辺番号の辺の状態を取得する。`cap`は辺の容量、`flow`は流量、`cost`はコスト。
- 引数
  - `i` : 辺番号
- 計算量
  - 辺番号指定の場合$O(1)$、全ての辺の場合$O(m)$ ($m$は追加した辺の数)

### 最小費用流
```java
public MinCostFlow.Flow flow(int s, int t)
public MinCostFlow.Flow flow(int s, int t, long flowLimit)
public class Flow {
  long flow;
  long cost;
}
```
頂点`s`から頂点`t`へ流せるだけ流し、その流量とコストを返す。
- 引数
  - `s`, `t` : 頂点番号$(0 \le s, t \lt n, s \ne t)$
  - `flowLimit` : 流す最大量
- 計算量 ($m$を追加した辺数、$F$を流量とする)
  - $O(F(n + m)\log{(n + m)})$

### 最小費用流(折れ線)
```java
public ArrayList<MinCostFlow.Flow> slope(int s, int t)
public ArrayList<MinCostFlow.Flow> slope(int s, int t, long flowLimit)
```
流量とコストの関係の折れ線を返す。全ての$x$について、流量$x$の時の最小コストを$g(x)$とすると、$(x,g(x))$は返り値を折れ線として見たものに含まれる。
- 返り値の最初の要素は$(0,0)$
- 3点が同一線上にあることはない
- 最後の要素は最大流量(`flowLimit`を指定した場合は`flowLimit`以下の値)
- `slope()`や`flow()`を合わせて複数回呼んだときの挙動は未定義
- $0\le nx \le 8\times 10^{18} + 1000$

- 引数
  - `s`, `t` : 頂点番号$(0 \le s, t \lt n, s \ne t)$
  - `flowLimit` : 流す最大量
- 計算量 ($m$を追加した辺数、$F$を流量とする)
  - $O(F(n + m)\log{(n + m)})$

