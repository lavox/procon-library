// verification-helper: PROBLEM https://onlinejudge.u-aizu.ac.jp/courses/library/3/DSL/all/DSL_2_G
import java.util.Scanner;

public class RSQAndRAQLong_aoj_test {
	public static void main(String[] args) {
		RSQAndRAQLong_aoj_test obj = new RSQAndRAQLong_aoj_test();
		obj.solve();
	}

	void solve() {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int q = sc.nextInt();
		LongLazySegmentTree tree = new LongLazySegmentTree(n, new LongLazySumAdd());
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < q; i++) {
			int k = sc.nextInt();
			if ( k == 0 ) {
				int s = sc.nextInt() - 1;
				int t = sc.nextInt() - 1;
				long x = sc.nextLong();
				tree.apply(s, t + 1, x);
			} else {
				int s = sc.nextInt() - 1;
				int t = sc.nextInt() - 1;
				out.append(tree.query(s, t + 1));
				out.append('\n');
			}
		}
		System.out.print(out.toString());
	}
}
