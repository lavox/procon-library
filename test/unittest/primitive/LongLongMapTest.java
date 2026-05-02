package primitive;


import org.junit.Test;
import static org.junit.Assert.*;

public class LongLongMapTest {

	@Test
	public void testConstructor() {
		LongLongMap map = new LongLongMap(16, -1L);
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
		assertEquals(-1L, map.get(0L));
	}

	@Test
	public void testCopyConstructor() {
		LongLongMap original = new LongLongMap(16, -1L);
		original.put(1L, 10L);
		original.put(2L, 20L);

		LongLongMap copy = new LongLongMap(original);
		assertEquals(2, copy.size());
		assertEquals(10L, copy.get(1L));
		assertEquals(20L, copy.get(2L));
		assertEquals(-1L, copy.get(3L));
	}

	@Test
	public void testPutAndGet() {
		LongLongMap map = new LongLongMap(16, -1L);
		assertEquals(-1L, map.put(1L, 10L)); // 新規
		assertEquals(10L, map.put(1L, 20L)); // 更新
		assertEquals(20L, map.get(1L));
		assertEquals(-1L, map.get(2L)); // 存在しない
	}

	@Test
	public void testContainsKey() {
		LongLongMap map = new LongLongMap(16, -1L);
		map.put(1L, 10L);
		assertTrue(map.containsKey(1L));
		assertFalse(map.containsKey(2L));
	}

	@Test
	public void testContainsValue() {
		LongLongMap map = new LongLongMap(16, -1L);
		map.put(1L, 10L);
		map.put(2L, 20L);
		assertTrue(map.containsValue(10L));
		assertTrue(map.containsValue(20L));
		assertFalse(map.containsValue(30L));
	}

	@Test
	public void testSizeAndIsEmpty() {
		LongLongMap map = new LongLongMap(16, -1L);
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());

		map.put(1L, 10L);
		assertEquals(1, map.size());
		assertFalse(map.isEmpty());

		map.put(2L, 20L);
		assertEquals(2, map.size());

