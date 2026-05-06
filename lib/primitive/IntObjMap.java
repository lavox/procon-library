package primitive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntFunction;

public class IntObjMap<V> {
	private int[] keys = null;
	private Object[] values = null;
	private int capacity = 0;
	private int mask = 0;
	private int size = 0;
	private int thr = 0;
	private float loadFactor = DEFAULT_LOAD_FACTOR;

	private static final int EMPTY = 0;
	private static final int EMPTY_FOR_EXTRA = 1;
	private static final int NEG = 1 << 31;

	private static final int INITIAL_CAPACITY = 7;
	private static final float DEFAULT_LOAD_FACTOR = 0.5f;

	private static final int RANDOM = (int)System.nanoTime();

	public IntObjMap() {
		this(INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
	}
	public IntObjMap(int initialCapacity) {
		this(initialCapacity, DEFAULT_LOAD_FACTOR);
	}
	public IntObjMap(float loadFactor) {
		this(INITIAL_CAPACITY, loadFactor);
	}
	public IntObjMap(int initialCapacity, float loadFactor) {
		this.loadFactor = loadFactor;
		prepareArray(newCapacity(initialCapacity, 1, loadFactor));
	}
	public IntObjMap(IntObjMap<V> from) {
		this.keys = from.keys.clone();
		this.values = from.values.clone();
		this.capacity = from.capacity;
		this.mask = from.mask;
		this.size = from.size;
		this.thr = from.thr;
		this.loadFactor = from.loadFactor;
	}
	private void prepareArray(int capacity) {
		assert Integer.bitCount(capacity) == 1;
		this.capacity = capacity;
		mask = capacity - 1;
		keys = new int[capacity + 1];
		keys[capacity] = EMPTY_FOR_EXTRA;
		values = new Object[capacity + 1];
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
	private void _insert(int idx, int key, V value) {
		keys[idx] = key;
		values[idx] = value;
		size++;
	}
	@SuppressWarnings("unchecked")
	private V _update(int idx, V value) {
		V old = (V)values[idx];
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
	@SuppressWarnings("unchecked")
	private V _remove(int idx) {
		size--;
		V old = (V)values[idx];
		if (idx == capacity) {
			keys[idx] = EMPTY_FOR_EXTRA;
			values[idx] = null;
			return old;
		}

		int cur = idx;
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
		values[prev] = null;
		return old;
	}

	public int size() {
		return size;
	}
	public boolean isEmpty() {
		return size == 0;
	}
	@SuppressWarnings("unchecked")
	public V get(int key) {
		int idx = index(key);
		if (idx >= 0) {
			return (V)values[idx];
		} else {
			return null;
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
	public V put(int key, V value) {
		ensureCapacity();
		int idx = index(key);
		if (idx >= 0) {
			return _update(idx, value);
		} else {
			_insert(NEG ^ idx, key, value);
			return null;
		}
	}
	private void resize(int new_capacity) {
		final int[] old_keys = keys;
		final Object[] old_values = values;
		final int old_capacity = capacity;
		prepareArray(new_capacity);
		final int[] new_keys = keys;
		final Object[] new_values = values;
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
	public V remove(int key) {
		int idx = index(key);
		if (idx >= 0) {
			return _remove(idx);
		} else {
			return null;
		}
	}
	public boolean remove(int key, V value) {
		int idx = index(key);
		if (idx >= 0 && Objects.equals(values[idx], value)) {
			_remove(idx);
			return true;
		} else {
			return false;
		}
	}
	public void clear() {
		Arrays.fill(keys, EMPTY);
		Arrays.fill(values, null);
		keys[capacity] = EMPTY_FOR_EXTRA;
		size = 0;
	}
	public boolean containsValue(V value) {
		if (keys[capacity] != EMPTY_FOR_EXTRA && Objects.equals(values[capacity], value)) return true;
		for (int i = 0; i < capacity; i++) {
			if (keys[i] != EMPTY && Objects.equals(values[i], value)) return true;
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
	@SuppressWarnings("unchecked")
	public ArrayList<V> values() {
		ArrayList<V> ret = new ArrayList<>(size);
		if (keys[capacity] != EMPTY_FOR_EXTRA) ret.add((V)values[capacity]);
		for (int i = 0; i < capacity; i++) {
			if (keys[i] != EMPTY) ret.add((V)values[i]);
		}
		return ret;
	}
	@SuppressWarnings("unchecked")
	public ArrayList<Entry<V>> entrySet() {
		ArrayList<Entry<V>> ret = new ArrayList<>(size);
		if (keys[capacity] != EMPTY_FOR_EXTRA) ret.add(new Entry<>(keys[capacity], (V)values[capacity]));
		for (int i = 0; i < capacity; i++) {
			if (keys[i] != EMPTY) ret.add(new Entry<>(keys[i], (V)values[i]));
		}
		return ret;
	}
	public static class Entry<V> {
		private int k;
		private V v;
		Entry(int k, V v) {
			this.k = k;
			this.v = v;
		}
		public int key() {
			return k;
		}
		public V value() {
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
		@SuppressWarnings("unchecked")
		public V value() {
			return (V)values[prev_pos];
		}
	}

	@SuppressWarnings("unchecked")
	public V getOrDefault(int key, V defaultValue) {
		int idx = index(key);
		if (idx >= 0) {
			return (V)values[idx];
		} else {
			return defaultValue;
		}
	}
	@SuppressWarnings("unchecked")
	public V putIfAbsent(int key, V value) {
		ensureCapacity();
		int idx = index(key);
		if (idx >= 0) {
			return (V)values[idx];
		} else {
			_insert(NEG ^ idx, key, value);
			return null;
		}
	}
	public boolean replace(int key, V oldValue, V newValue) {
		int idx = index(key);
		if (idx >= 0 && Objects.equals(values[idx], oldValue)) {
			_update(idx, newValue);
			return true;
		} else {
			return false;
		}
	}
	public V replace(int key, V value) {
		int idx = index(key);
		if (idx >= 0) {
			return _update(idx, value);
		} else {
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public V computeIfAbsent(int key, IntFunction<V> mappingFunction) {
		ensureCapacity();
		int idx = index(key);
		if (idx >= 0) {
			return (V)values[idx];
		} else {
			V v = mappingFunction.apply(key);
			if (v != null) {
				_insert(NEG ^ idx, key, v);
			}
			return v;
		}
	}
	@SuppressWarnings("unchecked")
	public V computeIfPresent(int key, RemappingFunction<? super V, ? extends V> remappingFunction) {
		int idx = index(key);
		if (idx >= 0) {
			V v = remappingFunction.apply(key, (V)values[idx]);
			if (v == null) {
				_remove(idx);
			} else {
				_update(idx, v);
			}
			return v;
		} else {
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public V compute(int key, RemappingFunction<? super V, ? extends V> remappingFunction) {
		ensureCapacity();
		int idx = index(key);
		if (idx >= 0) {
			V v = remappingFunction.apply(key, (V)values[idx]);
			if (v == null) {
				_remove(idx);
			} else {
				_update(idx, v);
			}
			return v;
		} else {
			V v = remappingFunction.apply(key, null);
			if (v != null) {
				_insert(NEG ^ idx, key, v);
			}
			return v;
		}
	}
	@SuppressWarnings("unchecked")
	public V merge(int key, V value, MergeFunction<? super V, ? extends V> remappingFunction) {
		if (value == null || remappingFunction == null) throw new NullPointerException();
		ensureCapacity();
		int idx = index(key);
		if (idx >= 0) {
			V v = remappingFunction.apply((V)values[idx], value);
			if (v == null) {
				_remove(idx);
			} else {
				_update(idx, v);
			}
			return v;
		} else {
			_insert(NEG ^ idx, key, value);
			return value;
		}
	}

	@SuppressWarnings("unchecked")
	public void forEach(KeyValueConsumer<? super V> action) {
		if (keys[capacity] != EMPTY_FOR_EXTRA) action.accept(keys[capacity], (V)values[capacity]);
		for (int i = 0; i < capacity; i++) {
			if (keys[i] != EMPTY) {
				action.accept(keys[i], (V)values[i]);
			}
		}
	}
	@SuppressWarnings("unchecked")
	public void replaceAll(RemappingFunction<? super V, ? extends V> function) {
		if (keys[capacity] != EMPTY_FOR_EXTRA) values[capacity] = function.apply(keys[capacity], (V)values[capacity]);
		for (int i = 0; i < capacity; i++) {
			if (keys[i] != EMPTY) {
				values[i] = function.apply(keys[i], (V)values[i]);
			}
		}
	}
	@Override
	public IntObjMap<V> clone() {
		return new IntObjMap<>(this);
	}

	@FunctionalInterface
	public interface RemappingFunction<V1, V2> {
		public V2 apply(int key, V1 value);
	}
	@FunctionalInterface
	public interface MergeFunction<V1, V2> {
		public V2 apply(V1 oldValue, V1 value);
	}
	@FunctionalInterface
	public interface KeyValueConsumer<V> {
		public void accept(int key, V value);
	}
}
