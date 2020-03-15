package com.myLab.corejava.array;

import java.util.Arrays;
import java.util.Random;

public class IcdCream {
    private static Random rand = new Random(47);
    static final String[] FLAVORS = {"Chocolate","Strawberry","Vanilla Fudge Swirl","Mint Chip","Mocha Almond Fudge","Rum Raisin","Praline Cream","Mud Pie"};

    /**
     * 该方法是从FLAVORS数组中随机取出n个值，并且n个值中不存在重复的
     * @param n
     * @return
     */
    public static String[] flavorSet(int n){
        if(n > FLAVORS.length)
            throw new IllegalArgumentException("Set too big");
        String[] results = new String[n];
        boolean[] picked = new boolean[FLAVORS.length];//boolean初始化是false
        for(int i = 0; i < n; i++){
            int t;
            do {
                t = rand.nextInt(FLAVORS.length);
            }while (picked[t]);//picked[t]=true就再次取随机数，直到picked[t]=false跳出循环
            results[i] = FLAVORS[t];
            picked[t] = true;//记录t被取过
        }
        return results;
    }

    public static void main(String[] args) {
        for(int i = 0; i < 7; i++)
            System.out.println(Arrays.toString(flavorSet(3)));//Arrays用来操作数组的一个工具类
    }

}
