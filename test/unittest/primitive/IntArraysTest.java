package primitive;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class IntArraysTest {

	private static final int SIZE = 1000;

	@Test
	void testSortAscendingRandom() {
		int[] arr = new int[SIZE];
		Random rng = new Random(12345);
		for (int i = 0; i < SIZE; i++) arr[i] = rng.nextInt();

		int[] copy = Arrays.copyOf(arr, arr.length);
		Arrays.sort(copy);

		IntArrays.sort(arr, Integer::compare);
		assertArrayEquals(copy, arr);
	}

	@Test
	void testSortDescendingRandom() {
		int[] arr = new int[SIZE];
		Random rng = new Random(54321);
		for (int i = 0; i < SIZE; i++) arr[i] = rng.nextInt();

		IntArrays.sort(arr, (x, y) -> Integer.compare(y, x));

		for (int i = 1; i < SIZE; i++) {
			assertTrue(arr[i - 1] >= arr[i]);
		}
	}

	@Test
	void testSortWithCustomComparator() {
		int[] arr = new int[SIZE];
		for (int i = 0; i < SIZE; i++) arr[i] = i;

		// 余り順ソート
		IntArrays.sort(arr, (x, y) -> Integer.compare(x % 10, y % 10));

		for (int i = 1; i < SIZE; i++) {
			int prev = arr[i - 1] % 10;
			int curr = arr[i] % 10;
			assertTrue(prev <= curr);
		}
	}

	@Test
	void testSortAlreadySorted() {
		int[] arr = new int[SIZE];
		for (int i = 0; i < SIZE; i++) arr[i] = i;

		int[] copy = Arrays.copyOf(arr, arr.length);
		IntArrays.sort(arr, Integer::compare);

		assertArrayEquals(copy, arr);
	}

	@Test
	void testSortAllSameValues() {
		int[] arr = new int[SIZE];
		Arrays.fill(arr, 42);

		IntArrays.sort(arr, Integer::compare);

		for (int v : arr) assertEquals(42, v);
	}

	@Test
	void testSortPartialRandom() {
		int[] arr = new int[SIZE];
		for (int i = 0; i < SIZE; i++) arr[i] = i;

		Random rng = new Random(2025);
		for (int i = 0; i < SIZE / 10; i++) {
			int j = rng.nextInt(SIZE);
			int k = rng.nextInt(SIZE);
			int tmp = arr[j];
			arr[j] = arr[k];
			arr[k] = tmp;
		}

		int[] copy = Arrays.copyOf(arr, arr.length);
		Arrays.sort(copy);

		IntArrays.sort(arr, Integer::compare);
		assertArrayEquals(copy, arr);
	}

	@Test
	void testSortSubrange() {
		int[] arr = new int[SIZE];
		Random rng = new Random(2025);
		for (int i = 0; i < SIZE; i++) arr[i] = rng.nextInt();

		int[] copy = Arrays.copyOf(arr, arr.length);
		Arrays.sort(copy, 100, 900);

		IntArrays.sort(arr, 100, 900, Integer::compare);
		assertArrayEquals(copy, arr);
	}
}
