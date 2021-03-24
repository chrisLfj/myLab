package com.myLab.corejava.algorithm;

import com.sun.xml.internal.bind.v2.model.core.Ref;

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

    /**
     * 排序+双指针两面夹逼的方式，注意去重的逻辑，这个方法代码实现更优美，并且避免了无畏的遍历
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSumImprove(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for(int k = 0; k < nums.length - 2; k++){
            if(nums[k] > 0) break;
            if(k > 0 && nums[k] == nums[k - 1]) continue;
            int i = k + 1, j = nums.length - 1;
            while(i < j){
                int sum = nums[k] + nums[i] + nums[j];
                if(sum < 0){
                    while(i < j && nums[i] == nums[++i]);
                } else if (sum > 0) {
                    while(i < j && nums[j] == nums[--j]);
                } else {
                    res.add(new ArrayList<Integer>(Arrays.asList(nums[k], nums[i], nums[j])));
                    while(i < j && nums[i] == nums[++i]);
                    while(i < j && nums[j] == nums[--j]);
                }
            }
        }
        return res;
    }

    public List<List<Integer>> threeSumBetter(int[] nums) {
        //数组先排序，从小到大
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        if (nums.length < 3) {
            return result;
        }
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                //遍历元素，如果遇到相等的则跳过
                continue;
            }
            //双指针L和R分别从头尾两个方向向中间靠拢，如果a+b+c>0说明大了，由于数组是经过排序的，那R指针就向左移动，反之说明小了，则L指针向右移动
            //L和R重合时就退出，然后i++继续以上逻辑
            int L = i+1;
            int R = nums.length - 1;
            while (L < R) {
                List<Integer> element = new ArrayList<>();

                if(nums[i] + nums[L] + nums[R] > 0)
                    R--;
                else if(nums[i] + nums[L] + nums[R] < 0)
                    L++;
                else if(nums[i] + nums[L] + nums[R] == 0){
                    element.add(nums[i]);
                    element.add(nums[L]);
                    element.add(nums[R]);
                    result.add(element);
                    //为防止[-2,0,0,2,2]这种情况，所以L和R在移动时也需要跳过相同值
                    while (L < R && nums[L] == nums[L + 1]) {
                        L = L + 1;
                    }
                    while (L < R && nums[R] == nums[R - 1]) {
                        R = R - 1;
                    }
                    L++;
                    R--;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        ThreeNumSum threeNumSum = new ThreeNumSum();
        List<List<Integer>> result = threeNumSum.threeSumBetter(new int[]{-1, 0, 1, 2, -1, -4});
        System.out.println(result);
    }
}
