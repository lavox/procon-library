import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

import java.util.BitSet;
import java.util.Iterator;

import java.lang.reflect.Array;

// https://github.com/lavox/procon-library
public class Main {
	public static void main(String[] args) {
		Main o = new Main();
		o.solve();
	}

	public void solve() {
		FastScanner sc = new FastScanner(System.in);
		int N = sc.nextInt();
		int[] A = new int[N - 1];
		int[] B = new int[N - 1];
		for (int i = 0; i < N - 1; i++) {
			A[i] = sc.nextInt() - 1;
			B[i] = sc.nextInt() - 1;
		}
		long[] C = sc.nextLongArray(N);
		Rerooting<Value> tree = new Rerooting<>(N) {
			@Override
			public Value e() { return new Value(); }
			@Override
			public Value merge(Value x, Value y) {
				return x.merge(y);
			}
			@Override
			public Value mergeSubtree(Value x, Edge e) {
				return x.mergeSubtree(C[e.to()]);
			}
			@Override
			public Value nodeValue(Value x, int v) {
				return new Value(x.csum, x.f);
			}
		};
		for (int i = 0; i < N - 1; i++) {
			tree.addEdge(A[i], B[i]);
		}
		tree.build();
		long ans = Long.MAX_VALUE;
		for (int i = 0; i < N; i++) {
			ans = Math.min(ans, tree.nodeValue(i).f);
		}
		System.out.println(ans);
	}

