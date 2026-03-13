import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

import java.util.Arrays;

import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.PrimitiveIterator;
import java.util.PriorityQueue;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

// template & library : https://github.com/lavox/procon-library
public class Main {
	public static void main(String[] args) {
		Main o = new Main();
		o.solve();
	}

	public void solve() {
		FastScanner sc = new FastScanner(System.in);
		int N = sc.nextInt();
		long[][] A = sc.nextLongMatrix(N, N);
		ShortestPath.TspRoute r = ShortestPath.tsp(A);
		System.out.println(r.dist);
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

// === begin: graph/ShortestPath.java ===
class ShortestPath {
	public static final long INF = Long.MAX_VALUE;
	public static final long NINF = Long.MIN_VALUE;

	// Dijkstra
	public static <E extends CostEdge> Dist dijkstra(GenericGraph<E> g, int s) {
		return dijkstra(g, new int[] {s}, null);
	}
	public static <E extends CostEdge> Dist dijkstra(GenericGraph<E> g, int[] ss, Predicate<E> canPass) {
		int n = g.size();
		Dist d = new Dist(n);
		final long[] dist = d.dist;
		final int[] parent = d.parent;
		PriorityQueue<Step> queue = new PriorityQueue<>();
		Step[] step = new Step[n];
		for (int s: ss) {
			step[s] = new Step(s, 0, -1);
			queue.add(step[s]);
		}
		while (queue.size() > 0) {
			Step st = queue.poll();
			if (step[st.p] != st) continue;
			dist[st.p] = st.d;
			parent[st.p] = st.parent;
			for (E e: g.edges(st.p)) {
				if (canPass != null && !canPass.test(e)) continue;
				int np = e.to();
				long nd = st.d + e.cost();
				if (step[np] == null || nd < step[np].d) {
					step[np] = new Step(np, nd, st.p);
					queue.add(step[np]);
				}
			}
		}
		return d;
	}

	// BFS
	public static <E extends Edge> Dist bfs(GenericGraph<E> g, int s) {
		return bfs(g, new int[] {s}, null);
	}
	public static <E extends Edge> Dist bfs(GenericGraph<E> g, int[] ss, Predicate<E> canPass) {
		int n = g.size();
		Dist d = new Dist(n);
		final long[] dist = d.dist;
		final int[] parent = d.parent;
		int[] queue = new int[n];
		int ri = 0;
		int wi = 0;
		for (int s: ss) {
			dist[s] = 0;
			queue[wi++] = s;
		}
		while (ri < wi) {
			int p = queue[ri++];
			for (E e: g.edges(p)) {
				if (canPass != null && !canPass.test(e)) continue;
				int np = e.to();
				if (dist[np] != INF) continue;
				dist[np] = dist[p] + 1;
				parent[np] = p;
				queue[wi++] = np;
			}
		}
		return d;
	}

	// 01-BFS
	public static <E extends Edge> Dist bfs01(GenericGraph<E> g, int s, Predicate<E> edge1) {
		return bfs01(g, new int[] {s}, edge1, null);
	}
	public static <E extends Edge> Dist bfs01(GenericGraph<E> g, int[] ss, Predicate<E> edge1, Predicate<E> canPass) {
		int n = g.size();
		Dist d = new Dist(n);
		final long[] dist = d.dist;
		final int[] parent = d.parent;
		ArrayDeque<Step> queue = new ArrayDeque<>();
		Step[] step = new Step[n];
		for (int s: ss) {
			step[s] = new Step(s, 0, -1);
			queue.addLast(step[s]);
		}
		while (queue.size() > 0) {
			Step st = queue.pollFirst();
			if (step[st.p] != st) continue;
			dist[st.p] = st.d;
			parent[st.p] = st.parent;
			for (E e: g.edges(st.p)) {
				if (canPass != null && !canPass.test(e)) continue;
				int dd = edge1.test(e) ? 1 : 0;
				int np = e.to();
				long nd = st.d + dd;
				if (step[np] == null || nd < step[np].d) {
					step[np] = new Step(np, nd, st.p);
					if (dd == 0) {
						queue.addFirst(step[np]);
					} else {
						queue.addLast(step[np]);
					}
				}
			}
		}
		return d;
	}

	// Bellman Ford
	public static <E extends CostEdge> Dist bellmanFord(GenericGraph<E> g, int s) {
		return bellmanFord(g, new int[] {s});
	}
	public static <E extends CostEdge> Dist bellmanFord(GenericGraph<E> g, int[] starts) {
		int n = g.size();
		Dist d = new Dist(n);
		final long[] dist = d.dist;
		final int[] parent = d.parent;
		for (int s: starts) {
			dist[s] = 0;
		}
		for (int i = 0; i < n - 1; i++) {
			boolean update = false;
			for (int v = 0; v < n; v++) {
				if (dist[v] == INF) continue;
				for (E e: g.edges(v)) {
					int np = e.to();
					if (dist[v] + e.cost() < dist[np]) {
						dist[np] = dist[v] + e.cost();
						parent[np] = v;
						update = true;
					}
				}
			}
			if (!update) return d;
		}
		BitSet visited = new BitSet(n);
		int[] queue = new int[n];
		int wi = 0;
		boolean update = false;
		for (int v = 0; v < n; v++) {
			if (dist[v] == INF) continue;
			for (E e: g.edges(v)) {
				int np = e.to();
				if (dist[np] == NINF) continue;
				if (dist[v] == NINF || dist[v] + e.cost() < dist[np]) {
					dist[np] = NINF;
					parent[np] = v;
					d.hasNegativeLoop = true;
					visited.set(np);
					queue[wi++] = np;
					update = true;
				}
			}
		}
		if (!update) return d;
		int ri = 0;
		while (ri < wi) {
			int v = queue[ri++];
			for (E e: g.edges(v)) {
				int np = e.to();
				if (visited.get(np)) continue;
				visited.set(np);
				dist[np] = NINF;
				parent[np] = v;
				queue[ri++] = np;
			}
		}
		return d;
	}

	// Warshall Floyd
	public static DistMap wfCreateMap(int n) {
		DistMap d = new DistMap(n);
		d.init();
		return d;
	}
	public static DistMap warshallFloyd(DistMap d) {
		int n = d.n;
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (d.dist[i][k] == INF || d.dist[k][j] == INF) continue;
					d.dist[i][j] = Math.min(d.dist[i][j], d.dist[i][k] + d.dist[k][j]);
				}
			}
		}
		return d;
	}
	public static DistMap wfUpdateEdge(DistMap d, int from, int to, long dist) {
		if (d.dist[from][to] <= dist) return d;
		d.dist[from][to] = dist;
		int n = d.n;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (d.dist[i][from] == INF || d.dist[to][j] == INF) continue;
				d.dist[i][j] = Math.min(d.dist[i][j], d.dist[i][from] + dist + d.dist[to][j]);
			}
		}
		return d;
	}

	// TSP
	public static TspRoute tsp(long[][] distMap) {
		return tsp(distMap, 0, false);
	}
	public static TspRoute tsp(long[][] distMap, int s, boolean oneway) {
		int n = distMap.length;
		long[][] dp = new long[n][1 << n];
		for (int i = 0; i < n; i++) Arrays.fill(dp[i], INF);
		dp[s][1 << s] = 0;
		int full = (1 << n) - 1;
		for (int bit = 0; bit <= full; bit++) {
			for (int _b = bit, i = 0; _b != 0; _b &= ~(1 << i)) {
				i = Integer.numberOfTrailingZeros(_b);
				if (dp[i][bit] == INF) continue;
				for (int rb = full & ~bit, j = 0; rb != 0; rb &= ~(1 << j)) {
					j = Integer.numberOfTrailingZeros(rb);
					if (distMap[i][j] == INF) continue;
					dp[j][bit | (1 << j)] = Math.min(dp[j][bit | (1 << j)], dp[i][bit] + distMap[i][j]);
				}
			}
		}
		TspRoute ret = new TspRoute(n, distMap);
		for (int g = 0; g < n; g++) {
			long d = dp[g][full];
			if (d == INF) continue;
			if (!oneway) {
				if (distMap[g][s] == INF) continue;
				d += distMap[g][s];
			}
			if (d < ret.dist) {
				ret.dist = d;
				ret.g = g;
			}
		}
		ret.dp = dp;
		return ret;
	}	

	public static class DistMap {
		public final int n;
		public final long[][] dist;
		private DistMap(int n) {
			this.n = n;
			this.dist = new long[n][n];
		}
		private void init() {
			for (int i = 0; i < n; i++) {
				Arrays.fill(dist[i], INF);
				dist[i][i] = 0;
			}
		}
		public boolean hasNegativeLoop() {
			for (int i = 0; i < n; i++) {
				if (dist[i][i] < 0) return true;
			}
			return false;
		}
	}
	public static class Dist {
		public int n = 0;
		public long[] dist = null;
		public int[] parent = null;
		private boolean hasNegativeLoop = false;
		private Dist(int n) {
			this.n = n;
			this.dist = new long[n];
			this.parent = new int[n];
			Arrays.fill(dist, INF);
			Arrays.fill(parent, -1);
		}
		public int[] path(int t) {
			IntArrayList path = new IntArrayList();
			int cur = t;
			while (cur != -1) {
				path.add(cur);
				cur = parent[cur];
			}
			int ps = path.size();
			int[] ret = new int[ps];
			for (int i = 0; i < ps; i++) ret[i] = path.get(ps - i- 1);
			return ret;
		}
		public boolean hasNegativeLoop() {
			return hasNegativeLoop;
		}
	}
	public static class TspRoute {
		public int n;
		public int g = -1;
		public long dist = INF;
		public long[][] dp = null;
		public long[][] distMap = null;
		private TspRoute(int n, long[][] distMap) {
			this.n = n;
			this.distMap = distMap;
		}

		int[] path() {
			if (dist == INF) return null;
			int[] path = new int[n];
			for (int i = n - 1, cur = g, bit = (1 << n) - 1; i >= 0; i--) {
				path[i] = cur;
				if (i == 0) break;
				long curDist = dp[cur][bit];
				bit &= ~(1 << cur);
				for (int _b = bit, prev = 0; _b != 0 ; _b &= ~(1 << prev)) {
					prev = Integer.numberOfTrailingZeros(_b);
					if (dp[prev][bit] != INF && distMap[prev][cur] != INF && dp[prev][bit] + distMap[prev][cur] == curDist) {
						cur = prev;
						break;
					}
				}
			}
			return path;
		}
	}

	private static class Step implements Comparable<Step> {
		private final int p;
		private final long d;
		private final int parent;
		private Step(int p, long d, int parent) {
			this.p = p;
			this.d = d;
			this.parent = parent;
		}
		@Override
		public int compareTo(Step o) {
			if (d != o.d) {
				return Long.compare(d, o.d);
			} else {
				return Integer.compare(p, o.p);
			}
		}
	}
}
// === end: graph/ShortestPath.java ===

