# procon-library

競技プログラミングのコンテストで個人的に使用しているJava用のテンプレートやライブラリを公開します。

よく使用するデータ構造やアルゴリズム、コンテスト用のテンプレートなどがあります。本家[ac-library](https://github.com/atcoder/ac-library/tree/master)からの移植も含みます(全て移植しているわけではありません)。

### data-structure
| ファイル名               | 内容                                 |
|:-------------------------|:-------------------------------------|
| UnionFind.java           | Union Find木                         |
| Compression.java         | 座標圧縮                             |
| FenwickTree.java         | Fenwick木                            |
| SegmentTree.java<br>IntSegmentTree.java<br>LongSegmentTree.java | セグメント木  |
| LazySegmentTree.java<br>IntLazySegmentTree.java<br>LongLazySegmentTree.java | 遅延評価セグメント木  |
| DynamicSegmentTree.java  | 動的セグメント木                     |
| Permutation.java         | 順列(順列全列挙含む)                 |

### graph
| ファイル名               | 内容                                 |
|:-------------------------|:-------------------------------------|
| MaxFlow.java             | 最大流・最小カット                   |
| MinCostFlow.java         | 最小費用流                           |
| LcaTree.java             | 最近共通祖先(LCA)                    |
| SccGraph.java            | 強連結成分分解(SCC)                  |
| TopologicalSort.java     | トポロジカルソート                   |
| TwoSat.java              | 2-SAT                                |
| WarshallFloyd.java       | Warshall-Floyd法 (全頂点間最短経路)  |

### math
| ファイル名               | 内容                                 |
|:-------------------------|:-------------------------------------|
| ModInt.java              | 剰余演算(四則演算、逆元、累乗、階乗、階乗の逆元、二項係数、順列) |
| MathUtil.java            | 中国剰余定理、Floor Sum、最大公約数  |
| Prime.java               | 素数関連(素数判定、素数列挙、素因数分解、約数列挙) |
| Matrix.java              | 行列演算(行列累乗含む)               |
| Vec.java                 | ベクトル演算                         |
| Convolution.java         | 畳み込み                             |

### string
| ファイル名               | 内容                                 |
|:-------------------------|:-------------------------------------|
| ZAlgorithm.java          | Z-Algorithm                          |
| SuffixArray.java         | Suffix Array                         |
| LcpArray.java            | LCP Array                            |

### template
| ファイル名               | 内容                                 |
|:-------------------------|:-------------------------------------|
| Main.java                | アルゴリズムコンテスト用の提出ファイルテンプレート(入出力ユーティリティ含む) |

### 今後整備予定
- 全方位木DP
- 巡回セールスマン問題
- ランレングス圧縮
- ビット演算関連
- LowLink

## ライセンス
このプロジェクトは[CC0 1.0 Universal](https://creativecommons.org/publicdomain/zero/1.0/legalcode)の下で公開しています。
