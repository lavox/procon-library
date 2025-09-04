package graph;

import java.util.ArrayList;

public class Graph {
	private int n;
	private ArrayList<Edge>[] edges;
	private int maxEdgeId = 0;
	private int edgeCnt = 0;
	
	@SuppressWarnings("unchecked")
	public Graph(int n) {
		this.n = n;
		edges = new ArrayList[n];
		for (int i = 0; i < n; i++) edges[i] = new ArrayList<>();
	}
	public void addDirEdge(Edge e) {
		edges[e.from()].add(e);
		maxEdgeId = Math.max(maxEdgeId, e.id());
		edgeCnt++;
	}
	public void addDirEdge(int from, int to) {
		edges[from].add(new Edge(from, to, edgeCnt));
		maxEdgeId = Math.max(maxEdgeId, edgeCnt++);
	}
	public void addDirEdge(int from, int to, int id) {
		edges[from].add(new Edge(from, to, id));
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
	public int edgeSize(int v) {
		return edges[v].size();
	}
	public int edgeSize() {
		return edgeCnt;
	}
	public int maxEdgeId() {
		return maxEdgeId;
	}
	public Edge edge(int v, int i) {
		return edges[v].get(i);
	}
	public ArrayList<Edge> edges(int v) {
		return edges[v];
	}
	public int[] edgesTo(int v) {
		int[] ret = new int[edgeSize(v)];
		for (int i = 0; i < ret.length; i++) ret[i] = edges[v].get(i).to();
		return ret;
	}
	public int size() {
		return n;
	}
}
