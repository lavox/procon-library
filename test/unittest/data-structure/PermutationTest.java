import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PermutationTest {
  @Test
  public void test_int() {
    int[] arr = new int[] {1, 2, 3};
    assertTrue(Permutation.nextPermutation(arr));
    assertArrayEquals(new int[] {1, 3, 2}, arr);
    assertTrue(Permutation.nextPermutation(arr));
    assertArrayEquals(new int[] {2, 1, 3}, arr);
    assertTrue(Permutation.nextPermutation(arr));
    assertArrayEquals(new int[] {2, 3, 1}, arr);
    assertTrue(Permutation.nextPermutation(arr));
    assertArrayEquals(new int[] {3, 1, 2}, arr);
    assertTrue(Permutation.nextPermutation(arr));
    assertArrayEquals(new int[] {3, 2, 1}, arr);
    assertFalse(Permutation.nextPermutation(arr));
  }
  @Test
  public void test_long() {
    long[] arr = new long[] {1, 2, 3};
    assertTrue(Permutation.nextPermutation(arr));
    assertArrayEquals(new long[] {1, 3, 2}, arr);
    assertTrue(Permutation.nextPermutation(arr));
    assertArrayEquals(new long[] {2, 1, 3}, arr);
    assertTrue(Permutation.nextPermutation(arr));
    assertArrayEquals(new long[] {2, 3, 1}, arr);
    assertTrue(Permutation.nextPermutation(arr));
    assertArrayEquals(new long[] {3, 1, 2}, arr);
    assertTrue(Permutation.nextPermutation(arr));
    assertArrayEquals(new long[] {3, 2, 1}, arr);
    assertFalse(Permutation.nextPermutation(arr));
  }
}
