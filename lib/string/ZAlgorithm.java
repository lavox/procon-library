package string;
import java.util.function.IntBinaryOperator;

public class ZAlgorithm {
	// Reference:
	// D. Gusfield,
	// Algorithms on Strings, Trees, and Sequences: Computer Science and
	// Computational Biology
	public static int[] zAlgorithm(IntBinaryOperator op, int n) {
		if (n == 0) return new int[] {};
		int[] z = new int[n];
		z[0] = 0;
		for (int i = 1, j = 0; i < n; i++) {
			z[i] = (j + z[j] <= i) ? 0 : Math.min(j + z[j] - i, z[i - j]);
			while (i + z[i] < n && op.applyAsInt(z[i], i + z[i]) == 0) z[i]++;
			if (j + z[j] < i + z[i]) j = i;
		}
		z[0] = n;
		return z;
	}
	public static <T> int[] zAlgorithm(T[] s) {
		return zAlgorithm((i, j) -> s[i].equals(s[j]) ? 0 : -1, s.length);
	}
	public static int[] zAlgorithm(String s) {
		return zAlgorithm((i, j) -> s.charAt(i) == s.charAt(j) ? 0 : -1, s.length());
	}
}
