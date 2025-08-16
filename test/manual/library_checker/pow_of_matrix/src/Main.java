import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

import java.util.Arrays;


// https://github.com/lavox/procon-library
public class Main {
	public static void main(String[] args) {
		Main o = new Main();
		o.solve();
	}

	static final long MOD = 998244353;
	public void solve() {
		FastScanner sc = new FastScanner(System.in);
		int N = sc.nextInt();
		long K = sc.nextLong();
		Matrix a = new Matrix(N, N);
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				a.set(i, j, sc.nextInt());
			}
		}
		Matrix b = a.powMod(K, MOD);

		StringBuilder out = new StringBuilder();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				out.append(b.get(i, j));
				out.append(' ');
			}
			out.append('\n');
		}
		System.out.print(out.toString());
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

// === begin: math/Matrix.java ===
class Matrix {
	long[][] a = null;
	public Matrix(int H, int W) {
		a = new long[H][W];
	}
	public Matrix(long[][] a) {
		this.a = new long[a.length][];
		for (int i = 0; i < a.length; i++) {
			this.a[i] = Arrays.copyOf(a[i], a[i].length);
		}
	}
	public long get(int i, int j) {
		return a[i][j];
	}
	public void set(int i, int j, long v) {
		a[i][j] = v;
	}
	public Matrix add(Matrix o) {
		assert a.length == o.a.length;
		if ( a.length == 0 ) return new Matrix(0, 0);
		assert a[0].length == o.a[0].length;
		Matrix ret = new Matrix(a.length, a[0].length);
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				ret.a[i][j] = a[i][j] + o.a[i][j];
			}
		}
		return ret;
	}
	public Matrix addMod(Matrix o, long p) {
		assert a.length == o.a.length;
		if ( a.length == 0 ) return new Matrix(0, 0);
		assert a[0].length == o.a[0].length;
		Matrix ret = new Matrix(a.length, a[0].length);
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				ret.a[i][j] = (a[i][j] + o.a[i][j]) % p;
			}
		}
		return ret;
	}
	public Matrix mul(Matrix o) {
		if ( o.a.length == 0 ) return new Matrix(0, 0);
		Matrix ret = new Matrix(a.length, o.a[0].length);
		for ( int i = 0 ; i < a.length ; i++ ) {
			for ( int j = 0 ; j < o.a[0].length ; j++ ) {
				for ( int k = 0 ; k < a[i].length ; k++ ) {
					ret.a[i][j] += a[i][k] * o.a[k][j];
				}
			}
		}
		return ret;
	}
	public Matrix mulMod(Matrix o, long p) {
		if ( o.a.length == 0 ) return new Matrix(0, 0);
		Matrix ret = new Matrix(a.length, o.a[0].length);
		for ( int i = 0 ; i < a.length ; i++ ) {
			for ( int j = 0 ; j < o.a[0].length ; j++ ) {
				for ( int k = 0 ; k < a[i].length ; k++ ) {
					ret.a[i][j] = (ret.a[i][j] + a[i][k] * o.a[k][j]) % p;
				}
			}
		}
		return ret;
  }
	public Matrix mul(long c) {
		if ( a.length == 0 ) return new Matrix(0, 0);
		Matrix ret = new Matrix(a.length, a[0].length);
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				ret.a[i][j] = c * a[i][j];
			}
		}
		return ret;
  }
	public Matrix mulMod(long c, long p) {
		if ( a.length == 0 ) return new Matrix(0, 0);
		Matrix ret = new Matrix(a.length, a[0].length);
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				ret.a[i][j] = (c * a[i][j]) % p;
			}
		}
		return ret;
	}
	public Vec mul(Vec v) {
		if ( a.length == 0 ) return new Vec(0);
		Vec ret = new Vec(a.length);
		for (int i = 0; i < a.length; i++) {
			for ( int k = 0 ; k < a[i].length ; k++ ) {
				ret.a[i] += a[i][k] * v.a[k];
			}
		}
		return ret;
	}
	public Vec mulMod(Vec v, long p) {
		if ( a.length == 0 ) return new Vec(0);
		Vec ret = new Vec(a.length);
		for (int i = 0; i < a.length; i++) {
			for ( int k = 0 ; k < a[i].length ; k++ ) {
				ret.a[i] = (ret.a[i] + a[i][k] * v.a[k]) % p;
			}
		}
		return ret;
	}
	public Matrix pow(long n) {
		Matrix ret = new Matrix(a.length, a.length);
		Matrix A = new Matrix(this.a);
		for ( int i = 0 ; i < a.length ; i++ ) ret.a[i][i] = 1; 
		while ( n > 0 ) {
			if ( (n & 1L) != 0 ) ret = ret.mul(A);
			A = A.mul(A);
			n >>>= 1;
		}
		return ret;
	}
	public Matrix powMod(long n, long p) {
		Matrix ret = new Matrix(a.length, a.length);
		Matrix A = new Matrix(this.a);
		for ( int i = 0 ; i < a.length ; i++ ) ret.a[i][i] = 1; 
		while ( n > 0 ) {
			if ( (n & 1L) != 0 ) ret = ret.mulMod(A, p);
			A = A.mulMod(A, p);
			n >>>= 1;
		}
		return ret;
	}

	public boolean equals(Matrix o) {
		if ( a.length != o.a.length ) return false;
		for (int i = 0; i < a.length; i++) {
			if ( a[i].length != o.a[i].length ) return false;
			for (int j = 0; j < a[i].length; j++) {
				if ( a[i] != o.a[i] ) return false;
			}
		}
		return true;
	}
}
// === end: math/Matrix.java ===

