# 形式的冪級数(FPS)

形式的冪級数に関する各種演算を実行する。[NyaanさんのC++ライブラリ](https://nyaannyaan.github.io/library/)のJavaへの移植。
- 形式的冪級数を表す抽象クラス`FormalPowerSeries`
- NTT-friendlyな素数向けの実装クラス`NTTFriendlyFPS` ※一般の法向けのクラスは未実装
- 数論変換用クラス`NTT`

## `FormalPowerSeries`, `NTTFriendlyFPS`
NTT-friendlyな素数の場合は、`NTTFriendlyFPS`クラスのインスタンスを生成して利用する。

### コンストラクタ
```java
public NTTFriendlyFPS(int n, ModOperation mop, NTT ntt)
public NTTFriendlyFPS(int n, int capacity, ModOperation mop, NTT ntt)
public NTTFriendlyFPS(int[] t, ModOperation mop, NTT ntt)
public NTTFriendlyFPS(int[] t, int size, int capacity, ModOperation mop, NTT ntt)
public NTTFriendlyFPS(FormalPowerSeries from)
public NTTFriendlyFPS(FormalPowerSeries from, int size, int capacity)
public FormalPowerSeries createNewSeries(int n);
public FormalPowerSeries createNewSeries(int n, int capacity);
public FormalPowerSeries createNewSeries(int[] t);
public FormalPowerSeries createNewSeries(int[] t, int size, int capacity);
public FormalPowerSeries clone();
public FormalPowerSeries clone(int size, int capacity);
```
初回に生成する場合はコンストラクタ`public NTTFriendlyNPS()`を使用する。インスタンスがある場合は、`createNewSeries()`により、同クラスのインスタンスの生成が可能。
- 引数
  - `n` : 最大次数+1の値。つまり`n`を指定すると`n-1`次までの冪級数を扱える
  - `capacity` : 内部で保持する配列の長さ。 $(n \le \mathrm{capacity})$
  - `t` : 初期配列
  - `size` : 最大次数+1の値
  - `mop` : 法に対応する`ModOperation`インスタンス
  - `ntt` : 法に対応する`NTT`インスタンス
  - `from` : コピーしてインスタンスを生成する場合のコピー元
- 計算量
  - $O(\mathrm{capacity})$

### 基本操作
```java
public int get(int i) // i次の項の係数を取得
public void set(int i, int v) // i次の項の係数を設定
public int size() // 最大次数 + 1の値を取得
public boolean empty() // 長さ0の冪級数かどうかを判定
public void clear() // 長さ0の冪級数にする
public void clear(int start, int end) // i次の項を0に設定 (start <= i < end)
public void resize(int len) // 冪級数をlen - 1次に拡張
public void append(int v) // 現在の最大次数の1次上に係数vの項を追加
public void ensureCapacity(int minLen) // 長さminLenの配列容量を確保
public int[] toArray() // 係数配列の取得
```

### 冪級数の四則演算・剰余
```java
public FormalPowerSeries add(FormalPowerSeries r)
public FormalPowerSeries add(int v)
public FormalPowerSeries addAsn(FormalPowerSeries r)
public FormalPowerSeries addAsn(int v)
public FormalPowerSeries sub(FormalPowerSeries r)
public FormalPowerSeries sub(int v)
public FormalPowerSeries subAsn(FormalPowerSeries r)
public FormalPowerSeries subAsn(int v)
public FormalPowerSeries minus()
public FormalPowerSeries mul(FormalPowerSeries r)
public FormalPowerSeries mul(int v)
public FormalPowerSeries mulAsn(FormalPowerSeries r)
public FormalPowerSeries mulAsn(int v)
public FormalPowerSeries div(FormalPowerSeries r)
public FormalPowerSeries divAsn(FormalPowerSeries r)
public FormalPowerSeries mod(FormalPowerSeries r)
public FormalPowerSeries modAsn(FormalPowerSeries r)
```
四則演算、剰余演算を行う。`Asn`付きのメソッドはインスタンス自体を書き換えて返却する。`Asn`なしのメソッドは新たなインスタンスを生成し、演算結果を返却する。
- 引数
  - `r` : 四則演算対象の冪級数。
  - `v` : 定数(0次式)
- 計算量
  - 加算・減算 $O(\mathrm{size})$ (冪級数)、$O(1)$ (定数)
  - 乗算・除算・剰余 $O(N\log{N})$ (冪級数)、$O(\mathrm{size})$ (定数) ※ $N$は次数の和

### 反転・ドット積・低次項の取得・シフト
```java
public void shrink() // 最高次数が非0となるように、高次の項を削除する
public FormalPowerSeries rev() // 0次〜size-1次の係数を、size-1次〜0次に入れ替える
public FormalPowerSeries revAsn() // rev()のinplace版
public FormalPowerSeries dot(FormalPowerSeries r) // ドット積(項ごとの積)
public FormalPowerSeries pre(int sz) // sz - 1次の項まで取得(足りない場合は0埋め)
public FormalPowerSeries preAsn(int sz) // pre()のinplace版
public FormalPowerSeries rshift(int sz) // 右シフトし、size - sz項の冪級数にする
public FormalPowerSeries rshiftAsn(int sz) // rshift()のinplace版
public FormalPowerSeries lshift(int sz) // 左シフトし、size + sz項の冪級数にする。低次の項は0埋め
public FormalPowerSeries lshiftAsn(int sz) // lshift()のinplace版
```
- 計算量
  - shrink, rev, revAsn, dot: $O(\mathrm{size})$
  - pre, preAsn: $O(\mathrm{sz})$
  - rshift, rshiftAsn: $O(\mathrm{size - sz})$
  - lshift, lshiftAsn: $O(\mathrm{size + sz})$

### 微積分
```java
public FormalPowerSeries diff()
public FormalPowerSeries diffAsn()
public FormalPowerSeries integral()
public FormalPowerSeries integralAsn()
```
- 計算量
  - $O(\mathrm{size})$

### 代入
```java
public int eval(int x)
```
形式的冪級数に$x$を代入した値を求める。
- 引数
  - `x` : 代入する値
- 計算量
  - $O(\mathrm{size})$

### 累乗
```java
public FormalPowerSeries pow(long k)
public FormalPowerSeries pow(long k, int deg)
```
- 引数
  - `k` : 冪
  - `deg` : 計算対象の最大次数 - 1。無指定の場合は、元と同じ次数まで計算する
- 計算量
  - $O(\mathrm{deg}\log\mathrm{deg})$

### 指数・対数
```java
public FormalPowerSeries log()
public FormalPowerSeries log(int deg)
public FormalPowerSeries exp()
public abstract FormalPowerSeries exp(int deg)
```
- 引数
  - `deg` : 計算対象の最大次数 - 1。無指定の場合は、元と同じ次数まで計算する
- 計算量
  - $O(\mathrm{deg}\log\mathrm{deg})$

### 逆元
```java
public FormalPowerSeries inv()
public FormalPowerSeries inv(int deg)
```
$fg \equiv 1 (\mod x^n)$ となる$g$を求める
- 引数
  - `deg` : 計算対象の最大次数 - 1。無指定の場合は、元と同じ次数まで計算する
- 計算量
  - $O(\mathrm{deg}\log\mathrm{deg})$

## NTTクラス

### コンストラクタ
```java
public NTT(ModOperation mop)
```
- 引数
  - `mop` : 法に対応する`ModOperation`インスタンス
- 計算量
  - $O(\log(\mathrm{mod}))$

## 検証
- [Inv of Formal Power Series (Library Checker)](https://judge.yosupo.jp/submission/346894)
- [Exp of Formal Power Series (Library Checker)](https://judge.yosupo.jp/submission/346895)
- [Log of Formal Power Series (Library Checker)](https://judge.yosupo.jp/submission/346896)
- [Pow of Formal Power Series (Library Checker)](https://judge.yosupo.jp/submission/346897)
