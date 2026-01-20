package math;

import java.util.Arrays;

// Ported to Java from the original C++ implementation by Nyaan.
// Original Source: https://nyaannyaan.github.io/library/fps/formal-power-series.hpp
public abstract class FormalPowerSeries implements Cloneable {
	protected int[] t = null;
	protected int size = 0;

	protected FormalPowerSeries(int n, ModOperation mop, NTT ntt) {
		this(n, n, mop, ntt);
	}
	protected FormalPowerSeries(int n, int capacity, ModOperation mop, NTT ntt) {
		assert n <= capacity;
		this.t = new int[capacity];
		this.size = n;
		this.mop = mop;
		this.ntt = ntt;
	}
	protected FormalPowerSeries(int[] t, ModOperation mop, NTT ntt) {
		this(t, t.length, t.length, mop, ntt);
	}
	protected FormalPowerSeries(int[] t, int size, int capacity, ModOperation mop, NTT ntt) {
		assert size <= capacity;
		this.t = new int[capacity];
		System.arraycopy(t, 0, this.t, 0, Math.min(t.length, size));
		this.size = size;
		this.mop = mop;
		this.ntt = ntt;
	}
	protected FormalPowerSeries(FormalPowerSeries from) {
		this(from, from.size, from.size);
	}
	protected FormalPowerSeries(FormalPowerSeries from, int size, int capacity) {
		assert size <= capacity;
		this.t = new int[capacity];
		System.arraycopy(from.t, 0, this.t, 0, Math.min(from.size, size));
		this.size = size;
		this.mop = from.mop;
		this.ntt = from.ntt;
	}
	public void copyFrom(FormalPowerSeries from) {
		copyFrom(from, 0, from.size);
	}
	public void copyFrom(FormalPowerSeries from, int start, int end) {
		assert from.size() >= end;
		System.arraycopy(from.t, start, this.t, start, end - start);
	}
	public abstract FormalPowerSeries createNewSeries(int n);
	public abstract FormalPowerSeries createNewSeries(int n, int capacity);
	public abstract FormalPowerSeries createNewSeries(int[] t);
	public abstract FormalPowerSeries createNewSeries(int[] t, int size, int capacity);

	@Override
	public abstract FormalPowerSeries clone();
	public abstract FormalPowerSeries clone(int size, int capacity);

	public int get(int i) { return t[i]; }
	public void set(int i, int v) { t[i] = v; }
	public int size() { return size; }
	public boolean empty() { return size() == 0; }
	public void clear() { t = new int[0]; size = 0; }
	public void clear(int start, int end) { Arrays.fill(t, start, end, 0); }
	public void resize(int len) {
		if (len > this.size) {
			if (t.length < len) {
				int nlen = Math.max(len, size * 2);
				int[] nt = new int[nlen];
				System.arraycopy(this.t, 0, nt, 0, size);
				this.t = nt;
			} else {
				Arrays.fill(this.t, this.size, len, 0);
			}
		}
		this.size = len;
	}
	public void append(int v) {
		ensureCapacity(size + 1);
		t[size++] = v;
	}
	public void ensureCapacity(int minLen) {
		if (minLen > t.length) {
			int nlen = Math.max(minLen, size * 2);
			int[] nt = new int[nlen];
			System.arraycopy(this.t, 0, nt, 0, size);
			this.t = nt;
		}
	}

	public FormalPowerSeries addAsn(FormalPowerSeries r) {
		if (r.size() > this.size()) this.resize(r.size());
		for (int i = 0; i < r.size(); i++) t[i] = mop.add(t[i], r.t[i]);
		return this;
	}

	public FormalPowerSeries addAsn(int v) {
		if (this.empty()) this.resize(1);
		t[0] = mop.add(t[0], v);
		return this;
	}

	public FormalPowerSeries subAsn(FormalPowerSeries r) {
		if (r.size() > this.size()) this.resize(r.size());
		for (int i = 0; i < r.size(); i++) t[i] = mop.sub(t[i], r.t[i]);
		return this;
	}

	public FormalPowerSeries subAsn(int v) {
		if (this.empty()) this.resize(1);
		t[0] = mop.sub(t[0], v);
		return this;
	}

