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
}
