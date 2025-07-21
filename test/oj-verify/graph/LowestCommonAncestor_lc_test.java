// verification-helper: PROBLEM https://judge.yosupo.jp/problem/lca
import java.util.Scanner;

public class LowestCommonAncestor_lc_test {
	public static void main(String[] args) {
		LowestCommonAncestor_lc_test obj = new LowestCommonAncestor_lc_test();
		obj.solve();
	}

	void solve() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int Q = sc.nextInt();
		LcaTree tree = new LcaTree(N);
		for (int i = 0; i < N - 1; i++) {
			int p = sc.nextInt();
			tree.addEdge(i + 1, p);
		}
		tree.build(0);
		StringBuilder out = new StringBuilder();
		for (int q = 0; q < Q; q++) {
			int u = sc.nextInt();
			int v = sc.nextInt();
			out.append(tree.lca(u, v));
			out.append('\n');
		}
		System.out.print(out.toString());
	}
}
