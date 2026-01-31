package math;

import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;

public class Bisect {
	public static int minTrueInt(int low, int high, IntPredicate condition) {
		assert low <= high;
		while (high - low > 1) {
			int mid = low + (high - low) / 2;
			if (condition.test(mid)) {
				high = mid;
			} else {
				low = mid;
			}
		}
		return high;
	}
	public static long minTrueLong(long low, long high, LongPredicate condition) {
		assert low <= high;
		while (high - low > 1) {
			long mid = low + (high - low) / 2;
			if (condition.test(mid)) {
				high = mid;
			} else {
				low = mid;
			}
		}
		return high;
	}
	public static double minTrueDouble(double low, double high, double eps, DoublePredicate condition) {
		assert low <= high;
		while (high - low > eps) {
			double mid = low + (high - low) / 2;
			if (condition.test(mid)) {
				high = mid;
			} else {
				low = mid;
			}
		}
		return high;
	}
	public static int maxTrueInt(int low, int high, IntPredicate condition) {
		assert low <= high;
		while (high - low > 1) {
			int mid = low + (high - low) / 2;
			if (condition.test(mid)) {
				low = mid;
			} else {
				high = mid;
			}
		}
		return low;
	}
	public static long maxTrueLong(long low, long high, LongPredicate condition) {
		assert low <= high;
		while (high - low > 1) {
			long mid = low + (high - low) / 2;
			if (condition.test(mid)) {
				low = mid;
			} else {
				high = mid;
			}
		}
		return low;
	}
	public static double maxTrueDouble(double low, double high, double eps, DoublePredicate condition) {
		assert low <= high;
		while (high - low > eps) {
			double mid = low + (high - low) / 2;
			if (condition.test(mid)) {
				low = mid;
			} else {
				high = mid;
			}
		}
		return low;
	}
}
