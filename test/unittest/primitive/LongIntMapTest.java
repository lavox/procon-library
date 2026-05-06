package primitive;


import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Random;

public class LongIntMapTest {

	@Test
	public void testConstructor() {
		LongIntMap map = new LongIntMap(16, -1);
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
		assertEquals(-1, map.get(0L));
	}

	@Test
	public void testCopyConstructor() {
		LongIntMap original = new LongIntMap(16, -1);
		original.put(1L, 10);
		original.put(2L, 20);

		LongIntMap copy = new LongIntMap(original);
		assertEquals(2, copy.size());
		assertEquals(10, copy.get(1L));
		assertEquals(20, copy.get(2L));
		assertEquals(-1, copy.get(3L));
	}

	@Test
	public void testPutAndGet() {
		LongIntMap map = new LongIntMap(16, -1);
		assertEquals(-1, map.put(1L, 10)); // 新規
		assertEquals(10, map.put(1L, 20)); // 更新
		assertEquals(20, map.get(1L));
		assertEquals(-1, map.get(2L)); // 存在しない
	}

	@Test
	public void testContainsKey() {
		LongIntMap map = new LongIntMap(16, -1);
		map.put(1L, 10);
		assertTrue(map.containsKey(1L));
		assertFalse(map.containsKey(2L));
	}

	@Test
	public void testContainsValue() {
		LongIntMap map = new LongIntMap(16, -1);
		map.put(1L, 10);
		map.put(2L, 20);
		assertTrue(map.containsValue(10));
		assertTrue(map.containsValue(20));
		assertFalse(map.containsValue(30));
	}

	@Test
	public void testSizeAndIsEmpty() {
		LongIntMap map = new LongIntMap(16, -1);
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());

		map.put(1L, 10);
		assertEquals(1, map.size());
		assertFalse(map.isEmpty());

		map.put(2L, 20);
		assertEquals(2, map.size());

