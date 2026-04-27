# Mo's algorithm
Mo's algorithmにおいて、クエリを管理するクラス。
`Comparable<MoQuery>`インターフェースを実装しており、この順序でソートすることでクエリの対象区間の移動距離が $O((N + Q)\sqrt{N})$ に収まる順序に並び替えることができる。

- 使用方法
  - クエリに区間以外の付加情報がある場合は、本クラスを継承したクラスを作成
  - クエリごとにインスタンスを生成し、配列(または`List`等)に格納
  - 配列に格納されたクエリをソート
  - ソートされた順に`l`, `r`の位置を伸縮させてクエリを処理

### コンストラクタ
```java
public MoQuery(int id, int l, int r, SqrtDecomposition sqd)
```
- 引数
  - `id` : クエリに一意な番号 (回答を元の順序に戻す際に使用することを想定)
  - `l`, `r` : クエリの対象区間`[l, r)` (半開区間を想定) $(0\le l, r \lt sqd.N)$
  - `sqd` : `SqrtDecomposition`インスタンス
- 計算量
  - $O(1)$

### インスタンス変数
```java
public final int id;     // クエリ番号
public final int l;      // クエリ区間の左端
public final int r;      // クエリ区間の右端
public final int l_bid;  // クエリ区間の左端のバケット番号
```

## 検証
- [AWC0040E 社内ランキング (AtCoder)](https://atcoder.jp/contests/awc0040/submissions/75317812)
