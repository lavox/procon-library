package data_structure;

public class IntFastClearingArray {
	private int[] val = null;
	private int[] gen = null;
	private int curGen = 1;
	private int defaultVal = 0;
	public IntFastClearingArray(int N, int defaultVal) {
		this.val = new int[N];
		this.gen = new int[N];
		this.defaultVal = defaultVal;
	}
	public void clear() {
		curGen++;
	}
	public int get(int i) {
		return gen[i] == curGen ? val[i] : defaultVal;
	}
	public void set(int i, int v) {
		gen[i] = curGen;
		val[i] = v;
	}
}
