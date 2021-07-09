package com.myLab.corejava.algorithm;

/**
 * leetcode 153题
 * 旋转有序数组的元素查找，所谓旋转有序数组是指在一个有序数组中间某个位置发生了旋转，例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2]
 * 针对于这种旋转数组，如何快速查找一个整数target是否在数组中，并返回它的下标
 * 解体思路：二分查找法，它适用于有序且有节的数组中快速查找元素O(logN)，但是对于旋转有序数组来说，
 * 它不是完全有序，而是分两部分，每部分都是一个有序数组，
 */
public class LCFindMinInRotatedSorted {
    public int findMin(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        if (n == 2) {
            return Math.min(nums[0], nums[1]);
        }
        if (nums[0] <= nums[n - 1]) {
            return nums[0];
        }
        int left = 0, right = n - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            if (nums[mid] < nums[0]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return nums[left];
    }

    public static void main(String[] args) {
//        int[] nums = {5, 6, 7, 0, 1, 2, 3, 4};
//        int[] nums = {2, 1};
        int[] nums = {3, 1, 2};
        LCFindMinInRotatedSorted test = new LCFindMinInRotatedSorted();
        System.out.println(test.findMin(nums));
    }
}
