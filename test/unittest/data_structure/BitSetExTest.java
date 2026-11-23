package data_structure;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;

import org.junit.Test;

public class BitSetExTest {
	private static final int[] SIZES = {0, 1, 63, 64, 65, 127, 128, 129, 193};

	@Test
	public void testConstructorAndCopies() {
		for (int n : SIZES) assertBits("empty", new BitSet(), new BitSetEx(n), n);
		long[] words = {1, Long.MIN_VALUE, 0};
		BitSetEx set = BitSetEx.valueOf(words);
		words[0] = 0;
		assertArrayEquals(new long[] {1, Long.MIN_VALUE, 0}, set.toLongArray());
		assertEquals(192, set.size());
		long[] copy = set.toLongArray();
		copy[1] = 0;
		assertTrue(set.get(127));
		BitSetEx clone = set.clone();
		assertNotSame(set, clone);
		assertEquals(set, clone);
		assertEquals(set.hashCode(), clone.hashCode());
		clone.clear();
		assertEquals(2, set.cardinality());
		assertFalse(set.equals(clone));
		assertFalse(new BitSetEx(1).equals(new BitSetEx(2)));
		assertFalse(set.equals(null));
		assertFalse(set.equals(new BitSet()));
		assertTrue(set.equals(set));
		assertBits("empty array", new BitSet(), BitSetEx.valueOf(new long[0]), 0);
	}

	@Test
	public void testSingleBitOperations() {
		BitSetEx actual = new BitSetEx(193);
		BitSet expected = new BitSet();
		Random random = new Random(1);
		for (int step = 0; step < 1000; step++) {
			int i = random.nextInt(193);
			switch (step % 5) {
			case 0: actual.set(i); expected.set(i); break;
			case 1: actual.clear(i); expected.clear(i); break;
			case 2: actual.flip(i); expected.flip(i); break;
			case 3: actual.set(i, true); expected.set(i, true); break;
			default: actual.set(i, false); expected.set(i, false); break;
			}
			assertBits("step=" + step, expected, actual, 193);
			assertEquals(expected.get(i), actual.get(i));
		}
		actual.clear();
		assertBits("clear", new BitSet(), actual, 193);
	}

	@Test
	public void testRangeOperationsAndGet() {
		Random random = new Random(2);
		for (int n : SIZES) {
			BitSet initial = randomBits(n, random);
			BitSetEx source = fromBits(initial, n);
			for (int l = 0; l <= n; l++) {
				for (int r = l; r <= n; r++) {
					String context = "n=" + n + ", range=[" + l + "," + r + ")";
					assertBits(context, initial.get(l, r), source.get(l, r), r - l);
					for (int op = 0; op < 5; op++) {
						BitSetEx actual = source.clone();
						BitSet expected = (BitSet) initial.clone();
						switch (op) {
						case 0: actual.set(l, r); expected.set(l, r); break;
						case 1: actual.clear(l, r); expected.clear(l, r); break;
						case 2: actual.flip(l, r); expected.flip(l, r); break;
						case 3: actual.set(l, r, true); expected.set(l, r, true); break;
						default: actual.set(l, r, false); expected.set(l, r, false); break;
						}
						assertBits(context + ", op=" + op, expected, actual, n);
					}
				}
			}
			assertBits("source unchanged", initial, source, n);
		}
		BitSetEx source = new BitSetEx(128);
		source.set(65);
		assertTrue(source.get(0, 65).isEmpty()); // 終端以降のビットを混入させない
		BitSetEx slice = source.get(64, 128);
		slice.clear();
		assertTrue(source.get(65));
	}

	@Test
	public void testReversedRanges() {
		BitSetEx set = new BitSetEx(65);
		assertThrows(IndexOutOfBoundsException.class, () -> set.set(2, 1));
		assertThrows(IndexOutOfBoundsException.class, () -> set.clear(2, 1));
		assertThrows(IndexOutOfBoundsException.class, () -> set.flip(2, 1));
		assertThrows(IndexOutOfBoundsException.class, () -> set.get(2, 1));
	}

	@Test
	public void testBitSearch() {
		Random random = new Random(3);
		for (int n : SIZES) {
			for (int pattern = 0; pattern < 3; pattern++) {
				BitSet expected = pattern == 2 ? randomBits(n, random) : new BitSet();
				if (pattern == 1) expected.set(0, n);
				BitSetEx actual = fromBits(expected, n);
				for (int i = 0; i <= n; i++) {
					String context = "n=" + n + ", pattern=" + pattern + ", i=" + i;
					assertEquals(context, expected.nextSetBit(i), actual.nextSetBit(i));
					int nextClear = expected.nextClearBit(i);
					assertEquals(context, nextClear < n ? nextClear : -1, actual.nextClearBit(i));
					assertEquals(context, expected.previousSetBit(i - 1), actual.previousSetBit(i - 1));
					assertEquals(context, expected.previousClearBit(i - 1), actual.previousClearBit(i - 1));
				}
				assertEquals(-1, actual.nextSetBit(Integer.MAX_VALUE));
				assertEquals(-1, actual.nextClearBit(Integer.MAX_VALUE));
			}
		}
	}

