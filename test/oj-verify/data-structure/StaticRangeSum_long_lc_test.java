// verification-helper: PROBLEM https://judge.yosupo.jp/problem/static_range_sum
import java.util.Scanner;

public class StaticRangeSum_long_lc_test {
	public static void main(String[] args) {
		StaticRangeSum_long_lc_test obj = new StaticRangeSum_long_lc_test();
		obj.solve();
	}

	void solve() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int Q = sc.nextInt();
		long[] a = new long[N];
		for (int i = 0; i < N; i++) {
			a[i] = sc.nextLong();
		}
		int[] l = new int[Q];
		int[] r = new int[Q];
		for (int q = 0; q < Q; q++) {
			l[q] = sc.nextInt();
			r[q] = sc.nextInt();
		}
		LongSegmentTree tree = new LongSegmentTree(N, (x, y) -> x + y, 0L);
		tree.initData(a);
		StringBuilder out = new StringBuilder();
		for (int q = 0; q < Q; q++) {
			out.append(tree.query(l[q], r[q]));
			out.append('\n');
		}
		System.out.print(out.toString());
	}
}
