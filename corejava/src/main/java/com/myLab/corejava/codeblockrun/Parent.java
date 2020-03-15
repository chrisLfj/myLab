package com.myLab.corejava.codeblockrun;

public class Parent {
	public static Foo foo1 = new Foo("Parent's static parameter");
	public Foo foo2 = new Foo("Parent's parameter");
	public Parent(){
		System.out.println("Parent's constructor");
	}
	
	static{
		System.out.println("Parent's static code block 1");
	}
	
	{
		System.out.println("Parent's non-static code block");
	}
	
	static{
		System.out.println("Parent's static code block 2");
	}
}