	@Test
	public void testLogicalOperationsAndSubset() {
		Random random = new Random(4);
		for (int n : SIZES) {
			for (int m : SIZES) {
				for (int pattern = 0; pattern < 6; pattern++) {
					BitSet a = pattern == 0 ? new BitSet() : randomBits(n, random);
					BitSet b = pattern == 1 ? new BitSet() : randomBits(m, random);
					if (pattern == 2) b.set(0, m);
					BitSetEx left = fromBits(a, n), right = fromBits(b, m);
					String context = "n=" + n + ", m=" + m + ", pattern=" + pattern;
					assertEquals(context, a.intersects(b), left.intersects(right));
					BitSet difference = (BitSet) a.clone();
					difference.andNot(b);
					assertEquals(context, difference.isEmpty(), left.isSubsetOf(right));
					assertTrue(left.isSubsetOf(left));
					assertTrue(left.isSubsetOf(left.clone()));
					for (int op = 0; op < 4; op++) {
						BitSet expected = (BitSet) a.clone();
						BitSetEx actual = left.clone();
						switch (op) {
						case 0: actual.and(right); expected.and(b); break;
						case 1: actual.or(right); expected.or(b); break;
						case 2: actual.xor(right); expected.xor(b); break;
						default: actual.andNot(right); expected.andNot(b); break;
						}
						if (expected.length() > n) expected.clear(n, expected.length());
						assertBits(context + ", op=" + op, expected, actual, n);
					}
					assertBits("left unchanged", a, left, n);
					assertBits("right unchanged", b, right, m);
				}
			}
		}
	}

	@Test
	public void testSelfLogicalOperations() {
		for (int n : SIZES) {
			BitSet expected = new BitSet();
			expected.set(0, n);
			BitSetEx set = fromBits(expected, n);
			set.and(set);
			assertBits("self and", expected, set, n);
			set.or(set);
			assertBits("self or", expected, set, n);
			set.xor(set);
			assertBits("self xor", new BitSet(), set, n);
			set = fromBits(expected, n);
			set.andNot(set);
			assertBits("self andNot", new BitSet(), set, n);
		}
	}

	@Test
	public void testLeftShifts() {
		checkShifts(true);
	}

	@Test
	public void testRightShifts() {
		checkShifts(false);
	}

	private void checkShifts(boolean left) {
		Random random = new Random(5);
		for (int n : SIZES) {
			for (int pattern = 0; pattern < 4; pattern++) {
				BitSet original = pattern == 3 ? randomBits(n, random) : new BitSet();
				if (pattern == 1) original.set(0, n);
				if (pattern == 2) for (int i = 0; i < n; i += 64) original.set(i);
				BitSetEx source = fromBits(original, n);
				// 各シフト量に加え、加算のオーバーフローを起こし得る値も確認する。
				for (int s = 0; s <= n + 2; s++) {
					int bits = s == n + 2 ? Integer.MAX_VALUE : s;
					String context = "n=" + n + ", pattern=" + pattern + ", bits=" + bits;
					BitSet expected = new BitSet();
					for (int i = original.nextSetBit(0); i >= 0; i = original.nextSetBit(i + 1)) {
						long target = left ? (long) i + bits : (long) i - bits;
						if (0 <= target && target < n) expected.set((int) target);
					}
					BitSetEx shifted = left ? source.leftShift(bits) : source.rightShift(bits);
					assertNotSame(source, shifted);
					assertBits(context, expected, shifted, n);
					BitSetEx assigned = source.clone();
					if (left) assigned.leftShiftAsn(bits); else assigned.rightShiftAsn(bits);
					assertBits(context + " assigned", expected, assigned, n);
					expected.or(original);
					BitSetEx combined = source.clone();
					if (left) combined.orLeftShiftAsn(bits); else combined.orRightShiftAsn(bits);
					assertBits(context + " or", expected, combined, n);
					shifted.clear();
					assertBits("source unchanged", original, source, n);
				}
			}
		}
	}

	private static BitSet randomBits(int n, Random random) {
		BitSet bits = new BitSet();
		for (int i = 0; i < n; i++) if (random.nextBoolean()) bits.set(i);
		return bits;
	}

	private static BitSetEx fromBits(BitSet bits, int n) {
		BitSetEx set = new BitSetEx(n);
		for (int i = bits.nextSetBit(0); i >= 0; i = bits.nextSetBit(i + 1)) set.set(i);
		return set;
	}

	private static void assertBits(String context, BitSet expected, BitSetEx actual, int n) {
		assertEquals(context, n, actual.length());
		assertEquals(context, n, actual.size());
		// 固定長の配列を比較し、末尾の未使用ビットに1が残っていないことも確認する。
		assertArrayEquals(context, Arrays.copyOf(expected.toLongArray(), (n + 63) / 64), actual.toLongArray());
		assertEquals(context, expected.cardinality(), actual.cardinality());
		assertEquals(context, expected.isEmpty(), actual.isEmpty());
	}
}
