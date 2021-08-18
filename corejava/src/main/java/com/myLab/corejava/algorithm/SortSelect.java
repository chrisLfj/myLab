package com.myLab.corejava.algorithm;

import java.util.Arrays;

/**
 * 选择排序，每次都在未排序的数组中选出最大(小)元素放到数组开始的位置，然后循环多次(数组长度-1次)，即可得到有序数组
 * 表现最稳定的排序算法之一，因为无论什么数据进去都是O(n2)的时间复杂度，所以用到它的时候，数据规模越小越好。
 * 唯一的好处可能就是不占用额外的内存空间了吧。理论上讲，选择排序可能也是平时排序一般人想到的最多的排序方法了吧
 * 时间复杂度O(n平方)，空间复杂度O(1)
 */
public class SortSelect {

    public static void selectSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {//外部循环，每循环一次就会有一个最小的元素移动到数去的前面
            int minValueIndex = i;//准备一个变量记录最小元素的下标，它的起始值就应该等于未排序数组的第一个元素下标，即等于i
            for (int j = i + 1; j < array.length; j++) {//内部循环，每次循环从下标i+1开始，因为i之前的元素已经时有序的了，不需要处理
                if (array[j] < array[minValueIndex]) {
                    minValueIndex = j;//在内部循环的过程中，只要发现小于下标minValueIndex位置的元素，则将该元素的下标赋值给minValueIndex，这样一次内部循环结束之后minValueIndex存放的就是未排序数组的最小元素的下标
                }
            }
            if (minValueIndex != i) {//判断minValueIndex是否发生了变化，没变化则不需要交换元素，提升效率
                int temp = 0;
                temp = array[i];
                array[i] = array[minValueIndex];
                array[minValueIndex] = temp;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {6,5,77,66,54,6,7,8,99,123};
        selectSort(array);
        Arrays.stream(array).forEach(o -> System.out.println(o));
    }
}
