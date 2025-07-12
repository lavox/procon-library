# Z Algorighm
`s`の長さを$n$として、長さ$n$の配列を返す。$i$番目の要素は、`s[0..n)`と`s[i..n)`の共通接頭辞(LCP=Longest Common Prefix)の長さ

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
```
- 引数
  - `s` : 文字列 または Tの配列(Tは`equals()`を適切に実装されている必要がある)
- 計算量
  - $O(n)$
