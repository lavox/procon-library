package data_structure;

import java.util.Arrays;

public class WaveletMatrix {
	private int n = 0;
	private int m = 0;
	private int height = 0;
	private long[][] bits = null;
	private int[][] cum = null;
	private int[] cnt0 = null;
	private static final int BLEN = 6;
	private static final int BMASK = (1 << BLEN) - 1;

	public WaveletMatrix(int[] arr, int height) {
		this.n = arr.length;
		this.m = (n + 63) >>> BLEN;
		this.height = height;
		this.build(arr);
	}
	public WaveletMatrix(int[] arr) {
		this(arr, 32 - Integer.numberOfLeadingZeros(Arrays.stream(arr).max().orElse(0)));
	}
	private void build(int[] arr) {
		this.bits = new long[height][m + 1];
		this.cum = new int[height][m + 1];
		this.cnt0 = new int[height];
		int[] cur = arr.clone();
		int[] tmp = new int[n];
		for (int h = height - 1; h >= 0; h--) {
			int i0 = 0;
			int i1 = 0;
			for (int i = 0; i < n; i++) {
				if (((cur[i] >>> h) & 1) == 0) {
					cur[i0++] = cur[i];
				} else {
					tmp[i1++] = cur[i];
					set(h, i);
				}
			}
			this.cnt0[h] = i0;
			System.arraycopy(tmp, 0, cur, i0, i1);
			prepareCumulative(h);
		}
	}
	private void prepareCumulative(int h) {
		for (int idx = 0; idx < m; idx++) {
			cum[h][idx + 1] = cum[h][idx] + Long.bitCount(bits[h][idx]);
		}
	}

	private void set(int h, int i) {
		bits[h][i >>> BLEN] |= 1L << (i & BMASK);
	}
	public int get(int h, int i) {
		return (int)((bits[h][i >>> BLEN] >>> (i & BMASK)) & 1L);
	}
	public int rank1(int h, int i) {
		int idx = i >>> BLEN;
		return cum[h][idx] + Long.bitCount(bits[h][idx] & ((1L << (i & BMASK)) - 1L));
	}
	public int rank0(int h, int i) {
		return i - rank1(h, i);
	}
	public int next_i0(int h, int i) {
		return rank0(h, i);
	}
	public int next_i1(int h, int i) {
		return cnt0[h] + rank1(h, i);
	}
	public int access(int i) {
		int ret = 0;
		int c = i;
		for (int h = height - 1; h >= 0; h--) {
			int b = get(h, c);
			int c1 = rank1(h, c);
			if (b == 0) {
				c -= c1;
			} else {
				ret |= 1 << h;
				c = cnt0[h] + c1;
			}
		}
		return ret;
	}
	public int quantile(int l, int r, int k) {
		if (k >= r - l) throw new IllegalArgumentException();
		int ret = 0;
		for (int h = height - 1; h >= 0; h--) {
			int lc1 = rank1(h, l);
			int rc1 = rank1(h, r);
			int len = (r - rc1) - (l - lc1);
			if (k < len) {
				l -= lc1;
				r -= rc1;
			} else {
				ret |= 1 << h;
				k -= len;
				l = cnt0[h] + lc1;
				r = cnt0[h] + rc1;
			}
		}
		return ret;
	}
	public int rangeFreq(int l, int r, int vmin, int vmax) {
		return rangeFreqBelow(l, r, vmax) - rangeFreqBelow(l, r, vmin);
	}
	public int rangeFreqBelow(int l, int r, int vmax) {
		if (vmax >= 1 << height) return r - l;
		int ret = 0;
		for (int h = height - 1; h >= 0; h--) {
			int lc1 = rank1(h, l);
			int rc1 = rank1(h, r);
			if (((vmax >>> h) & 1) == 0) {
				l -= lc1;
				r -= rc1;
			} else {
				ret += (r - rc1) - (l - lc1);
				l = cnt0[h] + lc1;
				r = cnt0[h] + rc1;
			}
		}
		return ret;
	}
	public int rangeFreqAbove(int l, int r, int vmin) {
		return r - l - rangeFreqBelow(l, r, vmin);
	}
	public int prevValue(int l, int r, int vmax) {
		int cnt = rangeFreqBelow(l, r, vmax);
		return cnt == 0 ? -1 : quantile(l, r, cnt - 1);
	}
	public int nextValue(int l, int r, int vmin) {
		int cnt = rangeFreqBelow(l, r, vmin);
		return cnt == r - l ? -1 : quantile(l, r, cnt);
	}

	public Range createRange(int l, int r) {
		return new Range(height - 1, l, r);
	}
	public class Range {
		int h = 0;
		int l = 0;
		int r = 0;
		int next_l0 = 0;
		int next_r0 = 0;
		int next_l1 = 0;
		int next_r1 = 0;
		public Range(int h, int l, int r) {
			this.h = h;
			this.l = l;
			this.r = r;
			calcNext();
		}
		private void calcNext() {
			this.next_l0 = rank0(h, l);
			this.next_r0 = rank0(h, r);
			this.next_l1 = cnt0[h] + l - this.next_l0;
			this.next_r1 = cnt0[h] + r - this.next_r0;
		}
		public void move0() {
			h--;
			l = next_l0;
			r = next_r0;
			if (h >= 0) calcNext();
		}
		public void move1() {
			h--;
			l = next_l1;
			r = next_r1;
			if (h >= 0) calcNext();
		}
		public int l() { return l; }
		public int r() { return r; }
		public int len() { return r - l; }
		public int next_l0() { return next_l0; }
		public int next_r0() { return next_r0; }
		public int next_l1() { return next_l1; }
		public int next_r1() { return next_r1; }
		public int next_len0() { return next_r0 - next_l0; }
		public int next_len1() { return next_r1 - next_l1; }
	}
}
