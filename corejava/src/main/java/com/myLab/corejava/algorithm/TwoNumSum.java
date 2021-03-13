package com.myLab.corejava.algorithm;

import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

/**
 * leetcode 1 两数之和
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 的那 两个 整数，并返回它们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 *
 * 你可以按任意顺序返回答案
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/two-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class TwoNumSum {

    /**
     * 比较粗暴的解决思路就是应为要找出数组中两数之和等于目标值的那两个数的下标，因此就遍历两边，此时时间复杂度为O(n^2)
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        int[] result={-1,-1};
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
        return result;
    }

    /**
     * 使用hash表实现将元素和下标存好，然后只需遍历数组，比如元素i，则它就去hash表中查找是否存在元素j=target-元素i
     * 由于hash表查询的时间复杂度为O(1)
     * 将数组元素存入hash表中的时间复杂度为O(n)
     * 因此，总的时间复杂度就是O(n)
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSumBetter(int[] nums, int target) {
        Map<Integer, Integer> store = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            store.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            if (store.containsKey(target - nums[i]) && i != store.get(target - nums[i])) {
                return new int[]{i, store.get(target - nums[i])};
            }
        }
        return new int[0];
    }
}
