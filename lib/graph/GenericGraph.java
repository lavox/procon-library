package graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PrimitiveIterator;

public class GenericGraph<E extends Edge> implements Graph {
	private ArrayList<E> _edges = null;
	private int maxEdgeId = 0;
	protected int n = 0;
	protected int[] start = null;
	protected Object[] edges = null;
	public GenericGraph(int n) {
		this.n = n;
		this._edges = new ArrayList<>();
	}
	public void addEdge(E e) {
		maxEdgeId = Math.max(maxEdgeId, e.id);
		_edges.add(e);
	}
	public int maxEdgeId() {
		return maxEdgeId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void forEachEdge(int v, Graph.EdgeConsumer action) {
		for (int ei = start[v]; ei < start[v + 1]; ei++) {
			E e = (E) edges[ei];
			action.accept(e.from, e.to, e.id, e.cost);
		}
	}

	public void build() {
		start = new int[n + 1];
		for (Edge e: _edges) start[e.from + 1]++;
		for (int i = 0; i < n; i++) start[i + 1] += start[i];
		int[] cnt = start.clone();
		edges = new Object[_edges.size()];
		for (Edge e: _edges) edges[cnt[e.from]++] = e;
	}

	@Override
	public int size() {
		return n;
	}
	public int edgeSize(int v) {
		return start[v + 1] - start[v];
	}
	@SuppressWarnings("unchecked")
	public E edge(int v, int i) {
		return (E)edges[start[v] + i];
	}
	@Override
	public Iterable<E> edges(int v) {
		return new Iterable<E>() {
			@Override
			public Iterator<E> iterator() {
				return new Iterator<E>() {
					int ei = 0;
					int ecnt = edgeSize(v);
					@Override
					public boolean hasNext() {
						return ei < ecnt;
					}
					@Override
					public E next() {
						return edge(v, ei++);
					}
				};
			}
		};
	}
	@Override
	public PrimitiveIterator.OfInt edgesTo(int v) {
		return new PrimitiveIterator.OfInt() {
			int i = 0;
			@Override
			public boolean hasNext() { return i < edgeSize(v); }
			@Override
			public int nextInt() { return edge(v, i++).to; }
		};
	}
}
