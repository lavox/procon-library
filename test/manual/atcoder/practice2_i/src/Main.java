import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

public class Main {
	public static void main(String[] args) {
		Main o = new Main();
		o.solve();
	}

	public void solve() {
		FastScanner sc = new FastScanner(System.in);
		String S = sc.next();
		int[] sa = suffixArray(S);
		int[] la = lcpArray(S, sa);
		long n = S.length();
		long ans = 0;
		for (int i = 0; i < n; i++) {
			if ( i == 0 ) {
				ans += n - sa[i];
			} else {
				ans += n - sa[i] - la[i - 1];
			}
		}
		System.out.println(ans);
	}

	// SuffixArray
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
	// LCPArray
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

	public static final char LF = '\n';
	public static final char SPACE = ' ';
	public static final String YES = "Yes";
	public static final String NO = "No";
	public static void print(int[] array, char sep) {
		print(array, sep, n -> n, 0, array.length);
	}
	public static void print(int[] array, char sep, IntUnaryOperator conv) {
		print(array, sep, conv, 0, array.length);
	}
	public static void print(int[] array, char sep, IntUnaryOperator conv, int start, int end) {
		StringBuilder ans = new StringBuilder();
		for (int i = start; i < end; i++) {
			ans.append(conv.applyAsInt(array[i]));
			ans.append(sep);
		}
		if (ans.length() > 0) ans.deleteCharAt(ans.length() - 1);
		System.out.println(ans.toString());
	}
	public static void print(long[] array, char sep) {
		print(array, sep, n -> n, 0, array.length);
	}
	public static void print(long[] array, char sep, LongUnaryOperator conv) {
		print(array, sep, conv, 0, array.length);
	}
	public static void print(long[] array, char sep, LongUnaryOperator conv, int start, int end) {
		StringBuilder ans = new StringBuilder();
		for (int i = start; i < end; i++) {
			ans.append(conv.applyAsLong(array[i]));
			ans.append(sep);
		}
		if (ans.length() > 0) ans.deleteCharAt(ans.length() - 1);
		System.out.println(ans.toString());
	}
	public static <T> void print(ArrayList<T> array, char sep) {
		print(array, sep, a -> a, 0, array.size());
	}
	public static <T> void print(ArrayList<T> array, char sep, UnaryOperator<T> conv) {
		print(array, sep, conv, 0, array.size());
	}
	public static <T> void print(ArrayList<T> array, char sep, UnaryOperator<T> conv, int start, int end) {
		StringBuilder ans = new StringBuilder();
		for (int i = start; i < end; i++) {
			ans.append(conv.apply(array.get(i)).toString());
			ans.append(sep);
		}
		if (ans.length() > 0) ans.deleteCharAt(ans.length() - 1);
		System.out.println(ans.toString());
	}
	public static void print(int a) { System.out.println(a); }
	public static void print(long a) { System.out.println(a); }
	public static void print(String s) { System.out.println(s); }
	public static void printYesNo(boolean yesno) {
		System.out.println(yesno ? YES : NO);
	}
	public static void printDouble(double val, int digit) {
		System.out.println(String.format("%." + digit + "f", val));
	}
}
class FastScanner {
	private final InputStream in;
	private final byte[] buf = new byte[1024];
	private int ptr = 0;
	private int buflen = 0;
	FastScanner( InputStream source ) { this.in = source; }
	private boolean hasNextByte() {
		if ( ptr < buflen ) return true;
		else {
			ptr = 0;
			try { buflen = in.read(buf); } catch (IOException e) { e.printStackTrace(); }
			if ( buflen <= 0 ) return false;
		}
		return true;
	} 
	private int readByte() { if ( hasNextByte() ) return buf[ptr++]; else return -1; } 
	private boolean isPrintableChar( int c ) { return 33 <= c && c <= 126; }
	private boolean isNumeric( int c ) { return '0' <= c && c <= '9'; }
	private void skipToNextPrintableChar() { while ( hasNextByte() && !isPrintableChar(buf[ptr]) ) ptr++; }
	public boolean hasNext() { skipToNextPrintableChar(); return hasNextByte(); }
	public String next() {
		if ( !hasNext() ) throw new NoSuchElementException();
		StringBuilder ret = new StringBuilder();
		int b = readByte();
		while ( isPrintableChar(b) ) { ret.appendCodePoint(b); b = readByte(); }
		return ret.toString();
	}
	public long nextLong() {
		if ( !hasNext() ) throw new NoSuchElementException();
		long ret = 0;
		int b = readByte();
		boolean negative = false;
		if ( b == '-' ) { negative = true; if ( hasNextByte() ) b = readByte(); }
		if ( !isNumeric(b) ) throw new NumberFormatException();
		while ( true ) {
			if ( isNumeric(b) ) ret = ret * 10 + b - '0';
			else if ( b == -1 || !isPrintableChar(b) ) return negative ? -ret : ret;
			else throw new NumberFormatException();
			b = readByte();
		}
	}
	public int nextInt() { return (int)nextLong(); }
	public double nextDouble() { return Double.parseDouble(next()); }
	public int[] nextIntArray(int N) { return nextIntArray(N, n -> n); }
	public int[] nextIntArray(int N, IntUnaryOperator conv) {
		int[] ret = new int[N];
		for (int i = 0; i < N; i++) ret[i] = conv.applyAsInt(nextInt());
		return ret;
	}
	public long[] nextLongArray(int N) {
		long[] ret = new long[N];
		for (int i = 0; i < N; i++) ret[i] = nextLong();
		return ret;
	}
	public String[] nextStringArray(int N) {
		String[] ret = new String[N];
		for (int i = 0; i < N; i++) ret[i] = next();
		return ret;
	}
	public int[][] nextIntMatrix(int N, int M) { return nextIntMatrix(N, M, n -> n); }
	public int[][] nextIntMatrix(int N, int M, IntUnaryOperator conv) {
		int[][] ret = new int[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				ret[i][j] = conv.applyAsInt(nextInt());
			}
		}
		return ret;
	}
	public long[][] nextLongMatrix(int N, int M) {
		long[][] ret = new long[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				ret[i][j] = nextLong();
			}
		}
		return ret;
	}
	public String[][] nextStringMatrix(int N, int M) {
		String[][] ret = new String[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				ret[i][j] = next();
			}
		}
		return ret;
	}
}
