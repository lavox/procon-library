import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

import java.util.function.IntFunction;
import java.util.function.Supplier;

// template & library : https://github.com/lavox/procon-library
public class Main {
	public static void main(String[] args) {
		Main o = new Main();
		o.solve();
	}

	static final int MOD = 998244353;
	public void solve() {
		FastScanner sc = new FastScanner(System.in);
		int N = sc.nextInt();
		int Q = sc.nextInt();
		WeightedUnionFind uf = new WeightedUnionFind(N, () -> new Value(0));
		int[] ans = new int[Q];
		for (int q = 0; q < Q; q++) {
			int t = sc.nextInt();
			if (t == 0) {
				int u = sc.nextInt();
				int v = sc.nextInt();
				int x = sc.nextInt();
				ans[q] = uf.unite(v, u, new Value(x)) ? 1 : 0;
			} else {
				int u = sc.nextInt();
				int v = sc.nextInt();
				if (uf.isSame(u, v)) {
					ans[q] = ((Value)uf.diff(v, u)).x;
				} else {
					ans[q] = -1;
				}
			}
		}
		print(ans, LF);
	}

	class Value implements WeightedUnionFind.Weight {
		int x = 0;
		Value(int x) {
			this.x = x;
		}
		public Value op(WeightedUnionFind.Weight o) {
			Value v = (Value)o;
			int nx = x + v.x;
			if (nx >= MOD) nx -= MOD;
			return new Value(nx);
		}
		public Value inv() {
			return x == 0 ? new Value(0) : new Value(MOD - x);
		}
		public boolean equals(WeightedUnionFind.Weight o) {
			Value v = (Value)o;
			return x == v.x;
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

// === begin: data_structure/WeightedUnionFind.java ===
class WeightedUnionFind {
	private int[] parent = null;
	private int[] size = null;
	private Data[] data = null;
	private Weight[] weight = null;
	
	public WeightedUnionFind(int N, Supplier<Weight> e) {
		this(N, e, null);
	}
	public WeightedUnionFind(int N, Supplier<Weight> e, IntFunction<Data> constructor) {
		parent = new int[N];
		size = new int[N];
		for (int i = 0; i < N; i++) {
			parent[i] = i;
			size[i] = 1;
		}
		weight = new Weight[N];
		for (int i = 0; i < N; i++) {
			weight[i] = e.get();
		}
		if (constructor != null) {
			data = new Data[N];
			for (int i = 0; i < N; i++) {
				data[i] = constructor.apply(i);
			}
		}
	}
	public int root(int i) {
		if (parent[i] == i) {
			return i;
		} else if (parent[parent[i]] == parent[i]) {
			return parent[i];
		} else {
			int r = root(parent[i]);
			weight[i] = weight[parent[i]].op(weight[i]);
			parent[i] = r;
			return r;
		}
	}
	public int size(int i) {
		return size[root(i)];
	}
	public Data data(int i) {
		return data[root(i)];
	}
	public Weight weight(int i) {
		root(i);
		return weight[i];
	}
	// diff = - weight(i) + weight(j)
	public Weight diff(int i, int j) {
		if (!isSame(i, j)) return null;
		return weight[i].inv().op(weight[j]);
	}
	// weight(i) + d = weight(j)
	public boolean unite(int i, int j, Weight d) {
		int ri = root(i);
		int rj = root(j);
		if (ri == rj) {
			return weight[i].op(d).equals(weight[j]);
		} else {
			if (size[ri] < size[rj]) {
				parent[ri] = rj;
				size[rj] += size[ri];
				if (data != null) {
					data[rj].merge(data[ri]);
				}
				weight[ri] = weight[j].op(weight[i].op(d).inv());
			} else {
				parent[rj] = ri;
				size[ri] += size[rj];
				if (data != null) {
					data[ri].merge(data[rj]);
				}
				weight[rj] = weight[i].op(d).op(weight[j].inv());
			}
			return true;
		}
	}
	public boolean isSame(int i, int j) {
		return root(i) == root(j);
	}
	public interface Data {
		public void merge(Data o);
	}
	public interface Weight {
		public Weight inv();
		public Weight op(Weight o);
		public boolean equals(Weight o);
	}
}
// === end: data_structure/WeightedUnionFind.java ===
