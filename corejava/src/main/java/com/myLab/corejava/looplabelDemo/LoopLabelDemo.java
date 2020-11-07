package com.myLab.corejava.looplabelDemo;

/**
 * 在阅读一些框架源码时经常看到一些循环之外有类似 outer: 的字样，那这是做什么用的呢？
 * 在java中这个叫“标签”，是后面跟一个冒号的标识符，对于java来说唯一用到标签的地方就是在循环语句之前
 * 标签需要紧靠在循环语句之前，在标签和循环语句之间写任何的代码都是不明智的。之所以在循环语句之前加标签
 * 理由就是我们希望在当前循环之内嵌套另一个循环或者一个开关，由于break和continue关键字通常只能中断或者继续
 * 当前循环，但如果配合标签来使用的话，它们就会中断到存在标签的地方，使代码的编写更具有灵活度。
 * 例如：
 * label1:
 * 外部循环{
 *     内部循环{
 *         break;//跳出内部循环回到外部循环
 *         continue;//继续下一次内部循环
 *         continue label1;//跳出内部循环，继续下一次的外部循环
 *         break label1;//跳出外部循环，实际上是终止了两个循环
 *     }
 * }
 */
public class LoopLabelDemo {
    /*
    for循环使用标签的例子
     */
    public void LabelFor(){
        int i = 0;
        outer:
        for(; true; ){
            inner:
            for(; i < 10; i++){
                System.out.println("i = " + i);
                if(i == 2){
                    System.out.println("contiune");
                    continue;
                }
                if(i == 3){
                    System.out.println("break inner");//中断inner循环，回到outer循环执行下一次
                    i++;
                    break;
                }
                if(i == 7){
                    System.out.println("continue outer");
                    i++;
                    continue outer;
                }
                if(i == 8){
                    System.out.println("break outer");
                    break outer;
                }
                for(int k = 0; k < 5; k++){
                    if(k == 3){
                        System.out.println("continue inner");
                        continue inner;
                    }
                }
            }
        }
    }

    public void LabelWhile(){
        int i = 0;
        outer:
        while(true){
            System.out.println("Outer while loop");
            inner:
            while(true){
                System.out.println("i = " + i);
                if(i == 1){
                    System.out.println("continue inner");
                    i++;
                    continue;
                }
                if(i == 3){
                    System.out.println("continue outer");
                    i++;
                    continue outer;
                }
                if(i == 5){
                    System.out.println("break inner");//跳出inner循环，回到outer循环执行下一次
                    i++;
                    break inner;
                }
                if(i == 7){
                    System.out.println("break outer");
                    break outer;
                }
                i++;
            }
        }
    }

    public static void main(String[] args) {
        LoopLabelDemo loopLabelDemo = new LoopLabelDemo();
        loopLabelDemo.LabelFor();
        loopLabelDemo.LabelWhile();
    }
}
