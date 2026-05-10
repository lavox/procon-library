package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PrimitiveIterator;

import primitive.IntArrayList;

public class CentroidDecomposer {
	public static SubTree createInitTree(Graph g) {
		Context ctx = new Context(g);
		int sid = ctx.nextSid();
		Arrays.fill(ctx.sidMap[0], sid);
		return new SubTree(ctx, 0, sid, 0, g.size());
	}

	public static class SubTree implements Graph {
		private final Context ctx;
		private final int gen;
		private final int sid;
		private final int sz;
		private final int root;
		private final int[] sidMap;
		private int centroid = -1;
		private ArrayList<SubTree> ctree = null;
		private int maxDepth = -1;
		protected SubTree(Context ctx, int gen, int sid, int root, int sz) {
			this.ctx = ctx;
			this.gen = gen;
			this.sid = sid;
			this.root = root;
			this.sz = sz;
			this.sidMap = ctx.sidMap[gen];
		}
		public void decompose() {
			if (centroid != -1) return;
			ctree = new ArrayList<>();
			if (sz == 1) {
				centroid = root;
				return;
			}
			ctx.prepareGen(gen + 1);
			centroid = searchCentroid();
			SubTree curTree = null;
			final int[] child = ctx.child[gen];
			for (Dfs.DfsStep s: ctx.dfs.dfsBothOrder(this, centroid)) {
				if (s.isPre) {
					if (s.parent == centroid) {
						int csz = child[s.cur] < child[s.parent] ? child[s.cur] : this.sz - child[s.parent];
						curTree = new SubTree(ctx, gen + 1, ctx.nextSid(), s.cur, csz);
						ctree.add(curTree);
					}
				} else {
					if (s.cur != centroid) {
						ctx.sidMap[gen + 1][s.cur] = curTree.sid;
						curTree.maxDepth = Math.max(curTree.maxDepth, s.depth - 1);
					}
				}
			}
		}
		private int searchCentroid() {
			int mid = (sz + 1) / 2;
			final int[] child = ctx.child[gen];
			for (Dfs.DfsStep s: ctx.dfs.dfsBothOrder(this, root)) {
				if (s.isPre) {
					child[s.cur] = 1;
				} else {
					if (s.parent != -1) {
						child[s.parent] += child[s.cur];
					}
					if (child[s.cur] >= mid) return s.cur;
				}
			}
			return -1;
		}
		public int centroid() {
			return centroid;
		}
		public ArrayList<SubTree> decomposedTree() {
			return ctree;
		}
		public boolean isMember(int v) {
			return sidMap[v] == this.sid;
		}
		public int root() {
			return root;
		}
		public int maxDepth() {
			return maxDepth;
		}
		@Override
		public int size() {
			return sz;
		}
		@Override
		public void forEachEdge(int v, EdgeConsumer action) {
			ctx.g.forEachEdge(v, (from, to, id, cost) -> {
				if (isMember(to)) action.accept(from, to, id, cost);
			});
		}
		@Override
		public Iterable<? extends Edge> edges(int v) {
			ArrayList<Edge> ret = new ArrayList<>();
			for (Edge e: ctx.g.edges(v)) {
				if (isMember(e.to)) ret.add(e);
			}
			return ret;
		}
		@Override
		public PrimitiveIterator.OfInt edgesTo(int v) {
			IntArrayList ret = new IntArrayList();
			PrimitiveIterator.OfInt it = ctx.g.edgesTo(v);
			while (it.hasNext()) {
				int to = it.nextInt();
				if (isMember(to)) ret.add(to);
			}
			return ret.iterator();
		}
	}

	private static class Context {
		protected int sid = 0;
		protected final Graph g;
		protected int[][] child;
		protected int[][] sidMap;
		protected Dfs dfs;
		private int genMax;
		private int sz;
		protected Context(Graph g) {
			this.g = g;
			this.sz = g.size();
			genMax = Math.max(31 - Integer.numberOfLeadingZeros(this.sz), 0);
			while (this.sz > (1 << genMax)) genMax++;
			genMax++;
			this.child = new int[genMax][];
			this.sidMap = new int[genMax][];
			this.dfs = new Dfs(g.size());
			prepareGen(0);
		}
		protected int nextSid() {
			return ++sid;
		}
		protected int sid(int gen, int v) {
			return gen < genMax && sidMap[gen] != null ? sidMap[gen][v] : 0;
		}
		protected void prepareGen(int gen) {
			if (gen >= genMax) throw new IllegalArgumentException();
			if (child[gen] == null) {
				child[gen] = new int[sz];
				sidMap[gen] = new int[sz];
			}
		}
	}
}
