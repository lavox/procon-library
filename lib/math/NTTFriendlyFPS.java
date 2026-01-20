package math;

// Ported to Java from the original C++ implementation by Nyaan.
// Original Source: https://nyaannyaan.github.io/library/fps/ntt-friendly-fps.hpp
public class NTTFriendlyFPS extends FormalPowerSeries {
	public NTTFriendlyFPS(int n, ModOperation mop, NTT ntt) {
		super(n, mop, ntt);
	}
	public NTTFriendlyFPS(int n, int capacity, ModOperation mop, NTT ntt) {
		super(n, capacity, mop, ntt);
	}
	public NTTFriendlyFPS(int[] t, ModOperation mop, NTT ntt) {
		super(t, mop, ntt);
	}
	public NTTFriendlyFPS(int[] t, int size, int capacity, ModOperation mop, NTT ntt) {
		super(t, size, capacity, mop, ntt);
	}
	public NTTFriendlyFPS(FormalPowerSeries from) {
		super(from);
	}
	public NTTFriendlyFPS(FormalPowerSeries from, int size, int capacity) {
		super(from, size, capacity);
	}

	@Override
	public NTTFriendlyFPS clone() {
		return new NTTFriendlyFPS(this);
	}
	@Override
	public NTTFriendlyFPS clone(int size, int capacity) {
		return new NTTFriendlyFPS(this, size, capacity);
	}
	@Override
	public FormalPowerSeries createNewSeries(int n) {
		return new NTTFriendlyFPS(n, this.mop, this.ntt);
	}
	@Override
	public FormalPowerSeries createNewSeries(int n, int capacity) {
		return new NTTFriendlyFPS(n, capacity, this.mop, this.ntt);
	}
	@Override
	public FormalPowerSeries createNewSeries(int[] t) {
		return new NTTFriendlyFPS(t, this.mop, this.ntt);
	}
	@Override
	public FormalPowerSeries createNewSeries(int[] t, int size, int capacity) {
		return new NTTFriendlyFPS(t, size, capacity, this.mop, this.ntt);
	}

	@Override
	public FormalPowerSeries mulAsn(FormalPowerSeries r) {
		if (this.empty() || r.empty()) {
			this.clear();
			return this;
		}
		this.t = ntt.multiply(this.t, this.size, r.t, r.size);
		this.size = t.length;
		return this;
	}

	@Override
	public void ntt() {
		ntt.ntt(t, size);
	}

	@Override
	public void intt() {
		ntt.intt(t, size);
	}

	@Override
	public void ntt_doubling() {
		t = ntt.ntt_doubling(t, size);
		size = size * 2;
	}

	@Override
	public FormalPowerSeries inv(int deg) {
		assert t[0] != 0;
		if (deg == -1) deg = this.size();
		FormalPowerSeries res = createNewSeries(deg);
		res.t[0] = mop.inv(t[0]);
		int deg2 = 2;
		while (deg2 < deg) deg2 <<= 1;
		FormalPowerSeries f = createNewSeries(deg2);
		FormalPowerSeries g = createNewSeries(deg2);
		for (int d = 1; d < deg; d <<= 1) {
			f.size = 2 * d;
			g.size = 2 * d;
			f.copyFrom(this, 0, Math.min(size, 2 * d));
			if (size < 2 * d) f.clear(size, 2 * d);
			g.copyFrom(res, 0, d);
			g.clear(d, 2 * d);
			f.ntt();
			g.ntt();
			for (int j = 0; j < 2 * d; j++) f.t[j] = mop.mul(f.t[j], g.t[j]);
			f.intt();
			f.clear(0, d);
			f.ntt();
			for (int j = 0; j < 2 * d; j++) f.t[j] = mop.mul(f.t[j], g.t[j]);
			f.intt();
			for (int j = d; j < Math.min(2 * d, deg); j++) res.t[j] = mop.sub(0, f.t[j]);
		}
		return res.preAsn(deg);
	}

	@Override
	public FormalPowerSeries exp(int deg) {
		assert this.size() == 0 || t[0] == 0;
		if (deg == -1) deg = this.size();
		mop.prepareFactorial(deg);

		FormalPowerSeries b = createNewSeries(new int[] {1, 1 < this.size() ? t[1] : 0});
		FormalPowerSeries c = createNewSeries(new int[] {1});
		FormalPowerSeries z1 = null;
		FormalPowerSeries z2 = createNewSeries(new int[] {1, 1});
		for (int m = 2; m < deg; m *= 2) {
			FormalPowerSeries y = b.clone(b.size, 2 * m);
			y.resize(2 * m);
			y.ntt();
			z1 = z2.clone();
			FormalPowerSeries z = createNewSeries(m);
			for (int i = 0; i < m; ++i) z.t[i] = mop.mul(y.t[i], z1.t[i]);
			z.intt();
			z.clear(0, m / 2);
			z.ntt();
			for (int i = 0; i < m; ++i) z.t[i] = mop.mul(z.t[i], mop.sub(0, z1.t[i]));
			z.intt();
			int oldSize = c.size;
			c.resize(c.size + z.size - m / 2);
			System.arraycopy(z.t, m / 2, c.t, oldSize, z.size - m / 2);
			z2 = c.clone(2 * m, 2 * m);
			z2.ntt();
			FormalPowerSeries x = this.clone(m, m * 2);
			x = x.diffAsn();
			x.append(0);
			x.ntt();
			for (int i = 0; i < m; ++i) x.t[i] = mop.mul(x.t[i], y.t[i]);
			x.intt();
			x.subAsn(b.diff());
			x.resize(2 * m);
			for (int i = 0; i < m - 1; ++i) {x.t[m + i] = x.t[i]; x.t[i] = 0;}
			x.ntt();
			for (int i = 0; i < 2 * m; ++i) x.t[i] = mop.mul(x.t[i], z2.t[i]);
			x.intt();
			x.resize(x.size - 1);
			x = x.integralAsn();
			for (int i = m; i < Math.min(this.size(), 2 * m); ++i) x.t[i] = mop.add(x.t[i], t[i]);
			x.clear(0, m);
			x.ntt();
			for (int i = 0; i < 2 * m; ++i) x.t[i] = mop.mul(x.t[i], y.t[i]);
			x.intt();
			oldSize = b.size;
			b.resize(b.size + x.size - m);
			System.arraycopy(x.t, m, b.t, oldSize, x.size - m);
		}
		return createNewSeries(b.t, deg, deg);
	}
}
