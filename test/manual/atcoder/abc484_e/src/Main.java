import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

import java.util.Arrays;

// template & library : https://github.com/lavox/procon-library
// https://github.com/lavox/procon-library/blob/main/lib/template/Main.java
public class Main {
	public static void main(String[] args) {
		Main o = new Main();
		o.solve();
	}

	public void solve() {
		FastScanner sc = new FastScanner(System.in);
		int N = sc.nextInt();
		long L = sc.nextLong();
		long R = sc.nextLong();
		long[] A = sc.nextLongArray(N);
		long[] cum = new long[N + 1];
		cum[0] = 1L << 60;
		for (int i = 0; i < N; i++) {
			cum[i + 1] = cum[i] + A[i];
		}
		long ans = 0;
		LongWaveletMatrix wm = new LongWaveletMatrix(cum);
		for (int i = 0; i < N; i++) {
			ans += wm.rangeFreq(i + 1, N + 1, cum[i] + L, cum[i] + R + 1);
		}
		System.out.println(ans);
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

// === begin: data_structure/LongWaveletMatrix.java ===
// https://github.com/lavox/procon-library/blob/main/lib/data_structure/WaveletMatrix.java
class LongWaveletMatrix {
	private int n = 0;
	private int m = 0;
	private int height = 0;
	private long[][] bits = null;
	private int[][] cum = null;
	private int[] cnt0 = null;
	private static final int BLEN = 6;
	private static final int BMASK = (1 << BLEN) - 1;

	public LongWaveletMatrix(long[] arr, int height) {
		this.n = arr.length;
		this.m = (n + 63) >>> BLEN;
		this.height = Math.max(height, 1);
		this.build(arr);
	}
	public LongWaveletMatrix(long[] arr) {
		this(arr, 64 - Long.numberOfLeadingZeros(Arrays.stream(arr).max().orElse(0)));
	}
	private void build(long[] arr) {
		this.bits = new long[height][m + 1];
		this.cum = new int[height][m + 1];
		this.cnt0 = new int[height];
		long[] cur = arr.clone();
		long[] tmp = new long[n];
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
	public int select1(int h, int k) {
		if (k < 0 || k >= n - cnt0[h]) return -1;
		int li = 0;
		int ri = m;
		while (ri - li > 1) {
			int mi = (li + ri) >> 1;
			if (cum[h][mi] <= k) li = mi;
			else ri = mi;
		}
		long b = bits[h][li];
		int rest_k = k - cum[h][li];
		int pos = 0;
		for (int rg = 32; rg > 0; rg >>= 1) {
			int c = Long.bitCount(b & ((1L << rg) - 1));
			if (c <= rest_k) {
				rest_k -= c;
				b >>>= rg;
				pos += rg;
			}
		}
		return (li << BLEN) + pos;
	}
	public int select0(int h, int k) {
		if (k < 0 || k >= cnt0[h]) return -1;
		int li = 0;
		int ri = m;
		while (ri - li > 1) {
			int mi = (li + ri) >> 1;
			if ((mi << BLEN) - cum[h][mi] <= k) li = mi;
			else ri = mi;
		}
		long b = bits[h][li];
		int rest_k = k - ((li << BLEN) - cum[h][li]);
		int pos = 0;
		for (int rg = 32; rg > 0; rg >>= 1) {
			int c = rg - Long.bitCount(b & ((1L << rg) - 1));
			if (c <= rest_k) {
				rest_k -= c;
				b >>>= rg;
				pos += rg;
			}
		}
		return (li << BLEN) + pos;
	}
	public int next_i0(int h, int i) {
		return rank0(h, i);
	}
	public int next_i1(int h, int i) {
		return cnt0[h] + rank1(h, i);
	}
	public long access(int i) {
		long ret = 0;
		int c = i;
		for (int h = height - 1; h >= 0; h--) {
			int b = get(h, c);
			int c1 = rank1(h, c);
			if (b == 0) {
				c -= c1;
			} else {
				ret |= 1L << h;
				c = cnt0[h] + c1;
			}
		}
		return ret;
	}
	public long quantile(int l, int r, int k) {
		if (k >= r - l) throw new IllegalArgumentException();
		long ret = 0;
		for (int h = height - 1; h >= 0; h--) {
			int lc1 = rank1(h, l);
			int rc1 = rank1(h, r);
			int len = (r - rc1) - (l - lc1);
			if (k < len) {
				l -= lc1;
				r -= rc1;
			} else {
				ret |= 1L << h;
				k -= len;
				l = cnt0[h] + lc1;
				r = cnt0[h] + rc1;
			}
		}
		return ret;
	}
	public int rangeFreq(int l, int r, long vmin, long vmax) {
		return rangeFreqBelow(l, r, vmax) - rangeFreqBelow(l, r, vmin);
	}
	public int rangeFreqBelow(int l, int r, long vmax) {
		if (vmax <= 0) return 0;
		if (height < 63 && vmax >= 1L << height) return r - l;
		int ret = 0;
		for (int h = height - 1; h >= 0; h--) {
			int lc1 = rank1(h, l);
			int rc1 = rank1(h, r);
			if (((vmax >>> h) & 1L) == 0) {
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
	public int rangeFreqAbove(int l, int r, long vmin) {
		return r - l - rangeFreqBelow(l, r, vmin);
	}
	public long prevValue(int l, int r, long vmax) {
		int cnt = rangeFreqBelow(l, r, vmax);
		return cnt == 0 ? -1 : quantile(l, r, cnt - 1);
	}
	public long nextValue(int l, int r, long vmin) {
		int cnt = rangeFreqBelow(l, r, vmin);
		return cnt == r - l ? -1 : quantile(l, r, cnt);
	}
	public int rank(int l, int r, long v) {
		if (v < 0 || (height < 63 && v >= 1L << height)) return 0;
		for (int h = height - 1; h >= 0; h--) {
			if (((v >>> h) & 1L) == 0) {
				l = rank0(h, l);
				r = rank0(h, r);
			} else {
				l = cnt0[h] + rank1(h, l);
				r = cnt0[h] + rank1(h, r);
			}
		}
		return r - l;
	}
	public int select(long v, int k) {
		return select(0, n, v, k);
	}
	public int select(int l, int r, long v, int k) {
		if (v < 0 || (height < 63 && v >= 1L << height) || l >= r) return -1;
		for (int h = height - 1; h >= 0; h--) {
			if (((v >>> h) & 1L) == 0) {
				l = rank0(h, l);
				r = rank0(h, r);
			} else {
				l = cnt0[h] + rank1(h, l);
				r = cnt0[h] + rank1(h, r);
			}
		}
		if (l + k >= r) return -1;
		int ret = l + k;
		for (int h = 0; h < height; h++) {
			if (((v >>> h) & 1L) == 0) {
				ret = select0(h, ret);
			} else {
				ret = select1(h, ret - cnt0[h]);
			}
		}
		return ret;
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
// === end: data_structure/LongWaveletMatrix.java ===
