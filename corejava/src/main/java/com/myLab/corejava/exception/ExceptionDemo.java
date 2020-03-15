package com.myLab.corejava.exception;


public class ExceptionDemo {

    private void testThrowException() {
        throw new RuntimeException("抛出异常");
    }

    private void testThrowsException1() throws Throwable {
        throw new RuntimeException("抛出异常");
    }

    /**
     * 由于testThrowException()方法抛出的是一个运行时异常，这个异常可以不用捕获，它会一直往上扔到控制台
     */
    public void testUnCatchExcepiton(){
        testThrowException();
    }

    /**
     *由于testThrowsException1方法后面加了throws关键字，就意味着所有调用它的地方都必须要显式的捕获异常
     * 可以用try...catch来捕获并处理或者直接在用throws把异常再往上扔出去
     * 下面方法演示了一种把异常吃掉的情况，虽然下面方法加了一个throws关键字，但事实上捕捉到的异常被吃掉了，不会有异常抛出去。
     * 先用try...catch捕获到了异常，可以进行打印日志等业务处理，如果想让外面捕获到异常那么就必须throw出去。
     *
     * @throws Throwable
     */
    public void testCatchException() throws Throwable{
        try {
            testThrowsException1();
        } catch (Throwable throwable) {
            System.out.println("捕获到exception1,并吃掉");
//            throw throwable;
        }
    }

    public static void main(String[] args) {
        ExceptionDemo exceptionDemo = new ExceptionDemo();
        exceptionDemo.testUnCatchExcepiton();//这里一个未经捕获的运行时异常会抛出来，程序会停止

        try {
            exceptionDemo.testCatchException();
            System.out.println("ok");//上面的方法调用中，try..catch了异常并且处理了，程序会继续往下走。
        } catch (Throwable throwable) {
            System.out.println("会再次捕获exception1么？");
            throwable.printStackTrace();
        }


    }
}
