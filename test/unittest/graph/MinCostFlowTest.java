package graph;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class MinCostFlowTest {
  @Test
  public void test() {
    int N = 10;
    int[] u = new int[] {0,0,0,0,0,3,3,4,4,5,5,6,6,6,7};
    int[] v = new int[] {3,4,6,8,9,1,5,5,9,1,9,1,2,9,6};
    int[] cap = new int[] {9,15,9,12,19,10,8,9,11,10,7,9,12,11,3};
    int[] cost = new int[] {12,0,12,16,5,16,13,10,7,4,19,17,13,11,14};

    MinCostFlow mcf = createGraph(N, u, v, cap, cost);
    ArrayList<MinCostFlow.Flow> slope = mcf.slope(0, N - 1);
    for (int i = 0; i < slope.size() - 1; i++) {
      MinCostFlow.Flow f0 = slope.get(i);
      MinCostFlow.Flow f1 = slope.get(i + 1);
      for (long f = f0.flow; f <= f1.flow; f++) {
        MinCostFlow _mcf = createGraph(N, u, v, cap, cost);
        MinCostFlow.Flow flow = _mcf.flow(0, N - 1, f);
        assertEquals("flow check", f, flow.flow);
        assertEquals("cost check", (f0.cost * (f1.flow - f) + f1.cost * (f - f0.flow)) / (f1.flow - f0.flow), flow.cost);
      }
    }
  }

  MinCostFlow createGraph(int N, int[] u, int[] v, int[] cap, int[] cost) {
    MinCostFlow mcf = new MinCostFlow(N);
    for (int i = 0; i < u.length; i++) {
      mcf.addEdge(u[i], v[i], cap[i], cost[i]);
    }
    return mcf;
  }
}
