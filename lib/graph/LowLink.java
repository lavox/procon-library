package graph;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.BitSet;

public class LowLink {
	private Node[] nodes = null;
	private Graph g = null;
	private BitSet isBridge = null;
	private ArrayList<Edge> bridges = null;
	private ArrayList<Node> articulations = null;
	private int cnt = 0;
	private int componentCnt = 0;

	public LowLink(Graph g) {
		this.g = g;
		this.nodes = new Node[g.size()];
		for (int i = 0; i < g.size(); i++) nodes[i] = new Node(i);
		this.isBridge = new BitSet(g.maxEdgeId());
		build();
	}
	private void build() {
		bridges = new ArrayList<>();
		cnt = 0;
		for (Node n: nodes) {
			if (n.visited) continue;
			componentCnt++;
			dfs(n);
		}
		articulations = new ArrayList<>();
		for (Node n: nodes) {
			if (n.isArticulation) articulations.add(n);
		}
	}
	public int componentCnt() {
		return componentCnt;
	}
	public boolean isBridge(Edge e) {
		return isBridge.get(e.id());
	}
	public boolean isBridge(int eid) {
		return isBridge.get(eid);
	}
	public ArrayList<Edge> bridges() {
		return bridges;
	}
	public boolean isArticulation(int i) {
		return nodes[i].isArticulation;
	}
	public int articulationCnt(int i) {
		return nodes[i].artCnt;
	}
	public ArrayList<Node> articulations() {
		return articulations;
	}
	public Node node(int i) {
		return nodes[i];
	}
	private void dfs(Node n0) {
		ArrayDeque<Node> stack = new ArrayDeque<>();
		stack.addLast(n0);
		while ( stack.size() > 0 ) {
			Node n = stack.peekLast();
			int v = n.id;
			if (n.iter == 0) {
				n.visited = true;
				n.ord = cnt;
				n.low = cnt;
				cnt++;
			}
			if (n.iter > 0) {
				Edge e = g.edge(v, n.iter - 1);
				Node to = node(e.to());
				if (n.parent == null || n.parent != to) {
					n.low = Math.min(n.low, to.low);
				}
				if (n.parent != null && n.ord <= to.low) {
					n.isArticulation = true;
					n.artCnt++;
				}
				if (n.ord < to.low) {
					isBridge.set(e.id());
					bridges.add(e);
				}
			}
			boolean stacked = false;
			while (n.iter < g.edgeSize(v)) {
				Edge e = g.edge(v, n.iter++);
				Node to = node(e.to());
				if (n.parent != null && to == n.parent) continue;
				if (!to.visited) {
					stacked = true;
					n.childCnt++;
					to.parent = n;
					stack.addLast(to);
					break;
				} else {
					n.low = Math.min(n.low, to.ord);
				}
			}
			if (!stacked) {
				if (n.parent == null && n.childCnt >= 2) {
					n.isArticulation = true;
					n.artCnt += n.childCnt - 1;
				}
				stack.pollLast();
			}
		}
	}

	public class Node {
		private int id = 0;
		private Node parent = null;
		private int ord = 0;
		private int low = 0;
		private boolean visited = false;
		private boolean isArticulation = false;
		private int artCnt = 0;
		private int childCnt = 0;
		private int iter = 0;

		private Node(int id) {
			this.id = id;
		}
		public int id() {
			return id;
		}
		public boolean isArticulation() {
			return isArticulation;
		}
		public int articulationCnt() {
			return artCnt;
		}
	}
}
