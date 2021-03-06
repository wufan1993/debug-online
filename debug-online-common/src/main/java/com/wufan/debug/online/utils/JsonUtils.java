package com.wufan.debug.online.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 我本非凡
 * Date:2020-12-10
 * Time:11:12:59
 * Description:JsonUtils.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
public class JsonUtils {


    static {
        //List字段如果为null,输出为[],而非null
        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.WriteNullListAsEmpty.getMask();
    }
    /*private static ObjectMapper mapper = new ObjectMapper();

    static {
        // 解决实体未包含字段反序列化时抛出异常
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 对于空的对象转json的时候不抛出错误
        //mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 允许属性名称没有引号
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 允许单引号
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        //mapper.disable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
    }*/


    public static String toJsonStr(Object o) {

        /*String asString = null;
        try {
            asString = mapper.writeValueAsString(o);
            return asString;
        } catch (JsonProcessingException e) {
            //log.error("序列化失败", e);
            throw new RuntimeException("序列化失败"+e.getMessage(), e);
        }*/
        return JSON.toJSONString(o);
        //String jsonString = JSONObject.toJSONString(o);
        //return jsonString;
    }


    public static <T> T fromJson(String str, Class<T> clazz) {

        return JSON.parseObject(str, clazz);
        /*T t;
        try {
            t = mapper.readValue(str, clazz);
            return t;
        } catch (IOException e) {
            //log.error("序列化失败", e);
            throw new RuntimeException("序列化失败", e);
        }*/
    }

}
