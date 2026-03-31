package graph;

import java.util.ArrayList;
import java.util.PrimitiveIterator;

public class GridGraph implements Graph {
	protected int height = 0;
	protected int width = 0;
	protected EdgePredicate forbiddenEdge = null;
	protected EdgeToLongFunction edgeCostProvider = null;
	protected int[][] dir = null;
	public static final int[][] DIR4 = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
	public static final int[][] DIR8 = {{0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}};

	public GridGraph(int height, int width) {
		this(height, width, DIR4);
	}
	public GridGraph(int height, int width, int[][] dir) {
		this.height = height;
		this.width = width;
		this.dir = dir;
	}
	public void setForbiddenEdge(EdgePredicate forbiddenEdge) {
		this.forbiddenEdge = forbiddenEdge;
	}
	public void setEdgeCostProvider(EdgeToLongFunction edgeCostProvider) {
		this.edgeCostProvider = edgeCostProvider;
	}
	@Override
	public int size() {
		return height * width;
	}
	public int height() {
		return height;
	}
	public int width() {
		return width;
	}
	public int r(int v) {
		return v / width;
	}
	public int c(int v) {
		return v % width;
	}
	public int v(int r, int c) {
		return r * width + c;
	}
	public boolean isValid(int r, int c) {
		return 0 <= r && r < height && 0 <= c && c < width;
	}
	public boolean isValid(int v) {
		return isValid(r(v), c(v));
	}
	public boolean isForbiddenEdge(int r0, int c0, int r1, int c1) {
		return forbiddenEdge != null && forbiddenEdge.test(r0, c0, r1, c1);
	}
	public long edgeCost(int r0, int c0, int r1, int c1) {
		return edgeCostProvider == null ? 1 : edgeCostProvider.applyAsLong(r0, c0, r1, c1);
	}
	@Override
	public void forEachEdge(int v, EdgeConsumer action) {
		int r0 = r(v);
		int c0 = c(v);
		for (int i = 0; i < dir.length; i++) {
			int r1 = r0 + dir[i][0];
			int c1 = c0 + dir[i][1];
			if (!isValid(r1, c1) || isForbiddenEdge(r0, c0, r1, c1)) continue;
			action.accept(v, v(r1, c1), -1, edgeCost(r0, c0, r1, c1));
		}
	}
	@Override
	public Iterable<? extends Edge> edges(int v) {
		ArrayList<Edge> edges = new ArrayList<>(4);
		int r0 = r(v);
		int c0 = c(v);
		for (int i = 0; i < dir.length; i++) {
			int r1 = r0 + dir[i][0];
			int c1 = c0 + dir[i][1];
			if (!isValid(r1, c1) || isForbiddenEdge(r0, c0, r1, c1)) continue;
			edges.add(new Edge(v, v(r1, c1), -1, edgeCost(r0, c0, r1, c1)));
		}
		return edges;
	}
	@Override
	public PrimitiveIterator.OfInt edgesTo(int v) {
		final int r0 = r(v);
		final int c0 = c(v);
		var iter = new PrimitiveIterator.OfInt() {
			int i = 0;
			int next = 0;
			@Override
			public boolean hasNext() { return i < dir.length; }
			@Override
			public int nextInt() {
				int ret = next;
				i++;
				advance();
				return ret;
			}
			private void advance() {
				while (i < dir.length) {
					int r1 = r0 + dir[i][0];
					int c1 = c0 + dir[i][1];
					if (isValid(r1, c1) && !isForbiddenEdge(r0, c0, r1, c1)) {
						next = v(r1, c1);
						break;
					}
					i++;
				}
			}
		};
		iter.advance();
		return iter;
	}
	@FunctionalInterface
	public interface EdgePredicate {
		public boolean test(int r0, int c0, int r1, int c1);
	}
	@FunctionalInterface
	public interface EdgeToLongFunction {
		public long applyAsLong(int r0, int c0, int r1, int c1);
	}
}
