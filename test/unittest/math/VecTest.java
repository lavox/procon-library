import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VecTest {
  @Test
  public void test_add() {
    Vec a = new Vec(new long[] {1, 2, 3});
    Vec b = new Vec(new long[] {4, 5, 6});
    Vec c = a.add(b);
    long[] expected = new long[] {5, 7, 9};
    assertArrayEquals(expected, c.a);
  }
  @Test
  public void test_sub() {
    Vec a = new Vec(new long[] {1, 2, 3});
    Vec b = new Vec(new long[] {4, 5, 6});
    Vec c = a.sub(b);
    long[] expected = new long[] {-3, -3, -3};
    assertArrayEquals(expected, c.a);
  }
  @Test
  public void test_mul() {
    Vec a = new Vec(new long[] {1, 2, 3});
    Vec b = a.mul(3);
    long[] expected = new long[] {3, 6, 9};
    assertArrayEquals(expected, b.a);
  }
  @Test
  public void test_dot() {
    Vec a = new Vec(new long[] {1, 2, 3});
    Vec b = new Vec(new long[] {4, 5, 6});
    long c = a.dot(b);
    assertEquals(32, c);
  }
  @Test
  public void test_norm() {
    Vec a = new Vec(new long[] {1, 2, 3});
    long b = a.norm1();
    assertEquals(6, b);

    long c = a.norm2Sq();
    double d = a.norm2();
    assertEquals(14, c);
    assertEquals(Math.sqrt(14), d, 1e-12);
  }
  @Test
  public void test_dist() {
    Vec a = new Vec(new long[] {1, 2, 3});
    Vec b = new Vec(new long[] {2, 1, 5});
    long c = a.dist1(b);
    assertEquals(4, c);

    long d = a.dist2Sq(b);
    double e = a.dist2(b);
    assertEquals(6, d);
    assertEquals(Math.sqrt(6), e, 1e-12);
  }
}
