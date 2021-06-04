package com.myLab.corejava.algorithm;

import java.util.HashMap;
import java.util.Map;

public class LCClimbStairs {
    /**
     * leetcode 70题，爬楼梯问题
     * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
     * 注意：给定 n 是一个正整数。
     * 这是个递归问题，找重复性，数学归纳法，假设台阶数量为n，则当n=1，n=2,n=3,n=4,n=5时分别找出需要几种方法。
     * 使用归纳法可知，爬楼梯的方法数的求解函数为f(n)，则有f(n)=f(n-1)+f(n-2)
     * 使用递归来解决爬楼梯问题，如果不存放中间结果的话，会存在很多的冗余计算，时间复杂度O(2^n)，2的n次方的复杂度，这个性能是有问题的
     * @return
     */
    public static int climbStairs(int n) {
        int[] ways = new int[n+1];
        ways[0] = 1;
        ways[1] = 1;
        for (int i = 2; i < n + 1; i++) {
            ways[i] = ways[i - 1] + ways[i - 2];
        }
        return ways[n];
    }

    //最笨的递归思路，一层一层的调用方法，而且中间计算有很多冗余
    public static int climbStairsBad(int n) {
        if (n == 2) {
            return 2;
        }
        if (n == 1) {
            return 1;
        }
        return climbStairs(n - 1) + climbStairs(n - 2);
    }
    //优化一下算法，保存中间数据，想到要保存计算过的结果，如果结果已经计算则直接从map中获取返回即可，但是这个代码写的还是不好
    //时间复杂度还是不理想，主要原因是1.递归调用方法，压栈出栈频繁。2.每次都去map中去查找key，这个遍历算法的时间复杂度O(n)
    public static int climbStairsBetter(int n) {
        Map<Integer, Integer> tempMap = new HashMap<>();
        return getClimbStairsWays(n, tempMap);
    }

    private static int getClimbStairsWays(int n, Map<Integer, Integer> tempMap) {
        if (tempMap.containsKey(n)) {
            return tempMap.get(n);
        } else if (n == 1) {
            tempMap.put(1, 1);
            return 1;
        } else if (n == 2) {
            tempMap.put(2, 2);
            return 2;
        } else {
            int result = climbStairs(n - 1) + climbStairs(n - 2);
            tempMap.put(n, result);
            return result;
        }
    }
}
