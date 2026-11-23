package data_structure;

import java.util.Arrays;

// https://github.com/lavox/procon-library/blob/main/lib/data_structure/BitSetEx.java
public class BitSetEx {
	private int n;
	private int length;
	private final long[] words;
	private static final int ADDRESS_BITS_PER_WORD = 6;
	private static final long WORD_MASK = 0xffffffffffffffffL;
	private static final int BITS_PER_WORD = 1 << ADDRESS_BITS_PER_WORD;

	public BitSetEx(int n) {
		this.n = n;
		this.length = n > 0 ? wordIndex(n - 1) + 1 : 0;
		this.words = new long[length];
	}
	private BitSetEx(long[] words, int n) {
		this.n = n;
		this.length = n > 0 ? wordIndex(n - 1) + 1 : 0;
		this.words = Arrays.copyOf(words, length);
	}
	private BitSetEx(BitSetEx from) {
		this(from.words, from.n);
	}
	@Override
	public BitSetEx clone() {
		return new BitSetEx(this);
	}

	private static int wordIndex(int bitIndex) {
		return bitIndex >> ADDRESS_BITS_PER_WORD;
	}
	public static BitSetEx valueOf(long[] longs) {
		return new BitSetEx(longs, longs.length << ADDRESS_BITS_PER_WORD);
	}
	public long[] toLongArray() {
		return words.clone();
	}

	public void flip(int bitIndex) {
		int wi = wordIndex(bitIndex);
		words[wi] ^= 1L << bitIndex;
	}
	public void flip(int fromIndex, int toIndex) {
		if (fromIndex >= toIndex) {
			if (fromIndex == toIndex) return;
			throw new IndexOutOfBoundsException(String.format("fromIndex: %d > toIndex: %d", fromIndex, toIndex));
		}
		int wi0 = wordIndex(fromIndex);
		int wi1 = wordIndex(toIndex - 1);
		long word0 = WORD_MASK << fromIndex;
		long word1 = WORD_MASK >>> -toIndex;
		if (wi0 == wi1) {
			words[wi0] ^= word0 & word1;
		} else {
			words[wi0] ^= word0;
			for (int i = wi0 + 1; i < wi1; i++) words[i] ^= WORD_MASK;
			words[wi1] ^= word1;
		}
	}

	public void set(int bitIndex) {
		int wi = wordIndex(bitIndex);
		words[wi] |= (1L << bitIndex);
	}
	public void set(int bitIndex, boolean value) {
		if (value) {
			set(bitIndex);
		} else {
			clear(bitIndex);
		}
	}
	public void set(int fromIndex, int toIndex) {
		if (fromIndex >= toIndex) {
			if (fromIndex == toIndex) return;
			throw new IndexOutOfBoundsException(String.format("fromIndex: %d > toIndex: %d", fromIndex, toIndex));
		}
		int wi0 = wordIndex(fromIndex);
		int wi1 = wordIndex(toIndex - 1);
		long word0 = WORD_MASK << fromIndex;
		long word1 = WORD_MASK >>> -toIndex;
		if (wi0 == wi1) {
			words[wi0] |= word0 & word1;
		} else {
			words[wi0] |= word0;
			for (int i = wi0 + 1; i < wi1; i++) words[i] |= WORD_MASK;
			words[wi1] |= word1;
		}
	}
	public void set(int fromIndex, int toIndex, boolean value) {
		if (value) {
			set(fromIndex, toIndex);
		} else {
			clear(fromIndex, toIndex);
		}
	}

	public void clear(int bitIndex) {
		int wi = wordIndex(bitIndex);
		words[wi] &= ~(1L << bitIndex);
	}
	public void clear(int fromIndex, int toIndex) {
		if (fromIndex >= toIndex) {
			if (fromIndex == toIndex) return;
			throw new IndexOutOfBoundsException(String.format("fromIndex: %d > toIndex: %d", fromIndex, toIndex));
		}
		int wi0 = wordIndex(fromIndex);
		int wi1 = wordIndex(toIndex - 1);
		long word0 = WORD_MASK << fromIndex;
		long word1 = WORD_MASK >>> -toIndex;
		if (wi0 == wi1) {
			words[wi0] &= ~(word0 & word1);
		} else {
			words[wi0] &= ~word0;
			Arrays.fill(words, wi0 + 1, wi1, 0L);
			words[wi1] &= ~word1;
		}
	}
	public void clear() {
		Arrays.fill(words, 0L);
	}

