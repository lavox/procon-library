import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

import java.util.function.IntBinaryOperator;

// template & library : https://github.com/lavox/procon-library
public class Main {
	public static void main(String[] args) {
		Main o = new Main();
		o.solve();
	}

	public void solve() {
		FastScanner sc = new FastScanner(System.in);
		String S = sc.next();
		StringBuilder s2 = new StringBuilder(2 * S.length());
		for (int i = 0; i < S.length(); i++) {
			s2.append(S.charAt(i));
			s2.append('$');
		}
		s2.deleteCharAt(s2.length() - 1);
		int[] rad = Manacher.manacher(s2.toString());
		int[] ans = new int[rad.length];
		for (int i = 0; i < rad.length; i++) {
			if (i % 2 == 0) {
				ans[i] = (rad[i] - 1) / 2 * 2 + 1;
			} else {
				ans[i] = rad[i] / 2 * 2;
			}
		}
		print(ans, SPACE);
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

// === begin: string/Manacher.java ===
class Manacher {
	public static int[] manacher(IntBinaryOperator op, int n) {
		int[] ret = new int[n];
		int i = 0, j = 0;
		while (i < n) {
			while(i - j >= 0 && i + j < n && op.applyAsInt(i - j, i + j) == 0) {
				++j;
			}
			ret[i] = j;
			int k = 1;
			while(i - k >= 0 && i + k < n && k + ret[i - k] < j) {
				ret[i + k] = ret[i - k];
				++k;
			}
			i += k;
			j -= k;
		}
		return ret;
	}
	public static <T> int[] manacher(T[] s) {
		return manacher((i, j) -> s[i].equals(s[j]) ? 0 : -1, s.length);
	}
	public static int[] manacher(String s) {
		return manacher((i, j) -> s.charAt(i) == s.charAt(j) ? 0 : -1, s.length());
	}
}
// === end: string/Manacher.java ===
