// verification-helper: PROBLEM https://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=ALDS1_1_B
import java.util.Scanner;

public class Gcd_aoj_test {
	public static void main(String[] args) {
		Gcd_aoj_test obj = new Gcd_aoj_test();
		obj.solve();
	}

	void solve() {
		Scanner sc = new Scanner(System.in);
		long x = sc.nextLong();
		long y = sc.nextLong();
		System.out.println(MathUtil.gcd(x, y));
	}
}
