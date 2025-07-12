class ZAlgorithm {
	// Reference:
	// D. Gusfield,
	// Algorithms on Strings, Trees, and Sequences: Computer Science and
	// Computational Biology
	public static <T> int[] zAlgorithm(T[] s) {
		int n = s.length;
		if (n == 0) return new int[] {};
		int[] z = new int[n];
		z[0] = 0;
		for (int i = 1, j = 0; i < n; i++) {
			z[i] = (j + z[j] <= i) ? 0 : Math.min(j + z[j] - i, z[i - j]);
			while (i + z[i] < n && s[z[i]].equals(s[i + z[i]])) z[i]++;
			if (j + z[j] < i + z[i]) j = i;
		}
		z[0] = n;
		return z;
	}
	public static int[] zAlgorithm(String s) {
		int n = s.length();
		Integer[] s2 = new Integer[n];
		for (int i = 0; i < n; i++) {
			s2[i] = (int)s.charAt(i);
		}
		return zAlgorithm(s2);
	}
}
