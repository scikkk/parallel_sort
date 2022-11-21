
/*
 * @Author: scikkk 203536673@qq.com
 * @Date: 2018-12-29 13:11:40
 * @LastEditors: scikkk
 * @LastEditTime: 2022-11-21 01:46:24
 * @Description: Main
 */
import sort.EnumSort;
import sort.QuickSort;
import sort.MergeSort;

import java.io.*;

public class Manager {
	private static int[] array = new int[100000];
	private static int dataLen;

	public static boolean test() {
		for (int i = 0; i < dataLen - 1; i++) {
			if (array[i] > array[i + 1]) {
				System.out.println("错误:" + i);
				return false;
			}
		}
		return true;
	}

	public static void load() throws IOException {
		String filename = ".\\src\\random.txt";
		BufferedReader readTxt = null;
		readTxt = new BufferedReader(new FileReader(new File(filename)));
		String txtLine = "";
		String str = "";
		while ((txtLine = readTxt.readLine()) != null) {
			str += txtLine;
		}
		String[] sarray = str.split(" ");
		for (int i = 0; i < sarray.length; i++) {
			array[i] = Integer.parseInt(sarray[i]);
			// System.out.println(sarray[i]);
		}
		dataLen = sarray.length;
		System.out.println("实际大小:" + dataLen);
	}

	public static void save(int label) throws IOException {
		FileWriter writer = new FileWriter(String.format("order%d.txt", label));
		for (int i = 0; i < dataLen; i++)
			writer.write(array[i] + "\r\n");
		writer.close();
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		System.out.println("程序开始！\n");
		System.out.println("排序开始！\n");

		// 串行快排
		load();
		long qsprtStartTime = System.currentTimeMillis();
		QuickSort.qsort(array, 0, dataLen - 1);
		long qsortEndTime = System.currentTimeMillis();
		save(1);
		System.out.println("测试结果:" + test());
		System.out.println("串行快速排序完成！\n");

		// 并行快排
		load();
		long pqsortStartTime = System.currentTimeMillis();
		QuickSort.pqsort(array, 0, dataLen - 1);
		long pqsortEndTime = System.currentTimeMillis();
		save(2);
		System.out.println("测试结果:" + test());
		System.out.println("并行快速排序完成！\n");

		// 串行归并
		load();
		long msortStartTime = System.currentTimeMillis();
		MergeSort.msort(array, 0, dataLen - 1);
		long msortEndTime = System.currentTimeMillis();
		save(3);
		System.out.println("测试结果:" + test());
		System.out.println("串行归并排序完成！\n");

		// 并行归并
		load();
		long pmsortStartTime = System.currentTimeMillis();
		MergeSort.pmsort(array, 0, dataLen - 1);
		long pmsortEndTime = System.currentTimeMillis();
		save(4);
		System.out.println("测试结果:" + test());
		System.out.println("并行归并排序完成！\n");

		// 串行枚举
		load();
		long esortStartTime = System.currentTimeMillis();
		EnumSort.esort(array, 0, dataLen - 1);
		long esortEndTime = System.currentTimeMillis();
		save(5);
		System.out.println("测试结果:" + test());
		System.out.println("串行枚举排序完成！\n");

		// 并行枚举
		load();
		long pesortStartTime = System.currentTimeMillis();
		EnumSort.pesort(array, 0, dataLen - 1);
		long pesortEndTime = System.currentTimeMillis();
		save(6);
		System.out.println("测试结果:" + test());
		System.out.println("并行枚举排序完成！\n");

		System.out.println("排序完成！\n");

		System.out.println("串行快速排序用时： " + (qsortEndTime - qsprtStartTime) + "ms\n");
		System.out.println("并行快速排序用时： " + (pqsortEndTime - pqsortStartTime) +
				"ms\n");
		System.out.println("串行归并排序用时： " + (pmsortEndTime - pmsortStartTime) +
				"ms\n");
		System.out.println("并行归并排序用时： " + (msortEndTime - msortStartTime) + "ms\n");
		System.out.println("串行枚举排序用时： " + (esortEndTime - esortStartTime) + "ms\n");
		System.out.println("并行枚举排序用时： " + (pesortEndTime - pesortStartTime) +
				"ms\n");
		System.out.println("程序结束！");
	}
}
