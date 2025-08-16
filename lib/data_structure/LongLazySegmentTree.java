package data_structure;
import java.util.Collection;
import java.util.function.LongPredicate;

public class LongLazySegmentTree {
	private int n = 0;
	private int size = 1;
	private int log = 0;
	private long[] d = null;
	private long[] lz = null;

	private LongLazyOperator _op = null;
	private long op(long a, long b) { return _op.op(a, b); }
	private long e() { return _op.e(); }
	private long mapping(long x, long a, int len) { return _op.mapping(x, a, len); }
	private long composition(long x, long y) { return _op.composition(x, y); }
	private long identity() { return _op.identity(); }
	
	public LongLazySegmentTree(int n, LongLazyOperator op) {
		initialize(n, op);
		for (int i = 0; i < size * 2; i++) d[i] = e();
		for (int i = 0; i < size; i++) lz[i] = identity();
	}
	public LongLazySegmentTree(long[] arr, LongLazyOperator op) {
		initialize(arr.length, op);
		for (int i = 0; i < size * 2; i++) d[i] = e();
		for (int i = 0; i < size; i++) lz[i] = identity();
		initData(arr);
	}
	public LongLazySegmentTree(Collection<Long> arr, LongLazyOperator op) {
		initialize(arr.size(), op);
		for (int i = 0; i < size * 2; i++) d[i] = e();
		for (int i = 0; i < size; i++) lz[i] = identity();
		initData(arr);
	}
	private void initialize(int n, LongLazyOperator op) {
		this._op = op;
		this.n = n;
		this.size = 1;
		while ( size < n ) size *= 2;
		log = Integer.numberOfTrailingZeros(size);
		
		d = new long[this.size * 2];
		lz = new long[this.size];
	}

