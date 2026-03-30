package graph;

import data_structure.Permutation;
import primitive.IntArrayList;

public class TopologicalSort {
	private int _n = 0;
	private SimpleGraph g = null;
	private Permutation nodes = null;
	
	public TopologicalSort(int n) {
		this._n = n;
		g = new SimpleGraph(n);
	}
	public void addEdge(int from, int to) {
		g.addDirEdge(from, to);
	}
	public boolean sort() {
		g.build();
		int[] index = new int[_n];
		Graph.EdgeConsumer action1 = (from, to, id, cost) -> {
			index[to]++;
		};
		for (int i = 0; i < g.size(); i++) g.forEachEdge(i, action1);
		
		IntArrayList queue = new IntArrayList(_n);
		int ri = 0;
		for ( int i = 0 ; i < _n ; i++ ) {
			if (index[i] == 0) queue.add(i);
		}
		
		nodes = new Permutation(_n);
		int i = 0;
		Graph.EdgeConsumer action2 = (from, to, id, cost) -> {
			index[to]--;
			if (index[to] == 0) queue.add(to);
		};
		while (ri < queue.size()) {
			int pos = queue.get(ri++);
			nodes.swapVal(pos, nodes.valAt(i++));
			g.forEachEdge(pos, action2);
		}
		return i == _n;
	}
	public int nodeAt(int i) {
		return nodes.valAt(i);
	}
	public int idxOf(int v) {
		return nodes.idxOf(v);
	}
	public int[] nodeArray() {
		return nodes.values();
	}
	public int[] indexArray() {
		return nodes.indexes();
	}
}
