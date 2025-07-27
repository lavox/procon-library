// verification-helper: PROBLEM https://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=GRL_3_B
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Bridges_aoj_test {
	public static void main(String[] args) {
		Bridges_aoj_test obj = new Bridges_aoj_test();
		obj.solve();
	}

	void solve() {
		Scanner sc = new Scanner(System.in);
		int vc = sc.nextInt();
		int ec = sc.nextInt();
		LowLink g = new LowLink(vc);
		for (int i = 0; i < ec; i++) {
			int s = sc.nextInt();
			int t = sc.nextInt();
			g.addEdge(Math.min(s, t), Math.max(s, t));
		}
		g.build();
		ArrayList<LowLink.Edge> bridges = new ArrayList<>(g.bridges());
		Collections.sort(bridges, (e0, e1) -> {
			if (e0.from() != e1.from()) {
				return e0.from() - e1.from();
			} else {
				return e0.to() - e1.to();
			}
		});
		StringBuilder out = new StringBuilder();
		for (LowLink.Edge e: bridges) {
			out.append(String.format("%d %d\n", e.from(), e.to()));
		}
		System.out.print(out.toString());
	}
}
