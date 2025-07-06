import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LazySegmentTreeTest {
  @Test
  public void test() {
    Integer[] arr = new Integer[] {
      2,4,5,3,6,7,4,3,1,1
    };
    LazySegmentTree<Integer, Integer> tree = new LazySegmentTree<>(arr, new LazyOperator<>() {
      public Integer op(Integer a, Integer b) {
        return Math.max(a, b);
      }
      public Integer e() {
        return 0;
      }
      public Integer mapping(Integer x, Integer a, int len) {
        return x + a;
      }
      public Integer composition(Integer x, Integer y) {
        return x + y;
      }
      public Integer identity() {
        return 0;
      }
    });
    tree.apply(1, 4, 5);
    // 2,9,10,8,6,7,4,3,1,1
    assertEquals(10, (int)tree.query(0, 3));
    assertEquals(2, tree.maxRight(0, a -> a <= 9));
    assertEquals(10, tree.maxRight(6, a -> a <= 5));
    assertEquals(6, tree.minLeft(10, a -> a <= 5));
    assertEquals(0, tree.minLeft(2, a -> a <= 9));
  }
  @Test
  public void testInt() {
    int[] arr = new int[] {
      2,4,5,3,6,7,4,3,1,1
    };
    IntLazySegmentTree tree = new IntLazySegmentTree(arr, new IntLazyMaxAdd());
    tree.apply(1, 4, 5);
    assertEquals(10, tree.query(0, 3));
    assertEquals(2, tree.maxRight(0, a -> a <= 9));
    assertEquals(10, tree.maxRight(6, a -> a <= 5));
    assertEquals(6, tree.minLeft(10, a -> a <= 5));
    assertEquals(0, tree.minLeft(2, a -> a <= 9));

    tree = new IntLazySegmentTree(arr, new IntLazyMaxUpdate());
    tree.apply(1, 4, 5);
    assertEquals(5, tree.query(0, 2));

    tree = new IntLazySegmentTree(arr, new IntLazyMinAdd());
    tree.apply(1, 4, 2);
    assertEquals(5, tree.query(1, 5));

    tree = new IntLazySegmentTree(arr, new IntLazyMinUpdate());
    tree.apply(1, 4, 2);
    assertEquals(2, tree.query(0, 5));

    tree = new IntLazySegmentTree(arr, new IntLazySumAdd());
    tree.apply(1, 4, 5);
    assertEquals(21, tree.query(0, 3));

    tree = new IntLazySegmentTree(arr, new IntLazySumUpdate());
    tree.apply(1, 4, 5);
    assertEquals(12, tree.query(0, 3));
  }
  @Test
  public void testLong() {
    long[] arr = new long[] {
      2,4,5,3,6,7,4,3,1,1
    };
    LongLazySegmentTree tree = new LongLazySegmentTree(arr, new LongLazyMaxAdd());
    tree.apply(1, 4, 5);
    assertEquals(10, tree.query(0, 3));
    assertEquals(2, tree.maxRight(0, a -> a <= 9));
    assertEquals(10, tree.maxRight(6, a -> a <= 5));
    assertEquals(6, tree.minLeft(10, a -> a <= 5));
    assertEquals(0, tree.minLeft(2, a -> a <= 9));

    tree = new LongLazySegmentTree(arr, new LongLazyMaxUpdate());
    tree.apply(1, 4, 5);
    assertEquals(5, tree.query(0, 2));

    tree = new LongLazySegmentTree(arr, new LongLazyMinAdd());
    tree.apply(1, 4, 2);
    assertEquals(5, tree.query(1, 5));

    tree = new LongLazySegmentTree(arr, new LongLazyMinUpdate());
    tree.apply(1, 4, 2);
    assertEquals(2, tree.query(0, 5));

    tree = new LongLazySegmentTree(arr, new LongLazySumAdd());
    tree.apply(1, 4, 5);
    assertEquals(21, tree.query(0, 3));

    tree = new LongLazySegmentTree(arr, new LongLazySumUpdate());
    tree.apply(1, 4, 5);
    assertEquals(12, tree.query(0, 3));
  }
}
