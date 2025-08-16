package data_structure;
import java.util.Collection;
import java.util.function.LongBinaryOperator;
import java.util.function.LongPredicate;

public class LongSegmentTree {
	int n = 0;
	int size = 1;
	int log = 0;
	long[] d = null;

	long _e = 0;
	LongBinaryOperator _op = null;

	long e() {
		return _e;
	}
	long op(long a, long b) {
		return _op.applyAsLong(a, b);
	}

	public LongSegmentTree(int n, LongBinaryOperator op, long e) {
		initialize(n, op, e);
		for (int i = 0; i < 2 * size; i++) d[i] = e();
	}
	public LongSegmentTree(long[] arr, LongBinaryOperator op, long e) {
		this(arr.length, op, e);
		initData(arr);
	}
	public LongSegmentTree(Collection<Long> arr, LongBinaryOperator op, long e) {
		this(arr.size(), op, e);
		initData(arr);
	}
	private void initialize(int n, LongBinaryOperator op, long e) {
		this.n = n;
		this._op = op;
		this._e = e;

		size = 1;
		while ( size < n ) size *= 2;
		log = Integer.numberOfTrailingZeros(size);
		d = new long[2 * size];
	}

	public void initData(long[] arr) {
		assert arr.length == n;
		System.arraycopy(arr, 0, d, size, n);
		for ( int i = size - 1 ; i >= 1 ; i-- ) update(i);
	}
	public void initData(Collection<Long> arr) {
		long[] larr = new long[arr.size()];
		int i = 0;
		for (Long a: arr) larr[i++] = a;
		initData(larr);
	}

	public void set(int p, long x) {
		assert 0 <= p && p < n;
		p += size;
		d[p] = x;
		for (int i = 1; i <= log; i++) update(p >> i);
	}

	public long get(int p) {
		assert 0 <= p && p < n;
		return d[p + size];
	}

	public long query(int l, int r) {
		assert 0 <= l && l <= r && r <= n;
		long sml = e(); long smr = e();
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

	public long allQuery() {
		return d[1];
	}

	public int maxRight(int l, LongPredicate f) {
		assert 0 <= l && l <= n;
		assert f.test(e());
		if ( l == n ) return n;
		l += size;
		long sm = e();
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

	public int minLeft(int r, LongPredicate f) {
		assert 0 <= r && r <= n;
		assert f.test(e());
		if ( r == 0 ) return 0;
		r += size;
		long sm = e();
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
