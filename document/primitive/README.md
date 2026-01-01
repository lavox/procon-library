# primitive用ユーティリティ
Javaのオートボクシングによる性能劣化を避けるためのprimitive専用各種ユーティリティ。

## ArrayList
- LongArrayList : long版

`java.util.ArrayList`のprimitive版。主要なほとんどのメソッドを実装だが一部未実装のメソッドあり。

拡張for文については`PrimitiveIterator.OfLong`を実装しているが、`long[]`の拡張for文と比較すると大幅に性能劣化している。性能を優先する場合は通常のfor文か、`for (long a: list.toArray())`を使用した方が良い。

```
=== benchmark ===
long[],for-each                  prepare:    13 ms, main:    20 ms
ArrayList<Long>,for-each         prepare:    83 ms, main:   149 ms
LongArrayList,for-each           prepare:     7 ms, main:   125 ms
LongArrayList,for                prepare:     7 ms, main:    19 ms
LongArrayList,PrimitiveIterator  prepare:     7 ms, main:   119 ms
LongArrayList,list.foreach       prepare:     7 ms, main:    91 ms
LongArrayList,for-values         prepare:     7 ms, main:    32 ms
```

## Arrays, Comparator
- LongArrays, LongComparator : long版

`java.util.Arrays`, `java.util.Comparator`のprimitive版。`Arrays`は`sort(list, comparator)`のみ実装している。`Arrays.sort()`より2倍程度遅いが、`ArrayList`を`Collections.sort()`するよりは2倍程度速い。

```
=== benchmark ===
long[],Arrays.sort               prepare:     4 ms, main:    45 ms
long[],LongArrays.sort           prepare:     4 ms, main:    84 ms
ArrayList<Long>,Collections.sort prepare:    17 ms, main:   169 ms
```
