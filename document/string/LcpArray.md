# LCP(Longest Common Prefix) Array
長さ $n$ の文字列`s`のLCP Arrayとして、長さ $n-1$ の配列を返す。 $i$ 番目の要素は、`s[sa[i]..n)`と`s[sa[i+1]..n)`のLCP(Longest Common Prefix)の長さ。

ac-libraryの[string](https://github.com/atcoder/ac-library/blob/master/document_ja/string.md)の移植。

```java
// 実行例
String s = "abracadabra";
int[] sa = suffixArray(s);
int[] la = lcpArray(s, sa);
// la = {1,4,1,1,0,3,0,0,0,2};
// 10 a -> 1
//  7 abra -> 4
//  0 abracadabra -> 1
//  3 acadabra -> 1
//  5 adabra -> 0
//  8 bra -> 3
//  1 bracadabra -> 0
//  4 cadabra -> 0
//  6 dabra -> 0
//  9 ra -> 2
//  2 racadabra
```

### LCP Array
```java
public static int[] lcpArray(String s, int[] sa)
public static <T> int[] lcpArray(T[] s, int[] sa)
public static int[] lcpArray(IntBinaryOperator op, int n, int[] sa)
```
- 引数
  - `s` : 文字列 または Tの配列(Tは`equals()`を適切に実装されている必要がある)
  - `op` : 2つの引数`i`,`j`に対し、対象の`i`番目と`j`番目が一致するなら0、一致しないならそれ以外を返す2変数の`int`値演算
  - `n` : 対象の長さ
  - `sa` : `s`のSuffix Array
- 計算量
  - $O(n)$

## 検証
- [AtCoder Library Practice Contest-I Number of Substrings (AtCoder)](https://atcoder.jp/contests/practice2/submissions/68701505)
