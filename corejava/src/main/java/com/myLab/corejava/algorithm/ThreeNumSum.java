package com.myLab.corejava.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * leetcode 15题 三数之和 高频面试算法题
 * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
 *
 * 注意：答案中不可以包含重复的三元组。
 *
 */
public class ThreeNumSum {
    /**
     * 暴力求解法：a + b + c =0转换为求a+b=-c,与两数求和类似，这里使用三层循环，时间复杂度为o(n^3)
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<String> resultStrList = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; i < nums.length - 1; j++) {
                for (int k = j + 1; i < nums.length; k++) {
                    if (i + j == -k) {

                        result.add(Arrays.asList(i,j,k));
                        //由于题目要求三元数组不能重复，因此还要对result中的三元数组进行判重处理
                    }
                }
            }
        }
        return result;
    }
}
