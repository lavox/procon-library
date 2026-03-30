package graph;

import java.util.Arrays;

import primitive.IntArrayList;

public class Bfs extends ShortestPath {
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
		IntArrayList queue = new IntArrayList(n);
		int ri = 0;
		for (int s: starts) {
			dist[s] = 0;
			queue.add(s);
		}
		
		Graph.EdgeConsumer action = (from, to, id, cost) -> {
			if (canPass != null && !canPass.test(from, to, id, cost)) return;
			if (dist[to] != INF) return;
			dist[to] = dist[from] + 1;
			parent[to] = from;
			queue.add(to);
		};

		while (ri < queue.size()) {
			int p = queue.get(ri++);
			g.forEachEdge(p, action);
		}
		return d;
	}
}
