package graph;

public class TwoSat {
	private boolean[] _answer = null;
	private int _n = 0;
	private SccGraph scc = null;
	public TwoSat(int n) {
		this._n = n;
		this.scc = new SccGraph(2 * n);
		this._answer = new boolean[_n];
	}
	public void addClause(int i, boolean f, int j, boolean g) {
		assert 0 <= i && i < _n;
		assert 0 <= j && j < _n;
		scc.addEdge(2 * i + (f ? 0 : 1), 2 * j + (g ? 1 : 0));
		scc.addEdge(2 * j + (g ? 0 : 1), 2 * i + (f ? 1 : 0));
	}
	public boolean isSatisfiable() {
		scc.decompose();
		int[] id = scc.ids();
		for (int i = 0; i < _n; i++) {
			if (id[2 * i] == id[2 * i + 1]) return false;
			_answer[i] = id[2 * i] < id[2 * i + 1];
		}
		return true;
	}
	public boolean[] answer() {
		return _answer;
	}
}
