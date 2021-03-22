package com.myLab.corejava.algorithm;

import java.util.Stack;
import java.util.Vector;

/**
 * leetcode中常见问题的解法
 */
public class LeetCode {
    /**
     * 直方图最大面积，84题
     * 每根柱子往左和往右分别找边界
     * 暴力解法：
     * 轮询每个值，分别枚举其左右边界的范围，然后求出面积，最终得到一个最大的面积
     * 使用栈来解决：
     * 这个解决思路要仔细的思考一下，形成自己的理解
     * 使用一个栈来存放一组由栈底到栈顶是从小到大的值，代表往左枚举过的值
     */
    public int largestRectangleArea(int[] heights) {
        Stack<Integer> orderStack = new Stack<>();
        orderStack.push(-1);
        int maxArea = 0;
        for (int i = 0; i < heights.length; i++) {
            if(orderStack.peek() == -1)
                orderStack.push(i);
            else if (heights[i] > heights[orderStack.peek()]) {
                orderStack.push(i);
            }else{
                while (orderStack.peek() != -1 && heights[orderStack.peek()] > heights[i]) {
                    int pop = -1;
                    pop = orderStack.pop();
                    maxArea = Math.max(maxArea, heights[pop] * ((i - pop) + (pop - orderStack.peek()) - 1));
                }
                orderStack.push(i);
            }
        }
        while (orderStack.peek() != -1) {
            int pop = -1;
            pop = orderStack.pop();
            maxArea = Math.max(maxArea, heights[pop] * ((heights.length - pop) + (pop - orderStack.peek()) - 1));
        }
        return maxArea;
    }

    /**
     * 滑动窗口
     * 1.单调队列，双端队列，即每移动一次就会有一个出栈和入栈
     * 2.使用heap
     */
    public void sildWindow() {

    }

    /**
     * leetcode 40题，最小的k个数
     * 1.sort    NlogN
     * 2.用heap  Nlogk       自己实现一个heap，然后将数组元素放到heap中，然后循环取k个最小值，也可以使用java自带的priority queue就是heap
     * 3.quick-sort 快速排序
     * @param arr
     * @param k
     * @return
     */
    public int[] getLeastNumbers(int[] arr, int k) {
        return null;
    }

    /**
     * leetcode 347题，前k个高频元素，常考
     * 步骤：
     * 1.遍历数组，取得元素以及重复次数，使用map存放
     * 2.将重复次数放入heap中
     * @param nums
     * @param k
     * @return
     */
    public int[] topKFrequent(int[] nums, int k) {
        return  null;
    }

  public static void main(String[] args) {
        LeetCode lc = new LeetCode();
        int[] heights = {2,1,2};
        System.out.println(lc.largestRectangleArea(heights));
    }
}
