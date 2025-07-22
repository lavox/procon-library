import java.util.Arrays;

class FenwickTree {
	private int _n = 0;
	private long[] data = null;
	public FenwickTree(int n) {
		this._n = n;
		this.data = new long[n];
	}
	public FenwickTree(long[] data) {
		this._n = data.length;
		this.data = Arrays.copyOf(data, _n);
		for (int i = 1; i <= _n; i++) {
			int p = i + (i & -i);
			if (p <= _n) this.data[p - 1] += this.data[i - 1];
		}
	}
	public void add(int p, long x) {
		assert 0 <= p && p < _n;
		p++;
		while (p <= _n) {
			data[p - 1] += x;
			p += p & -p;
		}
	}
	public long sum(int l, int r) {
		assert 0 <= l && l <= r && r <= _n;
		return sum(r) - sum(l);
	}
	public long sum(int r) {
		assert 0 <= r && r <= _n;
		long s = 0;
		while (r > 0) {
			s += data[r - 1];
			r -= r & -r;
		}
		return s;
	}
}
