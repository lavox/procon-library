package primitive;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PrimitiveIterator;

public class LongSetTest {

	@Test
	public void testConstructor() {
		LongSet set = new LongSet();
		assertEquals(0, set.size());
		assertTrue(set.isEmpty());
		assertFalse(set.contains(0L));
	}

	@Test
	public void testConstructorWithCapacity() {
		LongSet set = new LongSet(16);
		assertEquals(0, set.size());
		assertTrue(set.isEmpty());
	}

	@Test
	public void testConstructorWithArray() {
		long[] arr = {1L, 2L, 3L};
		LongSet set = new LongSet(arr);
		assertEquals(3, set.size());
		assertTrue(set.contains(1L));
		assertTrue(set.contains(2L));
		assertTrue(set.contains(3L));
		assertFalse(set.contains(4L));
	}

	@Test
	public void testConstructorWithEmptyArray() {
		long[] arr = {};
		LongSet set = new LongSet(arr);
		assertEquals(0, set.size());
		assertTrue(set.isEmpty());
	}

	@Test
	public void testAdd() {
		LongSet set = new LongSet();
		assertTrue(set.add(1L));
		assertEquals(1, set.size());
		assertTrue(set.contains(1L));
		assertFalse(set.add(1L)); // 重複追加
		assertEquals(1, set.size());
	}

	@Test
	public void testRemove() {
		LongSet set = new LongSet();
		set.add(1L);
		assertTrue(set.remove(1L));
		assertEquals(0, set.size());
		assertFalse(set.contains(1L));
		assertFalse(set.remove(1L)); // 存在しない
	}

	@Test
	public void testContains() {
		LongSet set = new LongSet();
		set.add(5L);
		assertTrue(set.contains(5L));
		assertFalse(set.contains(6L));
	}

	@Test
	public void testContainsAll() {
		LongSet set = new LongSet();
		set.add(1L);
		set.add(2L);
		set.add(3L);
		assertTrue(set.containsAll(new long[]{1L, 2L}));
		assertFalse(set.containsAll(new long[]{1L, 4L}));
		assertTrue(set.containsAll(new long[]{})); // 空配列
	}

	@Test
	public void testAddAll() {
		LongSet set = new LongSet();
		long[] arr = {1L, 2L, 3L};
		assertTrue(set.addAll(arr));
		assertEquals(3, set.size());
		assertFalse(set.addAll(new long[]{1L, 2L})); // 重複
		assertEquals(3, set.size());
	}

	@Test
	public void testRemoveAll() {
		LongSet set = new LongSet(new long[]{1L, 2L, 3L, 4L});
		long[] arr = {2L, 4L};
		assertTrue(set.removeAll(arr));
		assertEquals(2, set.size());
		assertTrue(set.contains(1L));
		assertFalse(set.contains(2L));
		assertTrue(set.contains(3L));
		assertFalse(set.contains(4L));
		assertFalse(set.removeAll(new long[]{5L})); // 存在しない
	}

	@Test
	public void testClear() {
		LongSet set = new LongSet(new long[]{1L, 2L, 3L});
		set.clear();
		assertEquals(0, set.size());
		assertTrue(set.isEmpty());
	}

	@Test
	public void testClone() {
		LongSet original = new LongSet(new long[]{1L, 2L, 3L});
		LongSet cloned = original.clone();
		assertEquals(original.size(), cloned.size());
		assertTrue(cloned.contains(1L));
		assertTrue(cloned.contains(2L));
		assertTrue(cloned.contains(3L));
		// 独立性確認
		cloned.add(4L);
		assertFalse(original.contains(4L));
	}

	@Test
	public void testIterator() {
		LongSet set = new LongSet(new long[]{1L, 2L, 3L});
		PrimitiveIterator.OfLong it = set.iterator();
		List<Long> list = new ArrayList<>();
		while (it.hasNext()) {
			list.add(it.nextLong());
		}
		Collections.sort(list); // 順序不定なのでソート
		assertEquals(Arrays.asList(1L, 2L, 3L), list);
	}

	@Test
	public void testToArray() {
		LongSet set = new LongSet(new long[]{1L, 2L, 3L});
		long[] arr = set.toArray();
		Arrays.sort(arr); // 順序不定
		assertArrayEquals(new long[]{1L, 2L, 3L}, arr);
	}

	@Test
	public void testForEach() {
		LongSet set = new LongSet(new long[]{1L, 2L, 3L});
		List<Long> list = new ArrayList<>();
		set.forEach(list::add);
		Collections.sort(list);
		assertEquals(Arrays.asList(1L, 2L, 3L), list);
	}

	@Test
	public void testSize() {
		LongSet set = new LongSet();
		assertEquals(0, set.size());
		set.add(1L);
		assertEquals(1, set.size());
		set.add(1L); // 重複
		assertEquals(1, set.size());
		set.remove(1L);
		assertEquals(0, set.size());
	}

	@Test
	public void testIsEmpty() {
		LongSet set = new LongSet();
		assertTrue(set.isEmpty());
		set.add(1L);
		assertFalse(set.isEmpty());
		set.remove(1L);
		assertTrue(set.isEmpty());
	}
}