	class Value {
		long csum = 0;
		long f = 0;
		Value(long csum, long f) {
			this.csum = csum;
			this.f = f;
		}
		Value() {
		}
		Value merge(Value o) {
			return new Value(csum + o.csum, f + o.f);
		}
		Value mergeSubtree(long C) {
			return new Value(csum + C, f + csum + C);
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

// === begin: graph/Rerooting.java ===
abstract class Rerooting<E> {
	public abstract E e();
	public abstract E merge(E x, E y);
	public abstract E mergeSubtree(E x, Edge e);
	public abstract E nodeValue(E x, int v);
	protected E leaf(Edge e) {
		return mergeSubtree(e(), e);
	}

	private int edgeCnt = 0;
	private E[] values = null;
	private Edge[] edges = null;
	private E[] edgeValues = null;
	private Graph g = null;
	private Class<?> eclass = null;

	@SuppressWarnings("unchecked")
	public Rerooting(int n) {
		g = new Graph(n);
		edges = new Edge[2 * (n - 1)];
		eclass = e().getClass();
		values = (E[]) Array.newInstance(eclass, n);
		edgeValues = (E[]) Array.newInstance(eclass, 2 * (n - 1));
	}
	public void addEdge(int u, int v) {
		int eid = (edgeCnt++) * 2;
		Edge e = new Edge(u, v, eid);
		Edge re = new Edge(v, u, eid + 1);
		g.addDirEdge(e);
		g.addDirEdge(re);
		edges[e.id()] = e;
		edges[re.id()] = re;
	}
	public void build() {
		dfs(0);
		bfs(0);
	}
	public E nodeValue(int id) {
		return values[id];
	}
	public E edgeValue(Edge e) {
		return edgeValues[e.id()];
	}
	public E edgeValue(int id) {
		return edgeValues[id];
	}
	public E edgeValue(int id, boolean rev) {
		return rev ? edgeValues[id ^ 1] : edgeValues[id];
	}
	public Edge rev(Edge e) {
		return edges[e.id() ^ 1];
	}
	public Edge[] edges() {
		return edges;
	}

	private void dfs(int v0) {
		Dfs dfs = new Dfs(g);
		for (Dfs.DfsStep s: dfs.dfsPostOrder(v0)) {
			if (s.parent != -1) {
				E val = e();
				Edge pe = g.edge(s.parent, s.edgeIndex);
				if (g.edgeSize(s.cur) > 1) {
					for (Edge e: g.edges(s.cur)) {
						if (e.to() == s.parent) continue;
						val = merge(val, edgeValues[e.id()]);
					}
					edgeValues[pe.id()] = mergeSubtree(val, pe);
				} else {
					edgeValues[pe.id()] = leaf(pe);
				}
			}
		}
	}
	@SuppressWarnings("unchecked")
	private void bfs(int v0) {
		int[] queue = new int[g.size()];
		int ri = 0;
		int wi = 0;
		queue[wi++] = v0;
		BitSet visited = new BitSet(g.size());
		while (ri < wi) {
			int v = queue[ri++];
			visited.set(v);
			E[] dpl = (E[])Array.newInstance(eclass, g.edgeSize(v) + 1);
			E[] dpr = (E[])Array.newInstance(eclass, g.edgeSize(v) + 1);
			dpl[0] = e();
			dpr[0] = e();
			for (int i = 0; i < g.edgeSize(v); i++) {
				Edge e1 = g.edge(v, i);
				Edge e2 = g.edge(v, g.edgeSize(v) - 1 - i);
				dpl[i + 1] = merge(dpl[i], edgeValues[e1.id()]);
				dpr[i + 1] = merge(dpr[i], edgeValues[e2.id()]);
			}
			for (int i = 0; i < g.edgeSize(v); i++) {
				Edge e = g.edge(v, i);
				Edge re = rev(e);
				if (visited.get(e.to())) continue;
				edgeValues[re.id()] = mergeSubtree(merge(dpl[i], dpr[g.edgeSize(v) - 1 - i]), re);
				queue[wi++] = e.to();
			}
			values[v] = nodeValue(dpl[g.edgeSize(v)], v);
		}
	}
}
// === end: graph/Rerooting.java ===

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

// === begin: graph/Dfs.java ===
class Dfs {
	private final Graph g;
	private int[] visitedGen = null;
	int gen = 0;

	public Dfs(Graph g) {
		this.g = g;
		this.visitedGen = new int[g.size()];
	}
	public Iterable<DfsStep> dfsBothOrder(int v0) {
		gen++;
		return () -> new DfsIterator(v0, true, true);
	}
	public Iterable<DfsStep> dfsPreOrder(int v0) {
		gen++;
		return () -> new DfsIterator(v0, true, false);
	}
	public Iterable<DfsStep> dfsPostOrder(int v0) {
		gen++;
		return () -> new DfsIterator(v0, false, true);
	}
	private void setVisited(int nodeId) {
		visitedGen[nodeId] = gen;
	}
	private boolean isVisited(int nodeId) {
		return visitedGen[nodeId] >= gen;
	}

	public class DfsIterator implements Iterator<DfsStep> {
		private final ArrayDeque<DfsStep> stack;
		private final boolean requirePreOrder;
		private final boolean requirePostOrder;
		private DfsStep nextStep = null;
		private DfsIterator(int v0, boolean requirePreOrder, boolean requirePostOrder) {
			this.requirePreOrder = requirePreOrder;
			this.requirePostOrder = requirePostOrder;
			this.stack = new ArrayDeque<>();
			this.stack.addLast(new DfsStep(v0, -1, 0, true, 0));
			setVisited(v0);
			_next();
		}
		@Override
		public boolean hasNext() {
			return nextStep != null;
		}
		@Override
		public DfsStep next() {
			if (nextStep == null)
				throw new NoSuchElementException();
			DfsStep ret = nextStep;
			_next();
			return ret;
		}
		private void _next() {
			nextStep = null;
			while (stack.size() > 0 && nextStep == null) {
				DfsStep s = stack.pollLast();
				if (s.isPre) {
					stack.addLast(new DfsStep(s.cur, s.parent, s.edgeIndex, false, s.depth));
					addNextEdge(s.cur, 0, s.depth + 1);
				} else if (s.parent != -1) {
					addNextEdge(s.parent, s.edgeIndex + 1, s.depth);
				}
				if ((s.isPre && requirePreOrder) || (!s.isPre && requirePostOrder)) {
					nextStep = s;
				}
			}
		}
		private void addNextEdge(int v, int ei0, int depth) {
			for (int ei = ei0; ei < g.edgeSize(v); ei++) {
				Edge e = g.edge(v, ei);
				if (isVisited(e.to()))
					continue;
				stack.addLast(new DfsStep(e.to(), v, ei, true, depth));
				setVisited(e.to());
				return;
			}
		}
	}

	public class DfsStep {
		public final int cur;
		public final int parent;
		public final int edgeIndex;
		public final boolean isPre;
		public final int depth;

		private DfsStep(int cur, int parent, int edgeIndex, boolean isPre, int depth) {
			this.cur = cur;
			this.parent = parent;
			this.edgeIndex = edgeIndex;
			this.isPre = isPre;
			this.depth = depth;
		}
		public boolean isVisited(int nodeId) {
			return Dfs.this.isVisited(nodeId);
		}
	}
}
// === end: graph/Dfs.java ===

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
