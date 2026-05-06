package primitive;

import java.util.Arrays;
import java.util.function.IntToLongFunction;
import java.util.function.LongBinaryOperator;

public class IntLongMap {
	private int[] keys = null;
	private long[] values = null;
	private int capacity = 0;
	private int mask = 0;
	private int size = 0;
	private int thr = 0;
	private long defaultValue = DEFAULT_VALUE;
	private float loadFactor = DEFAULT_LOAD_FACTOR;

	private static final int EMPTY = 0;
	private static final int EMPTY_FOR_EXTRA = 1;
	private static final int NEG = 1 << 31;

	private static final int INITIAL_CAPACITY = 7;
	private static final long DEFAULT_VALUE = Long.MIN_VALUE;
	private static final float DEFAULT_LOAD_FACTOR = 0.5f;

	private static final int RANDOM = (int)System.nanoTime();

	private static final LongBinaryOperator COUNT_UP = (a, b) -> a + b;

	public IntLongMap() {
		this(INITIAL_CAPACITY, DEFAULT_VALUE, DEFAULT_LOAD_FACTOR);
	}
	public IntLongMap(int initialCapacity) {
		this(initialCapacity, DEFAULT_VALUE, DEFAULT_LOAD_FACTOR);
	}
	public IntLongMap(int initialCapacity, float loadFactor) {
		this(initialCapacity, DEFAULT_VALUE, loadFactor);
	}
	public IntLongMap(int initialCapacity, long defaultValue) {
		this(initialCapacity, defaultValue, DEFAULT_LOAD_FACTOR);
	}
	public IntLongMap(int initialCapacity, long defaultValue, float loadFactor) {
		this.defaultValue = defaultValue;
		this.loadFactor = loadFactor;
		prepareArray(newCapacity(initialCapacity, 1, loadFactor));
	}
	public IntLongMap(IntLongMap from) {
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
		keys = new int[capacity + 1];
		keys[capacity] = EMPTY_FOR_EXTRA;
		values = new long[capacity + 1];
		thr = (int)(capacity * loadFactor);
	}
	private static int newCapacity(int sz, int cap, float lf) {
		cap = Math.max(Integer.highestOneBit(cap) << 1, 16);
		while (sz >= (int)(cap * lf)) cap <<= 1;
		return cap;
	}

	private int hash(int x) {
		x = (x ^ RANDOM) * 0x9e3779b9;
		return x ^ (x >>> 16);
	}
	private int index(int key) {
		if (key == EMPTY) return keys[capacity] == EMPTY_FOR_EXTRA ? capacity | NEG : capacity;
		int cur;
		if (keys[cur = hash(key) & mask] == EMPTY) return cur | NEG;
		if (keys[cur] == key) return cur;
		while (keys[(cur = (cur + 1) & mask)] != EMPTY) {
			if (keys[cur] == key) return cur;
		}
		return cur | NEG;
	}
	private void _insert(int idx, int key, long value) {
		keys[idx] = key;
		values[idx] = value;
		size++;
	}
	private long _update(int idx, long value) {
		long old = values[idx];
		values[idx] = value;
		return old;
	}

