package graph;
import java.util.ArrayList;
import java.util.Arrays;

public class TopologicalSort {
	private int _n = 0;
	private ArrayList<Integer>[] edge = null;
  private Permutation nodes = null;
	
	public TopologicalSort(int n) {
		this._n = n;
		edge = new ArrayList[n];
		for ( int i = 0 ; i < n ; i++ ) {
			edge[i] = new ArrayList<>();
		}
	}
	public void addEdge(int from, int to) {
		edge[from].add(to);
	}
	public boolean sort() {
		int[] index = new int[_n];
		for ( ArrayList<Integer> edges : edge ) {
			for ( int to : edges ) {
				index[to]++;
			}
		}
		
    int[] queue = new int[_n];
    int qw = 0;
    int qr = 0;
		for ( int i = 0 ; i < _n ; i++ ) {
			if ( index[i] == 0 ) queue[qw++] = i;
		}
		
    nodes = new Permutation(_n);
		int i = 0;
		while (qr < qw) {
			int pos = queue[qr++];
      nodes.swapVal(pos, nodes.valAt(i++));
			for (int next: edge[pos]) {
				index[next]--;
				if (index[next] == 0) queue[qw++] = next;
			}
		}
		return i == _n;
	}
  public int nodeAt(int i) {
    return nodes.valAt(i);
  }
  public int idxOf(int v) {
    return nodes.idxOf(v);
  }
  public int[] nodeArray() {
    return nodes.values();
  }
  public int[] indexArray() {
    return nodes.indexes();
  }

  class Permutation {
    private int[] value = null;
    private int[] index = null;
    public Permutation(int n) {
      value = new int[n];
      for (int i = 0; i < n; i++) value[i] = i;
      index = Arrays.copyOf(value, n);
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
  }
}
