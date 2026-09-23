package math;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BitUtilTest {
	@Test
	public void test() {
		assertTrue(BitUtil.check(6, 1));
		assertTrue(BitUtil.check(6, 2));
		assertFalse(BitUtil.check(6, 0));

		assertTrue(BitUtil.check(1L << 63, 63));
		assertFalse(BitUtil.check(1L << 63, 64));
	}
}