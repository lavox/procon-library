# Low Link
グラフの関節点と橋を検出する。
- 関節点: 取り除くと連結成分が増加する頂点
- 橋: 取り除くと連結成分が増加する辺

### コンストラクタ
```java
public LowLink(Graph g)
```
- 引数
  - `g` : 頂点数`n`、辺数`m`のグラフ
- 計算量
  - $O(n+m)$

### グラフの連結成分数の取得
```java
public int componentCnt()
```
グラフの連結成分数。
- 計算量
  - $O(1)$

### 橋の判定
```java
public boolean isBridge(Edge e)
public boolean isBridge(int eid)
```
橋かどうかを判定する。
- 引数
  - `e` : 辺
  - `eid` : 辺番号 $(0 \le \mathrm{eid} \lt m)$
- 計算量
  - $O(1)$

### すべての橋の取得
```java
public ArrayList<Edge> bridges()
```
すべての橋を取得する。
- 計算量
  - $O(1)$

### 関節点の判定
```java
public boolean isArticulation(int i)
```
関節点かどうかを判定する。
- 引数
  - `i` : 頂点番号 $(0 \le i \lt n)$
- 計算量
  - $O(1)$

### 関節点の分離度を取得
```java
public int articulationCnt(int i)
```
その関節点を取り除くといくつ連結成分が増加するかを取得する。
- 引数
  - `i` : 頂点番号 $(0 \le i \lt n)$
- 計算量
  - $O(1)$

### すべての関節点の取得
```java
public ArrayList<LowLink.Node> articulations()
public class Node {
  public int id()
  public boolean isArticulation()
  public int articulationCnt()
}
```
すべての関節点を取得する。
- 計算量
  - $O(1)$

### 頂点情報の取得
```java
public Node node(int i)
```
頂点の情報を取得する。
- 計算量
  - $O(1)$

## 検証
- [Articulation Points (Aizu Online Judge)](https://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=10857824#2)
- [Bridges (Aizu Online Judge)](https://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=10857826#2)
- [ABC334G Christmas Color Grid 2 (AtCoder)](https://atcoder.jp/contests/abc334/submissions/69047413)
