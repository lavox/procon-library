import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
		int M = sc.nextInt();
		SccGraph g = new SccGraph(N);
		for (int i = 0; i < M; i++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
			g.addEdge(a, b);
		}
		g.decompose();
		int[][] group = g.groups();
		StringBuilder out = new StringBuilder();
		out.append(group.length);
		for (int[] scc: group) {
			out.append(LF);
			out.append(scc.length);
			for (int i: scc) {
				out.append(SPACE);
				out.append(i);
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

class SccGraph {
	private int _n = 0;
	private ArrayList<Integer>[] edges = null;

	private int[] ids = null;
	private int group_num = 0;
	private int[][] groups = null;

	public SccGraph(int n) {
		this._n = n;
		this.edges = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			this.edges[i] = new ArrayList<>();
		}
	}
	public void addEdge(int from, int to) {
		edges[from].add(to);
	}

	public void decompose() {
		int now_ord = 0;
		int[] low = new int[_n];
		int[] ord = new int[_n];
		Arrays.fill(ord, -1);
		int[] sv = new int[_n];
		int[] si = new int[_n];
		int[] visited = new int[_n];
		ids = new int[_n];
		group_num = 0;
		int vi = 0;
		int s = -1;
		for (int i = 0; i < _n; i++) {
			if (ord[i] != -1) continue;

			sv[++s] = i;
			si[s] = 0;
			while ( s >= 0 ) {
				int v = sv[s];
				if (si[s] == 0) {
					low[v] = ord[v] = now_ord++;
					visited[vi++] = v;
				}
				if (si[s] < edges[v].size()) {
					int to = edges[v].get(si[s]++);
					if (ord[to] == -1) {
						sv[++s] = to;
						si[s] = 0;
						continue;
					} else {
						low[v] = Math.min(low[v], ord[to]);
					}
					continue;
				}
				if (low[v] == ord[v]) {
					while (true) {
						int u = visited[--vi];
						ord[u] = _n;
						ids[u] = group_num;
						if (u == v) break;
					}
					group_num++;
				}
				if (s > 0) {
					low[sv[s - 1]] = Math.min(low[sv[s - 1]], low[v]);
				}
				s--;
			}
		}

		int[] count = new int[group_num];
		for (int i = 0; i < _n; i++) {
			ids[i] = group_num - 1 - ids[i];
			count[ids[i]]++;
		}
		groups = new int[group_num][];
		for (int i = 0; i < group_num; i++) {
			groups[i] = new int[count[i]];
		}
		int[] ci = new int[group_num];
		for (int i = 0; i < _n; i++) {
			groups[ids[i]][ci[ids[i]]++] = i;
		}
	}

	public int[] ids() {
		return ids;
	}
	public int id(int i) {
		return ids[i];
	}
	public int[][] groups() {
		return groups;
	}
}
