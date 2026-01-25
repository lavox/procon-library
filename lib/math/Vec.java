package math;
import java.util.Arrays;

public class Vec {
	long[] a = null;
	public Vec(int N) {
		a = new long[N];
	}
	public Vec(long[] a) {
		this(a, true);
	}
	Vec(long[] a, boolean copy) {
		if (copy) {
			this.a = Arrays.copyOf(a, a.length);
		} else {
			this.a = a;
		}
	}
	public long get(int i) {
		return a[i];
	}
	public void set(int i, long c) {
		a[i] = c;
	}
	public Vec add(Vec o) {
		return new Vec(add(this.a, o.a), false);
	}
	public static long[] add(long[] a, long[] b) {
		assert a.length == b.length;
		long[] ret = new long[a.length];
		for (int i = 0; i < a.length; i++) {
			ret[i] = a[i] + b[i];
		}
		return ret;
	}
	public Vec sub(Vec o) {
		return new Vec(sub(this.a, o.a), false);
	}
	public static long[] sub(long[] a, long[] b) {
		assert a.length == b.length;
		long[] ret = new long[a.length];
		for (int i = 0; i < a.length; i++) {
			ret[i] = a[i] - b[i];
		}
		return ret;
	}
	public Vec mul(long c) {
		return new Vec(mul(this.a, c), false);
	}
	public static long[] mul(long[] a, long c) {
		long[] ret = new long[a.length];
		for (int i = 0; i < a.length; i++) {
			ret[i] = a[i] * c;
		}
		return ret;
	}
	public long dot(Vec o) {
		return dot(this.a, o.a);
	}
	public static long dot(long[] a, long[] b) {
		assert a.length == b.length;
		long ret = 0;
		for (int i = 0; i < a.length; i++) {
			ret += a[i] * b[i];
		}
		return ret;
	}
	public long norm1() {
		return norm1(this.a);
	}
	public static long norm1(long[] a) {
		long ret = 0;
		for (int i = 0; i < a.length; i++) {
			ret += Math.abs(a[i]);
		}
		return ret;
	}
	public long norm2Sq() {
		return norm2Sq(this.a);
	}
	public static long norm2Sq(long[] a) {
		long ret = 0;
		for (int i = 0; i < a.length; i++) {
			ret += Math.abs(a[i] * a[i]);
		}
		return ret;
	}
	public double norm2() {
		return norm2(this.a);
	}
	public static double norm2(long[] a) {
		return Math.sqrt(norm2Sq(a));
	}
	public long dist1(Vec o) {
		return dist1(this.a, o.a);
	}
	public static long dist1(long[] a, long[] b) {
		return norm1(sub(a, b));
	}
	public long dist2Sq(Vec o) {
		return dist2Sq(this.a, o.a);
	}
	public static long dist2Sq(long[] a, long[] b) {
		return norm2Sq(sub(a, b));
	}
	public double dist2(Vec o) {
		return dist2(this.a, o.a);
	}
	public static double dist2(long[] a, long[] b) {
		return Math.sqrt(dist2Sq(a, b));
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vec) {
			Vec o = (Vec)obj;
			if ( a.length != o.a.length ) return false;
			for (int i = 0; i < a.length; i++) {
				if ( a[i] != o.a[i] ) return false;
			}
			return true;
		}
		return false;
	}
}
