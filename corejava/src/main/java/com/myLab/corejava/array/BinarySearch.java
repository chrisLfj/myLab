package com.myLab.corejava.array;

/**
 * 二分查找算法
 * 数组的强项在于随机查询，即事先知道元素在数组中所处的下标，根据下标可以立即在数组中找到该元素，时间复杂度为O(1)
 * 但是如果我们事先并不知道元素在数组中的下标，甚至我们都不确定元素是否存在于数组中，一般简单方法是轮询该数组，找到或者找不到指定的元素。时间复杂度为O(n)
 * 那有没有一种更加快速的方法，从数组中找到或者找不到某一个元素呢？可以考虑二分查找算法。
 * 该算法的前提是，要求是一组连续结构中(数组)，并且存放的元素必须是有序的，将数组分为两部分(比如左边为小，右边为大)，找到中间那个边界元素，每次都判断边界元素是不是要找的元素。
 * 如果是那就返回，如果不是那就比较大小，如果边界元素<要查找的元素，那就从右边部分继续找到其中间边界元素，重复这个步骤
 */
public class BinarySearch {
    private int[] sort = {1,3,6,7,8,10,19,25,38,39,56,57,68,110,119,125,138,143,156,167,178,210,219,225,238,243,600,617,628,1110,1119,1125,1138,1200};

    public void search(int[] sourceArray, int value){
        int startIndex = 0;
        int endIndex = sourceArray.length - 1;
        int findTimes = 0;
        while(true){
            findTimes++;
            if((endIndex - startIndex) > 1){
                int halfIndex = (startIndex + endIndex)/2;//两个数的中间数
                if(sourceArray[halfIndex] == value){
                    System.out.println("find target! use:" + findTimes +" times and the index is " + halfIndex);
                    return;
                }
                else if(sourceArray[halfIndex] < value)
                    startIndex = halfIndex;//halfIndex < value则从右边部分继续找
                else
                    endIndex = halfIndex;//否则就从左边部分继续找
            }else{
                if(sourceArray[startIndex] == value){
                    System.out.println("find target! use:" + findTimes +" times and the index is " + startIndex);
                    return;
                }
                else if(sourceArray[endIndex] == value){
                    System.out.println("find target! use:" + findTimes +" times and the index is " + endIndex);
                    return;
                }
                else{
                    System.out.println("do not exist! use:" + findTimes +" times");
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        BinarySearch bs = new BinarySearch();
        System.out.println(bs.sort.length);
        bs.search(bs.sort, 143);
    }
}
