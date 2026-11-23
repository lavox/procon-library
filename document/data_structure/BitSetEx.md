# BitSetEx

`java.util.BitSet`をベースに、固定長のビット集合とシフト操作を実装したクラス。
以下では、ビット数を $n$、内部の64ビットワード数を $w = \lceil n / 64 \rceil$ とする。

`java.util.BitSet`と共通するメソッドの仕様は省略する。ただし、以下の違いがある。

- `new BitSetEx(n)`は固定長 $n$ の空集合を作成する。自動拡張は行わず、変更・取得は有効範囲内で行うこと。
- `length()`と`size()`は、ともに固定長 $n$ を返す。
- `valueOf(long[])`は配列長の64倍をビット数とし、`toLongArray()`は末尾のゼロワードも含めたコピーを返す。
- `equals`はビットの値に加えてビット数も比較する。
- サイズの異なる集合との論理演算では、自身のサイズを維持する。相手の範囲外は0として扱い、自身の範囲外の結果は切り捨てる。
- `nextSetBit`と`nextClearBit`は $n$ 未満を探索し、見つからなければ`-1`を返す。開始位置が $n$ 以上の場合も`-1`を返す。後方探索の開始位置は $n$ 未満とし、負の場合は`-1`を返す。

### 左シフト
```java
public BitSetEx leftShift(int bits)
public void leftShiftAsn(int bits)
```
ビット位置 $i$ を $i + bits$ に移した、サイズ $n$ の集合を返す。`leftShift(bits)` は元の集合は変更せずに新しい `BitSetEx` を返し、 `leftShiftAsn(bits)` は元の集合を置き換える。
`bit == 0` は元の集合と同じ集合を、`bits >= n` では全ビットを0とする。

- 引数
  - `bits` : シフト量 $(0 \le bits)$
- 計算量
  - $O(w)$

### 左シフトとのORによる更新
```java
public void orLeftShiftAsn(int bits)
```
更新前の自身と、その左シフト結果とのORで自身を更新する。`or(leftShift(bits))`と同じ結果を、シフト結果の集合を生成せずに求める。サイズは変わらない。
`bits == 0`または`bits >= n`では変更しない。

- 引数
  - `bits` : シフト量 $(0 \le bits)$
- 計算量
  - $O(w)$

### 右シフト
```java
public BitSetEx rightShift(int bits)
public void rightShiftAsn(int bits)
```
ビット位置 $i$ を $i - bits$ に移した、サイズ $n$ の集合を返す。`rightShift(bits)` は元の集合は変更せずに新しい `BitSetEx` を返し、 `rightShiftAsn(bits)` は元の集合を置き換える。
`bit == 0` は元の集合と同じ集合を、`bits >= n` では全ビットを0とする。

- 引数
  - `bits` : シフト量 $(0 \le bits)$
- 計算量
  - $O(w)$

### 右シフトとのORによる更新
```java
public void orRightShiftAsn(int bits)
```
更新前の自身と、その右シフト結果とのORで自身を更新する。`or(rightShift(bits))`と同じ結果を、シフト結果の集合を生成せずに求める。サイズは変わらない。
`bits == 0`または`bits >= n`では変更しない。

- 引数
  - `bits` : シフト量 $(0 \le bits)$
- 計算量
  - $O(w)$

### 部分集合の判定
```java
public boolean isSubsetOf(BitSetEx set)
```
自身が`set`の部分集合であれば`true`を返す。自身で1になっているすべてのビットが、`set`でも1になっているかを判定する。同じ集合の場合も`true`となり、どちらの集合も変更しない。
サイズが異なる場合は、範囲外のビットを0として扱う。自身が空集合の場合は、相手によらず`true`を返す。

- 引数
  - `set` : 包含先の集合
- 計算量
  - $O(w)$

## 検証
- [AWC0201E 荷物の運搬 (AtCoder)](https://atcoder.jp/contests/awc0201/submissions/81047361)
