package graph;

public class Graph extends GenericGraph<Edge> {
	@SuppressWarnings("unchecked")
	public Graph(int n) {
		super(n);
	}
	public void addDirEdge(int from, int to) {
		addDirEdge(new Edge(from, to, edgeCnt));
		maxEdgeId = Math.max(maxEdgeId, edgeCnt++);
	}
	public void addDirEdge(int from, int to, int id) {
		addDirEdge(new Edge(from, to, id));
		maxEdgeId = Math.max(maxEdgeId, id);
		edgeCnt++;
	}
	public void addUndirEdge(int u, int v) {
		edges[u].add(new Edge(u, v, edgeCnt++));
		edges[v].add(new Edge(v, u, edgeCnt));
		maxEdgeId = Math.max(maxEdgeId, edgeCnt++);
	}
	public void addUndirEdge(int u, int v, int id) {
		edges[u].add(new Edge(u, v, id));
		edges[v].add(new Edge(v, u, id));
		maxEdgeId = Math.max(maxEdgeId, id);
		edgeCnt += 2;
	}
}