	public void initData(long[] arr) {
		assert arr.length == n;
		System.arraycopy(arr, 0, d, size, n);
		for (int i = size - 1; i >= 1; i--) update(i);
	}
	public void initData(Collection<Long> arr) {
		long[] iarr = new long[arr.size()];
		int i = 0;
		for (Long a: arr) iarr[i++] = a;
		initData(iarr);
	}
	public void set(int p, long x) {
		p += size;
		for (int i = log; i >= 1; i--) push(p >> i, 1 << i);
		d[p] = x;
		for (int i = 1; i <= log; i++) update(p >> i);
	}
	public long get(int p) {
		p += size;
		for (int i = log; i >= 1; i--) push(p >> i, 1 << i);
		return d[p];
	}
	public long query(int l, int r) {
		if ( l == r ) return e();
		l += size;
		r += size;
		for (int i = log; i >= 1; i--) {
			if ( ((l >> i) << i) != l ) push(l >> i, 1 << i);
			if ( ((r >> i) << i) != r ) push((r - 1) >> i, 1 << i);
		}

		long sml = e(); long smr = e();
		while (l < r) {
			if ( (l & 1) != 0 ) sml = op(sml, d[l++]);
			if ( (r & 1) != 0 ) smr = op(d[--r], smr);
			l >>= 1;
			r >>= 1;
		}
		return op(sml, smr);
	}
	public long allQuery() { return d[1]; }
	public void apply(int p, long f) {
		p += size;
		for (int i = log; i >= 1; i--) push(p >> i, 1 << i);
		d[p] = mapping(f, d[p], 1);
		for (int i = 1; i <= log; i++) update(p >> i);
	}
	public void apply(int l, int r, long f) {
		if ( l == r ) return;
		l += size;
		r += size;
		for (int i = log; i >= 1; i--) {
			if ( ((l >> i) << i) != l ) push(l >> i, 1 << i);
			if ( ((r >> i) << i) != r ) push((r - 1) >> i, 1 << i);
		}
		{
			int l2 = l; int r2 = r; int len = 1;
			while ( l < r ) {
				if ( (l & 1) != 0 ) allApply(l++, f, len);
				if ( (r & 1) != 0 ) allApply(--r, f, len);
				l >>= 1;
				r >>= 1;
				len <<= 1;
			}
			l = l2;
			r = r2;
		}
		for (int i = 1; i <= log; i++) {
			if ( ((l >> i) << i) != l ) update(l >> i);
			if ( ((r >> i) << i) != r ) update((r - 1) >> i);
		}
	}
	public int maxRight(int l, LongPredicate g) {
		assert 0 <= l && l <= n;
		assert g.test(e());
		if (l == n) return n;
		l += size;
		for (int i = log; i >= 1; i--) push(l >> i, 1 << i);
		long sm = e();
		int len = 1;
		do {
			while (l % 2 == 0) {l >>= 1; len <<= 1;}
			if (!g.test(op(sm, d[l]))) {
				while (l < size) {
					push(l, len);
					l = (2 * l);
					len >>= 1;
					if (g.test(op(sm, d[l]))) {
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
	public int minLeft(int r, LongPredicate g) {
		assert 0 <= r && r <= n;
		assert g.test(e());
		if (r == 0) return 0;
		r += size;
		for (int i = log; i >= 1; i--) push((r - 1) >> i, 1 << i);
		long sm = e();
		int len = 1;
		do {
			r--;
			while (r > 1 && (r % 2) == 1) {r >>= 1; len <<= 1;}
			if (!g.test(op(d[r], sm))) {
				while (r < size) {
					push(r, len);
					r = (2 * r + 1);
					len >>= 1;
					if (g.test(op(d[r], sm))) {
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
	private void allApply(int k, long f, int len) {
		d[k] = mapping(f, d[k], len);
		if ( k < size ) lz[k] = composition(f, lz[k]);
	}
	private void push(int k, int len) {
		allApply(2 * k, lz[k], len >> 1);
		allApply(2 * k + 1, lz[k], len >> 1);
		lz[k] = identity();
	}
}
interface LongLazyOperator {
	public long op(long a, long b);
	public long e();
	public long mapping(long x, long a, int len);
	public long composition(long x, long y);
	public long identity();
}
class LongLazySumUpdate implements LongLazyOperator {
	@Override
	public long op(long a, long b) { return a + b; }
	@Override
	public long e() { return 0; }
	@Override
	public long mapping(long x, long a, int len) { return x == identity() ? a : x * len; }
	@Override
	public long composition(long x, long y) { return x == identity() ? y : x; }
	@Override
	public long identity() { return Long.MAX_VALUE; }
}
class LongLazySumAdd implements LongLazyOperator {
	@Override
	public long op(long a, long b) { return a + b; }
	@Override
	public long e() { return 0; }
	@Override
	public long mapping(long x, long a, int len) { return x * len + a; }
	@Override
	public long composition(long x, long y) { return x + y; }
	@Override
	public long identity() { return 0; }
}
class LongLazyMinUpdate implements LongLazyOperator {
	@Override
	public long op(long a, long b) { return Math.min(a, b); }
	@Override
	public long e() { return Long.MAX_VALUE; }
	@Override
	public long mapping(long x, long a, int len) { return x == identity() ? a : x; }
	@Override
	public long composition(long x, long y) { return x == identity() ? y : x; }
	@Override
	public long identity() { return Long.MAX_VALUE; }
}
class LongLazyMinAdd implements LongLazyOperator {
	@Override
	public long op(long a, long b) { return Math.min(a, b); }
	@Override
	public long e() { return Long.MAX_VALUE; }
	@Override
	public long mapping(long x, long a, int len) { return a == e() ? a : x + a; }
	@Override
	public long composition(long x, long y) { return x + y; }
	@Override
	public long identity() { return 0; }
}
class LongLazyMaxUpdate implements LongLazyOperator {
	@Override
	public long op(long a, long b) { return Math.max(a, b); }
	@Override
	public long e() { return Long.MIN_VALUE; }
	@Override
	public long mapping(long x, long a, int len) { return x == identity() ? a : x; }
	@Override
	public long composition(long x, long y) { return x == identity() ? y : x; }
	@Override
	public long identity() { return Long.MAX_VALUE; }
}
class LongLazyMaxAdd implements LongLazyOperator {
	@Override
	public long op(long a, long b) { return Math.max(a, b); }
	@Override
	public long e() { return Long.MIN_VALUE; }
	@Override
	public long mapping(long x, long a, int len) { return a == e() ? a : x + a; }
	@Override
	public long composition(long x, long y) { return x + y; }
	@Override
	public long identity() { return 0; }
}

