package com.qingfeng.electronic.base.converter;

import cn.hutool.core.lang.Pair;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MyBatis将实体中的List<String> 等类似数据转成JSON存储，处理String类型
 *
 * @author pjl
 * @version 1.0.0
 * @date 2023/9/7
 */
@MappedTypes({Object.class})
@MappedJdbcTypes({JdbcType.VARCHAR})
public class MyFieldTypeHandler<T> extends AbstractJsonTypeHandler<T> {
    private static final Logger log = LoggerFactory.getLogger(MyFieldTypeHandler.class);
    private final Class<T> type;

    public MyFieldTypeHandler(Class<T> type) {
        super();
        this.type = type;
        if (log.isTraceEnabled()) {
            log.trace("JacksonTypeHandler(" + type + ")");
        }
        Assert.notNull(type, "Type argument cannot be null", new Object[0]);
    }

    @Override
    protected T parse(String json) {
        try {
            if (this.type.equals(Pair.class)) {
                JSONObject jsonObject = JSONObject.parseObject(json);
                String key = jsonObject.getString("key");
                Object value = jsonObject.get("value");
                return (T) new Pair<String, Object>(key, value);
            } else {
                return JSONObject.parseObject(json, this.type);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected String toJson(T obj) {
        try {
            return JSONObject.toJSONString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
