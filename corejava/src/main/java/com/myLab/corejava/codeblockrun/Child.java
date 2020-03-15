package com.myLab.corejava.codeblockrun;

public class Child extends Parent{
	public static Foo foo1 = new Foo("Child's static parameter");
	public Foo foo2 = new Foo("Child's parameter"); 
	public Child(){
		System.out.println("Child's constructor");
	}
	
	static{
		System.out.println("Child's static code block 1");
	}
	
	{
		System.out.println("Child's non-static code block");
	}
	
	static{
		System.out.println("Child's static code block 2");
	}

}
