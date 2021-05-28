package com.myLab.corejava.StringDemo;

public class StringDemo {

    public static void main(String[] args) {
        String a = "111";
        String a1 = a;
        String b = new String("111");
        String c = "111";
        String d = a.substring(1);

        System.out.println(a==a1);//a和a1地址相同，指向同一个String对象
        System.out.println(a.equals(a1));//a和a1内容也相同
        System.out.println(a==b);//a和b地址不同，说明是两个对象
        System.out.println(a.equals(b));//a和b的内容相同
        System.out.println(a==c);//a和c的地址相同，说明指向同一个String对象
        System.out.println(a.equals(c));//a和c内容也相同
        System.out.println(a==d);//a和d地址不同，说明是两个对象
    }
    /*
    说明：
    String a = "111"，这种写法会在堆中的常量池中存放String对象，它会先到常量池中查看是否存在“111”这个String对象，
    如果存在，这直接返回该对象的引用，如果没有发现则新建一个“111”对象放在常量池中，这也就意味着常量池中不可能存在重复的
    String对象。因此，上面示例中a和c变量其实是存放这一个对象的引用，指向常量池中的“111”这个String对象。
    而使用new关键字则会新建一个对象，因此，a和b所指向的对象是两个不同的对象，但是它们的内容是一样的，我们可以看一下equals方法
    的源码，它在比较String对象的时候就是将其中的char[]数组拿出来一个一个比较的。
    String类是不可变的，它使用final来修饰，意味着String类不可以被继承，final类中的所有成员方法都会被隐式的指定为final方法，
    成员变量则可以根据需要来决定是否用final修饰，final修饰的变量，在语法上会要求必须要初始化，即要么在声明变量时初始化赋值，
    要么就在所有的构造方法中为其初始化赋值String类中就有一个final修饰的char[]变量来存放字符串，那也就意味着这个char[]变量是不可变的，
    这里变量中存放的是对象的地址，所以所谓的变量不可变是指该变量不可以再指向别的地址。
    String类的很多操作方法，例如concat，subString，trim，replace等方法，通过查看它们的源码可以看到，这些方法最终都是创建一个新的String对象返回
    因此上面示例中a和d变量是指向两个不同的对象的。
    总结：
    Stirng对象被创建之后，它就是不可变的；任何对其变化性的操作，都会new一个新的String对象
     */
}
