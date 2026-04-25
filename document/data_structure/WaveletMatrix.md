# Wavelet Matrix
静的な非負整数列 $A=(A_0, A_1, \dots, A_{N - 1})$ に対して、数列の区間に対して値の大小に関するクエリを高速に処理可能なデータ構造。以下の機能を提供。

- `quantile(l, r, k)`: $A_l, A_{l + 1}, \dots, A_{r - 1}$ で、$k$ 番目に小さい要素を求める
- `rangeFreq(l, r, vmin, vmax)`: $A_l, A_{l + 1}, \dots, A_{r - 1}$ で、`vmin` 以上 `vmax` 未満の要素の個数を求める
- `rangeFreqBelow(l, r, vmax)`: $A_l, A_{l + 1}, \dots, A_{r - 1}$ で、`vmax` 未満の要素の個数を求める
- `rangeFreqAbove(l, r, vmin)`: $A_l, A_{l + 1}, \dots, A_{r - 1}$ で、`vmin` 以上の要素の個数を求める
- `prevValue(l, r, vmax)`: $A_l, A_{l + 1}, \dots, A_{r - 1}$ で、`vmax` 未満で最も大きい要素を求める
- `nextValue(l, r, vmin)`: $A_l, A_{l + 1}, \dots, A_{r - 1}$ で、`vmin` 以上で最も小さい要素を求める
- `access(i)`: $i$ 番目の要素を求める

本クラスにおいて、高さ`h`は、最下位ビットを0としたときのビット位置に対応する階層を表す。

## `WaveletMatrix` クラス
### コンストラクタ
```java
public WaveletMatrix(int[] arr, int height)
public WaveletMatrix(int[] arr)
```
- 引数
  - `arr` : 対象となる非負整数配列
  - `height` : 構築するWavelet Matrixの高さ。省略した場合は、`arr`に含まれる最も大きい値のビット長が使用される。
- 計算量
  - $O(n\cdot\mathrm{height})$

### $k$ 番目に小さい要素の取得
```java
public int quantile(int l, int r, int k)
```
`[l, r)`で、$k$ 番目に小さい要素を取得する。
- 引数
  - `l`, `r` : 対象とする数列の範囲
  - `k` : 何番目に小さい要素を取得するか。0-indexで指定する。
- 計算量
  - $O(\mathrm{height})$

### 指定した値範囲の要素数の取得
```java
public int rangeFreq(int l, int r, int vmin, int vmax)
public int rangeFreqBelow(int l, int r, int vmax)
public int rangeFreqAbove(int l, int r, int vmin)
```
`[l, r)`で、`vmin`以上`vmax`未満の要素数を取得する。
- 引数
  - `l`, `r` : 対象とする数列の範囲
  - `vmin`, `vmax` : 対象とする値の範囲。 $\mathrm{vmin}\le x \lt \mathrm{vmax}$ を満たす $x$ の個数を求める。`rangeFreqBelow()`では`vmax`のみ、`rangeFreqAbove()`では`vmin`のみを指定する。
- 計算量
  - $O(\mathrm{height})$

### 指定した値の前後の値の取得
```java
public int prevValue(int l, int r, int vmax)
public int nextValue(int l, int r, int vmin)
```
`[l, r)`で、`vmax`未満で最も大きい値、`vmin`以上で最も小さい値を取得する。存在しない場合は-1を返す。
- 引数
  - `l`, `r` : 対象とする数列の範囲
  - `vmin`, `vmax` : 基準とする値。`prevValue()`では $x \lt \mathrm{vmax}$ を満たす最大の $x$ を、`nextValue()`では $\mathrm{vmin} \le x$ を満たす最小の $x$ を求める。
- 計算量
  - $O(\mathrm{height})$

### 高さ $h$ の $i$ 番目のビットの取得
```java
public int get(int h, int i)
```
高さ $h$ の $i$ 番目のビットを取得する。
- 引数
  - `h` : Wavelet Matrixにおける高さ $(0\le h\lt\mathrm{htight})$
  - `i` : `h`ビット目に対応するBit Vectorの何番目のビットを取得するか $(0\le i\lt N)$
- 計算量
  - $O(1)$

### 高さ $h$ の $i$ 番目より前にある0/1のビットの個数
```java
public int rank0(int h, int i)
public int rank1(int h, int i)
```
高さ $h$ の $i$ 番目のビットより前にある0や1のビットの個数。`rank0()`は0のビットの個数、`rank1()`は1のビットの個数を取得する。
- 引数
  - `h` : Wavelet Matrixにおける高さ $(0\le h\lt\mathrm{htight})$
  - `i` : `h`ビット目に対応するBit Vectorの何番目のビットより前のビット個数を取得するか $(0\le i\lt N)$
- 計算量
  - $O(1)$

### 高さ $h$ の $i$ 番目のビットの次の階層のインデックス取得
```java
public int next_i0(int h, int i)
public int next_i1(int h, int i)
```
高さ $h$ の $i$ 番目のビットが、高さ $h - 1$において何番目に対応するかを取得する。`next_i0()`は左側に進んだ場合、`next_i1()`は右側に進んだ場合の位置を取得する。
- 引数
  - `h` : Wavelet Matrixにおける高さ $(0\le h\lt\mathrm{htight})$
  - `i` : `h`ビット目に対応するBit Vectorの何番目のビットに対応する位置を取得するか $(0\le i\lt N)$
- 計算量
  - $O(1)$

### Wavelet Matrix探索用の範囲オブジェクトの生成
```java
public WaveletMatrix.Range createRange(int l, int r)
```
高さ $\mathrm{height} - 1$ の `[l, r)`の範囲に対応する`Range`オブジェクトを取得する。
- 引数
  - `l`, `r` : 対象とする数列の範囲
- 計算量
  - $O(1)$

## `WaveletMatrix.Range` クラス
Wavelet Matrixの階層を1つずつ下りながら探索を行うための補助クラス。
### 区間の取得
```java
public int l()
public int r()
public int len()
```
このオブジェクトが指す区間の下限・上限と長さを取得する。
- 計算量
  - $O(1)$

### 次の区間の取得
```java
public int next_l0()
public int next_r0()
public int next_l1()
public int next_r1()
public int next_len0()
public int next_len1()
```
このオブジェクトが指す区間に対し、次の高さに進んだ場合の下限・上限と長さを取得する。`0`がつくメソッドは左側に進んだ場合、`1`がつくメソッドは右に進んだ場合に対応する。
- 計算量
  - $O(1)$

### 次の区間に進む
```java
public void move0()
public void move1()
```
次の高さに進む。`move0()`は左に、`move1()`は右に進む。
- 計算量
  - $O(1)$

## 検証
- [Range Kth Smallest (Library Checker)](https://judge.yosupo.jp/submission/368569) (quantile)
- [Range Nearest Query (yukicoder)](https://yukicoder.me/submissions/1161651) (prevValue, nextValue)