	private boolean _canShift(int idxFrom, int idxTo, int key) {
		int i = hash(key) & mask;
		if (idxTo < idxFrom) {
			return i <= idxTo || idxFrom < i;
		} else {
			return idxFrom < i && i <= idxTo;
		}
	}
	private long _remove(int idx) {
		size--;
		if (idx == capacity) {
			keys[idx] = EMPTY_FOR_EXTRA;
			return values[idx];
		}

		int cur = idx;
		long ret = values[cur];
		int prev = cur;
		int k;
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
	public long get(int key) {
		int idx = index(key);
		if (idx >= 0) {
			return values[idx];
		} else {
			return defaultValue;
		}
	}
	public boolean containsKey(int key) {
		return index(key) >= 0;
	}
	private void ensureCapacity() {
		if (size >= thr) {
			resize(newCapacity(size, capacity, loadFactor));
		}
	}
	public long put(int key, long value) {
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
		final int[] old_keys = keys;
		final long[] old_values = values;
		final int old_capacity = capacity;
		prepareArray(new_capacity);
		final int[] new_keys = keys;
		final long[] new_values = values;
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
	public long remove(int key) {
		int idx = index(key);
		if (idx >= 0) {
			return _remove(idx);
		} else {
			return defaultValue;
		}
	}
	public boolean remove(int key, long value) {
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
	public boolean containsValue(long value) {
		if (keys[capacity] != EMPTY_FOR_EXTRA && values[capacity] == value) return true;
		for (int i = 0; i < capacity; i++) {
			if (keys[i] != EMPTY && values[i] == value) return true;
		}
		return false;
	}
	public int[] keySet() {
		int[] ret = new int[size];
		int ri = 0;
		if (keys[capacity] != EMPTY_FOR_EXTRA) ret[ri++] = keys[capacity];
		for (int i = 0; i < capacity; i++) {
			if (keys[i] != EMPTY) ret[ri++] = keys[i];
		}
		return ret;
	}
	public long[] values() {
		long[] ret = new long[size];
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
		private int k;
		private long v;
		Entry(int k, long v) {
			this.k = k;
			this.v = v;
		}
		public int key() {
			return k;
		}
		public long value() {
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
		public int key() {
			return keys[prev_pos];
		}
		public long value() {
			return values[prev_pos];
		}
	}

	public long getOrDefault(int key, long defaultValue) {
		int idx = index(key);
		if (idx >= 0) {
			return values[idx];
		} else {
			return defaultValue;
		}
	}
	public long putIfAbsent(int key, long value) {
		ensureCapacity();
		int idx = index(key);
		if (idx >= 0) {
			return values[idx];
		} else {
			_insert(NEG ^ idx, key, value);
			return defaultValue;
		}
	}
	public boolean replace(int key, long oldValue, long newValue) {
		int idx = index(key);
		if (idx >= 0 && values[idx] == oldValue) {
			_update(idx, newValue);
			return true;
		} else {
			return false;
		}
	}
	public long replace(int key, long value) {
		int idx = index(key);
		if (idx >= 0) {
			return _update(idx, value);
		} else {
			return defaultValue;
		}
	}
	public long computeIfAbsent(int key, IntToLongFunction mappingFunction) {
		ensureCapacity();
		int idx = index(key);
		if (idx >= 0) {
			return values[idx];
		} else {
			long v = mappingFunction.applyAsLong(key);
			_insert(NEG ^ idx, key, v);
			return v;
		}
	}
	public long computeIfPresent(int key, RemappingFunction remappingFunction) {
		int idx = index(key);
		if (idx >= 0) {
			long v = remappingFunction.apply(key, values[idx]);
			_update(idx, v);
			return v;
		} else {
			return defaultValue;
		}
	}
	public long compute(int key, RemappingFunction remappingFunction) {
		ensureCapacity();
		int idx = index(key);
		if (idx >= 0) {
			long v = remappingFunction.apply(key, values[idx]);
			_update(idx, v);
			return v;
		} else {
			long v = remappingFunction.apply(key, defaultValue);
			_insert(NEG ^ idx, key, v);
			return v;
		}
	}
	public long merge(int key, long value, LongBinaryOperator remappingFunction) {
		ensureCapacity();
		int idx = index(key);
		if (idx >= 0) {
			long v = remappingFunction.applyAsLong(values[idx], value);
			_update(idx, v);
			return v;
		} else {
			_insert(NEG ^ idx, key, value);
			return value;
		}
	}
	public long countUp(int key, long value) {
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
	public IntLongMap clone() {
		return new IntLongMap(this);
	}

	@FunctionalInterface
	public interface RemappingFunction {
		public long apply(int key, long value);
	}
	@FunctionalInterface
	public interface KeyValueConsumer {
		public void accept(int key, long value);
	}
}
