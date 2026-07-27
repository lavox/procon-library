import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

import java.util.Arrays;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;

// https://github.com/lavox/procon-library
public class Main {
	public static void main(String[] args) {
		Main o = new Main();
		o.solve();
	}

	public void solve() {
		FastScanner sc = new FastScanner(System.in);
		int N = sc.nextInt();
		String S = sc.next();

		RollingHashBuilder rhb = RollingHashBuilder.create(RollingHashBuilder.MOD1L61);
		RollingHash rh = rhb.newHash(N);
		rh.add(S);
		int ans = 0;
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				while (i + ans + 1 <= j && j + ans + 1 <= N) {
					if (rh.hash(i, ans + 1) != rh.hash(j, ans + 1)) break;
					ans++;
				}
			}
		}
		System.out.println(ans);
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

// === begin: string/RollingHash.java ===
class RollingHash {
	private final RollingHashBuilder builder;
	private long[] hash = null;
	private int hlen = 0;
	private int pos = 0;

	protected RollingHash(int n, RollingHashBuilder builder) {
		this.builder = builder;
		this.prepare(n);
	}
	protected void prepare(int n) {
		this.hlen = n + 1;
		hash = new long[n + 1];
		pos = 0;
		hash[pos] = 0;
	}

	public void add(String s) {
		add((i) -> s.charAt(i), s.length());
	}
	public void add(int[] array) {
		add((i) -> array[i], array.length);
	}
	public void add(IntUnaryOperator op, int len) {
		for (int i = 0; i < len; i++) add(op.applyAsInt(i));
	}
	public void add(int v) {
		pos++;
		hash[pos % hlen] = builder.shiftAdd(hash[(pos - 1) % hlen], 1, v);
	}
	public long hash(int i0, int len) {
		assert 0 <= len && len < hlen;
		assert Math.max(pos - hlen + 1, 0) <= i0 && i0 + len <= pos;
		return builder.diffHash(hash[(i0 + len) % hlen], hash[i0 % hlen], len);
	}
}

class RollingHashBuilder {
	public static final long MOD1L61 = (1L << 61) - 1;
	public static final long MOD998244353 = 998244353;
	public static final long MOD1000000007 = 1000000007;
	public static final long MOD1000000009 = 1000000009;
	public static final long MOD1000000021 = 1000000021;
	public static final long MOD1000000033 = 1000000033;
	public static final long MOD1000000087 = 1000000087;

	private long m = 0;
	private long b = 0;
	private LongArrayList pow = null;
	private static final int DEFAULT_MAX_VALUE = 2000000;
	
	public RollingHash newHash(int n) {
		return new RollingHash(n, this);
	}
	public static RollingHashBuilder createWithBase(long b, long m) {
		RollingHashBuilder ret = m == MOD1L61 ? new RollingHashBuilder61(m) : new RollingHashBuilder(m);
		ret.prepare(b);
		return ret;
	}
	public static RollingHashBuilder create(long m) {
		return create(m, DEFAULT_MAX_VALUE);
	}
	public static RollingHashBuilder create(long m, int maxVal) {
		RollingHashBuilder ret = m == MOD1L61 ? new RollingHashBuilder61(m) : new RollingHashBuilder(m);
		ret.prepare(ret.findBase(m, maxVal));
		return ret;
	}
	protected RollingHashBuilder(long m) {
		this.m = m;
	}

