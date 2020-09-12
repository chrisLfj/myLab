package com.myLab.corejava.pattern.strategy;

/**
 * 使用了策略模式之后，if-else分支被去掉，代码变得简洁易扩展
 */
public class OrderServerUseStrategy {
    public double discount(String type){
        double price = 0.0;
        DiscountStrategy discountStrategy = DiscountStrategyFactory.getDiscountStrategy(type);
        return discountStrategy.calDiscount(price);
    }
}
