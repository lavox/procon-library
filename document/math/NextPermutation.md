# 順列全列挙
辞書順で与えられた順列の次に大きい順列に書き換える。

### 次の順列に変換する
```java
public static boolean nextPermutation(int[] array)
public static boolean nextPermutation(long[] array)
```
辞書順で、与えられた順列に対して次に大きい順列が存在すれば`true`、存在しなければ`false`を返す。`true`の場合は、次に大きい順列に書き換える。
- 引数
  - `array` : 配列
- 計算量
  - $O(\mathrm{len(array)})$
