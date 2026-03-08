package graph;

import java.util.Arrays;
import java.util.BitSet;

public class BellmanFord {
	public static final long INF = Long.MAX_VALUE;
	public static final long NINF = Long.MIN_VALUE;
	public static long[] bellmanFord(GenericGraph<? extends CostEdge> g, int s) {
		int n = g.size();
		long[] ret = new long[n];
		Arrays.fill(ret, INF);
		ret[s] = 0;
		for (int i = 0; i < n - 1; i++) {
			boolean update = false;
			for (int v = 0; v < n; v++) {
				if (ret[v] == INF) continue;
				for (CostEdge e: g.edges(v)) {
					if (ret[v] + e.cost() < ret[e.to()]) {
						ret[e.to()] = ret[v] + e.cost();
						update = true;
					}
				}
			}
			if (!update) return ret;
		}
		BitSet visited = new BitSet(n);
		int[] queue = new int[n];
		int wi = 0;
		boolean update = false;
		for (int v = 0; v < n; v++) {
			if (ret[v] == INF) continue;
			for (CostEdge e: g.edges(v)) {
				if (ret[e.to()] == NINF) continue;
				if (ret[v] == NINF || ret[v] + e.cost() < ret[e.to()]) {
					ret[e.to()] = NINF;
					visited.set(e.to());
					queue[wi++] = e.to();
					update = true;
				}
			}
		}
		if (!update) return ret;
		int ri = 0;
		while (ri < wi) {
			int v = queue[ri++];
			for (CostEdge e: g.edges(v)) {
				if (visited.get(e.to())) continue;
				visited.set(e.to());
				ret[e.to()] = NINF;
				queue[ri++] = e.to();
			}
		}
		return ret;
	}
	public static boolean hasNegativeLoop(long[] dist) {
		for (long d: dist) {
			if (d == NINF) return true;
		}
		return false;
	}
}
