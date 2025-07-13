import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
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

	static final int[][] DIR = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
	public void solve() {
		FastScanner sc = new FastScanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		String[] S = sc.nextStringArray(N);

		MaxFlow mf = new MaxFlow(N * M + 2);
		int s = N * M;
		int t = N * M + 1;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				int p = i * M + j;
				if ( (i + j) % 2 == 0 ) {
					mf.addEdge(s, p, 1);
				} else {
					mf.addEdge(p, t, 1);
				}
				if ( S[i].charAt(j) != '.' ) continue;
				for (int[] d: DIR) {
					int ni = i + d[0];
					int nj = j + d[1];
					if ( ni < 0 || ni >= N || nj < 0 || nj >= M ) continue;
					if ( S[ni].charAt(nj) != '.' ) continue;
					if ( (i + j) % 2 == 0 ) {
						mf.addEdge(p, ni * M + nj, 1);
					} else {
						mf.addEdge(ni * M + nj, p, 1);
					}
				}
			}
		}
		long ans = mf.flow(s, t, N * M / 2);
		char[][] map = new char[N][];
		for (int i = 0; i < N; i++) {
			map[i] = S[i].toCharArray();
		}
		for (MaxFlow.Edge e: mf.edges()) {
			if ( e.from == s || e.to == t || e.flow == 0 ) continue;
			int i0 = e.from / M;
			int j0 = e.from % M;
			int i1 = e.to / M;
			int j1 = e.to % M;
			if ( i0 == i1 ) {
				if ( j0 < j1 ) {
					map[i0][j0] = '>';
					map[i1][j1] = '<';
				} else {
					map[i0][j0] = '<';
					map[i1][j1] = '>';
				}
			} else {
				if ( i0 < i1 ) {
					map[i0][j0] = 'v';
					map[i1][j1] = '^';
				} else {
					map[i0][j0] = '^';
					map[i1][j1] = 'v';
				}
			}
		}
		StringBuilder out = new StringBuilder();
		out.append(ans);
		for (int i = 0; i < N; i++) {
			out.append(LF);
			for (int j = 0; j < M; j++) {
				out.append(map[i][j]);
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

class MaxFlow {
	private int _n = 0;
	private ArrayList<_Edge>[] g = null;
	private ArrayList<_Edge> edge = null;
	private static final long INF = Long.MAX_VALUE;
	
	public MaxFlow(int n) {
		this._n = n;
		this.g = new ArrayList[n];
		for (int i = 0; i < n; i++) g[i] = new ArrayList<>();
		this.edge = new ArrayList<>();
	}
	public int addEdge(int from, int to, long cap) {
		assert 0 <= from && from < _n;
		assert 0 <= to && to < _n;
		assert 0 <= cap;
		int m = edge.size();
		_Edge _e = new _Edge(to, cap);
		_Edge _re = new _Edge(from, 0);
		_e.setReverse(_re);
		g[from].add(_e);
		g[to].add(_re);
		edge.add(_e);
		return m;
	}
	public Edge getEdge(int i) {
		assert 0 <= i && i < edge.size();
		_Edge _e = edge.get(i);
		_Edge _re = _e.rev;
		return new Edge(_re.to, _e.to, _e.cap + _re.cap, _re.cap);
	}
	public ArrayList<Edge> edges() {
		ArrayList<Edge> result = new ArrayList<>(edge.size());
		for (int i = 0; i < edge.size(); i++) result.add(getEdge(i));
		return result;
	}
	public void changeEdge(int i, long newCap, long newFlow) {
		assert 0 <= i && i < edge.size();
		assert 0 <= newFlow && newFlow <= newCap;
		_Edge _e = edge.get(i);
		_Edge _re = _e.rev;
		_e.cap = newCap - newFlow;
		_re.cap = newFlow;
	}
	public long flow(int s, int t) {
		return flow(s, t, INF);
	}
	public long flow(int s, int t, long flowLimit) {
		assert 0 <= s && s < _n;
		assert 0 <= t && t < _n;
		assert s != t;

		int[] level = new int[_n];
		int[] iter = new int[_n];
		int[] que = new int[_n];

		long flow = 0;
		while (flow < flowLimit) {
			bfs(s, t, que, level);
			if (level[t] == -1) break;
			Arrays.fill(iter, 0);
			long f = dfs(s, level, iter, t, flowLimit - flow);
			if (f == 0) break;
			flow += f;
		}
		return flow;
	}
	private void bfs(int s, int t, int[] que, int[] level) {
		Arrays.fill(level, -1);
		level[s] = 0;
		int qr = 0;
		int qw = 0;
		que[qw++] = s;
		while ( qw > qr ) {
			int v = que[qr++];
			for (_Edge e : g[v]) {
				if (e.cap == 0 || level[e.to] >= 0) continue;
				level[e.to] = level[v] + 1;
				if (e.to == t) return;
				que[qw++] = e.to;
			}
		}
	}
	private long dfs(int s, int[] level, int[] iter, int v, long up) {
		if (v == s) return up;
		long res = 0;
		int level_v = level[v];
		for (; iter[v] < g[v].size(); iter[v]++) {
			_Edge e = g[v].get(iter[v]);
			_Edge re = e.rev;
			if (level_v <= level[e.to] || re.cap == 0) continue;
			long d = dfs(s, level, iter, e.to, Math.min(up - res, re.cap));
			if (d <= 0) continue;
			e.cap += d;
			re.cap -= d;
			res += d;
			if (res == up) return res;
		}
		level[v] = _n;
		return res;
	}
	public BitSet minCut(int s) {
		BitSet visited = new BitSet(_n);
		int[] que = new int[_n];
		int qr = 0;
		int qw = 0;
		que[qw++] = s;
		while (qw > qr) {
			int p = que[qr++];
			visited.set(p);
			for (_Edge e: g[p]) {
				if (e.cap != 0 && !visited.get(e.to)) {
					visited.set(e.to);
					que[qw++] = e.to;
				}
			}
		}
		return visited;
	}
	public class Edge {
		int from = 0;
		int to = 0;
		long cap = 0;
		long flow = 0;
		Edge(int from, int to, long cap, long flow) {
			this.from = from;
			this.to = to;
			this.cap = cap;
			this.flow = flow;
		}
	}
	private class _Edge {
		int to = 0;
		long cap = 0;
		_Edge rev = null;
		private _Edge(int to, long cap) {
			this.to = to;
			this.cap = cap;
		}
		private void setReverse(_Edge rev) {
			this.rev = rev;
			rev.rev = this;
		}
	}
}
