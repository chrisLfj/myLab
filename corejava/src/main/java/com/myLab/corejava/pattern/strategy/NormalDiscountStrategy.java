package com.myLab.corejava.pattern.strategy;

public class NormalDiscountStrategy implements DiscountStrategy{
    /*
    每个策略类也可以有自己的状态和属性
     */

    /**
     * 提供自己的实现逻辑
     * @param price
     * @return
     */
    @Override
    public double calDiscount(double price) {
        return 0;
    }
}
