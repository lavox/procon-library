package graph;

public class WarshallFloyd extends ShortestPath {
	public static DistMap createMap(int n) {
		DistMap d = new DistMap(n);
		d.init();
		return d;
	}
	public static DistMap search(DistMap d) {
		int n = d.n;
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (d.dist[i][k] == INF || d.dist[k][j] == INF) continue;
					d.dist[i][j] = Math.min(d.dist[i][j], d.dist[i][k] + d.dist[k][j]);
				}
			}
		}
		return d;
	}
	public static DistMap updateEdge(DistMap d, int from, int to, long dist) {
		if (d.dist[from][to] <= dist) return d;
		d.dist[from][to] = dist;
		int n = d.n;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (d.dist[i][from] == INF || d.dist[to][j] == INF) continue;
				d.dist[i][j] = Math.min(d.dist[i][j], d.dist[i][from] + dist + d.dist[to][j]);
			}
		}
		return d;
	}
}
