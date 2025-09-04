package graph;
import java.util.BitSet;

public abstract class LongRerooting {
	public abstract long e();
	public abstract long merge(long x, long y);
	public abstract long mergeSubtree(long x, Edge e);
	public abstract long nodeValue(long x, int v);
	protected long leaf(Edge e) {
		return mergeSubtree(e(), e);
	}

	private int edgeCnt = 0;
	private long[] values = null;
	private Edge[] edges = null;
	private long[] edgeValues = null;
	private Graph g = null;

	public LongRerooting(int n) {
		g = new Graph(n);
		edges = new Edge[2 * (n - 1)];
		values = new long[n];
		edgeValues = new long[2 * (n - 1)];
	}
	public void addEdge(int u, int v) {
		int eid = (edgeCnt++) * 2;
		Edge e = new Edge(u, v, eid);
		Edge re = new Edge(v, u, eid + 1);
		g.addDirEdge(e);
		g.addDirEdge(re);
		edges[e.id()] = e;
		edges[re.id()] = re;
	}
	public void build() {
		dfs(0);
		bfs(0);
	}
	public long nodeValue(int id) {
		return values[id];
	}
	public long edgeValue(Edge e) {
		return edgeValues[e.id()];
	}
	public long edgeValue(int id) {
		return edgeValues[id];
	}
	public long edgeValue(int id, boolean rev) {
		return rev ? edgeValues[id ^ 1] : edgeValues[id];
	}
	public Edge rev(Edge e) {
		return edges[e.id() ^ 1];
	}
	public Edge[] edges() {
		return edges;
	}

	private void dfs(int v0) {
		Dfs dfs = new Dfs(g);
		for (Dfs.DfsStep s: dfs.dfsPostOrder(v0)) {
			if (s.parent != -1) {
				long val = e();
				Edge pe = g.edge(s.parent, s.edgeIndex);
				if (g.edgeSize(s.cur) > 1) {
					for (Edge e: g.edges(s.cur)) {
						if (e.to() == s.parent) continue;
						val = merge(val, edgeValues[e.id()]);
					}
					edgeValues[pe.id()] = mergeSubtree(val, pe);
				} else {
					edgeValues[pe.id()] = leaf(pe);
				}
			}
		}
	}
	private void bfs(int v0) {
		int[] queue = new int[g.size()];
		int ri = 0;
		int wi = 0;
		queue[wi++] = v0;
		BitSet visited = new BitSet(g.size());
		while (ri < wi) {
			int v = queue[ri++];
			visited.set(v);
			long[] dpl = new long[g.edgeSize(v) + 1];
			long[] dpr = new long[g.edgeSize(v) + 1];
			dpl[0] = e();
			dpr[0] = e();
			for (int i = 0; i < g.edgeSize(v); i++) {
				Edge e1 = g.edge(v, i);
				Edge e2 = g.edge(v, g.edgeSize(v) - 1 - i);
				dpl[i + 1] = merge(dpl[i], edgeValues[e1.id()]);
				dpr[i + 1] = merge(dpr[i], edgeValues[e2.id()]);
			}
			for (int i = 0; i < g.edgeSize(v); i++) {
				Edge e = g.edge(v, i);
				Edge re = rev(e);
				if (visited.get(e.to())) continue;
				edgeValues[re.id()] = mergeSubtree(merge(dpl[i], dpr[g.edgeSize(v) - 1 - i]), re);
				queue[wi++] = e.to();
			}
			values[v] = nodeValue(dpl[g.edgeSize(v)], v);
		}
	}
}
