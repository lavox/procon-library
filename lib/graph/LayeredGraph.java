package graph;

public abstract class LayeredGraph implements Graph {
	protected final Graph base;
	protected final int bSize;
	protected final int lSize;

	public LayeredGraph(Graph baseGraph, int layerSize) {
		this.base = baseGraph;
		this.bSize = baseGraph.size();
		this.lSize = layerSize;
	}
	@Override
	public int size() {
		return bSize * lSize;
	}
	public int baseSize() {
		return bSize;
	}
	public int layerSize() {
		return lSize;
	}
	public int bv(int v) {
		return v / lSize;
	}
	public int layer(int v) {
		return v % lSize;
	}
	public int v(int bv, int layer) {
		return bv * lSize + layer;
	}
	@Override
	public void forEachEdge(int v, EdgeConsumer action) {
		int bv0 = bv(v);
		int layer0 = layer(v);
		base.forEachEdge(bv0, (bfrom, bto, eid, bcost) -> {
			forEachBaseEdge(bv0, layer0, bto, eid, bcost, action);
		});
		forEachLayerEdge(bv0, layer0, action);
	}
	protected void acceptAction(int bv0, int layer0, int bv1, int layer1, int id, long cost, EdgeConsumer action) {
		action.accept(v(bv0, layer0), v(bv1, layer1), id, cost);
	}
	protected void forEachBaseEdge(int bv0, int layer0, int bv1, int eid, long bcost, EdgeConsumer action) {}
	protected void forEachLayerEdge(int bv0, int layer0, EdgeConsumer action) {}
}
