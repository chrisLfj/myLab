package com.myLab.corejava.pattern.strategy;

/**
 * 在实际开发过程中，遇到逻辑分支比较多的情况下，每个逻辑分支都要编写不少的代码，这时如果使用if-else或switch-case来处理这种分支情况
 * 所有不同的逻辑分支代码都耦合在了一起，代码冗长，而且如果要新增或者修改一个逻辑分支的代码时，都会修改这些逻辑分支，很容易影响其他逻辑功能的稳定
 * 那有什么办法可以去掉这些if-else或者switch-case呢？
 * 使用策略模式！
 */
public class OrderServerWithoutStrategy {
    public double discount(String type){
        double discount = 0.0;
        if(type.equalsIgnoreCase("normal")){
            //普通订单折扣算法
        }
        else if(type.equalsIgnoreCase("groupon")){
            //团购订单折扣算法
        }
        else if(type.equalsIgnoreCase("promotion")){
            //促销订单折扣算法
        }
        return discount;
    }
}
