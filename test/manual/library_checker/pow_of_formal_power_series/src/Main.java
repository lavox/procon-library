import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

import java.util.Arrays;

// template & library : https://github.com/lavox/procon-library
public class Main {
	public static void main(String[] args) {
		Main o = new Main();
		o.solve();
	}

	public void solve() {
		FastScanner sc = new FastScanner(System.in);
		int N = sc.nextInt();
		long M = sc.nextLong();
		int[] a = sc.nextIntArray(N);
		ModOperation mop = ModOperation.mod998();
		NTT ntt = new NTT(mop);
		FormalPowerSeries f = new NTTFriendlyFPS(a, mop, ntt);
		FormalPowerSeries g = f.pow(M);
		print(g.toArray(), SPACE);
	}

	public static final char LF = '\n';
	public static final char SPACE = ' ';
	public static final String YES = "Yes";
	public static final String NO = "No";
	public static void print(int[] array, char sep) {
		print(array, sep, n -> n, 0, array.length);
	}
	public static void print(int[] array, char sep, IntUnaryOperator conv) {
		print(array, sep, conv, 0, array.length);
	}
	public static void print(int[] array, char sep, IntUnaryOperator conv, int start, int end) {
		StringBuilder ans = new StringBuilder();
		for (int i = start; i < end; i++) {
			ans.append(conv.applyAsInt(array[i]));
			ans.append(sep);
		}
		if (ans.length() > 0) ans.deleteCharAt(ans.length() - 1);
		System.out.println(ans.toString());
	}
	public static void print(long[] array, char sep) {
		print(array, sep, n -> n, 0, array.length);
	}
	public static void print(long[] array, char sep, LongUnaryOperator conv) {
		print(array, sep, conv, 0, array.length);
	}
	public static void print(long[] array, char sep, LongUnaryOperator conv, int start, int end) {
		StringBuilder ans = new StringBuilder();
		for (int i = start; i < end; i++) {
			ans.append(conv.applyAsLong(array[i]));
			ans.append(sep);
		}
		if (ans.length() > 0) ans.deleteCharAt(ans.length() - 1);
		System.out.println(ans.toString());
	}
	public static <T> void print(T[] array, char sep) {
		print(array, sep, n -> n, 0, array.length);
	}
	public static <T> void print(T[] array, char sep, LongUnaryOperator conv) {
		print(array, sep, conv, 0, array.length);
	}
	public static <T> void print(T[] array, char sep, LongUnaryOperator conv, int start, int end) {
		StringBuilder ans = new StringBuilder();
		for (int i = start; i < end; i++) {
			ans.append(array[i].toString());
			ans.append(sep);
		}
		if (ans.length() > 0) ans.deleteCharAt(ans.length() - 1);
		System.out.println(ans.toString());
	}
	public static void printYesNo(boolean[] array, char sep) {
		printYesNo(array, sep, n -> n, 0, array.length);
	}
	public static void printYesNo(boolean[] array, char sep, LongUnaryOperator conv) {
		printYesNo(array, sep, conv, 0, array.length);
	}
	public static void printYesNo(boolean[] array, char sep, LongUnaryOperator conv, int start, int end) {
		StringBuilder ans = new StringBuilder();
		for (int i = start; i < end; i++) {
			ans.append(array[i] ? YES : NO);
			ans.append(sep);
		}
		if (ans.length() > 0) ans.deleteCharAt(ans.length() - 1);
		System.out.println(ans.toString());
	}
	public static <T> void print(ArrayList<T> array, char sep) {
		print(array, sep, a -> a, 0, array.size());
	}
	public static <T> void print(ArrayList<T> array, char sep, UnaryOperator<T> conv) {
		print(array, sep, conv, 0, array.size());
	}
	public static <T> void print(ArrayList<T> array, char sep, UnaryOperator<T> conv, int start, int end) {
		StringBuilder ans = new StringBuilder();
		for (int i = start; i < end; i++) {
			ans.append(conv.apply(array.get(i)).toString());
			ans.append(sep);
		}
		if (ans.length() > 0) ans.deleteCharAt(ans.length() - 1);
		System.out.println(ans.toString());
	}
	public static void print(int a) { System.out.println(a); }
	public static void print(long a) { System.out.println(a); }
	public static <T> void print(T s) { System.out.println(s.toString()); }
	public static void printYesNo(boolean yesno) {
		System.out.println(yesno ? YES : NO);
	}
	public static void printDouble(double val, int digit) {
		System.out.println(String.format("%." + digit + "f", val));
	}
	public static void print(int... a) { print(a, SPACE); }
	public static void print(long... a) { print(a, SPACE); }
	@SuppressWarnings("unchecked")
	public static <T> void print(T... s) { print(s, SPACE); }
}
class FastScanner {
	private final InputStream in;
	private final byte[] buf = new byte[1024];
	private int ptr = 0;
	private int buflen = 0;
	FastScanner( InputStream source ) { this.in = source; }
	private boolean hasNextByte() {
		if ( ptr < buflen ) return true;
		else {
			ptr = 0;
			try { buflen = in.read(buf); } catch (IOException e) { e.printStackTrace(); }
			if ( buflen <= 0 ) return false;
		}
		return true;
	} 
	private int readByte() { if ( hasNextByte() ) return buf[ptr++]; else return -1; } 
	private boolean isPrintableChar( int c ) { return 33 <= c && c <= 126; }
	private boolean isNumeric( int c ) { return '0' <= c && c <= '9'; }
	private void skipToNextPrintableChar() { while ( hasNextByte() && !isPrintableChar(buf[ptr]) ) ptr++; }
	public boolean hasNext() { skipToNextPrintableChar(); return hasNextByte(); }
	public String next() {
		if ( !hasNext() ) throw new NoSuchElementException();
		StringBuilder ret = new StringBuilder();
		int b = readByte();
		while ( isPrintableChar(b) ) { ret.appendCodePoint(b); b = readByte(); }
		return ret.toString();
	}
	public long nextLong() {
		if ( !hasNext() ) throw new NoSuchElementException();
		long ret = 0;
		int b = readByte();
		boolean negative = false;
		if ( b == '-' ) { negative = true; if ( hasNextByte() ) b = readByte(); }
		if ( !isNumeric(b) ) throw new NumberFormatException();
		while ( true ) {
			if ( isNumeric(b) ) ret = ret * 10 + b - '0';
			else if ( b == -1 || !isPrintableChar(b) ) return negative ? -ret : ret;
			else throw new NumberFormatException();
			b = readByte();
		}
	}
	public int nextInt() { return (int)nextLong(); }
	public double nextDouble() { return Double.parseDouble(next()); }
	public int[] nextIntArray(int N) { return nextIntArray(N, n -> n); }
	public int[] nextIntArray(int N, IntUnaryOperator conv) {
		int[] ret = new int[N];
		for (int i = 0; i < N; i++) ret[i] = conv.applyAsInt(nextInt());
		return ret;
	}
	public long[] nextLongArray(int N) {
		long[] ret = new long[N];
		for (int i = 0; i < N; i++) ret[i] = nextLong();
		return ret;
	}
	public String[] nextStringArray(int N) {
		String[] ret = new String[N];
		for (int i = 0; i < N; i++) ret[i] = next();
		return ret;
	}
	public int[][] nextIntMatrix(int N, int M) { return nextIntMatrix(N, M, n -> n); }
	public int[][] nextIntMatrix(int N, int M, IntUnaryOperator conv) {
		int[][] ret = new int[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				ret[i][j] = conv.applyAsInt(nextInt());
			}
		}
		return ret;
	}
	public long[][] nextLongMatrix(int N, int M) {
		long[][] ret = new long[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				ret[i][j] = nextLong();
			}
		}
		return ret;
	}
	public String[][] nextStringMatrix(int N, int M) {
		String[][] ret = new String[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				ret[i][j] = next();
			}
		}
		return ret;
	}
}

// === begin: math/NTTFriendlyFPS.java ===
// Ported to Java from the original C++ implementation by Nyaan.
// Original Source: https://nyaannyaan.github.io/library/fps/ntt-friendly-fps.hpp
class NTTFriendlyFPS extends FormalPowerSeries {
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
// === end: math/NTTFriendlyFPS.java ===

// === begin: math/ModInt.java ===
class ModInt {
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
// === end: math/ModInt.java ===

// === begin: math/NTT.java ===
// Ported to Java from the original C++ implementation by Nyaan.
// Original Source: https://nyaannyaan.github.io/library/ntt/ntt.hpp
class NTT {
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
// === end: math/NTT.java ===

// === begin: math/FormalPowerSeries.java ===
// Ported to Java from the original C++ implementation by Nyaan.
// Original Source: https://nyaannyaan.github.io/library/fps/formal-power-series.hpp
abstract class FormalPowerSeries implements Cloneable {
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
// === end: math/FormalPowerSeries.java ===
