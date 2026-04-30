# 盤面・座標
2次元格子盤面および座標を管理するクラス。

## 盤面(`Grid`)クラス
### コンストラクタ
```java
public Grid(int N)
public Grid(int H, int W)
```
- 引数
  - `N` : 盤面が正方形の場合の1辺の長さ
  - `H`, `W` : 盤面の縦横の長さ
- 計算量
  - $O(HW)$

### 座標(`Pos`)インスタンスの取得
```java
public Pos pos(int i, int j)
public Pos pos(int id)
```
- 引数
  - `i`, `j` : 座標。範囲外を指定した場合は例外をスローする。
  - `id` : 座標に対応するID
- 計算量
  - $O(1)$

### 盤面内判定
```java
public boolean isValid(int i, int j)
```
座標 (i, j) が盤面内かどうかを判定する。
- 引数
  - `i`, `j` : 座標
- 計算量
  - $O(1)$

### 隣接関係の初期化（4方向）
```java
public void initAdj()
public void initAdj(BitSet[] horizontalWall, BitSet[] verticalWall)
public void initAdj(BiPredicate<Pos, Pos> existsWall)
```
座標(`Pos`)インスタンスに対して、4方向の隣接関係を初期化する。引数を指定しなかった場合は壁はないものとして隣接関係を初期化する。
- 引数
  - `horizontalWall` : 長さ $W$ のBitSetの長さ $H - 1$ の配列。`horizontalWall[i]` は行$i$ と $i+1$ の間の $j$ 列目の壁の有無を表す
	- `verticalWall` : 長さ $W - 1$ のBitSetの長さ $H$ の配列。`verticalWall[i]` は行 $i$ の列 $j$ と $j+1$ の間の壁の有無を表す。
	- `existsWall` : 2つの座標の間の壁の有無を指定する。
- 計算量
  - $O(HW)$

### 隣接関係の初期化（8方向）
```java
public void initAdj8()
public void initAdj8(BiPredicate<Pos, Pos> existsWall)
```
座標(`Pos`)インスタンスに対して、8方向の隣接関係を初期化する。引数を指定しなかった場合は壁はないものとして隣接関係を初期化する。
- 引数
	- `existsWall` : 2つの座標の間の壁の有無を指定する。
- 計算量
  - $O(HW)$

### フィールド
```java
public final Pos[] pos1
```
- 全ての `Pos` を1次元配列で保持。インデックスは各 `Pos` の `id` に対応。

## 座標(`Pos`)クラス
### コンストラクタ
```java
public Pos(int i, int j, int id, Grid grid)
```
インスタンスを生成する。通常は `Grid` コンストラクタ内で自動生成される。
- 引数
  - `i`, `j` : 座標
  - `id` : 座標に一意なID
	- `grid` : Gridインスタンス
- 計算量
  - $O(1)$

### フィールド
```java
public final int i
public final int j
public final int id
public ArrayList<Pos> adj
public Pos[] adj4
public Pos[] adj8
```
- `i`, `j` : 行・列の座標。
- `id` : 1次元インデックス ($ 0 \leq id < HW $)。
- `adj` : 実在する隣接座標のリスト（`initAdj()` または `initAdj8()` 呼び出し後に設定）。
- `adj4` : 4方向の隣接座標の配列。`Direction.DIR` のインデックスに対応。`null` の要素は存在しない方向。
- `adj8` : 8方向の隣接座標の配列。`Direction.DIR8` のインデックスに対応。`null` の要素は存在しない方向。

### 隣接関係の初期化（4方向）
```java
public void initAdj(BiPredicate<Pos, Pos> existsWall)
```
4方向の隣接関係を初期化。`existsWall` 関数で壁の有無を判定。通常は`Grid`クラス経由で初期化する。`adj`と`adj4`を初期化する。
- 引数
	- `existsWall` : 2つの座標の間の壁の有無を指定する。
- 計算量
  - $O(1)$

### 隣接関係の初期化（8方向）
```java
public void initAdj8(BiPredicate<Pos, Pos> existsWall)
```
8方向の隣接関係を初期化。`existsWall` 関数で壁の有無を判定。通常は`Grid`クラス経由で初期化する。`adj`と`adj8`を初期化する。
- 引数
	- `existsWall` : 2つの座標の間の壁の有無を指定する。
- 計算量
  - $O(1)$

### 隣接位置の取得（4方向）
```java
public Pos next(Direction d)
public Pos next(Direction d, int cnt)
```
指定した方向の`cnt`マス先の位置を取得。`cnt`なしのメソッドは、壁のある方向を指定した場合は`null`を返却する。`cnt`ありのメソッドは、壁の有無に関わらず`cnt`マス先の座標を返す。
盤面外の場合はいずれのメソッドも`null`を返す。
- 引数
	- `d` : 方向
	- `cnt` : 何マス移動した位置か。無指定の場合は1とする。
- 計算量
  - $O(1)$

### 隣接位置の取得（8方向）
```java
public Pos next8(Direction d)
public Pos next8(Direction d, int cnt)
```
指定した方向の`cnt`マス先の位置を取得。`cnt`なしのメソッドは、壁のある方向を指定した場合は`null`を返却する。`cnt`ありのメソッドは、壁の有無に関わらず`cnt`マス先の座標を返す。
盤面外の場合はいずれのメソッドも`null`を返す。
- 引数
	- `d` : 方向
	- `cnt` : 何マス移動した位置か。無指定の場合は1とする。
- 計算量
  - $O(1)$
