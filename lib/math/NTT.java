package math;

import java.util.Arrays;

// Ported to Java from the original C++ implementation by Nyaan.
// Original Source: https://nyaannyaan.github.io/library/ntt/ntt.hpp
public class NTT {
	private final ModOperation mop;
	private final int mod;
	private final int pr;
	private final int level;
	private int[] dw, dy;
	private int cur_k = -1;

	public NTT(ModOperation mop) { 
		this.mop = mop;
		this.mod = mop.m;
		this.pr = get_pr();
		this.level = Integer.numberOfTrailingZeros(this.mod - 1);
		dw = new int[level];
		dy = new int[level];
	}

	public int get_pr() {
		int _mod = mod;
		long[] ds = new long[32];
		int idx = 0;
		long m = _mod - 1;
		for (long i = 2; i * i <= m; ++i) {
			if (m % i == 0) {
				ds[idx++] = i;
				while (m % i == 0) m /= i;
			}
		}
		if (m != 1) ds[idx++] = m;

		int _pr = 2;
		while (true) {
			int flg = 1;
			for (int i = 0; i < idx; ++i) {
				long a = _pr, b = (_mod - 1) / ds[i], r = 1;
				while (b != 0) {
					if ((b & 1) == 1) r = r * a % _mod;
					a = a * a % _mod;
					b >>= 1;
				}
				if (r == 1) {
					flg = 0;
					break;
				}
			}
			if (flg == 1) break;
			++_pr;
		}
		return _pr;
	};

	private void setwy(int k) {
		if (k <= cur_k) return;

		int[] w = new int[level];
		int[] y = new int[level];
		w[k - 1] = pow(pr, (mod - 1) / (1 << k));
		y[k - 1] = inv(w[k - 1]);
		for (int i = k - 2; i > 0; --i) {
			w[i] = mul(w[i + 1], w[i + 1]);
			y[i] = mul(y[i + 1], y[i + 1]);
		}
		dw[1] = w[1]; dy[1] = y[1]; dw[2] = w[2]; dy[2] = y[2];
		for (int i = 3; i < k; ++i) {
			dw[i] = mul(mul(dw[i - 1], y[i - 2]), w[i]);
			dy[i] = mul(mul(dy[i - 1], w[i - 2]), y[i]);
		}
		cur_k = k;
	}

	private final int add(int a, int b) {
		a += b;
		if (a >= mod) a -= mod;
		return a;
	}
	private final int sub(int a, int b) {
		a -= b;
		if (a < 0) a += mod;
		return a;
	}
	private final int mul(long a, long b) {
		return (int)(a * b % mod);
	}
	private final int pow(int a, long n) {
		assert n >= 0;
		int r = 1;
		while ( n > 0 ) {
			if ( (n & 1L) != 0 ) r = mul(r, a);
			a = mul(a, a);
			n >>>= 1;
		}
		return r;
	}
	private final int inv(int a) {
		return pow(a, mod - 2);
	}

	private void fft4(int[] a, int asz, int k) {
		if (asz <= 1) return;
		if (k == 1) {
			int a1 = a[1];
			a[1] = sub(a[0], a[1]);
			a[0] = add(a[0], a1);
			return;
		}
		if ((k & 1) == 1) {
			int v = 1 << (k - 1);
			for (int j = 0; j < v; ++j) {
				int ajv = a[j + v];
				a[j + v] = sub(a[j], ajv);
				a[j] = add(a[j], ajv);
			}
		}

		final int[] dw = this.dw;
		int u = 1 << (2 + (k & 1));
		int v = 1 << (k - 2 - (k & 1));
		int imag = dw[1];
		while (v != 0) {
			{
				int j0 = 0;
				int j1 = v;
				int j2 = j1 + v;
				int j3 = j2 + v;
				for (; j0 < v; ++j0, ++j1, ++j2, ++j3) {
					int t0 = a[j0], t1 = a[j1], t2 = a[j2], t3 = a[j3];
					int t0p2 = add(t0, t2), t1p3 = add(t1, t3);
					int t0m2 = sub(t0, t2), t1m3 = mul(sub(t1, t3), imag);
					a[j0] = add(t0p2, t1p3); a[j1] = sub(t0p2, t1p3);
					a[j2] = add(t0m2, t1m3); a[j3] = sub(t0m2 ,t1m3);
				}
			}
			// jh >= 1
			int ww = 1, xx = dw[2], wx = 1;
			for (int jh = 4; jh < u;) {
				ww = mul(xx, xx); wx = mul(ww, xx);
				int j0 = jh * v;
				int je = j0 + v;
				int j2 = je + v;
				for (; j0 < je; ++j0, ++j2) {
					int t0 = a[j0], t1 = mul(a[j0 + v], xx), t2 = mul(a[j2], ww),
							t3 = mul(a[j2 + v], wx);
					int t0p2 = add(t0, t2), t1p3 = add(t1, t3);
					int t0m2 = sub(t0, t2), t1m3 = mul(sub(t1, t3), imag);
					a[j0] = add(t0p2, t1p3); a[j0 + v] = sub(t0p2, t1p3);
					a[j2] = add(t0m2, t1m3); a[j2 + v] = sub(t0m2, t1m3);
				}
				xx = mul(xx, dw[Integer.numberOfTrailingZeros(jh += 4)]);
			}
			u <<= 2;
			v >>= 2;
		}
	}

