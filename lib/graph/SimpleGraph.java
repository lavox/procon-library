package graph;

public class SimpleGraph extends GenericGraph<Edge> {
	public SimpleGraph(int n) {
		super(n);
	}
	public void addDirEdge(int from, int to) {
		addEdge(new Edge(from, to));
	}
	public void addDirEdge(int from, int to, int id) {
		addEdge(new Edge(from, to, id));
	}
	public void addDirEdge(int from, int to, int id, long cost) {
		addEdge(new Edge(from, to, id, cost));
	}
	public void addUndirEdge(int u, int v) {
		addEdge(new Edge(u, v));
		addEdge(new Edge(v, u));
	}
	public void addUndirEdge(int u, int v, int id) {
		addEdge(new Edge(u, v, id));
		addEdge(new Edge(v, u, id));
	}
	public void addUndirEdge(int u, int v, int id, long cost) {
		addEdge(new Edge(u, v, id, cost));
		addEdge(new Edge(v, u, id, cost));
	}
	@Override
	public void forEachEdge(int v, Graph.EdgeConsumer action) {
		for (int ei = start[v]; ei < start[v + 1]; ei++) {
			Edge e = (Edge)edges[ei];
			action.accept(v, e.to, e.id, e.cost);
		}
	}
}
