package com.mylab.mybatisplus.quickstart;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 需利用spring注解，注入IOC容器，提供属性自动填充的能力
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void updateFill(MetaObject metaObject) {
        //对于有些字段需要在更新时自动填充，最典型的就是updateTime字段。
        //但是strictUpadteFill方法会调用严格填充策略strictFillStrategy方法，严格填充策略默认逻辑是如果字段有值则不覆盖，或者提供的目标值如果是null也不会覆盖
        //而我们常用的update场景是先读出相应的数据，经过业务处理之后更新了某些业务字段，这时希望框架帮忙自动将最新的时间更新至updateTime字段中
        //也就是说在先读再更新的场景下，就不适合使用严格填充策略了，你会发现它不会更新这个字段，所以这里推荐使用通用填充方法，即无论如何都会更新覆盖字段的值
//        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        //注意这里的fieldName属性要跟java类中的属性名一致，可以根据fieldName来区分不同属性的填充策略
        this.strictInsertFill(metaObject, "version", Long.class, 0L);
        this.strictInsertFill(metaObject,"createTime",LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject,"updateTime",LocalDateTime.class, LocalDateTime.now());
    }
}
