package geometry;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Vec2LTest {
  @Test
  public void test_add() {
    Vec2L a = new Vec2L(1, 2);
    Vec2L b = new Vec2L(4, 5);
    Vec2L c = a.add(b);
    Vec2L expected = new Vec2L(5, 7);
    assertEquals(expected, c);
  }
  @Test
  public void test_sub() {
    Vec2L a = new Vec2L(1, 2);
    Vec2L b = new Vec2L(4, 5);
    Vec2L c = a.sub(b);
    Vec2L expected = new Vec2L(-3, -3);
    assertEquals(expected, c);
  }
  @Test
  public void test_mul() {
    Vec2L a = new Vec2L(1, 2);
    Vec2L b = a.mul(3);
    Vec2L expected = new Vec2L(3, 6);
    assertEquals(expected, b);
  }
  @Test
  public void test_dot() {
    Vec2L a = new Vec2L(1, 2);
    Vec2L b = new Vec2L(4, 5);
    long c = a.dot(b);
    assertEquals(14, c);
  }
  @Test
  public void test_norm() {
    Vec2L a = new Vec2L(1, 2);

    long c = a.normSq();
    double d = a.norm();
    assertEquals(5, c);
    assertEquals(Math.sqrt(5), d, 1e-12);
  }
  @Test
  public void test_dist() {
    Vec2L a = new Vec2L(1, 2);
    Vec2L b = new Vec2L(2, 1);

    long d = a.distSq(b);
    double e = a.dist(b);
    assertEquals(2, d);
    assertEquals(Math.sqrt(2), e, 1e-12);
  }
}
