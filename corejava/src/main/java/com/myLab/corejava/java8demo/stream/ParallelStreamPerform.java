package com.myLab.corejava.java8demo.stream;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * 这个类通过几个小程序来分析一下并行流的执行效率，从而能够更好的窥见并行流的正确使用方式
 */
public class ParallelStreamPerform {
    /**
     * 构建一个测试方法，接受一个求和函数和一个long，对该long参数应用求和函数10次，返回执行时间
     * @param adder 该函数可以对自然数进行求和并返回求和结果
     * @param n
     * @return
     */
    public static long measureSumPerf(Function<Long, Long> adder, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1000000;
            System.out.println("Result: " + sum);
            if (duration < fastest) {
                fastest = duration;//取十次中耗时最短的那一次
            }
        }
        return fastest;
    }

    /**
     * 顺序对前n个自然数求和
     * @param n
     * @return
     */
    public static long sequentialSum(long n) {
        //Stream.iterate方法的逻辑是通过迭代器的方式返回一个无限流，无限流元素类型则是iterate的参数类型
        //这个无限流的初始元素是seed，后续的元素则根据提供的函数来生成，比如下面代码生成的无限流第一个元素是1L，而第二个元素是第一个元素+1，第三个元素是第二个元素+1，依此类推
        //通过limit(n)方法对这个无限流截断，变成由n个元素组成的有限流
        //然后再通过reduce规约的方式依次求和
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .reduce(0L, Long::sum);
    }

    /**
     * java8之前对前n个自然数求和的典型写法，通过for循环依次累加
     * @param n
     * @return
     */
    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 1; i <= n; i++) {
            result += i;
        }
        return result;
    }

    /**
     * 使用java8 并行流方式求和，也就是在顺序求和基础上使用了parallel方法
     * @param n
     * @return
     */
    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel()
                .reduce(0L, Long::sum);
    }

    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n).reduce(0L, Long::sum);
    }

    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
    }
    public static void main(String[] args) {
        System.out.println("Sequential sum done in: ");
        //采用方法引用这种方式使用函数式接口，它等价于这种写法：
        // (t->ParallelStreamPerform.sequentialSum())
        //使用方法引用的秘诀是，你要引用的方法签名(参数以及返回值)要与函数式接口中定义的方法签名一致
        //例如下面的代码中，引用的sequentialSum方法签名与Function中的apply方法签名一致
        long seqDuration = measureSumPerf(ParallelStreamPerform::sequentialSum, 10000000);
        System.out.println("顺序流求和耗时：" + seqDuration);
        long iterDuration = measureSumPerf(ParallelStreamPerform::iterativeSum, 10000000);
        System.out.println("循环求和耗时：" + iterDuration);
        long parallelDuration = measureSumPerf(ParallelStreamPerform::parallelSum, 10000000);
        System.out.println("并行流求和耗时：" + parallelDuration);
        long rangedSumDuration = measureSumPerf(ParallelStreamPerform::rangedSum, 10000000);
        System.out.println("特定数值流顺序求和耗时：" + rangedSumDuration);
        long parallelRangedSumDuration = measureSumPerf(ParallelStreamPerform::parallelRangedSum, 10000000);
        System.out.println("特定数值流并行求和耗时：" + parallelRangedSumDuration);
    }
}
/*
顺序流求和耗时：105ms
循环求和耗时：3ms
并行流求和耗时：115ms
特定数值流顺序求和耗时：4
特定数值流并行求和耗时：2

从结果上来看，前三个结果中流求和耗时比原始的求和耗时高好多，甚至使用并行流的耗时更差，
后面调整了一下流的数据结构之后，效率才真正的体现出来这是为什么呢
原因有二：
1.由于使用了iterate方式生成流，那并行拆分任务时很难把iterate分成多个独立任务并执行
2.iterate生成的是装箱的对象，必须拆箱成数字才能求和
这意味着，在这个特定情况下，归纳进程不是像图7-1那样进行的；整张数字列表在归纳过
程开始时没有准备好，因而无法有效地把流拆分为小块来并行处理。把流标记成并行，你其实是
给顺序处理增加了开销，它还要把每次求和操作分到一个不同的线程上。
这就说明了并行编程可能很复杂，有时候甚至有点违反直觉。如果用得不对（比如采用了一
个不易并行化的操作，如iterate），它甚至可能让程序的整体性能更差，所以在调用那个看似
神奇的parallel操作时，了解背后到底发生了什么是很有必要的。
从上面的分析来看，原始的for循环求和方式由于不需要拆箱，同时也没有“别扭的”拆分合并过程
它在效率上反而表现不错。
所以我们使用流的过程中一定要多加思考，不能随便运用，应该从几方面考虑，
1，是否可以避免诸如拆箱装箱这种无谓的损耗？
2，数据结构是否方便拆分和并行化，很重要的一点是要保证在内核中并行执行工作的时间比在内核之间传递数据的时间长。
基于以上考虑，对代码做了一些改进，主要是对生成流的方式做了优化，然后再使用并行流求和，效率就会大幅提升
 */
