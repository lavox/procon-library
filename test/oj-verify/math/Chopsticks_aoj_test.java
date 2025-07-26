// verification-helper: PROBLEM https://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=2659
import java.util.ArrayList;
import java.util.Scanner;

public class Chopsticks_aoj_test {
	public static void main(String[] args) {
		Chopsticks_aoj_test obj = new Chopsticks_aoj_test();
		obj.solve();
	}

	void solve() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		int D = sc.nextInt();
		long[] A = new long[M];
		for (int i = 0; i < M; i++) {
			A[i] = sc.nextLong();
		}
		long[][] R = new long[D][M];
		for (int d = 0; d < D; d++) {
			for (int i = 0; i < M; i++) {
				R[d][i] = sc.nextLong();
			}
		}
		long cur = N;
		for (int d = 0; d < D; d++) {
			ArrayList<Long> r = new ArrayList<>();
			ArrayList<Long> m = new ArrayList<>();
			for (int i = 0; i < M; i++) {
				if (R[d][i] != -1) {
					r.add(R[d][i]);
					m.add(A[i]);
				}
			}
			long[] ret = MathUtil.crt(r, m);
			long nm = ret[0], nr = ret[1];
			if (nr == 0 || nm > cur) {
				System.out.println(-1);
				return;
			}
			cur = ((cur - nm) / nr) * nr + nm;
		}
		System.out.println(cur);
	}
}
