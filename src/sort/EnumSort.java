/*
 * @Author: scikkk 203536673@qq.com
 * @Date: 2018-12-29 13:11:40
 * @LastEditors: scikkk
 * @LastEditTime: 2022-11-23 15:36:42
 * @Description: EnumSort
 */

package sort;

import java.util.concurrent.CountDownLatch;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EnumSort implements Runnable {
	private int left, right, pos;
	private int[] nums;
	private int[] buf_nums;
	CountDownLatch end_signal;

	EnumSort(int[] nums, int[] buf_nums, int left, int right, int pos, CountDownLatch end_signal) {
		this.left = left;
		this.right = right;
		this.pos = pos;
		this.nums = nums;
		this.buf_nums = buf_nums;
		this.end_signal = end_signal;
	}

	public static void esort(int[] nums, int left, int right) throws IOException {
		int[] buf_nums = new int[right - left + 1];
		for (int i = left; i <= right; i++) {
			int rank = 0;
			for (int j = 0; j <= right; j++) {
				if ((nums[i] > nums[j]) || ((nums[i] == nums[j] && i > j))) {
					rank++;
				}
			}
			buf_nums[rank] = nums[i];
		}
		for (int k = 0; k <= right; k++) {
			nums[k] = buf_nums[k];
		}

	}

	public static void pesort(int[] nums, int left, int right, int p_num) throws IOException, InterruptedException {
		int[] buf_nums = new int[right - left + 1];
		CountDownLatch end_signal = new CountDownLatch((right - left + 1));
		ExecutorService exec = Executors.newFixedThreadPool(p_num);
		for (int k = left; k <= right; k++) {
			exec.execute(new EnumSort(nums, buf_nums, left, right, k, end_signal));
		}
		exec.shutdown();
		end_signal.await();
		for (int k = left; k <= right; k++) {
			nums[k] = buf_nums[k];
		}
	}

	@Override
	public void run() {
		int rank = 0;
		for (int j = left; j <= right; j++) {
			if ((nums[pos] > nums[j]) || ((nums[pos] == nums[j] && pos > j))) {
				rank++;
			}
		}
		buf_nums[rank] = nums[pos];
		end_signal.countDown();
	}

}
