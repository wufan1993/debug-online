package com.wufan.debug.online.dashboard.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:13:12:48
 * Description:JsonUtils.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
@Slf4j
public class JsonUtils {


    private static ObjectMapper mapper = new ObjectMapper();

    static {
        // 解决实体未包含字段反序列化时抛出异常
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 对于空的对象转json的时候不抛出错误
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 允许属性名称没有引号
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 允许单引号
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }


    public static String toJsonStr(Object o) {
        String asString = null;
        try {
            asString = mapper.writeValueAsString(o);
            return asString;
        } catch (JsonProcessingException e) {
            log.error("序列化失败", e);
            throw new RuntimeException("序列化失败", e);
        }
    }


    public static <T> T fromJson(String str, Class<T> clazz) {
        T t;
        try {
            t = mapper.readValue(str, clazz);
            return t;
        } catch (IOException e) {
            log.error("序列化失败", e);
            throw new RuntimeException("序列化失败", e);
        }
    }

}
