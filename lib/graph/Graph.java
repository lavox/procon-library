package graph;

import java.util.ArrayList;

public interface Graph {
	public int size();
	public void forEachEdge(int v, EdgeConsumer action);
	public default Iterable<? extends Edge> edges(int v) {
		return () -> {
			ArrayList<Edge> edges = new ArrayList<>();
			forEachEdge(v, (from, to, id, cost) -> edges.add(new Edge(from, to, id, cost)));
			return edges.iterator();
		};
	}

	@FunctionalInterface
	public interface EdgeConsumer {
		public void accept(int from, int to, int id, long cost);
	}
}
