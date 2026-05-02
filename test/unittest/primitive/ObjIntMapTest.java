package primitive;


import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

public class ObjIntMapTest {

	@Test
	public void testConstructor() {
		ObjIntMap<String> map = new ObjIntMap<>(16, -1);
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
		assertEquals(-1, map.get("a"));
	}

	@Test
	public void testCopyConstructor() {
		ObjIntMap<String> original = new ObjIntMap<>(16, -1);
		original.put("b", 10);
		original.put("c", 20);

		ObjIntMap<String> copy = new ObjIntMap<>(original);
		assertEquals(2, copy.size());
		assertEquals(10, copy.get("b"));
		assertEquals(20, copy.get("c"));
		assertEquals(-1, copy.get("d"));
	}

	@Test
	public void testPutAndGet() {
		ObjIntMap<String> map = new ObjIntMap<>(16, -1);
		assertEquals(-1, map.put("b", 10)); // 新規
		assertEquals(10, map.put("b", 20)); // 更新
		assertEquals(20, map.get("b"));
		assertEquals(-1, map.get("c")); // 存在しない
	}

	@Test
	public void testContainsKey() {
		ObjIntMap<String> map = new ObjIntMap<>(16, -1);
		map.put("b", 10);
		assertTrue(map.containsKey("b"));
		assertFalse(map.containsKey("c"));
	}

	@Test
	public void testContainsValue() {
		ObjIntMap<String> map = new ObjIntMap<>(16, -1);
		map.put("b", 10);
		map.put("c", 20);
		assertTrue(map.containsValue(10));
		assertTrue(map.containsValue(20));
		assertFalse(map.containsValue(30));
	}

	@Test
	public void testSizeAndIsEmpty() {
		ObjIntMap<String> map = new ObjIntMap<>(16, -1);
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());

		map.put("b", 10);
		assertEquals(1, map.size());
		assertFalse(map.isEmpty());

		map.put("c", 20);
		assertEquals(2, map.size());

