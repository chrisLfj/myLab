package com.myLab.corejava.pattern.builder;

import org.springframework.util.StringUtils;

/**
 * 一个普通的类，没有使用builder模式
 */
public class ResourcePoolConfigWithoutBuilder {
    private static final int DEFAULT_MAX_TOTAL = 8;
    private static final int DEFAULT_MAX_IDLE = 8;
    private static final int DEFAULT_MIN_IDLE = 0;

    private String name;
    private int maxTotal = DEFAULT_MAX_TOTAL;
    private int maxIdle = DEFAULT_MAX_IDLE;
    private int minIdle = DEFAULT_MIN_IDLE;

    public ResourcePoolConfigWithoutBuilder(String name, Integer maxTotal, Integer maxIdle, Integer minIdle) {
        if(StringUtils.isEmpty(name))//name属性必须要有，因此这里有一个校验逻辑
            throw new IllegalArgumentException("name should not be empty.");
        this.name = name;

        if(maxTotal!=null){//maxTotal属性可以没有，但是如果有的话则不可以为负数
            if(maxTotal <= 0)
                throw new IllegalArgumentException("maxTotal should be positive.");
            this.maxTotal = maxTotal;
        }

        if(maxIdle!=null){
            if(maxIdle <= 0)
                throw new IllegalArgumentException("maxIdle should be positive.");
            this.maxIdle = maxIdle;
        }

        if(minIdle!=null){
            if(minIdle <= 0)
                throw new IllegalArgumentException("minIdle should be positive.");
            this.minIdle = minIdle;
        }

    }

    public ResourcePoolConfigWithoutBuilder(String name){
        this(name, null, null, null);
    }

    public ResourcePoolConfigWithoutBuilder(String name, Integer maxTotal){
        this(name, maxTotal, null, null);
    }

    public ResourcePoolConfigWithoutBuilder(String name, Integer maxTotal, Integer maxIdle){
        this(name, maxTotal, maxIdle, null);
    }

}
/*
以上面代码为例，类中有四个参数，并且每个参数在赋值时都有逻辑，参数个数如果不多的情况下可以通过上面代码的方式通过不同的构造函数来满足创建需求，
或者通过简单工厂方式，将逻辑封装到一个工厂类中。
但是如果参数个数逐渐增多，变成8个，10个甚至更多，那构造函数的入参就会非常的多，在使用的时候容易搞错顺序，出现bug，难维护；同时，赋值的逻辑也会变得很长，可读性变差
当然也有一种方式可以解决，那就是通过set方法，每个属性都有自己的set方法，将属性的校验逻辑放到set方法中，同时将必须要有的属性放到构造函数中，在创建该类对象的时候
先通过构造方法将必须要传入的属性传入，然后视情况决定该调用哪些set方法，但是从java封装特性来说，暴露set方法显然是违反封装特性的，因为任何地方都可以通过set方法更改对象里的值。
但是如果在创建对象时还需要一些校验需求，比如说属性之间必须满足一定的逻辑关系才算创建成功，比如要创建一个长方形，那一个边必须要比另一个边要长，才算创建成功，
这时我们就会发现，使用上面的几种创建对象的方式都没有地方写这部分逻辑，当然在构造函数里写也可以，但是那样又需要把参数都传入一个构造函数中，解决不了参数增多带来的问题。
这时我们就可以考虑使用“建造者模式”来解决问题了

 */