package com.myLab.corejava.enumDemo;

public class UseColorEnum {

	public static void main(String[] args){
//		ColorEnum cEnum = new ColorEnum();枚举类型是私有化的构造函数，因此无法实例化
		ColorEnum blue = ColorEnum.BLUE; 
		ColorEnum red = ColorEnum.valueOf("RED");
		ColorEnum green = ColorEnum.getEnumByKey("green");
		System.out.println("key:" + blue.getKey() + " value:" + blue.getValue() + " int:" + blue.ordinal());
		System.out.println("key:" + red.getKey() + " value:" + red.getValue() + " int:" + red.ordinal());
		System.out.println("key:" + green.getKey() + " value:" + green.getValue() + " int:" + green.ordinal());

//      运行结果:
//		key:blue value:蓝色 int:2
//		key:red value:红色 int:1
//		key:green value:绿色 int:0
	}
}
