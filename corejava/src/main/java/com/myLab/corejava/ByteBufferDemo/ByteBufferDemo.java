package com.myLab.corejava.ByteBufferDemo;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * ByteBuffer常被用于NIO中的数据缓冲区，NIO是面向buffer来读写数据的，可以使用它在jvm堆内存中或者操作系统的内存中分配一块内存空间存放byte数组
 * 这个byte数组作为ByteBufer的一个成员变量存在。ByteBuffer在使用时常被定义成static final静态常量，在声明时即初始化，这样做的好处是将其作为一个
 * 缓存资源重复利用，例如在使用NIO读取一个大文件时，我们每5000个字节一次循环从流中读取数据，如果每次循环都申请一个5000字节的ByteBuffer缓存的话，
 * 那jvm对内存可能很快就被占满，进而触发gc，会严重影响程序性能。因此我们将ByteBuffer作为静态常量，初始化5000个字节，循环一次读取5000个字节存入其中，
 * 处理完之后调用其clear方法即可将内容清空，然后下个循环可以重复使用，这样就只需要重复使用5000个字节的数组即可，内存占用也只会有5000字节。
 *
 * 有几个关键的值position，offset，limit，capacity，mark，length(这个length不代表数组长度)
 * 在创建出一个ByteBuffer后，这几个值的初始状态是：position=offset，offset=0(没有设别指定的话，默认为0)，limit=offset+length，capacity=数组长度，mark=-1
 * position代表当前可以读写的数组下标，每次读写之后position都会+1
 * offset代表偏移量，如果创建ByteBuffer时传入的offset>0，那position=offset，表示初始从偏移量位置开始读写
 * limit则代表可以读写的限制下标，如果position超过limit则会抛出异常，在创建ByteBuffer时，如果limit>数组长度，也会抛出异常
 * mark标识，调用mark()方法打标，mark=position，记录下当前position位置，这样可以在后续过程中调用reset方法让position回到mark位置
 */
public class ByteBufferDemo {
    public void byteBuffrDemo() {
        String str = "this is a byte buffer demo!";
        //ByteBuffer的allocate方法和wrap方法都是在jvm中分配空间，这个空间可以被jvm进行垃圾回收
        //ByteBuffer的allocateDirect方法则是通过操作系统来创建内存空间作为缓冲区，这是一种直接缓冲区，跟jvm中的内存空间相比，直接缓冲区与操作系统耦合更好，
        //即省去了内核空间跟jvm进程空间数据拷贝的步骤，能进一步提高I/O效率，副作用就是创建直接缓冲区的系统开销较大，且这个空间无法被jvm自动回收
        ByteBuffer bb = ByteBuffer.wrap(str.getBytes(StandardCharsets.UTF_8));
        for (int i = 0; i < bb.limit(); i++) {
            System.out.println(bb.get(i));//通过get(index)方法获取指定下标的值，这种读取不会改变position的值。
        }
        for (int i = 0; i < bb.limit(); i++) {
            System.out.println(bb.get());//通过get()方法来从position位置开始读取数据，且position会+1
        }
        /*
        下面两行代码，只是想加一个字符a，但是会抛出BufferOverflowException异常
        这是因为经过上面的循环get之后，position已经等于limit了，这时put方法再向数组中写入数据，会从当前position位置开始写入，position会增加，
        会导致position超出limit，但是数据还未写完，导致抛出异常
         */
//        String str1 = "a";
//        bb.put(str1.getBytes(StandardCharsets.UTF_8));

        /*
        flip方法的作用是limit=position，position=0，mark=-1，这样整个数组又可以重新处于可读写状态了。
        另外还提供了一些方法来灵活操作limit和position，方便程序灵活的对数组从不同的位置进行读写，例如bb.limit(int newlimit)方法可以修改limit的值，
        但是传入的limit值不允许大于capacity值，这样会导致报错；bb.position(int newPosition)方法可以修改position的值，这样程序可以从指定位置开始读写，
        当然newposition值也是有限制的
         */
        bb.flip();

        /*
        下面代码是想验证，如果只读取了一部分内容，然后调用flip方法会怎样？
        结论是：本来ByteBuffer的长度是27个字节(limit=27),for循环读出5个字节之后，这时position=5，
        调用了flip方法之后的情况是，limit=position=5，position=0，mark=-1，这就意味着可以从下标0开始读写数据，但是只能读写5个字节，因为limit=5。
        这时往这个ByteBuffer中put 6个字母的字符串，就会抛出BufferOverflowException异常
         */
        for (int i = 0; i < 5; i++) {
            System.out.println(bb.get());
        }
        bb.flip();
        String str1 = "abcdef";//字符串长度大于5会抛出异常
        bb.put(str1.getBytes(StandardCharsets.UTF_8));

        /*
        下面代码验证offset对ByteBuffer的影响，字符长度是3，偏移量1，length如果=3的话，limit=offset+length=4大于字符长度，也会抛出异常
         */
        String str2 = "abc";
        ByteBuffer bb1 = ByteBuffer.wrap(str2.getBytes(StandardCharsets.UTF_8), 1, 2);//这样初始化的结果是position=offset=1从offset即开始读写，limit=position+offset=1+2=3
        System.out.println(bb1.get());//position+1=2
        System.out.println(bb1.get());//position+1=3
        System.out.println(bb1.get());//position+1>3抛出异常
        System.out.println(bb1.get());
    }

}
