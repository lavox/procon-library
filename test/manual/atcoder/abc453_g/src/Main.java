import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

import java.util.List;

import java.util.function.BinaryOperator;
import java.util.function.IntFunction;
import java.util.function.Supplier;

// template & library : https://github.com/lavox/procon-library
public class Main {
	public static void main(String[] args) {
		Main o = new Main();
		o.solve();
	}

	public void solve() {
		FastScanner sc = new FastScanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		PersistentSegmentTree<Long>[] A = new PersistentSegmentTree[N];
		A[0] = new PersistentSegmentTree<Long>(M, (a, b) -> a + b, () -> 0L);
		for (int i = 1; i < N; i++) {
			A[i] = A[0];
		}
		int Q = sc.nextInt();
		ArrayList<Long> ans = new ArrayList<>();
		for (int q = 0; q < Q; q++) {
			int k = sc.nextInt();
			if (k == 1) {
				int X = sc.nextInt() - 1;
				int Y = sc.nextInt() - 1;
				A[X] = A[Y];
			} else if (k == 2) {
				int X = sc.nextInt() - 1;
				int Y = sc.nextInt() - 1;
				long Z = sc.nextLong();
				A[X] = A[X].update(Y, Z);
			} else {
				int X = sc.nextInt() - 1;
				int L = sc.nextInt() - 1;
				int R = sc.nextInt() - 1;
				ans.add(A[X].query(L, R + 1));
			}
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

// === begin: data_structure/segment_tree/PersistentSegmentTree.java ===
class PersistentSegmentTree<S> {
	private final int n;
	private final Monoid<S> monoid;
	private final Node<S> root;

	public PersistentSegmentTree(int n, BinaryOperator<S> op, Supplier<S> e) {
		this(n, i -> e.get(), op, e);
	}
	public PersistentSegmentTree(S[] arr, BinaryOperator<S> op, Supplier<S> e) {
		this(arr.length, i -> i < arr.length ? arr[i] : e.get(), op, e);
	}
	public PersistentSegmentTree(List<S> arr, BinaryOperator<S> op, Supplier<S> e) {
		this(arr.size(), i -> i < arr.size() ? arr.get(i): e.get(), op, e);
	}
	public PersistentSegmentTree(int n, IntFunction<S> dataProvider, BinaryOperator<S> op, Supplier<S> e) {
		this(n, dataProvider, new Monoid<>(op, e));
	}
	private PersistentSegmentTree(int n, IntFunction<S> dataProvider, Monoid<S> monoid) {
		this.root = new Node<>(0, n, dataProvider, monoid);
		this.n = n;
		this.monoid = monoid;
	}
	private PersistentSegmentTree(PersistentSegmentTree<S> from, Node<S> root) {
		this.n = from.n;
		this.monoid = from.monoid;
		this.root = root;
	}

	public PersistentSegmentTree<S> update(int p, S x) {
		if (p >= n) throw new IndexOutOfBoundsException();
		return new PersistentSegmentTree<>(this, root.update(p, x, 0, n, monoid));
	}
	public S get(int p) {
		if (p >= n) throw new IndexOutOfBoundsException();
		return root.get(p, 0, n);
	}
	public S query(int l, int r) {
		if (n == 0) return monoid.e();
		return root.query(l, r, 0, n, monoid);
	}
	public S allQuery() {
		return query(0, n);
	}

	private static class Node<S> {
		private final S data;
		private final Node<S> ln;
		private final Node<S> rn;

		protected Node(S data, Node<S> ln, Node<S> rn) {
			this.data = data;
			this.ln = ln;
			this.rn = rn;
		}
		protected Node(int l, int r, IntFunction<S> dataProvider, Monoid<S> monoid) {
			if (l + 1 >= r) {
				this.ln = null;
				this.rn = null;
				this.data = l + 1 == r ? dataProvider.apply(l) : monoid.e();
			} else {
				int m = l + ((r - l) >> 1);
				this.ln = new Node<>(l, m, dataProvider, monoid);
				this.rn = new Node<>(m, r, dataProvider, monoid);
				this.data = monoid.op(ln.data, rn.data);
			}
		}
		protected Node<S> createNode(S data, Node<S> ln, Node<S> rn) {
			return new Node<>(data, ln, rn);
		}
		protected Node<S> merge(Node<S> ln, Node<S> rn, Monoid<S> monoid) {
			return createNode(monoid.op(ln.data, rn.data), ln, rn);
		}
		protected int mid(int l, int r) {
			return l + ((r - l) >> 1);
		}

		protected S get(int p, int node_l, int node_r) {
			assert node_l <= p && p < node_r;
			if (node_l + 1 == node_r) return data;
			int node_m = mid(node_l, node_r);
			return p < node_m ? ln.get(p, node_l, node_m) : rn.get(p, node_m, node_r);
		}
		protected Node<S> update(int p, S x, int node_l, int node_r, Monoid<S> monoid) {
			assert node_l <= p && p < node_r;
			if (node_l + 1 == node_r) return createNode(x, null, null);
			int node_m = mid(node_l, node_r);
			return p < node_m 
				? merge(ln.update(p, x, node_l, node_m, monoid), rn, monoid) 
				: merge(ln, rn.update(p, x, node_m, node_r, monoid), monoid);
		}
		protected S query(int l, int r, int node_l, int node_r, Monoid<S> monoid) {
			if (r <= node_l || node_r <= l) return monoid.e();
			else if (l <= node_l && node_r <= r) return data;
			else {
				int node_m = mid(node_l, node_r);
				return monoid.op(ln.query(l, r, node_l, node_m, monoid), rn.query(l, r, node_m, node_r, monoid));
			}
		}
	}

	private static class Monoid<S> {
		private final BinaryOperator<S> _op;
		private final Supplier<S> _e;
		protected Monoid(BinaryOperator<S> _op, Supplier<S> _e) {
			this._op = _op;
			this._e = _e;
		}
		protected S e() {
			return _e.get();
		}
		protected S op(S a, S b) {
			return _op.apply(a, b);
		}
	}
}
// === end: data_structure/segment_tree/PersistentSegmentTree.java ===