		map.remove("b");
		assertEquals(1, map.size());
	}

	@Test
	public void testRemove() {
		ObjIntMap<String> map = new ObjIntMap<>(16, -1);
		map.put("b", 10);
		map.put("c", 20);

		assertEquals(10, map.remove("b"));
		assertEquals(-1, map.remove("b")); // もうない
		assertEquals(1, map.size());

		assertFalse(map.remove("c", 10)); // 値が違う
		assertTrue(map.remove("c", 20));
		assertEquals(0, map.size());
	}

	@Test
	public void testClear() {
		ObjIntMap<String> map = new ObjIntMap<>(16, -1);
		map.put("b", 10);
		map.put("c", 20);
		assertEquals(2, map.size());

		map.clear();
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
		assertEquals(-1, map.get("b"));
	}

	@Test
	public void testKeySet() {
		ObjIntMap<String> map = new ObjIntMap<>(16, -1);
		map.put("b", 10);
		map.put("d", 30);
		map.put("c", 20);

		ArrayList<String> keys = map.keySet();
		assertEquals(3, keys.size());
		// 順序は不定なのでソートして確認
		Collections.sort(keys);
		assertArrayEquals(new String[]{"b", "c", "d"}, keys.toArray(new String[keys.size()]));
	}

	@Test
	public void testValues() {
		ObjIntMap<String> map = new ObjIntMap<>(16, -1);
		map.put("b", 10);
		map.put("d", 30);
		map.put("c", 20);

		int[] values = map.values();
		assertEquals(3, values.length);
		java.util.Arrays.sort(values);
		assertArrayEquals(new int[]{10, 20, 30}, values);
	}

	@Test
	public void testEntrySet() {
		ObjIntMap<String> map = new ObjIntMap<>(16, -1);
		map.put("b", 10);
		map.put("c", 20);

		ArrayList<ObjIntMap.Entry<String>> entries = map.entrySet();
		assertEquals(2, entries.size());
		// エントリの内容を確認
		boolean found1 = false, found2 = false;
		for (ObjIntMap.Entry<String> e : entries) {
				if (e.key() == "b" && e.value() == 10) found1 = true;
				if (e.key() == "c" && e.value() == 20) found2 = true;
		}
		assertTrue(found1 && found2);
	}

	@Test
	public void testEntryIterator() {
		ObjIntMap<String> map = new ObjIntMap<>(16, -1);
		map.put("b", 10);
		map.put("c", 20);

		ObjIntMap<String>.EntryIterator it = map.entryIterator();
		int count = 0;
		while (it.hasNext()) {
				it.next();
				count++;
		}
		assertEquals(2, count);
	}

	@Test
	public void testGetOrDefault() {
		ObjIntMap<String> map = new ObjIntMap<>(16, -1);
		map.put("b", 10);
		assertEquals(10, map.getOrDefault("b", 99));
		assertEquals(99, map.getOrDefault("c", 99));
	}

	@Test
	public void testPutIfAbsent() {
		ObjIntMap<String> map = new ObjIntMap<>(16, -1);
		assertEquals(-1, map.putIfAbsent("b", 10));
		assertEquals(10, map.putIfAbsent("b", 20)); // 既存なので無視
		assertEquals(10, map.get("b"));
	}

	@Test
	public void testReplace() {
		ObjIntMap<String> map = new ObjIntMap<>(16, -1);
		map.put("b", 10);
		assertTrue(map.replace("b", 10, 20));
		assertEquals(20, map.get("b"));

		assertFalse(map.replace("b", 10, 30)); // 古い値が違う
		assertEquals(20, map.get("b"));

		assertTrue(map.replace("b", 30)); // 値指定なし
		assertEquals(30, map.get("b"));
	}

	@Test
	public void testComputeIfAbsent() {
		ObjIntMap<String> map = new ObjIntMap<>(16, -1);
		assertEquals(11, map.computeIfAbsent("b", k -> k.length() + 10));
		assertEquals(11, map.get("b"));

		assertEquals(11, map.computeIfAbsent("b", k -> k.length() + 20)); // 既存なので無視
		assertEquals(11, map.get("b"));
	}

	@Test
	public void testComputeIfPresent() {
		ObjIntMap<String> map = new ObjIntMap<>(16, -1);
		map.put("b", 10);
		assertEquals(20, map.computeIfPresent("b", (k, v) -> v * 2));
		assertEquals(20, map.get("b"));

		assertEquals(-1, map.computeIfPresent("c", (k, v) -> v * 2)); // 存在しない
	}

	@Test
	public void testCompute() {
		ObjIntMap<String> map = new ObjIntMap<>(16, -1);
		assertEquals(11, map.compute("b", (k, v) -> k.length() + 10)); // 新規
		assertEquals(22, map.compute("b", (k, v) -> v * 2)); // 更新
		assertEquals(22, map.get("b"));
	}

	@Test
	public void testMerge() {
		ObjIntMap<String> map = new ObjIntMap<>(16, -1);
		assertEquals(10, map.merge("b", 10, (v1, v2) -> v1 + v2)); // 新規
		assertEquals(15, map.merge("b", 5, (v1, v2) -> v1 + v2)); // マージ
		assertEquals(15, map.get("b"));
	}
	
	@Test
	public void testCountUp() {
		ObjIntMap<String> map = new ObjIntMap<>(16, -1);
		assertEquals(1, map.countUp("b", 1));
		assertEquals(6, map.countUp("b", 5));
		assertEquals(6, map.get("b"));
	}

	@Test
	public void testForEach() {
		ObjIntMap<String> map = new ObjIntMap<>(16, -1);
		map.put("b", 10);
		map.put("c", 20);

		int[] sum = {0};
		map.forEach((k, v) -> sum[0] += k.length() + v);
		assertEquals(1 + 10 + 1 + 20, sum[0]);
	}

	@Test
	public void testReplaceAll() {
		ObjIntMap<String> map = new ObjIntMap<>(16, -1);
		map.put("b", 10);
		map.put("c", 20);

		map.replaceAll((k, v) -> k.length() + v);
		assertEquals(11, map.get("b"));
		assertEquals(21, map.get("c"));
	}

	@Test
	public void testClone() {
		ObjIntMap<String> map = new ObjIntMap<>(16, -1);
		map.put("b", 10);

		ObjIntMap<String> cloned = map.clone();
		assertEquals(10, cloned.get("b"));
		cloned.put("c", 20);
		assertFalse(map.containsKey("c")); // 元に影響なし
	}

	@Test
	public void testResize() {
		ObjIntMap<String> map = new ObjIntMap<>(4, -1);
		for (int i = 0; i < 10; i++) {
				map.put(Integer.toString(i), i * 10);
		}
		assertEquals(10, map.size());
		for (int i = 0; i < 10; i++) {
				assertEquals(i * 10, map.get(Integer.toString(i)));
		}
	}
}