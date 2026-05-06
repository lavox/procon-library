package primitive;


import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class ObjLongMapTest {

	@Test
	public void testConstructor() {
		ObjLongMap<String> map = new ObjLongMap<>(16, -1L);
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
		assertEquals(-1, map.get("a"));
	}

	@Test
	public void testCopyConstructor() {
		ObjLongMap<String> original = new ObjLongMap<>(16, -1L);
		original.put("b", 10L);
		original.put("c", 20L);

		ObjLongMap<String> copy = new ObjLongMap<>(original);
		assertEquals(2, copy.size());
		assertEquals(10L, copy.get("b"));
		assertEquals(20L, copy.get("c"));
		assertEquals(-1L, copy.get("d"));
	}

	@Test
	public void testPutAndGet() {
		ObjLongMap<String> map = new ObjLongMap<>(16, -1L);
		assertEquals(-1L, map.put("b", 10L)); // 新規
		assertEquals(10L, map.put("b", 20L)); // 更新
		assertEquals(20L, map.get("b"));
		assertEquals(-1L, map.get("c")); // 存在しない
	}

	@Test
	public void testContainsKey() {
		ObjLongMap<String> map = new ObjLongMap<>(16, -1L);
		map.put("b", 10L);
		assertTrue(map.containsKey("b"));
		assertFalse(map.containsKey("c"));
	}

	@Test
	public void testContainsValue() {
		ObjLongMap<String> map = new ObjLongMap<>(16, -1L);
		map.put("b", 10L);
		map.put("c", 20L);
		assertTrue(map.containsValue(10L));
		assertTrue(map.containsValue(20L));
		assertFalse(map.containsValue(30L));
	}

	@Test
	public void testSizeAndIsEmpty() {
		ObjLongMap<String> map = new ObjLongMap<>(16, -1L);
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());

		map.put("b", 10L);
		assertEquals(1, map.size());
		assertFalse(map.isEmpty());

		map.put("c", 20L);
		assertEquals(2, map.size());

		map.remove("b");
		assertEquals(1, map.size());
	}

	@Test
	public void testRemove() {
		ObjLongMap<String> map = new ObjLongMap<>(16, -1L);
		map.put("b", 10L);
		map.put("c", 20L);

		assertEquals(10L, map.remove("b"));
		assertEquals(-1L, map.remove("b")); // もうない
		assertEquals(1, map.size());

		assertFalse(map.remove("c", 10L)); // 値が違う
		assertTrue(map.remove("c", 20L));
		assertEquals(0, map.size());
	}

	@Test
	public void testClear() {
		ObjLongMap<String> map = new ObjLongMap<>(16, -1L);
		map.put("b", 10L);
		map.put("c", 20L);
		assertEquals(2, map.size());

		map.clear();
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
		assertEquals(-1L, map.get("b"));
	}

	@Test
	public void testKeySet() {
		ObjLongMap<String> map = new ObjLongMap<>(16, -1L);
		map.put("b", 10L);
		map.put("d", 30L);
		map.put("c", 20L);

		ArrayList<String> keys = map.keySet();
		assertEquals(3, keys.size());
		// 順序は不定なのでソートして確認
		Collections.sort(keys);
		assertArrayEquals(new String[]{"b", "c", "d"}, keys.toArray(new String[keys.size()]));
	}

	@Test
	public void testValues() {
		ObjLongMap<String> map = new ObjLongMap<>(16, -1L);
		map.put("b", 10L);
		map.put("d", 30L);
		map.put("c", 20L);

		long[] values = map.values();
		assertEquals(3, values.length);
		java.util.Arrays.sort(values);
		assertArrayEquals(new long[]{10, 20, 30}, values);
	}

	@Test
	public void testEntrySet() {
		ObjLongMap<String> map = new ObjLongMap<>(16, -1L);
		map.put("b", 10L);
		map.put("c", 20L);

		ArrayList<ObjLongMap.Entry<String>> entries = map.entrySet();
		assertEquals(2, entries.size());
		// エントリの内容を確認
		boolean found1 = false, found2 = false;
		for (ObjLongMap.Entry<String> e : entries) {
				if (e.key() == "b" && e.value() == 10L) found1 = true;
				if (e.key() == "c" && e.value() == 20L) found2 = true;
		}
		assertTrue(found1 && found2);
	}

	@Test
	public void testEntryIterator() {
		ObjLongMap<String> map = new ObjLongMap<>(16, -1L);
		map.put("b", 10L);
		map.put("c", 20L);

		ObjLongMap<String>.EntryIterator it = map.entryIterator();
		int count = 0;
		while (it.hasNext()) {
				it.next();
				count++;
		}
		assertEquals(2, count);
	}

	@Test
	public void testGetOrDefault() {
		ObjLongMap<String> map = new ObjLongMap<>(16, -1L);
		map.put("b", 10L);
		assertEquals(10L, map.getOrDefault("b", 99L));
		assertEquals(99L, map.getOrDefault("c", 99L));
	}

	@Test
	public void testPutIfAbsent() {
		ObjLongMap<String> map = new ObjLongMap<>(16, -1L);
		assertEquals(-1L, map.putIfAbsent("b", 10L));
		assertEquals(10L, map.putIfAbsent("b", 20L)); // 既存なので無視
		assertEquals(10L, map.get("b"));
	}

	@Test
	public void testReplace() {
		ObjLongMap<String> map = new ObjLongMap<>(16, -1L);
		map.put("b", 10);
		assertTrue(map.replace("b", 10L, 20L));
		assertEquals(20L, map.get("b"));

		assertFalse(map.replace("b", 10L, 30L)); // 古い値が違う
		assertEquals(20L, map.get("b"));

		assertEquals(20L, map.replace("b", 30L)); // 値指定なし
		assertEquals(30L, map.get("b"));
	}

	@Test
	public void testComputeIfAbsent() {
		ObjLongMap<String> map = new ObjLongMap<>(16, -1L);
		assertEquals(11L, map.computeIfAbsent("b", k -> k.length() + 10));
		assertEquals(11L, map.get("b"));

		assertEquals(11L, map.computeIfAbsent("b", k -> k.length() + 20)); // 既存なので無視
		assertEquals(11L, map.get("b"));
	}

	@Test
	public void testComputeIfPresent() {
		ObjLongMap<String> map = new ObjLongMap<>(16, -1L);
		map.put("b", 10L);
		assertEquals(20L, map.computeIfPresent("b", (k, v) -> v * 2));
		assertEquals(20L, map.get("b"));

		assertEquals(-1L, map.computeIfPresent("c", (k, v) -> v * 2)); // 存在しない
	}

	@Test
	public void testCompute() {
		ObjLongMap<String> map = new ObjLongMap<>(16, -1L);
		assertEquals(11L, map.compute("b", (k, v) -> k.length() + 10)); // 新規
		assertEquals(22L, map.compute("b", (k, v) -> v * 2)); // 更新
		assertEquals(22L, map.get("b"));
	}

	@Test
	public void testMerge() {
		ObjLongMap<String> map = new ObjLongMap<>(16, -1L);
		assertEquals(10L, map.merge("b", 10L, (v1, v2) -> v1 + v2)); // 新規
		assertEquals(15L, map.merge("b", 5L, (v1, v2) -> v1 + v2)); // マージ
		assertEquals(15L, map.get("b"));
	}
	
	@Test
	public void testCountUp() {
		ObjLongMap<String> map = new ObjLongMap<>(16, -1L);
		assertEquals(1L, map.countUp("b", 1L));
		assertEquals(6L, map.countUp("b", 5L));
		assertEquals(6L, map.get("b"));
	}

	@Test
	public void testForEach() {
		ObjLongMap<String> map = new ObjLongMap<>(16, -1L);
		map.put("b", 10L);
		map.put("c", 20L);

		int[] sum = {0};
		map.forEach((k, v) -> sum[0] += k.length() + v);
		assertEquals(1 + 10 + 1 + 20, sum[0]);
	}

	@Test
	public void testReplaceAll() {
		ObjLongMap<String> map = new ObjLongMap<>(16, -1L);
		map.put("b", 10L);
		map.put("c", 20L);

		map.replaceAll((k, v) -> k.length() + v);
		assertEquals(11L, map.get("b"));
		assertEquals(21L, map.get("c"));
	}

	@Test
	public void testClone() {
		ObjLongMap<String> map = new ObjLongMap<>(16, -1L);
		map.put("b", 10L);

		ObjLongMap<String> cloned = map.clone();
		assertEquals(10L, cloned.get("b"));
		cloned.put("c", 20L);
		assertFalse(map.containsKey("c")); // 元に影響なし
	}

	@Test
	public void testResize() {
		ObjLongMap<String> map = new ObjLongMap<>(4, -1);
		for (int i = 0; i < 10; i++) {
				map.put(Integer.toString(i), i * 10);
		}
		assertEquals(10, map.size());
		for (int i = 0; i < 10; i++) {
				assertEquals(i * 10, map.get(Integer.toString(i)));
		}
	}

	@Test
	public void testRandom() {
		long DEFAULT_VALUE = -1;
		Random rnd = new Random(41);
		int[] KIND = new int[] {10, 1_000, 1_000_000};
		for (int t = 0; t < 100; t++) {
			int B = KIND[t % KIND.length];
			ObjLongMap<String> myMap = new ObjLongMap<>(10, DEFAULT_VALUE);
			HashMap<String, Long> refMap = new HashMap<>(10);

			for (int i = 0; i < 100_000; i++) {
				int k = rnd.nextInt(B);
				String key = k == 0 ? null : Integer.toString(k);
				long val = rnd.nextLong();
				int op = rnd.nextInt(3);

				if (op == 0) { // put
					myMap.put(key, val);
					refMap.put(key, val);
				} else if (op == 1) { // remove
					myMap.remove(key);
					refMap.remove(key);
				} else { // get
					assertEquals(refMap.getOrDefault(key, DEFAULT_VALUE).longValue(), myMap.get(key));
				}
				assertEquals(refMap.size(), myMap.size());
			}
		}
	}
}