import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

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
				return x.mergeSubtree(C[e.to().id()]);
			}
			@Override
			public Value nodeValue(Value x, Node n) {
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

abstract class Rerooting<E> {
	public abstract E e();
	public abstract E merge(E x, E y);
	public abstract E mergeSubtree(E x, Edge e);
	public abstract E nodeValue(E x, Node n);
	protected E leaf(Edge e) {
		return mergeSubtree(e(), e);
	}

	private ArrayList<Node> nodes = null;
	private ArrayList<Edge> edges = null;

	public Rerooting(int n) {
		nodes = new ArrayList<>(n);
		edges = new ArrayList<>(n - 1);
		for (int i = 0; i < n; i++) nodes.add(new Node(i));
	}
	private Node node(int i) {
		return nodes.get(i);
	}
	public void addEdge(int u, int v) {
		int eid = edges.size();
		Node nu = node(u);
		Node nv = node(v);
		Edge e = new Edge(eid, nu, nv);
		Edge re = new Edge(eid, nv, nu);
		e.rev = re;
		re.rev = e;
		nu.edge.add(e);
		nv.edge.add(re);
		edges.add(e);
	}
	public void build() {
		dfs(node(0));
		bfs(node(0));
	}
	public E nodeValue(int id) {
		return node(id).value;
	}
	public E edgeValue(int id, boolean rev) {
		return rev ? edges.get(id).dp : edges.get(id).rev.dp;
	}
	public ArrayList<Edge> edges() {
		return edges;
	}

	private void dfs(Node n0) {
		ArrayDeque<Node> stack = new ArrayDeque<>();
		stack.addLast(n0);
		while (stack.size() > 0) {
			Node n = stack.peekLast();
			if (n.iter < n.edge.size()) {
				Edge e = n.edge.get(n.iter++);
				if (n.parentEdge != null && n.parentEdge.from == e.to) continue;
				e.to.parentEdge = e;
				stack.add(e.to);
				continue;
			}
			if (n.parentEdge != null) {
				if (n.iter > 0) {
					E val = e();
					for (Edge e: n.edge) {
						if (n.parentEdge.from == e.to) continue;
						val = merge(val, e.dp);
					}
					n.parentEdge.dp = mergeSubtree(val, n.parentEdge);
				} else {
					n.parentEdge.dp = leaf(n.parentEdge);
				}
			}
			stack.pollLast();
		}
	}
	private void bfs(Node n0) {
		ArrayDeque<Node> queue = new ArrayDeque<>();
		queue.addLast(n0);
		while (queue.size() > 0) {
			Node n = queue.pollFirst();
			ArrayList<E> dpl = new ArrayList<>(n.edge.size() + 1);
			ArrayList<E> dpr = new ArrayList<>(n.edge.size() + 1);
			dpl.add(e());
			dpr.add(e());
			for (int i = 0; i < n.edge.size(); i++) {
				dpl.add(merge(dpl.get(i), n.edge(i).dp));
				dpr.add(merge(dpr.get(i), n.edge(n.edge.size() - 1 - i).dp));
			}
			for (int i = 0; i < n.edge.size(); i++) {
				Edge e = n.edge.get(i);
				if (n.parentEdge != null && n.parentEdge.from == e.to) continue;
				e.rev.dp = mergeSubtree(merge(dpl.get(i), dpr.get(n.edge.size() - 1 - i)), e.rev);
				queue.addLast(e.to);
			}
			n.value = nodeValue(dpl.get(n.edge.size()), n);
		}
	}

	public class Node {
		private int id;
		private ArrayList<Edge> edge;
		private Edge parentEdge = null;
		private int iter = 0;
		private Edge edge(int i) { return edge.get(i); }

		private E value = null;
		private Node(int id) {
			this.id = id;
			edge = new ArrayList<>();
		}
		public int id() {
			return id;
		}
	}
	public class Edge {
		private int id;
		private Node from;
		private Node to;
		private Edge rev = null;
		private E dp = null;
		private Edge(int id, Node from, Node to) {
			this.id = id;
			this.from = from;
			this.to = to;
		}
		public int id() {
			return id;
		}
		public Node from() {
			return from;
		}
		public Node to() {
			return to;
		}
	}
}
