package com.myLab.corejava.algorithm;

import java.util.PriorityQueue;
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
            if (orderStack.peek() == -1)
                orderStack.push(i);
            else if (heights[i] > heights[orderStack.peek()]) {
                orderStack.push(i);
            } else {
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
     *
     * @param arr
     * @param k
     * @return
     */
    public int[] getLeastNumbers(int[] arr, int k) {
        //使用java自带的优先队列来实现，优先队列其实就是一个堆的数据结构，而且它是一个小顶堆，也就是父节点总是比其两个子节点小，正好满足找最小的K个值

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < arr.length; i++) {
            priorityQueue.add(arr[i]);
        }
        int res[] = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = priorityQueue.poll();
        }
        return res;
    }

    /**
     * leetcode 347题，前k个高频元素，常考
     * 步骤：
     * 1.遍历数组，取得元素以及重复次数，使用map存放
     * 2.将重复次数放入heap中
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] topKFrequent(int[] nums, int k) {
        return null;
    }

    /**
     * leetcode 70题，爬楼梯问题
     * 这是个递归问题，找重复性，数学归纳法，n=1，n=2,n=3找重复性
     * @return
     */
    public int climbStairs() {
        return 0;
    }

    /**
     * leetcode 22题，括号生成，思路有些晦涩，需要多看看视频
     * 递归的思维步骤，每一层都可能添加右或者左括号
     * @param args
     */

    /**
     * leetcode 98题，判断给定二叉树为有效的
     * 1.递归方法，中序遍历方法
     * @param args
     */

    /**
     * leetcode 104题，二叉树的最大深度
     * @param args
     */

    public static void main(String[] args) {
        LeetCode lc = new LeetCode();
        int[] heights = {2, 1, 2};
        System.out.println(lc.largestRectangleArea(heights));

        int[] nums = {1,2,1,4,5,3,65,55,66,65,65,65,2,2,2,55,55};
        int[] res = lc.getLeastNumbers(nums, 5);
        for (int i = 0; i < res.length; i++) {
            System.out.println(res[i]);
        }
    }
}