// === begin: graph/GenericGraph.java ===
class GenericGraph<E extends Edge> {
	protected int n;
	protected ArrayList<E>[] edges;
	protected int maxEdgeId = 0;
	protected int edgeCnt = 0;
	
	@SuppressWarnings("unchecked")
	public GenericGraph(int n) {
		this.n = n;
		edges = new ArrayList[n];
		for (int i = 0; i < n; i++) edges[i] = new ArrayList<>();
	}
	public void addDirEdge(E e) {
		edges[e.from()].add(e);
		maxEdgeId = Math.max(maxEdgeId, e.id());
		edgeCnt++;
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
	public ArrayList<E> edges(int v) {
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
// === end: graph/GenericGraph.java ===

// === begin: primitive/IntArrayList.java ===
class IntArrayList implements Iterable<Integer> {
	private int[] data = null;
	private int size = 0;
	private static final int DEFAULT_CAPACITY = 10;

	public IntArrayList() {}
	public IntArrayList(int initialCapacity) {
		data = new int[initialCapacity];
	}
	public IntArrayList(int[] data) {
		this.data = Arrays.copyOf(data, data.length);
		this.size = data.length;
	}
	public IntArrayList(IntArrayList array) {
		this.data = Arrays.copyOf(array.data, array.size);
		this.size = array.size;
	}
	public boolean add(int e) {
		ensureCapacity(size + 1);
		data[size++] = e;
		return true;
	}
	public void add(int index, int element) {
		if (index < 0 || index > size) throw new IndexOutOfBoundsException();
		ensureCapacity(size + 1);
		System.arraycopy(data, index, data, index + 1, size - index);
		data[index] = element;
		size++;
	}
	public void addAll(IntArrayList c) {
		addAll(size, c);
	}
	public void addAll(int index, IntArrayList c) {
		if (index < 0 || index > size) throw new IndexOutOfBoundsException();
		if (c.size() == 0) return;
		ensureCapacity(size + c.size);
		System.arraycopy(data, index, data, index + c.size(), size - index);
		System.arraycopy(c.data, 0, data, index, c.size());
		size += c.size();
	}
	public void clear() {
		size = 0;
	}
	public IntArrayList clone() {
		IntArrayList copy = new IntArrayList();
		if (data != null) {
			copy.data = new int[data.length];
			System.arraycopy(data, 0, copy.data, 0, data.length);
		}
		copy.size = size;
		return copy;
	}
	public boolean contains(int e) {
		for (int value : data) {
			if (value == e) return true;
		}
		return false;
	}
	public void ensureCapacity(int minCapacity) {
		if (data == null) {
			data = new int[Math.max(DEFAULT_CAPACITY, minCapacity)];
		} else if (data.length < minCapacity) {
			data = Arrays.copyOf(data, Math.max(data.length * 2, minCapacity));
		}
	}
	public void forEach(IntConsumer action) {
		if (data != null) {
			for (int i = 0; i < size; i++) {
				action.accept(data[i]);
			}
		}
	}
	public int get(int index) {
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
		return data[index];
	}
	public int last() {
		if (size == 0) throw new IndexOutOfBoundsException();
		return data[size - 1];
	}
	public int indexOf(int e) {
		for (int i = 0; i < size; i++) {
			if (data[i] == e) return i;
		}
		return -1;
	}
	public boolean isEmpty() {
		return size == 0;
	}
	public PrimitiveIterator.OfInt iterator() {
		return new IntArrayIterator(data, size);
	}
	private final class IntArrayIterator implements PrimitiveIterator.OfInt {
		private int index = 0;
		private int size;
		private int[] data;
		IntArrayIterator(int[] data, int size) {
			this.data = data;
			this.size = size;
		}
		@Override
		public boolean hasNext() {
			return index != size;
		}
		@Override
		public int nextInt() {
			int i = index;
			if (i == size) throw new IndexOutOfBoundsException();
			index = i + 1;
			return data[i];
		}
	}
	public int lastIndexOf(int e) {
		for (int i = size - 1; i >= 0; i--) {
			if (data[i] == e) return i;
		}
		return -1;
	}
	public int removeByIndex(int index) {
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
		int oldValue = data[index];
		System.arraycopy(data, index + 1, data, index, size - index - 1);
		data[--size] = 0;
		return oldValue;
	}
	public int removeLast() {
		if (size == 0) throw new IndexOutOfBoundsException();
		return data[--size];
	}
	public boolean removeByVal(int e) {
		int index = indexOf(e);
		if (index >= 0) {
			removeByIndex(index);
			return true;
		}
		return false;
	}
	public boolean removeAll(IntArrayList c) {
		if (size == 0 || c.size() == 0) return false;
		int w = 0;
		boolean removed = false;
		for (int r = 0; r < size; r++) {
			if (c.indexOf(data[r]) < 0) {
				data[w++] = data[r];
			} else {
				removed = true;
			}
		}
		size = w;
		return removed;
	}
	public boolean removeIf(IntPredicate filter) {
		if (data == null || size == 0) return false;
		int w = 0;
		boolean modified = false;
		for (int r = 0; r < size; r++) {
			if (!filter.test(data[r])) {
				data[w++] = data[r];
			} else {
				modified = true;
			}
		}
		size = w;
		return modified;
	}
	public void removeRange(int fromIndex, int toIndex) {
		if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
			throw new IndexOutOfBoundsException();
		}
		System.arraycopy(data, toIndex, data, fromIndex, size - toIndex);
		size -= (toIndex - fromIndex);
	}
	public void replaceAll(IntUnaryOperator operator) {
		if (data == null) return;
		for (int i = 0; i < size; i++) {
			data[i] = operator.applyAsInt(data[i]);
		}
	}
	public boolean retainAll(IntArrayList c) {
		if (size == 0 || c.size() == 0) return false;
		int w = 0;
		boolean removed = false;
		for (int r = 0; r < size; r++) {
			if (c.indexOf(data[r]) >= 0) {
				data[w++] = data[r];
			} else {
				removed = true;
			}
		}
		size = w;
		return removed;
	}
	public int set(int index, int element) {
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
		int oldValue = data[index];
		data[index] = element;
		return oldValue;
	}
	public int size() {
		return size;
	}
	public void sort() {
		Arrays.sort(data, 0, size);
	}
	public void sort(IntComparator c) {
		IntArrays.sort(data, 0, size, c);
	}
	public void sort(int fromIndex, int toIndex, IntComparator c) {
		if (toIndex < 0 || toIndex > size) throw new IndexOutOfBoundsException();
		IntArrays.sort(data, fromIndex, toIndex, c);
	}
	public int[] toArray() {
		if (data == null) return new int[0];
		return Arrays.copyOf(data, size);
	}
	public int[] toArray(int[] a) {
		if (a.length < size) {
			return Arrays.copyOf(data, size);
		}
		System.arraycopy(data, 0, a, 0, size);
		if (a.length > size) a[size] = 0;
		return a;
	}
	public void trimToSize() {
		if (data == null || data.length == size) return;
		data = Arrays.copyOf(data, size);
	}
	@Override
	public boolean equals(Object o) {
		if (o instanceof IntArrayList) {
			IntArrayList ol = (IntArrayList)o;
			if (size != ol.size) return false;
			for (int i = 0; i < size; i++) {
				if (data[i] != ol.data[i]) return false;
			}
			return true;
		}
		return false;
	}
	@Override
	public int hashCode() {
		int hashCode = 1;
		for (int i = 0; i < size; i++) {
			hashCode = 31 * hashCode + data[i];
		}
		return hashCode;
	}
}
// === end: primitive/IntArrayList.java ===

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

// === begin: graph/CostEdge.java ===
class CostEdge extends Edge {
	private final long cost;
	public CostEdge(int from, int to, long cost, int id) {
		super(from, to, id);
		this.cost = cost;
	}
	public long cost() {
		return cost;
	}
}
// === end: graph/CostEdge.java ===

// === begin: primitive/IntArrays.java ===
class IntArrays {
	public static void sort(int[] a, IntComparator comp) {
		sort(a, 0, a.length, comp);
	}
	public static void sort(int[] a, int fromIndex, int toIndex, IntComparator comp) {
		if (toIndex - fromIndex <= 1) return;
		int maxDepth = 2 * (31 - Integer.numberOfLeadingZeros(toIndex - fromIndex));
		introSort(a, fromIndex, toIndex, maxDepth, comp);
	}

	private static void introSort(int[] a, int left, int right, int maxDepth, IntComparator comp) {
		while (right - left > 32) {
			if (maxDepth == 0) {
				heapSort(a, left, right, comp);
				return;
			}
			maxDepth--;
			
			int mid = (left + right) >>> 1;
			if (comp.compare(a[left], a[mid]) > 0) swap(a, left, mid);
			if (comp.compare(a[mid], a[right - 1]) > 0) {
				swap(a, mid, right - 1);
				if (comp.compare(a[left], a[mid]) > 0) swap(a, left, mid);
			}
			int pivot = a[mid];
			int i = left + 1, j = right - 2;
			while (i <= j) {
				while (comp.compare(a[i], pivot) < 0) i++;
				while (comp.compare(a[j], pivot) > 0) j--;
				if (i <= j) {
						swap(a, i, j);
						i++; j--;
				}
			}

			if (j - left < right - i) {
				introSort(a, left, j + 1, maxDepth, comp);
				left = i;
			} else {
				introSort(a, i, right, maxDepth, comp);
				right = j + 1;
			}
		}
		insertionSort(a, left, right, comp);
	}
	private static void insertionSort(int[] a, int left, int right, IntComparator comp) {
		for (int i = left + 1; i < right; i++) {
			int v = a[i];
			int j = i - 1;
			while (j >= left && comp.compare(a[j], v) > 0) {
				a[j + 1] = a[j];
				j--;
			}
			a[j + 1] = v;
		}
	}
	private static void heapSort(int[] a, int left, int right, IntComparator comp) {
		int n = right - left;
		for (int i = (n >>> 1) - 1; i >= 0; i--) downHeap(a, i, n, left, comp);
		for (int i = n - 1; i > 0; i--) {
			swap(a, left, left + i);
			downHeap(a, 0, i, left, comp);
		}
	}
	private static void downHeap(int[] a, int i, int n, int base, IntComparator comp) {
		while (true) {
			int l = (i << 1) + 1;
			if (l >= n) break;
			int r = l + 1;
			int largest = l;
			if (r < n && comp.compare(a[base + l], a[base + r]) < 0) largest = r;
			if (comp.compare(a[base + i], a[base + largest]) >= 0) break;
			swap(a, base + i, base + largest);
			i = largest;
		}
	}
	private static void swap(int[] a, int i, int j) {
			int tmp = a[i];
			a[i] = a[j];
			a[j] = tmp;
	}
}
// === end: primitive/IntArrays.java ===

// === begin: primitive/IntComparator.java ===
@FunctionalInterface
interface IntComparator {
    int compare(int a, int b);
}
// === end: primitive/IntComparator.java ===
