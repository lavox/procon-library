import java.util.Arrays;

class Convolution {
	private static class FftInfo {
		int g;
		int rank2;
		long[] root;
		long[] iroot;
		long[] rate2;
		long[] irate2;
		long[] rate3;
		long[] irate3;

		private FftInfo(long mod) {
			this.g = primitive_root((int)mod);
			this.rank2 = Long.numberOfTrailingZeros(mod - 1);

			this.root = new long[rank2 + 1];
			this.iroot = new long[rank2 + 1];
			this.rate2 = new long[Math.max(0, rank2 - 2 + 1)];
			this.irate2 = new long[Math.max(0, rank2 - 2 + 1)];
			this.rate3 = new long[Math.max(0, rank2 - 3 + 1)];
			this.irate3 = new long[Math.max(0, rank2 - 3 + 1)];

			root[rank2] = pow(g % mod, (mod - 1) >> rank2, mod);
			iroot[rank2] = inv(root[rank2], mod);
			for (int i = rank2 - 1; i >= 0; i--) {
				root[i] = (root[i + 1] * root[i + 1]) % mod;
				iroot[i] = (iroot[i + 1] * iroot[i + 1]) % mod;
			}

			{
				long prod = 1;
				long iprod = 1;
				for (int i = 0; i <= rank2 - 2; i++) {
					rate2[i] = (root[i + 2] * prod) % mod;
					irate2[i] = (iroot[i + 2] * iprod) % mod;
					prod = (prod * iroot[i + 2]) % mod;
					iprod = (iprod * root[i + 2]) % mod;
				}
			}
			{
				long prod = 1;
				long iprod = 1;
				for (int i = 0; i <= rank2 - 3; i++) {
					rate3[i] = (root[i + 3] * prod) % mod;
					irate3[i] = (iroot[i + 3] * iprod) % mod;
					prod = (prod * iroot[i + 3]) % mod;
					iprod = (iprod * root[i + 3]) % mod;
				}
			}
		}
	};

	private static int bit_ceil(int n) {
		int x = 1;
		while (x < n) x *= 2;
		return x;
	}
	private static long add(long a, long b, long m) {
		long ret = a + b;
		return ret >= m ? ret - m : ret;
	}
	private static long sub(long a, long b, long m) {
		long ret = a - b;
		return ret < 0 ? ret + m : ret;
	}
	private static long mul(long a, long b, long m) {
		return (a * b) % m;
	}
	private static long pow(long a, long n, long m) {
		assert n >= 0;
		long c = a, r = 1;
		while ( n > 0 ) {
			if ( (n & 1L) != 0 ) r = mul(r, c, m);
			c = mul(c, c, m);
			n >>>= 1;
		}
		return r;
	}
	private static long inv(long a, long m) {
		return pow(a, m - 2, m);
	}
	private static long mod(long x, long m) {
		x %= m;
		return x < 0 ? x + m : x;
	}

	private static int primitive_root(int m) {
		if (m == 2) return 1;
		if (m == 167772161) return 3;
		if (m == 469762049) return 3;
		if (m == 754974721) return 11;
		if (m == 998244353) return 3;
		int[] divs = new int[20];
		divs[0] = 2;
		int cnt = 1;
		int x = (m - 1) / 2;
		while (x % 2 == 0) x /= 2;
		for (int i = 3; ((long)(i))*i <= x; i += 2) {
			if (x % i == 0) {
				divs[cnt++] = i;
				while (x % i == 0) {
					x /= i;
				}
			}
		}
		if (x > 1) {
			divs[cnt++] = x;
		}
		for (int g = 2;; g++) {
			boolean ok = true;
			for (int i = 0; i < cnt; i++) {
				if (pow(g, (m - 1) / divs[i], m) == 1) {
					ok = false;
					break;
				}
			}
			if (ok) return g;
		}
	}

	private static void butterfly(long[] a, long mod) {
		int n = a.length;
		int h = Integer.numberOfTrailingZeros(n);
		FftInfo info = new FftInfo(mod);

		int len = 0;
		while (len < h) {
			if (h - len == 1) {
				int p = 1 << (h - len - 1);
				long rot = 1;
				for (int s = 0; s < (1 << len); s++) {
					int offset = s << (h - len);
					for (int i = 0; i < p; i++) {
						long l = a[i + offset];
						long r = mul(a[i + offset + p], rot, mod);
						a[i + offset] = add(l, r, mod);
						a[i + offset + p] = sub(l, r, mod);
					}
					if (s + 1 != (1 << len))
						rot = mul(rot, info.rate2[Integer.numberOfTrailingZeros(~s)], mod);
				}
				len++;
			} else {
				// 4-base
				int p = 1 << (h - len - 2);
				long rot = 1;
				long imag = info.root[2];
				for (int s = 0; s < (1 << len); s++) {
					long rot2 = mul(rot, rot, mod);
					long rot3 = mul(rot2, rot, mod);
					int offset = s << (h - len);
					for (int i = 0; i < p; i++) {
						long mod2 = mod * mod;
						long a0 = a[i + offset];
						long a1 = a[i + offset + p] * rot;
						long a2 = a[i + offset + 2 * p] * rot2;
						long a3 = a[i + offset + 3 * p] * rot3;
						long a1na3imag = mod(a1 + mod2 - a3, mod) * imag;
						long na2 = mod2 - a2;
						a[i + offset] = mod(a0 + a2 + a1 + a3, mod);
						a[i + offset + 1 * p] = mod(a0 + a2 + (2 * mod2 - (a1 + a3)), mod);
						a[i + offset + 2 * p] = mod(a0 + na2 + a1na3imag, mod);
						a[i + offset + 3 * p] = mod(a0 + na2 + (mod2 - a1na3imag), mod);
					}
					if (s + 1 != (1 << len))
						rot = mul(rot, info.rate3[Integer.numberOfTrailingZeros(~s)], mod);
				}
				len += 2;
			}
		}
	}

