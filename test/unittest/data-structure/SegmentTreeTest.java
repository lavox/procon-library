import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SegmentTreeTest {
  @Test
  public void test() {
    Integer[] arr = new Integer[] {
      2,4,5,3,6,7,4,3,1,1
    };
    SegmentTree<Integer> tree = new SegmentTree<>(arr, (a, b) -> Math.max(a, b), () -> 0);
    assertEquals(5, (int)tree.query(0, 3));
    assertEquals(4, tree.maxRight(0, a -> a <= 5));
    assertEquals(10, tree.maxRight(6, a -> a <= 5));
    assertEquals(6, tree.minLeft(10, a -> a <= 5));
    assertEquals(0, tree.minLeft(2, a -> a <= 5));
  }
  @Test
  public void testInt() {
    int[] arr = new int[] {
      2,4,5,3,6,7,4,3,1,1
    };
    IntSegmentTree tree = new IntSegmentTree(arr, (a, b) -> Math.max(a, b), 0);
    assertEquals(5, tree.query(0, 3));
    assertEquals(4, tree.maxRight(0, a -> a <= 5));
    assertEquals(10, tree.maxRight(6, a -> a <= 5));
    assertEquals(6, tree.minLeft(10, a -> a <= 5));
    assertEquals(0, tree.minLeft(2, a -> a <= 5));
  }
  @Test
  public void testLong() {
    long[] arr = new long[] {
      2,4,5,3,6,7,4,3,1,1
    };
    LongSegmentTree tree = new LongSegmentTree(arr, (a, b) -> Math.max(a, b), 0);
    assertEquals(5, tree.query(0, 3));
    assertEquals(4, tree.maxRight(0, a -> a <= 5));
    assertEquals(10, tree.maxRight(6, a -> a <= 5));
    assertEquals(6, tree.minLeft(10, a -> a <= 5));
    assertEquals(0, tree.minLeft(2, a -> a <= 5));
  }
}