	protected void prepare(long b) {
		this.b = b;
		pow = new LongArrayList();
		pow.add(1L);
	}
	protected long mul(long x, long y) {
		return (x * y) % m;
	}
	protected long diffHash(long h1, long h2, int len) {
		return mod(h1 + m - mul(h2, pow(len)));
	}
	protected long shiftAdd(long x, int len, long y) {
		return mod(mul(x, pow(len)) + y);
	}
	protected long mod(long x) {
		return x % m;
	}
	protected long pow(int n) {
		while (pow.size() <= n) {
			pow.add(mul(pow.get(pow.size() - 1), b));
		}
		return pow.get(n);
	}
	protected long pow(long x, long n) {
		long c = x, r = 1;
		while (n > 0) {
			if ((n & 1L) != 0) r = mul(r, c);
			c = mul(c, c);
			n >>>= 1;
		}
		return r;
	}
	protected long findBase(long m, int maxV) {
		Random rnd = new Random();
		long mMax = Math.min(m, Integer.MAX_VALUE);
		long root = findPrimitiveRoot(m);
		while (true) {
			int k = rnd.nextInt((int)(mMax - maxV - 2)) + maxV + 1;
			if (gcd(m - 1, k) != 1) continue;
			long b = pow(root, k);
			if (b > maxV) return b;
		}
	}
	private static long findPrimitiveRoot(long m) {
		if (m == MOD1L61) return 37;
		else if (m == MOD998244353) return 3;
		else if (m == MOD1000000007) return 5;
		else if (m == MOD1000000009) return 13;
		else if (m == MOD1000000021) return 2;
		else if (m == MOD1000000033) return 5;
		else if (m == MOD1000000087) return 3;
		throw new IllegalArgumentException("Base must be specified.");
	}
	private static long gcd(long a, long b) {
		while ( b != 0 ) {
			long tmp = b;
			b = a % b;
			a = tmp;
		}
		return a;
	}
}
class RollingHashBuilder61 extends RollingHashBuilder {
	private static final long MOD61 = MOD1L61;
	private static final long MASK30 = (1L << 30) - 1;
	private static final long MASK31 = (1L << 31) - 1;
	private static final long MASK61 = MOD1L61;
	protected RollingHashBuilder61(long m) {
		super(m);
	}
	@Override
	protected long mul(long x, long y) {
		return mul61(x, y);
	}
	@Override
	protected long mod(long x) {
		return mod61(x);
	}
	private static long mul61(long x, long y) {
		long au = x >> 31;
		long ad = x & MASK31;
		long bu = y >> 31;
		long bd = y & MASK31;
		long mid = ad * bu + au * bd;
		long midu = mid >> 30;
		long midd = mid & MASK30;
		long res = mod61(au * bu * 2 + midu + (midd << 31) + ad * bd);
		return res;
	}
	private static long mod61(long x) {
		long xu = x >>> 61;
		long xd = x & MASK61;
		long res = xu + xd;
		if (res >= MOD61) res -= MOD61;
		return res;
	}
}
// === end: string/RollingHash.java ===

// === begin: primitive/LongArrayList.java ===
class LongArrayList implements Iterable<Long> {
	private long[] data = null;
	private int size = 0;
	private static final int DEFAULT_CAPACITY = 10;

