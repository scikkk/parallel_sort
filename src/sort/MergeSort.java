/*
 * @Author: scikkk 203536673@qq.com
 * @Date: 2018-12-29 13:11:40
 * @LastEditors: scikkk
 * @LastEditTime: 2022-11-23 15:40:03
 * @Description: MergeSort
 */

package sort;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class MergeSort implements Runnable {
	private int left, right;
	private int[] nums;
	private CountDownLatch end_signal;

	public MergeSort(int[] nums, int left, int right, CountDownLatch end_signal) {
		this.nums = nums;
		this.left = left;
		this.right = right;
		this.end_signal = end_signal;
	}

	public static void merge(int[] nums, int left, int right) {
		int[] buf_nums = new int[right - left + 1];
		int mid = (left + right) / 2;
		int idx = 0;
		int idx_a = left, idx_b = mid + 1;
		while ((idx_a <= mid) && (idx_b <= right)) {
			if (nums[idx_a] < nums[idx_b]) {
				buf_nums[idx] = nums[idx_a];
				idx++;
				idx_a++;
			} else {
				buf_nums[idx] = nums[idx_b];
				idx++;
				idx_b++;
			}
		}
		while (idx_a <= mid) {
			buf_nums[idx] = nums[idx_a];
			idx++;
			idx_a++;
		}
		while (idx_b <= right) {
			buf_nums[idx] = nums[idx_b];
			idx++;
			idx_b++;
		}
		for (int k = 0; k < idx; k++) {
			nums[left + k] = buf_nums[k];
		}
	}

	public static void msort(int[] nums, int left, int right) {
		if (left >= right) {
			return;
		}
		int mid = (left + right) / 2;
		msort(nums, left, mid);
		msort(nums, mid + 1, right);
		merge(nums, left, right);

	}

	private static void swap(int[] nums, int a, int b) {
		int tmp = nums[a];
		nums[a] = nums[b];
		nums[b] = tmp;
	}

	public static void p4msort(int[] nums, int left, int right) throws IOException, InterruptedException {
		// 均匀划分
		CountDownLatch end_signal = new CountDownLatch(4);
		int sub_len = (right - left + 1) / 4;
		// 局部排序
		Thread thread0 = new Thread(new MergeSort(nums, 0, sub_len, end_signal));
		Thread thread1 = new Thread(new MergeSort(nums, sub_len + 1, 2 * sub_len + 1, end_signal));
		Thread thread2 = new Thread(new MergeSort(nums, 2 * sub_len + 2, 3 * sub_len + 2, end_signal));
		Thread thread3 = new Thread(new MergeSort(nums, 3 * sub_len + 3, right, end_signal));
		thread0.start();
		thread1.start();
		thread2.start();
		thread3.start();
		end_signal.await();
		ArrayList<Integer> a0 = new ArrayList<Integer>();
		ArrayList<Integer> a1 = new ArrayList<Integer>();
		ArrayList<Integer> a2 = new ArrayList<Integer>();
		ArrayList<Integer> a3 = new ArrayList<Integer>();
		// 正则采样
		int[] samples = new int[16];
		for (int k = 0; k < 16; k++) {
			samples[k] = nums[(right - left + 1) / 16 * k];
		}
		// 采样排序
		MergeSort.msort(samples, 0, 15);
		// 全局交换
		for (int k = left; k <= right; k++) {
			if (nums[k] <= samples[4])
				a0.add(nums[k]);
			else if ((nums[k] <= samples[8]) && (nums[k] > samples[4]))
				a1.add(nums[k]);
			else if ((nums[k] <= samples[12]) && (nums[k] > samples[8]))
				a2.add(nums[k]);
			else if (nums[k] > samples[12])
				a3.add(nums[k]);
			else {
				System.out.println("错误！");
				break;
			}
		}
		int idx = 0;
		for (int k : a0) {
			nums[idx++] = k;
		}
		for (int k : a1) {
			nums[idx++] = k;
		}
		for (int k : a2) {
			nums[idx++] = k;
		}
		for (int k : a3) {
			nums[idx++] = k;
		}
		// 归并排序
		thread0 = new Thread(new MergeSort(nums, 0, a0.size() - 1, end_signal));
		thread1 = new Thread(new MergeSort(nums, a0.size(), a0.size() + a1.size() - 1, end_signal));
		thread2 = new Thread(
				new MergeSort(nums, a0.size() + a1.size(), a0.size() + a1.size() + a2.size() - 1, end_signal));
		thread3 = new Thread(new MergeSort(nums, a0.size() + a1.size() + a2.size(),
				a0.size() + a1.size() + a2.size() + a3.size() - 1, end_signal));
		thread0.start();
		thread1.start();
		thread2.start();
		thread3.start();
	}

	public static void p3msort(int[] nums, int left, int right) throws IOException, InterruptedException {
		// 均匀划分
		CountDownLatch end_signal = new CountDownLatch(3);
		int sub_len = (right - left + 1) / 3;
		// 局部排序
		Thread thread0 = new Thread(new MergeSort(nums, 0, sub_len, end_signal));
		Thread thread1 = new Thread(new MergeSort(nums, sub_len + 1, 2 * sub_len + 1, end_signal));
		Thread thread2 = new Thread(new MergeSort(nums, 2 * sub_len + 2, 3 * sub_len + 2, end_signal));
		thread0.start();
		thread1.start();
		thread2.start();
		end_signal.await();
		// 正则采样
		int[] samples = new int[9];
		for (int k = 0; k < 9; k++) {
			samples[k] = nums[(right - left + 1) / 9 * k];
		}
		// 采样排序
		MergeSort.msort(samples, 0, 8);
		// 全局交换
		int p0 = left - 1, p1 = left - 1;
		for (int k = left; k <= right; k++) {
			if (nums[k] <= samples[3]) {
				p0++;
				p1++;
				swap(nums, k, p1);
				swap(nums, p0, p1);
			} else if ((nums[k] <= samples[6]) && (nums[k] > samples[3])) {
				p1++;
				swap(nums, k, p1);
			}
		}
		// 归并排序
		thread0 = new Thread(new MergeSort(nums, left, p0, end_signal));
		thread1 = new Thread(new MergeSort(nums, p0 + 1, p1, end_signal));
		thread2 = new Thread(new MergeSort(nums, p1 + 1, right, end_signal));
		thread0.start();
		thread1.start();
		thread2.start();
	}

	@Override
	public void run() {
		msort(nums, left, right);
		end_signal.countDown();
	}
}
