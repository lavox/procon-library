package math;

import java.util.Arrays;

public class ModInt {
	final ModOperation mop;
	int v = 0;
	ModInt(ModOperation mop, int v) {
		this.mop = mop;
		this.v = v;
	}
	public int mod() {
		return mop.m;
	}
	public int val() {
		return v;
	}
	public ModInt add(ModInt o) {
		return new ModInt(mop, mop.add(v, o.v));
	}
	public ModInt add(ModInt o1, ModInt o2) {
		return add(o1).addAsn(o2);
	}
	public ModInt add(ModInt o1, ModInt o2, ModInt o3) {
		return add(o1).addAsn(o2).addAsn(o3);
	}
	public ModInt add(ModInt o1, ModInt... o) {
		ModInt ret = add(o1);
		for (ModInt m: o) ret.addAsn(m);
		return ret;
	}
	public ModInt add(long a) {
		return new ModInt(mop, mop.add(v, mop.mod(a)));
	}
	public ModInt add(long a1, long a2) {
		return add(a1).addAsn(mop.mod(a2));
	}
	public ModInt add(long a1, long a2, long a3) {
		return add(a1).addAsn(mop.mod(a2)).addAsn(mop.mod(a3));
	}
	public ModInt add(long a1, long... a) {
		ModInt ret = add(a1);
		for (long n: a) ret.addAsn(n);
		return ret;
	}
	public ModInt sub(ModInt o) {
		return new ModInt(mop, mop.sub(v, o.v));
	}
	public ModInt sub(long a) {
		return new ModInt(mop, mop.sub(v, mop.mod(a)));
	}
	public ModInt mul(ModInt o) {
		return new ModInt(mop, mop.mul(v, o.v));
	}
	public ModInt mul(ModInt o1, ModInt o2) {
		return mul(o1).mulAsn(o2);
	}
	public ModInt mul(ModInt o1, ModInt o2, ModInt o3) {
		return mul(o1).mulAsn(o2).mulAsn(o3);
	}
	public ModInt mul(ModInt o1, ModInt... o) {
		ModInt ret = mul(o1);
		for (ModInt m: o) ret.mulAsn(m);
		return ret;
	}
	public ModInt mul(long a) {
		return new ModInt(mop, mop.mul(v, mop.mod(a)));
	}
	public ModInt mul(long a1, long a2) {
		return mul(a1).mulAsn(mop.mod(a2));
	}
	public ModInt mul(long a1, long a2, long a3) {
		return mul(a1).mulAsn(mop.mod(a2)).mulAsn(mop.mod(a3));
	}
	public ModInt mul(long a1, long... a) {
		ModInt ret = mul(a1);
		for (long n: a) ret.mulAsn(n);
		return ret;
	}
	public ModInt div(ModInt o) {
		return new ModInt(mop, mop.div(v, o.v));
	}
	public ModInt div(long a) {
		return new ModInt(mop, mop.div(v, mop.mod(a)));
	}
	public ModInt inv() {
		return new ModInt(mop, mop.inv(v));
	}

	public ModInt addAsn(ModInt o) {
		v = mop.add(v, o.v);
		return this;
	}
	public ModInt addAsn(long a) {
		v = mop.add(v, mop.mod(a));
		return this;
	}
	public ModInt subAsn(ModInt o) {
		v = mop.sub(v, o.v);
		return this;
	}
	public ModInt subAsn(long a) {
		v = mop.sub(v, mop.mod(a));
		return this;
	}
	public ModInt mulAsn(ModInt o) {
		v = mop.mul(v, o.v);
		return this;
	}
	public ModInt mulAsn(long a) {
		v = mop.mul(v, mop.mod(a));
		return this;
	}
	public ModInt divAsn(ModInt o) {
		v = mop.div(v, o.v);
		return this;
	}
	public ModInt divAsn(long a) {
		v = mop.div(v, mop.mod(a));
		return this;
	}
	@Override
	public boolean equals(Object o) {
		if (o instanceof ModInt) {
			ModInt m = (ModInt)o;
			return val() == m.val() && mod() == m.mod();
		}
		return false;
	}
	@Override
	public int hashCode() {
		return 31 * (31 + mod()) + val();
	}
	@Override
	public String toString() {
		return Integer.toString(v);
	}
}

class ModFraction extends ModInt {
	double vd = 0;
	ModFraction(ModOperation mop, int v, double vd) {
		super(mop, v);
		this.vd = vd;
	}
	@Override
	public ModInt add(ModInt o) {
		return new ModFraction(mop, mop.add(v, o.v), vd + ((ModFraction)o).vd);
	}
	@Override
	public ModInt add(long a) {
		return new ModFraction(mop, mop.add(v, mop.mod(a)), vd + a);
	}
	@Override
	public ModInt sub(ModInt o) {
		return new ModFraction(mop, mop.sub(v, o.v), vd - ((ModFraction)o).vd);
	}
	@Override
	public ModInt sub(long a) {
		return new ModFraction(mop, mop.sub(v, mop.mod(a)), vd - a);
	}
	@Override
	public ModInt mul(ModInt o) {
		return new ModFraction(mop, mop.mul(v, o.v), vd * ((ModFraction)o).vd);
	}
	public ModInt mul(long a) {
		return new ModFraction(mop, mop.mul(v, mop.mod(a)), vd * a);
	}
	@Override
	public ModInt div(ModInt o) {
		return new ModFraction(mop, mop.div(v, o.v), vd / ((ModFraction)o).vd);
	}
	@Override
	public ModInt div(long a) {
		return new ModFraction(mop, mop.div(v, mop.mod(a)), vd / a);
	}
	@Override
	public ModInt inv() {
		return new ModFraction(mop, mop.inv(v), 1.0 / vd);
	}

