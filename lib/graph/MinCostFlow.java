package graph;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MinCostFlow {
	private int _n = 0;
	private ArrayList<Edge> _edges = null;

	public MinCostFlow(int n) {
		this._n = n;
		this._edges = new ArrayList<>();
	}

	public int addEdge(int from, int to, long cap, long cost) {
		assert 0 <= from && from < _n;
		assert 0 <= to && to < _n;
		assert 0 <= cap;
		assert 0 <= cost;
		int m = _edges.size();
		_edges.add(new Edge(from, to, cap, cost));
		return m;
	}

	public Edge getEdge(int i) {
		assert 0 <= i && i < _edges.size();
		return _edges.get(i);
	}
	public ArrayList<Edge> edges() { return _edges; }

	public Flow flow(int s, int t) {
		return flow(s, t, Long.MAX_VALUE);
	}
	public Flow flow(int s, int t, long flowLimit) {
		ArrayList<Flow> f = slope(s, t, flowLimit);
		return f.get(f.size() - 1);
	}
	public ArrayList<Flow> slope(int s, int t) {
		return slope(s, t, Long.MAX_VALUE);
	}
	public ArrayList<Flow> slope(int s, int t, long flowLimit) {
		assert 0 <= s && s < _n;
		assert 0 <= t && t < _n;
		assert s != t;

		int m = _edges.size();
		ArrayList<_Edge>[] g = new ArrayList[_n];
		_Edge[] edges = new _Edge[m];
		for (int i = 0; i < _n; i++) g[i] = new ArrayList<>();
		for (int i = 0; i < m; i++) {
			Edge e = _edges.get(i);
			_Edge e1 = new _Edge(e.to, e.cap - e.flow, e.cost);
			_Edge e2 = new _Edge(e.from, e.flow, -e.cost);
			e1.rev = e2;
			e2.rev = e1;
			g[e.from].add(e1);
			g[e.to].add(e2);
			edges[i] = e1;
		}

		ArrayList<Flow> result = slope(g, s, t, flowLimit);
		for (int i = 0; i < m; i++) {
			_Edge e = edges[i];
			_edges.get(i).flow = _edges.get(i).cap - e.cap;
		}
		return result;
	}

	boolean dualRef(ArrayList<_Edge>[] g, int s, int t, Flow[] dual_dist, _Edge[] prev_e, BitSet vis, int[] que_min, PriorityQueue<Q> que) {
		for (int i = 0; i < _n; i++) dual_dist[i].cost = Long.MAX_VALUE;
		vis.clear();
		int qr = 0, qw = 0;
		que.clear();

		dual_dist[s].cost = 0;
		que_min[qw++] = s;
		while ( qw > qr || que.size() > 0 ) {
			int v;
			if (qw > qr) {
				v = que_min[qr++];
			} else {
				Q q = que.poll();
				v = q.to;
			}
			if (vis.get(v)) continue;
			vis.set(v);
			if (v == t) break;
			long dual_v = dual_dist[v].flow;
			long dist_v = dual_dist[v].cost;
			for (_Edge e: g[v]) {
				if (e.cap == 0) continue;
				long cost = e.cost - dual_dist[e.to].flow + dual_v;
				if (dual_dist[e.to].cost - dist_v > cost) {
					long dist_to = dist_v + cost;
					dual_dist[e.to].cost = dist_to;
					prev_e[e.to] = e.rev;
					if (dist_to == dist_v) {
						que_min[qw++] = e.to;
					} else {
						que.add(new Q(dist_to, e.to));
					}
				}
			}
		}
		if (!vis.get(t)) {
			return false;
		}

		for (int v = 0; v < _n; v++) {
			if (!vis.get(v)) continue;
			dual_dist[v].flow -= dual_dist[t].cost - dual_dist[v].cost;
		}
		return true;
	}

	private ArrayList<Flow> slope(ArrayList<_Edge>[] g, int s, int t, long flowLimit) {
		Flow[] dual_dist = new Flow[_n];
		for (int i = 0; i < _n; i++) dual_dist[i] = new Flow(0, 0);
		_Edge[] prev_e = new _Edge[_n];
		BitSet vis = new BitSet(_n);
		int[] que_min = new int[_n];
		PriorityQueue<Q> que = new PriorityQueue<>(Comparator.reverseOrder());

		long flow = 0;
		long cost = 0, prev_cost_per_flow = -1;
		ArrayList<Flow> result = new ArrayList<>();
		result.add(new Flow(0, 0));
		while (flow < flowLimit) {
			if (!dualRef(g, s, t, dual_dist, prev_e, vis, que_min, que)) break;
			long c = flowLimit - flow;
			for (int v = t; v != s; v = prev_e[v].to) {
				c = Math.min(c, prev_e[v].rev.cap);
			}
			for (int v = t; v != s; v = prev_e[v].to) {
				_Edge e = prev_e[v];
				e.cap += c;
				e.rev.cap -= c;
			}
			long d = -dual_dist[s].flow;
			flow += c;
			cost += c * d;
			if (prev_cost_per_flow == d) {
				result.remove(result.size() - 1);
			}
			result.add(new Flow(flow, cost));
			prev_cost_per_flow = d;
		}
		return result;
	}

	public class Edge {
		int from, to;
		long cap, flow;
		long cost;
		Edge(int from, int to, long cap, long cost) {
			this.from = from;
			this.to = to;
			this.cap = cap;
			this.flow = 0;
			this.cost = cost;
		}
	}
	public class Flow {
		long flow;
		long cost;
		Flow(long flow, long cost) {
			this.flow = flow;
			this.cost = cost;
		}
	}
	private class _Edge {
		int to;
		_Edge rev;
		long cap, cost;
		_Edge(int to, long cap, long cost) {
			this.to = to;
			this.cap = cap;
			this.cost = cost;
		}
	}
	private class Q implements Comparable<Q> {
		long key;
		int to;
		Q(long key, int to) {
			this.key = key;
			this.to = to;
		}
		@Override
		public int compareTo(Q o) {
			if ( key != o.key ) {
				return key > o.key ? -1 : 1;
			} else {
				return 0;
			}
		}
	}
}
