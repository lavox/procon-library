import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.PrimitiveIterator;
import java.util.Random;

import primitive.IntArrayList;

public class IntArrayListBenchmark {
	static int N = 30_000_000;
	static int[] orig = null;
	static int blackhole = 0;
	public static void main(String[] args) {
		orig = new Random(41).ints(N, 0, 100).toArray();
		runAll("=== warmup ===");
		System.gc();

		runAll("=== benchmark ===");
	}

	static void runAll(String name) {
		System.out.println(name);
		case1();
		case2();
		case3();
		case4();
		case5();
		case6();
		case7();
	}

	static void measure(String name, Runnable prep, Runnable main) {
		long prepTime = 0;
		long mainTime = 0;
		int ITER = 5;
		for (int t = 0; t < ITER; t++) {
			long t1 = System.nanoTime();
			prep.run();
			long t2 = System.nanoTime();
			main.run();
			long t3 = System.nanoTime();
			prepTime += (t2 - t1);
			mainTime += (t3 - t2);
		}
		prepTime = Math.round(prepTime / (1e6 * ITER));
		mainTime = Math.round(mainTime / (1e6 * ITER));
		System.out.printf("%-32s prepare: %5d ms, main: %5d ms%n", name, prepTime, mainTime);
	}
	static void case1() {
		int[] list = new int[N];
		measure("int[],for-each",
			() -> { Arrays.fill(list, 0); System.arraycopy(orig, 0, list, 0, N); },
			() -> {
				int sum = 0;
				for (int x : list) {
					if (x % 2 == 0) sum += x;
				}
				blackhole += sum;
			}
		);
	}
	static void case2() {
		ArrayList<Integer> list = new ArrayList<>(N);
		measure("ArrayList<Integer>,for-each",
			() -> { list.clear(); for (int x : orig) list.add(x); },
			() -> {
				int sum = 0;
				for (int x : list) {
					if (x % 2 == 0) sum += x;
				}
				blackhole += sum;
			}
		);
	}
	static void case3() {
		IntArrayList list = new IntArrayList(N);
		measure("IntArrayList,for-each",
			() -> { list.clear(); for (int x : orig) list.add(x); },
			() -> {
				int sum = 0;
				for (int x : list) {
					if (x % 2 == 0) sum += x;
				}
				blackhole += sum;
			}
		);
	}
	static void case4() {
		IntArrayList list = new IntArrayList(N);
		measure("IntArrayList,for",
			() -> { list.clear(); for (int x : orig) list.add(x); },
			() -> {
				int sum = 0;
				for (int i = 0; i < list.size(); i++) {
					int x = list.get(i);
					if (x % 2 == 0) sum += x;
				}
				blackhole += sum;
			}
		);
	}
	static void case5() {
		IntArrayList list = new IntArrayList(N);
		measure("IntArrayList,PrimitiveIterator",
			() -> { list.clear(); for (int x : orig) list.add(x); },
			() -> {
				long sum = 0;
				for (PrimitiveIterator.OfInt it = list.iterator(); it.hasNext();) {
					long x = it.nextInt();
					if (x % 2 == 0) sum += x;
				}
				blackhole += sum;
			}
		);
	}
	static void case6() {
		IntArrayList list = new IntArrayList(N);
		measure("IntArrayList,list.foreach",
			() -> { list.clear(); for (int x : orig) list.add(x); },
			() -> {
				int[] sum = new int[1];
				list.forEach((int x) -> {
					if (x % 2 == 0) sum[0] += x;
				});
				blackhole += sum[0];
			}
		);
	}
	static void case7() {
		IntArrayList list = new IntArrayList(N);
		measure("IntArrayList,for-values",
			() -> { list.clear(); for (int x : orig) list.add(x); },
			() -> {
				int sum = 0;
				for (int x: list.toArray()) {
					if (x % 2 == 0) sum += x;
				}
				blackhole += sum;
			}
		);
	}
}

class IntArrayListSortBenchmark {
	static int N = 5_000_000;
	static int[] orig = null;
	static int blackhole = 0;
	public static void main(String[] args) {
		orig = new Random(41).ints(N, 0, 100).toArray();
		runAll("=== warmup ===");
		System.gc();

		runAll("=== benchmark ===");
	}

	static void runAll(String name) {
		System.out.println(name);
		case1();
		case2();
		case3();
	}

	static void measure(String name, Runnable prep, Runnable main) {
		long prepTime = 0;
		long mainTime = 0;
		int ITER = 5;
		for (int t = 0; t < ITER; t++) {
			long t1 = System.nanoTime();
			prep.run();
			long t2 = System.nanoTime();
			main.run();
			long t3 = System.nanoTime();
			prepTime += (t2 - t1);
			mainTime += (t3 - t2);
		}
		prepTime = Math.round(prepTime / (1e6 * ITER));
		mainTime = Math.round(mainTime / (1e6 * ITER));
		System.out.printf("%-32s prepare: %5d ms, main: %5d ms%n", name, prepTime, mainTime);
	}
	static void case1() {
		int[] list = new int[N];
		measure("int[],Arrays.sort",
			() -> { Arrays.fill(list, 0); System.arraycopy(orig, 0, list, 0, N); },
			() -> {
				Arrays.sort(list);
			}
		);
	}
	static void case2() {
		ArrayList<Integer> list = new ArrayList<>(N);
		measure("ArrayList<Integer>,Collections.sort",
			() -> { list.clear(); for (int x : orig) list.add(x); },
			() -> {
				Collections.sort(list, Comparator.naturalOrder());
			}
		);
	}
	static void case3() {
		IntArrayList list = new IntArrayList(N);
		measure("IntArrayList,sort",
			() -> { list.clear(); for (int x : orig) list.add(x); },
			() -> {
				list.sort();
			}
		);
	}
}
