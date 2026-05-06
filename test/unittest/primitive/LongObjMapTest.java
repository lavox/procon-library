package primitive;


import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class LongObjMapTest {

	@Test
	public void testConstructor() {
		LongObjMap<String> map = new LongObjMap<>(16);
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
		assertNull(map.get(0L));
	}

	@Test
	public void testCopyConstructor() {
		LongObjMap<String> original = new LongObjMap<>(16);
		original.put(1L, "a");
		original.put(2L, "b");

		LongObjMap<String> copy = new LongObjMap<>(original);
		assertEquals(2, copy.size());
		assertEquals("a", copy.get(1L));
		assertEquals("b", copy.get(2L));
		assertNull(copy.get(3L));
	}

	@Test
	public void testPutAndGet() {
		LongObjMap<String> map = new LongObjMap<>(16);
		assertNull(map.put(1L, "x")); // 新規
		assertEquals("x", map.put(1L, "y")); // 更新
		assertEquals("y", map.get(1L));
		assertNull(map.get(2L)); // 存在しない
	}

	@Test
	public void testContainsKey() {
		LongObjMap<String> map = new LongObjMap<>(16);
		map.put(1L, "a");
		assertTrue(map.containsKey(1L));
		assertFalse(map.containsKey(2L));
	}

	@Test
	public void testContainsValue() {
		LongObjMap<String> map = new LongObjMap<>(16);
		map.put(1L, "a");
		map.put(2L, "b");
		assertTrue(map.containsValue("a"));
		assertTrue(map.containsValue("b"));
		assertFalse(map.containsValue("c"));
	}

	@Test
	public void testSizeAndIsEmpty() {
		LongObjMap<String> map = new LongObjMap<>(16);
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());

		map.put(1L, "x");
		assertEquals(1, map.size());
		assertFalse(map.isEmpty());

		map.put(2L, "y");
		assertEquals(2, map.size());

		map.remove(1L);
		assertEquals(1, map.size());
	}

	@Test
	public void testRemove() {
		LongObjMap<String> map = new LongObjMap<>(16);
		map.put(1L, "a");
		map.put(2L, "b");

		assertEquals("a", map.remove(1L));
		assertNull(map.remove(1L)); // もうない
		assertEquals(1, map.size());

		assertFalse(map.remove(2L, "x")); // 値が違う
		assertTrue(map.remove(2L, "b"));
		assertEquals(0, map.size());
	}

	@Test
	public void testClear() {
		LongObjMap<String> map = new LongObjMap<>(16);
		map.put(1L, "a");
		map.put(2L, "b");
		assertEquals(2, map.size());

		map.clear();
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
		assertNull(map.get(1L));
	}

	@Test
	public void testKeySet() {
		LongObjMap<String> map = new LongObjMap<>(16);
		map.put(1L, "a");
		map.put(3L, "c");
		map.put(2L, "b");

		long[] keys = map.keySet();
		assertEquals(3, keys.length);
		// 順序は不定なのでソートして確認
		java.util.Arrays.sort(keys);
		assertArrayEquals(new long[]{1L, 2L, 3L}, keys);
	}

	@Test
	public void testValues() {
		LongObjMap<String> map = new LongObjMap<>(16);
		map.put(1L, "a");
		map.put(3L, "c");
		map.put(2L, "b");

		ArrayList<String> values = map.values();
		assertEquals(3, values.size());
		Collections.sort(values);
		assertArrayEquals(new String[]{"a", "b", "c"}, values.toArray(new String[values.size()]));
	}

	@Test
	public void testEntrySet() {
		LongObjMap<String> map = new LongObjMap<>(16);
		map.put(1L, "a");
		map.put(2L, "b");

		ArrayList<LongObjMap.Entry<String>> entries = map.entrySet();
		assertEquals(2, entries.size());
		// エントリの内容を確認
		boolean found1 = false, found2 = false;
		for (LongObjMap.Entry<String> e : entries) {
			if (e.key() == 1L && "a".equals(e.value())) found1 = true;
			if (e.key() == 2L && "b".equals(e.value())) found2 = true;
		}
		assertTrue(found1 && found2);
	}

	@Test
	public void testEntryIterator() {
		LongObjMap<String> map = new LongObjMap<>(16);
		map.put(1L, "a");
		map.put(2L, "b");

		LongObjMap<String>.EntryIterator it = map.entryIterator();
		int count = 0;
		while (it.hasNext()) {
			it.next();
			count++;
		}
		assertEquals(2, count);
	}

	@Test
	public void testGetOrDefault() {
		LongObjMap<String> map = new LongObjMap<>(16);
		map.put(1L, "x");
		assertEquals("x", map.getOrDefault(1L, "default"));
		assertEquals("default", map.getOrDefault(2L, "default"));
	}

	@Test
	public void testPutIfAbsent() {
		LongObjMap<String> map = new LongObjMap<>(16);
		assertNull(map.putIfAbsent(1L, "a"));
		assertEquals("a", map.putIfAbsent(1L, "b")); // 既存なので無視
		assertEquals("a", map.get(1L));
	}

	@Test
	public void testReplace() {
		LongObjMap<String> map = new LongObjMap<>(16);
		map.put(1L, "a");
		assertTrue(map.replace(1L, "a", "b"));
		assertEquals("b", map.get(1L));

		assertFalse(map.replace(1L, "a", "c")); // 古い値が違う
		assertEquals("b", map.get(1L));

		assertEquals("b", map.replace(1L, "x")); // 値指定なし
		assertEquals("x", map.get(1L));
	}

	@Test
	public void testComputeIfAbsent() {
		LongObjMap<String> map = new LongObjMap<>(16);
		assertEquals("1", map.computeIfAbsent(1L, k -> String.valueOf(k)));
		assertEquals("1", map.get(1L));

		assertEquals("1", map.computeIfAbsent(1L, k -> String.valueOf(k * 2))); // 既存なので無視
		assertEquals("1", map.get(1L));
	}

	@Test
	public void testComputeIfPresent() {
		LongObjMap<String> map = new LongObjMap<>(16);
		map.put(1L, "a");
		assertEquals("aa", map.computeIfPresent(1L, (k, v) -> v + v));
		assertEquals("aa", map.get(1L));

		assertNull(map.computeIfPresent(1L, (k, v) -> null));
		assertEquals(null, map.get(1L));
		assertFalse(map.containsKey(1L));

		assertNull(map.computeIfPresent(2L, (k, v) -> v + v)); // 存在しない
	}

	@Test
	public void testCompute() {
		LongObjMap<String> map = new LongObjMap<>(16);
		assertEquals("1", map.compute(1L, (k, v) -> String.valueOf(k))); // 新規
		assertEquals("11", map.compute(1L, (k, v) -> v + v)); // 更新
		assertEquals("11", map.get(1L));
		assertNull(map.compute(1L, (k, v) -> null)); // 削除
		assertEquals(null, map.get(1L));
		assertFalse(map.containsKey(1L));
	}

	@Test
	public void testMerge() {
		LongObjMap<String> map = new LongObjMap<>(16);
		assertEquals("a", map.merge(1L, "a", (v1, v2) -> v1 + v2)); // 新規
		assertEquals("ab", map.merge(1L, "b", (v1, v2) -> v1 + v2)); // マージ
		assertEquals("ab", map.get(1L));
	}

	@Test
	public void testForEach() {
		LongObjMap<String> map = new LongObjMap<>(16);
		map.put(1L, "a");
		map.put(2L, "b");

		StringBuilder sb = new StringBuilder();
		map.forEach((k, v) -> sb.append(k).append(":").append(v).append(","));
		assertTrue(sb.toString().contains("1:a") && sb.toString().contains("2:b"));
	}

	@Test
	public void testReplaceAll() {
		LongObjMap<String> map = new LongObjMap<>(16);
		map.put(1L, "a");
		map.put(2L, "b");

		map.replaceAll((k, v) -> k + ":" + v);
		assertEquals("1:a", map.get(1L));
		assertEquals("2:b", map.get(2L));
	}

	@Test
	public void testClone() {
		LongObjMap<String> map = new LongObjMap<>(16);
		map.put(1L, "a");

		LongObjMap<String> cloned = map.clone();
		assertEquals("a", cloned.get(1L));
		cloned.put(2L, "b");
		assertFalse(map.containsKey(2L)); // 元に影響なし
	}

	@Test
	public void testResize() {
		LongObjMap<String> map = new LongObjMap<>(4);
		for (long i = 0; i < 10; i++) {
			map.put(i, "v" + i);
		}
		assertEquals(10, map.size());
		for (long i = 0; i < 10; i++) {
			assertEquals("v" + i, map.get(i));
		}
	}

	@Test
	public void testNullValue() {
		LongObjMap<String> map = new LongObjMap<>(16);
		assertNull(map.put(1L, null)); // nullを格納
		assertNull(map.get(1L)); // nullを取得
		assertTrue(map.containsKey(1L)); // キーは存在する
		assertTrue(map.containsValue((String)null));
	}

	@Test
	public void testRandom() {
		Random rnd = new Random(41);
		int[] KIND = new int[] {10, 1_000, 1_000_000};
		for (int t = 0; t < 100; t++) {
			int B = KIND[t % KIND.length];
			LongObjMap<String> myMap = new LongObjMap<>(10);
			HashMap<Long, String> refMap = new HashMap<>(10);

			for (int i = 0; i < 100_000; i++) {
				long key = rnd.nextInt(B);
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
