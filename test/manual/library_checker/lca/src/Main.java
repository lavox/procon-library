import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

import java.util.ArrayDeque;
import java.util.Arrays;

// https://github.com/lavox/procon-library
public class Main {
	public static void main(String[] args) {
		Main o = new Main();
		o.solve();
	}

	public void solve() {
		FastScanner sc = new FastScanner(System.in);
		int N = sc.nextInt();
		int Q = sc.nextInt();
		Graph g = new Graph(N);
		for (int i = 0; i < N - 1; i++) {
			int p = sc.nextInt();
			g.addUndirEdge(i + 1, p);
		}
		Lca lca = new Lca(g, 0);
		StringBuilder out = new StringBuilder();
		for (int q = 0; q < Q; q++) {
			int u = sc.nextInt();
			int v = sc.nextInt();
			out.append(lca.lca(u, v));
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

// === begin: graph/Lca.java ===
class Lca {
	private Graph g = null;
	private int[] depth = null;
	private int[][] anc = null;
	private int K = 1;
	private int[] kmax = null;

	public Lca(Graph g, int root) {
		this.g = g;
		this.K = 1;
		while ((1 << this.K) < g.size()) this.K++;
		build(root);
	}
	private void build(int root) {
		int n = g.size();
		depth = new int[n];
		anc = new int[K][n];
		kmax = new int[n];
		Arrays.fill(depth, -1);
		for (int k = 0; k < K; k++) Arrays.fill(anc[k], -1);
		ArrayDeque<Integer> stack = new ArrayDeque<>();
		depth[root] = 0;
		stack.add(root);
		while (stack.size() > 0) {
			int pos = stack.pollFirst();
			kmax[pos] = 31 - Integer.numberOfLeadingZeros(depth[pos]);
			for (Edge e: g.edges(pos)) {
				int child = e.to();
				if (depth[child] != -1) continue;
				depth[child] = depth[pos] + 1;
				anc[0][child] = pos;
				stack.addLast(child);
			}
		}
		for (int k = 1; k < K; k++) {
			for (int i = 0; i < n; i++) {
				if (anc[k - 1][i] >= 0) {
					anc[k][i] = anc[k - 1][anc[k - 1][i]];
				}
			}
		}
	}
	public int lca(int u, int v) {
		if (depth[u] < depth[v]) return lca(v, u);
		for (int dd = depth[u] - depth[v], k = 0; dd > 0; dd &= ~(1 << k)) {
			k = Integer.numberOfTrailingZeros(dd);
			u = anc[k][u];
		}
		if (u == v) return u;
		for (int k = kmax[u]; k >= 0; k--) {
			if (anc[k][u] != anc[k][v]) {
				u = anc[k][u];
				v = anc[k][v];
			}
		}
		return anc[0][u];
	}
	public int depth(int u) {
		return depth[u];
	}
	public int ancestor(int u, int d) {
		if (d > depth[u]) return -1;
		while (d > 0) {
			int k = Integer.numberOfTrailingZeros(d);
			u = anc[k][u];
			d &= ~(1 << k);
		}
		return u;
	}
}
// === end: graph/Lca.java ===

// === begin: graph/Graph.java ===
class Graph {
	private int n;
	private ArrayList<Edge>[] edges;
	
	@SuppressWarnings("unchecked")
	public Graph(int n) {
		this.n = n;
		edges = new ArrayList[n];
		for (int i = 0; i < n; i++) edges[i] = new ArrayList<>();
	}
	public void addDirEdge(Edge e) {
		edges[e.from()].add(e);
	}
	public void addDirEdge(int from, int to) {
		edges[from].add(new Edge(from, to, 0));
	}
	public void addDirEdge(int from, int to, int id) {
		edges[from].add(new Edge(from, to, id));
	}
	public void addUndirEdge(int u, int v) {
		edges[u].add(new Edge(u, v, 0));
		edges[v].add(new Edge(v, u, 0));
	}
	public void addUndirEdge(int u, int v, int id) {
		edges[u].add(new Edge(u, v, id));
		edges[v].add(new Edge(v, u, id));
	}
	public int edgeSize(int v) {
		return edges[v].size();
	}
	public Edge edge(int v, int i) {
		return edges[v].get(i);
	}
	public ArrayList<Edge> edges(int v) {
		return edges[v];
	}
	public int[] edgesTo(int v) {
		int[] ret = new int[edgeSize(v)];
		for (int i = 0; i < ret.length; i++) ret[i] = edges[v].get(i).to();
		return ret;
	}
	public int size() {
		return n;
	}
}
// === end: graph/Graph.java ===

// === begin: graph/Edge.java ===
class Edge {
	private final int from;
	private final int to;
	private final int id;
	public Edge(int from, int to, int id) {
		this.from = from;
		this.to = to;
		this.id = id;
	}
	public int from() {
		return from;
	}
	public int to() {
		return to;
	}
	public int id() {
		return id;
	}
}
// === end: graph/Edge.java ===
