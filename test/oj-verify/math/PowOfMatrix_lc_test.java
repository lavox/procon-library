// verification-helper: PROBLEM https://judge.yosupo.jp/problem/pow_of_matrix
import java.util.Scanner;

public class PowOfMatrix_lc_test {
	public static void main(String[] args) {
		PowOfMatrix_lc_test obj = new PowOfMatrix_lc_test();
		obj.solve();
	}

	static final long MOD = 998244353;
	void solve() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		long K = sc.nextLong();
		Matrix a = new Matrix(N, N);
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				a.set(i, j, sc.nextInt());
			}
		}
		Matrix b = a.powMod(K, MOD);

		StringBuilder out = new StringBuilder();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				out.append(b.get(i, j));
				out.append(' ');
			}
			out.append('\n');
		}
		System.out.print(out.toString());
	}
}
