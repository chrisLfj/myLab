package com.myLab.corejava.array;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * PriorityQueue优先队列，采用堆的数据结构来管理一个数组，堆的数据结构就决定了堆顶的元素一定是最大(大顶堆)或者最小(小顶堆)
 * 因此可以在O(1)的时间复杂度下快速返回堆顶元素，非常适合优先队列的使用场景。由于堆的操作过程中，需要比较元素大小来实现元素的移动，
 * 因此，PriorityQueue在初始化是需要传入一个Comparator函数或者如果不传Comparator函数那就要求堆中的元素需要实现comparable接口
 * PriorityQueue默认是小顶堆，即最小元素在堆顶
 */
public class PriorityQueueDemo {
    public static void main(String[] args) {
        Element e85 = new Element(85);
        Element e90 = new Element(90);
        Element e60 = new Element(60);
        Element e80 = new Element(80);
        Element e70 = new Element(70);
        Element e10 = new Element(10);
        Element e30 = new Element(30);
        Element e50 = new Element(50);
        Element e20 = new Element(20);
        Element e40 = new Element(40);
        PriorityQueue<Element> pq = new PriorityQueue<>();
        pq.add(e85);
        pq.add(e90);
        pq.add(e60);
        pq.add(e80);
        pq.add(e70);
        pq.add(e10);
        pq.add(e30);
        pq.add(e50);
        pq.add(e20);
        pq.add(e40);
        Arrays.stream(pq.toArray()).forEach(t -> System.out.println(t.toString()));
    }
}

class Element implements Comparable<Element>{
    public int getValue() {
        return value;
    }

    int value;

    public Element(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(Element o) {
        return this.value - o.value;
    }

    public String toString() {
        return "" + value;
    }
}