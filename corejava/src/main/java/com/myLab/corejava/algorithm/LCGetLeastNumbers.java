package com.myLab.corejava.algorithm;

import java.util.PriorityQueue;

public class LCGetLeastNumbers {
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
}
