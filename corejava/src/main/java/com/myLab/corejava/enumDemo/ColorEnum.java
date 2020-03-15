package com.myLab.corejava.enumDemo;

public enum ColorEnum {
	GREEN("green", "绿色"), RED("red","红色"), BLUE("blue","蓝色");
	
	private final String key;
	private final String value;
	
	private ColorEnum(String key, String value){
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
	
	//根据key获取枚举，由于枚举类型无法实例化，因此方法要static才可以被外界调用
	public static ColorEnum getEnumByKey(String key){
		if(null == key){
			return null;
		}
		for(ColorEnum temp: ColorEnum.values()){
			if(temp.getKey().equals(key)){
				return temp;
			}
		}
		return null;
	}

// 下面是将ColorEnum字节码文件反编译后的java，可以看到枚举类的内部设计
//	public final class ColorEnum extends Enum  枚举类会被final修饰，意味着无法被继承
//	{
//      静态工具方法，可以获得枚举类中的所有元素数组
//	    public static ColorEnum[] values()
//	    {
//	        return (ColorEnum[])$VALUES.clone();
//	    }
//      静态工具方法，根据枚举类名称获得枚举类
//	    public static ColorEnum valueOf(String s)
//	    {
//	        return (ColorEnum)Enum.valueOf(ColorEnum, s);
//	    }
//
//	    //私有构造方法，意味着枚举类无法在外部被实例化，
//	    //s:枚举类名，i:枚举类的序号，从0开始。构造方法在原基础上加上我们新增的两个形参s1:key,s2:value
//	    private ColorEnum(String s, int i, String s1, String s2)
//	    {
//	        super(s, i);
//	        key = s1;
//	        value = s2;
//	    }
//
//	    //自定义方法，通过key值获得对应的枚举对象
//	    public static ColorEnum getEnumByKey(String s)
//	    {
//	        if(null == s)
//	            return null;
//	        ColorEnum acolorenum[] = values();
//	        int i = acolorenum.length;
//	        for(int j = 0; j < i; j++)
//	        {
//	            ColorEnum colorenum = acolorenum[j];
//	            if(colorenum.getKey().equals(s))
//	                return colorenum;
//	        }
//
//	        return null;
//	    }
//
//	    public String getKey()
//	    {
//	        return key;
//	    }
//
//	    public String getValue()
//	    {
//	        return value;
//	    }
//      下面是重点
//      可以看到枚举类中的每个元素其实都是枚举类型，而且是静态并且是final，存放于静态变量区域
//      也就是说RED,GREEN,BLUE都是拥有数据结构的，这里的设计特别像数据库字典表
//	    public static final ColorEnum RED;
//	    public static final ColorEnum GREEN;
//	    public static final ColorEnum BLUE;
//	    //我们自定义的两个字段
//	    private final String key;
//	    private final String value;
//	    private static final ColorEnum $VALUES[];
//      静态代码块是关键，在ColorEnum加载时会将枚举元素在内部实例化并初始化，这里的思想类似于往数据库的字典表里初始化数据，以供外部使用
//	    static 
//	    {
//	        RED = new ColorEnum("RED", 0, "red", "\u7EFE\u3223\u58CA");
//	        GREEN = new ColorEnum("GREEN", 1, "green", "\u7F01\u80EF\u58CA");
//	        BLUE = new ColorEnum("BLUE", 2, "blue", "\u9483\u6FCA\u58CA");
//	        $VALUES = (new ColorEnum[] {
//	            RED, GREEN, BLUE
//	        });
//	    }
//	}
}
