# procon-library

競技プログラミングのコンテストで個人的に使用しているJava用のテンプレートやライブラリを公開します。

よく使用するデータ構造やアルゴリズム、コンテスト用のテンプレートなどがあります。本家[ac-library](https://github.com/atcoder/ac-library/tree/master)からの移植も含みます(全て移植しているわけではありません)。

- data-structure
  - UnionFind.java Union Find木
  - Compression.java 座標圧縮
  - SegmentTree.java, IntSegmentTree.java, LongSegmentTree.java セグメント木
  - LazySegmentTree.java, IntLazySegmentTree.java, LongLazySegmentTree.java 遅延評価セグメント木
  - DynamicSegmentTree.java 動的セグメント木
- math
  - ModInt.java 剰余演算(四則演算、逆元、累乗、階乗、階乗の逆元、二項係数、順列)
  - Matrix.java 行列演算(行列累乗含む)
  - Vec.java ベクトル演算
- string
  - ZAlgorithm.java Z-Algorithm
  - SuffixArray.java Suffix Array
  - LcpArray.java LCP Array
- template
  - Main.java アルゴリズムコンテスト用の提出ファイルテンプレート(入出力ユーティリティ含む)

### 今後整備予定
- 最大流(Dinic)
- 最小費用流
- 最近共通祖先(LCA)
- 強連結分解
- トポロジカルソート
- 2-SAT
- Warshall-Floyd
- 全方位木DP
- 巡回セールスマン問題
- ランレングス圧縮
- 順列列挙
- 中国剰余定理
- 素数関連
- ビット演算関連
- LowLink
- 高速フーリエ変換
- 高速剰余変換
- 畳み込み

## ライセンス
このプロジェクトは[CC0 1.0 Universal](https://creativecommons.org/publicdomain/zero/1.0/legalcode)の下で公開しています。
