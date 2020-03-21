package com.myLab.corejava.pattern.proxy;

public class UserControllerNoProxy implements IUserController{
    //...省略其他属性和方法...
    private MetricsCollector metricsCollector; // 依赖注入

    public UserVo login(String telephone, String password) {
        long startTimestamp = System.currentTimeMillis();

        // ... 省略login逻辑...

        long endTimeStamp = System.currentTimeMillis();
        long responseTime = endTimeStamp - startTimestamp;
        RequestInfo requestInfo = new RequestInfo("login", responseTime, startTimestamp);
        metricsCollector.recordRequest(requestInfo);

        //...返回UserVo数据...
        return null;
    }

    public UserVo register(String telephone, String password) {
        long startTimestamp = System.currentTimeMillis();

        // ... 省略register逻辑...

        long endTimeStamp = System.currentTimeMillis();
        long responseTime = endTimeStamp - startTimestamp;
        RequestInfo requestInfo = new RequestInfo("register", responseTime, startTimestamp);
        metricsCollector.recordRequest(requestInfo);

        //...返回UserVo数据...
        return null;
    }
}
/*
上面代码的问题是，每个方法里都有一段搜集开始结束时间，计算处理时长的代码，这些代码都是重复的，并且与业务无关，但同时它又与业务代码偶合在一起
未来替换的成本也比较大。
业务代码最好功能职责单一，只聚焦业务处理。
针对这样的场景，代理模式就可以派上用场了
 */
