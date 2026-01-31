package data_structure;
import java.util.Collection;
import java.util.function.IntPredicate;

// Ported to Java from the original C++ implementation by Atcoder.
// Original Source: hhttps://github.com/atcoder/ac-library/blob/master/atcoder/lazysegtree.hpp
public class IntLazySegmentTree {
	private int n = 0;
	private int size = 1;
	private int log = 0;
	private int[] d = null;
	private int[] lz = null;

	private IntLazyOperator _op = null;
	private int op(int a, int b) { return _op.op(a, b); }
	private int e() { return _op.e(); }
	private int mapping(int x, int a, int len) { return _op.mapping(x, a, len); }
	private int composition(int x, int y) { return _op.composition(x, y); }
	private int identity() { return _op.identity(); }
	
	public IntLazySegmentTree(int n, IntLazyOperator op) {
		initialize(n, op);
		for (int i = 0; i < size * 2; i++) d[i] = e();
		for (int i = 0; i < size; i++) lz[i] = identity();
	}
	public IntLazySegmentTree(int[] arr, IntLazyOperator op) {
		initialize(arr.length, op);
		for (int i = 0; i < size * 2; i++) d[i] = e();
		for (int i = 0; i < size; i++) lz[i] = identity();
		initData(arr);
	}
	public IntLazySegmentTree(Collection<Integer> arr, IntLazyOperator op) {
		initialize(arr.size(), op);
		for (int i = 0; i < size * 2; i++) d[i] = e();
		for (int i = 0; i < size; i++) lz[i] = identity();
		initData(arr);
	}
	private void initialize(int n, IntLazyOperator op) {
		this._op = op;
		this.n = n;
		this.size = 1;
		while ( size < n ) size *= 2;
		log = Integer.numberOfTrailingZeros(size);
		
		d = new int[this.size * 2];
		lz = new int[this.size];
	}

	public void initData(int[] arr) {
		assert arr.length == n;
		System.arraycopy(arr, 0, d, size, n);
		for (int i = size - 1; i >= 1; i--) update(i);
	}
	public void initData(Collection<Integer> arr) {
		int[] iarr = new int[arr.size()];
		int i = 0;
		for (Integer a: arr) iarr[i++] = a;
		initData(iarr);
	}
	public void set(int p, int x) {
		p += size;
		for (int i = log; i >= 1; i--) push(p >> i, 1 << i);
		d[p] = x;
		for (int i = 1; i <= log; i++) update(p >> i);
	}
	public int get(int p) {
		p += size;
		for (int i = log; i >= 1; i--) push(p >> i, 1 << i);
		return d[p];
	}
	public int query(int l, int r) {
		if ( l == r ) return e();
		l += size;
		r += size;
		for (int i = log; i >= 1; i--) {
			if ( ((l >> i) << i) != l ) push(l >> i, 1 << i);
			if ( ((r >> i) << i) != r ) push((r - 1) >> i, 1 << i);
		}

		int sml = e(); int smr = e();
		while (l < r) {
			if ( (l & 1) != 0 ) sml = op(sml, d[l++]);
			if ( (r & 1) != 0 ) smr = op(d[--r], smr);
			l >>= 1;
			r >>= 1;
		}
		return op(sml, smr);
	}
	public int allQuery() { return d[1]; }
	public void apply(int p, int f) {
		p += size;
		for (int i = log; i >= 1; i--) push(p >> i, 1 << i);
		d[p] = mapping(f, d[p], 1);
		for (int i = 1; i <= log; i++) update(p >> i);
	}
	public void apply(int l, int r, int f) {
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
	public int maxRight(int l, IntPredicate g) {
		assert 0 <= l && l <= n;
		assert g.test(e());
		if (l == n) return n;
		l += size;
		for (int i = log; i >= 1; i--) push(l >> i, 1 << i);
		int sm = e();
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
	public int minLeft(int r, IntPredicate g) {
		assert 0 <= r && r <= n;
		assert g.test(e());
		if (r == 0) return 0;
		r += size;
		for (int i = log; i >= 1; i--) push((r - 1) >> i, 1 << i);
		int sm = e();
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
	private void allApply(int k, int f, int len) {
		d[k] = mapping(f, d[k], len);
		if ( k < size ) lz[k] = composition(f, lz[k]);
	}
	private void push(int k, int len) {
		allApply(2 * k, lz[k], len >> 1);
		allApply(2 * k + 1, lz[k], len >> 1);
		lz[k] = identity();
	}
}
interface IntLazyOperator {
	public int op(int a, int b);
	public int e();
	public int mapping(int x, int a, int len);
	public int composition(int x, int y);
	public int identity();
}
class IntLazySumUpdate implements IntLazyOperator {
	@Override
	public int op(int a, int b) { return a + b; }
	@Override
	public int e() { return 0; }
	@Override
	public int mapping(int x, int a, int len) { return x == identity() ? a : x * len; }
	@Override
	public int composition(int x, int y) { return x == identity() ? y : x; }
	@Override
	public int identity() { return Integer.MAX_VALUE; }
}
class IntLazySumAdd implements IntLazyOperator {
	@Override
	public int op(int a, int b) { return a + b; }
	@Override
	public int e() { return 0; }
	@Override
	public int mapping(int x, int a, int len) { return x * len + a; }
	@Override
	public int composition(int x, int y) { return x + y; }
	@Override
	public int identity() { return 0; }
}
class IntLazyMinUpdate implements IntLazyOperator {
	@Override
	public int op(int a, int b) { return Math.min(a, b); }
	@Override
	public int e() { return Integer.MAX_VALUE; }
	@Override
	public int mapping(int x, int a, int len) { return x == identity() ? a : x; }
	@Override
	public int composition(int x, int y) { return x == identity() ? y : x; }
	@Override
	public int identity() { return Integer.MAX_VALUE; }
}
class IntLazyMinAdd implements IntLazyOperator {
	@Override
	public int op(int a, int b) { return Math.min(a, b); }
	@Override
	public int e() { return Integer.MAX_VALUE; }
	@Override
	public int mapping(int x, int a, int len) { return a == e() ? a : x + a; }
	@Override
	public int composition(int x, int y) { return x + y; }
	@Override
	public int identity() { return 0; }
}
class IntLazyMaxUpdate implements IntLazyOperator {
	@Override
	public int op(int a, int b) { return Math.max(a, b); }
	@Override
	public int e() { return Integer.MIN_VALUE; }
	@Override
	public int mapping(int x, int a, int len) { return x == identity() ? a : x; }
	@Override
	public int composition(int x, int y) { return x == identity() ? y : x; }
	@Override
	public int identity() { return Integer.MAX_VALUE; }
}
class IntLazyMaxAdd implements IntLazyOperator {
	@Override
	public int op(int a, int b) { return Math.max(a, b); }
	@Override
	public int e() { return Integer.MIN_VALUE; }
	@Override
	public int mapping(int x, int a, int len) { return a == e() ? a : x + a; }
	@Override
	public int composition(int x, int y) { return x + y; }
	@Override
	public int identity() { return 0; }
}

