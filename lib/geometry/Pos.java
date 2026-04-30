package geometry;

import java.util.ArrayList;
import java.util.function.BiPredicate;

public class Pos {
	private final Grid grid;
	public final int i;
	public final int j;
	public final int id;

	public ArrayList<Pos> adj = null;
	public Pos[] adj4 = null;
	public Pos[] adj8 = null;

	public Pos(int i, int j, int id, Grid grid) {
		this.i = i;
		this.j = j;
		this.id = id;
		this.grid = grid;
	}
	public void initAdj(BiPredicate<Pos, Pos> existsWall) {
		adj = new ArrayList<>();
		adj4 = new Pos[Direction.DIR.length];
		for (Direction d: Direction.DIR) {
			int ai = i + d.di, aj = j + d.dj;
			if (!grid.isValid(ai, aj)) continue;
			Pos a = grid.pos(ai, aj);
			if (a == null || existsWall.test(this, a)) continue;
			adj4[d.id] = a;
			adj.add(a);
		}
	}
	public void initAdj8(BiPredicate<Pos, Pos> existsWall) {
		adj = new ArrayList<>();
		adj8 = new Pos[Direction.DIR8.length];
		for (Direction d: Direction.DIR8) {
			int ai = i + d.di, aj = j + d.dj;
			if (!grid.isValid(ai, aj)) continue;
			Pos a = grid.pos(ai, aj);
			if (a == null || existsWall.test(this, a)) continue;
			adj8[d.id8] = a;
			adj.add(a);
		}
	}
	public Pos next(Direction d) {
		return adj4[d.id];
	}
	public Pos next(Direction d, int cnt) {
		int ni = i + d.di * cnt;
		int nj = j + d.dj * cnt;
		return grid.isValid(ni, nj) ? grid.pos(ni, nj) : null;
	}
	public Pos next8(Direction d) {
		return adj8[d.id8];
	}
	public Pos next8(Direction d, int cnt) {
		return next(d, cnt);
	}

	@Override
	public int hashCode() {
		return id;
	}
	@Override
	public boolean equals(Object o) {
		if (o instanceof Pos) {
			return id == ((Pos)o).id;
		} else {
			return false;
		}
	}
	@Override
	public String toString() {
		return String.format("(%d,%d)", i, j);
	}
}
