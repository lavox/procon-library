import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

class LcaTree {
	private int _n = 0;
	private ArrayList<Integer>[] edges = null;
	private int[] depth = null;
	private int[][] anc = null;
	private int K = 1;
	private int[] kmax = null;

	public LcaTree(int n) {
		this._n = n;
		this.K = 1;
		while ((1 << this.K) < n) this.K++;
		this.edges = new ArrayList[n];
		for (int i = 0; i < n; i++) this.edges[i] = new ArrayList<>();
	}
	public void addEdge(int u, int v) {
		edges[u].add(v);
		edges[v].add(u);
	}
	public void build(int root) {
		depth = new int[_n];
		anc = new int[K][_n];
		kmax = new int[_n];
		Arrays.fill(depth, -1);
		for (int k = 0; k < K; k++) Arrays.fill(anc[k], -1);
		ArrayDeque<Integer> stack = new ArrayDeque<>();
		depth[root] = 0;
		stack.add(root);
		while (stack.size() > 0) {
			int pos = stack.pollFirst();
			kmax[pos] = 31 - Integer.numberOfLeadingZeros(depth[pos]);
			for (int child: edges[pos]) {
				if (depth[child] != -1) continue;
				depth[child] = depth[pos] + 1;
				anc[0][child] = pos;
				stack.addLast(child);
			}
		}
		for (int k = 1; k < K; k++) {
			for (int i = 0; i < _n; i++) {
				if (anc[k - 1][i] >= 0) {
					anc[k][i] = anc[k - 1][anc[k - 1][i]];
				}
			}
		}
	}
	public int lca(int u, int v) {
		if (depth[u] < depth[v]) return lca(v, u);
		for (int dd = depth[u] - depth[v], k = 0; dd > 0; dd &= ~(1 << k)) {
			k = Integer.numberOfTrailingZeros(dd);
			u = anc[k][u];
		}
		if (u == v) return u;
		for (int k = kmax[u]; k >= 0; k--) {
			if (anc[k][u] != anc[k][v]) {
				u = anc[k][u];
				v = anc[k][v];
			}
		}
		return anc[0][u];
	}
	public int depth(int u) {
		return depth[u];
	}
	public int ancestor(int u, int d) {
		if (d > depth[u]) return -1;
		while (d > 0) {
			int k = Integer.numberOfTrailingZeros(d);
			u = anc[k][u];
			d &= ~(1 << k);
		}
		return u;
	}
}
