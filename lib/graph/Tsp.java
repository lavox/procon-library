package graph;

import java.util.Arrays;

public class Tsp extends ShortestPath {
	public static TspRoute search(long[][] distMap) {
		return search(distMap, 0, false);
	}
	public static TspRoute search(long[][] distMap, int s, boolean oneway) {
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
}
