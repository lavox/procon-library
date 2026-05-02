package primitive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.LongBinaryOperator;
import java.util.function.ToLongFunction;

public class ObjLongMap<K> {
	private Object[] keys;
	private long[] values;
	private int capacity;
	private int mask;
	private int size;
	private int occupiedCnt;
	private int thr;
	private byte[] state;
	private long defaultValue;

	private static final byte EMPTY = 0;
	private static final byte OCCUPIED = 1;
	private static final byte REMOVED = 2;

	private static final int INITIAL_CAPACITY = 15;
	private static final long DEFAULT_VALUE = Long.MIN_VALUE;

	private static final LongBinaryOperator COUNT_UP = (a, b) -> a + b;

	public ObjLongMap() {
		this(INITIAL_CAPACITY, DEFAULT_VALUE);
	}
	public ObjLongMap(int initialCapacity) {
		this(initialCapacity, DEFAULT_VALUE);
	}
	public ObjLongMap(int initialCapacity, long defaultValue) {
		this.defaultValue = defaultValue;
		init(Integer.highestOneBit(initialCapacity) << 1);
	}
	public ObjLongMap(ObjLongMap<K> from) {
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
		keys = new Object[capacity];
		values = new long[capacity];
		state = new byte[capacity];
		thr = Math.min((int)(capacity * 0.7), capacity - 1);
		occupiedCnt = 0;
		size = 0;
	}

	private static <K> int hash(K k) {
		int x = k == null ? 0 : k.hashCode();
		x ^= (x >>> 16);
		x *= 0x7feb352d;
		x ^= (x >>> 15);
		x *= 0x846ca68b;
		x ^= (x >>> 16);
		return x;
	}
	private static int hash2(int x) {
		return ((x * 0x9e3779b9) >>> 1) | 1;
	}
	private int index(K key) {
		int h = hash(key);
		int d = hash2(h);
		int cur = h & mask;
		int target = -1;
		while (state[cur] != EMPTY) {
			if (state[cur] == OCCUPIED && Objects.equals(keys[cur], key)) {
				return cur;
			} else if (state[cur] == REMOVED && target == -1) {
				target = cur;
			}
			cur = (cur + d) & mask;
		}
		return target == -1 ? cur : target;
	}
	private boolean matchesKey(int idx, K key) {
		return state[idx] == OCCUPIED && Objects.equals(keys[idx], key);
	}
	private void assign(int idx, K key, long value) {
		if (state[idx] == EMPTY) occupiedCnt++;
		size++;
		keys[idx] = key;
		values[idx] = value;
		state[idx] = OCCUPIED;
	}
	private long _remove(int idx) {
		size--;
		state[idx] = REMOVED;
		keys[idx] = null;
		return values[idx];
	}

