package graph;

public class Edge {
	public int from;
	public int to;
	public int id;
	public long cost;
	public Edge(int from, int to, int id, long cost) {
		this.from = from;
		this.to = to;
		this.id = id;
		this.cost = cost;
	}
	public Edge(int from, int to, int id) {
		this.from = from;
		this.to = to;
		this.id = id;
		this.cost = 1;
	}
	public Edge(int from, int to) {
		this.from = from;
		this.to = to;
		this.id = -1;
		this.cost = 1;
	}
	public int from() {
		return from;
	}
	public int to() {
		return to;
	}
	public int id() {
		return id;
	}
	public long cost() {
		return cost;
	}
}
