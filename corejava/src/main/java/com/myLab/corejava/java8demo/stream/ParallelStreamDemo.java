package com.myLab.corejava.java8demo.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;

/**
 * Stream不是集合元素，它不是数据结构，而是有关算法和计算的。它更像是一个更高级别的Iterator
 * Iterator只能显式的一个一个遍历元素并对其进行操作；而stream，用户只要给出需要对其包含的元素
 * 进行什么操作即可，比如“过滤掉长度大于10的字符串”、“获取每个字符串的首字母”等等，Stream会
 * 隐式的在内部进行遍历，做出相应的数据转换。
 * Stream就如同一个迭代器Iterator，单向，不可往复，数据只能遍历一次，遍历过一次后即用尽了，就
 * 好比流水从面前流过，一去不复返，取名为Stream颇有诗意。
 * Stream虽然只可遍历一次，但是在这个过程中却可以对每条数据进行多个逻辑处理的叠加，同时，与Iterator
 * 不同的是，Stream可以并行化操作，而迭代器只能命令式的，串行化操作。Stream可以将数据分成多段，
 * 其中每一段都在不同的线程中进行处理，然后将结果一起输出。Stream的并行操作依赖于Java7的Fork/Join
 * 框架来拆分任务/合并结果，以此加速处理过程。
 */
public class ParallelStreamDemo {
    public static void main(String[] args) throws InterruptedException {
        //构造一个10000个元素的集合
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < 200000; i++)
            list.add(i);
        //统计ParallelStream有多少线程参与运行
        Set<Thread> threadSet = new CopyOnWriteArraySet<>();
        //并行执行
        list.parallelStream().forEach(integer -> {
            Thread worker = Thread.currentThread();//获得worker线程
            threadSet.add(worker);
            if(integer%1000 == 0)
                System.out.println("list:" + integer + "-" + worker.getName());
        });
        System.out.println("threadSet一共有" + threadSet.size() + "个线程");
        for(Thread t: threadSet)
            System.out.println(t.getName());
        System.out.println("系统一共有" + Runtime.getRuntime().availableProcessors() + "个CPU");

        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for(int i = 0; i < 100000; i++){
            list1.add(i);
            list2.add(i);
        }
        Set<Thread> threadSetTwo = new CopyOnWriteArraySet<>();
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Thread threadA = new Thread(() -> {
           list1.parallelStream().forEach(integer -> {
               Thread thread = Thread.currentThread();
               threadSetTwo.add(thread);
               if(integer%1000 == 0)
                   System.out.println("list1:" + integer + "-" + thread.getName());
           });
           countDownLatch.countDown();
        }, "threadA");
        Thread threadB = new Thread(() -> {
            list2.parallelStream().forEach(integer -> {
                Thread thread = Thread.currentThread();
                threadSetTwo.add(thread);
                if(integer%1000 == 0)
                    System.out.println("list2:" + integer + "-" + thread.getName());
            });
            countDownLatch.countDown();
        }, "threadB");

        threadA.start();
        threadB.start();
        countDownLatch.await();
        System.out.println("threadSetTwo一共有" + threadSetTwo.size() + "个线程");

        System.out.println("---------------------------");
        System.out.println(threadSet);
        System.out.println(threadSetTwo);
        System.out.println("---------------------------");
        threadSetTwo.addAll(threadSet);
        System.out.println(threadSetTwo);
        System.out.println("threadSetTwo一共有" + threadSetTwo.size() + "个线程");
    }
}
/*
1.分治法，Fork Join主要是使用了分治法来解决问题，主要思路是将一个大任务拆分成很多个小任务，利用有限的线程来分别执行这些小任务，
同时还可以对这些小任务进行一些父子关系的建立，比如一个任务只有等到其所有子任务完成之后才可以执行。
2.工作窃取，Fork Join最核心的地方就是利用现代硬件设备多核，程序执行过程中cpu可能会出现空闲，利用好空闲cpu会显著提升性能。
工作窃取算法是指某个线程从其它线程的任务队列中窃取任务来执行。Fork Join将一个大任务拆分成若干互不依赖的子任务，为了尽量避免线程
之间为了获取任务而产生的并发冲突，于是就将这些子任务放到不同的队列中，每个队列中的任务由一个单独的线程来执行，线程和队列一一对应，
如果线程A率先完成其队列中的任务，而线程B的队列中还有任务没有执行完，这时线程A可以去线程B的队列中窃取一个任务来执行，此时线程A和B
就存在访问一个队列的情况，那为了尽可能的避免线程A和B获取任务时的竞争，通常会使用双端队列，正常线程执行自己队列的任务时从双端队列
的头部拿任务执行，当窃取其它线程任务的时候，则从被窃取线程的队列尾部拿任务，但是如果队列中只剩下了一个任务时，还是有冲突的可能。
3.ForkJoinPool，java8为Fork Join线程设计了一个通用线程池，这个线程池用来处理哪些没有显式提交到任何线程池的任务，它拥有的默认
线程数量等于运行计算机上的处理器数量，例如一个4核心机器，在初始化ForkJoinPool时，会有3个“ForkJoinPool.commonPool-worker-”开头
的线程创建出来(具体参考ForkJoinPool源码makeCommonPool()方法)，同时还会有一个发起者线程也会参与进来，比如上例代码中在main线程中
发起的list.parallelStream().forEach(...)方法，那在执行的过程中main线程也会参与任务的执行，所以总共加起来是4个线程在并行执行。
另外，可以通过-Djava.util.concurrent.ForkJoinPool.common.parallelism=N （N为线程数量），具体实践下来发现，即使这个parallelism
设置成了1，实际在执行时线程池中还是会有2个线程在执行任务，其中一个线程呢就是发起者线程。
4.parallelStream的陷阱，这里要注意的是parallelStream共享了一个Fork Join线程池，这样会带来一个什么问题呢，如上面的示例代码，
有list1和list2，然后分别使用了两个线程,threadA和threadB来发起parallelStream来处理list1和list2，这时要注意的是，fork join线程池
中的线程并不会翻倍，它还是原先那些线程（会多两个发起者线程threadA和threadB），它们承担起了处理list1和list2的双重任务，这就给我们带来
思考，那就是在使用parallelStream的时候要注意避免过多的任务积压，在一些要求低延迟场景下，可能会带来一些隐藏的性能问题。

代码执行结果解读：
第一段解读
list2:79000-ForkJoinPool.commonPool-worker-2
list2:80000-ForkJoinPool.commonPool-worker-2
list2:81000-ForkJoinPool.commonPool-worker-2
list1:13000-ForkJoinPool.commonPool-worker-2
list1:14000-ForkJoinPool.commonPool-worker-2
list1:15000-ForkJoinPool.commonPool-worker-2
list1:16000-ForkJoinPool.commonPool-worker-2
由上面这段执行结果来看，ForkJoinPool.commonPool-worker-2的执行队列中既有list2的任务也有list1的任务，这些任务都加入到一个队列中了

第二段解读
---------------------------
[Thread[main,5,main]]
[Thread[threadB,5,], Thread[ForkJoinPool.commonPool-worker-3,5,main], Thread[threadA,5,], Thread[ForkJoinPool.commonPool-worker-2,5,main], Thread[ForkJoinPool.commonPool-worker-1,5,main]]
---------------------------
上面第一行，有时程序执行的快，可能只需要一个发起者线程就够了，有时都没有用到ForkJoinPool.commonPool-worker开头的线程来执行
第二行，可以看到连续两次parallelStream调用之后，除了三个ForkJoinPool.commonPool-worker开头的线程之外，还有两个发起者线程也加入到线程池中了

 */
