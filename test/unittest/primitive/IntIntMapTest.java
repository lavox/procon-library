package primitive;


import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Random;

public class IntIntMapTest {

	@Test
	public void testConstructor() {
		IntIntMap map = new IntIntMap(16, -1);
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
		assertEquals(-1, map.get(0));
	}

	@Test
	public void testCopyConstructor() {
		IntIntMap original = new IntIntMap(16, -1);
		original.put(1, 10);
		original.put(2, 20);

		IntIntMap copy = new IntIntMap(original);
		assertEquals(2, copy.size());
		assertEquals(10, copy.get(1));
		assertEquals(20, copy.get(2));
		assertEquals(-1, copy.get(3));
	}

	@Test
	public void testPutAndGet() {
		IntIntMap map = new IntIntMap(16, -1);
		assertEquals(-1, map.put(1, 10)); // 新規
		assertEquals(10, map.put(1, 20)); // 更新
		assertEquals(20, map.get(1));
		assertEquals(-1, map.get(2)); // 存在しない
	}

	@Test
	public void testContainsKey() {
		IntIntMap map = new IntIntMap(16, -1);
		map.put(1, 10);
		assertTrue(map.containsKey(1));
		assertFalse(map.containsKey(2));
	}

	@Test
	public void testContainsValue() {
		IntIntMap map = new IntIntMap(16, -1);
		map.put(1, 10);
		map.put(2, 20);
		assertTrue(map.containsValue(10));
		assertTrue(map.containsValue(20));
		assertFalse(map.containsValue(30));
	}

	@Test
	public void testSizeAndIsEmpty() {
		IntIntMap map = new IntIntMap(16, -1);
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());

		map.put(1, 10);
		assertEquals(1, map.size());
		assertFalse(map.isEmpty());

		map.put(2, 20);
		assertEquals(2, map.size());

