package com.qingfeng.electronic.base.converter;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2024/1/2
 */
@MappedTypes(Object.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class MyListLongFieldTypeHandler extends AbstractJsonTypeHandler<Object> {

    private static final Logger log = LoggerFactory.getLogger(MyListLongFieldTypeHandler.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    public MyListLongFieldTypeHandler(Class<?> type) {
        if (log.isTraceEnabled()) {
            log.trace("MyListLongFieldTypeHandler init("+type+")");
        }

        Assert.notNull(type, "Type argument cannot be null");
    }

    @Override
    protected Object parse(String json) {
        try {
            // 这里进行json解析
            return objectMapper.readValue(json, new TypeReference<List<List<Long>>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static void setObjectMapper(ObjectMapper objectMapper) {
        MyListLongFieldTypeHandler.objectMapper = objectMapper;
    }
}
