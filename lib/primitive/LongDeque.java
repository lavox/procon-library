package primitive;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;

public class LongDeque {
	private long[] data = null;
	private int head = 0;
	private int tail = 0;
	private int size = 0;
	private static final int DEFAULT_CAPACITY = 16;

	public LongDeque() {
		this(DEFAULT_CAPACITY);
	}
	public LongDeque(int numElements) {
		data = new long[Math.max(numElements, 0) + 1];
	}
	public LongDeque(long[] c) {
		this(c.length);
		System.arraycopy(c, 0, data, 0, c.length);
		head = 0;
		tail = c.length;
		size = c.length;
	}
	private int inc(int i, int len) {
		if (++i >= len) i = 0;
		return i;
	}
	private int dec(int i, int len) {
		if (--i < 0) i = len - 1;
		return i;
	}
	private void ensureCapacity(int minCapacity) {
		int oldCapacity = data.length;
		if (oldCapacity < minCapacity) {
			boolean full = oldCapacity == size;
			int extend = oldCapacity < 64 ? oldCapacity + 2 : oldCapacity >> 1;
			int newCapacity = Math.max(oldCapacity + extend, minCapacity);
			extend = newCapacity - oldCapacity;
			long[] old_data = data;
			data = Arrays.copyOf(data, newCapacity);
			if (tail < head || full) {
				System.arraycopy(old_data, head, data, head + extend, oldCapacity - head);
				head += extend;
			}
		}
	}
	public void addFirst(long e) {
		head = dec(head, data.length);
		data[head] = e;
		size++;
		ensureCapacity(size + 1);
	}
	public void addLast(long e) {
		data[tail] = e;
		tail = inc(tail, data.length);
		size++;
		ensureCapacity(size + 1);
	}
	public boolean add(long e) {
		addLast(e);
		return true;
	}
	public boolean addAll(long[] c) {
		if (c.length == 0) return false;
		ensureCapacity(size + c.length + 1);
		if (tail + c.length < data.length) {
			System.arraycopy(c, 0, data, tail, c.length);
		} else {
			System.arraycopy(c, 0, data, tail, data.length - tail);
			System.arraycopy(c, data.length - tail, data, 0, c.length - (data.length - tail));
		}
		tail = (tail + c.length) % data.length;
		size += c.length;
		return true;
	}
	public long pollFirst() {
		if (size == 0) throw new NoSuchElementException();
		long ret = data[head];
		head = inc(head, data.length);
		size--;
		return ret;
	}
	public long pollLast() {
		if (size == 0) throw new NoSuchElementException();
		tail = dec(tail, data.length);
		long ret = data[tail];
		size--;
		return ret;
	}
	public long poll() {
		return pollFirst();
	}
	public long peekFirst() {
		if (size == 0) throw new NoSuchElementException();
		return data[head];
	}
	public long peekLast() {
		if (size == 0) throw new NoSuchElementException();
		return data[dec(tail, data.length)];
	}
	public long peekFirst(int i) {
		if (i < 0 || i >= size) throw new IndexOutOfBoundsException();
		int index = head + i;
		if (index >= data.length) index -= data.length;
		return data[index];
	}
	public long peekLast(int i) {
		if (i < 0 || i >= size) throw new IndexOutOfBoundsException();
		int index = tail - i - 1;
		if (index < 0) index += data.length;
		return data[index];
	}
	public void push(long e) {
		addFirst(e);
	}
	public long pop() {
		return pollFirst();
	}
	public int size() {
		return size;
	}
	public boolean isEmpty() {
		return size == 0;
	}
	public PrimitiveIterator.OfLong iterator() {
		return new DequeIterator(head, size);
	}
	private final class DequeIterator implements PrimitiveIterator.OfLong {
		private int index;
		private int prev;
		private int rest;
		DequeIterator(int head, int size) {
			this.index = head;
			this.rest = size;
		}
		@Override
		public boolean hasNext() {
			return rest > 0;
		}
		@Override
		public long nextLong() {
			if (rest == 0) throw new NoSuchElementException();
			prev = index;
			index = inc(index, data.length);
			rest--;
			return data[prev];
		}
	}
	public PrimitiveIterator.OfLong descendingIterator() {
		return new DescendingDequeIterator(tail, size);
	}
	private final class DescendingDequeIterator implements PrimitiveIterator.OfLong {
		private int index;
		private int prev;
		private int rest;
		DescendingDequeIterator(int tail, int size) {
			this.index = dec(tail, data.length);
			this.rest = size;
		}
		@Override
		public boolean hasNext() {
			return rest > 0;
		}
		@Override
		public long nextLong() {
			if (rest == 0) throw new NoSuchElementException();
			prev = index;
			index = dec(index, data.length);
			rest--;
			return data[prev];
		}
	}
	public void clear() {
		head = 0;
		tail = 0;
		size = 0;
	}
	public long[] toArray() {
		long[] ret = new long[size];
		if (head <= tail) {
			System.arraycopy(data, head, ret, 0, size);
		} else {
			System.arraycopy(data, head, ret, 0, data.length - head);
			System.arraycopy(data, 0, ret, data.length - head, tail);
		}
		return ret;
	}
}
