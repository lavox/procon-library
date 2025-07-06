// verification-helper: PROBLEM https://judge.yosupo.jp/problem/matrix_product
import java.util.Scanner;

public class MatrixProduct_lc_test {
	public static void main(String[] args) {
		MatrixProduct_lc_test obj = new MatrixProduct_lc_test();
		obj.solve();
	}

	static final long MOD = 998244353;
	void solve() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		int K = sc.nextInt();
		Matrix a = new Matrix(N, M);
		Matrix b = new Matrix(M, K);
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				a.set(i, j, sc.nextInt());
			}
		}
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < K; j++) {
				b.set(i, j, sc.nextInt());
			}
		}
		Matrix c = a.mulMod(b, MOD);

		StringBuilder out = new StringBuilder();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < K; j++) {
				out.append(c.get(i, j));
				out.append(' ');
			}
			out.append('\n');
		}
		System.out.print(out.toString());
	}
}
