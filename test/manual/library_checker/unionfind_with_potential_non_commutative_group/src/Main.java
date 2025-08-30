import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

import java.util.function.IntFunction;
import java.util.function.Supplier;

// template & library : https://github.com/lavox/procon-library
public class Main {
	public static void main(String[] args) {
		Main o = new Main();
		o.solve();
	}

	ModOperation mop = null;
	public void solve() {
		FastScanner sc = new FastScanner(System.in);
		int N = sc.nextInt();
		int Q = sc.nextInt();
		mop = new ModOperation(ModOperation.MOD998);
		WeightedUnionFind uf = new WeightedUnionFind(N, () -> new Value(1, 0, 0, 1));
		StringBuilder ans = new StringBuilder();
		for (int q = 0; q < Q; q++) {
			int t = sc.nextInt();
			if (t == 0) {
				int u = sc.nextInt();
				int v = sc.nextInt();
				int x00 = sc.nextInt();
				int x01 = sc.nextInt();
				int x10 = sc.nextInt();
				int x11 = sc.nextInt();
				Value diff = new Value(x00, x01, x10, x11);
				ans.append(uf.unite(v, u, diff) ? 1 : 0);
			} else {
				int u = sc.nextInt();
				int v = sc.nextInt();
				if (uf.isSame(u, v)) {
					Value d = (Value)uf.diff(v, u);
					ans.append(d.x00);
					ans.append(SPACE);
					ans.append(d.x01);
					ans.append(SPACE);
					ans.append(d.x10);
					ans.append(SPACE);
					ans.append(d.x11);
				} else {
					ans.append(-1);
				}
			}
			ans.append(LF);
		}
		System.out.print(ans.toString());
	}

	class Value implements WeightedUnionFind.Weight {
		ModInt x00 = null;
		ModInt x01 = null;
		ModInt x10 = null;
		ModInt x11 = null;
		Value(ModInt x00, ModInt x01, ModInt x10, ModInt x11) {
			this.x00 = x00;
			this.x01 = x01;
			this.x10 = x10;
			this.x11 = x11;
		}
		Value(int x00, int x01, int x10, int x11) {
			this.x00 = mop.create(x00);
			this.x01 = mop.create(x01);
			this.x10 = mop.create(x10);
			this.x11 = mop.create(x11);
		}

		public Value op(WeightedUnionFind.Weight o) {
			Value v = (Value)o;
			return new Value(
				x00.mul(v.x00).addAsn(x01.mul(v.x10)),
				x00.mul(v.x01).addAsn(x01.mul(v.x11)),
				x10.mul(v.x00).addAsn(x11.mul(v.x10)),
				x10.mul(v.x01).addAsn(x11.mul(v.x11))
			);
		}
		public Value inv() {
			ModInt det = x00.mul(x11).sub(x01.mul(x10)).inv();
			return new Value(
				det.mul(x11),
				det.mul(-x01.val()),
				det.mul(-x10.val()),
				det.mul(x00)
			);
		}
		public boolean equals(WeightedUnionFind.Weight o) {
			Value v = (Value)o;
			return x00.equals(v.x00) && x01.equals(v.x01) && x10.equals(v.x10) && x11.equals(v.x11);
		}
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

// === begin: data_structure/WeightedUnionFind.java ===
class WeightedUnionFind {
	private int[] parent = null;
	private int[] size = null;
	private Data[] data = null;
	private Weight[] weight = null;
	
	public WeightedUnionFind(int N, Supplier<Weight> e) {
		this(N, e, null);
	}
	public WeightedUnionFind(int N, Supplier<Weight> e, IntFunction<Data> constructor) {
		parent = new int[N];
		size = new int[N];
		for (int i = 0; i < N; i++) {
			parent[i] = i;
			size[i] = 1;
		}
		weight = new Weight[N];
		for (int i = 0; i < N; i++) {
			weight[i] = e.get();
		}
		if (constructor != null) {
			data = new Data[N];
			for (int i = 0; i < N; i++) {
				data[i] = constructor.apply(i);
			}
		}
	}
	public int root(int i) {
		if (parent[i] == i) {
			return i;
		} else if (parent[parent[i]] == parent[i]) {
			return parent[i];
		} else {
			int r = root(parent[i]);
			weight[i] = weight[parent[i]].op(weight[i]);
			parent[i] = r;
			return r;
		}
	}
	public int size(int i) {
		return size[root(i)];
	}
	public Data data(int i) {
		return data[root(i)];
	}
	public Weight weight(int i) {
		root(i);
		return weight[i];
	}
	// diff = - weight(i) + weight(j)
	public Weight diff(int i, int j) {
		if (!isSame(i, j)) return null;
		return weight[i].inv().op(weight[j]);
	}
	// weight(i) + d = weight(j)
	public boolean unite(int i, int j, Weight d) {
		int ri = root(i);
		int rj = root(j);
		if (ri == rj) {
			return weight[i].op(d).equals(weight[j]);
		} else {
			if (size[ri] < size[rj]) {
				parent[ri] = rj;
				size[rj] += size[ri];
				if (data != null) {
					data[rj].merge(data[ri]);
				}
				weight[ri] = weight[j].op(weight[i].op(d).inv());
			} else {
				parent[rj] = ri;
				size[ri] += size[rj];
				if (data != null) {
					data[ri].merge(data[rj]);
				}
				weight[rj] = weight[i].op(d).op(weight[j].inv());
			}
			return true;
		}
	}
	public boolean isSame(int i, int j) {
		return root(i) == root(j);
	}
	public interface Data {
		public void merge(Data o);
	}
	public interface Weight {
		public Weight inv();
		public Weight op(Weight o);
		public boolean equals(Weight o);
	}
}
// === end: data_structure/WeightedUnionFind.java ===

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
		factorialMax = max;
		fact = new int[max + 1];
		finv = new int[max + 1];
		inv = new int[max + 1];

		fact[0] = 1;
		finv[0] = 1;
		fact[1] = 1;
		finv[1] = 1;
		inv[1] = 1;
		for (int i = 2; i <= max; i++) {
			fact[i] = mul(fact[i - 1],  i);
			inv[i] = -mul(inv[m % i], m / i);
			if ( inv[i] < 0 ) inv[i] += m;
			finv[i] = mul(finv[i - 1], inv[i]);
		}
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
