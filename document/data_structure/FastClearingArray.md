# Fast-Clearing Array
インデックス付き配列の高速クリアを実現するデータ構造。全要素を初期値に戻すのではなく、内部世代番号を更新することで、`clear()` を $O(1)$ で実行できる。

格納する要素がint用の`IntFastClearingArray`も利用可能。

### コンストラクタ
```java
// 一般クラス用
public FastClearingArray(int N, T defaultVal)
// int用
public IntFastClearingArray(int N, int defaultVal)
```
- 引数
  - `N` : 配列のサイズ
  - `defaultVal` : クリア後および未設定時のデフォルト値
- 計算量
  - $O(N)$ (内部配列の確保)

### 値の取得
```java
// 一般クラス用
public T get(int i)
// int用
public int get(int i)
```
`i` 番目の要素を返す。まだ `set` されていないか、`clear()` 後であれば `defaultVal` を返す。
- 引数
  - `i` : インデックス番号 $(0 \le i\lt N)$
- 計算量
  - $O(1)$

### 値の設定
```java
// 一般クラス用
public void set(int i, T v)
// int用
public void set(int i, int v)
```
`i` 番目の要素に値 `v` を設定する。
- 引数
  - `i` : インデックス番号 $(0 \le i\lt N)$
	- `v` : 設定する値
- 計算量
  - $O(1)$

### クリア
```java
// 一般クラス・int用共通
public void clear()
```
全要素の値をリセットする。
- 計算量
  - $O(1)$
