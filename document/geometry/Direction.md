# 方向
2次元格子上の方向を表す列挙型。4方向と8方向の移動を扱い、逆方向や回転を簡単に求められる。

### 定数
```java
public static final Direction[] DIR
public static final Direction[] DIR8
```
- `DIR` : 4方向 (右, 下, 左, 上)
- `DIR8` : 8方向 (右, 右下, 下, 左下, 左, 左上, 上, 右上)

### フィールド
```java
public final int di
public final int dj
public final int id
public final int id8
public final String name
```
- `di`, `dj` : 方向ベクトルの変位
- `id` : 4方向インデックス (R=0, D=1, L=2, U=3)
- `id8` : 8方向インデックス (R=0, RD=1, D=2, LD=3, L=4, LU=5, U=6, RU=7)
- `name` : 方向を表す文字列

### 逆方向
```java
public Direction rev()
```
方向の逆向きを返す。例: `R.rev() == L`
- 計算量
  - $O(1)$

### 90度回転
```java
public Direction rot()
public Direction rot(int i)
```
- 引数
  - `i` : 回転量。$90i$ 度、時計回りに回転する。省略した場合は $90$ 度とする。
- 計算量
  - $O(1)$

### 45度回転
```java
public Direction rot45()
public Direction rot45(int i)
```
- 引数
  - `i` : 回転量。$45i$ 度、時計回りに回転する。省略した場合は $45$ 度とする。
- 計算量
  - $O(1)$

### 90度逆回転
```java
public Direction irot()
public Direction irot(int i)
```
- 引数
  - `i` : 回転量。$90i$ 度、反時計回りに回転する。省略した場合は $90$ 度とする。
- 計算量
  - $O(1)$

### 45度逆回転
```java
public Direction irot45()
public Direction irot45(int i)
```
- 引数
  - `i` : 回転量。$45i$ 度、反時計回りに回転する。省略した場合は $45$ 度とする。
- 計算量
  - $O(1)$

### 水平/垂直判定
```java
public boolean isHorizontal()
public boolean isVertical()
```
水平・垂直の判定をする。
- 計算量
  - $O(1)$
