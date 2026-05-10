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

import java.util.Arrays;
import java.util.PrimitiveIterator;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;

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
	private GenericGraph<Edge> g = null;
	private Class<?> eclass = null;

	@SuppressWarnings("unchecked")
	public Rerooting(int n) {
		g = new GenericGraph<>(n);
		edges = new Edge[2 * (n - 1)];
		eclass = e().getClass();
		values = (E[]) Array.newInstance(eclass, n);
		edgeValues = (E[]) Array.newInstance(eclass, 2 * (n - 1));
	}
	public void addEdge(int u, int v) {
		int eid = (edgeCnt++) * 2;
		Edge e = new Edge(u, v, eid);
		Edge re = new Edge(v, u, eid + 1);
		g.addEdge(e);
		g.addEdge(re);
		edges[e.id()] = e;
		edges[re.id()] = re;
	}
	public void build() {
		g.build();
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
		Dfs dfs = new Dfs(g.size());
		for (Dfs.DfsStep s: dfs.dfsPostOrder(g, v0)) {
			if (s.parent != -1) {
				E val = e();
				Edge pe = edges[s.edgeIndex];
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

// === begin: graph/Dfs.java ===
class Dfs {
	private int[] visitedGen = null;
	int gen = 0;

	public Dfs(int size) {
		this.visitedGen = new int[size];
	}
	public Iterable<DfsStep> dfsBothOrder(Graph g, int v0) {
		gen++;
		return () -> new DfsIterator(g, v0, true, true);
	}
	public Iterable<DfsStep> dfsPreOrder(Graph g, int v0) {
		gen++;
		return () -> new DfsIterator(g, v0, true, false);
	}
	public Iterable<DfsStep> dfsPostOrder(Graph g, int v0) {
		gen++;
		return () -> new DfsIterator(g, v0, false, true);
	}
	private void setVisited(int nodeId) {
		visitedGen[nodeId] = gen;
	}
	private boolean isVisited(int nodeId) {
		return visitedGen[nodeId] >= gen;
	}

	public class DfsIterator implements Iterator<DfsStep> {
		private final Graph g;
		private final ArrayDeque<DfsStep> stack;
		private final boolean requirePreOrder;
		private final boolean requirePostOrder;
		private DfsStep nextStep = null;
		protected DfsIterator(Graph g, int v0, boolean requirePreOrder, boolean requirePostOrder) {
			this.g = g;
			this.requirePreOrder = requirePreOrder;
			this.requirePostOrder = requirePostOrder;
			this.stack = new ArrayDeque<>();
			this.stack.addLast(new DfsStep(v0, -1, 0, null, true, 0));
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
					stack.addLast(new DfsStep(s.cur, s.parent, s.edgeIndex, s.eit, false, s.depth));
					addNextEdge(s.cur, g.edges(s.cur).iterator(), s.depth + 1);
				} else if (s.parent != -1) {
					addNextEdge(s.parent, s.eit, s.depth);
				}
				if ((s.isPre && requirePreOrder) || (!s.isPre && requirePostOrder)) {
					nextStep = s;
				}
			}
		}
		private void addNextEdge(int v, Iterator<? extends Edge> eit, int depth) {
			if (eit == null) return;
			while (eit.hasNext()) {
				Edge e = eit.next();
				if (isVisited(e.to())) continue;
				stack.addLast(new DfsStep(e.to(), v, e.id(), eit, true, depth));
				setVisited(e.to());
				return;
			}
		}
	}

	public class DfsStep {
		public final int cur;
		public final int parent;
		public final int edgeIndex; // edgeのid
		public final boolean isPre;
		public final int depth;
		private final Iterator<? extends Edge> eit; // parentのedgeのiterator

		protected DfsStep(int cur, int parent, int edgeIndex, Iterator<? extends Edge> eit, boolean isPre, int depth) {
			this.cur = cur;
			this.parent = parent;
			this.edgeIndex = edgeIndex;
			this.eit = eit;
			this.isPre = isPre;
			this.depth = depth;
		}
		public boolean isVisited(int nodeId) {
			return Dfs.this.isVisited(nodeId);
		}
	}
}
// === end: graph/Dfs.java ===

// === begin: graph/GenericGraph.java ===
class GenericGraph<E extends Edge> implements Graph {
	private ArrayList<E> _edges = null;
	private int maxEdgeId = 0;
	protected int n = 0;
	protected int[] start = null;
	protected Object[] edges = null;
	public GenericGraph(int n) {
		this.n = n;
		this._edges = new ArrayList<>();
	}
	public void addEdge(E e) {
		maxEdgeId = Math.max(maxEdgeId, e.id);
		_edges.add(e);
	}
	public int maxEdgeId() {
		return maxEdgeId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void forEachEdge(int v, Graph.EdgeConsumer action) {
		for (int ei = start[v]; ei < start[v + 1]; ei++) {
			E e = (E) edges[ei];
			action.accept(e.from, e.to, e.id, e.cost);
		}
	}

	public void build() {
		start = new int[n + 1];
		for (Edge e: _edges) start[e.from + 1]++;
		for (int i = 0; i < n; i++) start[i + 1] += start[i];
		int[] cnt = start.clone();
		edges = new Object[_edges.size()];
		for (Edge e: _edges) edges[cnt[e.from]++] = e;
	}

	@Override
	public int size() {
		return n;
	}
	public int edgeSize(int v) {
		return start[v + 1] - start[v];
	}
	@SuppressWarnings("unchecked")
	public E edge(int v, int i) {
		return (E)edges[start[v] + i];
	}
	@Override
	public Iterable<E> edges(int v) {
		return new Iterable<E>() {
			@Override
			public Iterator<E> iterator() {
				return new Iterator<E>() {
					int ei = 0;
					int ecnt = edgeSize(v);
					@Override
					public boolean hasNext() {
						return ei < ecnt;
					}
					@Override
					public E next() {
						return edge(v, ei++);
					}
				};
			}
		};
	}
	@Override
	public PrimitiveIterator.OfInt edgesTo(int v) {
		return new PrimitiveIterator.OfInt() {
			int i = 0;
			@Override
			public boolean hasNext() { return i < edgeSize(v); }
			@Override
			public int nextInt() { return edge(v, i++).to; }
		};
	}
}
// === end: graph/GenericGraph.java ===

// === begin: graph/Edge.java ===
class Edge {
	public int from;
	public int to;
	public int id;
	public long cost;
	public Edge(int from, int to, int id, long cost) {
		this.from = from;
		this.to = to;
		this.id = id;
		this.cost = cost;
	}
	public Edge(int from, int to, int id) {
		this.from = from;
		this.to = to;
		this.id = id;
		this.cost = 1;
	}
	public Edge(int from, int to) {
		this.from = from;
		this.to = to;
		this.id = -1;
		this.cost = 1;
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
	public long cost() {
		return cost;
	}
}
// === end: graph/Edge.java ===

// === begin: graph/Graph.java ===
interface Graph {
	public int size();
	public void forEachEdge(int v, EdgeConsumer action);
	public default Iterable<? extends Edge> edges(int v) {
		return () -> {
			ArrayList<Edge> edges = new ArrayList<>();
			forEachEdge(v, (from, to, id, cost) -> edges.add(new Edge(from, to, id, cost)));
			return edges.iterator();
		};
	}
	public default PrimitiveIterator.OfInt edgesTo(int v) {
		IntArrayList toArr = new IntArrayList();
		forEachEdge(v, (from, to, id, cost) -> toArr.add(to));
		return new PrimitiveIterator.OfInt() {
			int i = 0;
			@Override
			public boolean hasNext() { return i < toArr.size(); }
			@Override
			public int nextInt() { return toArr.get(i++); }
		};
	}

	@FunctionalInterface
	public interface EdgeConsumer {
		public void accept(int from, int to, int id, long cost);
	}
}
// === end: graph/Graph.java ===

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

// === begin: primitive/IntComparator.java ===
@FunctionalInterface
interface IntComparator {
    int compare(int a, int b);
}
// === end: primitive/IntComparator.java ===

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
