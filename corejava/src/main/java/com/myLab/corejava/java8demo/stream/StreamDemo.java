package com.myLab.corejava.java8demo.stream;

import java.util.*;
import java.util.stream.Collectors;

public class StreamDemo {
    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("potato", true, 100, Dish.Type.OTHER),
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
//                .skip(2)//跳过前两个卡路里大于300的元素，这两个元素不会放到最终的数组中，但是这两个元素依然会进入filtering和map的逻辑中
                .limit(3)//截短流，limit也可以用在set上，这时它不会以任何顺序排列
                .collect(Collectors.toList());//java8实战p102,每个中间操作都打印元素信息，从中可以看看流是如何处理元素的
        System.out.println(threeHighCaloricDishName);
        /*
        卡路里大于300的过滤结果如下：
        filtering potato
        filtering pork
        mapping pork
        filtering beef
        mapping beef
        filtering chicken
        mapping chicken
        [pork, beef, chicken]
        从结果来分析，流是按照数组顺序依次处理里面的元素，每个元素都会经过filtering，map，limit这三个组成的处理链条，
        我们可以发现，potato在经过filtering之后并没有进入map的逻辑中，那是因为它不符合过滤条件(卡路里小于300)
        还可以发现，并不是所有大于300卡路里的元素都处理，因为由limit(3)的存在，它实现了一个短路的逻辑。
         */

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

        //练习分组功能
        //1.将食物按类型分组，通过一个function作为分组函数，对数据进行分组然后再进行归总
        Map<Dish.Type, List<Dish>> dishesByType = dishes.stream().collect(Collectors.groupingBy(Dish::getType));
        System.out.println(dishesByType);
        //2.对于一些逻辑复杂的分组函数，可以灵活自定义实现如下，把食物按照“高低热量”来进行分组，不到400卡的是低热量食物，高于700卡的为高热量食物
        Map<String, List<Dish>> dishesByCaloricLevel = dishes.stream().collect(Collectors.groupingBy(dish -> {
            if (dish.getCalories() <= 400)
                return "DIET";
            else if (dish.getCalories() <= 700)
                return "NORMAL";
            else
                return "FAT";
        }));
        System.out.println("复杂分组0：" + dishesByCaloricLevel);
        //也可以将上述代码中的根据卡路里范围来分组的逻辑写在一个方法中，然后通过方法引用来实现，这样代码可读性更好
        dishesByCaloricLevel = dishes.stream().collect(Collectors.groupingBy(Dish::getCaloricLevel));//根据传入的dish实例来进行方法引用
        System.out.println("复杂分组1：" + dishesByCaloricLevel);
        //由这两种方法引用的写法来仔细体会一下被应用的方法与对应函数式接口中定义的方法有什么关联性
        dishesByCaloricLevel = dishes.stream().collect(Collectors.groupingBy(Trader::getCaloricLevel));//根据某一个类来进行方法应用，这时引用的方法需要是静态方法
        System.out.println("复杂分组2：" + dishesByCaloricLevel);
        //3.多级分组，也就是基于一次分组的结果再次进行分组，比如先对食物进行类型分组，然后在对每个类型分组再次进行热量的高低分组
        //groupingBy函数支持嵌套，即内层分组函数外再套外层分组函数，可以扩展任意层级的分组
        Map<Dish.Type, Map<String, List<Dish>>> dishesByTypeCaloricLevel = dishes.stream().collect(
                Collectors.groupingBy(Dish::getType, Collectors.groupingBy(dish -> {
                    if (dish.getCalories() <= 400)
                        return "DIET";
                    else if (dish.getCalories() <= 700)
                        return "NORMAL";
                    else
                        return "FAT";
                }))
        );
        System.out.println("多级分组：" + dishesByTypeCaloricLevel);
        //4.对收集器返回的结果类型再进行一次转换
        Map<Dish.Type, Dish> mostCaloricByType = dishes.stream().collect(
                Collectors.groupingBy(Dish::getType, Collectors.collectingAndThen(
                        Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)),Optional::get
                )));
    }
}
