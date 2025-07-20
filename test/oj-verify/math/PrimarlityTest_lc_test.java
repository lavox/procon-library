// verification-helper: PROBLEM https://judge.yosupo.jp/problem/primality_test
import java.util.Scanner;

public class PrimarlityTest_lc_test {
	public static void main(String[] args) {
		PrimarlityTest_lc_test obj = new PrimarlityTest_lc_test();
		obj.solve();
	}

	void solve() {
		Scanner sc = new Scanner(System.in);
		int Q = sc.nextInt();
		long[] N = new long[Q];
		for (int q = 0; q < Q; q++) {
			N[q] = sc.nextLong();
		}

		StringBuilder out = new StringBuilder();
		for (int q = 0; q < Q; q++) {
			out.append(Prime.isPrimeByMillerRabin(N[q]) ? "Yes\n" : "No\n");
		}
		System.out.print(out.toString());
	}
}
