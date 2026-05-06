package primitive;

// import java.util.*;

// public class SortBenchmark {

//     public static void main(String[] args) {
//         int n = 1_000_000; // 要素数
//         int trials = 5;    // 計測回数
//         Random rnd = new Random(0);

//         // 元データ作成
//         int[] original = new int[n];
//         for (int i = 0; i < n; i++) {
//             original[i] = rnd.nextInt();
//         }

//         // ウォームアップ
//         warmUp(original);

//         // 計測: 自前クイックソート
//         long quickSortTime = benchmark(trials, () -> {
//             int[] arr = original.clone();
//             quickSort(arr, 0, arr.length - 1);
//         });
//         System.out.printf("自前クイックソート (int[]) 平均: %.2f ms%n", quickSortTime / 1e6);

//         // 計測: Arrays.sort(Integer[], Comparator)
//         long arraysSortTime = benchmark(trials, () -> {
//             int[] arr = original.clone();
//             // int[] sorted = Arrays.stream(arr).boxed().sorted(Comparator.naturalOrder()).mapToInt(Integer::intValue).toArray();
//             int[] sorted = Arrays.stream(arr).sorted().toArray();
//             // Integer[] arr = new Integer[original.length];
//             // for (int i = 0; i < arr.length; i++) arr[i] = original[i];
//             // Arrays.sort(arr, Comparator.naturalOrder());
//         });
//         System.out.printf("Arrays.sort (Integer[], Comparator) 平均: %.2f ms%n", arraysSortTime / 1e6);
//     }

//     private static void warmUp(int[] data) {
//         for (int i = 0; i < 3; i++) {
//             int[] arr = data.clone();
//             quickSort(arr, 0, arr.length - 1);
//         }
//     }

//     private static long benchmark(int trials, Runnable task) {
//         long total = 0;
//         for (int i = 0; i < trials; i++) {
//             long start = System.nanoTime();
//             task.run();
//             long end = System.nanoTime();
//             total += (end - start);
//         }
//         return total / trials;
//     }

//     // 自前クイックソート
//     private static void quickSort(int[] arr, int left, int right) {
//         if (left >= right) return;
//         int pivot = arr[(left + right) >>> 1];
//         int i = left, j = right;
//         while (i <= j) {
//             while (arr[i] < pivot) i++;
//             while (arr[j] > pivot) j--;
//             if (i <= j) {
//                 int tmp = arr[i];
//                 arr[i] = arr[j];
//                 arr[j] = tmp;
//                 i++;
//                 j--;
//             }
//         }
//         if (left < j) quickSort(arr, left, j);
//         if (i < right) quickSort(arr, i, right);
//     }
// }

import java.util.*;

public class SortBenchmark {

    public static void main(String[] args) {
        int n = 1_000_000; // 要素数
        int trials = 5;    // 計測回数
        Random rnd = new Random(0);

        // 元データ作成
        int[] original = new int[n];
        for (int i = 0; i < n; i++) {
            original[i] = rnd.nextInt();
        }

        // ウォームアップ
        warmUp(original);

        // 計測: 自前クイックソート
        long quickSortTime = benchmark(trials, () -> {
            int[] arr = original.clone();
            quickSort(arr, 0, arr.length - 1);
        });
        System.out.printf("自前クイックソート (int[]) 平均: %.2f ms%n", quickSortTime / 1e6);

        // 計測: Arrays.sort(Integer[], Comparator)
        long arraysSortTime = benchmark(trials, () -> {
            Integer[] arr = new Integer[original.length];
            for (int i = 0; i < arr.length; i++) arr[i] = original[i];
            Arrays.sort(arr, Comparator.naturalOrder());
        });
        System.out.printf("Arrays.sort (Integer[], Comparator) 平均: %.2f ms%n", arraysSortTime / 1e6);
    }

    private static void warmUp(int[] data) {
        for (int i = 0; i < 3; i++) {
            int[] arr = data.clone();
            quickSort(arr, 0, arr.length - 1);
        }
        for (int i = 0; i < 3; i++) {
            Integer[] arr = new Integer[data.length];
            for (int j = 0; j < arr.length; j++) arr[j] = data[j];
            Arrays.sort(arr, Comparator.naturalOrder());
        }
    }

    private static long benchmark(int trials, Runnable task) {
        long total = 0;
        for (int i = 0; i < trials; i++) {
            long start = System.nanoTime();
            task.run();
            long end = System.nanoTime();
            total += (end - start);
        }
        return total / trials;
    }

    // 自前クイックソート
    private static void quickSort(int[] arr, int left, int right) {
        if (left >= right) return;
        int pivot = arr[(left + right) >>> 1];
        int i = left, j = right;
        while (i <= j) {
            while (arr[i] < pivot) i++;
            while (arr[j] > pivot) j--;
            if (i <= j) {
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                i++;
                j--;
            }
        }
        if (left < j) quickSort(arr, left, j);
        if (i < right) quickSort(arr, i, right);
    }
}
