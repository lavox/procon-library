import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CompressionTest {
  @Test
  public void test() {
    long[] arr = new long[] {2, 5, 100, 101, 102, 5};
    Compression comp = new Compression(arr);
    assertEquals(5, comp.size());
    assertEquals(0, comp.toIndex(2));
    assertEquals(1, comp.toIndex(5));
    assertEquals(2, comp.toIndex(100));
    assertEquals(-1, comp.toIndex(1));

    assertEquals(102, comp.toValue(4));

    assertEquals(1, comp.toIndexFloor(6));
    assertEquals(1, comp.toIndexFloor(5));
    assertEquals(-1, comp.toIndexFloor(1));

    assertEquals(2, comp.toIndexCeiling(6));
    assertEquals(2, comp.toIndexCeiling(100));
    assertEquals(5, comp.toIndexCeiling(103));
  }
}
