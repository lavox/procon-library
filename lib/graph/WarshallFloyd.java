import java.util.Arrays;

class WarshallFloyd {
	public static long INF = Long.MAX_VALUE >> 1;
	public static long[][] createInitTable(int n) {
		long[][] d = new long[n][n];
		for (int i = 0; i < n; i++) {
			Arrays.fill(d[i], INF);
			d[i][i] = 0;
		}
		return d;
	}
	public static long[][] warshallFloyd(long[][] d) {
		int n = d.length;
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (d[i][k] == INF || d[k][j] == INF) continue;
					d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
				}
			}
		}
		return d;
	}
	public static boolean hasNegativeLoop(long[][] d) {
		for (int i = 0; i < d.length; i++) {
			if (d[i][i] < 0) return true;
		}
		return false;
	}
}
