package com.myLab.corejava.algorithm;

/**
 * leetcode 283 移动零
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序
 * 必须在原数组上操作，不能拷贝额外的数组。尽量减少操作次数。
 */
public class LCMoveZeros {
    /**
     * 解题思路：
     * 1.最常想到的一种思路是，遍历数组，只要发现不为0的元素就将其按顺序放到一个新的数组中，然后在后面补0即可
     * 不过这个方法需要额外申请一块新数组的内存，不太好
     * 2.一种好的方式是，只操作原数组即可实现移动零，思路就是零和非零元素进行交换，即准备两个指针i和j，它们同时
     * 从0开始遍历数组，当走到0元素的时候指针j停止前进，i则继续前进，当i走到非零元素是就跟j所指的元素进行交换，
     * 这样就能够实现0移动到数组后面，并且非零元素还保持原有顺序不变
     * @param nums
     */
    public void moveZeros(int[] nums) {
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                int temp = 0;
                temp = nums[j];
                nums[j] = nums[i];
                nums[i] = temp;
                j++;
            }
        }
    }

    public static void main(String[] args) {
//        int[] nums = {0, 1, 0, 3, 12};
        int[] nums = {2, 1, 0, 3, 12};
        LCMoveZeros LCMoveZeros = new LCMoveZeros();
        LCMoveZeros.moveZeros(nums);
        System.out.println(nums);
    }
}
