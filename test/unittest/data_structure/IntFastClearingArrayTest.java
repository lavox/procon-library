package data_structure;

import static org.junit.Assert.*;

import org.junit.Test;

public class IntFastClearingArrayTest {

	@Test
	public void testDefaultValue() {
		IntFastClearingArray array = new IntFastClearingArray(4, -1);
		assertEquals(-1, array.get(0));
		assertEquals(-1, array.get(3));
	}

	@Test
	public void testSetAndGet() {
		IntFastClearingArray array = new IntFastClearingArray(3, 0);
		assertEquals(0, array.get(1));
		array.set(1, 1);
		assertEquals(1, array.get(1));
		assertEquals(0, array.get(0));
	}

	@Test
	public void testClearResetsValues() {
		IntFastClearingArray array = new IntFastClearingArray(3, 0);
		array.set(0, 10);
		array.set(2, 20);
		assertEquals(10, array.get(0));
		assertEquals(20, array.get(2));

		array.clear();
		assertEquals(0, array.get(0));
		assertEquals(0, array.get(2));

		array.set(1, 30);
		assertEquals(30, array.get(1));
		assertEquals(0, array.get(0));
	}

	@Test
	public void testClearMultipleTimes() {
		IntFastClearingArray array = new IntFastClearingArray(2, 999);
		array.set(0, 1);
		assertEquals(1, array.get(0));

		array.clear();
		assertEquals(999, array.get(0));

		array.clear();
		assertEquals(999, array.get(0));
	}
}
