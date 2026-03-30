package graph;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Dijkstra extends ShortestPath {
	public static Dist search(Graph g, int s) {
		return search(g, new int[] {s}, null);
	}
	public static Dist search(Graph g, int[] starts, EdgePredicate canPass) {
		int n = g.size();
		Dist d = new Dist(n);
		final long[] dist = d.dist;
		final int[] parent = d.parent;
		Arrays.fill(dist, INF);
		Arrays.fill(parent, -1);
		PriorityQueue<Step> queue = new PriorityQueue<>();
		for (int s: starts) {
			dist[s] = 0;
			queue.add(new Step(s, 0, -1));
		}

		Graph.EdgeConsumer action = (from, to, id, cost) -> {
			if (canPass != null && !canPass.test(from, to, id, cost)) return;
			long nd = dist[from] + cost;
			if (nd < dist[to]) {
				dist[to] = nd;
				parent[to] = from;
				queue.add(new Step(to, nd, from));
			}
		};
		while (queue.size() > 0) {
			Step st = queue.poll();
			if (dist[st.p] != st.d) continue;
			g.forEachEdge(st.p, action);
		}
		return d;
	}
}
