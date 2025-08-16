package math;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ModIntTest {
  @Test
  public void test() {
    int K = 5;
    ModOperation mop = new ModOperation(5);

    ModInt[] a = new ModInt[K];
    for (int i = 0; i < K; i++) {
      a[i] = mop.create(i);
    }

    assertEquals(5, a[0].mod());
    assertEquals(1, a[1].val());

    assertEquals(1, a[2].add(a[4]).val());
    assertEquals(3, a[1].add(a[3], a[4]).val());
    assertEquals(4, a[1].add(a[1], a[3], a[4]).val());
    assertEquals(1, a[1].add(a[1], a[2], a[3], a[4]).val());

    assertEquals(1, a[2].add(9).val());
    assertEquals(3, a[1].add(-2, -1).val());
    assertEquals(4, a[1].add(6, -2, -1).val());
    assertEquals(1, a[1].add(21, -3, -7, -16).val());

    assertEquals(4, a[2].sub(a[3]).val());
    assertEquals(4, a[2].sub(-2).val());

    assertEquals(1, a[2].mul(a[3]).val());
    assertEquals(4, a[2].mul(a[3], a[4]).val());
    assertEquals(3, a[2].mul(a[3], a[4], a[2]).val());
    assertEquals(4, a[2].mul(a[3], a[4], a[2], a[3]).val());
    assertEquals(1, a[2].mul(8).val());
    assertEquals(4, a[2].mul(-7, -1).val());
    assertEquals(3, a[2].mul(13, -6, -3).val());
    assertEquals(4, a[2].mul(-12, 9, 12, 23).val());

    assertEquals(4, a[2].div(a[3]).val());
    assertEquals(4, a[2].div(-2).val());

    assertEquals(3, a[2].inv().val());

    ModInt x = mop.create(3);
    assertEquals(1, x.addAsn(a[3]).val());
    assertEquals(1, x.val());

    x = mop.create(3);
    assertEquals(1, x.subAsn(a[2]).val());
    assertEquals(1, x.val());

    x = mop.create(3);
    assertEquals(4, x.mulAsn(a[3]).val());
    assertEquals(4, x.val());

    x = mop.create(3);
    assertEquals(4, x.divAsn(a[2]).val());
    assertEquals(4, x.val());
  }

  @Test
  public void testFraction() {
    int K = 5;
    ModOperation mop = new ModOperation(5, true);

    ModInt[] a = new ModInt[K];
    for (int i = 0; i < K; i++) {
      a[i] = mop.create(i);
      assertTrue(a[i] instanceof ModFraction);
    }

    assertEquals(5, a[0].mod());
    assertEquals(1, a[1].val());

    assertEquals(1, a[2].add(a[4]).val());
    assertEquals(3, a[1].add(a[3], a[4]).val());
    assertEquals(4, a[1].add(a[1], a[3], a[4]).val());
    assertEquals(1, a[1].add(a[1], a[2], a[3], a[4]).val());

    assertEquals(1, a[2].add(9).val());
    assertEquals(3, a[1].add(-2, -1).val());
    assertEquals(4, a[1].add(6, -2, -1).val());
    assertEquals(1, a[1].add(21, -3, -7, -16).val());

    assertEquals(4, a[2].sub(a[3]).val());
    assertEquals(4, a[2].sub(-2).val());

    assertEquals(1, a[2].mul(a[3]).val());
    assertEquals(4, a[2].mul(a[3], a[4]).val());
    assertEquals(3, a[2].mul(a[3], a[4], a[2]).val());
    assertEquals(4, a[2].mul(a[3], a[4], a[2], a[3]).val());
    assertEquals(1, a[2].mul(8).val());
    assertEquals(4, a[2].mul(-7, -1).val());
    assertEquals(3, a[2].mul(13, -6, -3).val());
    assertEquals(4, a[2].mul(-12, 9, 12, 23).val());

    assertEquals(4, a[2].div(a[3]).val());
    assertEquals(4, a[2].div(-2).val());

    assertEquals(3, a[2].inv().val());

    ModInt x = mop.create(3);
    assertEquals(1, x.addAsn(a[3]).val());
    assertEquals(1, x.val());

    x = mop.create(3);
    assertEquals(1, x.subAsn(a[2]).val());
    assertEquals(1, x.val());

    x = mop.create(3);
    assertEquals(4, x.mulAsn(a[3]).val());
    assertEquals(4, x.val());

    x = mop.create(3);
    assertEquals(4, x.divAsn(a[2]).val());
    assertEquals(4, x.val());
  }

  @Test
  public void testFactorial() {
    int K = 11;
    ModOperation mop = new ModOperation(K);
    mop.prepareFactorial(8);
    assertEquals(5, mop.fact(8));
    assertEquals(9, mop.factInv(8));
    assertEquals(4, mop.combi(8, 4));
    assertEquals(8, mop.perm(8, 4));
  }
}
