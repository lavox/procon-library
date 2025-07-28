# Z Algorighm
`s`の長さを $n$ として、長さ $n$ の配列を返す。 $i$ 番目の要素は、`s[0..n)`と`s[i..n)`の共通接頭辞(LCP=Longest Common Prefix)の長さ

```java
// 実行例
String s = "ababaababaabababc";
int[] z = zAlgorithm(s);
// z = {17,0,3,0,1,10,0,3,0,1,5,0,4,0,2,0,0};
```

### Z Algorighm
```java
public static int[] zAlgorithm(String s)
public static <T> int[] zAlgorithm(T[] s)
public static int[] zAlgorithm(IntBinaryOperator op, int n) 
```
- 引数
  - `s` : 文字列 または Tの配列(Tは`equals()`を適切に実装されている必要がある)
  - `op` : 2つの引数`i`,`j`に対し、対象の`i`番目と`j`番目が一致するなら0、一致しないならそれ以外を返す2変数の`int`値演算
  - `n` : 対象の長さ
- 計算量
  - $O(n)$
