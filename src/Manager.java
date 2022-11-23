
/*
 * @Author: scikkk 203536673@qq.com
 * @Date: 2018-12-29 13:11:40
 * @LastEditors: scikkk
 * @LastEditTime: 2022-11-23 15:31:56
 * @Description: Main
 */
import sort.EnumSort;
import sort.QuickSort;
import sort.MergeSort;

import java.io.*;

public class Manager {
	private static int[] nums = new int[10000000];
	private static int nums_len;
	private static int rerun_times = 10;

	private static boolean test() {
		for (int i = 0; i < nums_len - 1; i++) {
			if (nums[i] > nums[i + 1]) {
				System.out.println("错误:" + i);
				return false;
			}
		}
		return true;
	}

	private static void load() throws IOException {
		String filename = ".\\random.txt";
		BufferedReader readTxt = null;
		readTxt = new BufferedReader(new FileReader(new File(filename)));
		String sline = "";
		String sdata = "";
		while ((sline = readTxt.readLine()) != null) {
			sdata += sline;
		}
		String[] snums = sdata.split(" ");
		nums_len = snums.length;
		for (int i = 0; i < nums_len; i++) {
			nums[i] = Integer.parseInt(snums[i]);
		}
	}

	private static void save(int label) throws IOException {
		FileWriter fw = new FileWriter(String.format(".\\results\\order%d.txt", label));
		for (int i = 0; i < nums_len; i++) {
			fw.write(nums[i] + "\n");
		}
		fw.close();
	}

	private static void quick_sort() throws InterruptedException, IOException {
		// 串行快排
		load();
		long qsort_start = System.currentTimeMillis();
		QuickSort.qsort(nums, 0, nums_len - 1);
		long qsort_end = System.currentTimeMillis();
		save(1);
		System.out.println("测试结果:" + test() + ";\t" + "串行快排:" + (qsort_end -
				qsort_start) + "ms");
		// 并行快排
		load();
		long p2qsort_start = System.currentTimeMillis();
		QuickSort.p4qsort(nums, 0, nums_len - 1);
		long p2qsort_end = System.currentTimeMillis();
		save(2);
		System.out.println("测试结果:" + test() + ";\t" + "2线程并行快排:" + (p2qsort_end -
				p2qsort_start) + "ms");
		// 并行快排
		load();
		long p4qsort_start = System.currentTimeMillis();
		QuickSort.p4qsort(nums, 0, nums_len - 1);
		long p4qsort_end = System.currentTimeMillis();
		save(2);
		System.out.println("测试结果:" + test() + ";\t" + "4线程并行快排:" + (p4qsort_end -
				p4qsort_start) + "ms");
		// 并行快排
		load();
		long p8qsort_start = System.currentTimeMillis();
		QuickSort.p8qsort(nums, 0, nums_len - 1);
		long p8qsort_end = System.currentTimeMillis();
		save(2);
		System.out.println("测试结果:" + test() + ";\t" + "8线程并行快排:" + (p8qsort_end -
				p8qsort_start) + "ms");
	}

	private static void enum_sort() throws IOException, InterruptedException {
		// 串行枚举
		load();
		long esort_start = System.currentTimeMillis();
		EnumSort.esort(nums, 0, nums_len - 1);
		long esort_end = System.currentTimeMillis();
		save(3);
		System.out.println("测试结果:" + test() + ";\t" + "串行枚举:" + (esort_end -
				esort_start) + "ms");
		// 并行枚举
		load();
		long p2esort_start = System.currentTimeMillis();
		EnumSort.pesort(nums, 0, nums_len - 1, 2);
		long p2esort_end = System.currentTimeMillis();
		save(4);
		System.out.println("测试结果:" + test() + ";\t" + "2线程并行枚举:" + (p2esort_end -
				p2esort_start) + "ms");
		// 并行枚举
		load();
		long p4esort_start = System.currentTimeMillis();
		EnumSort.pesort(nums, 0, nums_len - 1, 4);
		long p4esort_end = System.currentTimeMillis();
		save(4);
		System.out.println("测试结果:" + test() + ";\t" + "4线程并行枚举:" + (p4esort_end -
				p4esort_start) + "ms");
		// 并行枚举
		load();
		long p8esort_start = System.currentTimeMillis();
		EnumSort.pesort(nums, 0, nums_len - 1, 8);
		long p8esort_end = System.currentTimeMillis();
		save(4);
		System.out.println("测试结果:" + test() + ";\t" + "8线程并行枚举:" + (p8esort_end -
				p8esort_start) + "ms");
		// 并行枚举
		load();
		long p16esort_start = System.currentTimeMillis();
		EnumSort.pesort(nums, 0, nums_len - 1, 16);
		long p16esort_end = System.currentTimeMillis();
		save(4);
		System.out.println("测试结果:" + test() + ";\t" + "16线程并行枚举:" + (p16esort_end - p16esort_start) + "ms");
		// 并行枚举
		load();
		long p32esort_start = System.currentTimeMillis();
		EnumSort.pesort(nums, 0, nums_len - 1, 32);
		long p32esort_end = System.currentTimeMillis();
		save(4);
		System.out.println("测试结果:" + test() + ";\t" + "32线程并行枚举:" + (p32esort_end - p32esort_start) + "ms");
	}

	private static void merge_sort() throws IOException, InterruptedException {
		// 串行归并
		long time = 0;
		for (int k = 0; k < rerun_times; k++) {
			load();
			long msort_start = System.currentTimeMillis();
			MergeSort.msort(nums, 0, nums_len - 1);
			long msort_end = System.currentTimeMillis();
			time += msort_end - msort_start;
		}
		save(5);
		System.out.println("测试结果:" + test() + ";\t" + "串行归并:" + (time / rerun_times) + "ms");
		// 并行归并
		time = 0;
		for (int k = 0; k < rerun_times; k++) {
			load();
			long p3msort_start = System.currentTimeMillis();
			MergeSort.p3msort(nums, 0, nums_len - 1);
			long p3msort_end = System.currentTimeMillis();
			time += p3msort_end - p3msort_start;
		}
		save(6);
		System.out.println("测试结果:" + test() + ";\t" + "3线程并行归并:" + (time / rerun_times) + "ms");
		// 并行归并
		time = 0;
		for (int k = 0; k < rerun_times; k++) {
			load();
			long p4msort_start = System.currentTimeMillis();
			MergeSort.p4msort(nums, 0, nums_len - 1);
			long p4msort_end = System.currentTimeMillis();
			time += p4msort_end - p4msort_start;
		}
		save(6);
		System.out.println("测试结果:" + test() + ";\t" + "4线程并行归并:" + (time / rerun_times) + "ms");
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		quick_sort();
		System.out.println("=================================");
		enum_sort();
		System.out.println("=================================");
		merge_sort();
		System.out.println(nums_len);
	}
}
