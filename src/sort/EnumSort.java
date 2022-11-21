/*
 * @Author: scikkk 203536673@qq.com
 * @Date: 2018-12-29 13:11:40
 * @LastEditors: scikkk
 * @LastEditTime: 2022-11-21 01:46:21
 * @Description: EnumSort
 */

package sort;

import java.util.concurrent.CountDownLatch;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EnumSort implements Runnable {
	private int begin, end, pos;
	private int[] array;
	private int[] brrby;
	CountDownLatch mergeSignal;

	EnumSort(int[] array, int[] brrby, int begin, int end, int pos, CountDownLatch mergeSignal) {
		this.begin = begin;
		this.end = end;
		this.pos = pos;
		this.array = array;
		this.brrby = brrby;
		this.mergeSignal = mergeSignal;
	}

	public static void esort(int[] array, int p, int q) throws IOException {
		int[] brrby = new int[q - p + 1];
		for (int i = p; i <= q; i++) {
			int rank = 0;
			for (int j = 0; j <= q; j++) {
				if ((array[i] > array[j]) || ((array[i] == array[j] && i > j)))
					rank++;
			}
			brrby[rank] = array[i];
		}
		for (int i = 0; i <= q; i++) {
			array[i] = brrby[i];
		}

	}

	public static void pesort(int[] array, int Begin, int End) throws IOException, InterruptedException {
		int[] Brrby = new int[End - Begin + 1];
		CountDownLatch mergeSignal = new CountDownLatch((End - Begin + 1));
		ExecutorService exec = Executors.newFixedThreadPool(8);
		for (int i = Begin; i <= End; i++) {
			// 线程池
			exec.execute(new EnumSort(array, Brrby, Begin, End, i, mergeSignal));
		}
		exec.shutdown();

		mergeSignal.await();

		for (int i = Begin; i <= End; i++) {
			array[i] = Brrby[i];
		}

	}

	@Override
	public void run() {
		int rank = 0;
		for (int j = begin; j <= end; j++) {
			if ((array[pos] > array[j]) || ((array[pos] == array[j] && pos > j)))
				rank++;
		}
		brrby[rank] = array[pos];
		mergeSignal.countDown();
		// System.out.println(brrby[rank]);
	}

}