	@Override
	public ModInt addAsn(ModInt o) {
		super.addAsn(o);
		vd += ((ModFraction)o).vd;
		return this;
	}
	@Override
	public ModInt addAsn(long a) {
		super.addAsn(a);
		vd += a;
		return this;
	}
	@Override
	public ModInt subAsn(ModInt o) {
		super.subAsn(o);
		vd -= ((ModFraction)o).vd;
		return this;
	}
	@Override
	public ModInt subAsn(long a) {
		super.subAsn(a);
		vd -= a;
		return this;
	}
	@Override
	public ModInt mulAsn(ModInt o) {
		super.mulAsn(o);
		vd *= ((ModFraction)o).vd;
		return this;
	}
	@Override
	public ModInt mulAsn(long a) {
		super.mulAsn(a);
		vd *= a;
		return this;
	}
	@Override
	public ModInt divAsn(ModInt o) {
		super.divAsn(o);
		vd /= ((ModFraction)o).vd;
		return this;
	}
	@Override
	public ModInt divAsn(long a) {
		super.divAsn(a);
		vd /= a;
		return this;
	}
	@Override
	public String toString() {
		return String.format("%.10f", vd);
	}
}

class ModOperation {
	final int m;
	public static final int MOD998 = 998244353;
	public static final int MOD107 = 1000000007;
	boolean fraction = false;

	int factorialMax = -1;
	int[] fact = null; // i!
	int[] finv = null; // (i!)^(-1)
	int[] inv = null; // i^(-1)

	public ModOperation(int m) {
		this.m = m;
	}
	public ModOperation(int m, boolean fraction) {
		this.m = m;
		this.fraction = fraction;
	}
	public static ModOperation mod998() {
		return new ModOperation(MOD998);
	}
	public static ModOperation mod107() {
		return new ModOperation(MOD107);
	}
	public ModInt create(long v) {
		v %= m;
		if ( v < 0 ) v += m;
		return fraction ? new ModFraction(this, (int)v, v) : new ModInt(this, (int)v);
	}
	public ModInt one() {
		return create(1);
	}
	public ModInt zero() {
		return create(0);
	}
	public ModInt createInv(long v) {
		v %= m;
		if ( v < 0 ) v += m;
		return fraction ? new ModFraction(this, inv((int)v), 1.0 / v) : new ModInt(this, inv((int)v));
	}
	public int add(int a, int b) {
		int ret = a + b;
		return ret >= m ? ret - m : ret;
	}
	public int sub(int a, int b) {
		int ret = a - b;
		return ret < 0 ? ret + m : ret;
	}
	public int mul(int a, int b) {
		return (int)((((long)a) * b) % m);
	}
	public int div(int a, int b) {
		return mul(a, inv(b));
	}
	public int pow(int a, long n) {
		assert n >= 0;
		int c = a, r = 1;
		while ( n > 0 ) {
			if ( (n & 1L) != 0 ) r = mul(r, c);
			c = mul(c, c);
			n >>>= 1;
		}
		return r;
	}
	public int inv(int a) {
		return a <= factorialMax ? inv[a] : pow(a, m - 2);
	}
	public int mod(long a) {
		int ret = (int)(a % m);
		return ret < 0 ? ret + m : ret;
	}
	
	public void prepareFactorial(int max) {
		max = Math.max(max, 1);
		if (max <= factorialMax) return;
		if (factorialMax == -1) {
			fact = new int[max + 1];
			finv = new int[max + 1];
			inv = new int[max + 1];
			fact[0] = 1;
			finv[0] = 1;
			fact[1] = 1;
			finv[1] = 1;
			inv[1] = 1;
			factorialMax = 1;
		} else {
			fact = Arrays.copyOf(fact, max + 1);
			finv = Arrays.copyOf(finv, max + 1);
			inv = Arrays.copyOf(inv, max + 1);
		}

		for (int i = factorialMax + 1; i <= max; i++) {
			fact[i] = mul(fact[i - 1],  i);
			inv[i] = -mul(inv[m % i], m / i);
			if ( inv[i] < 0 ) inv[i] += m;
			finv[i] = mul(finv[i - 1], inv[i]);
		}
		factorialMax = max;
	}
	public int fact(int a) {
		return fact[a];
	}
	public int factInv(int a) {
		return finv[a];
	}
	public int combi(int n, int r) {
		return mul(mul(fact[n], finv[r]), finv[n - r]);
	}
	public int perm(int n, int r) {
		return mul(fact[n], finv[n - r]);
	}
}