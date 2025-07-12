class LcpArray {
	// Reference:
	// T. Kasai, G. Lee, H. Arimura, S. Arikawa, and K. Park,
	// Linear-Time Longest-Common-Prefix Computation in Suffix Arrays and Its
	// Applications
	public static <T> int[] lcpArray(T[] s, int[] sa) {
		int n = s.length;
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
				if (!s[j + h].equals(s[i + h])) break;
			}
			lcp[rnk[i] - 1] = h;
		}
		return lcp;
	}
	public static int[] lcpArray(String s, int[] sa) {
		int n = s.length();
		Integer[] s2 = new Integer[n];
		for (int i = 0; i < n; i++) {
			s2[i] = (int)s.charAt(i);
		}
		return lcpArray(s2, sa);
	}
}
