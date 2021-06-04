package com.myLab.corejava.algorithm;

/**
 * leetcode 11 盛最多水的容器
 * 这一题其实是求最大面积的
 * 给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0) 。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 *
 * 说明：你不能倾斜容器。
 */
public class LCMaxArea {
    /**
     * 解题思路：
     * 1.粗暴方法是，循环套循环，即先用第一个元素分别跟其它n-1个元素求面积，然后再用第二个元素跟其它n-2个元素求面积
     * 最后取这些面积中最大的一个即得到结果，但是这种方法的时间复杂度是O(n^2)
     * 2.优化方法是，使用一次循环即可达到效果，目的是减少求面积的次数，原理是两个指针i和j分别从数组的头和尾向中间移动，
     * 如果i的元素大于j的元素，则i不动，只移动j，移动之前先求面积，反之则j不动，只移动i，直到i和j重合，这个过程中最大的面积即结果
     * @param height
     * @return
     */
    public int maxArea(int[] height) {
        int maxArea = 0;
        for (int i = 0, j = height.length - 1; i < j; ) {
            if (height[i] <= height[j]) {
                int area = height[i++] * (j - i + 1);
                maxArea = Math.max(maxArea, area);
            } else {
                int area = height[j--] * (j - i + 1);
                maxArea = Math.max(maxArea, area);
            }
        }
        return maxArea;
    }

    public static void main(String[] args) {
        int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        LCMaxArea LCMaxArea = new LCMaxArea();
        System.out.println(LCMaxArea.maxArea(height));
    }
}
