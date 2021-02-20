package com.myLab.corejava.java8demo.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Stream API的几个小练习
 * (1) 找出2011年发生的所有交易，并按交易额排序（从低到高）。
 * (2) 交易员都在哪些不同的城市工作过？
 * (3) 查找所有来自于剑桥的交易员，并按姓名排序。
 * (4) 返回所有交易员的姓名字符串，按字母顺序排序。
 * (5) 有没有交易员是在米兰工作的？
 * (6) 打印生活在剑桥的交易员的所有交易额。
 * (7) 所有交易中，最高的交易额是多少？
 * (8) 找到交易额最小的交易。
 */
public class StreamPractise {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        //练习1
        List<Transaction> result1 = transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
//                .sorted(Comparator.comparing((t) -> t.getValue()))
                .collect(Collectors.toList());
        System.out.println(result1);

        //练习2
        List<String> result2 = transactions.stream()
                .map(t -> t.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());//或者直接使用collect(toSet())这样就不需要distinct()了
        System.out.println(result2);

        //练习3
        List<Trader> result3 = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(transaction -> transaction.getTrader())
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
//                .sorted((t1, t2) -> t1.getName().compareToIgnoreCase(t2.getName()))
                .collect(Collectors.toList());
        System.out.println(result3);

        //练习4
        String result4 = transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (n1, n2) -> n1 + n2);//将多个流合并为一个流，逐个拼接每个名字，得到一个将所有名字连接起来的字符串，但是这样的方式不够高效，因为每次拼接时都需要建立一个新的String对象
//                .collect(joining()) //可以使用这种更高效的方式来拼接，内部会用到StringBuilder
        System.out.println(result4);

        //练习5
        Boolean result5 = transactions.stream()
                .anyMatch(t -> t.getTrader().getCity().equalsIgnoreCase("Milan"));
        System.out.println(result5);

        //练习6
        Integer result6 = transactions.stream()
                .filter(t -> t.getTrader().getCity().equalsIgnoreCase("Cambridge"))
                .map(t -> t.getValue())
                .reduce((v1, v2) -> v1 + v2)
                .get();
        System.out.println(result6);

        //练习7
        Transaction result7 = transactions.stream()
                .max((t1,t2) -> t1.getValue() - t2.getValue())
                .get();
        System.out.println(result7);

        //练习8
        Transaction result8 = transactions.stream()
                .min((t1, t2) -> t1.getValue() - t2.getValue())
                .get();
        System.out.println(result8);
    }
}
