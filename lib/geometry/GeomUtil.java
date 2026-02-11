package geometry;

import java.util.ArrayList;
import java.util.Arrays;

public class GeomUtil {
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