		map.remove(1L);
		assertEquals(1, map.size());
	}

	@Test
	public void testRemove() {
		LongIntMap map = new LongIntMap(16, -1);
		map.put(1L, 10);
		map.put(2L, 20);

		assertEquals(10, map.remove(1L));
		assertEquals(-1, map.remove(1L)); // もうない
		assertEquals(1, map.size());

		assertFalse(map.remove(2L, 10)); // 値が違う
		assertTrue(map.remove(2L, 20));
		assertEquals(0, map.size());
	}

	@Test
	public void testClear() {
		LongIntMap map = new LongIntMap(16, -1);
		map.put(1L, 10);
		map.put(2L, 20);
		assertEquals(2, map.size());

		map.clear();
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
		assertEquals(-1, map.get(1L));
	}

	@Test
	public void testKeySet() {
		LongIntMap map = new LongIntMap(16, -1);
		map.put(1L, 10);
		map.put(3L, 30);
		map.put(2L, 20);

		long[] keys = map.keySet();
		assertEquals(3, keys.length);
		// 順序は不定なのでソートして確認
		java.util.Arrays.sort(keys);
		assertArrayEquals(new long[]{1L, 2L, 3L}, keys);
	}

	@Test
	public void testValues() {
		LongIntMap map = new LongIntMap(16, -1);
		map.put(1L, 10);
		map.put(3L, 30);
		map.put(2L, 20);

		int[] values = map.values();
		assertEquals(3, values.length);
		java.util.Arrays.sort(values);
		assertArrayEquals(new int[]{10, 20, 30}, values);
	}

	@Test
	public void testEntrySet() {
		LongIntMap map = new LongIntMap(16, -1);
		map.put(1L, 10);
		map.put(2L, 20);

		LongIntMap.Entry[] entries = map.entrySet();
		assertEquals(2, entries.length);
		// エントリの内容を確認
		boolean found1 = false, found2 = false;
		for (LongIntMap.Entry e : entries) {
				if (e.key() == 1L && e.value() == 10) found1 = true;
				if (e.key() == 2L && e.value() == 20) found2 = true;
		}
		assertTrue(found1 && found2);
	}

	@Test
	public void testEntryIterator() {
		LongIntMap map = new LongIntMap(16, -1);
		map.put(1L, 10);
		map.put(2L, 20);

		LongIntMap.EntryIterator it = map.entryIterator();
		int count = 0;
		while (it.hasNext()) {
				it.next();
				count++;
		}
		assertEquals(2, count);
	}

	@Test
	public void testGetOrDefault() {
		LongIntMap map = new LongIntMap(16, -1);
		map.put(1L, 10);
		assertEquals(10, map.getOrDefault(1L, 99));
		assertEquals(99, map.getOrDefault(2L, 99));
	}

	@Test
	public void testPutIfAbsent() {
		LongIntMap map = new LongIntMap(16, -1);
		assertEquals(-1, map.putIfAbsent(1L, 10));
		assertEquals(10, map.putIfAbsent(1L, 20)); // 既存なので無視
		assertEquals(10, map.get(1L));
	}

	@Test
	public void testReplace() {
		LongIntMap map = new LongIntMap(16, -1);
		map.put(1L, 10);
		assertTrue(map.replace(1L, 10, 20));
		assertEquals(20, map.get(1L));

		assertFalse(map.replace(1L, 10, 30)); // 古い値が違う
		assertEquals(20, map.get(1L));

		assertEquals(20, map.replace(1L, 30)); // 値指定なし
		assertEquals(30, map.get(1L));
	}

	@Test
	public void testComputeIfAbsent() {
		LongIntMap map = new LongIntMap(16, -1);
		assertEquals(10, map.computeIfAbsent(1L, k -> (int)k * 10));
		assertEquals(10, map.get(1L));

		assertEquals(10, map.computeIfAbsent(1L, k -> (int)k * 20)); // 既存なので無視
		assertEquals(10, map.get(1L));
	}

	@Test
	public void testComputeIfPresent() {
		LongIntMap map = new LongIntMap(16, -1);
		map.put(1L, 10);
		assertEquals(20, map.computeIfPresent(1L, (k, v) -> v * 2));
		assertEquals(20, map.get(1L));

		assertEquals(-1, map.computeIfPresent(2L, (k, v) -> v * 2)); // 存在しない
	}

	@Test
	public void testCompute() {
		LongIntMap map = new LongIntMap(16, -1);
		assertEquals(10, map.compute(1L, (k, v) -> (int)k * 10)); // 新規
		assertEquals(20, map.compute(1L, (k, v) -> v * 2)); // 更新
		assertEquals(20, map.get(1L));
	}

	@Test
	public void testMerge() {
		LongIntMap map = new LongIntMap(16, -1);
		assertEquals(10, map.merge(1L, 10, (v1, v2) -> v1 + v2)); // 新規
		assertEquals(15, map.merge(1L, 5, (v1, v2) -> v1 + v2)); // マージ
		assertEquals(15, map.get(1L));
	}
	
	@Test
	public void testCountUp() {
		LongIntMap map = new LongIntMap(16, -1);
		assertEquals(1, map.countUp(1L, 1));
		assertEquals(6, map.countUp(1L, 5));
		assertEquals(6, map.get(1L));
	}

	@Test
	public void testForEach() {
		LongIntMap map = new LongIntMap(16, -1);
		map.put(1L, 10);
		map.put(2L, 20);

		int[] sum = {0};
		map.forEach((k, v) -> sum[0] += k + v);
		assertEquals(1 + 10 + 2 + 20, sum[0]);
	}

	@Test
	public void testReplaceAll() {
		LongIntMap map = new LongIntMap(16, -1);
		map.put(1L, 10);
		map.put(2L, 20);

		map.replaceAll((k, v) -> (int)k + v);
		assertEquals(11, map.get(1L));
		assertEquals(22, map.get(2L));
	}

	@Test
	public void testClone() {
		LongIntMap map = new LongIntMap(16, -1);
		map.put(1L, 10);

		LongIntMap cloned = map.clone();
		assertEquals(10, cloned.get(1L));
		cloned.put(2L, 20);
		assertFalse(map.containsKey(2L)); // 元に影響なし
	}

	@Test
	public void testResize() {
		LongIntMap map = new LongIntMap(4, -1);
		for (int i = 0; i < 10L; i++) {
				map.put(i, i * 10);
		}
		assertEquals(10, map.size());
		for (long i = 0L; i < 10L; i++) {
				assertEquals(i * 10L, map.get(i));
		}
	}

	@Test
	public void testRandom() {
		int DEFAULT_VALUE = -1;
		Random rnd = new Random(41);
		int[] KIND = new int[] {10, 1_000, 1_000_000};
		for (int t = 0; t < 100; t++) {
			int B = KIND[t % KIND.length];
			LongIntMap myMap = new LongIntMap(10, DEFAULT_VALUE);
			HashMap<Long, Integer> refMap = new HashMap<>(10);

			for (int i = 0; i < 100_000; i++) {
				long key = rnd.nextInt(B);
				int val = rnd.nextInt();
				int op = rnd.nextInt(3);

				if (op == 0) { // put
					myMap.put(key, val);
					refMap.put(key, val);
				} else if (op == 1) { // remove
					myMap.remove(key);
					refMap.remove(key);
				} else { // get
					assertEquals(refMap.getOrDefault(key, DEFAULT_VALUE).intValue(), myMap.get(key));
				}
				assertEquals(refMap.size(), myMap.size());
			}
		}
	}
}