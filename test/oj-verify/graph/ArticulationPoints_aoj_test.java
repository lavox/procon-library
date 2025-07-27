// verification-helper: PROBLEM https://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=GRL_3_A
import java.util.ArrayList;
import java.util.Scanner;

public class ArticulationPoints_aoj_test {
	public static void main(String[] args) {
		ArticulationPoints_aoj_test obj = new ArticulationPoints_aoj_test();
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
			g.addEdge(s, t);
		}
		g.build();
		ArrayList<LowLink.Node> art = g.articulations();
		StringBuilder out = new StringBuilder();
		for (LowLink.Node n: art) {
			out.append(n.id());
			out.append('\n');
		}
		System.out.print(out.toString());
	}
}
