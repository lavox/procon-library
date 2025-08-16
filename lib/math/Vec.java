package math;
import java.util.Arrays;

public class Vec {
	long[] a = null;
	public Vec(int N) {
		a = new long[N];
	}
	public Vec(long[] a) {
		this.a = Arrays.copyOf(a, a.length);
	}
	public long get(int i) {
		return a[i];
	}
	public void set(int i, long c) {
		a[i] = c;
	}
	public Vec add(Vec o) {
		assert a.length == o.a.length;
		Vec ret = new Vec(a.length);
		for (int i = 0; i < a.length; i++) {
			ret.a[i] = a[i] + o.a[i];
		}
		return ret;
	}
	public Vec sub(Vec o) {
		assert a.length == o.a.length;
		Vec ret = new Vec(a.length);
		for (int i = 0; i < a.length; i++) {
			ret.a[i] = a[i] - o.a[i];
		}
		return ret;
	}
	public Vec mul(long c) {
		Vec ret = new Vec(a.length);
		for (int i = 0; i < a.length; i++) {
			ret.a[i] = a[i] * c;
		}
		return ret;
	}
	public long dot(Vec o) {
		assert a.length == o.a.length;
		long ret = 0;
		for (int i = 0; i < a.length; i++) {
			ret += a[i] * o.a[i];
		}
		return ret;
	}
	public long norm1() {
		long ret = 0;
		for (int i = 0; i < a.length; i++) {
			ret += Math.abs(a[i]);
		}
		return ret;
	}
	public long norm2Sq() {
		long ret = 0;
		for (int i = 0; i < a.length; i++) {
			ret += Math.abs(a[i] * a[i]);
		}
		return ret;
	}
	public double norm2() {
		return Math.sqrt(norm2Sq());
	}
	public long dist1(Vec o) {
		return sub(o).norm1();
	}
	public long dist2Sq(Vec o) {
		return sub(o).norm2Sq();
	}
	public double dist2(Vec o) {
		return Math.sqrt(dist2Sq(o));
	}

	public boolean equals(Vec o) {
		if ( a.length != o.a.length ) return false;
		for (int i = 0; i < a.length; i++) {
			if ( a[i] != o.a[i] ) return false;
		}
		return true;
	}
}
