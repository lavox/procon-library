package math;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class MatrixTest {
  @Test
  public void test_add() {
    long[][] a_arr = new long[][] {
      {1, 2, 3},
      {4, 5, 6},
    };
    long[][] b_arr = new long[][] {
      {10, 20, 30},
      {40, 50, 60}
    };
    Matrix a = new Matrix(a_arr);
    Matrix b = new Matrix(b_arr);
    Matrix c = a.add(b);
    long[][] expected = new long[][] {
      {11, 22, 33},
      {44, 55, 66}
    };
    for (int i = 0; i < a_arr.length; i++) {
      assertArrayEquals(expected[i], c.a[i]);
    }
  }
  @Test
  public void test_addMod() {
    long[][] a_arr = new long[][] {
      {1, 2, 3},
      {4, 5, 6},
    };
    long[][] b_arr = new long[][] {
      {10, 20, 30},
      {40, 50, 60}
    };
    Matrix a = new Matrix(a_arr);
    Matrix b = new Matrix(b_arr);
    Matrix c = a.addMod(b, 7);
    long[][] expected = new long[][] {
      {4, 1, 5},
      {2, 6, 3}
    };
    for (int i = 0; i < a_arr.length; i++) {
      assertArrayEquals(expected[i], c.a[i]);
    }
  }
  @Test
  public void test_mul() {
    long[][] a_arr = new long[][] {
      {1, 2, 3},
      {4, 5, 6},
    };
    long[][] b_arr = new long[][] {
      {10, 40},
      {20, 50},
      {30, 60},
    };
    Matrix a = new Matrix(a_arr);
    Matrix b = new Matrix(b_arr);
    Matrix c = a.mul(b);
    long[][] expected = new long[][] {
      {140, 320},
      {320, 770}
    };
    for (int i = 0; i < a_arr.length; i++) {
      assertArrayEquals(expected[i], c.a[i]);
    }
  }
  @Test
  public void test_mulVec() {
    long[][] a_arr = new long[][] {
      {1, 2, 3},
      {4, 5, 6},
    };
    long[] b_arr = new long[] {2, 3, 4};
    Matrix a = new Matrix(a_arr);
    Vec b = new Vec(b_arr);
    Vec c = a.mul(b);
    long[] expected = new long[] {20, 47};
    assertArrayEquals(expected, c.a);
  }
  @Test
  public void test_mulVecMod() {
    long[][] a_arr = new long[][] {
      {1, 2, 3},
      {4, 5, 6},
    };
    long[] b_arr = new long[] {2, 3, 4};
    Matrix a = new Matrix(a_arr);
    Vec b = new Vec(b_arr);
    Vec c = a.mulMod(b, 7);
    long[] expected = new long[] {6, 5};
    assertArrayEquals(expected, c.a);
  }
  @Test
  public void test_pow() {
    long[][] a_arr = new long[][] {
      {1, 2},
      {3, 4},
    };
    Matrix a = new Matrix(a_arr);
    Matrix b = a.pow(3);
    long[][] expected = new long[][] {
      {37, 54},
      {81, 118},
    };
    for (int i = 0; i < b.a.length; i++) {
      assertArrayEquals(expected[i], b.a[i]);
    }
  }
}
