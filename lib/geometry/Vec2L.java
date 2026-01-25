package geometry;

import java.util.Comparator;

public class Vec2L {
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
