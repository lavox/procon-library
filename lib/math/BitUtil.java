package math;

import java.util.Arrays;

// https://github.com/lavox/procon-library/blob/main/lib/math/BitUtil.java
public class BitUtil {
	public static boolean check(int bit, int k) {
		return ((bit >>> k) & 1) == 1;
	}
	public static boolean check(long bit, int k) {
		return ((bit >>> k) & 1L) == 1L;
	}
	public static int[] xorBase(int[] vec) {
		return xorBase(vec, 32);
	}
	public static int[] xorBase(int[] vec, int maxRank) {
		if (maxRank == 0) return new int[0];
		int[] base = new int[Math.min(vec.length, maxRank)];
		int cnt = 0;
		for (int v: vec) {
			for (int i = 0; i < cnt; i++) {
				int x = v ^ base[i];
				v = Integer.compareUnsigned(v, x) < 0 ? v : x;
			}
			if (v != 0) {
				base[cnt++] = v;
				if (cnt >= maxRank) break;
			}
		}
		return Arrays.copyOf(base, cnt);
	}
	public static long[] xorBase(long[] vec) {
		return xorBase(vec, 64);
	}
	public static long[] xorBase(long[] vec, int maxRank) {
		if (maxRank == 0) return new long[0];
		long[] base = new long[Math.min(vec.length, maxRank)];
		int cnt = 0;
		for (long v: vec) {
			for (int i = 0; i < cnt; i++) {
				long x = v ^ base[i];
				v = Long.compareUnsigned(v, x) < 0 ? v : x;
			}
			if (v != 0) {
				base[cnt++] = v;
				if (cnt >= maxRank) break;
			}
		}
		return Arrays.copyOf(base, cnt);
	}
}