	public LongArrayList() {}
	public LongArrayList(int initialCapacity) {
		data = new long[initialCapacity];
	}
	public LongArrayList(long[] data) {
		this.data = Arrays.copyOf(data, data.length);
		this.size = data.length;
	}
	public LongArrayList(LongArrayList array) {
		this.data = Arrays.copyOf(array.data, array.size);
		this.size = array.size;
	}
	public boolean add(long e) {
		ensureCapacity(size + 1);
		data[size++] = e;
		return true;
	}
	public void add(int index, long element) {
		if (index < 0 || index > size) throw new IndexOutOfBoundsException();
		ensureCapacity(size + 1);
		System.arraycopy(data, index, data, index + 1, size - index);
		data[index] = element;
		size++;
	}
	public void addAll(LongArrayList c) {
		addAll(size, c);
	}
	public void addAll(int index, LongArrayList c) {
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
	public LongArrayList clone() {
		LongArrayList copy = new LongArrayList();
		if (data != null) {
			copy.data = new long[data.length];
			System.arraycopy(data, 0, copy.data, 0, data.length);
		}
		copy.size = size;
		return copy;
	}
	public boolean contains(long e) {
		for (long value : data) {
			if (value == e) return true;
		}
		return false;
	}
	public void ensureCapacity(int minCapacity) {
		if (data == null) {
			data = new long[Math.max(DEFAULT_CAPACITY, minCapacity)];
		} else if (data.length < minCapacity) {
			data = Arrays.copyOf(data, Math.max(data.length * 2, minCapacity));
		}
	}
	public void forEach(LongConsumer action) {
		if (data != null) {
			for (int i = 0; i < size; i++) {
				action.accept(data[i]);
			}
		}
	}
	public long get(int index) {
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
		return data[index];
	}
	public long last() {
		if (size == 0) throw new IndexOutOfBoundsException();
		return data[size - 1];
	}
	public int indexOf(long e) {
		for (int i = 0; i < size; i++) {
			if (data[i] == e) return i;
		}
		return -1;
	}
	public boolean isEmpty() {
		return size == 0;
	}
	public PrimitiveIterator.OfLong iterator() {
		return new LongArrayIterator(data, size);
	}
	private final class LongArrayIterator implements PrimitiveIterator.OfLong {
		private int index = 0;
		private int size;
		private long[] data;
		LongArrayIterator(long[] data, int size) {
			this.data = data;
			this.size = size;
		}
		@Override
		public boolean hasNext() {
			return index != size;
		}
		@Override
		public long nextLong() {
			int i = index;
			if (i == size) throw new IndexOutOfBoundsException();
			index = i + 1;
			return data[i];
		}
	}
	public int lastIndexOf(long e) {
		for (int i = size - 1; i >= 0; i--) {
			if (data[i] == e) return i;
		}
		return -1;
	}
	public long removeByIndex(int index) {
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
		long oldValue = data[index];
		System.arraycopy(data, index + 1, data, index, size - index - 1);
		data[--size] = 0;
		return oldValue;
	}
	public long removeLast() {
		if (size == 0) throw new IndexOutOfBoundsException();
		return data[--size];
	}
	public boolean removeByVal(long e) {
		int index = indexOf(e);
		if (index >= 0) {
			removeByIndex(index);
			return true;
		}
		return false;
	}
	public boolean removeAll(LongArrayList c) {
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
	public boolean removeIf(LongPredicate filter) {
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
	public void replaceAll(LongUnaryOperator operator) {
		if (data == null) return;
		for (int i = 0; i < size; i++) {
			data[i] = operator.applyAsLong(data[i]);
		}
	}
	public boolean retainAll(LongArrayList c) {
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
	public long set(int index, long element) {
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
		long oldValue = data[index];
		data[index] = element;
		return oldValue;
	}
	public int size() {
		return size;
	}
	public void sort() {
		Arrays.sort(data, 0, size);
	}
	public void sort(LongComparator c) {
		LongArrays.sort(data, 0, size, c);
	}
	public void sort(int fromIndex, int toIndex, LongComparator c) {
		if (toIndex < 0 || toIndex > size) throw new IndexOutOfBoundsException();
		LongArrays.sort(data, fromIndex, toIndex, c);
	}
	public long[] toArray() {
		if (data == null) return new long[0];
		return Arrays.copyOf(data, size);
	}
	public long[] toArray(long[] a) {
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
		if (o instanceof LongArrayList) {
			LongArrayList ol = (LongArrayList)o;
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
			hashCode = 31 * hashCode + (int)(data[i] ^ (data[i] >>> 32));
		}
		return hashCode;
	}
}
// === end: primitive/LongArrayList.java ===

// === begin: primitive/LongArrays.java ===
class LongArrays {
	public static void sort(long[] a, LongComparator comp) {
		sort(a, 0, a.length, comp);
	}
	public static void sort(long[] a, int fromIndex, int toIndex, LongComparator comp) {
		if (toIndex - fromIndex <= 1) return;
		int maxDepth = 2 * (31 - Integer.numberOfLeadingZeros(toIndex - fromIndex));
		introSort(a, fromIndex, toIndex, maxDepth, comp);
	}

	private static void introSort(long[] a, int left, int right, int maxDepth, LongComparator comp) {
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
			long pivot = a[mid];
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
	private static void insertionSort(long[] a, int left, int right, LongComparator comp) {
		for (int i = left + 1; i < right; i++) {
			long v = a[i];
			int j = i - 1;
			while (j >= left && comp.compare(a[j], v) > 0) {
				a[j + 1] = a[j];
				j--;
			}
			a[j + 1] = v;
		}
	}
	private static void heapSort(long[] a, int left, int right, LongComparator comp) {
		int n = right - left;
		for (int i = (n >>> 1) - 1; i >= 0; i--) downHeap(a, i, n, left, comp);
		for (int i = n - 1; i > 0; i--) {
			swap(a, left, left + i);
			downHeap(a, 0, i, left, comp);
		}
	}
	private static void downHeap(long[] a, int i, int n, int base, LongComparator comp) {
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
	private static void swap(long[] a, int i, int j) {
			long tmp = a[i];
			a[i] = a[j];
			a[j] = tmp;
	}
}
// === end: primitive/LongArrays.java ===

// === begin: primitive/LongComparator.java ===
@FunctionalInterface
interface LongComparator {
    int compare(long a, long b);
}
// === end: primitive/LongComparator.java ===
