import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

import java.util.Arrays;
import java.util.PrimitiveIterator;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.LongToIntFunction;

// template & library : https://github.com/lavox/procon-library
// https://github.com/lavox/procon-library/blob/main/lib/template/Main.java
public class Main {
	public static void main(String[] args) {
		Main o = new Main();
		o.solve();
	}

	public void solve() {
		FastScanner sc = new FastScanner(System.in);
		int Q = sc.nextInt();
		int K = 30;
		Trie trie = new DenseTrie(2, Q * K);
		TrieMultiset tset = new TrieMultiset(trie, Q * K);
		IntArrayList ans = new IntArrayList();
		for (int q = 0; q < Q; q++) {
			int t = sc.nextInt();
			int x = sc.nextInt();
			if (t == 0) {
				IntUnaryOperator op = l -> (x >>> (K - l - 1)) & 1;
				if (!tset.contains(K, op)) tset.add(K, op);
			} else if (t == 1) {
				IntUnaryOperator op = l -> (x >>> (K - l - 1)) & 1;
				if (tset.contains(K, op)) tset.remove(K, op);
			} else {
				int a = 0;
				int node = Trie.ROOT;
				for (int l = 0; l < K; l++) {
					int c = (x >>> (K - l - 1)) & 1;
					a <<= 1;
					if (tset.passCount(trie.getChild(node, c)) == 0) {c = c ^ 1; a |= 1;};
					node = trie.getChild(node, c);
				}
				ans.add(a);
			}
		}
		print(ans.toArray(), LF);
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

// === begin: data_structure/Trie.java ===
interface Trie {
	public static final int NONE = -1;
	public static final int ROOT = 0;

	public int size();
	public int alphabetSize();
	public int getChild(int node, int i);
	public int createChild(int node, int i);
	public int getOrCreateChild(int node, int i);
}

class DenseTrie implements Trie {
	private IntArrayList child = null;
	private int alphabetSize = 0;
	private final IntArrayList NONE_LIST;
	protected int size = 0;

	public DenseTrie(int alphabetSize) {
		this(alphabetSize, 1);
	}
	public DenseTrie(int alphabetSize, int defaultCapacity) {
		this.alphabetSize = alphabetSize;
		int[] tmp = new int[alphabetSize];
		Arrays.fill(tmp, NONE);
		NONE_LIST = new IntArrayList(tmp);
		child = new IntArrayList(defaultCapacity * alphabetSize);
		newNode(NONE);
	}
	private int newNode(int idx) {
		int newNode = size++;
		child.addAll(NONE_LIST);
		if (idx != NONE) child.set(idx, newNode);
		return newNode;
	}

	@Override
	public int size() {return size;}
	@Override 
	public int alphabetSize() {return alphabetSize;}
	@Override
	public int getChild(int node, int i) {
		return child.get(index(node, i));
	}
	@Override
	public int createChild(int node, int i) {
		int idx = index(node, i);
		assert child.get(idx) == NONE;
		return newNode(idx);
	}
	@Override
	public int getOrCreateChild(int node, int i) {
		int idx = index(node, i);
		int curChild = child.get(idx);
		if (curChild == NONE) {
			return newNode(idx);
		} else {
			return curChild;
		}
	}

	private int index(int node, int i) {
		assert ROOT <= node && node < size;
		assert 0 <= i && i < alphabetSize;
		return node * alphabetSize + i;
	}
}

class SparseTrie implements Trie {
	private LongIntMap child = null;
	private int alphabetSize = 0;
	protected int size = 0;

	public SparseTrie(int alphabetSize) {
		this(alphabetSize, 1);
	}
	public SparseTrie(int alphabetSize, int defaultSize) {
		this.alphabetSize = alphabetSize;
		this.child = new LongIntMap(defaultSize, NONE);
		newNode(NONE);
	}
	private int newNode(long idx) {
		int newNode = size++;
		if (idx != NONE) child.put(idx, newNode);
		return newNode;
	}

	@Override
	public int size() {return size;}
	@Override 
	public int alphabetSize() {return alphabetSize;}
	@Override
	public int getChild(int node, int i) {
		return child.get(index(node, i));
	}
	@Override
	public int createChild(int node, int i) {
		long idx = index(node, i);
		assert child.get(idx) == NONE;
		return newNode(idx);
	}
	@Override
	public int getOrCreateChild(int node, int i) {
		long idx = index(node, i);
		int curChild = child.get(idx);
		if (curChild == NONE) {
			return newNode(idx);
		} else {
			return curChild;
		}
	}

	private long index(int node, int i) {
		assert ROOT <= node && node < size;
		assert 0 <= i && i < alphabetSize;
		return ((long)node) * alphabetSize + i;
	}
}

class TrieMultiset {
	private Trie trie;
	private IntArrayList passCnt;
	private IntArrayList terminalCnt;
	public TrieMultiset(Trie trie) {
		this(trie, trie.size());
	}
	public TrieMultiset(Trie trie, int defaultCapacity) {
		this.trie = trie;
		this.passCnt = new IntArrayList(defaultCapacity);
		this.terminalCnt = new IntArrayList(defaultCapacity);
		prepareSize(trie.size());
	}
	private void prepareSize(int requiredSize) {
		while (passCnt.size() < requiredSize) {
			passCnt.add(0);
			terminalCnt.add(0);
		}
	}

	public int size() {
		return passCount(Trie.ROOT);
	}
	public int add(int length, int[] data) {
		return add(length, data, null);
	}
	public int add(int length, int[] data, int[] path) {
		assert path == null || path.length >= length + 1;
		int node = Trie.ROOT;
		for (int l = 0; l < length; l++) {
			countUp(node, false);
			if (path != null) path[l] = node;
			node = trie.getOrCreateChild(node, data[l]);
			prepareSize(trie.size());
		}
		countUp(node, true);
		if (path != null) path[length] = node;
		return node;
	}
	public int add(int length, IntUnaryOperator dataAt) {
		return add(length, dataAt, null);
	}
	public int add(int length, IntUnaryOperator dataAt, int[] path) {
		assert path == null || path.length >= length + 1;
		int node = Trie.ROOT;
		for (int l = 0; l < length; l++) {
			countUp(node, false);
			if (path != null) path[l] = node;
			node = trie.getOrCreateChild(node, dataAt.applyAsInt(l));
			prepareSize(trie.size());
		}
		countUp(node, true);
		if (path != null) path[length] = node;
		return node;
	}
	public int remove(int length, int[] data) {
		return remove(length, data, null);
	}
	public int remove(int length, int[] data, int[] path) {
		assert path == null || path.length >= length + 1;
		int node = Trie.ROOT;
		for (int l = 0; l < length; l++) {
			countDown(node, false);
			if (path != null) path[l] = node;
			node = trie.getChild(node, data[l]);
		}
		countDown(node, true);
		if (path != null) path[length] = node;
		return node;
	}
	public int remove(int length, IntUnaryOperator dataAt) {
		return remove(length, dataAt, null);
	}
	public int remove(int length, IntUnaryOperator dataAt, int[] path) {
		assert path == null || path.length >= length + 1;
		int node = Trie.ROOT;
		for (int l = 0; l < length; l++) {
			countDown(node, false);
			if (path != null) path[l] = node;
			node = trie.getChild(node, dataAt.applyAsInt(l));
		}
		countDown(node, true);
		if (path != null) path[length] = node;
		return node;
	}
	int node(int length, int[] data) {
		int node = Trie.ROOT;
		for (int l = 0; l < length && node != Trie.NONE; l++) {
			node = trie.getChild(node, data[l]);
		}
		return node;
	}
	int node(int length, IntUnaryOperator dataAt) {
		int node = Trie.ROOT;
		for (int l = 0; l < length && node != Trie.NONE; l++) {
			node = trie.getChild(node, dataAt.applyAsInt(l));
		}
		return node;
	}
	public int passCount(int node) {
		return node == Trie.NONE ? 0 : passCnt.get(node);
	}
	public int terminalCount(int node) {
		return node == Trie.NONE ? 0 : terminalCnt.get(node);
	}
	public int count(int length, int[] data) {
		return terminalCount(node(length, data));
	}
	public int count(int length, IntUnaryOperator dataAt) {
		return terminalCount(node(length, dataAt));
	}
	public int countPrefix(int length, int[] data) {
		return passCount(node(length, data));
	}
	public int countPrefix(int length, IntUnaryOperator dataAt) {
		return passCount(node(length, dataAt));
	}
	public boolean contains(int length, int[] data) {
		return count(length, data) > 0;
	}
	public boolean contains(int length, IntUnaryOperator dataAt) {
		return count(length, dataAt) > 0;
	}

	private void countUp(int node, boolean terminal) {
		passCnt.set(node, passCnt.get(node) + 1);
		if (terminal) terminalCnt.set(node, terminalCnt.get(node) + 1);
	}
	private void countDown(int node, boolean terminal) {
		passCnt.set(node, passCnt.get(node) - 1);
		if (terminal) terminalCnt.set(node, terminalCnt.get(node) - 1);
	}
}
// === end: data_structure/Trie.java ===

// === begin: primitive/LongIntMap.java ===
// https://github.com/lavox/procon-library/blob/main/lib/primitive/LongIntMap.java
class LongIntMap {
	private long[] keys = null;
	private int[] values = null;
	private int capacity = 0;
	private int mask = 0;
	private int size = 0;
	private int thr = 0;
	private int defaultValue = DEFAULT_VALUE;
	private float loadFactor = DEFAULT_LOAD_FACTOR;

	private static final long EMPTY = 0;
	private static final long EMPTY_FOR_EXTRA = 1;
	private static final int NEG = 1 << 31;

	private static final int INITIAL_CAPACITY = 7;
	private static final int DEFAULT_VALUE = Integer.MIN_VALUE;
	private static final float DEFAULT_LOAD_FACTOR = 0.5f;

	private static final long RANDOM = System.nanoTime();

	private static final IntBinaryOperator COUNT_UP = (a, b) -> a + b;

	public LongIntMap() {
		this(INITIAL_CAPACITY, DEFAULT_VALUE, DEFAULT_LOAD_FACTOR);
	}
	public LongIntMap(int initialCapacity) {
		this(initialCapacity, DEFAULT_VALUE, DEFAULT_LOAD_FACTOR);
	}
	public LongIntMap(float loadFactor) {
		this(INITIAL_CAPACITY, DEFAULT_VALUE, loadFactor);
	}
	public LongIntMap(int initialCapacity, float loadFactor) {
		this(initialCapacity, DEFAULT_VALUE, loadFactor);
	}
	public LongIntMap(int initialCapacity, int defaultValue) {
		this(initialCapacity, defaultValue, DEFAULT_LOAD_FACTOR);
	}
	public LongIntMap(int initialCapacity, int defaultValue, float loadFactor) {
		this.defaultValue = defaultValue;
		this.loadFactor = loadFactor;
		prepareArray(newCapacity(initialCapacity, 1, loadFactor));
	}
	public LongIntMap(LongIntMap from) {
		this.keys = from.keys.clone();
		this.values = from.values.clone();
		this.capacity = from.capacity;
		this.mask = from.mask;
		this.size = from.size;
		this.thr = from.thr;
		this.defaultValue = from.defaultValue;
		this.loadFactor = from.loadFactor;
	}
	private void prepareArray(int capacity) {
		assert Integer.bitCount(capacity) == 1;
		this.capacity = capacity;
		mask = capacity - 1;
		keys = new long[capacity + 1];
		keys[capacity] = EMPTY_FOR_EXTRA;
		values = new int[capacity + 1];
		thr = (int)(capacity * loadFactor);
	}
	private static int newCapacity(int sz, int cap, float lf) {
		cap = Math.max(Integer.highestOneBit(cap) << 1, 16);
		while (sz >= (int)(cap * lf)) cap <<= 1;
		return cap;
	}

	private int hash(long x) {
		x = (x ^ RANDOM) * 0x9e3779b97f4a7c15L;
		x = x ^ (x >>> 32);
		return (int)(x ^ (x >>> 16));
	}
	private int index(long key) {
		if (key == EMPTY) return keys[capacity] == EMPTY_FOR_EXTRA ? capacity | NEG : capacity;
		int cur;
		if (keys[cur = hash(key) & mask] == EMPTY) return cur | NEG;
		if (keys[cur] == key) return cur;
		while (keys[(cur = (cur + 1) & mask)] != EMPTY) {
			if (keys[cur] == key) return cur;
		}
		return cur | NEG;
	}
	private void _insert(int idx, long key, int value) {
		keys[idx] = key;
		values[idx] = value;
		size++;
	}
	private int _update(int idx, int value) {
		int old = values[idx];
		values[idx] = value;
		return old;
	}

	private boolean _canShift(int idxFrom, int idxTo, long key) {
		int i = hash(key) & mask;
		if (idxTo < idxFrom) {
			return i <= idxTo || idxFrom < i;
		} else {
			return idxFrom < i && i <= idxTo;
		}
	}
	private int _remove(int idx) {
		size--;
		if (idx == capacity) {
			keys[idx] = EMPTY_FOR_EXTRA;
			return values[idx];
		}

		int cur = idx;
		int ret = values[cur];
		int prev = cur;
		long k;
		while ((k = keys[cur = (cur + 1) & mask]) != EMPTY) {
			if (_canShift(cur, prev, k)) {
				keys[prev] = k;
				values[prev] = values[cur];
				prev = cur;
			}
		}
		keys[prev] = EMPTY;
		return ret;
	}

	public int size() {
		return size;
	}
	public boolean isEmpty() {
		return size == 0;
	}
	public int get(long key) {
		int idx = index(key);
		if (idx >= 0) {
			return values[idx];
		} else {
			return defaultValue;
		}
	}
	public boolean containsKey(long key) {
		return index(key) >= 0;
	}
	private void ensureCapacity() {
		if (size >= thr) {
			resize(newCapacity(size, capacity, loadFactor));
		}
	}
	public int put(long key, int value) {
		ensureCapacity();
		int idx = index(key);
		if (idx >= 0) {
			return _update(idx, value);
		} else {
			_insert(NEG ^ idx, key, value);
			return defaultValue;
		}
	}
	private void resize(int new_capacity) {
		final long[] old_keys = keys;
		final int[] old_values = values;
		final int old_capacity = capacity;
		prepareArray(new_capacity);
		final long[] new_keys = keys;
		final int[] new_values = values;
		final int new_mask = mask;

		if (old_keys[old_capacity] != EMPTY_FOR_EXTRA) {
			new_keys[new_capacity] = old_keys[old_capacity];
			new_values[new_capacity] = old_values[old_capacity];
		}
		int cur;
		for (int oi = 0; oi < old_capacity; oi++) {
			if (old_keys[oi] != EMPTY) {
				if (new_keys[cur = hash(old_keys[oi]) & new_mask] != EMPTY) {
					while (new_keys[cur = (cur + 1) & new_mask] != EMPTY);
				}
				new_keys[cur] = old_keys[oi];
				new_values[cur] = old_values[oi];
			}
		}
	}
	public int remove(long key) {
		int idx = index(key);
		if (idx >= 0) {
			return _remove(idx);
		} else {
			return defaultValue;
		}
	}
	public boolean remove(long key, int value) {
		int idx = index(key);
		if (idx >= 0 && values[idx] == value) {
			_remove(idx);
			return true;
		} else {
			return false;
		}
	}
	public void clear() {
		Arrays.fill(keys, EMPTY);
		keys[capacity] = EMPTY_FOR_EXTRA;
		size = 0;
	}
	public boolean containsValue(int value) {
		if (keys[capacity] != EMPTY_FOR_EXTRA && values[capacity] == value) return true;
		for (int i = 0; i < capacity; i++) {
			if (keys[i] != EMPTY && values[i] == value) return true;
		}
		return false;
	}
	public long[] keySet() {
		long[] ret = new long[size];
		int ri = 0;
		if (keys[capacity] != EMPTY_FOR_EXTRA) ret[ri++] = keys[capacity];
		for (int i = 0; i < capacity; i++) {
			if (keys[i] != EMPTY) ret[ri++] = keys[i];
		}
		return ret;
	}
	public int[] values() {
		int[] ret = new int[size];
		int ri = 0;
		if (keys[capacity] != EMPTY_FOR_EXTRA) ret[ri++] = values[capacity];
		for (int i = 0; i < capacity; i++) {
			if (keys[i] != EMPTY) ret[ri++] = values[i];
		}
		return ret;
	}
	public Entry[] entrySet() {
		Entry[] ret = new Entry[size];
		int ri = 0;
		if (keys[capacity] != EMPTY_FOR_EXTRA) ret[ri++] = new Entry(keys[capacity], values[capacity]);
		for (int i = 0; i < capacity; i++) {
			if (keys[i] != EMPTY) ret[ri++] = new Entry(keys[i], values[i]);
		}
		return ret;
	}
	public static class Entry {
		private long k;
		private int v;
		Entry(long k, int v) {
			this.k = k;
			this.v = v;
		}
		public long key() {
			return k;
		}
		public int value() {
			return v;
		}
	}

	public EntryIterator entryIterator() {
		return new EntryIterator();
	}
	public class EntryIterator {
		private int prev_pos = -1;
		private int pos = -1;
		EntryIterator() {
			advance();
		}
		private void advance() {
			pos++;
			while (pos < capacity && keys[pos] == EMPTY) pos++;
			if (pos == capacity && keys[pos] == EMPTY_FOR_EXTRA) pos++;
		}
		public boolean hasNext() {
			return pos <= capacity;
		}
		public void next() {
			prev_pos = pos;
			advance();
		}
		public long key() {
			return keys[prev_pos];
		}
		public int value() {
			return values[prev_pos];
		}
	}

	public int getOrDefault(long key, int defaultValue) {
		int idx = index(key);
		if (idx >= 0) {
			return values[idx];
		} else {
			return defaultValue;
		}
	}
	public int putIfAbsent(long key, int value) {
		ensureCapacity();
		int idx = index(key);
		if (idx >= 0) {
			return values[idx];
		} else {
			_insert(NEG ^ idx, key, value);
			return defaultValue;
		}
	}
	public boolean replace(long key, int oldValue, int newValue) {
		int idx = index(key);
		if (idx >= 0 && values[idx] == oldValue) {
			_update(idx, newValue);
			return true;
		} else {
			return false;
		}
	}
	public int replace(long key, int value) {
		int idx = index(key);
		if (idx >= 0) {
			return _update(idx, value);
		} else {
			return defaultValue;
		}
	}
	public int computeIfAbsent(long key, LongToIntFunction mappingFunction) {
		ensureCapacity();
		int idx = index(key);
		if (idx >= 0) {
			return values[idx];
		} else {
			int v = mappingFunction.applyAsInt(key);
			_insert(NEG ^ idx, key, v);
			return v;
		}
	}
	public int computeIfPresent(long key, RemappingFunction remappingFunction) {
		int idx = index(key);
		if (idx >= 0) {
			int v = remappingFunction.apply(key, values[idx]);
			_update(idx, v);
			return v;
		} else {
			return defaultValue;
		}
	}
	public int compute(long key, RemappingFunction remappingFunction) {
		ensureCapacity();
		int idx = index(key);
		if (idx >= 0) {
			int v = remappingFunction.apply(key, values[idx]);
			_update(idx, v);
			return v;
		} else {
			int v = remappingFunction.apply(key, defaultValue);
			_insert(NEG ^ idx, key, v);
			return v;
		}
	}
	public int merge(long key, int value, IntBinaryOperator remappingFunction) {
		ensureCapacity();
		int idx = index(key);
		if (idx >= 0) {
			int v = remappingFunction.applyAsInt(values[idx], value);
			_update(idx, v);
			return v;
		} else {
			_insert(NEG ^ idx, key, value);
			return value;
		}
	}
	public int countUp(long key, int value) {
		return merge(key, value, COUNT_UP);
	}

	public void forEach(KeyValueConsumer action) {
		if (keys[capacity] != EMPTY_FOR_EXTRA) action.accept(keys[capacity], values[capacity]);
		for (int i = 0; i < capacity; i++) {
			if (keys[i] != EMPTY) {
				action.accept(keys[i], values[i]);
			}
		}
	}
	public void replaceAll(RemappingFunction function) {
		if (keys[capacity] != EMPTY_FOR_EXTRA) values[capacity] = function.apply(keys[capacity], values[capacity]);
		for (int i = 0; i < capacity; i++) {
			if (keys[i] != EMPTY) {
				values[i] = function.apply(keys[i], values[i]);
			}
		}
	}
	@Override
	public LongIntMap clone() {
		return new LongIntMap(this);
	}

	@FunctionalInterface
	public interface RemappingFunction {
		public int apply(long key, int value);
	}
	@FunctionalInterface
	public interface KeyValueConsumer {
		public void accept(long key, int value);
	}
}
// === end: primitive/LongIntMap.java ===

// === begin: primitive/IntArrayList.java ===
// https://github.com/lavox/procon-library/blob/main/lib/primitive/IntArrayList.java
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
		if (array.data != null) this.data = Arrays.copyOf(array.data, array.size);
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
		if (data == null) return false;
		for (int i = 0; i < size; i++) {
			if (data[i] == e) return true;
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
		if (data == null) return;
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
		if (data == null) return;
		Arrays.sort(data, 0, size);
	}
	public void sort(IntComparator c) {
		if (data == null) return;
		IntArrays.sort(data, 0, size, c);
	}
	public void sort(int fromIndex, int toIndex, IntComparator c) {
		if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) throw new IndexOutOfBoundsException();
		if (data == null) return;
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
		if (data != null) System.arraycopy(data, 0, a, 0, size);
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
// https://github.com/lavox/procon-library/blob/main/lib/primitive/IntArrays.java
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
// https://github.com/lavox/procon-library/blob/main/lib/primitive/IntComparator.java
@FunctionalInterface
interface IntComparator {
    int compare(int a, int b);
}
// === end: primitive/IntComparator.java ===
