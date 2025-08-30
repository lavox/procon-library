package data_structure;

import java.util.function.IntFunction;

public class UnionFind {
	private int[] parent = null;
	private int[] size = null;
	private Data[] data = null;
	
	public UnionFind(int N) {
		this(N, null);
	}
	public UnionFind(int N, IntFunction<Data> constructor) {
		parent = new int[N];
		size = new int[N];
		for (int i = 0; i < N; i++) {
			parent[i] = i;
			size[i] = 1;
		}
		if (constructor != null) {
			data = new Data[N];
			for (int i = 0; i < N; i++) {
				data[i] = constructor.apply(i);
			}
		}
	}
	public int root(int i) {
		return parent[i] == i ? i : (parent[i] = root(parent[i]));
	}
	public int size(int i) {
		return size[root(i)];
	}
	public Data data(int i) {
		return data[root(i)];
	}
	public void unite(int i, int j) {
		int ri = root(i);
		int rj = root(j);
		if (ri == rj) {
			return;
		} else {
			if (size[ri] < size[rj]) {
				parent[ri] = rj;
				size[rj] += size[ri];
				if (data != null) {
					data[rj].merge(data[ri]);
				}
			} else {
				parent[rj] = ri;
				size[ri] += size[rj];
				if (data != null) {
					data[ri].merge(data[rj]);
				}
			}
		}
	}
	public boolean isSame(int i, int j) {
		return root(i) == root(j);
	}
	public interface Data {
		public void merge(Data o);
	}
}
