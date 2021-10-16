package com.myLab.corejava.lab;

/**
 * 浮点数丢失精度的测试，浮点数在计算机中用二进制来表示，是按照规范来的，它会存在一定的精度丢失
 * 如果想保证精度不丢失可以使用dicimal，它可以保证精度不丢失
 * 如果想了解细节，可以查阅相关资料，极客时间的结算及组成原理课程中也有介绍
 */
public class FloatPointLostPrecisionTest {
    public static void main(String[] args) {
        double f = 24690000.11;
        System.out.println(f-0.111);//正常计算的结果应该是2.4689999999E7，而程序运行的结果却是2.4689999998999998E7，存在精度的丢失
    }
}
