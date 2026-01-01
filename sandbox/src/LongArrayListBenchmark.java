import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.PrimitiveIterator;
import java.util.Random;

import primitive.LongArrayList;

public class LongArrayListBenchmark {
	static int N = 30_000_000;
	static long[] orig = null;
	static long blackhole = 0;
	public static void main(String[] args) {
		orig = new Random(41).longs(N, 0, 100).toArray();
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
		long[] list = new long[N];
		measure("long[],for-each",
			() -> { Arrays.fill(list, 0); System.arraycopy(orig, 0, list, 0, N); },
			() -> {
				long sum = 0;
				for (long x : list) {
					if (x % 2 == 0) sum += x;
				}
				blackhole += sum;
			}
		);
	}
	static void case2() {
		ArrayList<Long> list = new ArrayList<>(N);
		measure("ArrayList<Long>,for-each",
			() -> { list.clear(); for (long x : orig) list.add(x); },
			() -> {
				long sum = 0;
				for (long x : list) {
					if (x % 2 == 0) sum += x;
				}
				blackhole += sum;
			}
		);
	}
	static void case3() {
		LongArrayList list = new LongArrayList(N);
		measure("LongArrayList,for-each",
			() -> { list.clear(); for (long x : orig) list.add(x); },
			() -> {
				long sum = 0;
				for (long x : list) {
					if (x % 2 == 0) sum += x;
				}
				blackhole += sum;
			}
		);
	}
	static void case4() {
		LongArrayList list = new LongArrayList(N);
		measure("LongArrayList,for",
			() -> { list.clear(); for (long x : orig) list.add(x); },
			() -> {
				long sum = 0;
				for (int i = 0; i < list.size(); i++) {
					long x = list.get(i);
					if (x % 2 == 0) sum += x;
				}
				blackhole += sum;
			}
		);
	}
	static void case5() {
		LongArrayList list = new LongArrayList(N);
		measure("LongArrayList,PrimitiveIterator",
			() -> { list.clear(); for (long x : orig) list.add(x); },
			() -> {
				long sum = 0;
				for (PrimitiveIterator.OfLong it = list.iterator(); it.hasNext();) {
					long x = it.nextLong();
					if (x % 2 == 0) sum += x;
				}
				blackhole += sum;
			}
		);
	}
	static void case6() {
		LongArrayList list = new LongArrayList(N);
		measure("LongArrayList,list.foreach",
			() -> { list.clear(); for (long x : orig) list.add(x); },
			() -> {
				long[] sum = new long[1];
				list.forEach((long x) -> {
					if (x % 2 == 0) sum[0] += x;
				});
				blackhole += sum[0];
			}
		);
	}
	static void case7() {
		LongArrayList list = new LongArrayList(N);
		measure("LongArrayList,for-values",
			() -> { list.clear(); for (long x : orig) list.add(x); },
			() -> {
				long sum = 0;
				for (long x: list.toArray()) {
					if (x % 2 == 0) sum += x;
				}
				blackhole += sum;
			}
		);
	}
}

class LongArrayListSortBenchmark {
	static int N = 5_000_000;
	static long[] orig = null;
	static long blackhole = 0;
	public static void main(String[] args) {
		orig = new Random(41).longs(N, 0, 100).toArray();
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
		long[] list = new long[N];
		measure("long[],Arrays.sort",
			() -> { Arrays.fill(list, 0); System.arraycopy(orig, 0, list, 0, N); },
			() -> {
				Arrays.sort(list);
			}
		);
	}
	static void case2() {
		ArrayList<Long> list = new ArrayList<>(N);
		measure("ArrayList<Long>,Collections.sort",
			() -> { list.clear(); for (long x : orig) list.add(x); },
			() -> {
				Collections.sort(list, Comparator.naturalOrder());
			}
		);
	}
	static void case3() {
		LongArrayList list = new LongArrayList(N);
		measure("LongArrayList,sort",
			() -> { list.clear(); for (long x : orig) list.add(x); },
			() -> {
				list.sort();
			}
		);
	}

}
