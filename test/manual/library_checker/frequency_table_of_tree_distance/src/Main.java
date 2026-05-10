import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;

// template & library : https://github.com/lavox/procon-library
public class Main {
	public static void main(String[] args) {
		Main o = new Main();
		o.solve();
	}

	public void solve() {
		FastScanner sc = new FastScanner(System.in);
		int N = sc.nextInt();
		SimpleGraph g = new SimpleGraph(N);
		for (int i = 0; i < N - 1; i++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
			g.addUndirEdge(a, b);
		}
		g.build();

		ArrayDeque<CentroidDecomposer.SubTree> queue = new ArrayDeque<>();
		queue.add(CentroidDecomposer.createInitTree(g));
		Dfs dfs = new Dfs(g.size());
		long[] ans = new long[N + 1];
		while (queue.size() > 0) {
			CentroidDecomposer.SubTree sg = queue.pollFirst();
			if (sg.size() == 1) continue;
			sg.decompose();
			int md = 0;
			for (CentroidDecomposer.SubTree t: sg.decomposedTree()) {
				md = Math.max(md, t.maxDepth());
			}
			long[] tot = new long[md + 2];
			tot[0] = 1;
			for (CentroidDecomposer.SubTree t: sg.decomposedTree()) {
				long[] cnt = new long[t.maxDepth() + 2];
				for (Dfs.DfsStep s: dfs.dfsPreOrder(t, t.root())) {
					tot[s.depth + 1] += 1;
					cnt[s.depth + 1] += 1;
				}
				long[] marr = Convolution.convolution_ll(cnt, cnt);
				for (int i = 0; i < marr.length; i++) ans[i] -= marr[i];
			}
			long[] marr2 = Convolution.convolution_ll(tot, tot);
			for (int i = 0; i < marr2.length; i++) ans[i] += marr2[i];
			queue.addAll(sg.decomposedTree());
		}
		print(ans, SPACE, l -> l / 2, 1, N);
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

// === begin: graph/CentroidDecomposer.java ===
class CentroidDecomposer {
	public static SubTree createInitTree(Graph g) {
		Context ctx = new Context(g);
		int sid = ctx.nextSid();
		Arrays.fill(ctx.sidMap[0], sid);
		return new SubTree(ctx, 0, sid, 0, g.size());
	}

	public static class SubTree implements Graph {
		private final Context ctx;
		private final int gen;
		private final int sid;
		private final int sz;
		private final int root;
		private final int[] sidMap;
		private int centroid = -1;
		private ArrayList<SubTree> ctree = null;
		private int maxDepth = -1;
		protected SubTree(Context ctx, int gen, int sid, int root, int sz) {
			this.ctx = ctx;
			this.gen = gen;
			this.sid = sid;
			this.root = root;
			this.sz = sz;
			this.sidMap = ctx.sidMap[gen];
		}
		public void decompose() {
			if (centroid != -1) return;
			ctree = new ArrayList<>();
			if (sz == 1) {
				centroid = root;
				return;
			}
			ctx.prepareGen(gen + 1);
			centroid = searchCentroid();
			SubTree curTree = null;
			final int[] child = ctx.child[gen];
			for (Dfs.DfsStep s: ctx.dfs.dfsBothOrder(this, centroid)) {
				if (s.isPre) {
					if (s.parent == centroid) {
						int csz = child[s.cur] < child[s.parent] ? child[s.cur] : this.sz - child[s.parent];
						curTree = new SubTree(ctx, gen + 1, ctx.nextSid(), s.cur, csz);
						ctree.add(curTree);
					}
				} else {
					if (s.cur != centroid) {
						ctx.sidMap[gen + 1][s.cur] = curTree.sid;
						curTree.maxDepth = Math.max(curTree.maxDepth, s.depth - 1);
					}
				}
			}
		}
		private int searchCentroid() {
			int mid = (sz + 1) / 2;
			final int[] child = ctx.child[gen];
			for (Dfs.DfsStep s: ctx.dfs.dfsBothOrder(this, root)) {
				if (s.isPre) {
					child[s.cur] = 1;
				} else {
					if (s.parent != -1) {
						child[s.parent] += child[s.cur];
					}
					if (child[s.cur] >= mid) return s.cur;
				}
			}
			return -1;
		}
		public int centroid() {
			return centroid;
		}
		public ArrayList<SubTree> decomposedTree() {
			return ctree;
		}
		public boolean isMember(int v) {
			return sidMap[v] == this.sid;
		}
		public int root() {
			return root;
		}
		public int maxDepth() {
			return maxDepth;
		}
		@Override
		public int size() {
			return sz;
		}
		@Override
		public void forEachEdge(int v, EdgeConsumer action) {
			ctx.g.forEachEdge(v, (from, to, id, cost) -> {
				if (isMember(to)) action.accept(from, to, id, cost);
			});
		}
		@Override
		public Iterable<? extends Edge> edges(int v) {
			ArrayList<Edge> ret = new ArrayList<>();
			for (Edge e: ctx.g.edges(v)) {
				if (isMember(e.to)) ret.add(e);
			}
			return ret;
		}
		@Override
		public PrimitiveIterator.OfInt edgesTo(int v) {
			IntArrayList ret = new IntArrayList();
			PrimitiveIterator.OfInt it = ctx.g.edgesTo(v);
			while (it.hasNext()) {
				int to = it.nextInt();
				if (isMember(to)) ret.add(to);
			}
			return ret.iterator();
		}
	}

	private static class Context {
		protected int sid = 0;
		protected final Graph g;
		protected int[][] child;
		protected int[][] sidMap;
		protected Dfs dfs;
		private int genMax;
		private int sz;
		protected Context(Graph g) {
			this.g = g;
			this.sz = g.size();
			genMax = Math.max(31 - Integer.numberOfLeadingZeros(this.sz), 0);
			while (this.sz > (1 << genMax)) genMax++;
			genMax++;
			this.child = new int[genMax][];
			this.sidMap = new int[genMax][];
			this.dfs = new Dfs(g.size());
			prepareGen(0);
		}
		protected int nextSid() {
			return ++sid;
		}
		protected int sid(int gen, int v) {
			return gen < genMax && sidMap[gen] != null ? sidMap[gen][v] : 0;
		}
		protected void prepareGen(int gen) {
			if (gen >= genMax) throw new IllegalArgumentException();
			if (child[gen] == null) {
				child[gen] = new int[sz];
				sidMap[gen] = new int[sz];
			}
		}
	}
}
// === end: graph/CentroidDecomposer.java ===

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

// === begin: graph/SimpleGraph.java ===
class SimpleGraph extends GenericGraph<Edge> {
	public SimpleGraph(int n) {
		super(n);
	}
	public void addDirEdge(int from, int to) {
		addEdge(new Edge(from, to));
	}
	public void addDirEdge(int from, int to, int id) {
		addEdge(new Edge(from, to, id));
	}
	public void addDirEdge(int from, int to, int id, long cost) {
		addEdge(new Edge(from, to, id, cost));
	}
	public void addUndirEdge(int u, int v) {
		addEdge(new Edge(u, v));
		addEdge(new Edge(v, u));
	}
	public void addUndirEdge(int u, int v, int id) {
		addEdge(new Edge(u, v, id));
		addEdge(new Edge(v, u, id));
	}
	public void addUndirEdge(int u, int v, int id, long cost) {
		addEdge(new Edge(u, v, id, cost));
		addEdge(new Edge(v, u, id, cost));
	}
	@Override
	public void forEachEdge(int v, Graph.EdgeConsumer action) {
		for (int ei = start[v]; ei < start[v + 1]; ei++) {
			Edge e = (Edge)edges[ei];
			action.accept(v, e.to, e.id, e.cost);
		}
	}
}
// === end: graph/SimpleGraph.java ===

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

// === begin: math/Convolution.java ===
// Ported to Java from the original C++ implementation by Atcoder.
// Original Source: https://github.com/atcoder/ac-library/blob/master/atcoder/convolution.hpp
class Convolution {
	private static class FftInfo {
		int g;
		int rank2;
		long[] root;
		long[] iroot;
		long[] rate2;
		long[] irate2;
		long[] rate3;
		long[] irate3;

		private FftInfo(long mod) {
			this.g = primitive_root((int)mod);
			this.rank2 = Long.numberOfTrailingZeros(mod - 1);

			this.root = new long[rank2 + 1];
			this.iroot = new long[rank2 + 1];
			this.rate2 = new long[Math.max(0, rank2 - 2 + 1)];
			this.irate2 = new long[Math.max(0, rank2 - 2 + 1)];
			this.rate3 = new long[Math.max(0, rank2 - 3 + 1)];
			this.irate3 = new long[Math.max(0, rank2 - 3 + 1)];

			root[rank2] = pow(g % mod, (mod - 1) >> rank2, mod);
			iroot[rank2] = inv(root[rank2], mod);
			for (int i = rank2 - 1; i >= 0; i--) {
				root[i] = (root[i + 1] * root[i + 1]) % mod;
				iroot[i] = (iroot[i + 1] * iroot[i + 1]) % mod;
			}

			{
				long prod = 1;
				long iprod = 1;
				for (int i = 0; i <= rank2 - 2; i++) {
					rate2[i] = (root[i + 2] * prod) % mod;
					irate2[i] = (iroot[i + 2] * iprod) % mod;
					prod = (prod * iroot[i + 2]) % mod;
					iprod = (iprod * root[i + 2]) % mod;
				}
			}
			{
				long prod = 1;
				long iprod = 1;
				for (int i = 0; i <= rank2 - 3; i++) {
					rate3[i] = (root[i + 3] * prod) % mod;
					irate3[i] = (iroot[i + 3] * iprod) % mod;
					prod = (prod * iroot[i + 3]) % mod;
					iprod = (iprod * root[i + 3]) % mod;
				}
			}
		}
	};

	private static int bit_ceil(int n) {
		int x = 1;
		while (x < n) x *= 2;
		return x;
	}
	private static long add(long a, long b, long m) {
		long ret = a + b;
		return ret >= m ? ret - m : ret;
	}
	private static long sub(long a, long b, long m) {
		long ret = a - b;
		return ret < 0 ? ret + m : ret;
	}
	private static long mul(long a, long b, long m) {
		return (a * b) % m;
	}
	private static long pow(long a, long n, long m) {
		assert n >= 0;
		long c = a, r = 1;
		while ( n > 0 ) {
			if ( (n & 1L) != 0 ) r = mul(r, c, m);
			c = mul(c, c, m);
			n >>>= 1;
		}
		return r;
	}
	private static long inv(long a, long m) {
		return pow(a, m - 2, m);
	}
	private static long mod(long x, long m) {
		x %= m;
		return x < 0 ? x + m : x;
	}

	private static int primitive_root(int m) {
		if (m == 2) return 1;
		if (m == 167772161) return 3;
		if (m == 469762049) return 3;
		if (m == 754974721) return 11;
		if (m == 998244353) return 3;
		int[] divs = new int[20];
		divs[0] = 2;
		int cnt = 1;
		int x = (m - 1) / 2;
		while (x % 2 == 0) x /= 2;
		for (int i = 3; ((long)(i))*i <= x; i += 2) {
			if (x % i == 0) {
				divs[cnt++] = i;
				while (x % i == 0) {
					x /= i;
				}
			}
		}
		if (x > 1) {
			divs[cnt++] = x;
		}
		for (int g = 2;; g++) {
			boolean ok = true;
			for (int i = 0; i < cnt; i++) {
				if (pow(g, (m - 1) / divs[i], m) == 1) {
					ok = false;
					break;
				}
			}
			if (ok) return g;
		}
	}

	private static void butterfly(long[] a, long mod) {
		int n = a.length;
		int h = Integer.numberOfTrailingZeros(n);
		FftInfo info = new FftInfo(mod);

		int len = 0;
		while (len < h) {
			if (h - len == 1) {
				int p = 1 << (h - len - 1);
				long rot = 1;
				for (int s = 0; s < (1 << len); s++) {
					int offset = s << (h - len);
					for (int i = 0; i < p; i++) {
						long l = a[i + offset];
						long r = mul(a[i + offset + p], rot, mod);
						a[i + offset] = add(l, r, mod);
						a[i + offset + p] = sub(l, r, mod);
					}
					if (s + 1 != (1 << len))
						rot = mul(rot, info.rate2[Integer.numberOfTrailingZeros(~s)], mod);
				}
				len++;
			} else {
				// 4-base
				int p = 1 << (h - len - 2);
				long rot = 1;
				long imag = info.root[2];
				for (int s = 0; s < (1 << len); s++) {
					long rot2 = mul(rot, rot, mod);
					long rot3 = mul(rot2, rot, mod);
					int offset = s << (h - len);
					for (int i = 0; i < p; i++) {
						long mod2 = mod * mod;
						long a0 = a[i + offset];
						long a1 = a[i + offset + p] * rot;
						long a2 = a[i + offset + 2 * p] * rot2;
						long a3 = a[i + offset + 3 * p] * rot3;
						long a1na3imag = mod(a1 + mod2 - a3, mod) * imag;
						long na2 = mod2 - a2;
						a[i + offset] = mod(a0 + a2 + a1 + a3, mod);
						a[i + offset + 1 * p] = mod(a0 + a2 + (2 * mod2 - (a1 + a3)), mod);
						a[i + offset + 2 * p] = mod(a0 + na2 + a1na3imag, mod);
						a[i + offset + 3 * p] = mod(a0 + na2 + (mod2 - a1na3imag), mod);
					}
					if (s + 1 != (1 << len))
						rot = mul(rot, info.rate3[Integer.numberOfTrailingZeros(~s)], mod);
				}
				len += 2;
			}
		}
	}

	private static void butterfly_inv(long[] a, long mod) {
		int n = a.length;
		int h = Integer.numberOfTrailingZeros(n);
		FftInfo info = new FftInfo(mod);

		int len = h;
		while (len != 0) {
			if (len == 1) {
				int p = 1 << (h - len);
				long irot = 1;
				for (int s = 0; s < (1 << (len - 1)); s++) {
					int offset = s << (h - len + 1);
					for (int i = 0; i < p; i++) {
						long l = a[i + offset];
						long r = a[i + offset + p];
						a[i + offset] = add(l, r, mod);
						a[i + offset + p] = mul(sub(l, r, mod), irot, mod);
					}
					if (s + 1 != (1 << (len - 1)))
						irot = mul(irot, info.irate2[Integer.numberOfTrailingZeros(~s)], mod);
				}
				len--;
			} else {
				// 4-base
				int p = 1 << (h - len);
				long irot = 1;
				long iimag = info.iroot[2];
				for (int s = 0; s < (1 << (len - 2)); s++) {
					long irot2 = mul(irot, irot, mod);
					long irot3 = mul(irot2, irot, mod);
					int offset = s << (h - len + 2);
					for (int i = 0; i < p; i++) {
						long a0 = a[i + offset + 0 * p];
						long a1 = a[i + offset + 1 * p];
						long a2 = a[i + offset + 2 * p];
						long a3 = a[i + offset + 3 * p];

						long a2na3iimag = mul(sub(a2, a3, mod), iimag, mod);

						a[i + offset] = (a0 + a1 + a2 + a3) % mod;
						a[i + offset + 1 * p] = mul(mod(a0 - a1 + a2na3iimag, mod), irot, mod);
						a[i + offset + 2 * p] = mul(mod(a0 + a1 - a2 - a3, mod), irot2, mod);
						a[i + offset + 3 * p] = mul(mod(a0 - a1 - a2na3iimag, mod), irot3, mod);
					}
					if (s + 1 != (1 << (len - 2)))
						irot = mul(irot, info.irate3[Integer.numberOfTrailingZeros(~s)], mod);
				}
				len -= 2;
			}
		}
	}

	private static long[] convolution_naive(long[] a, long[] b, long mod) {
		int n = a.length, m = b.length;
		long[] ans = new long[n + m - 1];
		if (n < m) {
			for (int j = 0; j < m; j++) {
				for (int i = 0; i < n; i++) {
					ans[i + j] = add(ans[i + j], mul(a[i], b[j], mod), mod);
				}
			}
		} else {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					ans[i + j] = add(ans[i + j], mul(a[i], b[j], mod), mod);
				}
			}
		}
		return ans;
	}

