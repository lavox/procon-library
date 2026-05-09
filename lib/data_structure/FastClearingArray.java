package data_structure;

public class FastClearingArray<T> {
	private Object[] val = null;
	private int[] gen = null;
	private int curGen = 1;
	private T defaultVal = null;
	public FastClearingArray(int N, T defaultVal) {
		this.val = new Object[N];
		this.gen = new int[N];
		this.defaultVal = defaultVal;
	}
	public void clear() {
		curGen++;
	}
	@SuppressWarnings("unchecked")
	public T get(int i) {
		return gen[i] == curGen ? (T)val[i] : defaultVal;
	}
	public void set(int i, T v) {
		gen[i] = curGen;
		val[i] = v;
	}
}
