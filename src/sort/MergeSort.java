/*
 * @Author: scikkk 203536673@qq.com
 * @Date: 2018-12-29 13:11:40
 * @LastEditors: scikkk
 * @LastEditTime: 2022-11-21 01:46:02
 * @Description: MergeSort
 */

package sort;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class MergeSort implements Runnable {
	private int begin, end;
	private int[] array;
	private CountDownLatch mergeSignal;

	public MergeSort(int[] array, int begin, int end) {
		this.array = array;
		this.begin = begin;
		this.end = end;
	}

	public MergeSort(int[] array, int begin, int end, CountDownLatch mergeSignal) {
		this.array = array;
		this.begin = begin;
		this.end = end;
		this.mergeSignal = mergeSignal;
	}

	public static void merge(int[] array, int p, int q) {
		int[] brrby = new int[q - p + 1];
		int r = (p + q) / 2;
		int bIndex = 0;
		int cp = p, cr = r + 1;
		while ((cp <= r) && (cr <= q)) {
			if (array[cp] < array[cr]) {
				brrby[bIndex] = array[cp];
				bIndex++;
				cp++;
			} else {
				brrby[bIndex] = array[cr];
				bIndex++;
				cr++;
			}
		}
		if (cp <= r) {
			while (cp <= r) {
				brrby[bIndex] = array[cp];
				bIndex++;
				cp++;
			}
		} else {
			while (cr <= q) {
				brrby[bIndex] = array[cr];
				bIndex++;
				cr++;
			}
		}
		for (int i = 0; i < bIndex; i++) {
			array[p + i] = brrby[i];
		}
	}

	public static void msort(int[] array, int p, int q) {
		if (p >= q)
			return;
		int r = (p + q) / 2;
		msort(array, p, r);
		msort(array, r + 1, q);
		merge(array, p, q);

	}

	public static void pmsort(int[] array, int begin, int end) throws IOException, InterruptedException {
		// 均匀划分
		CountDownLatch mergeSignal = new CountDownLatch(4);
		int length0 = (end - begin + 1) / 4;
		Thread thread0 = new Thread(new MergeSort(array, 0, length0, mergeSignal));
		Thread thread1 = new Thread(new MergeSort(array, length0 + 1, 2 * length0 + 1, mergeSignal));
		Thread thread2 = new Thread(new MergeSort(array, 2 * length0 + 2, 3 * length0 + 2, mergeSignal));
		Thread thread3 = new Thread(new MergeSort(array, 3 * length0 + 3, 29999, mergeSignal));
		thread0.start();
		thread1.start();
		thread2.start();
		thread3.start();
		mergeSignal.await();
		// System.out.println("2222");
		ArrayList<Integer> a0 = new ArrayList();
		ArrayList<Integer> a1 = new ArrayList();
		ArrayList<Integer> a2 = new ArrayList();
		ArrayList<Integer> a3 = new ArrayList();
		int[] arrayFlags = new int[16];
		for (int k = 0; k < 16; k++) {
			arrayFlags[k] = array[(end - begin + 1) / 16 * k];
		}
		// for (int i = 0; i < 16; i++)
		// System.out.println(arrayFlags[i]);
		MergeSort.msort(arrayFlags, 0, 15);
		// for (int i = 0; i < 16; i++)
		// System.out.println(arrayFlags[i]);
		// 全局交换
		for (int k = begin; k <= end; k++) {
			if (array[k] <= arrayFlags[4])
				a0.add(array[k]);
			else if ((array[k] <= arrayFlags[8]) && (array[k] > arrayFlags[4]))
				a1.add(array[k]);
			else if ((array[k] <= arrayFlags[12]) && (array[k] > arrayFlags[8]))
				a2.add(array[k]);
			else if (array[k] > arrayFlags[12])
				a3.add(array[k]);
			else {
				System.out.println("错误！");
				break;
			}
		}
		int idx = 0;
		for (int k = 0; k < a0.size(); k++) {
			array[idx] = a0.get(k);
			idx++;
		}
		for (int k = 0; k < a1.size(); k++) {
			array[idx] = a1.get(k);
			idx++;
		}
		for (int k = 0; k < a2.size(); k++) {
			array[idx] = a2.get(k);
			idx++;
		}
		for (int k = 0; k < a3.size(); k++) {
			array[idx] = a3.get(k);
			idx++;
		}
		mergeSignal = new CountDownLatch(4);
		thread0 = new Thread(new MergeSort(array, 0, a0.size() - 1, mergeSignal));
		thread1 = new Thread(new MergeSort(array, a0.size(), a0.size() + a1.size() - 1, mergeSignal));
		thread2 = new Thread(
				new MergeSort(array, a0.size() + a1.size(), a0.size() + a1.size() + a2.size() - 1, mergeSignal));
		thread3 = new Thread(new MergeSort(array, a0.size() + a1.size() + a2.size(),
				a0.size() + a1.size() + a2.size() + a3.size() - 1, mergeSignal));
		thread0.start();
		thread1.start();
		thread2.start();
		thread3.start();
		mergeSignal.await();
	}

	@Override
	public void run() {
		msort(array, begin, end);
		// System.out.println("1111");
		mergeSignal.countDown();
	}
}
