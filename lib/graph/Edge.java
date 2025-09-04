package graph;

public class Edge {
	private final int from;
	private final int to;
	private final int id;
	public Edge(int from, int to, int id) {
		this.from = from;
		this.to = to;
		this.id = id;
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
}
