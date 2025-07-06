// verification-helper: PROBLEM https://onlinejudge.u-aizu.ac.jp/courses/library/3/DSL/all/DSL_2_F
import java.util.Scanner;

public class RMQAndRUQInt_aoj_test {
	public static void main(String[] args) {
		RMQAndRUQInt_aoj_test obj = new RMQAndRUQInt_aoj_test();
		obj.solve();
	}

	void solve() {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int q = sc.nextInt();
		IntLazySegmentTree tree = new IntLazySegmentTree(n, new IntLazyMinUpdate());
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
