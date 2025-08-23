# ローリングハッシュ
指定区間のローリングハッシュを求める。`int`型配列、文字列、`i`番目の値を返す`IntUnaryOperator`に対して実行可能。

- 参考サイト
  - [安全で爆速なRollingHashの話](https://qiita.com/keymoon/items/11fac5627672a6d6a9f6)
  - [Rolling Hashで基数に原始根を使う【新歓ブログリレー2020 36日目】](https://trap.jp/post/1036/)

### インスタンス生成
```java
public static RollingHash createWithBase(int n, long b, long m)
public static RollingHash create(int n, long m)
public static RollingHash create(int n, long m, int maxVal)
```
RollingHashを求めるためのインスタンスを生成する。以下の3パターンの生成方法を提供する。
- 基数`b`と素数`m`を指定
- 素数`m`を指定し、基数`b`は自動設定(基数は $2\times 10^6$ より大きい値を自動設定する)
- 素数`m`を指定し、基数`b`は自動設定(基数は指定した最大値`maxVal`より大きい値を自動設定する)
基数を自動設定する場合は、`m`として以下のいずれかを使用すること。
```java
public static final long MOD1L61 = (1L << 61) - 1;
public static final long MOD998244353 = 998244353;
public static final long MOD1000000007 = 1000000007;
public static final long MOD1000000009 = 1000000009;
public static final long MOD1000000021 = 1000000021;
public static final long MOD1000000033 = 1000000033;
public static final long MOD1000000087 = 1000000087;
```
また、コンストラクタの直接呼び出しは行わないこと。
- 引数
  - `n` : ローリングハッシュを求める区間の最大の長さ
  - `b` : ハッシュ計算に使用する基数の値
  - `m` : ハッシュ計算に使用する素数の値。`maxVal`より大きい値を指定すること。定数で指定した値以外の場合は、 $2^{31}-1$ 以下の値を使用すること
  - `maxVal` : 文字列(または配列)の値として使用される値の最大値
- 計算量
  - $O(n)$
  - 基数を自動設定する場合は、乱数で`m`の原始根かつ`maxVal`以上になるように設定するためリトライを行うが、平均3回、最大でも数十回程度のため通常は無視できる

### 文字列・配列の追加
```java
public void add(String s)
public void add(int[] array)
public void add(IntUnaryOperator op, int len)
public void add(int v)
```
文字列や配列を追加する。`n`を超えた場合は、最後から`n`文字のローリングハッシュが求められるように残して先頭の文字は削除される。
- 引数
  - `s` : 追加する文字列。各文字を`char`変換した値を追加する
  - `array` : 追加する`int`配列
  - `op` : `i`番目の値を返す関数
  - `len` : 追加する長さ
  - `v` : 追加する値(1つの値を追加する場合)
- 計算量
  - $O(\mathrm{len})$

### ローリングハッシュの値の取得
```java
public long hash(int i0, int len) 
```
指定した区間のローリングハッシュの値の取得
- 引数
  - `i0` : 区間の先頭位置
  - `len` : 区間の長さ
- 計算量
  - $O(1)$

## 検証
- [ABC141E Who Says a Pun? (AtCoder)](https://atcoder.jp/contests/abc141/submissions/68052704) (MOD998244353)
- [ABC141E Who Says a Pun? (AtCoder)](https://atcoder.jp/contests/abc141/submissions/68052651) (MOD1L61)
