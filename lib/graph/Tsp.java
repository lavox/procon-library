package graph;

import java.util.Arrays;

public class Tsp {
	public static final long INF = Long.MAX_VALUE;
	public static MinRoute search(long[][] distMap) {
		return search(distMap, 0, false);
	}
	public static MinRoute search(long[][] distMap, int s, boolean oneway) {
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
		MinRoute ret = new MinRoute();
		for (int g = 0; g < n; g++) {
			long d = dp[g][full];
			if (!oneway) {
				if (distMap[g][s] == INF) continue;
				d += distMap[g][s];
			}
			if (d < ret.dist) {
				ret.dist = d;
				ret.g = g;
			}
		}
		ret.path = new int[n];
		for (int i = n - 1, cur = ret.g, bit = full; i >= 0; i--) {
			ret.path[i] = cur;
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
		ret.dp = dp;
		return ret;
	}
	public static class MinRoute {
		int g = -1;
		long dist = INF;
		int[] path = null;
		long[][] dp = null;
	}
}
