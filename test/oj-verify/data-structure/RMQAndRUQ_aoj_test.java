// verification-helper: PROBLEM https://onlinejudge.u-aizu.ac.jp/courses/library/3/DSL/all/DSL_2_F
import java.util.Scanner;

public class RMQAndRUQ_aoj_test {
	public static void main(String[] args) {
		RMQAndRUQ_aoj_test obj = new RMQAndRUQ_aoj_test();
		obj.solve();
	}

	void solve() {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int q = sc.nextInt();
		LazySegmentTree<Integer, Integer> tree = new LazySegmentTree<>(
			n,
			new LazyOperator<>() {
				public Integer op(Integer a, Integer b) { return Math.min(a, b); }
				public Integer e() { return Integer.MAX_VALUE; }
				public Integer mapping(Integer x, Integer a, int len) { return x == Integer.MAX_VALUE ? a : x; }
				public Integer composition(Integer x, Integer y) { return x == Integer.MAX_VALUE ? y : x; }
				public Integer identity() { return Integer.MAX_VALUE; }
			}
		);
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < q; i++) {
			int k = sc.nextInt();
			if ( k == 0 ) {
				int s = sc.nextInt();
				int t = sc.nextInt();
				int x = sc.nextInt();
				tree.apply(s, t + 1, x);
			} else {
				int s = sc.nextInt();
				int t = sc.nextInt();
				out.append(tree.query(s, t + 1));
				out.append('\n');
			}
		}
		System.out.print(out.toString());
	}
}
