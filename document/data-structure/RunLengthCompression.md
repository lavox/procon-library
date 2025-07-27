# ランレングス圧縮
ランレングス圧縮を行う。同じ値が連続する箇所を圧縮する。`int`型配列、文字列、`i`番目の値を返す`IntUnaryOperator`に対して実行可能。

各機能は`RunLengthCompression`クラスのstaticメソッドとして実装される。ランレングスの各要素は`RunLength`クラスのインスタンスとして表現される。

### ランレングス要素を表現するクラス
```java
class RunLength {
	public int val;
	public long length;
	public long i0;
	public RunLength(int val, long length)
	public RunLength(int val, long length, long i0)
}
```
- 引数
  - `val` : 値(文字の場合はcharにキャストした値)
  - `length` : 長さ
  - `i0` : 開始位置
- コンストラクタの計算量
  - $O(1)$

### 圧縮
```java
public static ArrayList<RunLength> encode(String str)
public static ArrayList<RunLength> encode(int[] vals)
public static ArrayList<RunLength> encode(IntUnaryOperator op, int addLen)
```
圧縮を行う。
- 引数
  - `str` : 文字列
  - `vals` : `int`配列
  - `op` : `i`番目の値を返す関数
  - `addLen` : 追加する長さ
- 計算量
  - $O(\mathrm{addLen})$

### 追加
```java
public static ArrayList<RunLength> add(ArrayList<RunLength> array, String str)
public static ArrayList<RunLength> add(ArrayList<RunLength> array, int[] vals)
public static ArrayList<RunLength> add(ArrayList<RunLength> array, IntUnaryOperator op, int addLen)
public static ArrayList<RunLength> add(ArrayList<RunLength> array, int val, long length)
public static ArrayList<RunLength> add(ArrayList<RunLength> array, char c, long length)
public static ArrayList<RunLength> add(ArrayList<RunLength> array, RunLength e)
```
`array`に対して指定した要素を追加する。
- 引数
  - `array` : ランレングス圧縮を表す`ArrayList`。これに対して指定した値を追加する
  - `str` : 文字列
  - `vals` : `int`配列
  - `op` : `i`番目の値を返す関数
  - `addLen` : 追加する長さ
  - `val` : 値
  - `c` : 文字
  - `length` : 値や文字の長さ
  - `e` : `RunLength`要素(圧縮や`i0`の値の整合性のため、本メソッドを使用して追加すること)
- 計算量
  - `String`, `int[]`, `IntUnaryOperator`指定 : $O(\mathrm{addLen})$
  - `int`, `char`, `RunLength`指定 : $O(1)$

### 全体の長さの取得
```java
public static long totalLength(ArrayList<RunLength> array)
```
圧縮済みの全体の長さを取得する。
- 引数
  - `array` : ランレングス圧縮を表す`ArrayList`
- 計算量
  - $O(1)$

### 指定した位置を含むランレングス要素またはその順序番号を取得
```java
public static int searchIndex(ArrayList<RunLength> array, long i)
public static RunLength search(ArrayList<RunLength> array, long i)
```
圧縮前の`i`番目の値がどのランレングス要素に含まれるか(またはその順序番号)を取得する。
- 引数
  - `array` : ランレングス圧縮を表す`ArrayList`
  - `i` : 圧縮前の値の位置
- 計算量
  - $O(\log s)$ ($s$は`array`の要素数)

### 復元
```java
public static int[] decode(ArrayList<RunLength> array)
public static String decodeAsString(ArrayList<RunLength> array)
```
元の値を`int`配列または文字列で取得する。全体の長さ`totalLen()`が`int`の範囲であること。
- 引数
  - `array` : ランレングス圧縮を表す`ArrayList`
- 計算量
  - $O(\mathrm{totalLen})$

