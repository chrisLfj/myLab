package com.myLab.corejava.java8demo.lambda;

import com.myLab.corejava.java8demo.stream.Dish;

import java.util.function.Consumer;

public class LambdaDemo {

    public static void process(Runnable runnable) {
        runnable.run();
    }

    public void testInvokeMethod(Consumer<Dish> consumer) {
        Dish dish = new Dish("pork", false, 800, Dish.Type.MEAT);
        consumer.accept(dish);
    }

    public static void main(String[] args) {
        Runnable r1 = () -> System.out.println("Hello World 1");
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World 2");
            }
        };
        process(r1);
        process(r2);
        process(() -> System.out.println("Hello World 3"));

        LambdaDemo lambdaDemo = new LambdaDemo();
        lambdaDemo.testInvokeMethod(Dish::getCaloricLevel1);
    }
}
