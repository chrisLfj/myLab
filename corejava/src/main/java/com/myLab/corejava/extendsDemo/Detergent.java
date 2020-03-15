package com.myLab.corejava.extendsDemo;

public class Detergent extends Cleanser {
    public Detergent(){
        System.out.println("实例化子类Detergent");
    }

    /**
     * 重写父类apply方法，将完全覆盖父类apply方法的逻辑
     */
    public void apply() {
        append(" Detergent.apply()");
    }

    /**
     * 子类内部可以使用super来表示父类，用以区分相同方法的调用
     */
    public void dilute(){
        super.dilute();
        //ps在父类中是protected的，因此可以被子类访问到
        String str = ps;
        //编译失败，因为变量s时父类的private变量，子类无法访问
        //String str = s;
    }
    /**
     * 子类内部如果
     */
    public void foam() {
        append(" Detergent.foam()");
    }

//    public String toString(){
//        return "Detergent.toString";
//    }

    public static void main(String[] args){
        Detergent d = new Detergent();
        d.dilute();
        d.apply();
        d.foam();
        System.out.println(d);//通过子类对象中隐含的父类对象调用了父类的toString方法，
        System.out.println(d.ps);

        //结果如下：
//        实例化父类Cleanser
//        实例化子类Detergent
//        Cleanser dilute() Detergent.apply() Detergent.foam()
//        Cleanser protected String

        //总结：
//        当实例化一个子类时会先实例化一个父类，这个父类的对象被包装在子类对象内部，供子类使用
//        父类的方法或变量必须是public或者protected或者默认访问权限(但前提是子类与父类在一个包内)才能被子类访问到
//        子类可以重写父类的方法，完全覆盖父类方法的逻辑，也可以在方法中使用super关键字来调用父类方法
//        父类的private变量无法被子类访问到，但子类可以通过访问父类的方法来间接操作父类private变量
    }
}