	public FormalPowerSeries mulAsn(int v) {
		for (int k = 0; k < size(); k++) t[k] = mop.mul(t[k], v);
		return this;
	}

	public FormalPowerSeries divAsn(FormalPowerSeries r) {
		if (this.size() < r.size()) {
			this.clear();
			return this;
		}
		int n = this.size() - r.size() + 1;
		if (r.size() <= 64) {
			FormalPowerSeries f = this.clone();
			FormalPowerSeries g = r.clone();

			g.shrink();
			int coeff = mop.inv(g.t[g.size() - 1]);
			for (int i = 0; i < g.size(); i++) g.t[i] = mop.mul(g.t[i], coeff);
			int deg = f.size() - g.size() + 1;
			int gs = g.size();
			FormalPowerSeries quo = createNewSeries(deg);
			for (int i = deg - 1; i >= 0; i--) {
				quo.t[i] = f.t[i + gs - 1];
				for (int j = 0; j < gs; j++) f.t[i + j] = mop.sub(f.t[i + j], mop.mul(quo.t[i], g.t[j]));
			}
			this.t = quo.mul(coeff).t;
			this.resize(n);
			return this;
		}
		this.t = this.revAsn().preAsn(n).mulAsn(r.rev().inv(n)).preAsn(n).revAsn().t;
		return this;
	}

	public FormalPowerSeries modAsn(FormalPowerSeries r) {
		this.subAsn(this.div(r).mulAsn(r));
		shrink();
		return this;
	}

	public FormalPowerSeries add(FormalPowerSeries r) { return this.clone().addAsn(r); }
	public FormalPowerSeries add(int v) { return this.clone().addAsn(v); }
	public FormalPowerSeries sub(FormalPowerSeries r) { return this.clone().subAsn(r); }
	public FormalPowerSeries sub(int v) { return this.clone().subAsn(v); }
	public FormalPowerSeries mul(FormalPowerSeries r) { return this.clone().mulAsn(r); }
	public FormalPowerSeries mul(int v) { return this.clone().mulAsn(v); }
	public FormalPowerSeries div(FormalPowerSeries r) { return this.clone().divAsn(r); }
	public FormalPowerSeries mod(FormalPowerSeries r) { return this.clone().modAsn(r); }
	public FormalPowerSeries minus() {
		FormalPowerSeries ret = this.createNewSeries(size);
		for (int i = 0; i < size; i++) ret.t[i] = mop.sub(0, t[i]);
		return ret;
	}

	public void shrink() {
		while (size > 0 && t[size - 1] == 0) size--;
	}

	public FormalPowerSeries rev() {
		FormalPowerSeries ret = createNewSeries(this.size());
		for (int i = 0; i < this.size(); i++) ret.t[i] = t[this.size() - i - 1];
		return ret;
	}
	public FormalPowerSeries revAsn() {
		for (int i = 0; i < this.size() / 2; i++) swap(t, i, size - i - 1);
		return this;
	}
	private final void swap(int[] t, int i, int j) {
		int tmp = t[i];
		t[i] = t[j];
		t[j] = tmp;
	}

	public FormalPowerSeries dot(FormalPowerSeries r) {
		FormalPowerSeries ret = createNewSeries(Math.min(this.size(), r.size()));
		for (int i = 0; i < ret.size(); i++) ret.t[i] = mop.mul(t[i], r.t[i]);
		return ret;
	}

	// 前 sz 項を取ってくる。sz に足りない項は 0 埋めする
	public FormalPowerSeries pre(int sz) {
		return clone(sz, sz);
	}
	public FormalPowerSeries preAsn(int sz) {
		this.resize(sz);
		return this;
	}

	public FormalPowerSeries rshift(int sz) {
		if (this.size() <= sz) return createNewSeries(0);
		FormalPowerSeries ret = createNewSeries(size - sz);
		System.arraycopy(this.t, sz, ret.t, 0, size - sz);
		return ret;
	}
	public FormalPowerSeries rshiftAsn(int sz) {
		if (this.size() <= sz) { resize(0); return this; }
		System.arraycopy(this.t, sz, this.t, 0, size - sz);
		this.size = size - sz;
		return this;
	}

