package data_structure;

import java.util.function.IntFunction;
import java.util.function.Supplier;

public class WeightedUnionFind {
	private int[] parent = null;
	private int[] size = null;
	private Data[] data = null;
	private Weight[] weight = null;
	
	public WeightedUnionFind(int N, Supplier<Weight> e) {
		this(N, e, null);
	}
	public WeightedUnionFind(int N, Supplier<Weight> e, IntFunction<Data> constructor) {
		parent = new int[N];
		size = new int[N];
		for (int i = 0; i < N; i++) {
			parent[i] = i;
			size[i] = 1;
		}
		weight = new Weight[N];
		for (int i = 0; i < N; i++) {
			weight[i] = e.get();
		}
		if (constructor != null) {
			data = new Data[N];
			for (int i = 0; i < N; i++) {
				data[i] = constructor.apply(i);
			}
		}
	}
	public int root(int i) {
		if (parent[i] == i) {
			return i;
		} else if (parent[parent[i]] == parent[i]) {
			return parent[i];
		} else {
			int r = root(parent[i]);
			weight[i] = weight[parent[i]].op(weight[i]);
			parent[i] = r;
			return r;
		}
	}
	public int size(int i) {
		return size[root(i)];
	}
	public Data data(int i) {
		return data[root(i)];
	}
	public Weight weight(int i) {
		root(i);
		return weight[i];
	}
	// diff = - weight(i) + weight(j)
	public Weight diff(int i, int j) {
		if (!isSame(i, j)) return null;
		return weight[i].inv().op(weight[j]);
	}
	// weight(i) + d = weight(j)
	public boolean unite(int i, int j, Weight d) {
		int ri = root(i);
		int rj = root(j);
		if (ri == rj) {
			return weight[i].op(d).equals(weight[j]);
		} else {
			if (size[ri] < size[rj]) {
				parent[ri] = rj;
				size[rj] += size[ri];
				if (data != null) {
					data[rj].merge(data[ri]);
				}
				weight[ri] = weight[j].op(weight[i].op(d).inv());
			} else {
				parent[rj] = ri;
				size[ri] += size[rj];
				if (data != null) {
					data[ri].merge(data[rj]);
				}
				weight[rj] = weight[i].op(d).op(weight[j].inv());
			}
			return true;
		}
	}
	public boolean isSame(int i, int j) {
		return root(i) == root(j);
	}
	public interface Data {
		public void merge(Data o);
	}
	public interface Weight {
		public Weight inv();
		public Weight op(Weight o);
		public boolean equals(Weight o);
	}
}
