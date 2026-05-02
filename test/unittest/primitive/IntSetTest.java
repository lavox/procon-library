package primitive;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PrimitiveIterator;

public class IntSetTest {

	@Test
	public void testConstructor() {
		IntSet set = new IntSet();
		assertEquals(0, set.size());
		assertTrue(set.isEmpty());
		assertFalse(set.contains(0));
	}

	@Test
	public void testConstructorWithCapacity() {
		IntSet set = new IntSet(16);
		assertEquals(0, set.size());
		assertTrue(set.isEmpty());
	}

	@Test
	public void testConstructorWithArray() {
		int[] arr = {1, 2, 3};
		IntSet set = new IntSet(arr);
		assertEquals(3, set.size());
		assertTrue(set.contains(1));
		assertTrue(set.contains(2));
		assertTrue(set.contains(3));
		assertFalse(set.contains(4));
	}

	@Test
	public void testConstructorWithEmptyArray() {
		int[] arr = {};
		IntSet set = new IntSet(arr);
		assertEquals(0, set.size());
		assertTrue(set.isEmpty());
	}

	@Test
	public void testAdd() {
		IntSet set = new IntSet();
		assertTrue(set.add(1));
		assertEquals(1, set.size());
		assertTrue(set.contains(1));
		assertFalse(set.add(1)); // 重複追加
		assertEquals(1, set.size());
	}

	@Test
	public void testRemove() {
		IntSet set = new IntSet();
		set.add(1);
		assertTrue(set.remove(1));
		assertEquals(0, set.size());
		assertFalse(set.contains(1));
		assertFalse(set.remove(1)); // 存在しない
	}

	@Test
	public void testContains() {
		IntSet set = new IntSet();
		set.add(5);
		assertTrue(set.contains(5));
		assertFalse(set.contains(6));
	}

	@Test
	public void testContainsAll() {
		IntSet set = new IntSet();
		set.add(1);
		set.add(2);
		set.add(3);
		assertTrue(set.containsAll(new int[]{1, 2}));
		assertFalse(set.containsAll(new int[]{1, 4}));
		assertTrue(set.containsAll(new int[]{})); // 空配列
	}

	@Test
	public void testAddAll() {
		IntSet set = new IntSet();
		int[] arr = {1, 2, 3};
		assertTrue(set.addAll(arr));
		assertEquals(3, set.size());
		assertFalse(set.addAll(new int[]{1, 2})); // 重複
		assertEquals(3, set.size());
	}

	@Test
	public void testRemoveAll() {
		IntSet set = new IntSet(new int[]{1, 2, 3, 4});
		int[] arr = {2, 4};
		assertTrue(set.removeAll(arr));
		assertEquals(2, set.size());
		assertTrue(set.contains(1));
		assertFalse(set.contains(2));
		assertTrue(set.contains(3));
		assertFalse(set.contains(4));
		assertFalse(set.removeAll(new int[]{5})); // 存在しない
	}

	@Test
	public void testClear() {
		IntSet set = new IntSet(new int[]{1, 2, 3});
		set.clear();
		assertEquals(0, set.size());
		assertTrue(set.isEmpty());
	}

	@Test
	public void testClone() {
		IntSet original = new IntSet(new int[]{1, 2, 3});
		IntSet cloned = original.clone();
		assertEquals(original.size(), cloned.size());
		assertTrue(cloned.contains(1));
		assertTrue(cloned.contains(2));
		assertTrue(cloned.contains(3));
		// 独立性確認
		cloned.add(4);
		assertFalse(original.contains(4));
	}

	@Test
	public void testIterator() {
		IntSet set = new IntSet(new int[]{1, 2, 3});
		PrimitiveIterator.OfInt it = set.iterator();
		List<Integer> list = new ArrayList<>();
		while (it.hasNext()) {
			list.add(it.nextInt());
		}
		Collections.sort(list); // 順序不定なのでソート
		assertEquals(Arrays.asList(1, 2, 3), list);
	}

	@Test
	public void testToArray() {
		IntSet set = new IntSet(new int[]{1, 2, 3});
		int[] arr = set.toArray();
		Arrays.sort(arr); // 順序不定
		assertArrayEquals(new int[]{1, 2, 3}, arr);
	}

	@Test
	public void testForEach() {
		IntSet set = new IntSet(new int[]{1, 2, 3});
		List<Integer> list = new ArrayList<>();
		set.forEach(list::add);
		Collections.sort(list);
		assertEquals(Arrays.asList(1, 2, 3), list);
	}

	@Test
	public void testSize() {
		IntSet set = new IntSet();
		assertEquals(0, set.size());
		set.add(1);
		assertEquals(1, set.size());
		set.add(1); // 重複
		assertEquals(1, set.size());
		set.remove(1);
		assertEquals(0, set.size());
	}

	@Test
	public void testIsEmpty() {
		IntSet set = new IntSet();
		assertTrue(set.isEmpty());
		set.add(1);
		assertFalse(set.isEmpty());
		set.remove(1);
		assertTrue(set.isEmpty());
	}
}