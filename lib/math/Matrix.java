package math;
import java.util.Arrays;

public class Matrix {
	long[][] a = null;
	public Matrix(int H, int W) {
		a = new long[H][W];
	}
	public Matrix(long[][] a) {
		this(a, true);
	}
	Matrix(long[][] a, boolean copy) {
		if (copy) {
			this.a = new long[a.length][];
			for (int i = 0; i < a.length; i++) {
				this.a[i] = Arrays.copyOf(a[i], a[i].length);
			}
		} else {
			this.a = a;
		}
	}
	public long get(int i, int j) {
		return a[i][j];
	}
	public void set(int i, int j, long v) {
		a[i][j] = v;
	}
	public Matrix add(Matrix o) {
		return new Matrix(add(this.a, o.a), false);
	}
	public static long[][] add(long[][] a, long[][] b) {
		assert a.length == a.length;
		if ( a.length == 0 ) return new long[0][0];
		assert a[0].length == b[0].length;
		long[][] ret = new long[a.length][a[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				ret[i][j] = a[i][j] + b[i][j];
			}
		}
		return ret;
	}
	public Matrix addMod(Matrix o, long p) {
		return new Matrix(addMod(this.a, o.a, p), false);
	}
	public static long[][] addMod(long[][] a, long[][] b, long p) {
		assert a.length == b.length;
		if ( a.length == 0 ) return new long[0][0];
		assert a[0].length == b[0].length;
		long[][] ret = new long[a.length][a[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				ret[i][j] = (a[i][j] + b[i][j]) % p;
			}
		}
		return ret;
	}
	public Matrix mul(Matrix o) {
		return new Matrix(mul(this.a, o.a), false);
	}
	public static long[][] mul(long[][] a, long[][] b) {
		if ( b.length == 0 ) return new long[0][0];
		long[][] ret = new long[a.length][b[0].length];
		for ( int i = 0 ; i < a.length ; i++ ) {
			for ( int j = 0 ; j < b[0].length ; j++ ) {
				for ( int k = 0 ; k < a[i].length ; k++ ) {
					ret[i][j] += a[i][k] * b[k][j];
				}
			}
		}
		return ret;
	}
	public Matrix mulMod(Matrix o, long p) {
		return new Matrix(mulMod(this.a, o.a, p), false);
  }
	public static long[][] mulMod(long[][] a, long[][] b, long p) {
		if ( b.length == 0 ) return new long[0][0];
		long[][] ret = new long[a.length][b[0].length];
		for ( int i = 0 ; i < a.length ; i++ ) {
			for ( int j = 0 ; j < b[0].length ; j++ ) {
				for ( int k = 0 ; k < a[i].length ; k++ ) {
					ret[i][j] = (ret[i][j] + a[i][k] * b[k][j]) % p;
				}
			}
		}
		return ret;
	}
	public Matrix mul(long c) {
		return new Matrix(mul(this.a, c), false);
  }
	public static long[][] mul(long[][] a, long c) {
		if ( a.length == 0 ) return new long[0][0];
		long[][] ret = new long[a.length][a[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				ret[i][j] = c * a[i][j];
			}
		}
		return ret;
	}
	public Matrix mulMod(long c, long p) {
		return new Matrix(mulMod(this.a, c, p), false);
	}
	public static long[][] mulMod(long[][] a, long c, long p) {
		if ( a.length == 0 ) return new long[0][0];
		long[][] ret = new long[a.length][a[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				ret[i][j] = (c * a[i][j]) % p;
			}
		}
		return ret;
	}
	public Vec mul(Vec v) {
		return new Vec(mul(this.a, v.a), false);
	}
	public static long[] mul(long[][] a, long[] v) {
		if ( a.length == 0 ) return new long[0];
		long[] ret = new long[a.length];
		for (int i = 0; i < a.length; i++) {
			for ( int k = 0 ; k < a[i].length ; k++ ) {
				ret[i] += a[i][k] * v[k];
			}
		}
		return ret;
	}
	public Vec mulMod(Vec v, long p) {
		return new Vec(mulMod(this.a, v.a, p), false);
	}
	public static long[] mulMod(long[][] a, long[] v, long p) {
		if ( a.length == 0 ) return new long[0];
		long[] ret = new long[a.length];
		for (int i = 0; i < a.length; i++) {
			for ( int k = 0 ; k < a[i].length ; k++ ) {
				ret[i] = (ret[i] + a[i][k] * v[k]) % p;
			}
		}
		return ret;
	}
	public Matrix pow(long n) {
		return new Matrix(pow(this.a, n), false);
	}
	public static long[][] pow(long[][] a, long n) {
		long[][] ret = new long[a.length][a.length];
		long[][] A = new long[a.length][];
		for (int i = 0; i < a.length; i++) A[i] = Arrays.copyOf(a[i], a[i].length);
		for ( int i = 0 ; i < a.length ; i++ ) ret[i][i] = 1; 
		while ( n > 0 ) {
			if ( (n & 1L) != 0 ) ret = mul(ret, A);
			A = mul(A, A);
			n >>>= 1;
		}
		return ret;
	}
	public Matrix powMod(long n, long p) {
		return new Matrix(powMod(this.a, n, p), false);
	}
	public static long[][] powMod(long[][] a, long n, long p) {
		long[][] ret = new long[a.length][a.length];
		long[][] A = new long[a.length][];
		for (int i = 0; i < a.length; i++) A[i] = Arrays.copyOf(a[i], a[i].length);
		for ( int i = 0 ; i < a.length ; i++ ) ret[i][i] = 1; 
		while ( n > 0 ) {
			if ( (n & 1L) != 0 ) ret = mulMod(ret, A, p);
			A = mulMod(A, A, p);
			n >>>= 1;
		}
		return ret;
	}

	public boolean equals(Matrix o) {
		if ( a.length != o.a.length ) return false;
		for (int i = 0; i < a.length; i++) {
			if ( a[i].length != o.a[i].length ) return false;
			for (int j = 0; j < a[i].length; j++) {
				if ( a[i] != o.a[i] ) return false;
			}
		}
		return true;
	}
}
