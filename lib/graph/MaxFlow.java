package graph;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

public class MaxFlow {
	private int _n = 0;
	private ArrayList<_Edge>[] g = null;
	private ArrayList<_Edge> edge = null;
	private static final long INF = Long.MAX_VALUE;

	public MaxFlow(int n) {
		this._n = n;
		this.g = new ArrayList[n];
		for (int i = 0; i < n; i++) g[i] = new ArrayList<>();
		this.edge = new ArrayList<>();
	}
	public int addEdge(int from, int to, long cap) {
		assert 0 <= from && from < _n;
		assert 0 <= to && to < _n;
		assert 0 <= cap;
		int m = edge.size();
		_Edge _e = new _Edge(to, cap);
		_Edge _re = new _Edge(from, 0);
		_e.setReverse(_re);
		g[from].add(_e);
		g[to].add(_re);
		edge.add(_e);
		return m;
	}
	public Edge getEdge(int i) {
		assert 0 <= i && i < edge.size();
		_Edge _e = edge.get(i);
		_Edge _re = _e.rev;
		return new Edge(_re.to, _e.to, _e.cap + _re.cap, _re.cap);
	}
	public ArrayList<Edge> edges() {
		ArrayList<Edge> result = new ArrayList<>(edge.size());
		for (int i = 0; i < edge.size(); i++) result.add(getEdge(i));
		return result;
	}
	public void changeEdge(int i, long newCap, long newFlow) {
		assert 0 <= i && i < edge.size();
		assert 0 <= newFlow && newFlow <= newCap;
		_Edge _e = edge.get(i);
		_Edge _re = _e.rev;
		_e.cap = newCap - newFlow;
		_re.cap = newFlow;
	}
	public long flow(int s, int t) {
		return flow(s, t, INF);
	}
	public long flow(int s, int t, long flowLimit) {
		assert 0 <= s && s < _n;
		assert 0 <= t && t < _n;
		assert s != t;

		int[] level = new int[_n];
		int[] iter = new int[_n];
		int[] que = new int[_n];

		long flow = 0;
		while (flow < flowLimit) {
			bfs(s, t, que, level);
			if (level[t] == -1) break;
			Arrays.fill(iter, 0);
			long f = dfs(s, level, iter, t, flowLimit - flow);
			if (f == 0) break;
			flow += f;
		}
		return flow;
	}
	private void bfs(int s, int t, int[] que, int[] level) {
		Arrays.fill(level, -1);
		level[s] = 0;
		int qr = 0;
		int qw = 0;
		que[qw++] = s;
		while ( qw > qr ) {
			int v = que[qr++];
			for (_Edge e : g[v]) {
				if (e.cap == 0 || level[e.to] >= 0) continue;
				level[e.to] = level[v] + 1;
				if (e.to == t) return;
				que[qw++] = e.to;
			}
		}
	}
	private long dfs(int s, int[] level, int[] iter, int v, long up) {
		if (v == s) return up;
		long res = 0;
		int level_v = level[v];
		for (; iter[v] < g[v].size(); iter[v]++) {
			_Edge e = g[v].get(iter[v]);
			_Edge re = e.rev;
			if (level_v <= level[e.to] || re.cap == 0) continue;
			long d = dfs(s, level, iter, e.to, Math.min(up - res, re.cap));
			if (d <= 0) continue;
			e.cap += d;
			re.cap -= d;
			res += d;
			if (res == up) return res;
		}
		level[v] = _n;
		return res;
	}
	public BitSet minCut(int s) {
		BitSet visited = new BitSet(_n);
		int[] que = new int[_n];
		int qr = 0;
		int qw = 0;
		que[qw++] = s;
		while (qw > qr) {
			int p = que[qr++];
			visited.set(p);
			for (_Edge e: g[p]) {
				if (e.cap != 0 && !visited.get(e.to)) {
					visited.set(e.to);
					que[qw++] = e.to;
				}
			}
		}
		return visited;
	}
	public class Edge {
		int from = 0;
		int to = 0;
		long cap = 0;
		long flow = 0;
		Edge(int from, int to, long cap, long flow) {
			this.from = from;
			this.to = to;
			this.cap = cap;
			this.flow = flow;
		}
	}
	private class _Edge {
		int to = 0;
		long cap = 0;
		_Edge rev = null;
		private _Edge(int to, long cap) {
			this.to = to;
			this.cap = cap;
		}
		private void setReverse(_Edge rev) {
			this.rev = rev;
			rev.rev = this;
		}
	}
}
