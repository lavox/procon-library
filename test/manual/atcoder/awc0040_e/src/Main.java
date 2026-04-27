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
		int Q = sc.nextInt();
		int[] X = new int[N];
		int[] V = new int[N];
		for (int i = 0; i < N; i++) {
			X[i] = sc.nextInt();
			V[i] = sc.nextInt();
		}
		Compression cmp_x = new Compression(X, 0, 1000000100);
		Compression cmp_v = new Compression(V);
		SqrtDecomposition sqd = new SqrtDecomposition(cmp_x.size());
		int[] ev = new int[cmp_x.size()];
		Arrays.fill(ev, -1);
		for (int i = 0; i < N; i++) {
			int xi = cmp_x.toIndex(X[i]);
			int vi = cmp_v.toIndex(V[i]);
			ev[xi] = vi;
		}
		MoQuery[] queries = new MoQuery[Q];
		for (int q = 0; q < Q; q++) {
			int L = sc.nextInt();
			int R = sc.nextInt();
			int li = cmp_x.toIndexCeiling(L);
			int ri = cmp_x.toIndexFloor(R) + 1;
			queries[q] = new MoQuery(q, li, ri, sqd);
		}
		Arrays.sort(queries);

		long[] ans = new long[Q];
		int[] cnt = new int[cmp_v.size()];
		long dup = 0;
		long tot = 0;
		int li = 0;
		int ri = 0;
		for (MoQuery qr: queries) {
			while (ri < qr.r) {
				if (ev[ri] != -1) {
					cnt[ev[ri]]++;
					dup += cnt[ev[ri]] - 1;
					tot++;
				}
				ri++;
			}
			while (qr.l < li) {
				li--;
				if (ev[li] != -1) {
					cnt[ev[li]]++;
					dup += cnt[ev[li]] - 1;
					tot++;
				}
			}
			while (qr.r < ri) {
				ri--;
				if (ev[ri] != -1) {
					cnt[ev[ri]]--;
					dup -= cnt[ev[ri]];
					tot--;
				}
			}
			while (li < qr.l) {
				if (ev[li] != -1) {
					cnt[ev[li]]--;
					dup -= cnt[ev[li]];
					tot--;
				}
				li++;
			}
			long a = tot * (tot + 1) / 2;
			ans[qr.id] = a - dup;
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

// === begin: data_structure/Compression.java ===
class Compression {
	long[] vals = null;
	
	public Compression(long[] vals, long... v) {
		long[] arr = Arrays.copyOf(vals, vals.length + v.length);
		System.arraycopy(v, 0, arr, vals.length, v.length);
		init(arr);
	}
	public Compression(int[] vals, int... v) {
		long[] arr = new long[vals.length + v.length];
		for (int i = 0; i < vals.length; i++) arr[i] = vals[i];
		for (int i = 0; i < v.length; i++) arr[vals.length + i] = v[i];
		init(arr);
	}
	private void init(long[] arr) {
		int sz = 0;
		Arrays.sort(arr);
		for ( int i = 0 ; i < arr.length ; i++ ) {
			if ( i == 0 || arr[i] != arr[i - 1] ) sz++;
		}
		this.vals = new long[sz];
		int vi = 0;
		for ( int i = 0 ; i < arr.length ; i++ ) {
			if ( i == 0 || arr[i] != arr[i - 1] ) this.vals[vi++] = arr[i];
		}
	}
	public long toValue(int i) {
		return vals[i];
	}
	public int toIndex(long v) {
		int idx = search(v);
		if ( idx < 0 ) return -1;
		return vals[idx] == v ? idx : -1;
	}
	public int toIndexFloor(long v) {
		return search(v);
	}
	public int toIndexCeiling(long v) {
		int idx = search(v);
		if ( idx < 0 ) return 0;
		return vals[idx] == v ? idx : idx + 1;
	}
	public int size() {
		return vals.length;
	}
	private int search(long v) {
		if ( vals[vals.length - 1] <= v ) return vals.length - 1;
		if ( v < vals[0] ) return -1;
		int min = 0;
		int max = vals.length - 1;
		while ( max - min > 1 ) {
			int mid = ( min + max ) / 2;
			if ( vals[mid] <= v ) { min = mid; } else { max = mid; }
		}
		return min;
	}
}
// === end: data_structure/Compression.java ===

// === begin: data_structure/MoQuery.java ===
class MoQuery implements Comparable<MoQuery> {
	public final int id;
	public final int l;
	public final int r;
	public final int l_bid;
	public MoQuery(int id, int l, int r, SqrtDecomposition sqd) {
		this.id = id;
		this.l = l;
		this.r = r;
		this.l_bid = sqd.bid(l);
	}
	@Override
	public int compareTo(MoQuery o) {
		if (l_bid != o.l_bid) {
			return Integer.compare(l_bid, o.l_bid);
		} else if (r != o.r) {
			if (l_bid % 2 == 0) {
				return Integer.compare(r, o.r);
			} else {
				return -Integer.compare(r, o.r);
			}
		} else {
			return Integer.compare(id, o.id);
		}
	}
}
// === end: data_structure/MoQuery.java ===

// === begin: data_structure/SqrtDecomposition.java ===
class SqrtDecomposition {
	public final int N;
	public final int B;
	public final int BC;
	public SqrtDecomposition(int N) {
		this(N, (int)Math.round(Math.sqrt(N)));
	}
	public SqrtDecomposition(int N, int B) {
		this.N = N;
		this.B = Math.max(B, 1);
		this.BC = (N + this.B - 1) / this.B;
	}
	public int bid(int idx) {
		return idx / B;
	}
	public int lid(int idx) {
		return idx % B;
	}
	public int idx(int bid, int lid) {
		return bid * B + lid;
	}
	public int bStart(int bid) {
		return bid * B;
	}
	public int bEnd(int bid) {
		return Math.min((bid + 1) * B, N);
	}
	public int bLen(int bid) {
		return bEnd(bid) - bStart(bid);
	}
	public int bCnt() {
		return BC;
	}
}
// === end: data_structure/SqrtDecomposition.java ===
