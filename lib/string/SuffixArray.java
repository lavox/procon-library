package string;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Consumer;

// Ported to Java from the original C++ implementation by Atcoder.
// Original Source: https://github.com/atcoder/ac-library/blob/master/atcoder/string.hpp
public class SuffixArray {
	private static int[] convertToPrimitiveArray(Integer[] arr) {
		int[] ret = new int[arr.length];
		for (int i = 0; i < arr.length; i++) ret[i] = arr[i];
		return ret;
	}
	private static int[] saNaive(int[] s) {
		int n = s.length;
		Integer[] sa = new Integer[n];
		for (int i = 0; i < n; i++) sa[i] = i;
		Arrays.sort(sa, (l, r) -> {
			if (l == r) return 0;
			while (l < n && r < n) {
				if (s[l] != s[r]) return s[l] - s[r];
				l++;
				r++;
			}
			return l == n ? -1 : 1;
		});
		return convertToPrimitiveArray(sa);
	}
	private static int[] saDoubling(int[] s) {
		int n = s.length;
		Integer[] sa = new Integer[n];
		int[] rnk = s;
		int[] tmp = new int[n];
		for (int i = 0; i < n; i++) sa[i] = i;
		for (int k = 1; k < n; k *= 2) {
			final int _k = k;
			final int[] _rnk = rnk;
			Comparator<Integer> cmp = (x, y) -> {
				if (_rnk[x] != _rnk[y]) return _rnk[x] - _rnk[y];
				int rx = x + _k < n ? _rnk[x + _k] : -1;
				int ry = y + _k < n ? _rnk[y + _k] : -1;
				return rx - ry;
			};
			Arrays.sort(sa, cmp);
			tmp[sa[0]] = 0;
			for (int i = 1; i < n; i++) {
					tmp[sa[i]] = tmp[sa[i - 1]] + (cmp.compare(sa[i - 1], sa[i]) < 0 ? 1 : 0);
			}
			int[] tmp2 = tmp;
			tmp = rnk;
			rnk = tmp2;
		}
		return convertToPrimitiveArray(sa);
	}
	
	// SA-IS, linear-time suffix array construction
	// Reference:
	// G. Nong, S. Zhang, and W. H. Chan,
	// Two Efficient Algorithms for Linear Time Suffix Array Construction
	private static final int THRESHOLD_NAIVE = 10;
	private static final int THRESHOLD_DOUBLING = 40;
	private static int[] saIs(int[] s, int upper) {
		int n = s.length;
		if (n == 0) return new int[] {};
		if (n == 1) return new int[] {0};
		if (n == 2) {
			if (s[0] < s[1]) {
				return new int[] {0, 1};
			} else {
				return new int[] {1, 0};
			}
		}
		if (n < THRESHOLD_NAIVE) {
			return saNaive(s);
		}
		if (n < THRESHOLD_DOUBLING) {
			return saDoubling(s);
		}

		int[] sa = new int[n];
		boolean[] ls = new boolean[n];
		for (int i = n - 2; i >= 0; i--) {
				ls[i] = (s[i] == s[i + 1]) ? ls[i + 1] : (s[i] < s[i + 1]);
		}
		int[] sum_l = new int[upper + 1];
		int[] sum_s = new int[upper + 1];
		for (int i = 0; i < n; i++) {
			if (!ls[i]) {
				sum_s[s[i]]++;
			} else {
				sum_l[s[i] + 1]++;
			}
		}
		for (int i = 0; i <= upper; i++) {
			sum_s[i] += sum_l[i];
			if (i < upper) sum_l[i + 1] += sum_s[i];
		}

		Consumer<int[]> induce = (int[] lms) -> {
			Arrays.fill(sa, -1);
			int[] buf = new int[upper + 1];
			System.arraycopy(sum_s, 0, buf, 0, sum_l.length);
			for (int d : lms) {
					if (d == n) continue;
					sa[buf[s[d]]++] = d;
			}
			System.arraycopy(sum_l, 0, buf, 0, sum_l.length);
			sa[buf[s[n - 1]]++] = n - 1;
			for (int i = 0; i < n; i++) {
				int v = sa[i];
				if (v >= 1 && !ls[v - 1]) {
					sa[buf[s[v - 1]]++] = v - 1;
				}
			}
			System.arraycopy(sum_l, 0, buf, 0, sum_l.length);
			for (int i = n - 1; i >= 0; i--) {
				int v = sa[i];
				if (v >= 1 && ls[v - 1]) {
					sa[--buf[s[v - 1] + 1]] = v - 1;
				}
			}
		};

		int[] lms_map = new int[n + 1];
		Arrays.fill(lms_map, -1);
		int m = 0;
		for (int i = 1; i < n; i++) {
			if (!ls[i - 1] && ls[i]) {
				lms_map[i] = m++;
			}
		}
		int[] lms = new int[m];
		int mi = 0;
		for (int i = 1; i < n; i++) {
			if (!ls[i - 1] && ls[i]) {
				lms[mi++] = i;
			}
		}

		induce.accept(lms);

		if ( m != 0 ) {
			int[] sorted_lms = new int[m];
			mi = 0;
			for (int v : sa) {
				if (lms_map[v] != -1) sorted_lms[mi++] = v;
			}
			int[] rec_s = new int[m];
			int rec_upper = 0;
			rec_s[lms_map[sorted_lms[0]]] = 0;
			for (int i = 1; i < m; i++) {
				int l = sorted_lms[i - 1], r = sorted_lms[i];
				int end_l = (lms_map[l] + 1 < m) ? lms[lms_map[l] + 1] : n;
				int end_r = (lms_map[r] + 1 < m) ? lms[lms_map[r] + 1] : n;
				boolean same = true;
				if (end_l - l != end_r - r) {
					same = false;
				} else {
					while (l < end_l) {
						if (s[l] != s[r]) {
							break;
						}
						l++;
						r++;
					}
					if (l == n || s[l] != s[r]) same = false;
				}
				if (!same) rec_upper++;
				rec_s[lms_map[sorted_lms[i]]] = rec_upper;
			}

			int[] rec_sa = saIs(rec_s, rec_upper);

			for (int i = 0; i < m; i++) {
				sorted_lms[i] = lms[rec_sa[i]];
			}
			induce.accept(sorted_lms);
		}
		return sa;
	}
	public static int[] suffixArray(int[] s, int upper) {
		assert 0 <= upper;
		for (int d : s) {
			assert 0 <= d && d <= upper;
		}
		int[] sa = saIs(s, upper);
		return sa;
	}
	public static <T extends Comparable<T>> int[] suffixArray(T[] s) {
			int n = s.length;
			Integer[] idx = new Integer[n];
			for (int i = 0; i < n; i++) idx[i] = i;
			Arrays.sort(idx, (l, r) -> {return s[l].compareTo(s[r]);});
			int[] s2 = new int[n];
			int now = 0;
			for (int i = 0; i < n; i++) {
				if (i != 0 && !s[idx[i - 1]].equals(s[idx[i]])) now++;
				s2[idx[i]] = now;
			}
			return saIs(s2, now);
	}
	public static int[] suffixArray(String s) {
		int n = s.length();
		int[] s2 = new int[n];
		for (int i = 0; i < n; i++) {
			s2[i] = s.charAt(i);
		}
		return saIs(s2, 255);
	}
}
