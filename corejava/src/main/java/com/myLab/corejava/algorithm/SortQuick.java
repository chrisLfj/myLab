package com.myLab.corejava.algorithm;

/**
 * 快速排序法，通过一次排序将待排记录分割成独立的两部分，其中一部分记录的关键字均比另一部分的关键字小，则可分别对这两部分记录继续进行排序，以达到整个序列有序。
 * 快速排序时间复杂度为O(nlogn)
 * 使用分治法来把一个list分为两个sublist，具体方法如下
 * 1.从数列中跳出一个元素，称为“基准”pivot；
 * 2.所有比pivot小的元素移动到它左边，所有比pivot大的元素在它右边，这个过程称为partition操作
 * 3.递归地recursive按照1，2两个步骤分别对pivot左边的子数列和pivot右边的子数列进行排序
 */
public class SortQuick {
    public void quickSort(int[] arr, int left, int right) {
        int len = arr.length;
        int partitionIndex;
        if (left < right) {
            partitionIndex = partition(arr, left, right);
            quickSort(arr, left, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, right);
        }
    }

    private int partition(int[] arr, int left, int right) {
        int pivot = left;
        int index = pivot + 1;
        for (int i = index; i <= right; i++) {
            if (arr[i] < arr[pivot]) {
                swap(arr, i, index);
                index++;
            }
        }
        swap(arr, pivot, index - 1);
        return index - 1;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
