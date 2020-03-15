package com.myLab.corejava.innerclass;

public class InnerClassTest {
	public int outField1 = 1;
	protected int outField2 = 2;
	int outField3 = 3;
	private int outField4 = 4;
	public int sameName = 100;
	private ActionListener al;
	public static String outField5 = "外部类的静态变量";
	
	public InnerClassA getInnerInstance(){
		return new InnerClassA();
	}
	
	public NoNameInnerClassInterface getNoNameInnerClass(final int number1, int number2){
		return new NoNameInnerClassInterface(){
			public int getNumber() {
				//匿名内部类中获得外部方法中的变量，外部方法中需要传入匿名内部类中的变量必须是final修饰的，
				//通过查看字节码文件，会发现编译器编译内部类后，匿名内部类中会拥有外部方法变量的拷贝，为了保证拷贝和原变量之间的一致性问题，要求变量本身不可变，因此要用final来修饰。
				return number1 + 3;
//				return number2 + 3;报错，因为number2不是用final修饰的
			}
		};
	}
	
	public ActionListener getAl() {
		return al;
	}

	public void setAl(ActionListener al) {
		this.al = al;
	}

	//普通内部类
	class InnerClassA{
		public int field1 = 5;
		protected int field2 = 6;
		int field3 = 7;
		private int field4 = 8;	
		public int sameName = 200;
//		public static int filed5 = 9;普通内部类中不可以有static变量
		
		public void printOuterFields(){
			//内部类可以访问外部类的所有成员变量，不管是公有还是私有的都可以访问
			System.out.println("外部类outFiled1字段值为:" + outField1);
			System.out.println("外部类outFiled2字段值为:" + outField2);
			System.out.println("外部类outFiled3字段值为:" + outField3);
			System.out.println("外部类outFiled4字段值为:" + outField4);
		}
		
		public void printFields(){
			System.out.println("内部类field1字段值为:" + field1);
			System.out.println("内部类field2字段值为:" + field2);
			System.out.println("内部类field3字段值为:" + field3);
			System.out.println("内部类field4字段值为:" + field4);
			//如果内部类中存在与外部类重名的变量时，默认访问的是内部类的变量
			System.out.println("内部类访问内部类重名字段值为:" + sameName);
			//访问外部类重名变量，内部类中使用OuterClass.this来获得外部类的引用
			System.out.println("内部类访问外部类重名字段值为:" + InnerClassTest.this.sameName);
		}
	}
	
	//匿名内部类必须要事先存在的
	interface NoNameInnerClassInterface{
		int getNumber();
	}
	
	//与普通内部类不同的是，静态内部类在编译后没有外部类的引用，它不依赖于外部类，也不可以访问外部类中非static的变量
	static class StaticInnerClass{
		public String name1;
		public static String name2 = "内部类的静态变量";

		public void print(){
//			System.out.println("" + outField1);报错，不能访问非static的外部类变量
			System.out.println(outField5);
		}
	}
	
	public static void main(String[] args){
		InnerClassTest outer = new InnerClassTest();
		//普通内部类创建的两种方式
		//方式1，必须依赖外部类对象进行创建
		InnerClassTest.InnerClassA inner = outer.new InnerClassA();
		//方式2，通过外部类中方法返回内部类对象
		InnerClassTest.InnerClassA inner1 = outer.getInnerInstance();
		inner.printOuterFields();
		inner.printFields();
		System.out.println("--------------------");
		inner1.printOuterFields();
		inner1.printFields();
		
		//匿名内部类
		//用法1，监听器的常用用法
		outer.setAl(new ActionListener(){
			public void actionPerformed() {
				// TODO 自动生成的方法存根
				System.out.println("我是匿名内部类里的方法");
			}								
		});
		outer.getAl().actionPerformed();
		//用法2，方法中如何给匿名内部类传参
		NoNameInnerClassInterface nnInnerClass = outer.getNoNameInnerClass(3, 5);
		System.out.println("" + nnInnerClass.getNumber());
		
		//静态内部类
		System.out.println(InnerClassTest.StaticInnerClass.name2);//访问静态内部类的静态变量
		StaticInnerClass sic = new InnerClassTest.StaticInnerClass();//内部类的实例化无需依赖外部类实例
		sic.print();
	}
}