		map.remove(1L);
		assertEquals(1, map.size());
	}

	@Test
	public void testRemove() {
		LongLongMap map = new LongLongMap(16, -1L);
		map.put(1L, 10L);
		map.put(2L, 20L);

		assertEquals(10L, map.remove(1L));
		assertEquals(-1L, map.remove(1L)); // もうない
		assertEquals(1, map.size());

		assertFalse(map.remove(2L, 10L)); // 値が違う
		assertTrue(map.remove(2L, 20L));
		assertEquals(0, map.size());
	}

	@Test
	public void testClear() {
		LongLongMap map = new LongLongMap(16, -1L);
		map.put(1L, 10L);
		map.put(2L, 20L);
		assertEquals(2, map.size());

		map.clear();
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
		assertEquals(-1L, map.get(1L));
	}

	@Test
	public void testKeySet() {
		LongLongMap map = new LongLongMap(16, -1L);
		map.put(1L, 10L);
		map.put(3L, 30L);
		map.put(2L, 20L);

		long[] keys = map.keySet();
		assertEquals(3, keys.length);
		// 順序は不定なのでソートして確認
		java.util.Arrays.sort(keys);
		assertArrayEquals(new long[]{1L, 2L, 3L}, keys);
	}

	@Test
	public void testValues() {
		LongLongMap map = new LongLongMap(16, -1L);
		map.put(1L, 10L);
		map.put(3L, 30L);
		map.put(2L, 20L);

		long[] values = map.values();
		assertEquals(3, values.length);
		java.util.Arrays.sort(values);
		assertArrayEquals(new long[]{10L, 20L, 30L}, values);
	}

	@Test
	public void testEntrySet() {
		LongLongMap map = new LongLongMap(16, -1L);
		map.put(1L, 10L);
		map.put(2L, 20L);

		LongLongMap.Entry[] entries = map.entrySet();
		assertEquals(2, entries.length);
		// エントリの内容を確認
		boolean found1 = false, found2 = false;
		for (LongLongMap.Entry e : entries) {
				if (e.key() == 1L && e.value() == 10L) found1 = true;
				if (e.key() == 2L && e.value() == 20L) found2 = true;
		}
		assertTrue(found1 && found2);
	}

	@Test
	public void testEntryIterator() {
		LongLongMap map = new LongLongMap(16, -1L);
		map.put(1L, 10L);
		map.put(2L, 20L);

		LongLongMap.EntryIterator it = map.entryIterator();
		int count = 0;
		while (it.hasNext()) {
				it.next();
				count++;
		}
		assertEquals(2, count);
	}

	@Test
	public void testGetOrDefault() {
		LongLongMap map = new LongLongMap(16, -1L);
		map.put(1L, 10L);
		assertEquals(10L, map.getOrDefault(1L, 99L));
		assertEquals(99L, map.getOrDefault(2L, 99L));
	}

	@Test
	public void testPutIfAbsent() {
		LongLongMap map = new LongLongMap(16, -1L);
		assertEquals(-1L, map.putIfAbsent(1L, 10L));
		assertEquals(10L, map.putIfAbsent(1L, 20L)); // 既存なので無視
		assertEquals(10L, map.get(1L));
	}

	@Test
	public void testReplace() {
		LongLongMap map = new LongLongMap(16, -1L);
		map.put(1L, 10L);
		assertTrue(map.replace(1L, 10L, 20L));
		assertEquals(20L, map.get(1L));

		assertFalse(map.replace(1L, 10L, 30L)); // 古い値が違う
		assertEquals(20L, map.get(1L));

		assertTrue(map.replace(1L, 30L)); // 値指定なし
		assertEquals(30L, map.get(1L));
	}

	@Test
	public void testComputeIfAbsent() {
		LongLongMap map = new LongLongMap(16, -1L);
		assertEquals(10L, map.computeIfAbsent(1L, k -> k * 10L));
		assertEquals(10L, map.get(1L));

		assertEquals(10L, map.computeIfAbsent(1L, k -> k * 20L)); // 既存なので無視
		assertEquals(10L, map.get(1L));
	}

	@Test
	public void testComputeIfPresent() {
		LongLongMap map = new LongLongMap(16, -1L);
		map.put(1L, 10L);
		assertEquals(20L, map.computeIfPresent(1L, (k, v) -> v * 2L));
		assertEquals(20L, map.get(1L));

		assertEquals(-1L, map.computeIfPresent(2L, (k, v) -> v * 2L)); // 存在しない
	}

	@Test
	public void testCompute() {
		LongLongMap map = new LongLongMap(16, -1L);
		assertEquals(10L, map.compute(1L, (k, v) -> k * 10L)); // 新規
		assertEquals(20L, map.compute(1L, (k, v) -> v * 2L)); // 更新
		assertEquals(20L, map.get(1L));
	}

	@Test
	public void testMerge() {
		LongLongMap map = new LongLongMap(16, -1L);
		assertEquals(10L, map.merge(1L, 10L, (v1, v2) -> v1 + v2)); // 新規
		assertEquals(15L, map.merge(1L, 5L, (v1, v2) -> v1 + v2)); // マージ
		assertEquals(15L, map.get(1L));
	}
	
	@Test
	public void testCountUp() {
		LongLongMap map = new LongLongMap(16, -1L);
		assertEquals(1L, map.countUp(1L, 1L));
		assertEquals(6L, map.countUp(1L, 5L));
		assertEquals(6L, map.get(1L));
	}

	@Test
	public void testForEach() {
		LongLongMap map = new LongLongMap(16, -1L);
		map.put(1L, 10L);
		map.put(2L, 20L);

		long[] sum = {0L};
		map.forEach((k, v) -> sum[0] += k + v);
		assertEquals(1L + 10L + 2L + 20L, sum[0]);
	}

	@Test
	public void testReplaceAll() {
		LongLongMap map = new LongLongMap(16, -1L);
		map.put(1L, 10L);
		map.put(2L, 20L);

		map.replaceAll((k, v) -> k + v);
		assertEquals(11L, map.get(1L));
		assertEquals(22L, map.get(2L));
	}

	@Test
	public void testClone() {
		LongLongMap map = new LongLongMap(16, -1L);
		map.put(1L, 10L);

		LongLongMap cloned = map.clone();
		assertEquals(10L, cloned.get(1L));
		cloned.put(2L, 20L);
		assertFalse(map.containsKey(2L)); // 元に影響なし
	}

	@Test
	public void testResize() {
		LongLongMap map = new LongLongMap(4, -1L);
		for (long i = 0L; i < 10L; i++) {
				map.put(i, i * 10L);
		}
		assertEquals(10, map.size());
		for (long i = 0L; i < 10L; i++) {
				assertEquals(i * 10L, map.get(i));
		}
	}
}