	public FormalPowerSeries lshift(int sz) {
		FormalPowerSeries ret = createNewSeries(size + sz);
		System.arraycopy(this.t, 0, ret.t, sz, size);
		return ret;
	}
	public FormalPowerSeries lshiftAsn(int sz) {
		int oldSize = size;
		resize(size + sz);
		System.arraycopy(this.t, 0, this.t, sz, oldSize);
		this.clear(0, sz);
		return this;
	}

	public FormalPowerSeries diff() {
		final int n = size;
		FormalPowerSeries ret = createNewSeries(Math.max(0, n - 1));
		int coeff = 1;
		for (int i = 1; i < n; i++) {
			ret.t[i - 1] = mop.mul(t[i], coeff);
			coeff = mop.add(coeff, 1);
		}
		return ret;
	}
	public FormalPowerSeries diffAsn() {
		final int n = size;
		int coeff = 1;
		for (int i = 1; i < n; i++) {
			t[i - 1] = mop.mul(t[i], coeff);
			coeff = mop.add(coeff, 1);
		}
		size = Math.max(0, n - 1);
		return this;
	}

	public FormalPowerSeries integral() {
		final int n = size;
		FormalPowerSeries ret = createNewSeries(n + 1);
		if (n > 0) ret.t[1] = 1;
		int mod = mop.m;
		for (int i = 2; i <= n; i++) ret.t[i] = mop.mul(mop.sub(0, ret.t[mod % i]), mod / i);
		for (int i = 0; i < n; i++) ret.t[i + 1] = mop.mul(ret.t[i + 1], t[i]);
		return ret;
	}
	public FormalPowerSeries integralAsn() {
		final int n = size();
		mop.prepareFactorial(n);
		if (t.length < n + 1) {
			t = Arrays.copyOf(t, n + 1);
		}
		for (int i = n; i >= 1; i--) {
			t[i] = mop.mul(t[i - 1], mop.inv(i));
		}
		size = size + 1;
		return this;
	}

	public int eval(int x) {
		int r = 0, w = 1;
		for (int i = 0; i < size; i++) {
			int v = t[i];
			r = mop.add(r, mop.mul(w, v));
			w = mop.mul(w, x);
		}
		return r;
	}

	public FormalPowerSeries log() { return log(-1); }
	public FormalPowerSeries log(int deg) {
		assert(!empty() && t[0] == 1);
		if (deg == -1) deg = size();
		return this.diff().mulAsn(this.inv(deg)).preAsn(deg - 1).integral();
	}

	public FormalPowerSeries pow(long k) { return pow(k, -1); }
	public FormalPowerSeries pow(long k, int deg) {
		final int n = size();
		if (deg == -1) deg = n;
		if (k == 0) {
			FormalPowerSeries ret = createNewSeries(deg);
			if (deg > 0) ret.t[0] = 1;
			return ret;
		}
		for (int i = 0; i < n; i++) {
			if (this.t[i] != 0) {
				int rev = mop.div(1, t[i]);
				FormalPowerSeries ret = this.mul(rev).rshiftAsn(i).log(deg).mulAsn(mop.mod(k)).exp(deg);
				ret.mulAsn(mop.pow(t[i], k));
				long ls = i * k;
				if (ls >= deg) {
					ret = createNewSeries(0);
				} else {
					ret = ret.lshiftAsn((int)ls).preAsn(deg);
				}
				// ret = (ret << (i * k)).pre(deg);
				if (ret.size() < deg) ret.resize(deg);
				return ret;
			}
			if ((i + 1) * k >= deg) return createNewSeries(deg);
		}
		return createNewSeries(deg);
	}
	public int[] toArray() {
		return Arrays.copyOf(t, size);
	}

	protected final ModOperation mop;
	protected final NTT ntt;
	public abstract FormalPowerSeries mulAsn(FormalPowerSeries r);
	public abstract void ntt();
	public abstract void intt();
	public abstract void ntt_doubling();
	public FormalPowerSeries inv() { return inv(-1); }
	public abstract FormalPowerSeries inv(int deg);
	public FormalPowerSeries exp() { return exp(-1); }
	public abstract FormalPowerSeries exp(int deg);
}
