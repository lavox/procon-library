# アルゴリズムコンテスト用テンプレート

アルゴリズムコンテストの提出ファイル用のテンプレートです。`void solve()`内に入出力や処理の本体を記述します。
入出力の利便性向上のためのクラスやメソッドが組み込まれています。

## 入力関連
Java標準の`java.util.Scanner`クラスは処理が遅いため、入力を高速に読み込むことのできる`FastScanner`クラスを用意しています。テンプレート内に
```java
FastScanner sc = new FastScanner(System.in);
```
まで記述してあるので、その後に
```java
int N = sc.nextInt();
```
などのように入力の読み込み処理を記述することができます。以下は用意している入力用メソッドです。

### int型の入力
```java
public int nextInt()
```
int型の数値を1つ読み込みます。

```java
public int[] nextIntArray(int N)
public int[] nextIntArray(int N, IntUnaryOperator conv)
```
N個のint型の数値を配列形式で読み込みます。0-indexedにしたい場合などは`int[] a = sc.nextIntArray(N, n -> n - 1)`のように`conv`を指定することで値を変換することができます。

```java
public int[][] nextIntMatrix(int N, int M)
public int[][] nextIntMatrix(int N, int M, IntUnaryOperator conv)
```
N行M列のint型の数値を2次元配列形式で読み込みます。`conv`の使用方法は配列の時と同様です。

### long型の入力
```java
public long nextLong()
```
long型の数値を1つ読み込みます。

```java
public long[] nextLongArray(int N)
```
N個のlong型の数値を配列形式で読み込みます。

```java
public long[][] nextLongMatrix(int N, int M)
```
N行M列のlong型の数値を2次元配列形式で読み込みます。

### double型の入力
```java
public double nextDouble()
```
double型の数値を1つ読み込みます。

### String型の入力
```java
public String next()
```
文字列を1つ読み込みます。

```java
public String[] nextStringArray(int N) 
```
N個の文字列を配列形式で読み込みます。

```java
public String[][] nextStringMatrix(int N, int M)
```
N行M列のlong型の数値を2次元配列形式で読み込みます。

```java
public boolean hasNext()
```
次の入力の有無をチェックします。(滅多に使いません)

## 出力関連
Mainクラスのstaticメソッドや定数を用意しています。出力用メソッドはいずれも最後に改行文字`\n`を出力します。

### 1つの値の出力
```java
public static void print(int a)
public static void print(long a)
public static <T> void print(T s)
```
指定された値を出力します。多数回繰り返して呼ぶことは推奨しません。配列用の出力メソッドを使用するか、`StringBuilder`を使って出力する文字列を組み立ててから出力してください。

`T s`については、`toString()`の結果を出力します。

```java
public static void printYesNo(boolean yesno)
```
`true`を指定した場合は`Yes`を、`false`を指定した場合は`No`を出力します。

```java
public static void printDouble(double val, int digit)
```
doubleの値を小数点以下`digit`位まで出力します。

### 配列の出力
```java
public static void print(int[] array, char sep)
public static void print(int[] array, char sep, IntUnaryOperator conv)
public static void print(int[] array, char sep, IntUnaryOperator conv, int start, int end)
public static void print(long[] array, char sep)
public static void print(long[] array, char sep, LongUnaryOperator conv)
public static void print(long[] array, char sep, LongUnaryOperator conv, int start, int end)
public static <T> void print(T[] array, char sep)
public static <T> void print(T[] array, char sep, LongUnaryOperator conv)
public static <T> void print(T[] array, char sep, LongUnaryOperator conv, int start, int end)
public static <T> void print(ArrayList<T> array, char sep)
public static <T> void print(ArrayList<T> array, char sep, UnaryOperator<T> conv)
public static <T> void print(ArrayList<T> array, char sep, UnaryOperator<T> conv, int start, int end)
public static void printYesNo(boolean[] array, char sep)
public static void printYesNo(boolean[] array, char sep, LongUnaryOperator conv)
public static void printYesNo(boolean[] array, char sep, LongUnaryOperator conv, int start, int end)
```
配列や`ArrayList`形式の値を出力します。区切り文字は`sep`で指定します(通常は`SPACE`または`LF`を指定します)。また`conv`を指定することで値を変換して出力することも可能です。

`start`と`end`を指定することで、配列のどの範囲を出力するか指定することも可能です。$\mathsf{start} \le i \lt \mathsf{end}$の範囲が出力されます。

`T[] array`や`ArrayList<T> array`については、`toString()`の結果を出力します。
`boolean[] array`については、`true`は`Yes`を、`false`は`No`を出力します。

### 複数値の出力
```java
public static void print(int... a)
public static void print(long... a)
public static <T> void print(T... s)
```
指定された値をスペース区切りで出力します。
`T... s`については、`toString()`の結果を出力します。

### 定数
```java
public static final char LF = '\n';
public static final char SPACE = ' ';
public static final String YES = "Yes";
public static final String NO = "No";
```
上記の定数が定義されています。
