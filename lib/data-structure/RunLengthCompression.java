import java.util.ArrayList;
import java.util.function.IntUnaryOperator;

class RunLengthCompression {
	public static ArrayList<RunLength> encode(String str) {
		return encode((i) -> (int)str.charAt(i), str.length());
	}
	public static ArrayList<RunLength> encode(int[] vals) {
		return encode((i) -> vals[i], vals.length);
	}
	public static ArrayList<RunLength> encode(IntUnaryOperator op, int addLen) {
		return add(new ArrayList<>(), op, addLen);
	}
	public static ArrayList<RunLength> add(ArrayList<RunLength> array, String str) {
		return add(array, (i) -> str.charAt(i), str.length());
	}
	public static ArrayList<RunLength> add(ArrayList<RunLength> array, int[] vals) {
		return add(array, (i) -> vals[i], vals.length);
	}
	public static ArrayList<RunLength> add(ArrayList<RunLength> array, int val, long length) {
		RunLength prev = array.size() == 0 ? null : array.get(array.size() - 1);
		if (prev != null && prev.val == val) {
			prev.length += length;
		} else {
			array.add(new RunLength(val, length, prev.i0 + prev.length));
		}
		return array;
	}
	public static ArrayList<RunLength> add(ArrayList<RunLength> array, char c, long length) {
		return add(array, (int)c, length);
	}
	public static ArrayList<RunLength> add(ArrayList<RunLength> array, IntUnaryOperator op, int addLen) {
		RunLength prev = array.size() == 0 ? null : array.get(array.size() - 1);
		for (int i = 0; i < addLen; i++) {
			int v = op.applyAsInt(i);
			if (prev == null) {
				prev = new RunLength(v, 1, 0);
				array.add(prev);
				continue;
			} else if (prev.val == v) {
				prev.length++;
			} else {
				prev = new RunLength(v, 1, prev.i0 + prev.length);
				array.add(prev);
			}
		}
		return array;
	}
	public static ArrayList<RunLength> add(ArrayList<RunLength> array, RunLength e) {
		if (array.size() != 0 && array.get(array.size() - 1).val == e.val) {
			array.get(array.size() - 1).length += e.length;
			return array;
		}
		e.i0 = totalLength(array);
		array.add(e);
		return array;
	}
	public static long totalLength(ArrayList<RunLength> array) {
		if (array.size() == 0) return 0;
		RunLength last = array.get(array.size() - 1);
		return last.i0 + last.length;
	}
	public static int searchIndex(ArrayList<RunLength> array, long i) {
		if (i >= totalLength(array)) return array.size();
		int min = 0;
		int max = array.size();
		while (max - min > 1) {
			int mid = (min + max) / 2;
			if (i < array.get(mid).i0) {
				max = mid;
			} else {
				min = mid;
			}
		}
		return min;
	}
	public static RunLength search(ArrayList<RunLength> array, long i) {
		int ai = searchIndex(array, i);
		return ai < array.size() ? array.get(ai) : null;
	}
	public static int[] decode(ArrayList<RunLength> array) {
		int[] ret = new int[(int)totalLength(array)];
		int i = 0;
		for (RunLength e: array) {
			for (int j = 0; j < e.length; j++) ret[i++] = e.val;
		}
		return ret;
	}
	public static String decodeAsString(ArrayList<RunLength> array) {
		char[] ret = new char[(int)totalLength(array)];
		int i = 0;
		for (RunLength e: array) {
			for (int j = 0; j < e.length; j++) ret[i++] = (char)e.val;
		}
		return new String(ret);
	}
}
class RunLength {
	public int val;
	public long length;
	public long i0;
	public RunLength(int val, long length) {
		this(val, length, 0);
	}
	public RunLength(int val, long length, long i0) {
		this.val = val;
		this.length = length;
		this.i0 = i0;
	}
}
