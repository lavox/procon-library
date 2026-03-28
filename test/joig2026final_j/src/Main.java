import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

// template & library : https://github.com/lavox/procon-library
public class Main {
	public static void main(String[] args) {
		Main o = new Main();
		o.solve();
		// o.debug();
	}
	void debug() {
		Random rnd = new Random(123);
		for (int t = 0; t < 10000; t++) {
			int N = 2 + rnd.nextInt(10);
			int M = 2 + rnd.nextInt(10);
			int a0 = rnd.nextInt(10) + 1;
			int m = rnd.nextInt(10) + 1;
			long[] A = new long[N];
			Arrays.fill(A, a0);
			long[] B = new long[M];
			for (int i = 0; i < M; i++) {
				B[i] = 1 + rnd.nextInt(m);
			}
			long ans0 = solve4(N, M, A.clone(), B.clone());
			long ans1 = solve6(N, M, A.clone(), B.clone());
			if (ans0 != ans1) {
				System.out.println(String.format("wrong:%d", ans0));
				System.out.println(String.format("correct:%d", ans1));
				System.out.println(String.format("%d %d", N, M));
				print(A, SPACE);
				print(B, SPACE);
				return;
			}
		}
	}

	public void solve() {
		FastScanner sc = new FastScanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		long[] A = sc.nextLongArray(N);
		long[] B = sc.nextLongArray(M);
		boolean eq = true;
		long ans = 0;
		long acnt = 0;
		for (int i = 0; i < N; i++) acnt += A[i];
		long bcnt = 0;
		for (int i = 0; i < M; i++) bcnt += B[i];
		for (int i = 1; i < N; i++) {
			if (A[0] != A[i]) {
				eq = false;
				break;
			}
		}
		ans = solve7(N, M, A, B);
		// if ((acnt <= 500000 && bcnt <= 500000) || N <= 2 ) {
		// 	ans = solve6(N, M, A, B);
		// } else if (eq) {
		// 	ans = solve4(N, M, A, B);
		// }
		System.out.println(ans);
	}

	long solve4(int N, int M, long[] A, long[] B) {
		long ans = 0;
		Arrays.sort(B);
		int bi = 0;
		long bc = 0;
		for (int i = 0; i < M; i++) {
			if (bi >= A[0]) break;
			long b = Math.min(B[M - i - 1], N);
			long b0 = Math.min(N - bc, b);
			ans += b0;
			b -= b0;
			bc += b0;
			if (bc >= N) {
				bi++;
				bc = 0;
				if (bi >= A[0]) break;
				ans += b;
				bc += b;
			}
		}
		return ans;
	}

	long solve6(int N, int M, long[] A, long[] B) {
		long ans = 0;
		PriorityQueue<Rice> queue = new PriorityQueue<>(CMP);
		for (int i = 0; i < M; i++) {
			queue.add(new Rice(B[i], i));
		}
		Arrays.sort(A);
		for (int i = N - 1; i >= 0; i--) {
			ArrayList<Rice> keep = new ArrayList<>();
			for (int j = 0; j < A[i] && queue.size() > 0; j++) {
				Rice r = queue.poll();
				ans++;
				r.cnt--;
				if (r.cnt > 0) {
					keep.add(r);
				}
			}
			queue.addAll(keep);
		}
		return ans;
	}
	long solve7(int N, int M, long[] A, long[] B) {
		long ans = 0;
		Arrays.sort(A);
		Arrays.sort(B);
		A = reverse(A);
		B = reverse(B);
		int[] lim = new int[M];
		int bi = 0;
		lim[0] = N;
		for (int i = 1; i < M; i++) {
			int a = lim[i - 1];
			while (a > 0 && A[a - 1] <= i) a--;
			lim[i] = a;
		}

		long bc = 0;
		for (int i = 0; i < M; i++) {
			if (bi >= M || lim[bi] == 0) break;
			long b = Math.min(B[M - i - 1], lim[bi]);
			long b0 = Math.min(lim[bi] - bc, b);
			ans += b0;
			b -= b0;
			bc += b0;
			if (bc >= lim[bi]) {
				bi++;
				bc = 0;
				if (bi >= M || lim[bi] == 0) break;
				b = Math.min(b, lim[bi]);
				ans += b;
				bc += b;
				if (bc >= lim[bi]) {
					bi++;
					bc = 0;
				}
			}
		}
		return ans;
	}
	long[] reverse(long[] a) {
		long[] ret = new long[a.length];
		for (int i = 0; i < a.length; i++) {
			ret[i] = a[a.length - i - 1];
		}
		return ret;
	}

	static final Comparator<Rice> CMP = Comparator.<Rice>comparingLong(r -> -r.cnt).thenComparingInt(r -> r.id);
	static final Comparator<Rice> CMP2 = Comparator.<Rice>comparingLong(r -> r.cnt).thenComparingInt(r -> r.id);
	class Rice {
		long cnt = 0;
		int id = 0;
		Rice(long cnt, int id) {
			this.cnt = cnt;
			this.id = id;
		}
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
