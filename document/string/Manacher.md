# Manacher Algorithm(最長回文半径)
`s`の長さを $n$ として、長さ $n$ の配列を返す。 $i$ 番目の要素は、$i$ 番目の文字を中心とする最長回文の半径

```java
// 実行例
String s = "abaaababa";
int[] radius = manacher(s);
// radius = {1,2,1,4,1,2,3,2,1};
```

[Luzhiledさんのライブラリ](https://ei1333.github.io/luzhiled/snippets/string/manacher.html)の移植。

### Manacher Algorighm
```java
public static int[] manacher(IntBinaryOperator op, int n)
public static <T> int[] manacher(T[] s)
public static int[] manacher(String s)
```
- 引数
  - `s` : 文字列 または Tの配列(Tは`equals()`を適切に実装されている必要がある)
  - `op` : 2つの引数`i`,`j`に対し、対象の`i`番目と`j`番目が一致するなら0、一致しないならそれ以外を返す2変数の`int`値演算
  - `n` : 対象の長さ
- 計算量
  - $O(n)$

## 検証
- [Enumerate Palindromes (Library Checker)](https://judge.yosupo.jp/submission/349207)
