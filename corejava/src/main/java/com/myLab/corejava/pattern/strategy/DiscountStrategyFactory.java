package com.myLab.corejava.pattern.strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * 既然要把逻辑分支从if-else或switch中剥离出来，并且要消除冗长的if-else或switch代码，那就意味着需要将策略类的生成逻辑封装起来
 * 这个就是典型的工厂模式可以干的事情，因此策略模式中一般会包含一个策略工厂类
 * 当然这里并不是说if-else或者switch是代码的“坏味道”，毕竟作为语法来说实现很简单，逻辑不复杂的情况下可读性和可维护性也都不错，
 * 凡事有个度，分支逻辑并不复杂的时候，硬是用策略模式来去掉if-else，这也是一种过渡设计，毕竟策略模式会凭空增加额外的类。
 * 下面的策略工厂类提供了两种获得策略类的方式
 * 1.每个策略类都事先new出来，放在Map中，作为单例模式供使用方使用
 * 2.每个策略类的class类放在map中，每次使用时都要new一个新的策略类对象供使用方使用
 * 为什么要有这两种策略类的使用方式呢？
 * 一般情况下，策略类都是纯粹的算法实现，是一种面向过程的编程方式，一般是输入一个或多个参数，经过算法逻辑之后得到一个返回值
 * 这种情况下策略类没有状态，因此可以被共享使用，不需要每次都创建一个新的策略对象，用1这种方式就可以
 * 但是有些特殊情况下，策略类是有状态的，比如根据业务场景需要，每次获得策略对象都希望是一个新的对象，那就需要使用2这种方式了
 */
public class DiscountStrategyFactory {
    private static final Map<String, DiscountStrategy> strategies = new HashMap<>();
//    private static final Map<String, Class<? extends DiscountStrategy>> strategies = new HashMap<>();


    /**
     * 静态代码块中将所有的策略类都初始化到一个map数组中，key=type，value=策略类实例
     * 形成一个“字典表”结构，这样就可以使用“查表法”来根据不同的key获取不同的策略类实例了
     */
    static {
        strategies.put("normal", new NormalDiscountStrategy());
        strategies.put("groupon", new GrouponDiscountStrategy());
        strategies.put("promotion", new PromotionDiscountStrategy());

//        strategies.put("normal", NormalDiscountStrategy.class);
//        strategies.put("groupon", GrouponDiscountStrategy.class);
//        strategies.put("promotion", PromotionDiscountStrategy.class);
    }

    public static DiscountStrategy getDiscountStrategy(String type) {
        return strategies.get(type);
//        return strategies.get(type).newInstance();
    }
}
