# Suffix Array
長さ$n$の文字列`s`のSuffix Arrayとして、長さ$n$の配列を返す。Suffix Array `sa`は$(0,1,\dots,n-1)$の順列であって、各$i=0,1,\dots,n-2$について
- `s[sa[i]..n) < s[sa[i+1]..n)`
を満たすもの。

ac-libraryの[string](https://github.com/atcoder/ac-library/blob/master/document_ja/string.md)の移植。

```java
// 実行例
String s = "abracadabra";
int[] sa = suffixArray(s);
// sa = {10,7,0,3,5,8,1,4,6,9,2};
// 10 a
//  7 abra
//  0 abracadabra
//  3 acadabra
//  5 adabra
//  8 bra
//  1 bracadabra
//  4 cadabra
//  6 dabra
//  9 ra
//  2 racadabra
```

### Suffix Array
```java
public static int[] suffixArray(String s) // (1)
public static <T extends Comparable<T>> int[] suffixArray(T[] s) // (2)
public static int[] suffixArray(int[] s, int upper) // (3)
```
- 引数
  - `s` : 文字列 または Tの配列(Tは`equals()`, `compareTo()`を適切に実装されている必要がある)
  - `upper` : `s`の値域($0\le s[i] \le \mathrm{upper}$)
- 計算量
  - (1) $O(n)$
  - (2) $O(n \log n)$
  - (3) $O(n + \mathrm{upper})$
