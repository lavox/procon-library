package graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.BitSet;
import java.util.PriorityQueue;
import java.util.function.Predicate;

import primitive.IntArrayList;

public class ShortestPath {
	public static final long INF = Long.MAX_VALUE;
	public static final long NINF = Long.MIN_VALUE;

	// Dijkstra
	public static <E extends CostEdge> Dist dijkstra(GenericGraph<E> g, int s) {
		return dijkstra(g, new int[] {s}, null);
	}
	public static <E extends CostEdge> Dist dijkstra(GenericGraph<E> g, int[] starts, Predicate<E> canPass) {
		int n = g.size();
		Dist d = new Dist(n);
		final long[] dist = d.dist;
		final int[] parent = d.parent;
		PriorityQueue<Step> queue = new PriorityQueue<>();
		Step[] step = new Step[n];
		for (int s: starts) {
			step[s] = new Step(s, 0, -1);
			queue.add(step[s]);
		}
		while (queue.size() > 0) {
			Step st = queue.poll();
			if (step[st.p] != st) continue;
			dist[st.p] = st.d;
			parent[st.p] = st.parent;
			for (E e: g.edges(st.p)) {
				if (canPass != null && !canPass.test(e)) continue;
				int np = e.to();
				long nd = st.d + e.cost();
				if (step[np] == null || nd < step[np].d) {
					step[np] = new Step(np, nd, st.p);
					queue.add(step[np]);
				}
			}
		}
		return d;
	}

	// BFS
	public static <E extends Edge> Dist bfs(GenericGraph<E> g, int s) {
		return bfs(g, new int[] {s}, null);
	}
	public static <E extends Edge> Dist bfs(GenericGraph<E> g, int[] starts, Predicate<E> canPass) {
		int n = g.size();
		Dist d = new Dist(n);
		final long[] dist = d.dist;
		final int[] parent = d.parent;
		int[] queue = new int[n];
		int ri = 0;
		int wi = 0;
		for (int s: starts) {
			dist[s] = 0;
			queue[wi++] = s;
		}
		while (ri < wi) {
			int p = queue[ri++];
			for (E e: g.edges(p)) {
				if (canPass != null && !canPass.test(e)) continue;
				int np = e.to();
				if (dist[np] != INF) continue;
				dist[np] = dist[p] + 1;
				parent[np] = p;
				queue[wi++] = np;
			}
		}
		return d;
	}

	// 01-BFS
	public static <E extends Edge> Dist bfs01(GenericGraph<E> g, int s, Predicate<E> edge1) {
		return bfs01(g, new int[] {s}, edge1, null);
	}
	public static <E extends Edge> Dist bfs01(GenericGraph<E> g, int[] starts, Predicate<E> edge1, Predicate<E> canPass) {
		int n = g.size();
		Dist d = new Dist(n);
		final long[] dist = d.dist;
		final int[] parent = d.parent;
		ArrayDeque<Step> queue = new ArrayDeque<>();
		Step[] step = new Step[n];
		for (int s: starts) {
			step[s] = new Step(s, 0, -1);
			queue.addLast(step[s]);
		}
		while (queue.size() > 0) {
			Step st = queue.pollFirst();
			if (step[st.p] != st) continue;
			dist[st.p] = st.d;
			parent[st.p] = st.parent;
			for (E e: g.edges(st.p)) {
				if (canPass != null && !canPass.test(e)) continue;
				int dd = edge1.test(e) ? 1 : 0;
				int np = e.to();
				long nd = st.d + dd;
				if (step[np] == null || nd < step[np].d) {
					step[np] = new Step(np, nd, st.p);
					if (dd == 0) {
						queue.addFirst(step[np]);
					} else {
						queue.addLast(step[np]);
					}
				}
			}
		}
		return d;
	}

	// Bellman Ford
	public static <E extends CostEdge> Dist bellmanFord(GenericGraph<E> g, int s) {
		return bellmanFord(g, new int[] {s});
	}
	public static <E extends CostEdge> Dist bellmanFord(GenericGraph<E> g, int[] starts) {
		int n = g.size();
		Dist d = new Dist(n);
		final long[] dist = d.dist;
		final int[] parent = d.parent;
		for (int s: starts) {
			dist[s] = 0;
		}
		for (int i = 0; i < n - 1; i++) {
			boolean update = false;
			for (int v = 0; v < n; v++) {
				if (dist[v] == INF) continue;
				for (E e: g.edges(v)) {
					int np = e.to();
					if (dist[v] + e.cost() < dist[np]) {
						dist[np] = dist[v] + e.cost();
						parent[np] = v;
						update = true;
					}
				}
			}
			if (!update) return d;
		}
		BitSet visited = new BitSet(n);
		int[] queue = new int[n];
		int wi = 0;
		boolean update = false;
		for (int v = 0; v < n; v++) {
			if (dist[v] == INF) continue;
			for (E e: g.edges(v)) {
				int np = e.to();
				if (dist[np] == NINF) continue;
				if (dist[v] == NINF || dist[v] + e.cost() < dist[np]) {
					dist[np] = NINF;
					parent[np] = v;
					d.hasNegativeLoop = true;
					visited.set(np);
					queue[wi++] = np;
					update = true;
				}
			}
		}
		if (!update) return d;
		int ri = 0;
		while (ri < wi) {
			int v = queue[ri++];
			for (E e: g.edges(v)) {
				int np = e.to();
				if (visited.get(np)) continue;
				visited.set(np);
				dist[np] = NINF;
				parent[np] = v;
				queue[ri++] = np;
			}
		}
		return d;
	}

