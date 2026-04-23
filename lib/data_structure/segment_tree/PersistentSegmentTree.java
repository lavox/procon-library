package data_structure.segment_tree;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.IntFunction;
import java.util.function.Supplier;

public class PersistentSegmentTree<S> {
	private final int n;
	private final Monoid<S> monoid;
	private final Node<S> root;

	public PersistentSegmentTree(int n, BinaryOperator<S> op, Supplier<S> e) {
		this(n, i -> e.get(), op, e);
	}
	public PersistentSegmentTree(S[] arr, BinaryOperator<S> op, Supplier<S> e) {
		this(arr.length, i -> i < arr.length ? arr[i] : e.get(), op, e);
	}
	public PersistentSegmentTree(List<S> arr, BinaryOperator<S> op, Supplier<S> e) {
		this(arr.size(), i -> i < arr.size() ? arr.get(i): e.get(), op, e);
	}
	public PersistentSegmentTree(int n, IntFunction<S> dataProvider, BinaryOperator<S> op, Supplier<S> e) {
		this(n, dataProvider, new Monoid<>(op, e));
	}
	private PersistentSegmentTree(int n, IntFunction<S> dataProvider, Monoid<S> monoid) {
		this.root = new Node<>(0, n, dataProvider, monoid);
		this.n = n;
		this.monoid = monoid;
	}
	private PersistentSegmentTree(PersistentSegmentTree<S> from, Node<S> root) {
		this.n = from.n;
		this.monoid = from.monoid;
		this.root = root;
	}

	public PersistentSegmentTree<S> update(int p, S x) {
		if (p >= n) throw new IndexOutOfBoundsException();
		return new PersistentSegmentTree<>(this, root.update(p, x, 0, n, monoid));
	}
	public S get(int p) {
		if (p >= n) throw new IndexOutOfBoundsException();
		return root.get(p, 0, n);
	}
	public S query(int l, int r) {
		if (n == 0) return monoid.e();
		return root.query(l, r, 0, n, monoid);
	}
	public S allQuery() {
		return query(0, n);
	}

	private static class Node<S> {
		private final S data;
		private final Node<S> ln;
		private final Node<S> rn;

		protected Node(S data, Node<S> ln, Node<S> rn) {
			this.data = data;
			this.ln = ln;
			this.rn = rn;
		}
		protected Node(int l, int r, IntFunction<S> dataProvider, Monoid<S> monoid) {
			if (l + 1 >= r) {
				this.ln = null;
				this.rn = null;
				this.data = l + 1 == r ? dataProvider.apply(l) : monoid.e();
			} else {
				int m = l + ((r - l) >> 1);
				this.ln = new Node<>(l, m, dataProvider, monoid);
				this.rn = new Node<>(m, r, dataProvider, monoid);
				this.data = monoid.op(ln.data, rn.data);
			}
		}
		protected Node<S> createNode(S data, Node<S> ln, Node<S> rn) {
			return new Node<>(data, ln, rn);
		}
		protected Node<S> merge(Node<S> ln, Node<S> rn, Monoid<S> monoid) {
			return createNode(monoid.op(ln.data, rn.data), ln, rn);
		}
		protected int mid(int l, int r) {
			return l + ((r - l) >> 1);
		}

		protected S get(int p, int node_l, int node_r) {
			assert node_l <= p && p < node_r;
			if (node_l + 1 == node_r) return data;
			int node_m = mid(node_l, node_r);
			return p < node_m ? ln.get(p, node_l, node_m) : rn.get(p, node_m, node_r);
		}
		protected Node<S> update(int p, S x, int node_l, int node_r, Monoid<S> monoid) {
			assert node_l <= p && p < node_r;
			if (node_l + 1 == node_r) return createNode(x, null, null);
			int node_m = mid(node_l, node_r);
			return p < node_m 
				? merge(ln.update(p, x, node_l, node_m, monoid), rn, monoid) 
				: merge(ln, rn.update(p, x, node_m, node_r, monoid), monoid);
		}
		protected S query(int l, int r, int node_l, int node_r, Monoid<S> monoid) {
			if (r <= node_l || node_r <= l) return monoid.e();
			else if (l <= node_l && node_r <= r) return data;
			else {
				int node_m = mid(node_l, node_r);
				return monoid.op(ln.query(l, r, node_l, node_m, monoid), rn.query(l, r, node_m, node_r, monoid));
			}
		}
	}

	private static class Monoid<S> {
		private final BinaryOperator<S> _op;
		private final Supplier<S> _e;
		protected Monoid(BinaryOperator<S> _op, Supplier<S> _e) {
			this._op = _op;
			this._e = _e;
		}
		protected S e() {
			return _e.get();
		}
		protected S op(S a, S b) {
			return _op.apply(a, b);
		}
	}
}
