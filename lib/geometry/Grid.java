package geometry;

import java.util.BitSet;
import java.util.function.BiPredicate;

public class Grid {
	private final int H;
	private final int W;
	private final Pos[][] pos;
	public final Pos[] pos1;

	public Grid(int N) {
		this(N, N);
	}
	public Grid(int H, int W) {
		this.H = H;
		this.W = W;
		this.pos = new Pos[H][W];
		this.pos1 = new Pos[H * W];
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				pos[i][j] = new Pos(i, j, i * W + j, this);
				pos1[pos[i][j].id] = pos[i][j];
			}
		}
	}
	public void initAdj() {
		initAdj((p1, p2) -> false);
	}
	public void initAdj(BitSet[] horizontalWall, BitSet[] verticalWall) {
		initAdj((p1, p2) -> {
			if (p1.i == p2.i) {
				if (Math.abs(p1.j - p2.j) == 1) {
					return verticalWall[p1.i].get(Math.min(p1.j, p2.j));
				} else {
					return false;
				}
			} else if (p1.j == p2.j) {
				if (Math.abs(p1.i - p2.i) == 1) {
					return horizontalWall[Math.min(p1.i, p2.i)].get(p1.j);
				} else {
					return false;
				}
			} else {
				return false;
			}
		});
	}
	public void initAdj(BiPredicate<Pos, Pos> existsWall) {
		for (Pos p: pos1) {
			p.initAdj(existsWall);
		}
	}
	public void initAdj8() {
		initAdj8((p1, p2) -> false);
	}
	public void initAdj8(BiPredicate<Pos, Pos> existsWall) {
		for (Pos p: pos1) {
			p.initAdj8(existsWall);
		}
	}

	public Pos pos(int i, int j) {
		return pos[i][j];
	}
	public Pos pos(int id) {
		return pos1[id];
	}
	public boolean isValid(int i, int j) {
		return 0 <= i && i < H && 0 <= j && j < W;
	}
}
