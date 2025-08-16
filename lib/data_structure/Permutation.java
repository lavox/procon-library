package data_structure;
import java.util.Arrays;

public class Permutation {
	private int[] value = null;
	private int[] index = null;
	public Permutation(int n) {
		value = new int[n];
		for (int i = 0; i < n; i++) value[i] = i;
		index = Arrays.copyOf(value, n);
	}
	private Permutation(int[] value, int[] index) {
		assert value.length == index.length;
		this.value = value;
		this.index = index;
	}
	public static Permutation createPermutationByValue(int[] val) {
		return new Permutation(val, createIndexArray(val));
	}
	public static Permutation createPermutationByIndex(int[] idx) {
		return new Permutation(createValueArray(idx), idx);
	}
	private static int[] createIndexArray(int[] val) {
		int[] idx = new int[val.length];
		for (int i = 0; i < val.length; i++) idx[val[i]] = i;
		return idx;
	}
	private static int[] createValueArray(int[] idx) {
		return createIndexArray(idx);
	}
	public int idxOf(int v) {
		return index[v];
	}
	public int valAt(int i) {
		return value[i];
	}
	public void swapVal(int u, int v) {
		swapIdx(index[u], index[v]);
	}
	public void swapIdx(int i, int j) {
		if (i == j) return;
		int u = value[i];
		int v = value[j];
		value[i] = v;
		value[j] = u;
		index[u] = j;
		index[v] = i;
	}
	public int[] values() {
		return value;
	}
	public int[] indexes() {
		return index;
	}
	public boolean nextPermutation() {
		int len = value.length;
		int l = len - 2;
		while (l >= 0 && value[l] >= value[l + 1]) l--;
		if (l < 0) return false;
		int r = len - 1;
		while (value[l] >= value[r]) r--;
		swapIdx(l, r);
		l++; r = len - 1;
		while (l < r) {
			swapIdx(l, r);
			l++; r--;
		}
		return true;
	}
	public static boolean nextPermutation(int[] array) {
		int len = array.length;
		int l = len - 2;
		while (l >= 0 && array[l] >= array[l + 1]) l--;
		if (l < 0) return false;
		int r = len - 1;
		while (array[l] >= array[r]) r--;
		int tmp = array[l]; array[l] = array[r]; array[r] = tmp;
		l++; r = len - 1;
		while (l < r) {
			tmp = array[l]; array[l] = array[r]; array[r] = tmp;
			l++; r--;
		}
		return true;
	}
	public static boolean nextPermutation(long[] array) {
		int len = array.length;
		int l = len - 2;
		while (l >= 0 && array[l] >= array[l + 1]) l--;
		if (l < 0) return false;
		int r = len - 1;
		while (array[l] >= array[r]) r--;
		long tmp = array[l]; array[l] = array[r]; array[r] = tmp;
		l++; r = len - 1;
		while (l < r) {
			tmp = array[l]; array[l] = array[r]; array[r] = tmp;
			l++; r--;
		}
		return true;
	}
}
