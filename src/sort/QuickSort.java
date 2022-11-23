/*
 * @Author: scikkk 203536673@qq.com
 * @Date: 2018-12-29 13:11:40
 * @LastEditors: scikkk
 * @LastEditTime: 2022-11-23 15:37:05
 * @Description: QuickSort
 */
package sort;

import java.io.IOException;

public class QuickSort implements Runnable {
	private int left, right;
	private int[] nums;

	QuickSort(int[] nums, int left, int right) {
		this.nums = nums;
		this.left = left;
		this.right = right;
	}

	private static void swap(int[] nums, int a, int b) {
		int tmp = nums[a];
		nums[a] = nums[b];
		nums[b] = tmp;
	}

	public static int partition(int[] nums, int left, int right) {
		swap(nums, left, (left + right) / 2);
		int pivot = nums[left];
		int j = left;
		for (int k = left; k <= right; k++) {
			if (nums[k] < pivot) {
				j++;
				swap(nums, j, k);
			}
		}
		swap(nums, left, j);
		return j;
	}

	public static void qsort(int[] nums, int left, int right) {
		if (left == right - 1) {
			if (nums[left] > nums[right]) {
				swap(nums, left, right);
			}
			return;
		}
		if (left < right) {
			int r = partition(nums, left, right);
			qsort(nums, left, r - 1);
			qsort(nums, r + 1, right);
		}
	}

	public static void p2qsort(int[] nums, int left, int right) throws InterruptedException, IOException {
		int r0 = partition(nums, left, right);
		Thread thread0 = new Thread(new QuickSort(nums, left, r0 - 1));
		Thread thread1 = new Thread(new QuickSort(nums, r0 + 1, right));
		thread0.start();
		thread1.start();
	}

	public static void p4qsort(int[] nums, int left, int right) throws InterruptedException, IOException {
		// 0 1 2
		int r2 = partition(nums, left, right);
		int r1 = partition(nums, left, r2 - 1);
		int r0 = partition(nums, left, r1 - 1);
		Thread thread0 = new Thread(new QuickSort(nums, left, r0 - 1));
		Thread thread1 = new Thread(new QuickSort(nums, r0 + 1, r1 - 1));
		Thread thread2 = new Thread(new QuickSort(nums, r1 + 1, r2 - 1));
		Thread thread3 = new Thread(new QuickSort(nums, r2 + 1, right));
		thread0.start();
		thread1.start();
		thread2.start();
		thread3.start();
	}

	public static void p8qsort(int[] nums, int left, int right) throws InterruptedException, IOException {
		// 0 1 2 3 4 5 6
		int r3 = partition(nums, left, right);
		int r1 = partition(nums, left, r3 - 1);
		int r5 = partition(nums, r3 + 1, right);
		int r0 = partition(nums, left, r1 - 1);
		int r2 = partition(nums, r1 + 1, r3 - 1);
		int r4 = partition(nums, r3 + 1, r5 - 1);
		int r6 = partition(nums, r5 + 1, right);
		Thread thread0 = new Thread(new QuickSort(nums, left, r0 - 1));
		Thread thread1 = new Thread(new QuickSort(nums, r0 + 1, r1 - 1));
		Thread thread2 = new Thread(new QuickSort(nums, r1 + 1, r2 - 1));
		Thread thread3 = new Thread(new QuickSort(nums, r2 + 1, r3 - 1));
		Thread thread4 = new Thread(new QuickSort(nums, r3 + 1, r4 - 1));
		Thread thread5 = new Thread(new QuickSort(nums, r4 + 1, r5 - 1));
		Thread thread6 = new Thread(new QuickSort(nums, r5 + 1, r6 - 1));
		Thread thread7 = new Thread(new QuickSort(nums, r6 + 1, right));
		thread0.start();
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();
		thread6.start();
		thread7.start();
	}

	@Override
	public void run() {
		qsort(nums, left, right);
	}
}
