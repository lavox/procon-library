package data_structure;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class WaveletMatrixTest {
  @Test
  public void testAccess() {
    int[] arr = new int[] {11, 1, 9, 0, 3, 5, 11, 5, 11, 16, 2, 10, 11, 17};
    WaveletMatrix wm = new WaveletMatrix(arr);
    for (int i = 0; i < arr.length; i++) {
      assertEquals(arr[i], wm.access(i));
    }
  }
  @Test
  public void testQuantile() {
    int[] arr = new int[] {11, 1, 9, 0, 3, 5, 11, 5, 11, 16, 2, 10, 11, 17};
    WaveletMatrix wm = new WaveletMatrix(arr);
    assertEquals(0, wm.quantile(3, 9, 0)); // 0, 3, 5, 11, 5, 11
    assertEquals(3, wm.quantile(3, 9, 1));
    assertEquals(5, wm.quantile(3, 9, 2));
    assertEquals(5, wm.quantile(3, 9, 3));
    assertEquals(11, wm.quantile(3, 9, 4));
    assertEquals(11, wm.quantile(3, 9, 5));

    assertEquals(1, wm.quantile(0, 3, 0));
    assertEquals(0, wm.quantile(0, 4, 0));
  }
  @Test
  public void testRangeFreq() {
    int[] arr = new int[] {11, 1, 9, 0, 3, 5, 11, 5, 11, 16, 2, 10, 11, 17};
    WaveletMatrix wm = new WaveletMatrix(arr);
    assertEquals(3, wm.rangeFreq(3, 9, 3, 6)); // 0, 3, 5, 11, 5, 11
    assertEquals(0, wm.rangeFreq(3, 9, 4, 5));

    assertEquals(4, wm.rangeFreqBelow(3, 9, 6));
    assertEquals(2, wm.rangeFreqBelow(3, 9, 5));

    assertEquals(5, wm.rangeFreqAbove(3, 9, 3));
    assertEquals(2, wm.rangeFreqAbove(3, 9, 6));
  }
  @Test
  public void testNextValue() {
    int[] arr = new int[] {11, 1, 9, 0, 3, 5, 11, 5, 11, 16, 2, 10, 11, 17};
    WaveletMatrix wm = new WaveletMatrix(arr);
    assertEquals(3, wm.nextValue(3, 9, 2)); // 0, 3, 5, 11, 5, 11
    assertEquals(3, wm.nextValue(3, 9, 3));

    assertEquals(5, wm.prevValue(3, 9, 6));
    assertEquals(3, wm.prevValue(3, 9, 5));
  }
  @Test
  public void testRank() {
    int[] arr = new int[] {11, 1, 9, 0, 3, 5, 11, 5, 11, 16, 2, 10, 11, 17};
    WaveletMatrix wm = new WaveletMatrix(arr);
    assertEquals(2, wm.rank(3, 12, 11)); // 0, 3, 5, 11, 5, 11, 16, 2, 10,
    assertEquals(0, wm.rank(3, 12, 1));
  }
  @Test
  public void testSelect() {
    int[] arr = new int[] {11, 1, 9, 0, 3, 5, 11, 5, 11, 16, 2, 10, 11, 17};
    WaveletMatrix wm = new WaveletMatrix(arr);
    assertEquals(8, wm.select(11, 2));
    assertEquals(6, wm.select(1, 10, 11, 0));
  }

  @Test
  public void testValueBoundaries() {
    int[] arr = {0, 1, Integer.MAX_VALUE, (1 << 30) - 1, (1 << 30), Integer.MAX_VALUE, (1 << 30), Integer.MAX_VALUE};
    checkQueries(arr, new WaveletMatrix(arr));
    checkQueries(arr, new WaveletMatrix(arr, 31));
  }

  @Test
  public void testZeroArrayAndRange() {
    int[] arr = new int[65];
    for (WaveletMatrix wm : new WaveletMatrix[] {new WaveletMatrix(arr), new WaveletMatrix(arr, 0)}) {
      checkQueries(arr, wm);
      WaveletMatrix.Range range = wm.createRange(1, 65);
      assertEquals(64, range.len());
      assertEquals(64, range.next_len0());
      assertEquals(0, range.next_len1());
      range.move0();
      assertEquals(1, range.l());
      assertEquals(65, range.r());
    }
  }

  @Test
  public void testEmptyArray() {
    WaveletMatrix wm = new WaveletMatrix(new int[0]);
    checkQueries(new int[0], wm);
    assertEquals(-1, wm.select(0, 0));
    WaveletMatrix.Range range = wm.createRange(0, 0);
    assertEquals(0, range.len());
    assertEquals(0, range.next_len0());
    assertEquals(0, range.next_len1());
  }

  @Test
  public void testBlockBoundaries() {
    for (int n : new int[] {63, 64, 65, 127, 128, 129}) {
      int[] arr = new int[n];
      for (int i = 0; i < n; i++) arr[i] = i % 3 == 0 ? Integer.MAX_VALUE : i % 5;
      checkQueries(arr, new WaveletMatrix(arr));
    }
  }

  @Test
  public void testRandomQueries() {
    java.util.Random random = new java.util.Random(20261115L);
    for (int trial = 0; trial < 20; trial++) {
      int[] arr = new int[random.nextInt(80) + 1];
      for (int i = 0; i < arr.length; i++) {
        arr[i] = trial % 2 == 0 ? random.nextInt(16) : random.nextInt() & Integer.MAX_VALUE;
      }
      checkQueries(arr, new WaveletMatrix(arr));
    }
  }

  // Compare against a sorted subarray and a direct scan, independently of the matrix layout.
  private void checkQueries(int[] arr, WaveletMatrix wm) {
    int n = arr.length;
    for (int i = 0; i < n; i++) assertEquals(arr[i], wm.access(i));
    java.util.Random random = new java.util.Random(42L);
    for (int query = 0; query < 30; query++) {
      int l = query == 0 ? 0 : random.nextInt(n + 1);
      int r = query == 0 ? n : l + random.nextInt(n - l + 1);
      int[] sorted = java.util.Arrays.copyOfRange(arr, l, r);
      java.util.Arrays.sort(sorted);
      for (int k = 0; k < sorted.length; k++) {
        assertEquals(sorted[k], wm.quantile(l, r, k));
      }
      int[] values = {Integer.MIN_VALUE, -1, 0, 1, 2, 16, (1 << 30) - 1, (1 << 30), Integer.MAX_VALUE,
          n == 0 ? 0 : arr[random.nextInt(n)]};
      for (int v : values) {
        int below = 0;
        int count = 0;
        int prev = -1;
        int next = -1;
        for (int x : sorted) {
          if (x < v) {
            below++;
            prev = x;
          } else if (next == -1) {
            next = x;
          }
          if (x == v) count++;
        }
        assertEquals(below, wm.rangeFreqBelow(l, r, v));
        assertEquals(r - l - below, wm.rangeFreqAbove(l, r, v));
        assertEquals(count, wm.rank(l, r, v));
        assertEquals(prev, wm.prevValue(l, r, v));
        assertEquals(next, wm.nextValue(l, r, v));
        int k = 0;
        for (int i = l; i < r; i++) {
          if (arr[i] == v) assertEquals(i, wm.select(l, r, v, k++));
        }
        assertEquals(-1, wm.select(l, r, v, k));
        if (l == 0 && r == n) assertEquals(-1, wm.select(v, k));
        int upper = Integer.MAX_VALUE;
        int between = 0;
        for (int x : sorted) if (v <= x && x < upper) between++;
        assertEquals(between, wm.rangeFreq(l, r, v, upper));
      }
    }
  }
}
