import java.util.ArrayList;

class MathUtil {
	private static long mod(long x, long m) {
		x %= m;
		if (x < 0) x += m;
		return x;
	}
	private static long[] inv_gcd(long a, long b) {
		a = mod(a, b);
		if (a == 0) return new long[] {b, 0};
		long s = b, t = a;
		long m0 = 0, m1 = 1;

		while (t > 0) {
			long u = s / t;
			s -= t * u;
			m0 -= m1 * u;
			long tmp = s;
			s = t;
			t = tmp;
			tmp = m0;
			m0 = m1;
			m1 = tmp;
		}
		if (m0 < 0) m0 += b / s;
		return new long[] {s, m0};
	}

	private static long[] convertLongArray(ArrayList<Long> array) {
		long[] ret = new long[array.size()];
		for (int i = 0; i < ret.length; i++) ret[i] = array.get(i);
		return ret;
	}
	public static long[] crt(ArrayList<Long> r, ArrayList<Long> m) {
		return crt(convertLongArray(r), convertLongArray(m));
	}
	public static long[] crt(long[] r, long[] m) {
		assert r.length == m.length;
		int n = r.length;
		long r0 = 0, m0 = 1;
		for (int i = 0; i < n; i++) {
			assert 1 <= m[i];
			long r1 = mod(r[i], m[i]), m1 = m[i];
			if (m0 < m1) {
				long tmp = r0;
				r0 = r1;
				r1 = tmp;
				tmp = m0;
				m0 = m1;
				m1 = tmp;
			}
			if (m0 % m1 == 0) {
					if (r0 % m1 != r1) return new long[] {0, 0};
					continue;
			}
			long[] ig = inv_gcd(m0, m1);
			long g = ig[0], im = ig[1];

			long u1 = (m1 / g);
			if ((r1 - r0) % g != 0) return new long[] {0, 0};
			long x = (r1 - r0) / g % u1 * im % u1;

			r0 += x * m0;
			m0 *= u1;
			if (r0 < 0) r0 += m0;
		}
		return new long[] {r0, m0};
	}

	public static long floorSum(long n, long m, long a, long b) {
		assert 0 <= n && n < (1L << 32);
		assert 1 <= m && m < (1L << 32);
		long ans = 0;
		if (a < 0) {
			long a2 = mod(a, m);
			ans -= 1L * n * (n - 1) / 2 * ((a2 - a) / m);
			a = a2;
		}
		if (b < 0) {
			long b2 = mod(b, m);
			ans -= 1L * n * ((b2 - b) / m);
			b = b2;
		}
		return ans + floorSumUnsigned(n, m, a, b);
	}
	private static long floorSumUnsigned(long n, long m, long a, long b) {
		long ans = 0;
		while (true) {
			if (a >= m) {
				ans += n * (n - 1) / 2 * (a / m);
				a %= m;
			}
			if (b >= m) {
				ans += n * (b / m);
				b %= m;
			}
			long y_max = a * n + b;
			if (y_max < m) break;
			n = y_max / m;
			b = y_max % m;
			long tmp = m;
			m = a;
			a = tmp;
		}
		return ans;
	}
	public static long gcd(long a, long b) {
		while ( b != 0 ) {
			long tmp = b;
			b = a % b;
			a = tmp;
		}
		return a;
	}
}