	public boolean get(int bitIndex) {
		int wi = wordIndex(bitIndex);
		return (words[wi] & (1L << bitIndex)) != 0;
	}
	public BitSetEx get(int fromIndex, int toIndex) {
		if (fromIndex > toIndex) throw new IndexOutOfBoundsException(String.format("fromIndex: %d > toIndex: %d", fromIndex, toIndex));
		if (fromIndex == toIndex) return new BitSetEx(0);
		BitSetEx ret = new BitSetEx(toIndex - fromIndex);
		int wi0 = wordIndex(fromIndex);
		int wi1 = wordIndex(toIndex - 1);
		long mask = WORD_MASK >>> -toIndex;
		if (wi0 == wi1) {
			ret.words[0] = (this.words[wi0] & mask) >>> fromIndex;
		} else if ((fromIndex & 0x3f) == 0) {
			System.arraycopy(this.words, wi0, ret.words, 0, wi1 - wi0 + 1);
			ret.words[wi1 - wi0] &= mask;
		} else {
			ret.words[0] = this.words[wi0] >>> fromIndex;
			for (int i = wi0 + 1; i < wi1; i++) {
				ret.words[i - wi0 - 1] |= this.words[i] << -fromIndex;
				ret.words[i - wi0] = this.words[i] >>> fromIndex;
			}
			ret.words[wi1 - wi0 - 1] |= (this.words[wi1] & mask) << -fromIndex;
			if (wi1 - wi0 < ret.words.length) {
				ret.words[wi1 - wi0] = (this.words[wi1] & mask) >>> fromIndex;
			}
		}
		return ret;
	}
	
	public int nextSetBit(int fromIndex) {
		if (fromIndex >= n) return -1;
		int wi = wordIndex(fromIndex);
		long word = words[wi] & (WORD_MASK << fromIndex);
		while (true) {
			if (word != 0) return (wi * BITS_PER_WORD) + Long.numberOfTrailingZeros(word);
			if (++wi == words.length) return -1;
			word = words[wi];
		}
	}
	public int nextClearBit(int fromIndex) {
		if (fromIndex >= n) return -1;
		int wi = wordIndex(fromIndex);
		long word = ~words[wi] & (WORD_MASK << fromIndex);
		while (true) {
			if (word != 0) {
				int ret = (wi * BITS_PER_WORD) + Long.numberOfTrailingZeros(word);
				return ret < n ? ret : -1;
			}
			if (++wi == words.length) return -1;
			word = ~words[wi];
		}
	}
	public int previousSetBit(int fromIndex) {
		if (fromIndex < 0) return -1;
		int wi = wordIndex(fromIndex);
		long word = words[wi] & (WORD_MASK >>> -(fromIndex + 1));
		while (true) {
			if (word != 0) return (wi + 1) * BITS_PER_WORD - 1 - Long.numberOfLeadingZeros(word);
			if (wi-- == 0) return -1;
			word = words[wi];
		}
	}
	public int previousClearBit(int fromIndex) {
		if (fromIndex < 0) return -1;
		int wi = wordIndex(fromIndex);
		long word = ~words[wi] & (WORD_MASK >>> -(fromIndex + 1));
		while (true) {
			if (word != 0) return (wi + 1) * BITS_PER_WORD - 1 - Long.numberOfLeadingZeros(word);
			if (wi-- == 0) return -1;
			word = ~words[wi];
		}
	}

	public int length() {
		return n;
	}
	public boolean isEmpty() {
		for (int i = 0; i < length; i++) {
			if (words[i] != 0) return false;
		}
		return true;
	}

	public boolean intersects(BitSetEx set) {
		int last = Math.min(this.length, set.length);
		for (int i = 0; i < last; i++) {
			if ((this.words[i] & set.words[i]) != 0) return true;
		}
		return false;
	}

	public int cardinality() {
		int ret = 0;
		for (int i = 0; i < length; i++) ret += Long.bitCount(words[i]);
		return ret;
	}

	public void and(BitSetEx set) {
		if (this == set) return;
		if (set.length < this.length) Arrays.fill(this.words, set.length, this.length, 0);
		int last = Math.min(this.length, set.length);
		for (int i = 0; i < last; i++) this.words[i] &= set.words[i];
	}
	public void or(BitSetEx set) {
		if (this == set) return;
		int last = Math.min(this.length, set.length);
		for (int i = 0; i < last; i++) this.words[i] |= set.words[i];
		if (length > 0) this.words[length - 1] &= WORD_MASK >>> -n;
	}
	public void xor(BitSetEx set) {
		if (this == set) { this.clear(); return; }
		int last = Math.min(this.length, set.length);
		for (int i = 0; i < last; i++) this.words[i] ^= set.words[i];
		if (length > 0) this.words[length - 1] &= WORD_MASK >>> -n;
	}
	public void andNot(BitSetEx set) {
		if (this == set) { this.clear(); return; }
		int last = Math.min(this.length, set.length);
		for (int i = 0; i < last; i++) this.words[i] &= ~set.words[i];
	}

