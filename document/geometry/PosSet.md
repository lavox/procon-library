# 座標集合
盤面上の複数の座標 (`Pos`) を集合として管理するクラス。内部では `BitSet` を使い、高速な集合演算を提供する。
内部で`BitSet`を使用しているため、$HW/64$ 程度の計算量のものは $O(M)$ と記述する。

### コンストラクタ
```java
public PosSet(Grid grid)
public PosSet(Collection<Pos> list, Grid grid)
```
- 引数
  - `grid` : Gridインスタンス
  - `list` : 管理する座標リスト
- 計算量
  - $O(M + m)$ ($m$ は`list`の個数)

### インスタンスの複製
```java
public PosSet clone()
```
現在の集合のコピーを返す。
- 計算量
  - $O(M)$

### BitSet 変換
```java
public BitSet toBitSet()
```
内部表現の `BitSet` を返す。
- 計算量
  - $O(M)$

### 最初 / 次の要素取得
```java
public Pos firstMem()
public Pos nextMem(Pos p)
```
`firstMem()` は集合内の最小 `id` の要素を、`nextMem(Pos p)`は`p`の次の要素を返す。存在しない場合は`null`を返す。ループ処理での使用を想定。
- 引数
  - `p` : 現在座標
- 計算量
  - $O(M)$

### 要素追加
```java
public void add(Pos p)
```
座標を追加する
- 引数
  - `p` : 追加する座標
- 計算量
  - $O(1)$

### 要素追加
```java
public void addAll(Predicate<Pos> addMem)
public void addAll(PosSet ps)
public void addAll(Collection<Pos> list)
public void addAll()
```
要素を追加する。引数指定なしの場合は、全座標を追加する。
- 引数
  - `addMem`, `ps`, `list` : 追加対象座標集合
- 計算量
  - $O(m)$ : `addAll(Collection<Pos> list)` ($m$ は追加対象座標数)
  - $O(M)$ : `addAll(PosSet ps)`, `addAll()`
  - $O(HW)$ : `addAll(Predicate<Pos> addMem)`

### 要素削除
```java
public void remove(Pos p)
public void removeAll(PosSet ps)
public void removeAll(BitSet bs)
public void removeAll(Predicate<Pos> removeTest)
```
要素を削除する。
- 引数
  - `p` : 削除する座標
  - `ps`, `bs`, `removeTest` : 削除対象座標集合
- 計算量
  - $O(1)$ : `remove(Pos p)`
  - $O(M)$ : `removeAll(PosSet ps)`, `removeAll(BitSet bs)`
  - $O(M + m)$ : `removeAll(Predicate<Pos> removeTest)` ($m$ は現在の座標数)

### 包含判定
```java
public boolean includes(PosSet ps)
public boolean includes(BitSet bs)
```
引数の集合が自身に含まれるかを判定する。
- 引数
  - `ps`, `bs` : 対象座標集合
- 計算量
  - $O(M)$

### 全座標削除
```java
public void clear()
```
全座標を削除する。
- 計算量
  - $O(M)$

### メンバ判定
```java
public boolean isMember(Pos p)
```
指定した座標がメンバに含まれるか判定する。
- 引数
  - `p` : 対象座標
- 計算量
  - $O(1)$

### 集合演算
```java
public PosSet or(PosSet ps)
public PosSet and(PosSet ps)
public PosSet not()
```
座標集合に対して集合演算を行い、結果を返却する。元の`PosSet`は変更されない。
- 引数
  - `ps` : 対象座標集合
- 計算量
  - $O(M)$

### 要素数の取得
```java
public int size()
```
集合の要素数を返す。
- 計算量
  - $O(1)$

### 配列への変換
```java
public ArrayList<Pos> toPosArray()
```
集合内の位置をリストとして返す。
- 計算量
  - $O(M + m)$ ($m$ は現在の座標数)

### 反復処理
`PosSet` は `Iterable<Pos>` を実装しており、`for (Pos p : posSet)` のように走査可能。

### デバッグ出力
```java
public void debug()
```
含まれる集合を2次元形式で標準エラー出力に出力する。
- 計算量
  - $O(HW)$

