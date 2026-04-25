import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

import java.util.Arrays;

// template & library : https://github.com/lavox/procon-library
public class Main {
	public static void main(String[] args) {
		Main o = new Main();
		o.solve();
	}

	public void solve() {
		FastScanner sc = new FastScanner(System.in);
		int N = sc.nextInt();
		int[] X = sc.nextIntArray(N);
		int Q = sc.nextInt();
		WaveletMatrix wm = new WaveletMatrix(X);
		int[] ans = new int[Q];
		Arrays.fill(ans, Integer.MAX_VALUE);
		for (int q = 0; q < Q; q++) {
			int l = sc.nextInt() - 1;
			int r = sc.nextInt();
			int x = sc.nextInt();
			int x0 = wm.prevValue(l, r, x);
			if (x0 != -1) ans[q] = x - x0;
			int x1 = wm.nextValue(l, r, x);
			if (x1 != -1) ans[q] = Math.min(ans[q], x1 - x);
		}
		print(ans, LF);
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
	public static <T> void print(T[] array, char sep) {
		print(array, sep, n -> n, 0, array.length);
	}
	public static <T> void print(T[] array, char sep, LongUnaryOperator conv) {
		print(array, sep, conv, 0, array.length);
	}
	public static <T> void print(T[] array, char sep, LongUnaryOperator conv, int start, int end) {
		StringBuilder ans = new StringBuilder();
		for (int i = start; i < end; i++) {
			ans.append(array[i].toString());
			ans.append(sep);
		}
		if (ans.length() > 0) ans.deleteCharAt(ans.length() - 1);
		System.out.println(ans.toString());
	}
	public static void printYesNo(boolean[] array, char sep) {
		printYesNo(array, sep, n -> n, 0, array.length);
	}
	public static void printYesNo(boolean[] array, char sep, LongUnaryOperator conv) {
		printYesNo(array, sep, conv, 0, array.length);
	}
	public static void printYesNo(boolean[] array, char sep, LongUnaryOperator conv, int start, int end) {
		StringBuilder ans = new StringBuilder();
		for (int i = start; i < end; i++) {
			ans.append(array[i] ? YES : NO);
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
	public static <T> void print(T s) { System.out.println(s.toString()); }
	public static void printYesNo(boolean yesno) {
		System.out.println(yesno ? YES : NO);
	}
	public static void printDouble(double val, int digit) {
		System.out.println(String.format("%." + digit + "f", val));
	}
	public static void print(int... a) { print(a, SPACE); }
	public static void print(long... a) { print(a, SPACE); }
	@SuppressWarnings("unchecked")
	public static <T> void print(T... s) { print(s, SPACE); }
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

// === begin: data_structure/WaveletMatrix.java ===
class WaveletMatrix {
	private int n = 0;
	private int m = 0;
	private int height = 0;
	private long[][] bits = null;
	private int[][] cum = null;
	private int[] cnt0 = null;
	private static final int BLEN = 6;
	private static final int BMASK = (1 << BLEN) - 1;

	public WaveletMatrix(int[] arr, int height) {
		this.n = arr.length;
		this.m = (n + 63) >>> BLEN;
		this.height = height;
		this.build(arr);
	}
	public WaveletMatrix(int[] arr) {
		this(arr, 32 - Integer.numberOfLeadingZeros(Arrays.stream(arr).max().orElse(0)));
	}
	private void build(int[] arr) {
		this.bits = new long[height][m + 1];
		this.cum = new int[height][m + 1];
		this.cnt0 = new int[height];
		int[] cur = arr.clone();
		int[] tmp = new int[n];
		for (int h = height - 1; h >= 0; h--) {
			int i0 = 0;
			int i1 = 0;
			for (int i = 0; i < n; i++) {
				if (((cur[i] >>> h) & 1) == 0) {
					cur[i0++] = cur[i];
				} else {
					tmp[i1++] = cur[i];
					set(h, i);
				}
			}
			this.cnt0[h] = i0;
			System.arraycopy(tmp, 0, cur, i0, i1);
			prepareCumulative(h);
		}
	}
	private void prepareCumulative(int h) {
		for (int idx = 0; idx < m; idx++) {
			cum[h][idx + 1] = cum[h][idx] + Long.bitCount(bits[h][idx]);
		}
	}

	private void set(int h, int i) {
		bits[h][i >>> BLEN] |= 1L << (i & BMASK);
	}
	public int get(int h, int i) {
		return (int)((bits[h][i >>> BLEN] >>> (i & BMASK)) & 1L);
	}
	public int rank1(int h, int i) {
		int idx = i >>> BLEN;
		return cum[h][idx] + Long.bitCount(bits[h][idx] & ((1L << (i & BMASK)) - 1L));
	}
	public int rank0(int h, int i) {
		return i - rank1(h, i);
	}
	public int next_i0(int h, int i) {
		return rank0(h, i);
	}
	public int next_i1(int h, int i) {
		return cnt0[h] + rank1(h, i);
	}
	public int access(int i) {
		int ret = 0;
		int c = i;
		for (int h = height - 1; h >= 0; h--) {
			int b = get(h, c);
			int c1 = rank1(h, c);
			if (b == 0) {
				c -= c1;
			} else {
				ret |= 1 << h;
				c = cnt0[h] + c1;
			}
		}
		return ret;
	}
	public int quantile(int l, int r, int k) {
		if (k >= r - l) throw new IllegalArgumentException();
		int ret = 0;
		for (int h = height - 1; h >= 0; h--) {
			int lc1 = rank1(h, l);
			int rc1 = rank1(h, r);
			int len = (r - rc1) - (l - lc1);
			if (k < len) {
				l -= lc1;
				r -= rc1;
			} else {
				ret |= 1 << h;
				k -= len;
				l = cnt0[h] + lc1;
				r = cnt0[h] + rc1;
			}
		}
		return ret;
	}
	public int rangeFreq(int l, int r, int vmin, int vmax) {
		return rangeFreqBelow(l, r, vmax) - rangeFreqBelow(l, r, vmin);
	}
	public int rangeFreqBelow(int l, int r, int vmax) {
		if (vmax >= 1 << height) return r - l;
		int ret = 0;
		for (int h = height - 1; h >= 0; h--) {
			int lc1 = rank1(h, l);
			int rc1 = rank1(h, r);
			if (((vmax >>> h) & 1) == 0) {
				l -= lc1;
				r -= rc1;
			} else {
				ret += (r - rc1) - (l - lc1);
				l = cnt0[h] + lc1;
				r = cnt0[h] + rc1;
			}
		}
		return ret;
	}
	public int rangeFreqAbove(int l, int r, int vmin) {
		return r - l - rangeFreqBelow(l, r, vmin);
	}
	public int prevValue(int l, int r, int vmax) {
		int cnt = rangeFreqBelow(l, r, vmax);
		return cnt == 0 ? -1 : quantile(l, r, cnt - 1);
	}
	public int nextValue(int l, int r, int vmin) {
		int cnt = rangeFreqBelow(l, r, vmin);
		return cnt == r - l ? -1 : quantile(l, r, cnt);
	}

	public Range createRange(int l, int r) {
		return new Range(height - 1, l, r);
	}
	public class Range {
		int h = 0;
		int l = 0;
		int r = 0;
		int next_l0 = 0;
		int next_r0 = 0;
		int next_l1 = 0;
		int next_r1 = 0;
		public Range(int h, int l, int r) {
			this.h = h;
			this.l = l;
			this.r = r;
			calcNext();
		}
		private void calcNext() {
			this.next_l0 = rank0(h, l);
			this.next_r0 = rank0(h, r);
			this.next_l1 = cnt0[h] + l - this.next_l0;
			this.next_r1 = cnt0[h] + r - this.next_r0;
		}
		public void move0() {
			h--;
			l = next_l0;
			r = next_r0;
			if (h >= 0) calcNext();
		}
		public void move1() {
			h--;
			l = next_l1;
			r = next_r1;
			if (h >= 0) calcNext();
		}
		public int l() { return l; }
		public int r() { return r; }
		public int len() { return r - l; }
		public int next_l0() { return next_l0; }
		public int next_r0() { return next_r0; }
		public int next_l1() { return next_l1; }
		public int next_r1() { return next_r1; }
		public int next_len0() { return next_r0 - next_l0; }
		public int next_len1() { return next_r1 - next_l1; }
	}
}
// === end: data_structure/WaveletMatrix.java ===
