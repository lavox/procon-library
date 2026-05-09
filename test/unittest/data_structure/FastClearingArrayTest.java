package data_structure;

import static org.junit.Assert.*;

import org.junit.Test;

public class FastClearingArrayTest {

	@Test
	public void testDefaultValue() {
		FastClearingArray<Integer> array = new FastClearingArray<>(4, -1);
		assertEquals(Integer.valueOf(-1), array.get(0));
		assertEquals(Integer.valueOf(-1), array.get(3));
	}

	@Test
	public void testSetAndGet() {
		FastClearingArray<String> array = new FastClearingArray<>(3, "default");
		assertEquals("default", array.get(1));
		array.set(1, "hello");
		assertEquals("hello", array.get(1));
		assertEquals("default", array.get(0));
	}

	@Test
	public void testClearResetsValues() {
		FastClearingArray<Integer> array = new FastClearingArray<>(3, 0);
		array.set(0, 10);
		array.set(2, 20);
		assertEquals(Integer.valueOf(10), array.get(0));
		assertEquals(Integer.valueOf(20), array.get(2));

		array.clear();
		assertEquals(Integer.valueOf(0), array.get(0));
		assertEquals(Integer.valueOf(0), array.get(2));

		array.set(1, 30);
		assertEquals(Integer.valueOf(30), array.get(1));
		assertEquals(Integer.valueOf(0), array.get(0));
	}

	@Test
	public void testClearMultipleTimes() {
		FastClearingArray<Long> array = new FastClearingArray<>(2, 999L);
		array.set(0, 1L);
		assertEquals(Long.valueOf(1L), array.get(0));

		array.clear();
		assertEquals(Long.valueOf(999L), array.get(0));

		array.clear();
		assertEquals(Long.valueOf(999L), array.get(0));
	}
}
