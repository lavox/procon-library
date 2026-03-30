package graph;

import java.util.ArrayDeque;
import java.util.Arrays;

public class Bfs01 extends ShortestPath {
	public static Dist search(Graph g, int s) {
		return search(g, s, (from, to, id, cost) -> cost > 0);
	}
	public static Dist search(Graph g, int s, EdgePredicate edge1) {
		return search(g, new int[] {s}, edge1, null);
	}
	public static Dist search(Graph g, int[] starts, EdgePredicate edge1, EdgePredicate canPass) {
		int n = g.size();
		Dist d = new Dist(n);
		final long[] dist = d.dist;
		final int[] parent = d.parent;
		Arrays.fill(dist, INF);
		Arrays.fill(parent, -1);
		ArrayDeque<Step> queue = new ArrayDeque<>();
		for (int s: starts) {
			dist[s] = 0;
			queue.addLast(new Step(s, 0, -1));
		}

		Graph.EdgeConsumer action = (from, to, id, cost) -> {
			if (canPass != null && !canPass.test(from, to, id, cost)) return;
			int dd = edge1.test(from, to, id, cost) ? 1 : 0;
			long nd = dist[from] + dd;
			if (nd < dist[to]) {
				dist[to] = nd;
				parent[to] = from;
				if (dd == 0) {
					queue.addFirst(new Step(to, nd, from));
				} else {
					queue.addLast(new Step(to, nd, from));
				}
			}
		};

		while (queue.size() > 0) {
			Step st = queue.pollFirst();
			if (dist[st.p] != st.d) continue;
			g.forEachEdge(st.p, action);
		}
		return d;
	}
}
