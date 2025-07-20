import java.util.ArrayList;
import java.util.Arrays;

class Prime {
	private int max = 0;
	int[] lpf = null;
	int[] primes = null;

	public Prime(int max) {
		this.max = max;
		lpf = new int[max + 1];
		Arrays.fill(lpf, 1);
		IntArrayList primes = new IntArrayList();
		for (int i = 2; i <= max; i++) {
			if (lpf[i] == 1) {
				lpf[i] = i;
				primes.add(i);
			}
			int thr = Math.min(lpf[i], max / i);
			for (int ji = 0, j = 0; ji < primes.size() && (j = primes.get(ji)) <= thr; ji++) {
				lpf[i * j] = j;
			}
		}
		this.primes = primes.toArray();
	}
	public boolean isPrime(long num) {
		if (num <= max) {
			return (num & 1L) == 0 ? num == 2L : (lpf[(int)num] == num && num > 1);
		} else {
			return isPrimeByMillerRabin(num);
		}
	}
	private static final long MR_THR = 4759123141L;
	private static final long[] MR_A_SMALL = {2, 7, 61};
	private static final long[] MR_A_LARGE = {2, 325, 9375, 28178, 450775, 9780504, 1795265022};
	public static boolean isPrimeByMillerRabin(long num) {
		if (num <= 1) return false;
		if (num == 2) return true;
		if ((num & 1L) == 0) return false;
		if (num < MR_THR) {
			return millerRabinTest(num, MR_A_SMALL);
		} else {
			return millerRabinTest(num, MR_A_LARGE);
		}
	}
	public int[] primes() {
		return primes;
	}
	public ArrayList<Factor> factorize(long num) {
		assert num <= ((long)max) * max;
		ArrayList<Factor> ret = new ArrayList<>();
		if (num <= max) {
			int n = (int)num;
			while (n > 1) {
				if ( ret.size() != 0 && ret.get(ret.size() - 1).base == lpf[n] ) {
					ret.get(ret.size() - 1).pow += 1;
				} else {
					ret.add(new Factor(lpf[n], 1));
				}
				n /= lpf[n];
			}
		} else {
			for (long p : primes) {
				if (p * p > num) break;
				int c = 0;
				while (num % p == 0) {
					c++;
					num /= p;
				}
				if ( c == 0 ) continue;
				ret.add(new Factor(p, c));
			}
			if (num > 1) ret.add(new Factor(num, 1));
		}
		return ret;
	}
	public long[] divisors(long num) {
		ArrayList<Factor> factor = factorize(num);
		int cnt = 1;
		for (Factor f: factor) cnt *= (1 + f.pow);
		long[] ret = new long[cnt];
		dfs_divisors(0, 0, 1, factor, ret);
		return ret;
	}
	private int dfs_divisors(int pos, int ri, long cur, ArrayList<Factor> factor, long[] ret) {
		if ( pos >= factor.size() ) {
			ret[ri++] = cur;
			return ri;
		}
		Factor f = factor.get(pos);
		for ( int i = 0 ; i <= f.pow ; i++ ) {
			ri = dfs_divisors(pos + 1, ri, cur, factor, ret);
			cur *= f.base;
		}
		return ri;
	}


	public class Factor {
		long base;
		int pow;
		Factor(long base, int pow) {
			this.base = base;
			this.pow = pow;
		}
	}

	private static boolean millerRabinTest(long N, long[] A) {
		long s = 0;
		long d = N - 1;
		while ((d & 1L) == 0) {
			++s;
			d >>>= 1;
		}
		if (N <= Integer.MAX_VALUE) {
			for (long a: A) {
				if (N <= a) return true;
				if (!_millerRabin32(s, d, N, a)) return false;
			}
		} else {
			long r2 = (1L << 62) % N;
			for (int i = 0; i < 66; i++) {
				r2 <<= 1;
				if (r2 >= N) r2 -= N;
			}
			long nInv = N;
			for (int i = 0; i < 5; i++) nInv *= 2 - N * nInv;
			for (long a: A) {
				if (N <= a) return true;
				if (!_millerRabin64(s, d, N, a, nInv, r2)) {
					return false;
				}
			}
		}
		return true;
	}
	private static boolean _millerRabin32(long s, long d, long N, long a) {
		long x = pow32(a, d, N);
		long t = 0;
		if (x != 1) {
			for (t = 0; t < s; ++t) {
				if (x == N - 1) break;
				x = mul32(x, x, N);
			}
			if (t == s) return false;
		}
		return true;
	}
	private static boolean _millerRabin64(long s, long d, long N, long a, long nInv, long r2) {
		long x = montgomeryPow(a, d, N, nInv, r2);
		long t = 0;
		if (x != 1) {
			for (t = 0; t < s; ++t) {
				if (x == N - 1) break;
				x = mul64(x, x, N, nInv, r2);
			}
			if (t == s) return false;
		}
		return true;
	}
	private static long pow32(long a, long n, long m) {
		long c = a, r = 1;
		while ( n > 0 ) {
			if ( (n & 1L) != 0 ) r = mul32(r, c, m);
			c = mul32(c, c, m);
			n >>>= 1;
		}
		return r;
	}
	private static long mul32(long a, long b, long m) {
		return (a * b) % m;
	}
	private static long mulHigh(long a, long b) {
		return Math.multiplyHigh(a, b) + ((a >> 63) & b) + ((b >> 63) & a);
	}
	private static long montgomeryMul(long a, long b, long nInv, long N) {
		return mulHigh(a, b) + mulHigh(-nInv * a * b, N) + (a * b == 0 ? 0 : 1);
	}
	private static long montgomeryPow(long a, long n, long N, long nInv, long r2) {
		long c = montgomeryMul(a, r2, nInv, N);
		long r = 1;
		while (n > 0) {
			if ((n & 1L) != 0) r = montgomeryMul(r, c, nInv, N);
			c = montgomeryMul(c, c, nInv, N);
			n >>>= 1;
		}
		return montgomeryMod(r, N);
	}
	private static long montgomeryMod(long a, long N) {
		return a < N ? a : a - N;
	}
	private static long mul64(long a, long b, long N, long nInv, long r2) {
		return montgomeryMod(montgomeryMul(montgomeryMul(a, r2, nInv, N), b, nInv, N), N);
	}

	private class IntArrayList {
		int[] data = null;
		int size = 0;
		public IntArrayList() {
			data = new int[] {};
		}
		public boolean add(int e) {
			if (data.length == size) grow(size + 1);
			data[size++] = e;
			return true;
		}
		public int get(int index) {
			if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
			return data[index];
		}
		public int size() {
			return size;
		}
		public int[] toArray() {
			return Arrays.copyOf(data, size);
		}
		private void grow(int minCapacity) {
			data = Arrays.copyOf(data, newCapacity(minCapacity));
		}
		private int newCapacity(int minCapacity) {
			int oldCapacity = data.length;
			int newCapacity = oldCapacity + (oldCapacity >> 1);
			if (newCapacity - minCapacity <= 0) {
					if (data.length == 0) return Math.max(10, minCapacity);
					return minCapacity;
			}
			return newCapacity;
		}
	}
}
