# procon-library

競技プログラミングのコンテストで個人的に使用しているJava用のテンプレートやライブラリを公開します。

よく使用するデータ構造やアルゴリズム、コンテスト用のテンプレートなどがあります。本家[ac-library](https://github.com/atcoder/ac-library/tree/master)からの移植も含みます(全て移植しているわけではありません)。

## ライブラリ・テンプレート
### data-structure
| ファイル名               | 内容                                 |
|:-------------------------|:-------------------------------------|
| UnionFind.java<br>WeightedUnionFind.java | Union Find木<br>ポテンシャル付きUnion Find木 |
| Compression.java         | 座標圧縮                             |
| FenwickTree.java         | Fenwick木                            |
| SegmentTree.java<br>IntSegmentTree.java<br>LongSegmentTree.java | セグメント木  |
| LazySegmentTree.java<br>IntLazySegmentTree.java<br>LongLazySegmentTree.java | 遅延評価セグメント木  |
| DynamicSegmentTree.java  | 動的セグメント木                     |
| Permutation.java         | 順列(順列全列挙含む)                 |
| RunLengthCompression.java| ランレングス圧縮                   |

### graph
| ファイル名               | 内容                                 |
|:-------------------------|:-------------------------------------|
| Graph.java<br>Edge.java  | グラフ基本クラス                     |
| Dfs.java                 | 深さ優先探索                         |
| MaxFlow.java             | 最大流・最小カット                   |
| MinCostFlow.java         | 最小費用流                           |
| Lca.java                 | 最近共通祖先(LCA)                    |
| SccGraph.java            | 強連結成分分解(SCC)                  |
| Rerooting.java<br>LongRerooting.java | 全方位木DP               |
| TopologicalSort.java     | トポロジカルソート                   |
| TwoSat.java              | 2-SAT                                |
| Tsp.java                 | 巡回セールスマン問題                 |
| WarshallFloyd.java       | Warshall-Floyd法 (全頂点間最短経路)  |
| LowLink.java             | Low Link(関節点・橋の検出)           |

### math
| ファイル名               | 内容                                 |
|:-------------------------|:-------------------------------------|
| ModInt.java              | 剰余演算(四則演算、逆元、累乗、階乗、階乗の逆元、二項係数、順列) |
| MathUtil.java            | 中国剰余定理、Floor Sum、最大公約数  |
| Prime.java               | 素数関連(素数判定、素数列挙、素因数分解、約数列挙) |
| Matrix.java              | 行列演算(行列累乗含む)               |
| Vec.java                 | ベクトル演算                         |
| Convolution.java         | 畳み込み                             |
| FormalPowerSeries.java<br>NTTFriendlyFPS.java<br>NTT.java | 形式的冪級数(FPS)、数論変換(NTT)  |

### geometry
| ファイル名               | 内容                                 |
|:-------------------------|:-------------------------------------|
| Vec2L.java               | 2次元ベクトル(ドット積、偏角ソート等) |

### string
| ファイル名               | 内容                                 |
|:-------------------------|:-------------------------------------|
| ZAlgorithm.java          | Z-Algorithm                          |
| SuffixArray.java         | Suffix Array                         |
| LcpArray.java            | LCP Array                            |
| RollingHash.java         | ローリングハッシュ                   |
| AhoCorasick.java         | Aho-Corasick法                       |

### primitive
| ファイル名               | 内容                                 |
|:-------------------------|:-------------------------------------|
| IntArrayList.java        | int版ArrayList                       |
| IntArrays.java           | Comparotorを使用したintのソート      |
| IntComparator.java       | int版Comparator                      |
| LongArrayList.java       | long版ArrayList                      |
| LongArrays.java          | Comparotorを使用したlongのソート     |
| LongComparator.java      | long版Comparator                     |

### template
| ファイル名               | 内容                                 |
|:-------------------------|:-------------------------------------|
| Main.java                | アルゴリズムコンテスト用の提出ファイルテンプレート(入出力ユーティリティ含む) |

### 今後整備予定
- ビット演算関連

## ツール
- ライブラリマージツール

詳細はtools配下のREADME.mdを参照。

## 注意事項
- 本ライブラリ・テンプレート・ツールはある程度の検証はしていますが、正しい動作を保証するものではありません。これらの使用によって生じたいかなる損害・不具合についても、作者は一切の責任を負いません。
- 仕様は予告なく変更する場合があります。

## ライセンス
このプロジェクトは[CC0 1.0 Universal](https://creativecommons.org/publicdomain/zero/1.0/legalcode)の下で公開しています。
