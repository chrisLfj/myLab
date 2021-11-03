package com.myLab.corejava.java8demo;

public class Interview {

    // x > 0
// - < y < +
// x ^ y
    public double power(int x, int y) {
        if (y == 0) {
            return 1;
        }
        int z = 0;
        z = y > 0 ? y : -y;
        double result = x;
        for (int i = 1; i <= z - 1; i++) {
            result = multiX(x, result);
        }
        return y > 0 ? result : 1 / result;
    }

    public double multiX(int x, double result) {
        return result * x;
    }

    public static void main(String[] args) {
        Interview test = new Interview();
        System.out.println(test.power(2, -3));
    }
}
