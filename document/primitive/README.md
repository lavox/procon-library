# primitive用ユーティリティ
Javaのオートボクシングによる性能劣化を避けるためのprimitive専用各種ユーティリティ。

## ArrayList
- IntArrayList : int版
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
- IntArrays, IntComparator : int版
- LongArrays, LongComparator : long版

`java.util.Arrays`, `java.util.Comparator`のprimitive版。`Arrays`は`sort(list, comparator)`のみ実装している。`Arrays.sort()`より2倍程度遅いが、`ArrayList`を`Collections.sort()`するよりは2倍程度速い。

```
=== benchmark ===
long[],Arrays.sort               prepare:     4 ms, main:    45 ms
long[],LongArrays.sort           prepare:     4 ms, main:    84 ms
ArrayList<Long>,Collections.sort prepare:    17 ms, main:   169 ms
```

## HashMap
| クラス名      | キー      | 値        |
|:--------------|:----------|:----------|
| IntIntMap     | int       | int       |
| IntLongMap    | int       | long      |
| IntObjMap     | int       | Object(V) |
| LongIntMap    | long      | int       |
| LongLongMap   | long      | long      |
| LongObjMap    | long      | Object(V) |
| ObjIntMap     | Object(K) | int       |
| ObjLongMap    | Object(K) | long      |

`java.util.HashMap`のprimitive版。主要なほとんどのメソッドは実装済みだが一部未実装のメソッドあり。
`java.util.HashMap`と動作仕様が異なる点は以下の通り。

- 値がint, longのクラス
  - `get()`等で値が存在しなかった場合に`null`の代わりに返す値を、コンストラクタにパラメータ`defaultValue`で指定する。無指定時はintの場合は`Integer.MIN_VALUE`、longの場合は`Long.MIN_VALUE`を指定したものとみなす。
	- `compute()`, `computeIfPresent()`, `merge()`で`java.util.HashMap`では`remappingFunction()`が`null`を返した場合は値が削除されるが、本クラスは値がprimitive型で`null`を返せないため、この仕様はサポートしていない。
	- 紐づけられている値に`value`を加えるメソッド`countUp(value)`を追加している。値が紐づけられていなかった場合は、`value`を紐づける。
- 全般
  - 全エントリを走査する際に、インスタンス生成の節約のため、`entrySet()`に加えて`public EntryIterator entryIterator()`を実装している。`EntryIterator`では`next()`でカーソルを進めた後、`key()`, `value()`でキーや値を取得することができる。

### 検証
- [Associative Array (Library Checker)](https://judge.yosupo.jp/submission/369795)
