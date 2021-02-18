package com.myLab.corejava.java8demo.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamDemo {
    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH)
        );
//        List<String> threeHighCaloricDishName = menu.stream().filter(d -> d.getCalories() > 300).map(Dish::getName).limit(3).collect(Collectors.toList());
        List<String> threeHighCaloricDishName = menu.stream()
                .filter(d -> {
                    System.out.println("filtering " + d.getName());
                    return d.getCalories() > 300;})
                .map(d -> {
                    System.out.println("mapping " + d.getName());
                    return d.getName();
                })
                .limit(3)//截短流，limit也可以用在set上，这时它不会以任何顺序排列
                .collect(Collectors.toList());//java8实战p102,每个中间操作都打印元素信息，从中可以看看流是如何处理元素的
        System.out.println(threeHighCaloricDishName);

        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()//去除重复值，根据hashcode和equals方法实现的
                .forEach(System.out::println);

        List<Dish> dishes = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(2)//跳过元素
                .collect(Collectors.toList());
        System.out.println(dishes);

        List<String> words = Arrays.asList("Java 8", "Lambda", "In", "Action");
        List<Integer> wordLengths = words.stream().map(String::length).collect(Collectors.toList());//采用map对每个元素应用一个函数输出特定类型的结果

        List<String> combineWords = words.stream()
                .map(w -> w.split(""))//words集合中的每个单词都会映射成一个由其字母构成的数组，但是每个单词是返回一个流的
                .flatMap(Arrays::stream)//flatmap则会将多个单词多个流合并到一起成为一个流，最终也就是返回所有单词的字母数组
                .distinct()
                .collect(Collectors.toList());
    }
}
