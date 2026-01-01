import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import primitive.LongArrays;

public class LongArraysBenchmark {
	static int N = 3_000_000;
	public static void main(String[] args) {
		// int N0 = 3_000_000;
		int N0 = 3_000;
		double errorRate = 1.0;// / 10_000;
		int errorWidth = 1;
		long[][] orig = createArray(N, N0, errorRate, errorWidth);
		runAll("=== warmup ===", orig);
		System.gc();

		runAll("=== benchmark ===", orig);
	}

	static long[][] createArray(int N, int N0, double errorRate, int errorWidth) {
		Random rnd = new Random(41);
		int K = (N - 1) / N0 + 1;
		long[][] ret = new long[K][N0];
		for (int k = 0; k < K; k++) {
			for (int i = 0; i < N0; i++) {
				if (rnd.nextDouble() < errorRate) {
					int i1 = Math.min(i + errorWidth, N0);
					for (; i < i1; i++) ret[k][i] = rnd.nextInt(N0);
				} else {
					ret[k][i] = i;
				}
			}
		}
		return ret;
	}

	static void runAll(String name, long[][] orig) {
		System.out.println(name);
		case1(orig);
		case2(orig);
		case3(orig);
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
	static void case1(long[][] orig) {
		long[][] list = new long[orig.length][];

		measure("long[],Arrays.sort",
			() -> {
				for (int i = 0; i < orig.length; i++) list[i] = Arrays.copyOf(orig[i], orig[i].length);
			},
			() -> {
				for (long[] l: list) Arrays.sort(l);
			}
		);
	}
	static void case2(long[][] orig) {
		long[][] list = new long[orig.length][];
		measure("long[],LongArrays.sort",
			() -> {
				for (int i = 0; i < orig.length; i++) list[i] = Arrays.copyOf(orig[i], orig[i].length);
			},
			() -> {
				for (long[] l: list) LongArrays.sort(l, (a, b) -> Long.compare(a, b));
			}
		);
	}
	static void case3(long[][] orig) {
		ArrayList<Long>[] list = new ArrayList[orig.length];
		measure("ArrayList<Long>,Collections.sort",
			() -> { 
				for (int i = 0; i < orig.length; i++) {
					list[i] = new ArrayList<>(orig[i].length);
					for (long v: orig[i]) list[i].add(v);
				}
			},
			() -> {
				for (ArrayList<Long> l: list) Collections.sort(l, (a, b) -> Long.compare(a, b));
			}
		);
	}
}
