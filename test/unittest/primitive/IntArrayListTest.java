package primitive;

import org.junit.jupiter.api.Test;
import java.util.PrimitiveIterator;
import static org.junit.jupiter.api.Assertions.*;

class IntArrayListTest {
	@Test
	void testAddAndGetSize() {
		IntArrayList list = new IntArrayList();
		assertEquals(0, list.size());

		for (int i = 0; i < 20; i++) {
			list.add(i);
		}

		assertEquals(20, list.size());
		for (int i = 0; i < 20; i++) {
			assertEquals(i, list.get(i));
		}
	}

	@Test
	void testAddAtIndex() {
		IntArrayList list = new IntArrayList();
		for (int i = 0; i < 5; i++) list.add(i);

		list.add(2, 99);
		assertEquals(6, list.size());
		assertEquals(99, list.get(2));
		assertEquals(2, list.get(3));
	}

	@Test
	void testAddAll() {
		IntArrayList list1 = new IntArrayList();
		IntArrayList list2 = new IntArrayList();
		for (int i = 0; i < 10; i++) list1.add(i);
		for (int i = 100; i < 110; i++) list2.add(i);

		list1.addAll(list2);
		assertEquals(20, list1.size());
		for (int i = 0; i < 10; i++) assertEquals(i, list1.get(i));
		for (int i = 10; i < 20; i++) assertEquals(100 + (i - 10), list1.get(i));
	}

	@Test
	void testRemoveByIndexAndValue() {
		IntArrayList list = new IntArrayList();
		for (int i = 100; i < 110; i++) list.add(i);

		int removed = list.removeByIndex(5);
		assertEquals(105, removed);
		assertEquals(9, list.size());

		assertTrue(list.removeByVal(103));
		assertFalse(list.removeByVal(1000));
		assertEquals(8, list.size());
		int[] expected = new int[] {100, 101, 102, 104, 106, 107, 108, 109};
		int[] actual = list.toArray();
		assertArrayEquals(expected, actual);
	}

	@Test
	void testRemoveIfAndRetainAll() {
		IntArrayList list = new IntArrayList();
		for (int i = 0; i < 10; i++) list.add(i);

		assertTrue(list.removeIf(x -> x % 2 == 0));
		assertEquals(5, list.size());
		for (int val : list.toArray()) assertTrue(val % 2 != 0);

		IntArrayList keep = new IntArrayList();
		keep.add(3);
		keep.add(7);
		assertTrue(list.retainAll(keep));
		assertEquals(2, list.size());
		assertEquals(3, list.get(0));
		assertEquals(7, list.get(1));
	}

	@Test
	void testSortAscendingAndCustom() {
		IntArrayList list = new IntArrayList();
		int[] arr = {5, 2, 9, 1, 8, 3, 7, 6, 4, 0};
		for (int v : arr) list.add(v);

		list.sort();
		for (int i = 0; i < list.size(); i++) assertEquals(i, list.get(i));

		list.sort((x, y) -> Integer.compare(y, x)); // Descending
		for (int i = 0; i < list.size(); i++) assertEquals(9 - i, list.get(i));
	}

	@Test
	void testIterator() {
		IntArrayList list = new IntArrayList();
		for (int i = 0; i < 10; i++) list.add(i);

		PrimitiveIterator.OfInt it = list.iterator();
		int count = 0;
		while (it.hasNext()) {
			assertEquals(count, it.nextInt());
			count++;
		}
		assertEquals(10, count);
		assertThrows(IndexOutOfBoundsException.class, it::nextInt);
	}

	@Test
	void testCloneAndToArray() {
		IntArrayList list = new IntArrayList();
		for (int i = 0; i < 16; i++) list.add(i);

		IntArrayList clone = list.clone();
		assertEquals(list.size(), clone.size());
		for (int i = 0; i < list.size(); i++) {
			assertEquals(list.get(i), clone.get(i));
		}

		int[] arr = clone.toArray();
		assertEquals(16, arr.length);
		for (int i = 0; i < arr.length; i++) assertEquals(i, arr[i]);
	}

	@Test
	void testEnsureCapacityAndTrimToSize() {
		IntArrayList list = new IntArrayList();
		for (int i = 0; i < 25; i++) list.add(i);
		int capacityBeforeTrim = list.toArray().length;

		list.trimToSize();
		int capacityAfterTrim = list.toArray().length;
		assertEquals(25, capacityAfterTrim);
		assertEquals(25, capacityBeforeTrim);
	}

	@Test
	void testEqualsAndHashCode() {
		IntArrayList list1 = new IntArrayList();
		IntArrayList list2 = new IntArrayList();
		for (int i = 0; i < 25; i++) {
			list1.add(i);
			list2.add(i);
		}
		assertTrue(list1.equals(list2));
		assertEquals(list1.hashCode(), list2.hashCode());

		list1.add(0);
		list2.add(1);
		assertFalse(list1.equals(list2));
		assertNotEquals(list1.hashCode(), list2.hashCode());
		list2.set(list2.size() - 1, 0);
		assertTrue(list1.equals(list2));
		assertEquals(list1.hashCode(), list2.hashCode());
		list1.add(0);
		assertFalse(list1.equals(list2));
		assertNotEquals(list1.hashCode(), list2.hashCode());
	}
}
