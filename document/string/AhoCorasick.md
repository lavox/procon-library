# Aho-Corasick法
Aho-Corasick法によるTrie および failure link, output linkを構築する。文字列だけでなく、int配列にも適用可能。

## AhoCorasickクラス
### インスタンス生成
```java
public AhoCorasick(int K)
public AhoCorasick(int K, char base)
```
インスタンスを生成する。英小文字だけの場合は`new AhoCorasick(26, 'a')`のように生成する。
- 引数
  - `K` : 使用する文字や数字の種類の上限
  - `base` : 文字列を管理する場合に、起点とする文字。
    - `addWord(String, int)`で追加する文字列は、`[base, base + K)`の文字で構成されている必要がある。
    - `addWord(int[], int)`で追加する配列は、`[0, K)`の値で構成されている必要がある。
- 計算量
  - $O(1)$

### 単語・配列の追加
```java
public Node addWord(String word, int wordId)
public Node addWord(int[] word, int wordId)
public Node addWord(IntUnaryOperator op, int n, int wordId)
```
検索対象となる単語や配列を追加する。同一の単語を追加した場合は、`wordId`は最後に追加したものが優先する。
- 引数
  - `word` : 追加する文字列や配列
  - `wordId` : 追加する文字列や配列のID。0以上の任意の番号を指定可能。その単語を表す`Node`にここで指定した`wordId`が設定される
  - `op` : `i`番目の値を返す関数
  - `n` : 単語の長さ
- 計算量
  - ならし $O(nK)$

### failure link, output linkの構築
```java
public void buildLink()
```
failure link, output linkを構築する
- 計算量
  - $O(WK)$ ($W$ は追加した単語の文字数の和)

### ノード数の取得
```java
public int nodeSize()
```
ノード数を取得する
- 計算量
  - $O(1)$

### ノードの取得
```java
public Node node(int id)
```
ノードを取得する。なお`id=0`はルートノードに対応する。
- 引数
  - `id` : ノードのID $(0\le\mathrm{id}\lt\mathrm{nodeSize()})$
- 計算量
  - $O(1)$

### ルートノードの取得
```java
public Node root()
```
ルートノードを取得する
- 計算量
  - $O(1)$

## AhoCorasick.Nodeクラス
### 親ノードの取得
```java
public Node parent()
```
このノードの親ノードを取得する
- 計算量
  - $O(1)$

### ノードIDの取得
```java
public int id()
```
このノードのノードIDを取得する。0以上`nodeSize()`未満の値を返却する。
- 計算量
  - $O(1)$

### ノードが表す文字(1文字)の取得
```java
public int val()
```
このノードが表す文字(1文字分)を取得する。0以上`K`未満の値を返却する。
- 計算量
  - $O(1)$

### ノードが表す文字列・配列の取得
```java
public int[] wordIntArray()
public String word()
```
このノードが表す文字列・配列を取得する。
- 計算量
  - $O(n)$ ($n$ は文字列・配列の長さ)

### failure linkの取得
```java
public Node failure()
```
このノードのsuffixであって、このノードより短い文字列を表すノード(failure link)を取得する。
- 計算量
  - $O(1)$

### 次のノードの取得
```java
public Node next(int c)
```
このノードから文字`c`で遷移した先のノードを取得する。自動的にfailure linkをたどって適切なノードを返却する(何も存在しない場合はルートノードを返す)。
- 計算量
  - $O(1)$

### 追加した単語を表すノードかどうかの判定
```java
public boolean isOutput()
```
このノードが追加した単語を表すノードかどうかを判定する。
- 計算量
  - $O(1)$

### 追加した単語を表すノードIDの取得
```java
public int wordId()
```
このノードが追加した単語を表す場合に、その`wordId`を取得する。追加した単語でない場合は`-1`を返す。
- 計算量
  - $O(1)$

### 追加した単語をsuffixとして含むかどうかの判定
```java
public boolean hasOutput()
```
このノードが追加した単語をsuffixとして含むかどうかを判定する。このノードが`"abcd"`の場合、例えば`"cd"`が追加した単語である場合に`true`を返す。
- 計算量
  - $O(1)$

### suffixとして含まれる追加した単語一覧の取得
```java
public ArrayList<Node> outputWords()
```
このノードが追加した単語をsuffixとして含む場合に、そのすべてを取得する。存在しない場合は`null`を返却する。
- 計算量
  - $O(m)$ ($m$ はsuffixとして含む追加した単語)

### suffixとして含まれる追加した単語の取得
```java
public Node nextOutput()
```
このノードが追加した単語をsuffixとして含む場合に、この単語を除いて最も長い単語を取得する。存在しない場合は`null`を返却する。
- 計算量
  - $O(1)$

## 検証
- [Aho Corasick (Library Checker)](https://judge.yosupo.jp/submission/311406)
- [ABC419F All Included (AtCoder)](https://atcoder.jp/contests/abc419/submissions/68963541)
- [ABC268Ex Taboo (AtCoder)](https://atcoder.jp/contests/abc268/submissions/68963502)
