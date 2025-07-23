# 最大流・最小カット
有向グラフの最大流・最小カットを求める。

ac-libraryの[MaxFlow](https://github.com/atcoder/ac-library/blob/master/document_ja/maxflow.md)の移植。

### コンストラクタ
```java
public MaxFlow(int n)
```
- 引数
  - `n` : 頂点数
- 計算量
  - $O(n)$

### 頂点間に辺を追加
```java
public int addEdge(int from, int to, long cap)
```
頂点`from`から頂点`to`への容量`cap`の辺を追加する。返り値は辺番号。
- 引数
  - `from`, `to` : 頂点番号 $(0 \le \mathrm{from}, \mathrm{to} \lt n)$
  - `cap` : 容量
- 計算量
  - ならし $O(1)$

### 辺の状態を取得
```java
public MaxFlow.Edge getEdge(int i)
public ArrayList<Edge> edges()
public class Edge {
  int from;
  int to;
  long cap;
  long flow;
}
```
全ての辺の状態、または指定した辺番号の辺の状態を取得する。`cap`は辺の容量、`flow`は流量。
- 引数
  - `i` : 辺番号
- 計算量
  - 辺番号指定の場合 $O(1)$ 、全ての辺の場合 $O(m)$  ( $m$ は追加した辺の数)

### 辺の容量・流量の変更
```java
public void changeEdge(int i, long newCap, long newFlow)
```
辺番号`i`の容量・流量を変更する。変更後に`flow()`を実行することで、変更後の容量・流量から開始した場合の最大流を求めることができる。
- 引数
  - `i` : 辺番号
  - `newCap` : 変更後の容量
  - `newFlow` : 変更後の流量
- 計算量
  - $O(1)$

### 最大流
```java
public long flow(int s, int t)
public long flow(int s, int t, long flowLimit)
```
頂点`s`から頂点`t`への最大流を求める。
- 引数
  - `s`, `t` : 頂点番号 $(0 \le s, t \lt n)$
  - `flowLimit` : 流す最大量
- 計算量 ( $m$ を追加した辺数とする)
  - $O((n + m)\sqrt{m})$ (辺の容量がすべて1の時)
  - $O(n^2m)$
  - $O(F(n + m))$ (返り値を $F$ とする)

### 最小カット
```java
public BitSet minCut(int s)
```
長さ $n$ の`BitSet`を返す。 $i$ 番目のビットは頂点 $s$ から頂点 $i$ に残余グラフで到達可能かどうかを表す。`flow(s,t)`を`flowLimit`なしでちょうど一回呼んだ後に呼ぶと、返り値は $s,t$ 間の最小カットに対応する。

残余グラフ：辺の順方向に`cap - flow`、逆方向に`flow`の容量があるとしたグラフ
- 引数
  - `s`, `t` : 頂点番号 $(0 \le s, t \lt n)$
- 計算量 ($m$を追加した辺数とする)
  - $O(n + m)$

