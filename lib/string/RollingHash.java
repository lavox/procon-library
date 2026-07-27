package string;

import java.util.Random;
import java.util.function.IntUnaryOperator;

import primitive.LongArrayList;

public class RollingHash {
	private final RollingHashBuilder builder;
	private long[] hash = null;
	private int hlen = 0;
	private int pos = 0;

	protected RollingHash(int n, RollingHashBuilder builder) {
		this.builder = builder;
		this.prepare(n);
	}
	protected void prepare(int n) {
		this.hlen = n + 1;
		hash = new long[n + 1];
		pos = 0;
		hash[pos] = 0;
	}

	public void add(String s) {
		add((i) -> s.charAt(i), s.length());
	}
	public void add(int[] array) {
		add((i) -> array[i], array.length);
	}
	public void add(IntUnaryOperator op, int len) {
		for (int i = 0; i < len; i++) add(op.applyAsInt(i));
	}
	public void add(int v) {
		pos++;
		hash[pos % hlen] = builder.shiftAdd(hash[(pos - 1) % hlen], 1, v);
	}
	public long hash(int i0, int len) {
		assert 0 <= len && len < hlen;
		assert Math.max(pos - hlen + 1, 0) <= i0 && i0 + len <= pos;
		return builder.diffHash(hash[(i0 + len) % hlen], hash[i0 % hlen], len);
	}
}

class RollingHashBuilder {
	public static final long MOD1L61 = (1L << 61) - 1;
	public static final long MOD998244353 = 998244353;
	public static final long MOD1000000007 = 1000000007;
	public static final long MOD1000000009 = 1000000009;
	public static final long MOD1000000021 = 1000000021;
	public static final long MOD1000000033 = 1000000033;
	public static final long MOD1000000087 = 1000000087;

	private long m = 0;
	private long b = 0;
	private LongArrayList pow = null;
	private static final int DEFAULT_MAX_VALUE = 2000000;
	
	public RollingHash newHash(int n) {
		return new RollingHash(n, this);
	}
	public static RollingHashBuilder createWithBase(long b, long m) {
		RollingHashBuilder ret = m == MOD1L61 ? new RollingHashBuilder61(m) : new RollingHashBuilder(m);
		ret.prepare(b);
		return ret;
	}
	public static RollingHashBuilder create(long m) {
		return create(m, DEFAULT_MAX_VALUE);
	}
	public static RollingHashBuilder create(long m, int maxVal) {
		RollingHashBuilder ret = m == MOD1L61 ? new RollingHashBuilder61(m) : new RollingHashBuilder(m);
		ret.prepare(ret.findBase(m, maxVal));
		return ret;
	}
	protected RollingHashBuilder(long m) {
		this.m = m;
	}

	protected void prepare(long b) {
		this.b = b;
		pow = new LongArrayList();
		pow.add(1L);
	}
	protected long mul(long x, long y) {
		return (x * y) % m;
	}
	protected long diffHash(long h1, long h2, int len) {
		return mod(h1 + m - mul(h2, pow(len)));
	}
	protected long shiftAdd(long x, int len, long y) {
		return mod(mul(x, pow(len)) + y);
	}
	protected long mod(long x) {
		return x % m;
	}
	protected long pow(int n) {
		while (pow.size() <= n) {
			pow.add(mul(pow.get(pow.size() - 1), b));
		}
		return pow.get(n);
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
		Random rnd = new Random();
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
class RollingHashBuilder61 extends RollingHashBuilder {
	private static final long MOD61 = MOD1L61;
	private static final long MASK30 = (1L << 30) - 1;
	private static final long MASK31 = (1L << 31) - 1;
	private static final long MASK61 = MOD1L61;
	protected RollingHashBuilder61(long m) {
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
