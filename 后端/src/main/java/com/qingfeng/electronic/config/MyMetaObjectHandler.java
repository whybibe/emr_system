package com.qingfeng.electronic.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/12/25
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * mp 执行insert、update操作时，自动填充实体类非空字段
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        //  填充创建时间和更新时间
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }

    /**
     * 执行修改操作的时候执行的方法
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 设置对那个属性进行赋值
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
