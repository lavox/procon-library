package data_structure.segment_tree;
import java.util.List;
import java.util.function.LongBinaryOperator;
import java.util.function.IntToLongFunction;

public class LongPersistentSegmentTree {
	private final int n;
	private final Monoid monoid;
	private final Node root;

	public LongPersistentSegmentTree(int n, LongBinaryOperator op, long e) {
		this(n, i -> e, op, e);
	}
	public LongPersistentSegmentTree(int[] arr, LongBinaryOperator op, long e) {
		this(arr.length, i -> i < arr.length ? arr[i] : e, op, e);
	}
	public LongPersistentSegmentTree(List<Integer> arr, LongBinaryOperator op, long e) {
		this(arr.size(), i -> i < arr.size() ? arr.get(i): e, op, e);
	}
	public LongPersistentSegmentTree(int n, IntToLongFunction dataProvider, LongBinaryOperator op, long e) {
		this(n, dataProvider, new Monoid(op, e));
	}
	private LongPersistentSegmentTree(int n, IntToLongFunction dataProvider, Monoid monoid) {
		this.root = new Node(0, n, dataProvider, monoid);
		this.n = n;
		this.monoid = monoid;
	}
	private LongPersistentSegmentTree(LongPersistentSegmentTree from, Node root) {
		this.n = from.n;
		this.monoid = from.monoid;
		this.root = root;
	}

	public LongPersistentSegmentTree update(int p, long x) {
		if (p >= n) throw new IndexOutOfBoundsException();
		return new LongPersistentSegmentTree(this, root.update(p, x, 0, n, monoid));
	}
	public long get(int p) {
		if (p >= n) throw new IndexOutOfBoundsException();
		return root.get(p, 0, n);
	}
	public long query(int l, int r) {
		if (n == 0) return monoid.e();
		return root.query(l, r, 0, n, monoid);
	}
	public long allQuery() {
		return query(0, n);
	}

	private static class Node {
		private final long data;
		private final Node ln;
		private final Node rn;

		protected Node(long data, Node ln, Node rn) {
			this.data = data;
			this.ln = ln;
			this.rn = rn;
		}
		protected Node(int l, int r, IntToLongFunction dataProvider, Monoid monoid) {
			if (l + 1 >= r) {
				this.ln = null;
				this.rn = null;
				this.data = l + 1 == r ? dataProvider.applyAsLong(l) : monoid.e();
			} else {
				int m = l + ((r - l) >> 1);
				this.ln = new Node(l, m, dataProvider, monoid);
				this.rn = new Node(m, r, dataProvider, monoid);
				this.data = monoid.op(ln.data, rn.data);
			}
		}
		protected Node createNode(long data, Node ln, Node rn) {
			return new Node(data, ln, rn);
		}
		protected Node merge(Node ln, Node rn, Monoid monoid) {
			return createNode(monoid.op(ln.data, rn.data), ln, rn);
		}
		protected int mid(int l, int r) {
			return l + ((r - l) >> 1);
		}

		protected long get(int p, int node_l, int node_r) {
			assert node_l <= p && p < node_r;
			if (node_l + 1 == node_r) return data;
			int node_m = mid(node_l, node_r);
			return p < node_m ? ln.get(p, node_l, node_m) : rn.get(p, node_m, node_r);
		}
		protected Node update(int p, long x, int node_l, int node_r, Monoid monoid) {
			assert node_l <= p && p < node_r;
			if (node_l + 1 == node_r) return createNode(x, null, null);
			int node_m = mid(node_l, node_r);
			return p < node_m 
				? merge(ln.update(p, x, node_l, node_m, monoid), rn, monoid) 
				: merge(ln, rn.update(p, x, node_m, node_r, monoid), monoid);
		}
		protected long query(int l, int r, int node_l, int node_r, Monoid monoid) {
			if (r <= node_l || node_r <= l) return monoid.e();
			else if (l <= node_l && node_r <= r) return data;
			else {
				int node_m = mid(node_l, node_r);
				return monoid.op(ln.query(l, r, node_l, node_m, monoid), rn.query(l, r, node_m, node_r, monoid));
			}
		}
	}

	private static class Monoid {
		private final LongBinaryOperator _op;
		private final long _e;
		protected Monoid(LongBinaryOperator _op, long _e) {
			this._op = _op;
			this._e = _e;
		}
		protected long e() {
			return _e;
		}
		protected long op(long a, long b) {
			return _op.applyAsLong(a, b);
		}
	}
}
