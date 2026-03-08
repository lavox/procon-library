package graph;

import java.util.ArrayList;

public class GenericGraph<E extends Edge> {
	protected int n;
	protected ArrayList<E>[] edges;
	protected int maxEdgeId = 0;
	protected int edgeCnt = 0;
	
	@SuppressWarnings("unchecked")
	public GenericGraph(int n) {
		this.n = n;
		edges = new ArrayList[n];
		for (int i = 0; i < n; i++) edges[i] = new ArrayList<>();
	}
	public void addDirEdge(E e) {
		edges[e.from()].add(e);
		maxEdgeId = Math.max(maxEdgeId, e.id());
		edgeCnt++;
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
	public ArrayList<E> edges(int v) {
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
