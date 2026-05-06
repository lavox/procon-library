package primitive;

import java.util.HashMap;
import java.util.Random;

public class IntIntListBenchmark {
	static int N = 5000;
	static int N2 = 10_000_000;
	static int v1 = 10_000_000;
	static int K1 = 10_000_000;
	static int v2 = 20_000_000;
	static int defaultSize = 15;
	static int[] keys = null;
	static int[] keys2 = null;
	static int[] values = null;
	static int[] values2 = null;
	static int blackhole = 0;
	public static void main(String[] args) {
		Random rnd = new Random(41);
		keys = rnd.ints(N, v1, v1 + K1).toArray();
		values = rnd.ints(N, 0, 10).toArray();

		keys2 = new int[N2];
		// for (int i = 0; i < N2; i++) keys2[i] = v2 + i;
		// for (int i = 0; i < N2; i++) keys2[i] = v2 + (i << 10);
		keys2 = rnd.ints(N2, v2, v2 + N2).toArray();
		values2 = rnd.ints(N2, 0, N2).toArray();
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
		case8();
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
		IntIntMap map = new IntIntMap(defaultSize);
		measure("IntIntMap,get(hit)",
			() -> { 
				map.clear();
				for (int i = 0; i < N; i++) map.put(keys[i], values[i]); },
			() -> {
				blackhole = 0;
				int sum = 0;
				for (int i = 0; i < N2; i++) {
					sum += map.getOrDefault(keys[i % N], 0);
				}
				blackhole += sum;
				// System.out.println(blackhole);
			}
		);
	}
	static void case2() {
		HashMap<Integer, Integer> map = new HashMap<>(defaultSize);
		measure("HashMap,get(hit)",
			() -> { 
				map.clear();
				for (int i = 0; i < N; i++) map.put(keys[i], values[i]); },
			() -> {
				blackhole = 0;
				int sum = 0;
				for (int i = 0; i < N2; i++) {
					sum += map.getOrDefault(keys[i % N], 0);
				}
				blackhole += sum;
			}
		);
	}
	static void case3() {
		IntIntMap map = new IntIntMap(defaultSize);
		measure("IntIntMap,get(no-hit)",
			() -> { 
				map.clear();
				for (int i = 0; i < N; i++) map.put(keys[i], values[i]); },
			() -> {
				blackhole = 0;
				int sum = 0;
				for (int i = 0; i < N2; i++) {
					sum += map.getOrDefault(keys2[i], 0);
				}
				blackhole += sum;
			}
		);
	}
	static void case4() {
		HashMap<Integer, Integer> map = new HashMap<>(defaultSize);
		measure("HashMap,get(no-hit)",
			() -> { 
				map.clear();
				for (int i = 0; i < N; i++) map.put(keys[i], values[i]); },
			() -> {
				blackhole = 0;
				int sum = 0;
				for (int i = 0; i < N2; i++) {
					sum += map.getOrDefault(keys2[i], 0);
				}
				blackhole += sum;
			}
		);
	}
	static void case5() {
		IntIntMap map = new IntIntMap(defaultSize);
		measure("IntIntMap,put(insert)",
			() -> {
				map.clear();
			},
			() -> {
				int sum = 0;
				for (int i = 0; i < N2; i++) {
					sum += map.put(keys2[i], values2[i]);
				}
				blackhole += sum;
			}
		);
	}
	static void case6() {
		HashMap<Integer, Integer> map = new HashMap<>(defaultSize);
		measure("HashMap,put(insert)",
			() -> {
				map.clear();
			},
			() -> {
				int sum = 0;
				for (int i = 0; i < N2; i++) {
					Integer a = map.put(keys2[i], values2[i]);
					sum += a == null ? 0 : a;
				}
				blackhole += sum;
			}
		);
	}
	static void case7() {
		IntIntMap map = new IntIntMap(defaultSize);
		measure("IntIntMap,put(update)",
			() -> {
				map.clear();
				for (int i = 0; i < N2; i++) map.put(keys2[i], 1);
			},
			() -> {
				int sum = 0;
				for (int i = 0; i < N2; i++) {
					sum += map.put(keys2[i], values2[i]);
				}
				blackhole += sum;
			}
		);
	}
	static void case8() {
		HashMap<Integer, Integer> map = new HashMap<>(defaultSize);
		measure("HashMap,put(update)",
			() -> {
				map.clear();
				for (int i = 0; i < N2; i++) map.put(keys2[i], 1);
			},
			() -> {
				int sum = 0;
				for (int i = 0; i < N2; i++) {
					Integer a = map.put(keys2[i], values2[i]);
					sum += a == null ? 0 : a;
				}
				blackhole += sum;
			}
		);
	}
}
