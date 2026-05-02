package primitive;

import java.util.PrimitiveIterator;
import java.util.function.LongConsumer;

public class LongSet {
	private LongIntMap map;
	private static final int PRESENT = 1;

	public LongSet() {
		map = new LongIntMap();
	}
	public LongSet(int initialCapacity) {
		map = new LongIntMap(initialCapacity);
	}
	public LongSet(long[] c) {
		map = new LongIntMap(Math.max(c.length, 8));
		for (int i = 0; i < c.length; i++) add(c[i]);
	}
	private LongSet(LongSet from) {
		this.map = from.map.clone();
	}
	public int size() { return map.size(); }
	public boolean isEmpty() { return map.isEmpty(); }
	public boolean contains(long e) {return map.containsKey(e); }
	public boolean containsAll(long[] c) {
		for (int i = 0; i < c.length; i++) {
			if (!contains(c[i])) return false;
		}
		return true;
	}
	public boolean add(long e) { return map.put(e, PRESENT) != PRESENT; }
	public boolean addAll(long[] c) {
		boolean ret = false;
		for (int i = 0; i < c.length; i++) ret |= add(c[i]);
		return ret;
	}
	public boolean remove(long e) {return map.remove(e) == PRESENT; }
	public boolean removeAll(long[] c) {
		boolean ret = false;
		for (int i = 0; i < c.length; i++) ret |= remove(c[i]);
		return ret;
	}
	public void clear() { map.clear(); }
	@Override
	public LongSet clone() { return new LongSet(this); }

	public PrimitiveIterator.OfLong iterator() {
		return new LongIterator();
	}
	private class LongIterator implements PrimitiveIterator.OfLong {
		private LongIntMap.EntryIterator it;
		LongIterator() {
			it = map.entryIterator();
		}
		@Override
		public boolean hasNext() {
			return it.hasNext();
		}
		@Override
		public long nextLong() {
			it.next();
			return it.key();
		}
	}

	public long[] toArray() { return map.keySet(); }
	public void forEach(LongConsumer action) {
		map.forEach((k, v) -> action.accept(k));
	}
}
