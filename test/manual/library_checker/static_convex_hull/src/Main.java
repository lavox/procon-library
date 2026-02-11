import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

import java.util.Arrays;
import java.util.Comparator;

// template & library : https://github.com/lavox/procon-library
public class Main {
	public static void main(String[] args) {
		Main o = new Main();
		o.solve();
	}

	public void solve() {
		FastScanner sc = new FastScanner(System.in);
		int T = sc.nextInt();
		StringBuilder out = new StringBuilder();
		for (int t = 0; t < T; t++) {
			int N = sc.nextInt();
			ArrayList<Vec2L> pts = new ArrayList<>(N);
			for (int i = 0; i < N; i++) {
				long x = sc.nextLong();
				long y = sc.nextLong();
				pts.add(new Vec2L(x, y));
			}
			ArrayList<Vec2L> ch = GeomUtil.convexHull(pts);
			out.append(ch.size());
			out.append(LF);
			for (Vec2L p: ch) {
				out.append(p.x);
				out.append(SPACE);
				out.append(p.y);
				out.append(LF);
			}
		}

		System.out.print(out.toString());
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

// === begin: geometry/GeomUtil.java ===
class GeomUtil {
	public static double EPS = 1e-9;
	public static boolean eq(double a, double b) {
		return Math.abs(a - b) < EPS;
	}
	public static boolean isZero(double a) {
		return Math.abs(a) < EPS;
	}

	public static ArrayList<Vec2L> convexHull(ArrayList<Vec2L> points) {
		if (points.size() == 0) return new ArrayList<>();
		Vec2L[] pts = points.toArray(new Vec2L[points.size()]);
		Arrays.sort(pts, (p1, p2) -> {
			if (p1.x != p2.x) {
				return Long.compare(p1.x, p2.x);
			} else {
				return Long.compare(p1.y, p2.y);
			}
		});
		ArrayList<Vec2L> ret = new ArrayList<>();
		ret.add(pts[0]);
		for (int i = 0; i < pts.length; i++) {
			while (ret.size() > 1 && !checkCcw(ret, pts[i])) {
				ret.remove(ret.size() - 1);
			}
			ret.add(pts[i]);
		}
		int sz1 = ret.size();
		for (int i = pts.length - 2; i >= 0; i--) {
			while ( ret.size() > sz1 && !checkCcw(ret, pts[i])) {
				ret.remove(ret.size() - 1);
			}
			ret.add(pts[i]);
		}
		while (ret.size() > 1 && ret.get(0).equals(ret.get(ret.size() - 1))) ret.remove(ret.size() - 1);
		return ret;
	}
	private static boolean checkCcw(ArrayList<Vec2L> hull, Vec2L p) {
		Vec2L p0 = hull.get(hull.size() - 2);
		Vec2L p1 = hull.get(hull.size() - 1);
		return (p1.x - p0.x) * (p.y - p1.y) - (p1.y - p0.y) * (p.x - p1.x) > 0;
	}
}
// === end: geometry/GeomUtil.java ===

// === begin: geometry/Vec2L.java ===
class Vec2L {
	long x;
	long y;
	public Vec2L(long x, long y) {
		this.x = x;
		this.y = y;
	}
	public Vec2L clone() {
		return new Vec2L(x, y);
	}
	public Vec2L add(Vec2L o) {
		return new Vec2L(x + o.x, y + o.y);
	}
	public Vec2L addAsn(Vec2L o) {
		x += o.x;
		y += o.y;
		return this;
	}
	public Vec2L sub(Vec2L o) {
		return new Vec2L(x - o.x, y - o.y);
	}
	public Vec2L subAsn(Vec2L o) {
		x -= o.x;
		y -= o.y;
		return this;
	}
	public Vec2L mul(long a) {
		return new Vec2L(a * x, a * y);
	}
	public Vec2L mulAsn(long a) {
		x *= a;
		y *= a;
		return this;
	}
	public Vec2L div(long a) {
		return new Vec2L(x / a, y / a);
	}
	public Vec2L divAsn(long a) {
		x /= a;
		y /= a;
		return this;
	}

	public long dot(Vec2L o) {
		return x * o.x + y * o.y;
	}
	public long cross(Vec2L o) {
		return x * o.y - y * o.x;
	}
	public double norm() {
		return Math.sqrt(x * x + y * y);
	}
	public long normSq() {
		return x * x + y * y;
	}
	public Vec2L rot(int q) {
		switch (q) {
			case 1:
				return rot90();
			case 2:
				return rot180();
			case 3:
				return rot270();
			default:
				return this.clone();
		}
	}
	public Vec2L rotAsn(int q) {
		switch (q) {
			case 1:
				return rot90Asn();
			case 2:
				return rot180Asn();
			case 3:
				return rot270Asn();
			default:
				return this;
		}
	}
	public Vec2L rot90() {
		return new Vec2L(-y, x);
	}
	public Vec2L rot90Asn() {
		long nx = -y;
		long ny = x;
		x = nx;
		y = ny;
		return this;
	}
	public Vec2L rot180() {
		return new Vec2L(-x, -y);
	}
	public Vec2L rot180Asn() {
		x = -x;
		y = -y;
		return this;
	}
	public Vec2L rot270() {
		return new Vec2L(y, -x);
	}
	public Vec2L rot270Asn() {
		long nx = y;
		long ny = -x;
		x = nx;
		y = ny;
		return this;
	}
	public double dist(Vec2L o) {
		return dist(this, o);
	}
	public long distSq(Vec2L o) {
		return distSq(this, o);
	}
	public static double dist(Vec2L a, Vec2L b) {
		return Math.sqrt(distSq(a, b));
	}
	public static long distSq(Vec2L a, Vec2L b) {
		long dx = a.x - b.x;
		long dy = a.y - b.y;
		return dx * dx + dy * dy;
	}
	public static final Comparator<Vec2L> ARG_CMP = (a, b) -> {
		int ha = a.y < 0 ? -1 : (a.x >= 0 && a.y == 0 ? 0 : 1);
		int hb = b.y < 0 ? -1 : (b.x >= 0 && b.y == 0 ? 0 : 1);
		if (ha != hb) return Integer.compare(ha, hb);
		return -Long.compare(a.x * b.y, a.y * b.x);
	};

	@Override
	public boolean equals(Object o) {
		if (o instanceof Vec2L) {
			Vec2L v = (Vec2L)o;
			return x == v.x && y == v.y;
		}
		return false;
	}
}
// === end: geometry/Vec2L.java ===
