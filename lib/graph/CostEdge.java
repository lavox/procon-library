package graph;

public class CostEdge extends Edge {
	private final long cost;
	public CostEdge(int from, int to, long cost, int id) {
		super(from, to, id);
		this.cost = cost;
	}
	public long cost() {
		return cost;
	}
}
