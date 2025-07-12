import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

public class Main {
	public static void main(String[] args) {
		Main o = new Main();
		o.solve();
	}

	public void solve() {
		FastScanner sc = new FastScanner(System.in);
		int n = sc.nextInt();
		DynamicSegmentTree<Integer> tree = new DynamicSegmentTree<>(0, 1000000001, (a, b) -> a + b, () -> 0);
		long ans = 0;
		for (int i = 0; i < n; i++) {
			int t = sc.nextInt();
			if ( t == 0 ) {
				int x = sc.nextInt();
				int y = sc.nextInt();
				tree.set(x, tree.get(x) + y);
			} else {
				int l = sc.nextInt();
				int r = sc.nextInt();
				ans += tree.query(l, r + 1);
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

class DynamicSegmentTree<S> {
	private long L = 0;
	private long R = 0;
	private BinaryOperator<S> _op = null;
	private Supplier<S> _e = null;
	private Node root = null;

	S op(S a, S b) {
		return _op.apply(a, b);
	}
	S e() {
		return _e.get();
	}

	public DynamicSegmentTree(long L, long R, BinaryOperator<S> op, Supplier<S> e) {
		this.L = L;
		this.R = R;
		this._op = op;
		this._e = e;
	}
	public S get(long p) {
		if ( root == null ) return e();
		return root.get(L, R, p);
	}
	public void set(long p, S x) {
		if ( root == null ) {
			root = new Node(p, x);
			return;
		}
		root.set(L, R, p, x);
	}
	public S query(long l, long r) {
		if ( root == null ) return e();
		return root.query(L, R, l, r);
	}

	private class Node {
		private Node left = null;
		private Node right = null;
		private S data = null;
		private S val = null;
		private long index = 0;

		private Node(long index, S val) {
			this.index = index;
			this.val = val;
			this.data = val;
		}
		private void update() {
			data = left == null ? val : (op(left.data, val));
			if ( right != null ) data = op(data, right.data);
		}

		private void set(long p0, long p1, long p, S x) {
			if ( p == index ) {
				val = x;
				update();
				return;
			}
			long m = (p0 + p1) >> 1;
			if ( p < m ) {
				if ( index < p ) {
					if ( left == null ) {
						left = new Node(index, val);
					} else {
						left.set(p0, m, index, val);
					}
					index = p; val = x;
				} else {
					if ( left == null ) {
						left = new Node(p, x);
					} else {
						left.set(p0, m, p, x);
					}
				}
			} else {
				if ( p < index ) {
					if ( right == null ) {
						right = new Node(index, val);
					} else {
						right.set(m, p1, index, val);
					}
					index = p; val = x;
				} else {
					if ( right == null ) {
						right = new Node(p, x);
					} else {
						right.set(m, p1, p, x);
					}
				}
			}
			update();
		}
		private S get(long p0, long p1, long p) {
			if ( p == index ) return val;
			long m = (p0 + p1) >> 1;
			if ( p < m ) {
				return left == null ? e() : left.get(p0, m, p);
			} else {
				return right == null ? e() : right.get(m, p1, p);
			}
		}
		private S query(long p0, long p1, long l, long r) {
			if ( p1 <= l || r <= p0 ) return e();
			if ( l <= p0 && p1 <= r ) return data;
			long m = (p0 + p1) >> 1;
			S ret = left == null ? e() : left.query(p0, m, l, r);
			if ( l <= index && index < r ) ret = op(ret, val);
			return right == null || r <= m ? ret : op(ret, right.query(m, p1, l, r));
		}
	}
}
