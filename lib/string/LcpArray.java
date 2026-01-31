package string;
import java.util.function.IntBinaryOperator;

// Ported to Java from the original C++ implementation by Atcoder.
// Original Source: https://github.com/atcoder/ac-library/blob/master/atcoder/string.hpp
public class LcpArray {
	// Reference:
	// T. Kasai, G. Lee, H. Arimura, S. Arikawa, and K. Park,
	// Linear-Time Longest-Common-Prefix Computation in Suffix Arrays and Its
	// Applications
	public static int[] lcpArray(IntBinaryOperator op, int n, int[] sa) {
		assert(n >= 1);
		int[] rnk = new int[n];
		for (int i = 0; i < n; i++) {
			rnk[sa[i]] = i;
		}
		int[] lcp = new int[n - 1];
		int h = 0;
		for (int i = 0; i < n; i++) {
			if (h > 0) h--;
			if (rnk[i] == 0) continue;
			int j = sa[rnk[i] - 1];
			for (; j + h < n && i + h < n; h++) {
				if (op.applyAsInt(j + h, i + h) != 0) break;
			}
			lcp[rnk[i] - 1] = h;
		}
		return lcp;
	}
	public static <T> int[] lcpArray(T[] s, int[] sa) {
		return lcpArray((i, j) -> s[i].equals(s[j]) ? 0 : -1, s.length, sa);
	}
	public static int[] lcpArray(String s, int[] sa) {
		return lcpArray((i, j) -> s.charAt(i) == s.charAt(j) ? 0 : -1, s.length(), sa);
	}
}