	private static void butterfly_inv(long[] a, long mod) {
		int n = a.length;
		int h = Integer.numberOfTrailingZeros(n);
		FftInfo info = new FftInfo(mod);

		int len = h;
		while (len != 0) {
			if (len == 1) {
				int p = 1 << (h - len);
				long irot = 1;
				for (int s = 0; s < (1 << (len - 1)); s++) {
					int offset = s << (h - len + 1);
					for (int i = 0; i < p; i++) {
						long l = a[i + offset];
						long r = a[i + offset + p];
						a[i + offset] = add(l, r, mod);
						a[i + offset + p] = mul(sub(l, r, mod), irot, mod);
					}
					if (s + 1 != (1 << (len - 1)))
						irot = mul(irot, info.irate2[Integer.numberOfTrailingZeros(~s)], mod);
				}
				len--;
			} else {
				// 4-base
				int p = 1 << (h - len);
				long irot = 1;
				long iimag = info.iroot[2];
				for (int s = 0; s < (1 << (len - 2)); s++) {
					long irot2 = mul(irot, irot, mod);
					long irot3 = mul(irot2, irot, mod);
					int offset = s << (h - len + 2);
					for (int i = 0; i < p; i++) {
						long a0 = a[i + offset + 0 * p];
						long a1 = a[i + offset + 1 * p];
						long a2 = a[i + offset + 2 * p];
						long a3 = a[i + offset + 3 * p];

						long a2na3iimag = mul(sub(a2, a3, mod), iimag, mod);

						a[i + offset] = (a0 + a1 + a2 + a3) % mod;
						a[i + offset + 1 * p] = mul(mod(a0 - a1 + a2na3iimag, mod), irot, mod);
						a[i + offset + 2 * p] = mul(mod(a0 + a1 - a2 - a3, mod), irot2, mod);
						a[i + offset + 3 * p] = mul(mod(a0 - a1 - a2na3iimag, mod), irot3, mod);
					}
					if (s + 1 != (1 << (len - 2)))
						irot = mul(irot, info.irate3[Integer.numberOfTrailingZeros(~s)], mod);
				}
				len -= 2;
			}
		}
	}

	private static long[] convolution_naive(long[] a, long[] b, long mod) {
		int n = a.length, m = b.length;
		long[] ans = new long[n + m - 1];
		if (n < m) {
			for (int j = 0; j < m; j++) {
				for (int i = 0; i < n; i++) {
					ans[i + j] = add(ans[i + j], mul(a[i], b[j], mod), mod);
				}
			}
		} else {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					ans[i + j] = add(ans[i + j], mul(a[i], b[j], mod), mod);
				}
			}
		}
		return ans;
	}

	private static long[] convolution_fft(long[] a, long[] b, long mod) {
		int n = a.length, m = b.length;
		int z = bit_ceil(n + m - 1);
		
		a = Arrays.copyOf(a, z);
		butterfly(a, mod);
		b = Arrays.copyOf(b, z);
		butterfly(b, mod);
		for (int i = 0; i < z; i++) {
			a[i] = mul(a[i], b[i], mod);
		}
		butterfly_inv(a, mod);
		a = Arrays.copyOf(a, n + m - 1);
		long iz = inv(mod(z, mod), mod);
		for (int i = 0; i < n + m - 1; i++) a[i] = mul(a[i], iz, mod);
		return a;
	}

	public static long[] convolution(long[] a, long[] b, long mod) {
		int n = a.length, m = b.length;
		if (n == 0 || m == 0) return new long[0];
		int z = bit_ceil(n + m - 1);
		assert (mod - 1) % z == 0;

		if (Math.min(n, m) <= 60) return convolution_naive(a, b, mod);
		return convolution_fft(a, b, mod);
	}

	private static final long MOD1 = 754974721;  // 2^24
	private static final long MOD2 = 167772161;  // 2^25
	private static final long MOD3 = 469762049;  // 2^26
	private static final long M2M3 = MOD2 * MOD3;
	private static final long M1M3 = MOD1 * MOD3;
	private static final long M1M2 = MOD1 * MOD2;
	private static final long M1M2M3 = MOD1 * MOD2 * MOD3;
	private static final long i1 = inv(mod(MOD2 * MOD3, MOD1), MOD1);
	private static final long i2 = inv(mod(MOD1 * MOD3, MOD2), MOD2);
	private static final long i3 = inv(mod(MOD1 * MOD2, MOD3), MOD3);
	private static final int MAX_AB_BIT = 24;
	public static long[] convolution_ll(long[] a, long[] b) {
		int n = a.length, m = b.length;
		if (n == 0 || m == 0) return new long[0];
		if ( n + m - 1 > (1 << MAX_AB_BIT) ) throw new RuntimeException();

		long[] c1 = convolution(a, b, MOD1);
		long[] c2 = convolution(a, b, MOD2);
		long[] c3 = convolution(a, b, MOD3);

		long[] c = new long[n + m - 1];
		for (int i = 0; i < n + m - 1; i++) {
			long x = 0;
			x += mul(c1[i], i1, MOD1) * M2M3;
			x += mul(c2[i], i2, MOD2) * M1M3;
			x += mul(c3[i], i3, MOD3) * M1M2;
			long diff = c1[i] - mod(x, MOD1);
			if (diff < 0) diff += MOD1;
			final long[] offset = new long[]{0, 0, M1M2M3, 2 * M1M2M3, 3 * M1M2M3};
			x -= offset[(int)(diff % 5)];
			c[i] = x;
		}
		return c;
	}
}