	public int hashCode() {
		long h = 1234;
		for (int i = length - 1; i >= 0; i--) h ^= words[i] * (i + 1);
		return (int)((h >> 32) ^ h);
	}
	public int size() {
		return n;
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o instanceof BitSetEx) {
			BitSetEx bs = (BitSetEx)o;
			if (this.n != bs.n) return false;
			for (int i = 0; i < length; i++) {
				if (this.words[i] != bs.words[i]) return false;
			}
			return true;
		}
		return false;
	}

	public BitSetEx leftShift(int bits) {
		BitSetEx ret = new BitSetEx(n);
		if (bits >= n) return ret;
		int a = wordIndex(bits);
		if ((bits & 0x3f) == 0) {
			System.arraycopy(this.words, 0, ret.words, a, length - a);
		} else {
			ret.words[a] = this.words[0] << bits;
			for (int i = a + 1; i < length; i++) {
				ret.words[i] = this.words[i - a - 1] >>> -bits;
				ret.words[i] |= this.words[i - a] << bits;
			}
		}
		ret.words[length - 1] &= WORD_MASK >>> -n;
		return ret;
	}
	public void leftShiftAsn(int bits) {
		if (bits == 0) return;
		if (bits >= n) { clear(); return; }
		int a = wordIndex(bits);
		if ((bits & 0x3f) == 0) {
			for (int i = length - 1; i >= a; i--) words[i] = words[i - a];
			Arrays.fill(words, 0, a, 0L);
		} else {
			for (int i = length - 1; i >= a + 1; i--) {
				words[i] = words[i - a] << bits;
				words[i] |= words[i - a - 1] >>> -bits;
			}
			words[a] = words[0] << bits;
			Arrays.fill(words, 0, a, 0L);
		}
		words[length - 1] &= WORD_MASK >>> -n;
	}
	public void orLeftShiftAsn(int bits) {
		if (bits == 0) return;
		if (bits >= n) return;
		int a = wordIndex(bits);
		if ((bits & 0x3f) == 0) {
			for (int i = length - 1; i >= a; i--) words[i] |= words[i - a];
		} else {
			for (int i = length - 1; i >= a + 1; i--) {
				words[i] |= words[i - a] << bits;
				words[i] |= words[i - a - 1] >>> -bits;
			}
			words[a] |= words[0] << bits;
		}
		words[length - 1] &= WORD_MASK >>> -n;
	}

	public BitSetEx rightShift(int bits) {
		BitSetEx ret = new BitSetEx(n);
		if (bits >= n) return ret;
		int a = wordIndex(bits);
		if ((bits & 0x3f) == 0) {
			System.arraycopy(this.words, a, ret.words, 0, length - a);
		} else {
			int last = length - a - 1;
			for (int i = 0; i < last; i++) {
				ret.words[i] = this.words[i + a] >>> bits;
				ret.words[i] |= this.words[i + a + 1] << -bits;
			}
			ret.words[last] = this.words[length - 1] >>> bits;
		}
		return ret;
	}
	public void rightShiftAsn(int bits) {
		if (bits == 0) return;
		if (bits >= n) { clear(); return; }
		int a = wordIndex(bits);
		if ((bits & 0x3f) == 0) {
			for (int i = 0; i < length - a; i++) words[i] = words[i + a];
			Arrays.fill(words, length - a, length, 0L);
		} else {
			int last = length - a - 1;
			for (int i = 0; i < last; i++) {
				words[i] = this.words[i + a] >>> bits;
				words[i] |= this.words[i + a + 1] << -bits;
			}
			words[last] = this.words[length - 1] >>> bits;
			Arrays.fill(words, last + 1, length, 0L);
		}
	}
	public void orRightShiftAsn(int bits) {
		if (bits == 0) return;
		if (bits >= n) return;
		int a = wordIndex(bits);
		if ((bits & 0x3f) == 0) {
			for (int i = 0; i < length - a; i++) words[i] |= words[i + a];
		} else {
			int last = length - a - 1;
			for (int i = 0; i < last; i++) {
				words[i] |= this.words[i + a] >>> bits;
				words[i] |= this.words[i + a + 1] << -bits;
			}
			words[last] |= this.words[length - 1] >>> bits;
		}
	}

	public boolean isSubsetOf(BitSetEx set) {
		int last = Math.min(this.length, set.length);
		for (int i = 0; i < last; i++) {
			if ((this.words[i] & ~set.words[i]) != 0) return false;
		}
		for (int i = last; i < length; i++) {
			if (this.words[i] != 0) return false;
		}
		return true;
	}
}
