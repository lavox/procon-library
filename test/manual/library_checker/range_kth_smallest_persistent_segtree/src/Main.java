import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

import java.util.Arrays;

import java.util.List;
import java.util.function.IntBinaryOperator;

import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;

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
		long[] a = sc.nextLongArray(N);
		Compression comp = new Compression(a);

		Number[] nums = new Number[N];
		for (int i = 0; i < N; i++) {
			nums[i] = new Number(i, comp.toIndex(a[i]));
		}
		Arrays.sort(nums, CMP);
		IntPersistentSegmentTree[] tree = new IntPersistentSegmentTree[comp.size()];
		tree[0] = new IntPersistentSegmentTree(N, (x, y) -> x + y, 0);
		for (Number n: nums) {
			int prev = tree[n.z] == null ? n.z - 1: n.z;
			tree[n.z] = tree[prev].update(n.i, 1);
		}

		long[] ans = new long[Q];
		for (int q = 0; q < Q; q++) {
			int l = sc.nextInt();
			int r = sc.nextInt();
			int k = sc.nextInt();
			int z = Bisect.maxTrueInt(-1, comp.size(), x -> tree[x].query(l, r) <= k);
			ans[q] = comp.toValue(z + 1);
		}
		print(ans, LF);
	}
	static final Comparator<Number> CMP = Comparator.<Number>comparingInt(n -> n.z).thenComparingInt(n -> n.i);
	class Number {
		int i = 0;
		int z = 0;
		Number(int i, int z) {
			this.i = i;
			this.z = z;
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

// === begin: data_structure/Compression.java ===
class Compression {
	long[] vals = null;
	
	public Compression(long[] vals) {
		long[] arr = Arrays.copyOf(vals, vals.length);
		int sz = 0;
		Arrays.sort(arr);
		for ( int i = 0 ; i < vals.length ; i++ ) {
			if ( i == 0 || arr[i] != arr[i - 1] ) sz++;
		}
		this.vals = new long[sz];
		int vi = 0;
		for ( int i = 0 ; i < vals.length ; i++ ) {
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

// === begin: math/Bisect.java ===
class Bisect {
	public static int minTrueInt(int low, int high, IntPredicate condition) {
		assert low <= high;
		while (high - low > 1) {
			int mid = low + (high - low) / 2;
			if (condition.test(mid)) {
				high = mid;
			} else {
				low = mid;
			}
		}
		return high;
	}
	public static long minTrueLong(long low, long high, LongPredicate condition) {
		assert low <= high;
		while (high - low > 1) {
			long mid = low + (high - low) / 2;
			if (condition.test(mid)) {
				high = mid;
			} else {
				low = mid;
			}
		}
		return high;
	}
	public static double minTrueDouble(double low, double high, double eps, DoublePredicate condition) {
		assert low <= high;
		while (high - low > eps) {
			double mid = low + (high - low) / 2;
			if (condition.test(mid)) {
				high = mid;
			} else {
				low = mid;
			}
		}
		return high;
	}
	public static int maxTrueInt(int low, int high, IntPredicate condition) {
		assert low <= high;
		while (high - low > 1) {
			int mid = low + (high - low) / 2;
			if (condition.test(mid)) {
				low = mid;
			} else {
				high = mid;
			}
		}
		return low;
	}
	public static long maxTrueLong(long low, long high, LongPredicate condition) {
		assert low <= high;
		while (high - low > 1) {
			long mid = low + (high - low) / 2;
			if (condition.test(mid)) {
				low = mid;
			} else {
				high = mid;
			}
		}
		return low;
	}
	public static double maxTrueDouble(double low, double high, double eps, DoublePredicate condition) {
		assert low <= high;
		while (high - low > eps) {
			double mid = low + (high - low) / 2;
			if (condition.test(mid)) {
				low = mid;
			} else {
				high = mid;
			}
		}
		return low;
	}
}
// === end: math/Bisect.java ===

// === begin: data_structure/segment_tree/IntPersistentSegmentTree.java ===
class IntPersistentSegmentTree {
	private final int n;
	private final Monoid monoid;
	private final Node root;

	public IntPersistentSegmentTree(int n, IntBinaryOperator op, int e) {
		this(n, i -> e, op, e);
	}
	public IntPersistentSegmentTree(int[] arr, IntBinaryOperator op, int e) {
		this(arr.length, i -> i < arr.length ? arr[i] : e, op, e);
	}
	public IntPersistentSegmentTree(List<Integer> arr, IntBinaryOperator op, int e) {
		this(arr.size(), i -> i < arr.size() ? arr.get(i): e, op, e);
	}
	public IntPersistentSegmentTree(int n, IntUnaryOperator dataProvider, IntBinaryOperator op, int e) {
		this(n, dataProvider, new Monoid(op, e));
	}
	private IntPersistentSegmentTree(int n, IntUnaryOperator dataProvider, Monoid monoid) {
		this.root = new Node(0, n, dataProvider, monoid);
		this.n = n;
		this.monoid = monoid;
	}
	private IntPersistentSegmentTree(IntPersistentSegmentTree from, Node root) {
		this.n = from.n;
		this.monoid = from.monoid;
		this.root = root;
	}

	public IntPersistentSegmentTree update(int p, int x) {
		if (p >= n) throw new IndexOutOfBoundsException();
		return new IntPersistentSegmentTree(this, root.update(p, x, 0, n, monoid));
	}
	public int get(int p) {
		if (p >= n) throw new IndexOutOfBoundsException();
		return root.get(p, 0, n);
	}
	public int query(int l, int r) {
		if (n == 0) return monoid.e();
		return root.query(l, r, 0, n, monoid);
	}
	public int allQuery() {
		return query(0, n);
	}

	private static class Node {
		private final int data;
		private final Node ln;
		private final Node rn;

		protected Node(int data, Node ln, Node rn) {
			this.data = data;
			this.ln = ln;
			this.rn = rn;
		}
		protected Node(int l, int r, IntUnaryOperator dataProvider, Monoid monoid) {
			if (l + 1 >= r) {
				this.ln = null;
				this.rn = null;
				this.data = l + 1 == r ? dataProvider.applyAsInt(l) : monoid.e();
			} else {
				int m = l + ((r - l) >> 1);
				this.ln = new Node(l, m, dataProvider, monoid);
				this.rn = new Node(m, r, dataProvider, monoid);
				this.data = monoid.op(ln.data, rn.data);
			}
		}
		protected Node createNode(int data, Node ln, Node rn) {
			return new Node(data, ln, rn);
		}
		protected Node merge(Node ln, Node rn, Monoid monoid) {
			return createNode(monoid.op(ln.data, rn.data), ln, rn);
		}
		protected int mid(int l, int r) {
			return l + ((r - l) >> 1);
		}

		protected int get(int p, int node_l, int node_r) {
			assert node_l <= p && p < node_r;
			if (node_l + 1 == node_r) return data;
			int node_m = mid(node_l, node_r);
			return p < node_m ? ln.get(p, node_l, node_m) : rn.get(p, node_m, node_r);
		}
		protected Node update(int p, int x, int node_l, int node_r, Monoid monoid) {
			assert node_l <= p && p < node_r;
			if (node_l + 1 == node_r) return createNode(x, null, null);
			int node_m = mid(node_l, node_r);
			return p < node_m 
				? merge(ln.update(p, x, node_l, node_m, monoid), rn, monoid) 
				: merge(ln, rn.update(p, x, node_m, node_r, monoid), monoid);
		}
		protected int query(int l, int r, int node_l, int node_r, Monoid monoid) {
			if (r <= node_l || node_r <= l) return monoid.e();
			else if (l <= node_l && node_r <= r) return data;
			else {
				int node_m = mid(node_l, node_r);
				return monoid.op(ln.query(l, r, node_l, node_m, monoid), rn.query(l, r, node_m, node_r, monoid));
			}
		}
	}

	private static class Monoid {
		private final IntBinaryOperator _op;
		private final int _e;
		protected Monoid(IntBinaryOperator _op, int _e) {
			this._op = _op;
			this._e = _e;
		}
		protected int e() {
			return _e;
		}
		protected int op(int a, int b) {
			return _op.applyAsInt(a, b);
		}
	}
}
// === end: data_structure/segment_tree/IntPersistentSegmentTree.java ===
