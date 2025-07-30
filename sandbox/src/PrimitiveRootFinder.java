// 移植元
// https://trap.jp/post/1036/

import java.util.Random;

class PrimitiveRootFinder {
	static long mul(long a, long b, long p) { // a*b mod 2^61-1
		assert p == (1L << 61) - 1;
		final long m30 = (1L << 30) - 1;
		final long m31 = (1L << 31) - 1;
		long au = a >> 31;
		long ad = a & m31;
		long bu = b >> 31;
		long bd = b & m31;
		long mid = ad * bu + au * bd;
		long midu = mid >> 30;
		long midd = mid & m30;
		long res = mod61(au * bu * 2 + midu + (midd << 31) + ad * bd, p);
		return res;
	}
	static final long m61 = (1L << 61) - 1;
	static long mod61(long x, long p) {
		long xu = x >>> 61;
		long xd = x & m61;
		long res = xu + xd;
		if (res >= p) res -= p;
		return res;
	}
	// static long mul(long a, long b, long p) {
	// 	return ((a % p) * (b % p)) % p;
	// }
	static long powmod(long base, long exp, long p) { // base^exp mod MOD
		if (exp == 0) {
			return 1;
		} else if (exp % 2 == 0) {
			long t = powmod(base, exp / 2, p);
			return mul(t, t, p);
		} else {
			return mul(base, powmod(base, exp - 1, p), p);
		}
	}

	public static void main(String[] args) {
		int K = 2000000;
		long max = 1000;
		Prime prime = new Prime(K);
		long p = (1L << 61) - 1;
		// long p = 1000000087;
		long[] div = prime.divisors(p - 1);
		// ArrayList<Long> primitiveRoot = new ArrayList<>();
		for (long i = 1; i < max; i++) {
			boolean isPrimitiveRoot = true;
			for (long v: div) {
				long r = powmod(i, v, p);
				if (r == 1 && v != p - 1) {
					isPrimitiveRoot = false;
					break;
				}
			}
			if (isPrimitiveRoot) {
				// primitiveRoot.add(i);
				System.out.println(String.format("primitive root = %d", i));
				return;
			}
		}
	}
}
// p = 2^61 - 1, primitive_root = 37
// p = 998244353, primitive_root = 3
// p = 1000000007, primitive_root = 5
// p = 1000000009, primitive_root = 13
// p = 1000000021, primitive_root = 2
// p = 1000000033, primitive_root = 5
// p = 1000000087, primitive_root = 3
class BaseFinder {
	static final long[][] pair = {
		{998244353, 3},
		{1000000007, 5},
		{1000000009, 13},
		{1000000021, 2},
		{1000000033, 5},
		{1000000087, 3},
	};

	public static void main(String[] args) {
		int pi = 2;
		long p = pair[pi][0];
		long pr = pair[pi][1];
		long pmax = p;
		// long p = (1L << 61) - 1;
		// long pr = 37;
		// long pmax = Integer.MAX_VALUE;
		int max = 100000;
		Random rnd = new Random();
		int sum = 0;
		int cnt_max = 0;
		int ITER = 10000;
		for (int i = 0; i < ITER; i++) {
			int cnt = 0;
			while (true) {
				cnt++;
				int k = rnd.nextInt((int)(pmax - max - 2)) + max + 1;
				if (MathUtil.gcd(p - 1, k) != 1) continue;
				long b = PrimitiveRootFinder.powmod(pr, k, p);
				if (b <= max) continue;
				sum += cnt;
				cnt_max = Math.max(cnt_max, cnt);
				// System.out.println(String.format("iter:%d, b:%d", cnt, b));
				break;
			}
		}
		System.out.println(String.format("avg:%d, max:%d", sum / ITER, cnt_max));
	}
}