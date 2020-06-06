package com.myLab.corejava.array;

import org.springframework.util.Assert;

/**
 * 冒泡排序，冒泡排序时间复杂度O(n²)
 */
public class BubbleSort {
    private int[] unSort = {1,3,4,2,7,6,10,9,5,8};

    /**
     * 一次升序冒泡逻辑,轮询每个元素，跟下一个元素比较，如果比下一个元素大那就交换位置，
     * 这样一次冒泡之后，数组中最大的那个元素就会冒到数组的尾端
     * @return
     */
    private boolean bubbleAsce(int maxIndex){
        Assert.isTrue(maxIndex < unSort.length, "非法入参");
        int temp;
        boolean isBubble = false;//如果发生了冒泡(相邻元素互换)，该值变为true。如果一次冒泡都没发生，该值为false，说明数组已经达到有序了
        for(int i = 0; i < maxIndex; i++){
            if(unSort[i] > unSort[i+1]){
                isBubble = true;
                temp = unSort[i];
                unSort[i] = unSort[i+1];
                unSort[i+1] = temp;
            }
            if(i+1 == maxIndex)
                break;
        }
        return isBubble;
    }

    /**
     * 冒泡算法，最最简单的方法就是，数组有几个元素就进行冒泡排序几次，比如数组中如果有10个元素，那就进行十次冒泡排序，最终排序完毕。
     * 但是这种方式浪费性能，因为10个元素随机组成的数组呢，基本上是不用十次冒泡就能达到有序，极端情况下，比如一个降序排列的包含10个元素的数组，
     * 要将其变为升序排列，那就需要10次冒泡。
     * 同时，经过一次冒泡之后，最大的元素已经冒到了数组的尾端，下一次冒泡在轮询的时候其实可以不需要轮询到最后一个元素了(因为它已经是最大的了)，
     * 轮询的数组元素个数可以减1，这样也可以降低计算次数
     * 下面的方法中，根据上面的描述，对冒泡算法的使用上进行了一些优化
     */
    public void bubbleSort(){
        Long startTime = System.currentTimeMillis();
        int bubbleSortTimes = 0;//这个变量记录调用bubble()方法的次数，也就是进行冒泡算法的次数
        for(int i = unSort.length - 1; i >= 0; i--){
            bubbleSortTimes++;//进行冒泡逻辑的次数+1
            if(bubbleAsce(i))//第一次冒泡算法的最大下标i=数组长度-1，每经过一次冒泡算法，最大下标i都会减去1个，减伤冒泡时轮询的次数
                continue;
            else{
                //如果发现数组已经有序了，则马上退出，不会做多余的冒泡算法
                System.out.println("进行了" + bubbleSortTimes +"次冒泡，完成排序");
                break;
            }
        }
        Long endTime = System.currentTimeMillis();
        for(int i:  unSort){
            System.out.println(i);
        }
        System.out.println("耗费时间：" + (endTime - startTime)/1000 + "s");
    }

    public static void main(String[] args) {
        BubbleSort bs = new BubbleSort();
        bs.bubbleSort();
    }
}
