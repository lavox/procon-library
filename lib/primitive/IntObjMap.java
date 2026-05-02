package primitive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntFunction;

public class IntObjMap<V> {
	private int[] keys;
	private Object[] values;
	private int capacity;
	private int mask;
	private int size;
	private int occupiedCnt;
	private int thr;
	private byte[] state;

	private static final byte EMPTY = 0;
	private static final byte OCCUPIED = 1;
	private static final byte REMOVED = 2;

	private static final int INITIAL_CAPACITY = 15;

	public IntObjMap() {
		this(INITIAL_CAPACITY);
	}
	public IntObjMap(int initialCapacity) {
		init(Integer.highestOneBit(initialCapacity) << 1);
	}
	public IntObjMap(IntObjMap<? extends V> from) {
		this.keys = from.keys.clone();
		this.values = from.values.clone();
		this.capacity = from.capacity;
		this.mask = from.mask;
		this.size = from.size;
		this.occupiedCnt = from.occupiedCnt;
		this.thr = from.thr;
		this.state = from.state.clone();
	}
	private void init(int capacity) {
		this.capacity = capacity;
		mask = capacity - 1;
		keys = new int[capacity];
		values = new Object[capacity];
		state = new byte[capacity];
		thr = Math.min((int)(capacity * 0.7), capacity - 1);
		occupiedCnt = 0;
		size = 0;
	}

	private static int hash(int x) {
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
	private int index(int key) {
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
	private boolean matchesKey(int idx, int key) {
		return state[idx] == OCCUPIED && keys[idx] == key;
	}
	private void assign(int idx, int key, V value) {
		if (state[idx] == EMPTY) occupiedCnt++;
		size++;
		keys[idx] = key;
		values[idx] = value;
		state[idx] = OCCUPIED;
	}
	private V _remove(int idx) {
		size--;
		state[idx] = REMOVED;
		V old = (V)values[idx];
		values[idx] = null;
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
		if (matchesKey(idx, key)) {
			return (V)values[idx];
		} else {
			return null;
		}
	}
	public boolean containsKey(int key) {
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
	@SuppressWarnings("unchecked")
	public V put(int key, V value) {
		ensureCapacity();
		int idx = index(key);
		if (matchesKey(idx, key)) {
			V old = (V)values[idx];
			values[idx] = value;
			return old;
		} else {
			assign(idx, key, value);
			return null;
		}
	}
	private void _put(int key, Object value) {
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
		int[] old_keys = keys;
		Object[] old_values = values;
		byte[] old_state = state;
		init(new_capacity);
		for (int i = 0; i < old_keys.length; i++) {
			if (old_state[i] == OCCUPIED) {
				_put(old_keys[i], old_values[i]);
			}
		}
		Arrays.fill(old_values, null);
	}
	@SuppressWarnings("unchecked")
	public V remove(int key) {
		int idx = index(key);
		if (matchesKey(idx, key)) {
			return _remove(idx);
		} else {
			return null;
		}
	}
	public boolean remove(int key, V value) {
		int idx = index(key);
		if (matchesKey(idx, key) && Objects.equals(values[idx], value)) {
			_remove(idx);
			return true;
		} else {
			return false;
		}
	}
	public void clear() {
		Arrays.fill(state, EMPTY);
		Arrays.fill(values, null);
		size = 0;
		occupiedCnt = 0;
	}
	public boolean containsValue(V value) {
		for (int i = 0; i < capacity; i++) {
			if (state[i] == OCCUPIED && Objects.equals(values[i], value)) return true;
		}
		return false;
	}
	public int[] keySet() {
		int[] ret = new int[size];
		int ii = 0;
		for (int i = 0; i < capacity; i++) {
			if (state[i] == OCCUPIED) ret[ii++] = keys[i];
		}
		return ret;
	}
	@SuppressWarnings("unchecked")
	public ArrayList<V> values() {
		ArrayList<V> ret = new ArrayList<>(size);
		for (int i = 0; i < capacity; i++) {
			if (state[i] == OCCUPIED) ret.add((V)values[i]);
		}
		return ret;
	}
	public ArrayList<Entry<V>> entrySet() {
		ArrayList<Entry<V>> ret = new ArrayList<>(size);
		for (int i = 0; i < capacity; i++) {
			if (state[i] == OCCUPIED) ret.add(new Entry<>(keys[i], (V)values[i]));
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
			while (pos < capacity && state[pos] != OCCUPIED) pos++;
		}
		public boolean hasNext() {
			return pos < capacity;
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
		if (matchesKey(idx, key)) {
			return (V)values[idx];
		} else {
			return defaultValue;
		}
	}
	@SuppressWarnings("unchecked")
	public V putIfAbsent(int key, V value) {
		ensureCapacity();
		int idx = index(key);
		if (matchesKey(idx, key)) {
			return (V)values[idx];
		} else {
			assign(idx, key, value);
			return null;
		}
	}
	public boolean replace(int key, V oldValue, V newValue) {
		int idx = index(key);
		if (matchesKey(idx, key) && Objects.equals(values[idx], oldValue)) {
			values[idx] = newValue;
			return true;
		} else {
			return false;
		}
	}
	public boolean replace(int key, V value) {
		int idx = index(key);
		if (matchesKey(idx, key)) {
			values[idx] = value;
			return true;
		} else {
			return false;
		}
	}
	public V computeIfAbsent(int key, IntFunction<V> mappingFunction) {
		ensureCapacity();
		int idx = index(key);
		if (matchesKey(idx, key)) {
			return (V)values[idx];
		} else {
			V v = mappingFunction.apply(key);
			if (v != null) {
				assign(idx, key, v);
			}
			return v;
		}
	}
	public V computeIfPresent(int key, RemappingFunction<? super V, ? extends V> remappingFunction) {
		int idx = index(key);
		if (matchesKey(idx, key)) {
			V v = remappingFunction.apply(key, (V)values[idx]);
			if (v == null) {
				_remove(idx);
			} else {
				values[idx] = v;
			}
			return v;
		} else {
			return null;
		}
	}
	public V compute(int key, RemappingFunction<? super V, ? extends V> remappingFunction) {
		ensureCapacity();
		int idx = index(key);
		if (matchesKey(idx, key)) {
			V v = remappingFunction.apply(key, (V)values[idx]);
			if (v == null) {
				_remove(idx);
			} else {
				values[idx] = v;
			}
			return v;
		} else {
			V v = remappingFunction.apply(key, null);
			if (v != null) {
				assign(idx, key, v);
			}
			return v;
		}
	}
	public V merge(int key, V value, MergeFunction<? super V, ? extends V> remappingFunction) {
		if (value == null || remappingFunction == null) throw new NullPointerException();
		ensureCapacity();
		int idx = index(key);
		if (matchesKey(idx, key)) {
			V v = remappingFunction.apply((V)values[idx], value);
			if (v == null) {
				_remove(idx);
			} else {
				values[idx] = v;
			}
			return v;
		} else {
			assign(idx, key, value);
			return value;
		}
	}

	public void forEach(KeyValueConsumer<? super V> action) {
		for (int i = 0; i < capacity; i++) {
			if (state[i] == OCCUPIED) {
				action.accept(keys[i], (V)values[i]);
			}
		}
	}
	public void replaceAll(RemappingFunction<? super V, ? extends V> function) {
		for (int i = 0; i < capacity; i++) {
			if (state[i] == OCCUPIED) {
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
