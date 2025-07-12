// verification-helper: PROBLEM https://judge.yosupo.jp/problem/zalgorithm
import java.util.Scanner;

public class ZAlgorithm_lc_test {
	public static void main(String[] args) {
		ZAlgorithm_lc_test obj = new ZAlgorithm_lc_test();
		obj.solve();
	}

	void solve() {
		Scanner sc = new Scanner(System.in);
		String S = sc.next();
		int[] a = ZAlgorithm.zAlgorithm(S);

		StringBuilder out = new StringBuilder();
		for (int i = 0; i < S.length(); i++) {
			out.append(a[i]);
			out.append(' ');
		}
		System.out.print(out.toString());
	}
}
