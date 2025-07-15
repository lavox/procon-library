class NextPermutation {
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
