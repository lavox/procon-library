package data_structure;

public class MoQuery implements Comparable<MoQuery> {
	public final int id;
	public final int l;
	public final int r;
	public final int l_bid;
	public MoQuery(int id, int l, int r, SqrtDecomposition sqd) {
		this.id = id;
		this.l = l;
		this.r = r;
		this.l_bid = sqd.bid(l);
	}
	@Override
	public int compareTo(MoQuery o) {
		if (l_bid != o.l_bid) {
			return Integer.compare(l_bid, o.l_bid);
		} else if (r != o.r) {
			if (l_bid % 2 == 0) {
				return Integer.compare(r, o.r);
			} else {
				return -Integer.compare(r, o.r);
			}
		} else {
			return Integer.compare(id, o.id);
		}
	}
}
