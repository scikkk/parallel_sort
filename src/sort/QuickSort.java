/*
 * @Author: scikkk 203536673@qq.com
 * @Date: 2018-12-29 13:11:40
 * @LastEditors: scikkk
 * @LastEditTime: 2022-11-21 01:35:38
 * @Description: QuickSort
 */
package sort;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class QuickSort implements Runnable {
	int begin, end;
	private int[] array;
	CountDownLatch mergeSignal;

	QuickSort(int[] array, int begin, int end, CountDownLatch mergeSignal) {
		this.array = array;
		this.begin = begin;
		this.end = end;
		this.mergeSignal = mergeSignal;
	}

	private static void Swap(int[] array, int p1, int p2) {
		int tmp = array[p1];
		array[p1] = array[p2];
		array[p2] = tmp;
	}

	public static int partition(int[] array, int p, int q) {
		Swap(array, p, (p + q) / 2);
		int pivot = array[p];
		int j = p;
		for (int k = p; k <= q; k++) {
			if (array[k] < pivot) {
				j++;
				Swap(array, j, k);
			}
		}
		Swap(array, p, j);
		return j;
	}

	public static void qsort(int[] array, int p, int q) {
		if (p == q - 1) {
			if (array[p] > array[q]) {

				Swap(array, p, q);

			}
			return;
		}
		if (p < q) {
			int r = partition(array, p, q);
			qsort(array, p, r - 1);
			qsort(array, r + 1, q);
		}
	}

	public static void pqsort(int[] Array, int Begin, int End) throws InterruptedException, IOException {
		// 0 1 2 3 4 5 6
		CountDownLatch mergeSignal = new CountDownLatch(8);
		int r3 = partition(Array, Begin, End);
		int r1 = partition(Array, Begin, r3 - 1);
		int r5 = partition(Array, r3 + 1, End);
		int r0 = partition(Array, Begin, r1 - 1);
		int r2 = partition(Array, r1 + 1, r3 - 1);
		int r4 = partition(Array, r3 + 1, r5 - 1);
		int r6 = partition(Array, r5 + 1, End);

		// System.out.println("partition:" + r0 + ' ' + r1 + ' ' + r2 + ' ' + r3 + ' ' +
		// r4 + ' ' + r5);
		Thread thread0 = new Thread(new QuickSort(Array, Begin, r0 - 1, mergeSignal));
		Thread thread1 = new Thread(new QuickSort(Array, r0 + 1, r1 - 1, mergeSignal));
		Thread thread2 = new Thread(new QuickSort(Array, r1 + 1, r2 - 1, mergeSignal));
		Thread thread3 = new Thread(new QuickSort(Array, r2 + 1, r3 - 1, mergeSignal));
		Thread thread4 = new Thread(new QuickSort(Array, r3 + 1, r4 - 1, mergeSignal));
		Thread thread5 = new Thread(new QuickSort(Array, r4 + 1, r5 - 1, mergeSignal));
		Thread thread6 = new Thread(new QuickSort(Array, r5 + 1, r6 - 1, mergeSignal));
		Thread thread7 = new Thread(new QuickSort(Array, r6 + 1, End, mergeSignal));
		thread0.start();
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();
		thread6.start();
		thread7.start();
		mergeSignal.await();
	}

	@Override
	public void run() {
		qsort(array, begin, end);
		mergeSignal.countDown();
	}
}
