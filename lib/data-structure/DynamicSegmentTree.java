import java.util.function.BinaryOperator;
import java.util.function.Supplier;

class DynamicSegmentTree<S> {
	private long L = 0;
	private long R = 0;
	private BinaryOperator<S> _op = null;
	private Supplier<S> _e = null;
	private Node root = null;

	S op(S a, S b) {
		return _op.apply(a, b);
	}
	S e() {
		return _e.get();
	}

	public DynamicSegmentTree(long L, long R, BinaryOperator<S> op, Supplier<S> e) {
		this.L = L;
		this.R = R;
		this._op = op;
		this._e = e;
	}
	public S get(long p) {
		if ( root == null ) return e();
		return root.get(L, R, p);
	}
	public void set(long p, S x) {
		if ( root == null ) {
			root = new Node(p, x);
			return;
		}
		root.set(L, R, p, x);
	}
	public S query(long l, long r) {
		if ( root == null ) return e();
		return root.query(L, R, l, r);
	}

	private class Node {
		private Node left = null;
		private Node right = null;
		private S data = null;
		private S val = null;
		private long index = 0;

		private Node(long index, S val) {
			this.index = index;
			this.val = val;
			this.data = val;
		}
		private void update() {
			data = left == null ? val : (op(left.data, val));
			if ( right != null ) data = op(data, right.data);
		}

		private void set(long p0, long p1, long p, S x) {
			if ( p == index ) {
				val = x;
				update();
				return;
			}
			long m = (p0 + p1) >> 1;
			if ( p < m ) {
				if ( index < p ) {
					if ( left == null ) {
						left = new Node(index, val);
					} else {
						left.set(p0, m, index, val);
					}
					index = p; val = x;
				} else {
					if ( left == null ) {
						left = new Node(p, x);
					} else {
						left.set(p0, m, p, x);
					}
				}
			} else {
				if ( p < index ) {
					if ( right == null ) {
						right = new Node(index, val);
					} else {
						right.set(m, p1, index, val);
					}
					index = p; val = x;
				} else {
					if ( right == null ) {
						right = new Node(p, x);
					} else {
						right.set(m, p1, p, x);
					}
				}
			}
			update();
		}
		private S get(long p0, long p1, long p) {
			if ( p == index ) return val;
			long m = (p0 + p1) >> 1;
			if ( p < m ) {
				return left == null ? e() : left.get(p0, m, p);
			} else {
				return right == null ? e() : right.get(m, p1, p);
			}
		}
		private S query(long p0, long p1, long l, long r) {
			if ( p1 <= l || r <= p0 ) return e();
			if ( l <= p0 && p1 <= r ) return data;
			long m = (p0 + p1) >> 1;
			S ret = left == null ? e() : left.query(p0, m, l, r);
			if ( l <= index && index < r ) ret = op(ret, val);
			return right == null || r <= m ? ret : op(ret, right.query(m, p1, l, r));
		}
	}
}
