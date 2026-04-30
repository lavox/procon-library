package geometry;

public enum Direction {
	R(0, 1, 0, 0, "R"),
	D(1, 0, 1, 2, "D"),
	L(0, -1, 2, 4, "L"),
	U(-1, 0, 3, 6, "U"),
	RD(1, 1, -1, 1, "RD"),
	LD(1, -1, -1, 3, "LD"),
	LU(-1, -1, -1, 5, "LU"),
	RU(-1, 1, -1, 7, "RU")
	;
	public static final Direction[] DIR = {R, D, L, U};
	public static final Direction[] DIR8 = {R, RD, D, LD, L, LU, U, RU};
	public final int di;
	public final int dj;
	public final int id;
	public final int id8;
	public final String name;

	private Direction(int di, int dj, int id, int id8, String name) {
		this.di = di;
		this.dj = dj;
		this.id = id;
		this.id8 = id8;
		this.name = name;
	}
	public Direction rev() {
		return DIR8[(this.id8 + 4) % DIR8.length];
	}
	public Direction rot() {
		return DIR8[(this.id8 + 2) % DIR8.length];
	}
	public Direction rot(int i) {
		int ri = (this.id8 + i * 2) % DIR8.length;
		if (ri < 0) ri += DIR8.length;
		return DIR8[ri];
	}
	public Direction rot45() {
		return DIR8[(this.id8 + 1) % DIR8.length];
	}
	public Direction rot45(int i) {
		int ri = (this.id8 + i) % DIR8.length;
		if (ri < 0) ri += DIR8.length;
		return DIR8[ri];
	}
	public Direction irot() {
		return DIR8[(this.id8 + 6) % DIR8.length];
	}
	public Direction irot(int i) {
		int ri = (this.id8 - i * 2) % DIR8.length;
		if (ri < 0) ri += DIR8.length;
		return DIR8[ri];
	}
	public Direction irot45() {
		return DIR8[(this.id8 + 7) % DIR8.length];
	}
	public Direction irot45(int i) {
		int ri = (this.id8 - i) % DIR8.length;
		if (ri < 0) ri += DIR8.length;
		return DIR8[ri];
	}
	public boolean isHorizontal() {
		return di == 0;
	}
	public boolean isVertical() {
		return dj == 0;
	}
	@Override
	public String toString() {
		return name;
	}
}
