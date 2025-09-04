package graph;

import java.util.ArrayList;

public class Graph {
	private int n;
	private ArrayList<Edge>[] edges;
	
	@SuppressWarnings("unchecked")
	public Graph(int n) {
		this.n = n;
		edges = new ArrayList[n];
		for (int i = 0; i < n; i++) edges[i] = new ArrayList<>();
	}
	public void addDirEdge(Edge e) {
		edges[e.from()].add(e);
	}
	public void addDirEdge(int from, int to) {
		edges[from].add(new Edge(from, to, 0));
	}
	public void addDirEdge(int from, int to, int id) {
		edges[from].add(new Edge(from, to, id));
	}
	public void addUndirEdge(int u, int v) {
		edges[u].add(new Edge(u, v, 0));
		edges[v].add(new Edge(v, u, 0));
	}
	public void addUndirEdge(int u, int v, int id) {
		edges[u].add(new Edge(u, v, id));
		edges[v].add(new Edge(v, u, id));
	}
	public int edgeSize(int v) {
		return edges[v].size();
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
