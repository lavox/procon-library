# 木の重心分解
木を重心で再帰的に分解するデータ構造。

## `CentroidDecomposer`クラス
### `SubTree`の生成
```java
public static SubTree createInitTree(Graph g)
```
初期の木を作成する。グラフ`g`全体を1つの`SubTree`として初期化。
- 引数
  - `g` : 無向グラフ（頂点数`n`）
- 計算量
  - $O(n)$

## `SubTree`クラス
### 木の分解
```java
public void decompose()
```
`SubTree`を重心で分解し、子の`SubTree`を作成する。
- 計算量
  - $O(n \log n)$ （全体での計算量）

### 重心の取得
```java
public int centroid()
```
このサブツリーの重心となる頂点番号を返す。`decompose()`の実行後に呼び出す必要がある。
- 計算量
  - $O(1)$

### 分解後の部分木の取得
```java
public ArrayList<SubTree> decomposedTree()
```
このサブツリーを重心で分割した子の`SubTree`リストを取得する。`decompose()`の実行後に呼び出す必要がある。
- 計算量
  - $O(1)$

### 頂点の確認
```java
public boolean isMember(int v)
```
頂点`v`がこのサブツリーに属しているかを確認する。
- 引数
  - `v` : 頂点番号
- 計算量
  - $O(1)$

### グラフインターフェース
`SubTree`は`Graph`インターフェースを実装しているため、以下のメソッドが使用可能。
```java
public int size()                               // サブツリーのサイズ
public void forEachEdge(int v, EdgeConsumer a) // 頂点vの辺をフィルタリング
public Iterable<? extends Edge> edges(int v)   // 頂点vのEdgeイテラブル
public PrimitiveIterator.OfInt edgesTo(int v)  // 頂点vの隣接頂点イテレータ
```

## 検証
- [Frequency Table of Tree Distance (Library Checker)](https://judge.yosupo.jp/submission/371673)
