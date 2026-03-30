package graph;

import java.util.BitSet;

import primitive.IntArrayList;

public class BellmanFord extends ShortestPath {
	public static Dist search(Graph g, int s) {
		return search(g, new int[] {s});
	}
	public static Dist search(Graph g, int[] starts) {
		int n = g.size();
		Dist d = new Dist(n);
		final long[] dist = d.dist;
		final int[] parent = d.parent;
		for (int s: starts) {
			dist[s] = 0;
		}

		boolean[] update = new boolean[] {false};
		Graph.EdgeConsumer action1 = (from, to, id, cost) -> {
			if (dist[from] + cost < dist[to]) {
				dist[to] = dist[from] + cost;
				parent[to] = from;
				update[0] = true;
			}
		};
		for (int i = 0; i < n - 1; i++) {
			update[0] = false;
			for (int v = 0; v < n; v++) {
				if (dist[v] == INF) continue;
				g.forEachEdge(v, action1);
			}
			if (!update[0]) return d;
		}

		BitSet visited = new BitSet(n);
		IntArrayList queue = new IntArrayList(n);
		update[0] = false;
		Graph.EdgeConsumer action2 = (from, to, id, cost) -> {
			if (dist[to] == NINF) return;
			if (dist[from] == NINF || dist[from] + cost < dist[to]) {
				dist[to] = NINF;
				parent[to] = from;
				d.hasNegativeLoop = true;
				visited.set(to);
				queue.add(to);
				update[0] = true;
			}
		};
		for (int v = 0; v < n; v++) {
			if (dist[v] == INF) continue;
			g.forEachEdge(v, action2);
		}
		if (!update[0]) return d;
		int ri = 0;
		Graph.EdgeConsumer action3 = (from, to, id, cost) -> {
			if (visited.get(to)) return;
			visited.set(to);
			dist[to] = NINF;
			parent[to] = from;
			queue.add(to);
		};
		while (ri < queue.size()) {
			int v = queue.get(ri++);
			g.forEachEdge(v, action3);
		}
		return d;
	}
}
