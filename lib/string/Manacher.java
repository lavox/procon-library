package string;

import java.util.function.IntBinaryOperator;

// Ported to Java from the original C++ implementation by Luzhiled.
// Original Source: hhttps://ei1333.github.io/luzhiled/snippets/string/manacher.html
public class Manacher {
	public static int[] manacher(IntBinaryOperator op, int n) {
		int[] ret = new int[n];
		int i = 0, j = 0;
		while (i < n) {
			while(i - j >= 0 && i + j < n && op.applyAsInt(i - j, i + j) == 0) {
				++j;
			}
			ret[i] = j;
			int k = 1;
			while(i - k >= 0 && i + k < n && k + ret[i - k] < j) {
				ret[i + k] = ret[i - k];
				++k;
			}
			i += k;
			j -= k;
		}
		return ret;
	}
	public static <T> int[] manacher(T[] s) {
		return manacher((i, j) -> s[i].equals(s[j]) ? 0 : -1, s.length);
	}
	public static int[] manacher(String s) {
		return manacher((i, j) -> s.charAt(i) == s.charAt(j) ? 0 : -1, s.length());
	}
}