	private void ifft4(int[] a, int asz, int k) {
		if (asz <= 1) return;
		if (k == 1) {
			int a1 = a[1];
			a[1] = sub(a[0], a[1]);
			a[0] = add(a[0], a1);
			return;
		}
		final int[] dy = this.dy;
		int u = 1 << (k - 2);
		int v = 1;
		int imag = dy[1];
		while (u != 0) {
			// jh = 0
			{
				int j0 = 0;
				int j1 = v;
				int j2 = v + v;
				int j3 = j2 + v;
				for (; j0 < v; ++j0, ++j1, ++j2, ++j3) {
					int t0 = a[j0], t1 = a[j1], t2 = a[j2], t3 = a[j3];
					int t0p1 = add(t0, t1), t2p3 = add(t2, t3);
					int t0m1 = sub(t0, t1), t2m3 = mul(sub(t2, t3), imag);
					a[j0] = add(t0p1, t2p3); a[j2] = sub(t0p1, t2p3);
					a[j1] = add(t0m1, t2m3); a[j3] = sub(t0m1, t2m3);
				}
			}
			// jh >= 1
			int ww = 1, xx = dy[2], yy = 1;
			u <<= 2;
			for (int jh = 4; jh < u;) {
				ww = mul(xx, xx); yy = mul(xx, imag);
				int j0 = jh * v;
				int je = j0 + v;
				int j2 = je + v;
				for (; j0 < je; ++j0, ++j2) {
					int t0 = a[j0], t1 = a[j0 + v], t2 = a[j2], t3 = a[j2 + v];
					int t0p1 = add(t0, t1), t2p3 = add(t2, t3);
					int t0m1 = mul(sub(t0, t1), xx), t2m3 = mul(sub(t2, t3), yy);
					a[j0] = add(t0p1, t2p3); a[j2] = mul(sub(t0p1, t2p3), ww);
					a[j0 + v] = add(t0m1, t2m3); a[j2 + v] = mul(sub(t0m1, t2m3), ww);
				}
				xx = mul(xx, dy[Integer.numberOfTrailingZeros(jh += 4)]);
			}
			u >>= 4;
			v <<= 2;
		}
		if ((k & 1) == 1) {
			u = 1 << (k - 1);
			for (int j = 0; j < u; ++j) {
				int ajv = sub(a[j], a[j + u]);
				a[j] = add(a[j], a[j + u]);
				a[j + u] = ajv;
			}
		}
	}

	public void ntt(int[] a, int asz) {
		if (asz <= 1) return;
		int k = Integer.numberOfTrailingZeros(asz);
		setwy(k);
		fft4(a, asz, k);
	}

	public void intt(int[] a, int asz) {
		if (asz <= 1) return;
		int k = Integer.numberOfTrailingZeros(asz);
		setwy(k);
		ifft4(a, asz, k);
		int iv = inv(asz);
		for (int i = 0; i < asz; i++) a[i] = mul(a[i], iv);
	}

	public int[] multiply(int[] a, int asz, int[] b, int bsz) {
		int l = asz + bsz - 1;
		if (Math.min(asz, bsz) <= 40) {
			int[] s = new int[l];
			for (int i = 0; i < asz; ++i)
				for (int j = 0; j < bsz; ++j) s[i + j] = add(s[i + j], mul(a[i], b[j]));
			return s;
		}
		int k = 2, M = 4;
		while (M < l) {M <<= 1; ++k;}
		setwy(k);
		int[] s = new int[M];
		for (int i = 0; i < asz; ++i) s[i] = a[i];
		fft4(s, M, k);
		if (a == b) { // 判定を簡易にして高速化
		// if (arrayEquals(a, asz, b, bsz)) {
			for (int i = 0; i < M; ++i) s[i] = mul(s[i], s[i]);
		} else {
			int[] t = new int[M];
			for (int i = 0; i < bsz; ++i) t[i] = b[i];
			fft4(t, M, k);
			for (int i = 0; i < M; ++i) s[i] = mul(s[i], t[i]);
		}
		ifft4(s, M, k);

		s = Arrays.copyOf(s, l);
		int invm = inv(M);
		for (int i = 0; i < l; ++i) s[i] = mul(s[i], invm);
		return s;
	}
	private boolean arrayEquals(int[] a, int asz, int[] b, int bsz) {
		if (asz != bsz) return false;
		for (int i = 0; i < asz; i++) if (a[i] != b[i]) return false;
		return true;
	}

	public int[] ntt_doubling(int[] a, int asz) {
		int M = asz;
		int[] b = Arrays.copyOf(a, asz);
		intt(b, asz);
		int r = 1, zeta = pow(pr, (mod - 1) / (M << 1));
		for (int i = 0; i < M; i++) {b[i] = mul(b[i], r); r = mul(r, zeta);}
		ntt(b, asz);
		int[] ret = a;
		if (a.length < asz + b.length) {
			ret = new int[asz + b.length];
			System.arraycopy(a, 0, ret, 0, asz);
		}
		System.arraycopy(b, 0, ret, asz, b.length);
		return ret;
	}
}
