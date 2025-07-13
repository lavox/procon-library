import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
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
		int K = sc.nextInt();
		int[][] A = sc.nextIntMatrix(N, N);
		long M = 1000000000;

		MinCostFlow mcf = new MinCostFlow(2 * N + 2);
		int s = 2 * N;
		int t = 2 * N + 1;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				mcf.addEdge(i, N + j, 1, M - A[i][j]);
			}
		}
		for (int i = 0; i < N; i++) {
			mcf.addEdge(s, i, K, 0);
			mcf.addEdge(N + i, t, K, 0);
		}
		mcf.addEdge(s, t, N * K, M);
		MinCostFlow.Flow flow = mcf.flow(s, t, K * N);
		BitSet used = new BitSet(N * N);
		for (int i = 0; i < N * N; i++) {
			MinCostFlow.Edge e = mcf.getEdge(i);
			if ( e.flow > 0 ) used.set(i);
		}
		StringBuilder out = new StringBuilder();
		out.append(M * K * N - flow.cost);
		for (int i = 0; i < N; i++) {
			out.append(LF);
			for (int j = 0; j < N; j++) {
				out.append(used.get(i * N + j) ? 'X' : '.');
			}
		}
		System.out.println(out.toString());
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
class MinCostFlow {
	private int _n = 0;
	private ArrayList<Edge> _edges = null;

	public MinCostFlow(int n) {
		this._n = n;
		this._edges = new ArrayList<>();
	}

	public int addEdge(int from, int to, long cap, long cost) {
		assert 0 <= from && from < _n;
		assert 0 <= to && to < _n;
		assert 0 <= cap;
		assert 0 <= cost;
		int m = _edges.size();
		_edges.add(new Edge(from, to, cap, cost));
		return m;
	}

	public Edge getEdge(int i) {
		assert 0 <= i && i < _edges.size();
		return _edges.get(i);
	}
	public ArrayList<Edge> edges() { return _edges; }

	public Flow flow(int s, int t) {
		return flow(s, t, Long.MAX_VALUE);
	}
	public Flow flow(int s, int t, long flowLimit) {
		ArrayList<Flow> f = slope(s, t, flowLimit);
		return f.get(f.size() - 1);
	}
	public ArrayList<Flow> slope(int s, int t) {
		return slope(s, t, Long.MAX_VALUE);
	}
	public ArrayList<Flow> slope(int s, int t, long flowLimit) {
		assert 0 <= s && s < _n;
		assert 0 <= t && t < _n;
		assert s != t;

		int m = _edges.size();
		ArrayList<_Edge>[] g = new ArrayList[_n];
		_Edge[] edges = new _Edge[m];
		for (int i = 0; i < _n; i++) g[i] = new ArrayList<>();
		for (int i = 0; i < m; i++) {
			Edge e = _edges.get(i);
			_Edge e1 = new _Edge(e.to, e.cap - e.flow, e.cost);
			_Edge e2 = new _Edge(e.from, e.flow, -e.cost);
			e1.rev = e2;
			e2.rev = e1;
			g[e.from].add(e1);
			g[e.to].add(e2);
			edges[i] = e1;
		}

		ArrayList<Flow> result = slope(g, s, t, flowLimit);
		for (int i = 0; i < m; i++) {
			_Edge e = edges[i];
			_edges.get(i).flow = _edges.get(i).cap - e.cap;
		}
		return result;
	}

	boolean dualRef(ArrayList<_Edge>[] g, int s, int t, Flow[] dual_dist, _Edge[] prev_e, BitSet vis, int[] que_min, PriorityQueue<Q> que) {
		for (int i = 0; i < _n; i++) dual_dist[i].cost = Long.MAX_VALUE;
		vis.clear();
		int qr = 0, qw = 0;
		que.clear();

		dual_dist[s].cost = 0;
		que_min[qw++] = s;
		while ( qw > qr || que.size() > 0 ) {
			int v;
			if (qw > qr) {
				v = que_min[qr++];
			} else {
				Q q = que.poll();
				v = q.to;
			}
			if (vis.get(v)) continue;
			vis.set(v);
			if (v == t) break;
			long dual_v = dual_dist[v].flow;
			long dist_v = dual_dist[v].cost;
			for (_Edge e: g[v]) {
				if (e.cap == 0) continue;
				long cost = e.cost - dual_dist[e.to].flow + dual_v;
				if (dual_dist[e.to].cost - dist_v > cost) {
					long dist_to = dist_v + cost;
					dual_dist[e.to].cost = dist_to;
					prev_e[e.to] = e.rev;
					if (dist_to == dist_v) {
						que_min[qw++] = e.to;
					} else {
						que.add(new Q(dist_to, e.to));
					}
				}
			}
		}
		if (!vis.get(t)) {
			return false;
		}

		for (int v = 0; v < _n; v++) {
			if (!vis.get(v)) continue;
			dual_dist[v].flow -= dual_dist[t].cost - dual_dist[v].cost;
		}
		return true;
	}

	private ArrayList<Flow> slope(ArrayList<_Edge>[] g, int s, int t, long flowLimit) {
		Flow[] dual_dist = new Flow[_n];
		for (int i = 0; i < _n; i++) dual_dist[i] = new Flow(0, 0);
		_Edge[] prev_e = new _Edge[_n];
		BitSet vis = new BitSet(_n);
		int[] que_min = new int[_n];
		PriorityQueue<Q> que = new PriorityQueue<>(Comparator.reverseOrder());

		long flow = 0;
		long cost = 0, prev_cost_per_flow = -1;
		ArrayList<Flow> result = new ArrayList<>();
		result.add(new Flow(0, 0));
		while (flow < flowLimit) {
			if (!dualRef(g, s, t, dual_dist, prev_e, vis, que_min, que)) break;
			long c = flowLimit - flow;
			for (int v = t; v != s; v = prev_e[v].to) {
				c = Math.min(c, prev_e[v].rev.cap);
			}
			for (int v = t; v != s; v = prev_e[v].to) {
				_Edge e = prev_e[v];
				e.cap += c;
				e.rev.cap -= c;
			}
			long d = -dual_dist[s].flow;
			flow += c;
			cost += c * d;
			if (prev_cost_per_flow == d) {
				result.remove(result.size() - 1);
			}
			result.add(new Flow(flow, cost));
			prev_cost_per_flow = d;
		}
		return result;
	}

	public class Edge {
		int from, to;
		long cap, flow;
		long cost;
		Edge(int from, int to, long cap, long cost) {
			this.from = from;
			this.to = to;
			this.cap = cap;
			this.flow = 0;
			this.cost = cost;
		}
	}
	public class Flow {
		long flow;
		long cost;
		Flow(long flow, long cost) {
			this.flow = flow;
			this.cost = cost;
		}
	}
	private class _Edge {
		int to;
		_Edge rev;
		long cap, cost;
		_Edge(int to, long cap, long cost) {
			this.to = to;
			this.cap = cap;
			this.cost = cost;
		}
	}
	private class Q implements Comparable<Q> {
		long key;
		int to;
		Q(long key, int to) {
			this.key = key;
			this.to = to;
		}
		@Override
		public int compareTo(Q o) {
			if ( key != o.key ) {
				return key > o.key ? -1 : 1;
			} else {
				return 0;
			}
		}
	}
}
