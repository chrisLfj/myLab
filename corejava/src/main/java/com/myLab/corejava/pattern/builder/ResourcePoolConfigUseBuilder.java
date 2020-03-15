package com.myLab.corejava.pattern.builder;

import org.springframework.util.StringUtils;

/**
 * 使用builder模式
 */
public class ResourcePoolConfigUseBuilder {
    private String name;
    private int maxTotal;
    private int maxIdle;
    private int minIdle;

    //私有化的构造方法，在这个例子中是可以的，因为调用它的Builder是静态内部类
    private ResourcePoolConfigUseBuilder(Builder builder){
        this.name = builder.name;
        this.maxTotal = builder.maxTotal;
        this.maxIdle = builder.maxIdle;
        this.minIdle = builder.minIdle;
    }
    //省略getter方法

    //设计一个静态内部类Builder用来搜集创建ResourcePoolConfigUseBuilder所需要的各种参数，然后封装参数之间的逻辑关系
    //也可以将Builder设计成独立的非内部类，比如ResourcePoolConfigBuilder
    public static class Builder{
        private static final int DEFAULT_MAX_TOTAL = 8;
        private static final int DEFAULT_MAX_IDLE = 8;
        private static final int DEFAULT_MIN_IDLE = 0;

        private String name;
        private int maxTotal = DEFAULT_MAX_TOTAL;
        private int maxIdle = DEFAULT_MAX_IDLE;
        private int minIdle = DEFAULT_MIN_IDLE;

        public ResourcePoolConfigUseBuilder build(){
            if(StringUtils.isEmpty(name))//name属性必须有且不能为空值，因此这里有一个校验逻辑
                throw new IllegalArgumentException("name should not be empty.");
            this.name = name;

            if(maxIdle > maxTotal){//属性之间的一些逻辑关系
                throw new IllegalArgumentException("something went wrong.");
            }

            if(minIdle > maxTotal || minIdle > maxIdle){
                throw new IllegalArgumentException("something went wrong.");
            }
            return new ResourcePoolConfigUseBuilder(this);
        }

        public Builder setName(String name) {
            if(StringUtils.isEmpty(name))
                throw new IllegalArgumentException("name should not be empty.");
            this.name = name;
            return this;
        }

        public Builder setMaxTotal(int maxTotal) {
            if(maxTotal <= 0)
                throw new IllegalArgumentException("maxTotal should be positive.");
            this.maxTotal = maxTotal;
            return this;
        }

        public Builder setMaxIdle(int maxIdle) {
            if(maxIdle <= 0)
                throw new IllegalArgumentException("maxIdle should be positive.");
            this.maxIdle = maxIdle;
            return this;
        }

        public Builder setMinIdle(int minIdle) {
            if(minIdle <= 0)
                throw new IllegalArgumentException("minIdle should be positive.");
            this.minIdle = minIdle;
            return this;
        }
    }

    public static void main(String[] args) {
        //使用示例
        ResourcePoolConfigUseBuilder config = new ResourcePoolConfigUseBuilder.Builder()
                .setName("test")
                .setMaxTotal(10)
                .setMaxIdle(10)
                .setMinIdle(0)
                .build();
    }
}
/*
上面代码是典型的建造者模式，通过为目标类创建一个建造者类，这个建造者类可以是目标类的静态内部类，也可以是一个独立的类。
建造者类为目标类的每个属性都提供了set方法(当然这个set方法可以在起名字时赋予更多的业务含义来提高代码的可读性)，通过这些方法使得建造者类可以搜集属性值，
最后在一个build方法中new一个目标类对象返回，在new之前就有机会再写更多的属性之间的逻辑关系。
建造者模式与工厂模式的区别在哪里？
归纳总结一下，就是工厂模式是用来创建不同但是相关类型的对象(继承同一父类或者接口的一组子对象)，有给定的参数来决定创建那种类型的对象，比如JsonPaser，XmlPaser，HtmlPaser，
而建造者模式则是用来创建一种拥有复杂关系的对象，属性和属性之间的逻辑关系比较多的对象，通过设置不同的可选参数，定制化的创建不同对象
 */