// === begin: math/Vec.java ===
class Vec {
	long[] a = null;
	public Vec(int N) {
		a = new long[N];
	}
	public Vec(long[] a) {
		this.a = Arrays.copyOf(a, a.length);
	}
	public long get(int i) {
		return a[i];
	}
	public void set(int i, long c) {
		a[i] = c;
	}
	public Vec add(Vec o) {
		assert a.length == o.a.length;
		Vec ret = new Vec(a.length);
		for (int i = 0; i < a.length; i++) {
			ret.a[i] = a[i] + o.a[i];
		}
		return ret;
	}
	public Vec sub(Vec o) {
		assert a.length == o.a.length;
		Vec ret = new Vec(a.length);
		for (int i = 0; i < a.length; i++) {
			ret.a[i] = a[i] - o.a[i];
		}
		return ret;
	}
	public Vec mul(long c) {
		Vec ret = new Vec(a.length);
		for (int i = 0; i < a.length; i++) {
			ret.a[i] = a[i] * c;
		}
		return ret;
	}
	public long dot(Vec o) {
		assert a.length == o.a.length;
		long ret = 0;
		for (int i = 0; i < a.length; i++) {
			ret += a[i] * o.a[i];
		}
		return ret;
	}
	public long norm1() {
		long ret = 0;
		for (int i = 0; i < a.length; i++) {
			ret += Math.abs(a[i]);
		}
		return ret;
	}
	public long norm2Sq() {
		long ret = 0;
		for (int i = 0; i < a.length; i++) {
			ret += Math.abs(a[i] * a[i]);
		}
		return ret;
	}
	public double norm2() {
		return Math.sqrt(norm2Sq());
	}
	public long dist1(Vec o) {
		return sub(o).norm1();
	}
	public long dist2Sq(Vec o) {
		return sub(o).norm2Sq();
	}
	public double dist2(Vec o) {
		return Math.sqrt(dist2Sq(o));
	}

	public boolean equals(Vec o) {
		if ( a.length != o.a.length ) return false;
		for (int i = 0; i < a.length; i++) {
			if ( a[i] != o.a[i] ) return false;
		}
		return true;
	}
}
// === end: math/Vec.java ===
