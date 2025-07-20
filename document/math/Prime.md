# 素数関連

素数判定や素因数分解等を行うライブラリ。

- 素数列挙
- 素数判定
- 素因数分解
- 約数列挙

参考サイト
- [ミラー・ラビンの素数判定法 (Miller-Rabin 法)](https://drken1215.hatenablog.com/entry/2023/05/23/233000)
- [128bit 整数型を使わない 64bit modint](https://yu212.hatenablog.com/entry/2023/12/14/203400)
- [素因数分解を <O(n), O(log(n)/log(log(n)))> で行う](https://rsk0315.hatenablog.com/entry/2023/05/03/133029) (※ただしO(log(n))にとどめた)


### コンストラクタ
```java
public Prime(int max)
```
`max`以下の素数を線形篩により事前計算する。
- 引数
  - `max` : 事前計算対象の最大値
- 計算量
  - $O(\mathrm{max})$

### 素数判定
```java
public boolean isPrime(long num)
public static boolean isPrimeByMillerRabin(long num)
```
素数かどうかを判定する。`isPrime()`で`num`が`max`以下の場合は事前計算した情報から判定する。`isPrime()`で`num`が`max`より大きい場合や、`isPrimeByMillerRabin()`の場合は、Miller-Rabin法により素数判定を行う。
- 引数
  - `num` : 判定対象の値
- 計算量
  - $O(1)$ (事前計算した情報から判定する場合)
  - $O((\log{\mathrm{num}})^3)$

### 素数リストの取得
```java
public int[] primes()
```
`max`以下の素数のリストを取得する。
- 計算量
  - $O(1)$

### 素因数分解
```java
public ArrayList<Prime.Factor> factorize(long num)
public class Factor {
  long base;
  int pow;
}
```
`num`を素因数分解する。$(1 \le \mathrm{num} \le \mathrm{max}^2)$の範囲で実行可能。

$(1 \le \mathrm{num} \le \mathrm{max})$の場合は各値について事前に求めておいた最小素因数を使用して素因数分解を行うため、$O(\log{(\mathrm{num})})$で実行可能。それ以上の場合は、素数を1つずつ割れるか試すため$O(\pi(\mathrm{max}) + \log{(\mathrm{num})})$となる。
- 引数
  - `num` : 素因数分解する値 $(1 \le \mathrm{num} \le \mathrm{max}^2)$
- 計算量
  - $O(\log{(\mathrm{num})})$: $(1 \le \mathrm{num} \le \mathrm{max})$
  - $O(\pi(\mathrm{max}) + \log{(\mathrm{num})})$: $(\mathrm{max} \le \mathrm{num} \le \mathrm{max}^2)$

### 約数列挙
```java
public long[] divisors(long num)
```
`num`の約数を全て列挙する。結果は昇順とは限らない。素因数分解を行なってから約数を列挙する。
- 引数
  - `num` : 約数列挙する値 $(1 \le \mathrm{num} \le \mathrm{max}^2)$
- 計算量
  - 素因数分解の計算量： `factorize()`参照
  - 約数列挙の計算量: $O(d)$ ($d$は約数の個数)