	// Warshall Floyd
	public static DistMap wfCreateMap(int n) {
		DistMap d = new DistMap(n);
		d.init();
		return d;
	}
	public static DistMap warshallFloyd(DistMap d) {
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
	public static DistMap wfUpdateEdge(DistMap d, int from, int to, long dist) {
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

	// TSP
	public static TspRoute tsp(long[][] distMap) {
		return tsp(distMap, 0, false);
	}
	public static TspRoute tsp(long[][] distMap, int s, boolean oneway) {
		int n = distMap.length;
		long[][] dp = new long[n][1 << n];
		for (int i = 0; i < n; i++) Arrays.fill(dp[i], INF);
		dp[s][1 << s] = 0;
		int full = (1 << n) - 1;
		for (int bit = 0; bit <= full; bit++) {
			for (int _b = bit, i = 0; _b != 0; _b &= ~(1 << i)) {
				i = Integer.numberOfTrailingZeros(_b);
				if (dp[i][bit] == INF) continue;
				for (int rb = full & ~bit, j = 0; rb != 0; rb &= ~(1 << j)) {
					j = Integer.numberOfTrailingZeros(rb);
					if (distMap[i][j] == INF) continue;
					dp[j][bit | (1 << j)] = Math.min(dp[j][bit | (1 << j)], dp[i][bit] + distMap[i][j]);
				}
			}
		}
		TspRoute ret = new TspRoute(n, distMap);
		for (int g = 0; g < n; g++) {
			long d = dp[g][full];
			if (d == INF) continue;
			if (!oneway) {
				if (distMap[g][s] == INF) continue;
				d += distMap[g][s];
			}
			if (d < ret.dist) {
				ret.dist = d;
				ret.g = g;
			}
		}
		ret.dp = dp;
		return ret;
	}	

	public static class DistMap {
		public final int n;
		public final long[][] dist;
		private DistMap(int n) {
			this.n = n;
			this.dist = new long[n][n];
		}
		private void init() {
			for (int i = 0; i < n; i++) {
				Arrays.fill(dist[i], INF);
				dist[i][i] = 0;
			}
		}
		public boolean hasNegativeLoop() {
			for (int i = 0; i < n; i++) {
				if (dist[i][i] < 0) return true;
			}
			return false;
		}
	}
	public static class Dist {
		public int n = 0;
		public long[] dist = null;
		public int[] parent = null;
		private boolean hasNegativeLoop = false;
		private Dist(int n) {
			this.n = n;
			this.dist = new long[n];
			this.parent = new int[n];
			Arrays.fill(dist, INF);
			Arrays.fill(parent, -1);
		}
		public int[] path(int t) {
			IntArrayList path = new IntArrayList();
			int cur = t;
			while (cur != -1) {
				path.add(cur);
				cur = parent[cur];
			}
			int ps = path.size();
			int[] ret = new int[ps];
			for (int i = 0; i < ps; i++) ret[i] = path.get(ps - i- 1);
			return ret;
		}
		public boolean hasNegativeLoop() {
			return hasNegativeLoop;
		}
	}
	public static class TspRoute {
		public int n;
		public int g = -1;
		public long dist = INF;
		public long[][] dp = null;
		public long[][] distMap = null;
		private TspRoute(int n, long[][] distMap) {
			this.n = n;
			this.distMap = distMap;
		}

		public int[] path() {
			if (dist == INF) return null;
			int[] path = new int[n];
			for (int i = n - 1, cur = g, bit = (1 << n) - 1; i >= 0; i--) {
				path[i] = cur;
				if (i == 0) break;
				long curDist = dp[cur][bit];
				bit &= ~(1 << cur);
				for (int _b = bit, prev = 0; _b != 0 ; _b &= ~(1 << prev)) {
					prev = Integer.numberOfTrailingZeros(_b);
					if (dp[prev][bit] != INF && distMap[prev][cur] != INF && dp[prev][bit] + distMap[prev][cur] == curDist) {
						cur = prev;
						break;
					}
				}
			}
			return path;
		}
	}

	private static class Step implements Comparable<Step> {
		private final int p;
		private final long d;
		private final int parent;
		private Step(int p, long d, int parent) {
			this.p = p;
			this.d = d;
			this.parent = parent;
		}
		@Override
		public int compareTo(Step o) {
			if (d != o.d) {
				return Long.compare(d, o.d);
			} else {
				return Integer.compare(p, o.p);
			}
		}
	}
}
