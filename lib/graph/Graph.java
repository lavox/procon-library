package graph;

import java.util.ArrayList;
import java.util.PrimitiveIterator;

import primitive.IntArrayList;

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
	public default PrimitiveIterator.OfInt edgesTo(int v) {
		IntArrayList toArr = new IntArrayList();
		forEachEdge(v, (from, to, id, cost) -> toArr.add(to));
		return new PrimitiveIterator.OfInt() {
			int i = 0;
			@Override
			public boolean hasNext() { return i < toArr.size(); }
			@Override
			public int nextInt() { return toArr.get(i++); }
		};
	}

	@FunctionalInterface
	public interface EdgeConsumer {
		public void accept(int from, int to, int id, long cost);
	}
}
