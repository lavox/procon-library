import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LcaTreeTest {
  @Test
  public void test() {
    int n = 11;
    LcaTree tree = new LcaTree(n);
    tree.addEdge(10, 9);
    tree.addEdge(10, 8);
    tree.addEdge(9, 7);
    tree.addEdge(9, 6);
    tree.addEdge(8, 5);
    tree.addEdge(7, 4);
    tree.addEdge(7, 3);
    tree.addEdge(6, 2);
    tree.addEdge(6, 1);
    tree.addEdge(4, 0);
    tree.build(10);
    int[] d = new int[]{4,3,3,3,3,2,2,2,1,1,0};
    for (int i = 0; i < n; i++) {
      assertEquals(d[i], tree.depth(i));
    }
    assertEquals(0, tree.ancestor(0, 0));
    assertEquals(4, tree.ancestor(0, 1));
    assertEquals(7, tree.ancestor(0, 2));
    assertEquals(9, tree.ancestor(0, 3));
    assertEquals(10, tree.ancestor(0, 4));
    assertEquals(-1, tree.ancestor(0, 5));
    assertEquals(-1, tree.ancestor(0, 100));
  }
}
