package primitive;


import org.junit.Test;
import static org.junit.Assert.*;

public class IntLongMapTest {

	@Test
	public void testConstructor() {
		IntLongMap map = new IntLongMap(16, -1);
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
		assertEquals(-1L, map.get(0));
	}

	@Test
	public void testCopyConstructor() {
		IntLongMap original = new IntLongMap(16, -1);
		original.put(1, 10L);
		original.put(2, 20L);

		IntLongMap copy = new IntLongMap(original);
		assertEquals(2L, copy.size());
		assertEquals(10L, copy.get(1));
		assertEquals(20L, copy.get(2));
		assertEquals(-1L, copy.get(3));
	}

	@Test
	public void testPutAndGet() {
		IntLongMap map = new IntLongMap(16, -1);
		assertEquals(-1L, map.put(1, 10L)); // 新規
		assertEquals(10L, map.put(1, 20L)); // 更新
		assertEquals(20L, map.get(1));
		assertEquals(-1L, map.get(2)); // 存在しない
	}

	@Test
	public void testContainsKey() {
		IntLongMap map = new IntLongMap(16, -1);
		map.put(1, 10);
		assertTrue(map.containsKey(1));
		assertFalse(map.containsKey(2));
	}

	@Test
	public void testContainsValue() {
		IntLongMap map = new IntLongMap(16, -1);
		map.put(1, 10L);
		map.put(2, 20L);
		assertTrue(map.containsValue(10L));
		assertTrue(map.containsValue(20L));
		assertFalse(map.containsValue(30L));
	}

	@Test
	public void testSizeAndIsEmpty() {
		IntLongMap map = new IntLongMap(16, -1);
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());

		map.put(1, 10L);
		assertEquals(1, map.size());
		assertFalse(map.isEmpty());

		map.put(2, 20L);
		assertEquals(2, map.size());

