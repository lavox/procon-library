import java.util.Arrays;

class Matrix {
	long[][] a = null;
	public Matrix(int H, int W) {
		a = new long[H][W];
	}
	public Matrix(long[][] a) {
		this.a = new long[a.length][];
		for (int i = 0; i < a.length; i++) {
			this.a[i] = Arrays.copyOf(a[i], a[i].length);
		}
	}
	public long get(int i, int j) {
		return a[i][j];
	}
	public void set(int i, int j, long v) {
		a[i][j] = v;
	}
	public Matrix add(Matrix o) {
		assert a.length == o.a.length;
		if ( a.length == 0 ) return new Matrix(0, 0);
		assert a[0].length == o.a[0].length;
		Matrix ret = new Matrix(a.length, a[0].length);
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				ret.a[i][j] = a[i][j] + o.a[i][j];
			}
		}
		return ret;
	}
	public Matrix addMod(Matrix o, long p) {
		assert a.length == o.a.length;
		if ( a.length == 0 ) return new Matrix(0, 0);
		assert a[0].length == o.a[0].length;
		Matrix ret = new Matrix(a.length, a[0].length);
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				ret.a[i][j] = (a[i][j] + o.a[i][j]) % p;
			}
		}
		return ret;
	}
	public Matrix mul(Matrix o) {
		if ( o.a.length == 0 ) return new Matrix(0, 0);
		Matrix ret = new Matrix(a.length, o.a[0].length);
		for ( int i = 0 ; i < a.length ; i++ ) {
			for ( int j = 0 ; j < o.a[0].length ; j++ ) {
				for ( int k = 0 ; k < a[i].length ; k++ ) {
					ret.a[i][j] += a[i][k] * o.a[k][j];
				}
			}
		}
		return ret;
	}
	public Matrix mulMod(Matrix o, long p) {
		if ( o.a.length == 0 ) return new Matrix(0, 0);
		Matrix ret = new Matrix(a.length, o.a[0].length);
		for ( int i = 0 ; i < a.length ; i++ ) {
			for ( int j = 0 ; j < o.a[0].length ; j++ ) {
				for ( int k = 0 ; k < a[i].length ; k++ ) {
					ret.a[i][j] = (ret.a[i][j] + a[i][k] * o.a[k][j]) % p;
				}
			}
		}
		return ret;
  }
	public Matrix mul(long c) {
		if ( a.length == 0 ) return new Matrix(0, 0);
		Matrix ret = new Matrix(a.length, a[0].length);
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				ret.a[i][j] = c * a[i][j];
			}
		}
		return ret;
  }
	public Matrix mulMod(long c, long p) {
		if ( a.length == 0 ) return new Matrix(0, 0);
		Matrix ret = new Matrix(a.length, a[0].length);
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				ret.a[i][j] = (c * a[i][j]) % p;
			}
		}
		return ret;
	}
	public Vec mul(Vec v) {
		if ( a.length == 0 ) return new Vec(0);
		Vec ret = new Vec(a.length);
		for (int i = 0; i < a.length; i++) {
			for ( int k = 0 ; k < a[i].length ; k++ ) {
				ret.a[i] += a[i][k] * v.a[k];
			}
		}
		return ret;
	}
	public Vec mulMod(Vec v, long p) {
		if ( a.length == 0 ) return new Vec(0);
		Vec ret = new Vec(a.length);
		for (int i = 0; i < a.length; i++) {
			for ( int k = 0 ; k < a[i].length ; k++ ) {
				ret.a[i] = (ret.a[i] + a[i][k] * v.a[k]) % p;
			}
		}
		return ret;
	}
	public Matrix pow(long n) {
		Matrix ret = new Matrix(a.length, a.length);
		Matrix A = new Matrix(this.a);
		for ( int i = 0 ; i < a.length ; i++ ) ret.a[i][i] = 1; 
		while ( n > 0 ) {
			if ( (n & 1L) != 0 ) ret = ret.mul(A);
			A = A.mul(A);
			n >>>= 1;
		}
		return ret;
	}
	public Matrix powMod(long n, long p) {
		Matrix ret = new Matrix(a.length, a.length);
		Matrix A = new Matrix(this.a);
		for ( int i = 0 ; i < a.length ; i++ ) ret.a[i][i] = 1; 
		while ( n > 0 ) {
			if ( (n & 1L) != 0 ) ret = ret.mulMod(A, p);
			A = A.mulMod(A, p);
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
