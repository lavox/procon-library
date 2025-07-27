import java.util.ArrayDeque;
import java.util.ArrayList;

class LowLink {
	private Node[] nodes = null;
	private ArrayList<Edge> edges = null;
	private ArrayList<Edge> bridges = null;
	private ArrayList<Node> articulations = null;
	private int cnt = 0;
	private int componentCnt = 0;

	public LowLink(int n) {
		this.nodes = new Node[n];
		for (int i = 0; i < n; i++) nodes[i] = new Node(i);
		this.edges = new ArrayList<>();
	}
	public void addEdge(int from, int to) {
		int eid = edges.size();
		Edge e = new Edge(nodes[from], nodes[to], eid);
		nodes[from].addEdge(e);
		edges.add(e);
		nodes[to].addEdge(new Edge(nodes[to], nodes[from], eid));
	}
	public void build() {
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
	public boolean isBridge(int eid) {
		return edges.get(eid).isBridge;
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
			if (n.iter == 0) {
				n.visited = true;
				n.ord = cnt;
				n.low = cnt;
				cnt++;
			}
			if (n.iter > 0) {
				Node to = n.edge.get(n.iter - 1).to;
				if (n.parent == null || n.parent != to) {
					n.low = Math.min(n.low, to.low);
				}
				if (n.parent != null && n.ord <= to.low) {
					n.isArticulation = true;
					n.artCnt++;
				}
				if ( n.ord < to.low ) {
					Edge e = edges.get(n.edge.get(n.iter - 1).eid);
					e.isBridge = true;
					bridges.add(e);
				}
			}
			boolean stacked = false;
			while (n.iter < n.edge.size()) {
				Edge e = n.edge.get(n.iter++);
				if (n.parent != null && e.to == n.parent) continue;
				if (!e.to.visited) {
					stacked = true;
					n.childCnt++;
					e.to.parent = n;
					stack.addLast(e.to);
					break;
				} else {
					n.low = Math.min(n.low, e.to.ord);
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
		private ArrayList<Edge> edge = null;
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
			edge = new ArrayList<>();
		}
		private void addEdge(Edge e) {
			edge.add(e);
		}
		public int id() {
			return id;
		}
		public int edgeCnt() {
			return edge.size();
		}
		public boolean isArticulation() {
			return isArticulation;
		}
		public int articulationCnt() {
			return artCnt;
		}
	}
	public class Edge {
		private Node from = null;
		private Node to = null;
		private int eid = 0;

		private boolean isBridge = false;
		private Edge(Node from, Node to, int id) {
			this.from = from;
			this.to = to;
			this.eid = id;
		}
		public int id() {
			return eid;
		}
		public int from() {
			return from.id;
		}
		public int to() {
			return to.id;
		}
	}
}
