package com.myLab.corejava.algorithm;

import java.util.Arrays;

/**
 * 冒泡排序，是一种比较排序法，通过比较数组中相邻元素的大小，如果前一个元素大于后一个元素，则互换顺序。
 * 因此在一次排序循环中，最大的元素会冒到"待排序元素"的最后面，就像水泡冒出水面。
 * 冒泡排序的时间复杂度位O(n平方)，空间复杂度O(1)，因为整个算法过程中不需要额外申请空间
 */
public class SortBubble {
    public static void bubbleSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) { //外层循环，需要array.length-1次冒泡循环，因为最后一个元素不需要冒泡，所以需要array.length-1次冒泡即可
            for (int index = 0; index < array.length - 1; index++) {//内层循环，下标从小到大移动，移动的过程中进行冒泡逻辑，需要移动array.length-1次
                int temp = 0;
                temp = array[index];
                if (array[index] > array[index + 1]) {
                    array[index] = array[index + 1];
                    array[index + 1] = temp;
                }
            }
        }
    }

    /**
     * 冒泡算法优化，思路是已经“冒泡”过的元素不需要再次参与冒泡逻辑，即内层循环的数组下标在从小到大进行移动的时候，不需要移动到最后
     * @param array
     */
    public static void optiBubbleSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int index = 0; index < array.length - 1 - i; index++) {//注意这里-i，就是一个算法优化，即表示之前已经有i个元素已经冒泡过了，因此下标不需要每次都移动到最后
                int temp = 0;
                temp = array[index];
                if (array[index] > array[index + 1]) {
                    array[index] = array[index + 1];
                    array[index + 1] = temp;
                }
            }
        }
    }
    public static void main(String[] args) {
        int[] array = {6,5,77,66,54};
        optiBubbleSort(array);
        Arrays.stream(array).forEach(o -> System.out.println(o));
    }
}