		map.remove(1);
		assertEquals(1, map.size());
	}

	@Test
	public void testRemove() {
		IntIntMap map = new IntIntMap(16, -1);
		map.put(1, 10);
		map.put(2, 20);

		assertEquals(10, map.remove(1));
		assertEquals(-1, map.remove(1)); // もうない
		assertEquals(1, map.size());

		assertFalse(map.remove(2, 10)); // 値が違う
		assertTrue(map.remove(2, 20));
		assertEquals(0, map.size());
	}

	@Test
	public void testClear() {
		IntIntMap map = new IntIntMap(16, -1);
		map.put(1, 10);
		map.put(2, 20);
		assertEquals(2, map.size());

		map.clear();
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
		assertEquals(-1, map.get(1));
	}

	@Test
	public void testKeySet() {
		IntIntMap map = new IntIntMap(16, -1);
		map.put(1, 10);
		map.put(3, 30);
		map.put(2, 20);

		int[] keys = map.keySet();
		assertEquals(3, keys.length);
		// 順序は不定なのでソートして確認
		java.util.Arrays.sort(keys);
		assertArrayEquals(new int[]{1, 2, 3}, keys);
	}

	@Test
	public void testValues() {
		IntIntMap map = new IntIntMap(16, -1);
		map.put(1, 10);
		map.put(3, 30);
		map.put(2, 20);

		int[] values = map.values();
		assertEquals(3, values.length);
		java.util.Arrays.sort(values);
		assertArrayEquals(new int[]{10, 20, 30}, values);
	}

	@Test
	public void testEntrySet() {
		IntIntMap map = new IntIntMap(16, -1);
		map.put(1, 10);
		map.put(2, 20);

		IntIntMap.Entry[] entries = map.entrySet();
		assertEquals(2, entries.length);
		// エントリの内容を確認
		boolean found1 = false, found2 = false;
		for (IntIntMap.Entry e : entries) {
				if (e.key() == 1 && e.value() == 10) found1 = true;
				if (e.key() == 2 && e.value() == 20) found2 = true;
		}
		assertTrue(found1 && found2);
	}

	@Test
	public void testEntryIterator() {
		IntIntMap map = new IntIntMap(16, -1);
		map.put(1, 10);
		map.put(2, 20);

		IntIntMap.EntryIterator it = map.entryIterator();
		int count = 0;
		while (it.hasNext()) {
				it.next();
				count++;
		}
		assertEquals(2, count);
	}

	@Test
	public void testGetOrDefault() {
		IntIntMap map = new IntIntMap(16, -1);
		map.put(1, 10);
		assertEquals(10, map.getOrDefault(1, 99));
		assertEquals(99, map.getOrDefault(2, 99));
	}

	@Test
	public void testPutIfAbsent() {
		IntIntMap map = new IntIntMap(16, -1);
		assertEquals(-1, map.putIfAbsent(1, 10));
		assertEquals(10, map.putIfAbsent(1, 20)); // 既存なので無視
		assertEquals(10, map.get(1));
	}

	@Test
	public void testReplace() {
		IntIntMap map = new IntIntMap(16, -1);
		map.put(1, 10);
		assertTrue(map.replace(1, 10, 20));
		assertEquals(20, map.get(1));

		assertFalse(map.replace(1, 10, 30)); // 古い値が違う
		assertEquals(20, map.get(1));

		assertEquals(20, map.replace(1, 30)); // 値指定なし
		assertEquals(30, map.get(1));
	}

	@Test
	public void testComputeIfAbsent() {
		IntIntMap map = new IntIntMap(16, -1);
		assertEquals(10, map.computeIfAbsent(1, k -> k * 10));
		assertEquals(10, map.get(1));

		assertEquals(10, map.computeIfAbsent(1, k -> k * 20)); // 既存なので無視
		assertEquals(10, map.get(1));
	}

	@Test
	public void testComputeIfPresent() {
		IntIntMap map = new IntIntMap(16, -1);
		map.put(1, 10);
		assertEquals(20, map.computeIfPresent(1, (k, v) -> v * 2));
		assertEquals(20, map.get(1));

		assertEquals(-1, map.computeIfPresent(2, (k, v) -> v * 2)); // 存在しない
	}

	@Test
	public void testCompute() {
		IntIntMap map = new IntIntMap(16, -1);
		assertEquals(10, map.compute(1, (k, v) -> k * 10)); // 新規
		assertEquals(20, map.compute(1, (k, v) -> v * 2)); // 更新
		assertEquals(20, map.get(1));
	}

	@Test
	public void testMerge() {
		IntIntMap map = new IntIntMap(16, -1);
		assertEquals(10, map.merge(1, 10, (v1, v2) -> v1 + v2)); // 新規
		assertEquals(15, map.merge(1, 5, (v1, v2) -> v1 + v2)); // マージ
		assertEquals(15, map.get(1));
	}

	@Test
	public void testCountUp() {
		IntIntMap map = new IntIntMap(16, -1);
		assertEquals(1, map.countUp(1, 1));
		assertEquals(6, map.countUp(1, 5));
		assertEquals(6, map.get(1));
	}

	@Test
	public void testForEach() {
		IntIntMap map = new IntIntMap(16, -1);
		map.put(1, 10);
		map.put(2, 20);

		int[] sum = {0};
		map.forEach((k, v) -> sum[0] += k + v);
		assertEquals(1 + 10 + 2 + 20, sum[0]);
	}

	@Test
	public void testReplaceAll() {
		IntIntMap map = new IntIntMap(16, -1);
		map.put(1, 10);
		map.put(2, 20);

		map.replaceAll((k, v) -> k + v);
		assertEquals(11, map.get(1));
		assertEquals(22, map.get(2));
	}

	@Test
	public void testClone() {
		IntIntMap map = new IntIntMap(16, -1);
		map.put(1, 10);

		IntIntMap cloned = map.clone();
		assertEquals(10, cloned.get(1));
		cloned.put(2, 20);
		assertFalse(map.containsKey(2)); // 元に影響なし
	}

	@Test
	public void testResize() {
		IntIntMap map = new IntIntMap(4, -1);
		for (int i = 0; i < 10; i++) {
				map.put(i, i * 10);
		}
		assertEquals(10, map.size());
		for (int i = 0; i < 10; i++) {
				assertEquals(i * 10, map.get(i));
		}
	}

	@Test
	public void testRandom() {
		int DEFAULT_VALUE = -1;
		Random rnd = new Random(41);
		int[] KIND = new int[] {10, 1_000, 1_000_000};
		for (int t = 0; t < 100; t++) {
			int B = KIND[t % KIND.length];
			IntIntMap myMap = new IntIntMap(10, DEFAULT_VALUE);
			HashMap<Integer, Integer> refMap = new HashMap<>(10);

			for (int i = 0; i < 100_000; i++) {
				int key = rnd.nextInt(B);
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