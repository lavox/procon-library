package graph;

import java.util.Arrays;

import primitive.IntArrayList;

public class ShortestPath {
	public static final long INF = Long.MAX_VALUE;
	public static final long NINF = Long.MIN_VALUE;

	public static class DistMap {
		public final int n;
		public final long[][] dist;
		protected DistMap(int n) {
			this.n = n;
			this.dist = new long[n][n];
		}
		protected void init() {
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
		protected boolean hasNegativeLoop = false;
		protected Dist(int n) {
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

	protected static class Step implements Comparable<Step> {
		protected final int p;
		protected final long d;
		protected final int parent;
		protected Step(int p, long d, int parent) {
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

	@FunctionalInterface
	public interface EdgePredicate {
		public boolean test(int from, int to, int id, long cost);
	}
}
