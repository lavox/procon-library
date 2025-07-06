// verification-helper: PROBLEM https://judge.yosupo.jp/problem/staticrmq
import java.util.Scanner;

public class StaticRMQ_int_lc_test {
	public static void main(String[] args) {
		StaticRMQ_int_lc_test obj = new StaticRMQ_int_lc_test();
		obj.solve();
	}

	void solve() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int Q = sc.nextInt();
		int[] a = new int[N];
		for (int i = 0; i < N; i++) {
			a[i] = sc.nextInt();
		}
		int[] l = new int[Q];
		int[] r = new int[Q];
		for (int q = 0; q < Q; q++) {
			l[q] = sc.nextInt();
			r[q] = sc.nextInt();
		}
		IntSegmentTree tree = new IntSegmentTree(N, (x, y) -> Math.min(x, y), Integer.MAX_VALUE);
		tree.initData(a);
		StringBuilder out = new StringBuilder();
		for (int q = 0; q < Q; q++) {
			out.append(tree.query(l[q], r[q]));
			out.append('\n');
		}
		System.out.print(out.toString());
	}
}
