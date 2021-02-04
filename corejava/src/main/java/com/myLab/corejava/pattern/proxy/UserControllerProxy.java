package com.myLab.corejava.pattern.proxy;

/**
 * 静态代理
 * 静态代理是指，代理类与被代理类都实现同样的接口，这样代理类就有着与被代理类相同的行为，代理类则可以根据不同的需求对被代理类的方法进行一些额外的增强
 * 对于调用方来说他们直接使用的是代理类的方法。
 */
public class UserControllerProxy implements IUserController{
    //...省略其他属性和方法...
    private MetricsCollector metricsCollector; // 依赖注入
    private IUserController userController;

    public UserVo login(String telephone, String password) {
        long startTimestamp = System.currentTimeMillis();

        //业务逻辑
        UserVo userVo = userController.login(telephone, password);

        long endTimeStamp = System.currentTimeMillis();
        long responseTime = endTimeStamp - startTimestamp;
        RequestInfo requestInfo = new RequestInfo("login", responseTime, startTimestamp);
        metricsCollector.recordRequest(requestInfo);

        //...返回UserVo数据...
        return userVo;
    }

    public UserVo register(String telephone, String password) {
        long startTimestamp = System.currentTimeMillis();

        //业务逻辑
        UserVo userVo = userController.register(telephone, password);

        long endTimeStamp = System.currentTimeMillis();
        long responseTime = endTimeStamp - startTimestamp;
        RequestInfo requestInfo = new RequestInfo("register", responseTime, startTimestamp);
        metricsCollector.recordRequest(requestInfo);

        //...返回UserVo数据...
        return userVo;
    }
}
/*
这是一个典型的代理模式实现，代理模式即一个类可以代替另一个类提供相同的行为，在这个过程中可以额外的提供一些服务。
那从实现上来说，要提供相同的行为，就意味着代理类和被代理类实现相同的接口，或者代理类继承被代理类。
从上面代码来看，UserControllerProxy与UserController都实现了IUserController接口，然后在每个方法上，都加入了一些登陆信息的搜集逻辑
试想一下，如果一个代理类需要代理多个类，那就要设计多个业务接口，然后这个代理类要实现这些接口，每个方法中加入额外的逻辑
这种代理模式也有一定的局限性，那就是代理类和被代理类在代码上必须要耦合在一起，并且要在每个方法中加入重复的逻辑
另外一种代理模式可以解决这个问题，那就是“动态代理”
 */
