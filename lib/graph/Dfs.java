package graph;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Dfs {
	private final Graph g;
	private int[] visitedGen = null;
	int gen = 0;

	public Dfs(Graph g) {
		this.g = g;
		this.visitedGen = new int[g.size()];
	}
	public Iterable<DfsStep> dfsBothOrder(int v0) {
		gen++;
		return () -> new DfsIterator(v0, true, true);
	}
	public Iterable<DfsStep> dfsPreOrder(int v0) {
		gen++;
		return () -> new DfsIterator(v0, true, false);
	}
	public Iterable<DfsStep> dfsPostOrder(int v0) {
		gen++;
		return () -> new DfsIterator(v0, false, true);
	}
	private void setVisited(int nodeId) {
		visitedGen[nodeId] = gen;
	}
	private boolean isVisited(int nodeId) {
		return visitedGen[nodeId] >= gen;
	}

	public class DfsIterator implements Iterator<DfsStep> {
		private final ArrayDeque<DfsStep> stack;
		private final boolean requirePreOrder;
		private final boolean requirePostOrder;
		private DfsStep nextStep = null;
		private DfsIterator(int v0, boolean requirePreOrder, boolean requirePostOrder) {
			this.requirePreOrder = requirePreOrder;
			this.requirePostOrder = requirePostOrder;
			this.stack = new ArrayDeque<>();
			this.stack.addLast(new DfsStep(v0, -1, 0, null, true, 0));
			setVisited(v0);
			_next();
		}
		@Override
		public boolean hasNext() {
			return nextStep != null;
		}
		@Override
		public DfsStep next() {
			if (nextStep == null)
				throw new NoSuchElementException();
			DfsStep ret = nextStep;
			_next();
			return ret;
		}
		private void _next() {
			nextStep = null;
			while (stack.size() > 0 && nextStep == null) {
				DfsStep s = stack.pollLast();
				if (s.isPre) {
					stack.addLast(new DfsStep(s.cur, s.parent, s.edgeIndex, s.eit, false, s.depth));
					addNextEdge(s.cur, g.edges(s.cur).iterator(), s.depth + 1);
				} else if (s.parent != -1) {
					addNextEdge(s.parent, s.eit, s.depth);
				}
				if ((s.isPre && requirePreOrder) || (!s.isPre && requirePostOrder)) {
					nextStep = s;
				}
			}
		}
		private void addNextEdge(int v, Iterator<? extends Edge> eit, int depth) {
			if (eit == null) return;
			while (eit.hasNext()) {
				Edge e = eit.next();
				if (isVisited(e.to())) continue;
				stack.addLast(new DfsStep(e.to(), v, e.id(), eit, true, depth));
				setVisited(e.to());
				return;
			}
		}
	}

	public class DfsStep {
		public final int cur;
		public final int parent;
		public final int edgeIndex; // edgeのid
		public final boolean isPre;
		public final int depth;
		private final Iterator<? extends Edge> eit; // parentのedgeのiterator

		private DfsStep(int cur, int parent, int edgeIndex, Iterator<? extends Edge> eit, boolean isPre, int depth) {
			this.cur = cur;
			this.parent = parent;
			this.edgeIndex = edgeIndex;
			this.eit = eit;
			this.isPre = isPre;
			this.depth = depth;
		}
		public boolean isVisited(int nodeId) {
			return Dfs.this.isVisited(nodeId);
		}
	}
}
