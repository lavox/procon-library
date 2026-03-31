import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PrimitiveIterator;
import java.util.PriorityQueue;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;

// template & library : https://github.com/lavox/procon-library
public class Main {
	public static void main(String[] args) {
		Main o = new Main();
		o.solve();
	}

	int[][] A = null;
	public void solve() {
		FastScanner sc = new FastScanner(System.in);
		int H = sc.nextInt();
		int W = sc.nextInt();
		int Y = sc.nextInt();
		A = new int[H + 2][W + 2];
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				A[i + 1][j + 1] = sc.nextInt();
			}
		}
		GridGraph g = new GridGraph(H + 2, W + 2);
		int[] dist = new int[g.size()];
		final int INF = Integer.MAX_VALUE;
		Arrays.fill(dist, INF);
		PriorityQueue<Step> queue = new PriorityQueue<>(CMP);
		dist[0] = 0;
		queue.add(new Step(0, 0));
		while (queue.size() > 0) {
			Step s = queue.poll();
			if (dist[s.p] != s.d) continue;
			for (PrimitiveIterator.OfInt iter = g.edgesTo(s.p); iter.hasNext();) {
				int to = iter.nextInt();
				int r1 = g.r(to);
				int c1 = g.c(to);
				int nd = Math.max(dist[s.p], A[r1][c1]);
				if (nd < dist[to]) {
					dist[to] = nd;
					queue.add(new Step(to, nd));
				}
			}
		}

		int[] ans = new int[Y];
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				int v = g.v(i + 1, j + 1);
				if (dist[v] == 0 || dist[v] > Y) continue;
				ans[dist[v] - 1]++;
			}
		}
		for (int i = 1; i < Y; i++) {
			ans[i] += ans[i - 1];
		}
		print(ans, LF, a -> H * W - a);
	}
	static final Comparator<Step> CMP = Comparator.<Step>comparingInt(s -> s.d).thenComparing(s -> s.p);
	class Step {
		int p = 0;
		int d = 0;
		Step(int p, int d) {
			this.p = p;
			this.d = d;
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

// === begin: graph/GridGraph.java ===
class GridGraph implements Graph {
	protected int height = 0;
	protected int width = 0;
	protected EdgePredicate forbiddenEdge = null;
	protected EdgeToLongFunction edgeCostProvider = null;
	protected int[][] dir = null;
	public static final int[][] DIR4 = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
	public static final int[][] DIR8 = {{0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}};

	public GridGraph(int height, int width) {
		this(height, width, DIR4);
	}
	public GridGraph(int height, int width, int[][] dir) {
		this.height = height;
		this.width = width;
		this.dir = dir;
	}
	public void setForbiddenEdge(EdgePredicate forbiddenEdge) {
		this.forbiddenEdge = forbiddenEdge;
	}
	public void setEdgeCostProvider(EdgeToLongFunction edgeCostProvider) {
		this.edgeCostProvider = edgeCostProvider;
	}
	@Override
	public int size() {
		return height * width;
	}
	public int height() {
		return height;
	}
	public int width() {
		return width();
	}
	public int r(int v) {
		return v / width;
	}
	public int c(int v) {
		return v % width;
	}
	public int v(int r, int c) {
		return r * width + c;
	}
	public boolean isValid(int r, int c) {
		return 0 <= r && r < height && 0 <= c && c < width;
	}
	public boolean isValid(int v) {
		return isValid(r(v), c(v));
	}
	public boolean isForbiddenEdge(int r0, int c0, int r1, int c1) {
		return forbiddenEdge != null && forbiddenEdge.test(r0, c0, r1, c1);
	}
	public long edgeCost(int r0, int c0, int r1, int c1) {
		return edgeCostProvider == null ? 1 : edgeCostProvider.applyAsLong(r0, c0, r1, c1);
	}
	@Override
	public void forEachEdge(int v, EdgeConsumer action) {
		int r0 = r(v);
		int c0 = c(v);
		for (int i = 0; i < dir.length; i++) {
			int r1 = r0 + dir[i][0];
			int c1 = c0 + dir[i][1];
			if (!isValid(r1, c1) || isForbiddenEdge(r0, c0, r1, c1)) continue;
			action.accept(v, v(r1, c1), -1, edgeCost(r0, c0, r1, c1));
		}
	}
	@Override
	public Iterable<? extends Edge> edges(int v) {
		ArrayList<Edge> edges = new ArrayList<>(4);
		int r0 = r(v);
		int c0 = c(v);
		for (int i = 0; i < dir.length; i++) {
			int r1 = r0 + dir[i][0];
			int c1 = c0 + dir[i][1];
			if (!isValid(r1, c1) || isForbiddenEdge(r0, c0, r1, c1)) continue;
			edges.add(new Edge(v, v(r1, c1), -1, edgeCost(r0, c0, r1, c1)));
		}
		return edges;
	}
	@Override
	public PrimitiveIterator.OfInt edgesTo(int v) {
		final int r0 = r(v);
		final int c0 = c(v);
		var iter = new PrimitiveIterator.OfInt() {
			int i = 0;
			int next = 0;
			@Override
			public boolean hasNext() { return i < dir.length; }
			@Override
			public int nextInt() {
				int ret = next;
				i++;
				advance();
				return ret;
			}
			private void advance() {
				while (i < dir.length) {
					int r1 = r0 + dir[i][0];
					int c1 = c0 + dir[i][1];
					if (isValid(r1, c1) && !isForbiddenEdge(r0, c0, r1, c1)) {
						next = v(r1, c1);
						break;
					}
					i++;
				}
			}
		};
		iter.advance();
		return iter;
	}
	@FunctionalInterface
	public interface EdgePredicate {
		public boolean test(int r0, int c0, int r1, int c1);
	}
	@FunctionalInterface
	public interface EdgeToLongFunction {
		public long applyAsLong(int r0, int c0, int r1, int c1);
	}
}
// === end: graph/GridGraph.java ===

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
