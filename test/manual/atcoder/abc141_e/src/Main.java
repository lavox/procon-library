import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

// https://github.com/lavox/procon-library
public class Main {
	public static void main(String[] args) {
		Main o = new Main();
		o.solve();
	}

	public void solve() {
		FastScanner sc = new FastScanner(System.in);
		int N = sc.nextInt();
		String S = sc.next();

		RollingHash rh = RollingHash.create(N, RollingHash.MOD1L61);
		rh.add(S);
		int ans = 0;
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				while (i + ans + 1 <= j && j + ans + 1 <= N) {
					if (rh.hash(i, ans + 1) != rh.hash(j, ans + 1)) break;
					ans++;
				}
			}
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

class RollingHash {
	public static final long MOD1L61 = (1L << 61) - 1;
	public static final long MOD998244353 = 998244353;
	public static final long MOD1000000007 = 1000000007;
	public static final long MOD1000000009 = 1000000009;
	public static final long MOD1000000021 = 1000000021;
	public static final long MOD1000000033 = 1000000033;
	public static final long MOD1000000087 = 1000000087;

	private long m = 0;
	private long b = 0;
	private long[] pow = null;
	private long[] hash = null;
	private int hlen = 0;
	private int pos = 0;
	private static final int DEFAULT_MAX_VALUE = 2000000;

	public static RollingHash createWithBase(int n, long b, long m) {
		RollingHash ret = m == MOD1L61 ? new RollingHash61(m) : new RollingHash(m);
		ret.prepare(n, b);
		return ret;
	}
	public static RollingHash create(int n, long m) {
		return create(n, m, DEFAULT_MAX_VALUE);
	}
	public static RollingHash create(int n, long m, int maxVal) {
		RollingHash ret = m == MOD1L61 ? new RollingHash61(m) : new RollingHash(m);
		ret.prepare(n, ret.findBase(m, maxVal));
		return ret;
	}

	protected RollingHash(long m) {
		this.m = m;
	}
	protected void prepare(int n, long b) {
		this.b = b;
		this.hlen = n + 1;
		pow = new long[n + 1];
		hash = new long[n + 1];
		pow[0] = 1;
		pos = 0;
		hash[pos] = 0;
		for (int i = 1; i <= n; i++) {
			pow[i] = mul(pow[i - 1], b);
		}
	}

	public void add(String s) {
		add((i) -> s.charAt(i), s.length());
	}
	public void add(int[] array) {
		add((i) -> array[i], array.length);
	}
	public void add(java.util.function.IntUnaryOperator op, int len) {
		for (int i = 0; i < len; i++) add(op.applyAsInt(i));
	}
	public void add(int v) {
		pos++;
		hash[pos % hlen] = mod(mul(hash[(pos - 1) % hlen], b) + v);
	}
	public long hash(int i0, int len) {
		assert 0 <= len && len < hlen;
		assert Math.max(pos - hlen + 1, 0) <= i0 && i0 + len <= pos;
		return mod(hash[(i0 + len) % hlen] + m - mul(hash[i0 % hlen], pow[len]));
	}

	protected long mul(long x, long y) {
		return (x * y) % m;
	}
	protected long mod(long x) {
		return x % m;
	}
	protected long pow(long x, long n) {
		long c = x, r = 1;
		while (n > 0) {
			if ((n & 1L) != 0) r = mul(r, c);
			c = mul(c, c);
			n >>>= 1;
		}
		return r;
	}
	protected long findBase(long m, int maxV) {
		java.util.Random rnd = new java.util.Random();
		long mMax = Math.min(m, Integer.MAX_VALUE);
		long root = findPrimitiveRoot(m);
		while (true) {
			int k = rnd.nextInt((int)(mMax - maxV - 2)) + maxV + 1;
			if (gcd(m - 1, k) != 1) continue;
			long b = pow(root, k);
			if (b > maxV) return b;
		}
	}
	private static long findPrimitiveRoot(long m) {
		if (m == MOD1L61) return 37;
		else if (m == MOD998244353) return 3;
		else if (m == MOD1000000007) return 5;
		else if (m == MOD1000000009) return 13;
		else if (m == MOD1000000021) return 2;
		else if (m == MOD1000000033) return 5;
		else if (m == MOD1000000087) return 3;
		throw new IllegalArgumentException("Base must be specified.");
	}
	private static long gcd(long a, long b) {
		while ( b != 0 ) {
			long tmp = b;
			b = a % b;
			a = tmp;
		}
		return a;
	}
}
class RollingHash61 extends RollingHash {
	private static final long MOD61 = MOD1L61;
	private static final long MASK30 = (1L << 30) - 1;
	private static final long MASK31 = (1L << 31) - 1;
	private static final long MASK61 = MOD1L61;

	protected RollingHash61(long m) {
		super(m);
	}
	@Override
	protected long mul(long x, long y) {
		return mul61(x, y);
	}
	@Override
	protected long mod(long x) {
		return mod61(x);
	}
	private static long mul61(long x, long y) {
		long au = x >> 31;
		long ad = x & MASK31;
		long bu = y >> 31;
		long bd = y & MASK31;
		long mid = ad * bu + au * bd;
		long midu = mid >> 30;
		long midd = mid & MASK30;
		long res = mod61(au * bu * 2 + midu + (midd << 31) + ad * bd);
		return res;
	}
	private static long mod61(long x) {
		long xu = x >>> 61;
		long xd = x & MASK61;
		long res = xu + xd;
		if (res >= MOD61) res -= MOD61;
		return res;
	}
}
