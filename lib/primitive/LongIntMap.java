package primitive;

import java.util.Arrays;
import java.util.function.IntBinaryOperator;
import java.util.function.LongToIntFunction;

public class LongIntMap {
	private long[] keys;
	private int[] values;
	private int capacity;
	private int mask;
	private int size;
	private int occupiedCnt;
	private int thr;
	private byte[] state;
	private int defaultValue;

	private static final byte EMPTY = 0;
	private static final byte OCCUPIED = 1;
	private static final byte REMOVED = 2;

	private static final int INITIAL_CAPACITY = 15;
	private static final int DEFAULT_VALUE = Integer.MIN_VALUE;

	private static final IntBinaryOperator COUNT_UP = (a, b) -> a + b;

	public LongIntMap() {
		this(INITIAL_CAPACITY, DEFAULT_VALUE);
	}
	public LongIntMap(int initialCapacity) {
		this(initialCapacity, DEFAULT_VALUE);
	}
	public LongIntMap(int initialCapacity, int defaultValue) {
		this.defaultValue = defaultValue;
		init(Integer.highestOneBit(initialCapacity) << 1);
	}
	public LongIntMap(LongIntMap from) {
		this.keys = from.keys.clone();
		this.values = from.values.clone();
		this.capacity = from.capacity;
		this.mask = from.mask;
		this.size = from.size;
		this.occupiedCnt = from.occupiedCnt;
		this.thr = from.thr;
		this.state = from.state.clone();
		this.defaultValue = from.defaultValue;
	}
	private void init(int capacity) {
		this.capacity = capacity;
		mask = capacity - 1;
		keys = new long[capacity];
		values = new int[capacity];
		state = new byte[capacity];
		thr = Math.min((int)(capacity * 0.7), capacity - 1);
		occupiedCnt = 0;
		size = 0;
	}

	private static int hash(long x) {
		x = (x ^ (x >>> 30)) * 0xbf58476d1ce4e5b9L;
		x = (x ^ (x >>> 27)) * 0x94d049bb133111ebL;
		x ^= (x >>> 31);
		return (int)x;
	}
	private static int hash2(int x) {
		return ((x * 0x9e3779b9) >>> 1) | 1;
	}
	private int index(long key) {
		int h = hash(key);
		int d = hash2(h);
		int cur = h & mask;
		int target = -1;
		while (state[cur] != EMPTY) {
			if (state[cur] == OCCUPIED && keys[cur] == key) {
				return cur;
			} else if (state[cur] == REMOVED && target == -1) {
				target = cur;
			}
			cur = (cur + d) & mask;
		}
		return target == -1 ? cur : target;
	}
	private boolean matchesKey(int idx, long key) {
		return state[idx] == OCCUPIED && keys[idx] == key;
	}
	private void assign(int idx, long key, int value) {
		if (state[idx] == EMPTY) occupiedCnt++;
		size++;
		keys[idx] = key;
		values[idx] = value;
		state[idx] = OCCUPIED;
	}
	private int _remove(int idx) {
		size--;
		state[idx] = REMOVED;
		return values[idx];
	}

