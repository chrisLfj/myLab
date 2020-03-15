package com.myLab.corejava.codeblockrun;

public class StaticDemo {
	
	static int i = 1; //静态变量存到静态区域
	
	static void speak(){//静态函数存到静态区域，调用时运行
		System.out.println("aaa");
	}
	
	static {//静态代码块，加载时运行
		i = i+3;
		System.out.println(i);
	}
	
	public StaticDemo(){//构造方法，初始化时执行
		i = i+5;
		System.out.println(i);
	}
	
	public static void main(String[] args){
		Foo foo1 = Child.foo1;
		System.out.println(foo1.str);
		System.out.println("------break line------");
		new Child();
		System.out.println("------break line------");
		new Child();//第二次实例化时不会再运行静态变量和静态代码块了
		System.out.println("------break line------");
		StaticDemo.speak();
		StaticDemo sd = new StaticDemo();
	}
	
//  运行结果:
//	4         表明会从main方法所在类开始加载，所以会先执行StaticDemo的静态代码块
//	Parent's static parameter
//	Parent's static code block 1
//	Parent's static code block 2
//	Child's static parameter
//	Child's static code block 1
//	Child's static code block 2
//	Child's static parameter
//			------break line------
//	Parent's parameter
//	Parent's non-static code block
//	Parent's constructor
//	Child's parameter
//	Child's non-static code block
//	Child's constructor
//			------break line------
//	Parent's parameter
//	Parent's non-static code block
//	Parent's constructor
//	Child's parameter
//	Child's non-static code block
//	Child's constructor
//			------break line------
//	aaa    静态函数在调用时执行
//	9           构造函数执行
	
//  结论：
//  第一个break line之前，其实只是执行了一句Foo foo1 = Child.foo1;
//  这是并没有实例化，只是加载了Child但Parent是Child的父类，因此先加载Parent再加载Child
//  在加载过程中初始化静态变量，执行静态代码块，如果存在多个静态代码块则按照声明顺序执行
//
//	第一个和第二个break line之间，执行了一句new Child();
//  类在实例化时会先加载，由于类已经在之前被加载过了，因此这里就不会再加载了，也就不会再次运行静态代码块和静态变量了
//	这也就是为什么说类的静态变量，静态代码块只会初始化或执行一次的原因。
//	实例化的过程会先初始化类成员变量(非static)，然后执行代码块，最后是构造函数
//   父子类的代码块执行顺序如下:
//1、父类静态变量和静态代码块（先声明的先执行）；
//
//2、子类静态变量和静态代码块（先声明的先执行）；
//
//3、父类的变量和代码块（先声明的先执行）；
//
//4、父类的构造函数；
//
//5、子类的变量和代码块（先声明的先执行）；
//
//6、子类的构造函数。
}
