package com.myLab.corejava.algorithm;

import com.myLab.corejava.array.PriorityQueueDemo;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 一个大顶堆的实现
 */
public class LCMaxHeap<E> {
    //数组的默认容量
    private static final int DEFAULT_INITIAL_CAPACITY = 11;
    //对象数组，保存元素
    private Object[] queue;
    //比较函数
    private final Comparator<? super E> comparator;
    //数组的实际大小
    private int size;

    public LCMaxHeap(Comparator<? super E> comparator) {
        this(comparator, DEFAULT_INITIAL_CAPACITY);
    }

    public LCMaxHeap(Comparator<? super E> comparator, int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException();
        }
        this.comparator = comparator;
        this.queue = new Object[capacity];
    }

    /**
     * 大顶堆中增加一个元素，支持数组容量自动扩展
     * 增加的元素先放在最后一个元素，然后向上移动，移动逻辑是判断自己是否比父节点大，如果大则向上替换父节点，一直到根节点；
     * @param newElement
     */
    public void add(E newElement) {
        int currentSize = size;
        if (currentSize >= queue.length) {
            //数组扩容
            grow();
        }
        size = currentSize + 1;
        if(currentSize == 0){
            queue[0] = newElement;
            return;
        }
        else{
            heapifyUp(currentSize, newElement);
        }
    }

    /**
     * 大顶堆中删除一个元素，将删除的元素与数组最后一个元素互换位置，然后向下移动，移动逻辑是如果比子节点中最大的节点小，则与最大子节点进行替换，一直到没有子节点为止
     * @param index
     * @return
     */
    public boolean remove(int index) {
        if (index >= size) {
            throw new IllegalArgumentException();
        }
        int currentSize = size;
        size = currentSize - 1;
        if (currentSize == 1) {
            queue[0] = new Object();
        } else {
            heapifyDown(index);
        }
        return true;
    }

    public boolean transToMinHeap() {
        return true;
    }

    public Object getHeapTopElement() {
        if (queue.length >= 1) {
            return queue[0];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public Object[] toArray() {
        return Arrays.copyOf(queue, size);
    }

    private void heapifyUp(int index, E newElement) {
        while (index > 0) {
            int parent = (index - 1) >>> 1;
            E eParent = (E)queue[parent];
            if (comparator.compare(eParent, newElement) >= 0) {
                break;
            }
            queue[index] = eParent;
            index = parent;
        }
        queue[index] = newElement;
    }

    private void heapifyDown(int index) {
        E lastElement = (E)queue[size];
        queue[size] = new Object();
        queue[index] = lastElement;
        while (index < (size >> 1)) {
            int leftChild = index * 2 + 1;
            int rightChild = index * 2 + 2;
            int biggerChild = 0;
            if (rightChild < size) {
                if (comparator.compare((E) queue[leftChild], (E) queue[rightChild]) >= 0) {
                    biggerChild = leftChild;
                } else {
                    biggerChild = rightChild;
                }
            } else if (leftChild < size) {
                biggerChild = leftChild;
            }
            if (comparator.compare((E) queue[biggerChild], lastElement) > 0) {
                queue[index] = queue[biggerChild];
                queue[biggerChild] = lastElement;
            } else {
                break;
            }
            index = biggerChild;
        }

    }

    private E getBiggerChild(int index) {
        int leftChild = index * 2 + 1;
        int rightChild = index * 2 + 2;
        if (rightChild < size) {
            return comparator.compare((E) queue[leftChild], (E) queue[rightChild]) >= 0 ? (E) queue[leftChild] : (E) queue[rightChild];
        } else if (leftChild < size) {
            return (E) queue[leftChild];
        } else {
            return null;
        }
    }
    /**
     * 每次扩容都翻倍，这里写的简单些，没有考虑整形越界问题
     */
    private void grow() {
        int newCapacity = queue.length << 1;
        queue  = Arrays.copyOf(queue, newCapacity);
    }

    public static void main(String[] args) {
        int size = 0;
        System.out.println(size << 1);
        System.out.println(size >> 1);
        System.out.println(size >>> 1);

        LCMaxHeap<PriorityQueueDemo.Element> intHeap = new LCMaxHeap<>((t, o) -> t.compareTo(o));
        PriorityQueueDemo.Element e85 = new PriorityQueueDemo.Element(85);
        PriorityQueueDemo.Element e90 = new PriorityQueueDemo.Element(90);
        PriorityQueueDemo.Element e60 = new PriorityQueueDemo.Element(60);
        PriorityQueueDemo.Element e80 = new PriorityQueueDemo.Element(80);
        PriorityQueueDemo.Element e70 = new PriorityQueueDemo.Element(70);
        PriorityQueueDemo.Element e10 = new PriorityQueueDemo.Element(10);
        PriorityQueueDemo.Element e30 = new PriorityQueueDemo.Element(30);
        PriorityQueueDemo.Element e50 = new PriorityQueueDemo.Element(50);
        PriorityQueueDemo.Element e20 = new PriorityQueueDemo.Element(20);
        PriorityQueueDemo.Element e40 = new PriorityQueueDemo.Element(40);
        intHeap.add(e85);
        intHeap.add(e90);
        intHeap.add(e60);
        intHeap.add(e80);
        intHeap.add(e70);
        intHeap.add(e10);
        intHeap.add(e30);
        intHeap.add(e50);
        intHeap.add(e20);
        intHeap.add(e40);
        Arrays.stream(intHeap.toArray()).forEach(t -> System.out.println(t.toString()));
        System.out.println("after add");
        intHeap.remove(5);
        Arrays.stream(intHeap.toArray()).forEach(t -> System.out.println(t.toString()));
        System.out.println("after remove");
        System.out.println(((PriorityQueueDemo.Element)intHeap.getHeapTopElement()).getValue());

    }
}
/*
>>：带符号右移。正数右移高位补0，负数右移高位补1。比如：
4 >> 1，结果是2；-4 >> 1，结果是-2。-2 >> 1，结果是-1。
>>>：无符号右移。无论是正数还是负数，高位通通补0。
对于正数而言，>>和>>>没区别。
对于负数而言，-2 >>> 1，结果是2147483647（Integer.MAX_VALUE），-1 >>> 1，结果是2147483647（Integer.MAX_VALUE）。
以下代码可以判断两个数的符号是否相等
return ((a >> 31) ^ (b >> 31)) == 0;
 */