// verification-helper: PROBLEM https://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=GRL_1_C
import java.util.Arrays;
import java.util.Scanner;

public class WarshallFloyd_aoj_test {
	public static void main(String[] args) {
		WarshallFloyd_aoj_test obj = new WarshallFloyd_aoj_test();
		obj.solve();
	}

	void solve() {
		Scanner sc = new Scanner(System.in);
		int vc = sc.nextInt();
		int ec = sc.nextInt();
		long[][] dist = WarshallFloyd.createInitTable(vc);
		for (int i = 0; i < ec; i++) {
			int s = sc.nextInt();
			int t = sc.nextInt();
			long d = sc.nextLong();
			dist[s][t] = d;
		}
		dist = WarshallFloyd.warshallFloyd(dist);
		if (WarshallFloyd.hasNegativeLoop(dist)) {
			System.out.println("NEGATIVE CYCLE");
			return;
		}
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < vc; i++) {
			for (int j = 0; j < vc; j++) {
				if (dist[i][j] == WarshallFloyd.INF) {
					out.append("INF");
				} else {
					out.append(dist[i][j]);
				}
				out.append(' ');
			}
			out.deleteCharAt(out.length() - 1);
			out.append('\n');
		}
		System.out.print(out.toString());
	}
}
