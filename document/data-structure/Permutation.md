# 順列
順列に関する操作用のクラス
- 値の入れ替え
- i番目の値や、値vの位置の取得
- 順列全列挙(staticでも実行可能)

### コンストラクタ
```java
public Permutation(int n)
public static Permutation createPermutationByValue(int[] val)
public static Permutation createPermutationByIndex(int[] idx)
```
`n`を指定した場合は、$\{0,1,\dots,n-1\}$で初期化される。また、`val`や`idx`を指定する場合は、必ず`0`から`n-1`までの値が1回ずつ現れる配列であること。
- 引数
  - `n` : 配列の長さ
  - `val` : 値の配列(`i`番目に`i`番目の値が格納されている配列)
  - `idx` : 位置の配列(`x`番目に値`x`の位置が格納されている配列)
- 計算量
  - $O(n)$

### 位置の取得
```java
public int idxOf(int v)
```
値`v`の位置を取得する
- 引数
  - `v` : 値
- 計算量
  - $O(1)$

### 値の取得
```java
public int valAt(int i)
```
位置`i`の値を取得する
- 引数
  - `i` : 位置
- 計算量
  - $O(1)$

### 値の入れ替え(値指定、位置指定)
```java
public void swapVal(int u, int v)
public void swapIdx(int i, int j)
```
位置や値を指定して入れ替えを行う。
- 引数
  - `i`, `j` : 位置
  - `u`, `v` : 値
- 計算量
  - $O(1)$

### 値を表す配列の取得
```java
public int[] values()
```
値を表す配列を取得する。取得した配列は書き換えを行わないこと。
- 計算量
  - $O(1)$

### 位置を表す配列の取得
```java
public int[] indexes()
```
位置を表す配列を取得する。取得した配列は書き換えを行わないこと。
- 計算量
  - $O(1)$

### 次の順列に変換する(staticあり)
```java
public boolean nextPermutation()
public static boolean nextPermutation(int[] array)
public static boolean nextPermutation(long[] array)
```
辞書順で、与えられた順列に対して次に大きい順列が存在すれば`true`、存在しなければ`false`を返す。`true`の場合は、次に大きい順列に書き換える。
- 引数
  - `array` : 配列
- 計算量
  - $O(\mathrm{len(array)})$