	private static long[] convolution_fft(long[] a, long[] b, long mod) {
		int n = a.length, m = b.length;
		int z = bit_ceil(n + m - 1);
		
		a = Arrays.copyOf(a, z);
		butterfly(a, mod);
		b = Arrays.copyOf(b, z);
		butterfly(b, mod);
		for (int i = 0; i < z; i++) {
			a[i] = mul(a[i], b[i], mod);
		}
		butterfly_inv(a, mod);
		a = Arrays.copyOf(a, n + m - 1);
		long iz = inv(mod(z, mod), mod);
		for (int i = 0; i < n + m - 1; i++) a[i] = mul(a[i], iz, mod);
		return a;
	}

	public static long[] convolution(long[] a, long[] b, long mod) {
		int n = a.length, m = b.length;
		if (n == 0 || m == 0) return new long[0];
		int z = bit_ceil(n + m - 1);
		assert (mod - 1) % z == 0;

		if (Math.min(n, m) <= 60) return convolution_naive(a, b, mod);
		return convolution_fft(a, b, mod);
	}

	private static final long MOD1 = 754974721;  // 2^24
	private static final long MOD2 = 167772161;  // 2^25
	private static final long MOD3 = 469762049;  // 2^26
	private static final long M2M3 = MOD2 * MOD3;
	private static final long M1M3 = MOD1 * MOD3;
	private static final long M1M2 = MOD1 * MOD2;
	private static final long M1M2M3 = MOD1 * MOD2 * MOD3;
	private static final long i1 = inv(mod(MOD2 * MOD3, MOD1), MOD1);
	private static final long i2 = inv(mod(MOD1 * MOD3, MOD2), MOD2);
	private static final long i3 = inv(mod(MOD1 * MOD2, MOD3), MOD3);
	private static final int MAX_AB_BIT = 24;
	public static long[] convolution_ll(long[] a, long[] b) {
		int n = a.length, m = b.length;
		if (n == 0 || m == 0) return new long[0];
		if ( n + m - 1 > (1 << MAX_AB_BIT) ) throw new RuntimeException();

		long[] c1 = convolution(a, b, MOD1);
		long[] c2 = convolution(a, b, MOD2);
		long[] c3 = convolution(a, b, MOD3);

		long[] c = new long[n + m - 1];
		for (int i = 0; i < n + m - 1; i++) {
			long x = 0;
			x += mul(c1[i], i1, MOD1) * M2M3;
			x += mul(c2[i], i2, MOD2) * M1M3;
			x += mul(c3[i], i3, MOD3) * M1M2;
			long diff = c1[i] - mod(x, MOD1);
			if (diff < 0) diff += MOD1;
			final long[] offset = new long[]{0, 0, M1M2M3, 2 * M1M2M3, 3 * M1M2M3};
			x -= offset[(int)(diff % 5)];
			c[i] = x;
		}
		return c;
	}
}
// === end: math/Convolution.java ===
