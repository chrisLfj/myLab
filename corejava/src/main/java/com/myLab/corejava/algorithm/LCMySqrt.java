package com.myLab.corejava.algorithm;

/**
 * leetcode 69题 x的平方根
 * 实现 int sqrt(int x) 函数。计算并返回 x 的平方根，其中 x 是非负整数。由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。
 * 链接：https://leetcode-cn.com/problems/sqrtx
 *
 * 使用二分法来实现，二分法的特点是
 * 1.有序数组
 * 2.有上下界
 * 3.可以通过索引快速访问
 *
 * 求x的平方根，可以在0-x之间的数字中查找k*k <= x 中最大的那个k
 */
public class LCMySqrt {
    public int mySqrt(int x) {
        int left = 0, right = x, ans = -1;
        while (left <= right) {
            int mid = (left + right)/ 2;
            if ((long)mid * mid <= x) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        LCMySqrt test = new LCMySqrt();
        System.out.println("hello world");
        System.out.println("" + test.mySqrt(5));
    }
}
