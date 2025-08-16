package string;

public class RollingHash {
	public static final long MOD1L61 = (1L << 61) - 1;
	public static final long MOD998244353 = 998244353;
	public static final long MOD1000000007 = 1000000007;
	public static final long MOD1000000009 = 1000000009;
	public static final long MOD1000000021 = 1000000021;
	public static final long MOD1000000033 = 1000000033;
	public static final long MOD1000000087 = 1000000087;

	private long m = 0;
	private long b = 0;
	private long[] pow = null;
	private long[] hash = null;
	private int hlen = 0;
	private int pos = 0;
	private static final int DEFAULT_MAX_VALUE = 2000000;

	public static RollingHash createWithBase(int n, long b, long m) {
		RollingHash ret = m == MOD1L61 ? new RollingHash61(m) : new RollingHash(m);
		ret.prepare(n, b);
		return ret;
	}
	public static RollingHash create(int n, long m) {
		return create(n, m, DEFAULT_MAX_VALUE);
	}
	public static RollingHash create(int n, long m, int maxVal) {
		RollingHash ret = m == MOD1L61 ? new RollingHash61(m) : new RollingHash(m);
		ret.prepare(n, ret.findBase(m, maxVal));
		return ret;
	}

	protected RollingHash(long m) {
		this.m = m;
	}
	protected void prepare(int n, long b) {
		this.b = b;
		this.hlen = n + 1;
		pow = new long[n + 1];
		hash = new long[n + 1];
		pow[0] = 1;
		pos = 0;
		hash[pos] = 0;
		for (int i = 1; i <= n; i++) {
			pow[i] = mul(pow[i - 1], b);
		}
	}

	public void add(String s) {
		add((i) -> s.charAt(i), s.length());
	}
	public void add(int[] array) {
		add((i) -> array[i], array.length);
	}
	public void add(java.util.function.IntUnaryOperator op, int len) {
		for (int i = 0; i < len; i++) add(op.applyAsInt(i));
	}
	public void add(int v) {
		pos++;
		hash[pos % hlen] = mod(mul(hash[(pos - 1) % hlen], b) + v);
	}
	public long hash(int i0, int len) {
		assert 0 <= len && len < hlen;
		assert Math.max(pos - hlen + 1, 0) <= i0 && i0 + len <= pos;
		return mod(hash[(i0 + len) % hlen] + m - mul(hash[i0 % hlen], pow[len]));
	}

	protected long mul(long x, long y) {
		return (x * y) % m;
	}
	protected long mod(long x) {
		return x % m;
	}
	protected long pow(long x, long n) {
		long c = x, r = 1;
		while (n > 0) {
			if ((n & 1L) != 0) r = mul(r, c);
			c = mul(c, c);
			n >>>= 1;
		}
		return r;
	}
	protected long findBase(long m, int maxV) {
		java.util.Random rnd = new java.util.Random();
		long mMax = Math.min(m, Integer.MAX_VALUE);
		long root = findPrimitiveRoot(m);
		while (true) {
			int k = rnd.nextInt((int)(mMax - maxV - 2)) + maxV + 1;
			if (gcd(m - 1, k) != 1) continue;
			long b = pow(root, k);
			if (b > maxV) return b;
		}
	}
	private static long findPrimitiveRoot(long m) {
		if (m == MOD1L61) return 37;
		else if (m == MOD998244353) return 3;
		else if (m == MOD1000000007) return 5;
		else if (m == MOD1000000009) return 13;
		else if (m == MOD1000000021) return 2;
		else if (m == MOD1000000033) return 5;
		else if (m == MOD1000000087) return 3;
		throw new IllegalArgumentException("Base must be specified.");
	}
	private static long gcd(long a, long b) {
		while ( b != 0 ) {
			long tmp = b;
			b = a % b;
			a = tmp;
		}
		return a;
	}
}
class RollingHash61 extends RollingHash {
	private static final long MOD61 = MOD1L61;
	private static final long MASK30 = (1L << 30) - 1;
	private static final long MASK31 = (1L << 31) - 1;
	private static final long MASK61 = MOD1L61;

	protected RollingHash61(long m) {
		super(m);
	}
	@Override
	protected long mul(long x, long y) {
		return mul61(x, y);
	}
	@Override
	protected long mod(long x) {
		return mod61(x);
	}
	private static long mul61(long x, long y) {
		long au = x >> 31;
		long ad = x & MASK31;
		long bu = y >> 31;
		long bd = y & MASK31;
		long mid = ad * bu + au * bd;
		long midu = mid >> 30;
		long midd = mid & MASK30;
		long res = mod61(au * bu * 2 + midu + (midd << 31) + ad * bd);
		return res;
	}
	private static long mod61(long x) {
		long xu = x >>> 61;
		long xd = x & MASK61;
		long res = xu + xd;
		if (res >= MOD61) res -= MOD61;
		return res;
	}
}
