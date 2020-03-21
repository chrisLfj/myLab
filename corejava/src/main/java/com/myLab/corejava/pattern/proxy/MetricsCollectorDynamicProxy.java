package com.myLab.corejava.pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MetricsCollectorDynamicProxy {
    private MetricsCollector metricsCollector; // 依赖注入

//    public MetricsCollectorDynamicProxy(MetricsCollector metricsCollector) {
//        this.metricsCollector = metricsCollector;
//    }

    public Object createProxy(Object proxiedObject) {
        Class<?>[] interfaces = proxiedObject.getClass().getInterfaces(); //按接口进行动态代理，要求proxiedObject必须实现接口
        DynamicProxyHandler handler = new DynamicProxyHandler(proxiedObject);
        return Proxy.newProxyInstance(proxiedObject.getClass().getClassLoader(), interfaces, handler);
    }

    private class DynamicProxyHandler implements InvocationHandler{
        private Object proxiedObject;//被代理的类

        public DynamicProxyHandler(Object proxiedObject) {
            this.proxiedObject = proxiedObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            long startTimestamp = System.currentTimeMillis();
            Object result = method.invoke(proxiedObject, args);
            long endTimeStamp = System.currentTimeMillis();
            long responseTime = endTimeStamp - startTimestamp;
            RequestInfo requestInfo = new RequestInfo("register", responseTime, startTimestamp);
            metricsCollector.recordRequest(requestInfo);
            return result;
        }
    }

    public static void main(String[] args) {
        MetricsCollectorDynamicProxy proxy = new MetricsCollectorDynamicProxy();
        IUserController userController = (IUserController)proxy.createProxy(new UserController());
    }
}
/*
动态代理，省略了为每个目标类编写代理类的麻烦，而是在运行时动态地创建代理类，java语言已经提供了动态代理的语法，
实际上，动态代理底层依赖的就是Java的反射语法。
Spring AOP就是基于动态代理实现的，动态代理在一些场景中应用广泛，例如：监控，统计，鉴权，限流，事务，幂等，日志等
RPC框架也可以看作一种代理模式，GoF书中将其称之为“远程代理”，将网络通信，数据编解码等细节隐藏起来，客户端在使用RPC服务时
就像是在调用本地函数一样，无需了解跟服务器交互的细节
 */
