package com.myLab.corejava.algorithm;

import java.util.Stack;

public class LCLargestRectangleArea {
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
}
