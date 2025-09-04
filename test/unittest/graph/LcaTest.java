package graph;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LcaTest {
  @Test
  public void test() {
    int n = 11;
    Graph g = new Graph(n);
    g.addUndirEdge(10, 9);
    g.addUndirEdge(10, 8);
    g.addUndirEdge(9, 7);
    g.addUndirEdge(9, 6);
    g.addUndirEdge(8, 5);
    g.addUndirEdge(7, 4);
    g.addUndirEdge(7, 3);
    g.addUndirEdge(6, 2);
    g.addUndirEdge(6, 1);
    g.addUndirEdge(4, 0);
    Lca lca = new Lca(g, 10);
    int[] d = new int[]{4,3,3,3,3,2,2,2,1,1,0};
    for (int i = 0; i < n; i++) {
      assertEquals(d[i], lca.depth(i));
    }
    assertEquals(0, lca.ancestor(0, 0));
    assertEquals(4, lca.ancestor(0, 1));
    assertEquals(7, lca.ancestor(0, 2));
    assertEquals(9, lca.ancestor(0, 3));
    assertEquals(10, lca.ancestor(0, 4));
    assertEquals(-1, lca.ancestor(0, 5));
    assertEquals(-1, lca.ancestor(0, 100));
  }
}
