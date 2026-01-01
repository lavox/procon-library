package primitive;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class LongArraysTest {

	private static final int SIZE = 1000;

	@Test
	void testSortAscendingRandom() {
		long[] arr = new long[SIZE];
		Random rng = new Random(12345);
		for (int i = 0; i < SIZE; i++) arr[i] = rng.nextLong();

		long[] copy = Arrays.copyOf(arr, arr.length);
		Arrays.sort(copy);

		LongArrays.sort(arr, Long::compare);
		assertArrayEquals(copy, arr);
	}

	@Test
	void testSortDescendingRandom() {
		long[] arr = new long[SIZE];
		Random rng = new Random(54321);
		for (int i = 0; i < SIZE; i++) arr[i] = rng.nextLong();

		LongArrays.sort(arr, (x, y) -> Long.compare(y, x));

		for (int i = 1; i < SIZE; i++) {
			assertTrue(arr[i - 1] >= arr[i]);
		}
	}

	@Test
	void testSortWithCustomComparator() {
		long[] arr = new long[SIZE];
		for (int i = 0; i < SIZE; i++) arr[i] = i;

		// 余り順ソート
		LongArrays.sort(arr, (x, y) -> Long.compare(x % 10, y % 10));

		for (int i = 1; i < SIZE; i++) {
			long prev = arr[i - 1] % 10;
			long curr = arr[i] % 10;
			assertTrue(prev <= curr);
		}
	}

	@Test
	void testSortAlreadySorted() {
		long[] arr = new long[SIZE];
		for (int i = 0; i < SIZE; i++) arr[i] = i;

		long[] copy = Arrays.copyOf(arr, arr.length);
		LongArrays.sort(arr, Long::compare);

		assertArrayEquals(copy, arr);
	}

	@Test
	void testSortAllSameValues() {
		long[] arr = new long[SIZE];
		Arrays.fill(arr, 42L);

		LongArrays.sort(arr, Long::compare);

		for (long v : arr) assertEquals(42L, v);
	}

	@Test
	void testSortPartialRandom() {
		long[] arr = new long[SIZE];
		for (int i = 0; i < SIZE; i++) arr[i] = i;

		Random rng = new Random(2025);
		for (int i = 0; i < SIZE / 10; i++) {
			int j = rng.nextInt(SIZE);
			int k = rng.nextInt(SIZE);
			long tmp = arr[j];
			arr[j] = arr[k];
			arr[k] = tmp;
		}

		long[] copy = Arrays.copyOf(arr, arr.length);
		Arrays.sort(copy);

		LongArrays.sort(arr, Long::compare);
		assertArrayEquals(copy, arr);
	}

	@Test
	void testSortSubrange() {
		long[] arr = new long[SIZE];
		Random rng = new Random(2025);
		for (int i = 0; i < SIZE; i++) arr[i] = rng.nextLong();

		long[] copy = Arrays.copyOf(arr, arr.length);
		Arrays.sort(copy, 100, 900);

		LongArrays.sort(arr, 100, 900, Long::compare);
		assertArrayEquals(copy, arr);
	}
}
