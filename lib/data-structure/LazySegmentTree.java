import java.lang.reflect.Array;
import java.util.Collection;
import java.util.function.Predicate;

class LazySegmentTree<S, F> {
	private int n = 0;
	private int size = 1;
	private int log = 0;
	private S[] d = null;
	private F[] lz = null;

	private LazyOperator<S, F> _op = null;
	private S op(S a, S b) { return _op.op(a, b); }
	private S e() { return _op.e(); }
	private S mapping(F x, S a, int len) { return _op.mapping(x, a, len); }
	private F composition(F x, F y) { return _op.composition(x, y); }
	private F identity() { return _op.identity(); }
	
	public LazySegmentTree(int n, LazyOperator<S, F> op) {
		initialize(n, op);
		for (int i = 0; i < size * 2; i++) d[i] = e();
		for (int i = 0; i < size; i++) lz[i] = identity();
	}
	public LazySegmentTree(S[] arr, LazyOperator<S, F> op) {
		initialize(arr.length, op);
		for (int i = 0; i < size * 2; i++) d[i] = e();
		for (int i = 0; i < size; i++) lz[i] = identity();
		initData(arr);
	}
	public LazySegmentTree(Collection<S> arr, LazyOperator<S, F> op) {
		initialize(arr.size(), op);
		for (int i = 0; i < size * 2; i++) d[i] = e();
		for (int i = 0; i < size; i++) lz[i] = identity();
		initData(arr);
	}
	private void initialize(int n, LazyOperator<S, F> op) {
		this._op = op;
		this.n = n;
		this.size = 1;
		while ( size < n ) size *= 2;
		log = Integer.numberOfTrailingZeros(size);
		
		d = (S[]) Array.newInstance(e().getClass(), this.size * 2);
		lz = (F[]) Array.newInstance(identity().getClass(), this.size);
	}

	public void initData(S[] arr) {
		assert arr.length == n;
		System.arraycopy(arr, 0, d, size, n);
		for (int i = size - 1; i >= 1; i--) update(i);
	}
	public void initData(Collection<S> arr) {
		initData((S[])arr.toArray());
	}
	public void set(int p, S x) {
		p += size;
		for (int i = log; i >= 1; i--) push(p >> i, 1 << i);
		d[p] = x;
		for (int i = 1; i <= log; i++) update(p >> i);
	}
	public S get(int p) {
		p += size;
		for (int i = log; i >= 1; i--) push(p >> i, 1 << i);
		return d[p];
	}
	public S query(int l, int r) {
		if ( l == r ) return e();
		l += size;
		r += size;
		for (int i = log; i >= 1; i--) {
			if ( ((l >> i) << i) != l ) push(l >> i, 1 << i);
			if ( ((r >> i) << i) != r ) push((r - 1) >> i, 1 << i);
		}

		S sml = e(); S smr = e();
		while (l < r) {
			if ( (l & 1) != 0 ) sml = op(sml, d[l++]);
			if ( (r & 1) != 0 ) smr = op(d[--r], smr);
			l >>= 1;
			r >>= 1;
		}
		return op(sml, smr);
	}
	public S allQuery() { return d[1]; }
	public void apply(int p, F f) {
		p += size;
		for (int i = log; i >= 1; i--) push(p >> i, 1 << i);
		d[p] = mapping(f, d[p], 1);
		for (int i = 1; i <= log; i++) update(p >> i);
	}
	public void apply(int l, int r, F f) {
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
	public int maxRight(int l, Predicate<S> g) {
		assert 0 <= l && l <= n;
		assert g.test(e());
		if (l == n) return n;
		l += size;
		for (int i = log; i >= 1; i--) push(l >> i, 1 << i);
		S sm = e();
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
	public int minLeft(int r, Predicate<S> g) {
		assert 0 <= r && r <= n;
		assert g.test(e());
		if (r == 0) return 0;
		r += size;
		for (int i = log; i >= 1; i--) push((r - 1) >> i, 1 << i);
		S sm = e();
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
	private void allApply(int k, F f, int len) {
		d[k] = mapping(f, d[k], len);
		if ( k < size ) lz[k] = composition(f, lz[k]);
	}
	private void push(int k, int len) {
		allApply(2 * k, lz[k], len >> 1);
		allApply(2 * k + 1, lz[k], len >> 1);
		lz[k] = identity();
	}
}
interface LazyOperator<S, F> {
	public S op(S a, S b);
	public S e();
	public S mapping(F x, S a, int len);
	public F composition(F x, F y);
	public F identity();
}