	public int size() {
		return size;
	}
	public boolean isEmpty() {
		return size == 0;
	}
	public long get(K key) {
		int idx = index(key);
		if (matchesKey(idx, key)) {
			return values[idx];
		} else {
			return defaultValue;
		}
	}
	public boolean containsKey(K key) {
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
	public long put(K key, long value) {
		ensureCapacity();
		int idx = index(key);
		if (matchesKey(idx, key)) {
			long old = values[idx];
			values[idx] = value;
			return old;
		} else {
			assign(idx, key, value);
			return defaultValue;
		}
	}
	private void _put(Object key, long value) {
		int h = hash((K)key);
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
		Object[] old_keys = keys;
		long[] old_values = values;
		byte[] old_state = state;
		init(new_capacity);
		for (int i = 0; i < old_keys.length; i++) {
			if (old_state[i] == OCCUPIED) {
				_put(old_keys[i], old_values[i]);
			}
		}
		Arrays.fill(old_keys, null);
	}
	public long remove(K key) {
		int idx = index(key);
		if (matchesKey(idx, key)) {
			return _remove(idx);
		} else {
			return defaultValue;
		}
	}
	public boolean remove(K key, long value) {
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
		Arrays.fill(keys, null);
		size = 0;
		occupiedCnt = 0;
	}
	public boolean containsValue(long value) {
		for (int i = 0; i < capacity; i++) {
			if (state[i] == OCCUPIED && values[i] == value) return true;
		}
		return false;
	}
	public ArrayList<K> keySet() {
		ArrayList<K> ret = new ArrayList<>(size);
		for (int i = 0; i < capacity; i++) {
			if (state[i] == OCCUPIED) ret.add((K)keys[i]);
		}
		return ret;
	}
	public long[] values() {
		long[] ret = new long[size];
		int ii = 0;
		for (int i = 0; i < capacity; i++) {
			if (state[i] == OCCUPIED) ret[ii++] = values[i];
		}
		return ret;
	}
	public ArrayList<Entry<K>> entrySet() {
		ArrayList<Entry<K>> ret = new ArrayList<>(size);
		for (int i = 0; i < capacity; i++) {
			if (state[i] == OCCUPIED) ret.add(new Entry<>((K)keys[i], values[i]));
		}
		return ret;
	}
	public static class Entry<K> {
		private K k;
		private long v;
		Entry(K k, long v) {
			this.k = k;
			this.v = v;
		}
		public K key() {
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
			while (pos < capacity && state[pos] != OCCUPIED) pos++;
		}
		public boolean hasNext() {
			return pos < capacity;
		}
		public void next() {
			prev_pos = pos;
			advance();
		}
		public K key() {
			return (K)keys[prev_pos];
		}
		public long value() {
			return values[prev_pos];
		}
	}

	public long getOrDefault(K key, long defaultValue) {
		int idx = index(key);
		if (matchesKey(idx, key)) {
			return values[idx];
		} else {
			return defaultValue;
		}
	}
	public long putIfAbsent(K key, long value) {
		ensureCapacity();
		int idx = index(key);
		if (matchesKey(idx, key)) {
			return values[idx];
		} else {
			assign(idx, key, value);
			return defaultValue;
		}
	}
	public boolean replace(K key, long oldValue, long newValue) {
		int idx = index(key);
		if (matchesKey(idx, key) && values[idx] == oldValue) {
			values[idx] = newValue;
			return true;
		} else {
			return false;
		}
	}
	public boolean replace(K key, long value) {
		int idx = index(key);
		if (matchesKey(idx, key)) {
			values[idx] = value;
			return true;
		} else {
			return false;
		}
	}
	public long computeIfAbsent(K key, ToLongFunction<K> mappingFunction) {
		ensureCapacity();
		int idx = index(key);
		if (matchesKey(idx, key)) {
			return values[idx];
		} else {
			long v = mappingFunction.applyAsLong(key);
			assign(idx, key, v);
			return v;
		}
	}
	public long computeIfPresent(K key, RemappingFunction<? super K> remappingFunction) {
		int idx = index(key);
		if (matchesKey(idx, key)) {
			long v = remappingFunction.apply(key, values[idx]);
			values[idx] = v;
			return v;
		} else {
			return defaultValue;
		}
	}
	public long compute(K key, RemappingFunction<? super K> remappingFunction) {
		ensureCapacity();
		int idx = index(key);
		if (matchesKey(idx, key)) {
			long v = remappingFunction.apply(key, values[idx]);
			values[idx] = v;
			return v;
		} else {
			long v = remappingFunction.apply(key, defaultValue);
			assign(idx, key, v);
			return v;
		}
	}
	public long merge(K key, long value, LongBinaryOperator remappingFunction) {
		ensureCapacity();
		int idx = index(key);
		if (matchesKey(idx, key)) {
			long v = remappingFunction.applyAsLong(values[idx], value);
			values[idx] = v;
			return v;
		} else {
			assign(idx, key, value);
			return value;
		}
	}
	public long countUp(K key, long value) {
		return merge(key, value, COUNT_UP);
	}

	public void forEach(KeyValueConsumer<? super K> action) {
		for (int i = 0; i < capacity; i++) {
			if (state[i] == OCCUPIED) {
				action.accept((K)keys[i], values[i]);
			}
		}
	}
	public void replaceAll(RemappingFunction<K> function) {
		for (int i = 0; i < capacity; i++) {
			if (state[i] == OCCUPIED) {
				values[i] = function.apply((K)keys[i], values[i]);
			}
		}
	}
	@Override
	public ObjLongMap<K> clone() {
		return new ObjLongMap<>(this);
	}

	@FunctionalInterface
	public interface RemappingFunction<K> {
		public long apply(K key, long value);
	}
	@FunctionalInterface
	public interface KeyValueConsumer<K> {
		public void accept(K key, long value);
	}
}
