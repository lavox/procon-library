// verification-helper: PROBLEM https://judge.yosupo.jp/problem/unionfind
import java.util.Scanner;

public class Unionfind_lc_test {
	public static void main(String[] args) {
		Unionfind_lc_test obj = new Unionfind_lc_test();
		obj.solve();
	}

	void solve() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int Q = sc.nextInt();
		UnionFind uf = new UnionFind(N);
		StringBuilder out = new StringBuilder();
		for (int q = 0; q < Q; q++) {
			int t = sc.nextInt();
			int u = sc.nextInt();
			int v = sc.nextInt();
			if ( t == 0 ) {
				uf.unite(u, v);
			} else {
				out.append(uf.isSame(u, v) ? 1 : 0);
				out.append('\n');
			}
		}
		System.out.print(out.toString());
	}
}
