package com.myLab.corejava.reflectDemo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class TestReflection {
    public static void main(String[] args) throws Exception {
        //反射功能是针对class类对象来说的，要使用反射首先要获取class对象
        Class<?> clazz = null;
        //方式一
        clazz = Demo.class;
        //方式二
        Object obj = new Demo();
        clazz = obj.getClass();
        //方式三，需处理类不存在异常ClassNotFoundException
        clazz = Class.forName("com.myLab.corejava.reflectDemo.Demo");
        //方式四，通过类加载器根据类路径来加载字节码生成class类对象，需处理类不存在异常ClassNotFoundException
        clazz = Service.class.getClassLoader().loadClass("com.myLab.corejava.reflectDemo.Demo");

        System.out.println(clazz.getName());//获取类名，全路径
        System.out.println(clazz.getSimpleName());//只获取类名
        System.out.println(Modifier.isPublic(clazz.getModifiers()));//判断类修饰符，是多个修饰符常量按位与的结果
        //获取对象实例
        obj = clazz.newInstance();//调用默认构造函数构造一个实例对象
        Constructor<?> cons = clazz.getConstructor(String.class);//获取一个入参为String的构造器
        obj = cons.newInstance("liufj");
        //获取所有构造函数
        Constructor<?>[] consAttr = clazz.getConstructors();
        for (Constructor<?> c : consAttr) {
            System.out.println(c.toString());
        }

        //获取名为key的成员变量反射对象
        Field key = clazz.getDeclaredField("key");
        //由于成员变量key的修饰符为private代表私有变量，因此需要设置为允许访问，才能读取它的值，否则会抛出异常
        key.setAccessible(true);
        System.out.println(key.get(obj));
        //而且还可以设置这个私有变量的值
        key.set(obj, "liuyq");
        System.out.println(key.get(obj));
        //修改静态成员变量的值
        Field code = clazz.getDeclaredField("code");
        code.set(null, "what");//注意这里传入的实例是null，因为静态变量是类变量，它不需要对应某个对象
        System.out.println(Demo.code);

        //获取方法的反射对象，并通过反射方式调用方法，如果方法有参数的话一定要传入参数类对象，否则会抛出NoSuchMethodException
        Method setKey = clazz.getMethod("setKey", String.class);
        setKey.invoke(obj, "liufj");
        Method getKey = clazz.getMethod("getKey");
        System.out.println(getKey.invoke(obj));

        //获取所有成员变量
        Field[] fields = clazz.getFields();
        for (Field f : fields) {
            System.out.println(Modifier.isPublic(f.getModifiers()));
            System.out.println(" ");
            System.out.println(Modifier.isStatic(f.getModifiers()));
            System.out.println(" ");
            System.out.println(f.getName());
        }

        //反射的方式调用一个对象中的方法
        //一般的方法是，传入值，这个值可以是基本类型也可以是引用类型，然后在方法体内对值进行处理
        //反射给我们提供的能力是，它改变了传统的对象，属性，方法的调用方式，同时可以允许你将方法，属性，对象作为值传入另外的方法中
        //方法对应Method类实例，属性对应Field类实例，对象对应Object类实例，只要得到了实例就意味着可以作为参数值传入某个通用方法，这个通用方法则可以是操作对象内方法和属性的一个抽象
        Method process = clazz.getMethod("process", int.class);
        process.invoke(obj, 1);
        //反射方式调用静态方法
        Method main = clazz.getMethod("main", String[].class);
        System.out.println(main.getReturnType());
        System.out.println(main.getName());
        //注意这里要用Object，如果这样写String[] p1 = new String[]{"hello ","world!"}; 那将p1传入invoke方法时，会认为是两个string参数，然后就会抛出参数个数错误异常。
        Object p1 = new String[]{"hello ","world!"};
        main.invoke(null, p1);

        //isPrimitive方法用于检查class对象是否为基本类型
        System.out.println(String.class.isPrimitive()); //false
        System.out.println(int.class.isPrimitive()); //true
        System.out.println(Integer.class.isPrimitive()); //false
        System.out.println(int[].class.isPrimitive()); //false，数组不是基本类型
        //基本类型与其对应的包装类的class对象并非同一个
        System.out.println(int.class == Integer.class); //false
        System.out.println(int.class == Integer.TYPE); //true
        //int[]与Integer[]在元素类型上不同，因此用“==”无法通过编译
        System.out.println(int[].class.equals(Integer[].class)); //false
        //判断class对象是否为数组类型
        System.out.println(int[].class.isArray()); //true
    }
}