		map.remove(1);
		assertEquals(1, map.size());
	}

	@Test
	public void testRemove() {
		IntLongMap map = new IntLongMap(16, -1);
		map.put(1, 10L);
		map.put(2, 20L);

		assertEquals(10L, map.remove(1));
		assertEquals(-1L, map.remove(1)); // もうない
		assertEquals(1, map.size());

		assertFalse(map.remove(2, 10L)); // 値が違う
		assertTrue(map.remove(2, 20L));
		assertEquals(0, map.size());
	}

	@Test
	public void testClear() {
		IntLongMap map = new IntLongMap(16, -1);
		map.put(1, 10L);
		map.put(2, 20L);
		assertEquals(2, map.size());

		map.clear();
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
		assertEquals(-1, map.get(1));
	}

	@Test
	public void testKeySet() {
		IntLongMap map = new IntLongMap(16, -1);
		map.put(1, 10L);
		map.put(3, 30L);
		map.put(2, 20L);

		int[] keys = map.keySet();
		assertEquals(3, keys.length);
		// 順序は不定なのでソートして確認
		java.util.Arrays.sort(keys);
		assertArrayEquals(new int[]{1, 2, 3}, keys);
	}

	@Test
	public void testValues() {
		IntLongMap map = new IntLongMap(16, -1);
		map.put(1, 10L);
		map.put(3, 30L);
		map.put(2, 20L);

		long[] values = map.values();
		assertEquals(3, values.length);
		java.util.Arrays.sort(values);
		assertArrayEquals(new long[]{10L, 20L, 30L}, values);
	}

	@Test
	public void testEntrySet() {
		IntLongMap map = new IntLongMap(16, -1);
		map.put(1, 10L);
		map.put(2, 20L);

		IntLongMap.Entry[] entries = map.entrySet();
		assertEquals(2, entries.length);
		// エントリの内容を確認
		boolean found1 = false, found2 = false;
		for (IntLongMap.Entry e : entries) {
				if (e.key() == 1 && e.value() == 10L) found1 = true;
				if (e.key() == 2 && e.value() == 20L) found2 = true;
		}
		assertTrue(found1 && found2);
	}

	@Test
	public void testEntryIterator() {
		IntLongMap map = new IntLongMap(16, -1);
		map.put(1, 10L);
		map.put(2, 20L);

		IntLongMap.EntryIterator it = map.entryIterator();
		int count = 0;
		while (it.hasNext()) {
				it.next();
				count++;
		}
		assertEquals(2, count);
	}

	@Test
	public void testGetOrDefault() {
		IntLongMap map = new IntLongMap(16, -1);
		map.put(1, 10L);
		assertEquals(10L, map.getOrDefault(1, 99L));
		assertEquals(99L, map.getOrDefault(2, 99L));
	}

	@Test
	public void testPutIfAbsent() {
		IntLongMap map = new IntLongMap(16, -1);
		assertEquals(-1L, map.putIfAbsent(1, 10L));
		assertEquals(10L, map.putIfAbsent(1, 20L)); // 既存なので無視
		assertEquals(10L, map.get(1));
	}

	@Test
	public void testReplace() {
		IntLongMap map = new IntLongMap(16, -1);
		map.put(1, 10);
		assertTrue(map.replace(1, 10L, 20L));
		assertEquals(20L, map.get(1));

		assertFalse(map.replace(1, 10, 30)); // 古い値が違う
		assertEquals(20L, map.get(1));

		assertTrue(map.replace(1, 30)); // 値指定なし
		assertEquals(30L, map.get(1));
	}

	@Test
	public void testComputeIfAbsent() {
		IntLongMap map = new IntLongMap(16, -1);
		assertEquals(10L, map.computeIfAbsent(1, k -> k * 10));
		assertEquals(10L, map.get(1));

		assertEquals(10L, map.computeIfAbsent(1, k -> k * 20)); // 既存なので無視
		assertEquals(10L, map.get(1));
	}

	@Test
	public void testComputeIfPresent() {
		IntLongMap map = new IntLongMap(16, -1);
		map.put(1, 10L);
		assertEquals(20L, map.computeIfPresent(1, (k, v) -> v * 2));
		assertEquals(20L, map.get(1));

		assertEquals(-1L, map.computeIfPresent(2, (k, v) -> v * 2)); // 存在しない
	}

	@Test
	public void testCompute() {
		IntLongMap map = new IntLongMap(16, -1);
		assertEquals(10L, map.compute(1, (k, v) -> k * 10)); // 新規
		assertEquals(20L, map.compute(1, (k, v) -> v * 2)); // 更新
		assertEquals(20L, map.get(1));
	}

	@Test
	public void testMerge() {
		IntLongMap map = new IntLongMap(16, -1);
		assertEquals(10L, map.merge(1, 10, (v1, v2) -> v1 + v2)); // 新規
		assertEquals(15L, map.merge(1, 5, (v1, v2) -> v1 + v2)); // マージ
		assertEquals(15L, map.get(1));
	}

	@Test
	public void testCountUp() {
		IntLongMap map = new IntLongMap(16, -1);
		assertEquals(1L, map.countUp(1, 1L));
		assertEquals(6L, map.countUp(1, 5L));
		assertEquals(6L, map.get(1));
	}

	@Test
	public void testForEach() {
		IntLongMap map = new IntLongMap(16, -1);
		map.put(1, 10L);
		map.put(2, 20L);

		long[] sum = {0};
		map.forEach((k, v) -> sum[0] += k + v);
		assertEquals(1 + 10 + 2 + 20, sum[0]);
	}

	@Test
	public void testReplaceAll() {
		IntLongMap map = new IntLongMap(16, -1);
		map.put(1, 10L);
		map.put(2, 20L);

		map.replaceAll((k, v) -> k + v);
		assertEquals(11L, map.get(1));
		assertEquals(22L, map.get(2));
	}

	@Test
	public void testClone() {
		IntLongMap map = new IntLongMap(16, -1);
		map.put(1, 10L);

		IntLongMap cloned = map.clone();
		assertEquals(10L, cloned.get(1));
		cloned.put(2, 20L);
		assertFalse(map.containsKey(2)); // 元に影響なし
	}

	@Test
	public void testResize() {
		IntLongMap map = new IntLongMap(4, -1);
		for (int i = 0; i < 10; i++) {
				map.put(i, i * 10);
		}
		assertEquals(10, map.size());
		for (int i = 0; i < 10; i++) {
				assertEquals(i * 10, map.get(i));
		}
	}
}