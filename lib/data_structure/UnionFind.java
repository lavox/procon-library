package data_structure;

public class UnionFind {
	int[] parent = null;
	int[] size = null;
	
	public UnionFind(int N) {
		parent = new int[N];
		size = new int[N];
		for ( int i = 0 ; i < N ; i++ ) {
			parent[i] = i;
			size[i] = 1;
		}
	}

	public int root(int i) {
		return parent[i] == i ? i : (parent[i] = root(parent[i]));
	}
	public int size(int i) {
		return size[root(i)];
	}

	public void unite(int i, int j) {
		int ri = root(i);
		int rj = root(j);
		if ( ri == rj ) {
			return;
		} else {
			if ( size[ri] < size[rj] ) {
				parent[ri] = rj;
				size[rj] += size[ri];
			} else {
				parent[rj] = ri;
				size[ri] += size[rj];
			}
		}
	}
	
	public boolean isSame(int i, int j) {
		return root(i) == root(j);
	}
}
