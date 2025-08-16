package data_structure;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UnionFindTest {
  @Test
  public void test() {
    int K = 5;
    UnionFind uf = new UnionFind(K);
    uf.unite(0, 2);
    assertFalse(uf.isSame(0, 1));
    assertTrue(uf.isSame(0, 2));
    assertEquals(2, uf.size(0));
    assertEquals(1, uf.size(1));
    
    uf.unite(2, 3);
    assertEquals(uf.size(0), 3);
    assertEquals(uf.root(0), uf.root(3));
    assertNotEquals(uf.root(1), uf.root(3));
  }
}
