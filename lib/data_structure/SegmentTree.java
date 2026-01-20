package data_structure;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class SegmentTree<S> {
	int n = 0;
	int size = 1;
	int log = 0;
	S[] d = null;

	Supplier<S> _e = null;
	BinaryOperator<S> _op = null;

	S e() {
		return _e.get();
	}
	S op(S a, S b) {
		return _op.apply(a, b);
	}

	public SegmentTree(int n, BinaryOperator<S> op, Supplier<S> e) {
		initialize(n, op, e);
		for (int i = 0; i < 2 * size; i++) d[i] = e();
	}
	public SegmentTree(S[] arr, BinaryOperator<S> op, Supplier<S> e) {
		this(arr.length, op, e);
		initData(arr);
	}
	public SegmentTree(Collection<S> arr, BinaryOperator<S> op, Supplier<S> e) {
		this(arr.size(), op, e);
		initData(arr);
	}
	@SuppressWarnings("unchecked")
	private void initialize(int n, BinaryOperator<S> op, Supplier<S> e) {
		this.n = n;
		this._op = op;
		this._e = e;

		size = 1;
		while ( size < n ) size *= 2;
		log = Integer.numberOfTrailingZeros(size);
		d = (S[]) Array.newInstance(e().getClass(), 2 * size);
	}

	public void initData(S[] arr) {
		assert arr.length == n;
		System.arraycopy(arr, 0, d, size, n);
		for ( int i = size - 1 ; i >= 1 ; i-- ) update(i);
	}
	@SuppressWarnings("unchecked")
	public void initData(Collection<S> arr) {
		initData((S[])arr.toArray());
	}

	public void set(int p, S x) {
		assert 0 <= p && p < n;
		p += size;
		d[p] = x;
		for (int i = 1; i <= log; i++) update(p >> i);
	}

	public S get(int p) {
		assert 0 <= p && p < n;
		return d[p + size];
	}

	public S query(int l, int r) {
		assert 0 <= l && l <= r && r <= n;
		S sml = e(); S smr = e();
		l += size;
		r += size;

		while ( l < r ) {
			if ( (l & 1) == 1 ) sml = op(sml, d[l++]);
			if ( (r & 1) == 1 ) smr = op(d[--r], smr);
			l >>= 1;
			r >>= 1;
		}
		return op(sml, smr);
	}

	public S allQuery() {
		return d[1];
	}

	public int maxRight(int l, Predicate<S> f) {
		assert 0 <= l && l <= n;
		assert f.test(e());
		if ( l == n ) return n;
		l += size;
		S sm = e();
		do {
			while ( l % 2 == 0 ) l >>= 1;
			if ( !f.test(op(sm, d[l])) ) {
				while ( l < size ) {
					l = (2 * l);
					if ( f.test(op(sm, d[l])) ) {
						sm = op(sm, d[l]);
						l++;
					}
				}
				return l - size;
			}
			sm = op(sm, d[l]);
			l++;
		} while ((l & -l) != l);
		return n;
	}

	public int minLeft(int r, Predicate<S> f) {
		assert 0 <= r && r <= n;
		assert f.test(e());
		if ( r == 0 ) return 0;
		r += size;
		S sm = e();
		do {
			r--;
			while ( r > 1 && r % 2 == 1 ) r >>= 1;
			if ( !f.test(op(d[r], sm)) ) {
				while ( r < size ) {
					r = (2 * r + 1);
					if ( f.test(op(d[r], sm)) ) {
						sm = op(d[r], sm);
						r--;
					}
				}
				return r + 1 - size;
			}
			sm = op(d[r], sm);
		} while ((r & -r) != r);
		return 0;
	}

	private void update(int k) {
		d[k] = op(d[2 * k], d[2 * k + 1]);
	}
}
