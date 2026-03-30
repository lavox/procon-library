package graph;
import java.util.Arrays;

// Ported to Java from the original C++ implementation by Atcoder.
// Original Source: https://github.com/atcoder/ac-library/blob/master/atcoder/scc.hpp
public class SccGraph {
	private int _n = 0;
	private SimpleGraph g = null;

	private int[] ids = null;
	private int group_num = 0;
	private int[][] groups = null;

	public SccGraph(int n) {
		this._n = n;
		this.g = new SimpleGraph(n);
	}
	public void addEdge(int from, int to) {
		g.addDirEdge(from, to);
	}

	public void decompose() {
		g.build();
		int now_ord = 0;
		int[] low = new int[_n];
		int[] ord = new int[_n];
		Arrays.fill(ord, -1);
		int[] sv = new int[_n];
		int[] si = new int[_n];
		int[] visited = new int[_n];
		ids = new int[_n];
		group_num = 0;
		int vi = 0;
		int s = -1;
		for (int i = 0; i < _n; i++) {
			if (ord[i] != -1) continue;

			sv[++s] = i;
			si[s] = 0;
			while ( s >= 0 ) {
				int v = sv[s];
				if (si[s] == 0) {
					low[v] = ord[v] = now_ord++;
					visited[vi++] = v;
				}
				if (si[s] < g.edgeSize(v)) {
					int to = g.edge(v, si[s]++).to;
					if (ord[to] == -1) {
						sv[++s] = to;
						si[s] = 0;
						continue;
					} else {
						low[v] = Math.min(low[v], ord[to]);
					}
					continue;
				}
				if (low[v] == ord[v]) {
					while (true) {
						int u = visited[--vi];
						ord[u] = _n;
						ids[u] = group_num;
						if (u == v) break;
					}
					group_num++;
				}
				if (s > 0) {
					low[sv[s - 1]] = Math.min(low[sv[s - 1]], low[v]);
				}
				s--;
			}
		}

		int[] count = new int[group_num];
		for (int i = 0; i < _n; i++) {
			ids[i] = group_num - 1 - ids[i];
			count[ids[i]]++;
		}
		groups = new int[group_num][];
		for (int i = 0; i < group_num; i++) {
			groups[i] = new int[count[i]];
		}
		int[] ci = new int[group_num];
		for (int i = 0; i < _n; i++) {
			groups[ids[i]][ci[ids[i]]++] = i;
		}
	}

	public int[] ids() {
		return ids;
	}
	public int id(int i) {
		return ids[i];
	}
	public int[][] groups() {
		return groups;
	}
}
