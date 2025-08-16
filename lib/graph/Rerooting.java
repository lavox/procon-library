package graph;
import java.util.ArrayDeque;
import java.util.ArrayList;

public abstract class Rerooting<E> {
	public abstract E e();
	public abstract E merge(E x, E y);
	public abstract E mergeSubtree(E x, Edge e);
	public abstract E nodeValue(E x, Node n);
	protected E leaf(Edge e) {
		return mergeSubtree(e(), e);
	}

	private ArrayList<Node> nodes = null;
	private ArrayList<Edge> edges = null;

	public Rerooting(int n) {
		nodes = new ArrayList<>(n);
		edges = new ArrayList<>(n - 1);
		for (int i = 0; i < n; i++) nodes.add(new Node(i));
	}
	private Node node(int i) {
		return nodes.get(i);
	}
	public void addEdge(int u, int v) {
		int eid = edges.size();
		Node nu = node(u);
		Node nv = node(v);
		Edge e = new Edge(eid, nu, nv);
		Edge re = new Edge(eid, nv, nu);
		e.rev = re;
		re.rev = e;
		nu.edge.add(e);
		nv.edge.add(re);
		edges.add(e);
	}
	public void build() {
		dfs(node(0));
		bfs(node(0));
	}
	public E nodeValue(int id) {
		return node(id).value;
	}
	public E edgeValue(int id, boolean rev) {
		return rev ? edges.get(id).dp : edges.get(id).rev.dp;
	}
	public ArrayList<Edge> edges() {
		return edges;
	}

	private void dfs(Node n0) {
		ArrayDeque<Node> stack = new ArrayDeque<>();
		stack.addLast(n0);
		while (stack.size() > 0) {
			Node n = stack.peekLast();
			if (n.iter < n.edge.size()) {
				Edge e = n.edge.get(n.iter++);
				if (n.parentEdge != null && n.parentEdge.from == e.to) continue;
				e.to.parentEdge = e;
				stack.add(e.to);
				continue;
			}
			if (n.parentEdge != null) {
				if (n.iter > 0) {
					E val = e();
					for (Edge e: n.edge) {
						if (n.parentEdge.from == e.to) continue;
						val = merge(val, e.dp);
					}
					n.parentEdge.dp = mergeSubtree(val, n.parentEdge);
				} else {
					n.parentEdge.dp = leaf(n.parentEdge);
				}
			}
			stack.pollLast();
		}
	}
	private void bfs(Node n0) {
		ArrayDeque<Node> queue = new ArrayDeque<>();
		queue.addLast(n0);
		while (queue.size() > 0) {
			Node n = queue.pollFirst();
			ArrayList<E> dpl = new ArrayList<>(n.edge.size() + 1);
			ArrayList<E> dpr = new ArrayList<>(n.edge.size() + 1);
			dpl.add(e());
			dpr.add(e());
			for (int i = 0; i < n.edge.size(); i++) {
				dpl.add(merge(dpl.get(i), n.edge(i).dp));
				dpr.add(merge(dpr.get(i), n.edge(n.edge.size() - 1 - i).dp));
			}
			for (int i = 0; i < n.edge.size(); i++) {
				Edge e = n.edge.get(i);
				if (n.parentEdge != null && n.parentEdge.from == e.to) continue;
				e.rev.dp = mergeSubtree(merge(dpl.get(i), dpr.get(n.edge.size() - 1 - i)), e.rev);
				queue.addLast(e.to);
			}
			n.value = nodeValue(dpl.get(n.edge.size()), n);
		}
	}

	public class Node {
		private int id;
		private ArrayList<Edge> edge;
		private Edge parentEdge = null;
		private int iter = 0;
		private Edge edge(int i) { return edge.get(i); }

		private E value = null;
		private Node(int id) {
			this.id = id;
			edge = new ArrayList<>();
		}
		public int id() {
			return id;
		}
	}
	public class Edge {
		private int id;
		private Node from;
		private Node to;
		private Edge rev = null;
		private E dp = null;
		private Edge(int id, Node from, Node to) {
			this.id = id;
			this.from = from;
			this.to = to;
		}
		public int id() {
			return id;
		}
		public Node from() {
			return from;
		}
		public Node to() {
			return to;
		}
	}
}
