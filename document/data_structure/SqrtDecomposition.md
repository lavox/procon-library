# 平方分割
平方分割用のユーティリティクラス。配列全体のindexや、バケット番号、バケット内の位置等の相互変換を行う。

### コンストラクタ
```java
public SqrtDecomposition(int N)
public SqrtDecomposition(int N, int B)
```
`n`を指定した場合は、 $\{0,1,\dots,n-1\}$ で初期化される。また、`val`や`idx`を指定する場合は、必ず`0`から`n-1`までの値が1回ずつ現れる配列であること。
- 引数
  - `N` : 平方分割する全体の長さ
  - `B` : 1つあたりのバケットの大きさ。省略した場合は $\sqrt{N}$ とする
- 計算量
  - $O(1)$

### バケット番号、バケット内の位置の取得
```java
public int bid(int idx)
public int lid(int idx)
```
`bid()`はバケット番号を、`lid()`はバケット内の位置を取得する。
- 引数
  - `idx` : 配列全体のindex $(0\le iex \lt N)$
- 計算量
  - $O(1)$

### 配列のindexの取得
```java
public int idx(int bid, int lid)
```
配列全体のindexを取得する。
- 引数
  - `bid` : バケット番号 $(0\le bid \lt BC)$
  - `lid` : バケット内の位置 $(0\le lid \lt B)$
- 計算量
  - $O(1)$

### バケットの開始位置・終了位置・長さの取得
```java
public int bStart(int bid)
public int bEnd(int bid)
public int bLen(int bid)
```
バケットの開始位置、終了位置、長さを取得する。
- 引数
  - `bid` : バケット番号 $(0\le bid \lt BC)$
- 計算量
  - $O(1)$

### バケットの個数の取得
```java
public int bCnt()
```
バケットの個数を取得する。
- 計算量
  - $O(1)$

### インスタンス変数
```java
public final int N;   // 配列全体の長さ
public final int B;   // バケット1つの長さ
public final int BC;  // バケットの個数
```

## 検証
- [AWC0040E 社内ランキング (AtCoder)](https://atcoder.jp/contests/awc0040/submissions/75317812)
