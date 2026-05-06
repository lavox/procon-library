package primitive;


import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class IntObjMapTest {

	@Test
	public void testConstructor() {
		IntObjMap<String> map = new IntObjMap<>(16);
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
		assertNull(map.get(0));
	}

	@Test
	public void testCopyConstructor() {
		IntObjMap<String> original = new IntObjMap<>(16);
		original.put(1, "a");
		original.put(2, "b");

		IntObjMap<String> copy = new IntObjMap<>(original);
		assertEquals(2, copy.size());
		assertEquals("a", copy.get(1));
		assertEquals("b", copy.get(2));
		assertNull(copy.get(3));
	}

	@Test
	public void testPutAndGet() {
		IntObjMap<String> map = new IntObjMap<>(16);
		assertNull(map.put(1, "x")); // 新規
		assertEquals("x", map.put(1, "y")); // 更新
		assertEquals("y", map.get(1));
		assertNull(map.get(2)); // 存在しない
	}

	@Test
	public void testContainsKey() {
		IntObjMap<String> map = new IntObjMap<>(16);
		map.put(1, "a");
		assertTrue(map.containsKey(1));
		assertFalse(map.containsKey(2));
	}

	@Test
	public void testContainsValue() {
		IntObjMap<String> map = new IntObjMap<>(16);
		map.put(1, "a");
		map.put(2, "b");
		assertTrue(map.containsValue("a"));
		assertTrue(map.containsValue("b"));
		assertFalse(map.containsValue("c"));
	}

	@Test
	public void testSizeAndIsEmpty() {
		IntObjMap<String> map = new IntObjMap<>(16);
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());

		map.put(1, "x");
		assertEquals(1, map.size());
		assertFalse(map.isEmpty());

		map.put(2, "y");
		assertEquals(2, map.size());

		map.remove(1);
		assertEquals(1, map.size());
	}

	@Test
	public void testRemove() {
		IntObjMap<String> map = new IntObjMap<>(16);
		map.put(1, "a");
		map.put(2, "b");

		assertEquals("a", map.remove(1));
		assertNull(map.remove(1)); // もうない
		assertEquals(1, map.size());

		assertFalse(map.remove(2, "x")); // 値が違う
		assertTrue(map.remove(2, "b"));
		assertEquals(0, map.size());
	}

	@Test
	public void testClear() {
		IntObjMap<String> map = new IntObjMap<>(16);
		map.put(1, "a");
		map.put(2, "b");
		assertEquals(2, map.size());

		map.clear();
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
		assertNull(map.get(1));
	}

	@Test
	public void testKeySet() {
		IntObjMap<String> map = new IntObjMap<>(16);
		map.put(1, "a");
		map.put(3, "c");
		map.put(2, "b");

		int[] keys = map.keySet();
		assertEquals(3, keys.length);
		// 順序は不定なのでソートして確認
		java.util.Arrays.sort(keys);
		assertArrayEquals(new int[]{1, 2, 3}, keys);
	}

	@Test
	public void testValues() {
		IntObjMap<String> map = new IntObjMap<>(16);
		map.put(1, "a");
		map.put(3, "c");
		map.put(2, "b");

		ArrayList<String> values = map.values();
		assertEquals(3, values.size());
		Collections.sort(values);
		assertArrayEquals(new String[]{"a", "b", "c"}, values.toArray(new String[values.size()]));
	}

	@Test
	public void testEntrySet() {
		IntObjMap<String> map = new IntObjMap<>(16);
		map.put(1, "a");
		map.put(2, "b");

		ArrayList<IntObjMap.Entry<String>> entries = map.entrySet();
		assertEquals(2, entries.size());
		// エントリの内容を確認
		boolean found1 = false, found2 = false;
		for (IntObjMap.Entry<String> e : entries) {
			if (e.key() == 1 && "a".equals(e.value())) found1 = true;
			if (e.key() == 2 && "b".equals(e.value())) found2 = true;
		}
		assertTrue(found1 && found2);
	}

	@Test
	public void testEntryIterator() {
		IntObjMap<String> map = new IntObjMap<>(16);
		map.put(1, "a");
		map.put(2, "b");

		IntObjMap<String>.EntryIterator it = map.entryIterator();
		int count = 0;
		while (it.hasNext()) {
			it.next();
			count++;
		}
		assertEquals(2, count);
	}

	@Test
	public void testGetOrDefault() {
		IntObjMap<String> map = new IntObjMap<>(16);
		map.put(1, "x");
		assertEquals("x", map.getOrDefault(1, "default"));
		assertEquals("default", map.getOrDefault(2, "default"));
	}

	@Test
	public void testPutIfAbsent() {
		IntObjMap<String> map = new IntObjMap<>(16);
		assertNull(map.putIfAbsent(1, "a"));
		assertEquals("a", map.putIfAbsent(1, "b")); // 既存なので無視
		assertEquals("a", map.get(1));
	}

	@Test
	public void testReplace() {
		IntObjMap<String> map = new IntObjMap<>(16);
		map.put(1, "a");
		assertTrue(map.replace(1, "a", "b"));
		assertEquals("b", map.get(1));

		assertFalse(map.replace(1, "a", "c")); // 古い値が違う
		assertEquals("b", map.get(1));

		assertEquals("b", map.replace(1, "x")); // 値指定なし
		assertEquals("x", map.get(1));
	}

	@Test
	public void testComputeIfAbsent() {
		IntObjMap<String> map = new IntObjMap<>(16);
		assertEquals("1", map.computeIfAbsent(1, k -> String.valueOf(k)));
		assertEquals("1", map.get(1));

		assertEquals("1", map.computeIfAbsent(1, k -> String.valueOf(k * 2))); // 既存なので無視
		assertEquals("1", map.get(1));
	}

	@Test
	public void testComputeIfPresent() {
		IntObjMap<String> map = new IntObjMap<>(16);
		map.put(1, "a");
		assertEquals("aa", map.computeIfPresent(1, (k, v) -> v + v));
		assertEquals("aa", map.get(1));

		assertNull(map.computeIfPresent(1, (k, v) -> null));
		assertEquals(null, map.get(1));
		assertFalse(map.containsKey(1));

		assertNull(map.computeIfPresent(2, (k, v) -> v + v)); // 存在しない
	}

	@Test
	public void testCompute() {
		IntObjMap<String> map = new IntObjMap<>(16);
		assertEquals("1", map.compute(1, (k, v) -> String.valueOf(k))); // 新規
		assertEquals("11", map.compute(1, (k, v) -> v + v)); // 更新
		assertEquals("11", map.get(1));
		assertNull(map.compute(1, (k, v) -> null)); // 削除
		assertEquals(null, map.get(1));
		assertFalse(map.containsKey(1));
	}

	@Test
	public void testMerge() {
		IntObjMap<String> map = new IntObjMap<>(16);
		assertEquals("a", map.merge(1, "a", (v1, v2) -> v1 + v2)); // 新規
		assertEquals("ab", map.merge(1, "b", (v1, v2) -> v1 + v2)); // マージ
		assertEquals("ab", map.get(1));
	}

	@Test
	public void testForEach() {
		IntObjMap<String> map = new IntObjMap<>(16);
		map.put(1, "a");
		map.put(2, "b");

		StringBuilder sb = new StringBuilder();
		map.forEach((k, v) -> sb.append(k).append(":").append(v).append(","));
		assertTrue(sb.toString().contains("1:a") && sb.toString().contains("2:b"));
	}

	@Test
	public void testReplaceAll() {
		IntObjMap<String> map = new IntObjMap<>(16);
		map.put(1, "a");
		map.put(2, "b");

		map.replaceAll((k, v) -> k + ":" + v);
		assertEquals("1:a", map.get(1));
		assertEquals("2:b", map.get(2));
	}

	@Test
	public void testClone() {
		IntObjMap<String> map = new IntObjMap<>(16);
		map.put(1, "a");

		IntObjMap<String> cloned = map.clone();
		assertEquals("a", cloned.get(1));
		cloned.put(2, "b");
		assertFalse(map.containsKey(2)); // 元に影響なし
	}

	@Test
	public void testResize() {
		IntObjMap<String> map = new IntObjMap<>(4);
		for (int i = 0; i < 10; i++) {
			map.put(i, "v" + i);
		}
		assertEquals(10, map.size());
		for (int i = 0; i < 10; i++) {
			assertEquals("v" + i, map.get(i));
		}
	}

	@Test
	public void testNullValue() {
		IntObjMap<String> map = new IntObjMap<>(16);
		assertNull(map.put(1, null)); // nullを格納
		assertNull(map.get(1)); // nullを取得
		assertTrue(map.containsKey(1)); // キーは存在する
		assertTrue(map.containsValue((String)null));
	}

	@Test
	public void testRandom() {
		Random rnd = new Random(41);
		int[] KIND = new int[] {10, 1_000, 1_000_000};
		for (int t = 0; t < 100; t++) {
			int B = KIND[t % KIND.length];
			IntObjMap<String> myMap = new IntObjMap<>(10);
			HashMap<Integer, String> refMap = new HashMap<>(10);

			for (int i = 0; i < 100_000; i++) {
				int key = rnd.nextInt(B);
				String val = Integer.toString(rnd.nextInt());
				int op = rnd.nextInt(3);

				if (op == 0) { // put
					myMap.put(key, val);
					refMap.put(key, val);
				} else if (op == 1) { // remove
					myMap.remove(key);
					refMap.remove(key);
				} else { // get
					assertEquals(refMap.getOrDefault(key, null), myMap.get(key));
				}
				assertEquals(refMap.size(), myMap.size());
			}
		}
	}
}
