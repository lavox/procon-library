package data_structure;

public class SqrtDecomposition {
	public final int N;
	public final int B;
	public final int BC;
	public SqrtDecomposition(int N) {
		this(N, (int)Math.round(Math.sqrt(N)));
	}
	public SqrtDecomposition(int N, int B) {
		this.N = N;
		this.B = Math.max(B, 1);
		this.BC = (N + this.B - 1) / this.B;
	}
	public int bid(int idx) {
		return idx / B;
	}
	public int lid(int idx) {
		return idx % B;
	}
	public int idx(int bid, int lid) {
		return bid * B + lid;
	}
	public int bStart(int bid) {
		return bid * B;
	}
	public int bEnd(int bid) {
		return Math.min((bid + 1) * B, N);
	}
	public int bLen(int bid) {
		return bEnd(bid) - bStart(bid);
	}
	public int bCnt() {
		return BC;
	}
}
