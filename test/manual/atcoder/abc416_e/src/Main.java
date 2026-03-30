import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.PrimitiveIterator;
import java.util.PriorityQueue;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

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
		ShortestPath.DistMap dm = WarshallFloyd.createMap(N + 1);
		for (int i = 0; i < M; i++) {
			int A = sc.nextInt() - 1;
			int B = sc.nextInt() - 1;
			long C = sc.nextLong();
			dm.dist[A][B] = Math.min(dm.dist[A][B], C);
			dm.dist[B][A] = Math.min(dm.dist[B][A], C);
		}
		int K = sc.nextInt();
		long T = sc.nextLong();
		int[] D = sc.nextIntArray(K, (n) -> n - 1);
		for (int i = 0; i < K; i++) {
			dm.dist[D[i]][N] = T;
			dm.dist[N][D[i]] = 0;
		}
		dm = WarshallFloyd.search(dm);
		int Q = sc.nextInt();
		ArrayList<Long> ans = new ArrayList<>();
		for (int q = 0; q < Q; q++) {
			int k = sc.nextInt();
			if (k == 1) {
				int x = sc.nextInt() - 1;
				int y = sc.nextInt() - 1;
				long t = sc.nextLong();
				dm = WarshallFloyd.updateEdge(dm, x, y, t);
				dm = WarshallFloyd.updateEdge(dm, y, x, t);
			} else if (k == 2) {
				int x = sc.nextInt() - 1;
				dm = WarshallFloyd.updateEdge(dm, x, N, T);
				dm = WarshallFloyd.updateEdge(dm, N, x, 0);
			} else {
				long sum = 0;
				for (int i = 0; i < N; i++) {
					for (int j = i + 1; j < N; j++) {
						if (dm.dist[i][j] == ShortestPath.INF) continue;
						sum += dm.dist[i][j] * 2;
					}
				}
				ans.add(sum);
			}
		}
		print(ans, LF);
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

// === begin: graph/WarshallFloyd.java ===
class WarshallFloyd extends ShortestPath {
	public static DistMap createMap(int n) {
		DistMap d = new DistMap(n);
		d.init();
		return d;
	}
	public static DistMap search(DistMap d) {
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
	public static DistMap updateEdge(DistMap d, int from, int to, long dist) {
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
}
// === end: graph/WarshallFloyd.java ===

// === begin: graph/ShortestPath.java ===
class ShortestPath {
	public static final long INF = Long.MAX_VALUE;
	public static final long NINF = Long.MIN_VALUE;

	public static class DistMap {
		public final int n;
		public final long[][] dist;
		protected DistMap(int n) {
			this.n = n;
			this.dist = new long[n][n];
		}
		protected void init() {
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
		protected boolean hasNegativeLoop = false;
		protected Dist(int n) {
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

	protected static class Step implements Comparable<Step> {
		protected final int p;
		protected final long d;
		protected final int parent;
		protected Step(int p, long d, int parent) {
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

	@FunctionalInterface
	public interface EdgePredicate {
		public boolean test(int from, int to, int id, long cost);
	}
}
// === end: graph/ShortestPath.java ===

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
