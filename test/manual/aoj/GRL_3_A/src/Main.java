import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

import java.util.ArrayDeque;

import java.util.BitSet;

// https://github.com/lavox/procon-library
public class Main {
	public static void main(String[] args) {
		Main o = new Main();
		o.solve();
	}

	public void solve() {
		FastScanner sc = new FastScanner(System.in);
		int vc = sc.nextInt();
		int ec = sc.nextInt();
		Graph g = new Graph(vc);
		for (int i = 0; i < ec; i++) {
			int s = sc.nextInt();
			int t = sc.nextInt();
			g.addUndirEdge(s, t);
		}
		LowLink ll = new LowLink(g);
		ArrayList<LowLink.Node> art = ll.articulations();
		StringBuilder out = new StringBuilder();
		for (LowLink.Node n: art) {
			out.append(n.id());
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

// === begin: graph/LowLink.java ===
class LowLink {
	private Node[] nodes = null;
	private Graph g = null;
	private BitSet isBridge = null;
	private ArrayList<Edge> bridges = null;
	private ArrayList<Node> articulations = null;
	private int cnt = 0;
	private int componentCnt = 0;

	public LowLink(Graph g) {
		this.g = g;
		this.nodes = new Node[g.size()];
		for (int i = 0; i < g.size(); i++) nodes[i] = new Node(i);
		this.isBridge = new BitSet(g.maxEdgeId());
		build();
	}
	private void build() {
		bridges = new ArrayList<>();
		cnt = 0;
		for (Node n: nodes) {
			if (n.visited) continue;
			componentCnt++;
			dfs(n);
		}
		articulations = new ArrayList<>();
		for (Node n: nodes) {
			if (n.isArticulation) articulations.add(n);
		}
	}
	public int componentCnt() {
		return componentCnt;
	}
	public boolean isBridge(Edge e) {
		return isBridge.get(e.id());
	}
	public boolean isBridge(int eid) {
		return isBridge.get(eid);
	}
	public ArrayList<Edge> bridges() {
		return bridges;
	}
	public boolean isArticulation(int i) {
		return nodes[i].isArticulation;
	}
	public int articulationCnt(int i) {
		return nodes[i].artCnt;
	}
	public ArrayList<Node> articulations() {
		return articulations;
	}
	public Node node(int i) {
		return nodes[i];
	}
	private void dfs(Node n0) {
		ArrayDeque<Node> stack = new ArrayDeque<>();
		stack.addLast(n0);
		while ( stack.size() > 0 ) {
			Node n = stack.peekLast();
			int v = n.id;
			if (n.iter == 0) {
				n.visited = true;
				n.ord = cnt;
				n.low = cnt;
				cnt++;
			}
			if (n.iter > 0) {
				Edge e = g.edge(v, n.iter - 1);
				Node to = node(e.to());
				if (n.parent == null || n.parent != to) {
					n.low = Math.min(n.low, to.low);
				}
				if (n.parent != null && n.ord <= to.low) {
					n.isArticulation = true;
					n.artCnt++;
				}
				if (n.ord < to.low) {
					isBridge.set(e.id());
					bridges.add(e);
				}
			}
			boolean stacked = false;
			while (n.iter < g.edgeSize(v)) {
				Edge e = g.edge(v, n.iter++);
				Node to = node(e.to());
				if (n.parent != null && to == n.parent) continue;
				if (!to.visited) {
					stacked = true;
					n.childCnt++;
					to.parent = n;
					stack.addLast(to);
					break;
				} else {
					n.low = Math.min(n.low, to.ord);
				}
			}
			if (!stacked) {
				if (n.parent == null && n.childCnt >= 2) {
					n.isArticulation = true;
					n.artCnt += n.childCnt - 1;
				}
				stack.pollLast();
			}
		}
	}

	public class Node {
		private int id = 0;
		private Node parent = null;
		private int ord = 0;
		private int low = 0;
		private boolean visited = false;
		private boolean isArticulation = false;
		private int artCnt = 0;
		private int childCnt = 0;
		private int iter = 0;

		private Node(int id) {
			this.id = id;
		}
		public int id() {
			return id;
		}
		public boolean isArticulation() {
			return isArticulation;
		}
		public int articulationCnt() {
			return artCnt;
		}
	}
}
// === end: graph/LowLink.java ===

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

// === begin: graph/Graph.java ===
class Graph {
	private int n;
	private ArrayList<Edge>[] edges;
	private int maxEdgeId = 0;
	private int edgeCnt = 0;
	
	@SuppressWarnings("unchecked")
	public Graph(int n) {
		this.n = n;
		edges = new ArrayList[n];
		for (int i = 0; i < n; i++) edges[i] = new ArrayList<>();
	}
	public void addDirEdge(Edge e) {
		edges[e.from()].add(e);
		maxEdgeId = Math.max(maxEdgeId, e.id());
		edgeCnt++;
	}
	public void addDirEdge(int from, int to) {
		edges[from].add(new Edge(from, to, edgeCnt));
		maxEdgeId = Math.max(maxEdgeId, edgeCnt++);
	}
	public void addDirEdge(int from, int to, int id) {
		edges[from].add(new Edge(from, to, id));
		maxEdgeId = Math.max(maxEdgeId, id);
		edgeCnt++;
	}
	public void addUndirEdge(int u, int v) {
		edges[u].add(new Edge(u, v, edgeCnt++));
		edges[v].add(new Edge(v, u, edgeCnt));
		maxEdgeId = Math.max(maxEdgeId, edgeCnt++);
	}
	public void addUndirEdge(int u, int v, int id) {
		edges[u].add(new Edge(u, v, id));
		edges[v].add(new Edge(v, u, id));
		maxEdgeId = Math.max(maxEdgeId, id);
		edgeCnt += 2;
	}
	public int edgeSize(int v) {
		return edges[v].size();
	}
	public int edgeSize() {
		return edgeCnt;
	}
	public int maxEdgeId() {
		return maxEdgeId;
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