	public int size() {
		return size;
	}
	public boolean isEmpty() {
		return size == 0;
	}
	public int get(long key) {
		int idx = index(key);
		if (matchesKey(idx, key)) {
			return values[idx];
		} else {
			return defaultValue;
		}
	}
	public boolean containsKey(long key) {
		int idx = index(key);
		return matchesKey(idx, key);
	}
	private void ensureCapacity() {
		if (occupiedCnt >= thr) {
			if (size < capacity * 0.5) {
				resize(capacity);
			} else {
				resize(capacity << 1);
			}
		}
	}
	public int put(long key, int value) {
		ensureCapacity();
		int idx = index(key);
		if (matchesKey(idx, key)) {
			int old = values[idx];
			values[idx] = value;
			return old;
		} else {
			assign(idx, key, value);
			return defaultValue;
		}
	}
	private void _put(long key, int value) {
		int h = hash(key);
		int d = hash2(h);
		int cur = h & mask;
		while (state[cur] != EMPTY) {
			cur = (cur + d) & mask;
		}
		occupiedCnt++;
		size++;
		keys[cur] = key;
		values[cur] = value;
		state[cur] = OCCUPIED;
	}
	private void resize(int new_capacity) {
		long[] old_keys = keys;
		int[] old_values = values;
		byte[] old_state = state;
		init(new_capacity);
		for (int i = 0; i < old_keys.length; i++) {
			if (old_state[i] == OCCUPIED) {
				_put(old_keys[i], old_values[i]);
			}
		}
	}
	public int remove(long key) {
		int idx = index(key);
		if (matchesKey(idx, key)) {
			return _remove(idx);
		} else {
			return defaultValue;
		}
	}
	public boolean remove(long key, int value) {
		int idx = index(key);
		if (matchesKey(idx, key) && values[idx] == value) {
			_remove(idx);
			return true;
		} else {
			return false;
		}
	}
	public void clear() {
		Arrays.fill(state, EMPTY);
		size = 0;
		occupiedCnt = 0;
	}
	public boolean containsValue(int value) {
		for (int i = 0; i < capacity; i++) {
			if (state[i] == OCCUPIED && values[i] == value) return true;
		}
		return false;
	}
	public long[] keySet() {
		long[] ret = new long[size];
		int ii = 0;
		for (int i = 0; i < capacity; i++) {
			if (state[i] == OCCUPIED) ret[ii++] = keys[i];
		}
		return ret;
	}
	public int[] values() {
		int[] ret = new int[size];
		int ii = 0;
		for (int i = 0; i < capacity; i++) {
			if (state[i] == OCCUPIED) ret[ii++] = values[i];
		}
		return ret;
	}
	public Entry[] entrySet() {
		Entry[] ret = new Entry[size];
		int ii = 0;
		for (int i = 0; i < capacity; i++) {
			if (state[i] == OCCUPIED) ret[ii++] = new Entry(keys[i], values[i]);
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
			while (pos < capacity && state[pos] != OCCUPIED) pos++;
		}
		public boolean hasNext() {
			return pos < capacity;
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
		if (matchesKey(idx, key)) {
			return values[idx];
		} else {
			return defaultValue;
		}
	}
	public int putIfAbsent(long key, int value) {
		ensureCapacity();
		int idx = index(key);
		if (matchesKey(idx, key)) {
			return values[idx];
		} else {
			assign(idx, key, value);
			return defaultValue;
		}
	}
	public boolean replace(long key, int oldValue, int newValue) {
		int idx = index(key);
		if (matchesKey(idx, key) && values[idx] == oldValue) {
			values[idx] = newValue;
			return true;
		} else {
			return false;
		}
	}
	public boolean replace(long key, int value) {
		int idx = index(key);
		if (matchesKey(idx, key)) {
			values[idx] = value;
			return true;
		} else {
			return false;
		}
	}
	public int computeIfAbsent(long key, LongToIntFunction mappingFunction) {
		ensureCapacity();
		int idx = index(key);
		if (matchesKey(idx, key)) {
			return values[idx];
		} else {
			int v = mappingFunction.applyAsInt(key);
			assign(idx, key, v);
			return v;
		}
	}
	public int computeIfPresent(long key, RemappingFunction remappingFunction) {
		int idx = index(key);
		if (matchesKey(idx, key)) {
			int v = remappingFunction.apply(key, values[idx]);
			values[idx] = v;
			return v;
		} else {
			return defaultValue;
		}
	}
	public int compute(long key, RemappingFunction remappingFunction) {
		ensureCapacity();
		int idx = index(key);
		if (matchesKey(idx, key)) {
			int v = remappingFunction.apply(key, values[idx]);
			values[idx] = v;
			return v;
		} else {
			int v = remappingFunction.apply(key, defaultValue);
			assign(idx, key, v);
			return v;
		}
	}
	public int merge(long key, int value, IntBinaryOperator remappingFunction) {
		ensureCapacity();
		int idx = index(key);
		if (matchesKey(idx, key)) {
			int v = remappingFunction.applyAsInt(values[idx], value);
			values[idx] = v;
			return v;
		} else {
			assign(idx, key, value);
			return value;
		}
	}
	public int countUp(long key, int value) {
		return merge(key, value, COUNT_UP);
	}

	public void forEach(KeyValueConsumer action) {
		for (int i = 0; i < capacity; i++) {
			if (state[i] == OCCUPIED) {
				action.accept(keys[i], values[i]);
			}
		}
	}
	public void replaceAll(RemappingFunction function) {
		for (int i = 0; i < capacity; i++) {
			if (state[i] == OCCUPIED) {
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
