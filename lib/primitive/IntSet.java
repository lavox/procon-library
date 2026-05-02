package primitive;

import java.util.PrimitiveIterator;
import java.util.function.IntConsumer;

public class IntSet {
	private IntIntMap map;
	private static final int PRESENT = 1;

	public IntSet() {
		map = new IntIntMap();
	}
	public IntSet(int initialCapacity) {
		map = new IntIntMap(initialCapacity);
	}
	public IntSet(int[] c) {
		map = new IntIntMap(Math.max(c.length, 8));
		for (int i = 0; i < c.length; i++) add(c[i]);
	}
	private IntSet(IntSet from) {
		this.map = from.map.clone();
	}
	public int size() { return map.size(); }
	public boolean isEmpty() { return map.isEmpty(); }
	public boolean contains(int e) {return map.containsKey(e); }
	public boolean containsAll(int[] c) {
		for (int i = 0; i < c.length; i++) {
			if (!contains(c[i])) return false;
		}
		return true;
	}
	public boolean add(int e) { return map.put(e, PRESENT) != PRESENT; }
	public boolean addAll(int[] c) {
		boolean ret = false;
		for (int i = 0; i < c.length; i++) ret |= add(c[i]);
		return ret;
	}
	public boolean remove(int e) {return map.remove(e) == PRESENT; }
	public boolean removeAll(int[] c) {
		boolean ret = false;
		for (int i = 0; i < c.length; i++) ret |= remove(c[i]);
		return ret;
	}
	public void clear() { map.clear(); }
	@Override
	public IntSet clone() { return new IntSet(this); }

	public PrimitiveIterator.OfInt iterator() {
		return new IntIterator();
	}
	private class IntIterator implements PrimitiveIterator.OfInt {
		private IntIntMap.EntryIterator it;
		IntIterator() {
			it = map.entryIterator();
		}
		@Override
		public boolean hasNext() {
			return it.hasNext();
		}
		@Override
		public int nextInt() {
			it.next();
			return it.key();
		}
	}

	public int[] toArray() { return map.keySet(); }
	public void forEach(IntConsumer action) {
		map.forEach((k, v) -> action.accept(k));
	}
}
