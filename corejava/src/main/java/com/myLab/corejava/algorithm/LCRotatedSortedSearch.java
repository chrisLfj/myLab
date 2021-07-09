package com.myLab.corejava.algorithm;

/**
 * leetcode 33题
 * 旋转有序数组的元素查找，所谓旋转有序数组是指在一个有序数组中间某个位置发生了旋转，例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2]
 * 针对于这种旋转数组，如何快速查找一个整数target是否在数组中，并返回它的下标
 * 解体思路：二分查找法，它适用于有序且有节的数组中快速查找元素O(logN)，但是对于旋转有序数组来说，
 * 它不是完全有序，而是分两部分，每部分都是一个有序数组，
 */
public class LCRotatedSortedSearch {
    public int search(int[] nums, int target) {
        int n = nums.length;
        if (n == 0) {
            return -1;
        }
        if (n == 1) {
            return nums[0] == target ? 0 : -1;
        }
        int left = 0, right = n - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            //每次循环进入时候，总是试图找到有序的那一部分数组，尝试在这部分数组中查找target，并通过移动left和right不断的缩小搜索范围
            if (nums[0] <= nums[mid]) {
                if (nums[0] <= target && target < nums[mid]) {
                    //如果判断target存在于低位的有序部分中，则移动right，之后每次循环进来都会在低位有序部分中查找
                    right = mid - 1;
                } else {
                    //如果判断target不存在于低位有序部分中，则意味着target存在于无序部分中，移动left，进行下一次循环的查找
                    left = mid + 1;
                }
            } else {
                if (nums[mid] < target && target <= nums[n - 1]) {
                    //如果判断target存在于高位有序部分中，则移动left，之后每次循环进来都会在高位有序部分中查找
                    left = mid + 1;
                } else {
                    //如果判断target不存在于高位有序部分中，则意味着target存在于无序部分中，移动right，进行下一次循环的查找
                    right = mid - 1;
                }
            }
        }
        return -1;
    }
}
