package com.wufan.debug.online.agent.track;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.java_websocket.client.WebSocketClient;
import com.wufan.debug.online.agent.utils.LogTrack;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:11:12:20
 * Description:ProcessSendSocket.java
 *
 * @author wufan02
 * @since JDK 1.8
 * 欲穷千里目 更上一层楼
 */
public class ProcessSendSocket {


    private static ObjectMapper mapper = new ObjectMapper();

    private static WebSocketClient client;

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


    public static void toSocketJsonStr(ProcessAgent agent) {

        if (client != null && client.isOpen()) {
            String string = toJsonString(agent);
            if (string != null) {
                client.send(string);
                //LogTrack.appendLog(string);
            }
        }
    }

    public static String toJsonString(ProcessAgent agent) {
        try {
            return mapper.writeValueAsString(agent);
        } catch (Exception e) {
            String message = "拦截器序列化异常" + e.getMessage();
            agent.setMessage(message);
            //设置入参数 出参数都为空 这两个会出现序列化异常
            agent.setArgs(null);
            agent.setRes(null);
            return toJsonErrorString(agent);
        }
    }

    public static String toJsonString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            LogTrack.appendLog("序列化异常" + e.getMessage());
        }
        return null;
    }

    public static String toJsonErrorString(ProcessAgent agent) {
        String json = null;
        try {
            json = mapper.writeValueAsString(agent);
        } catch (Exception e) {
            LogTrack.appendLog("序列化异常" + e.getMessage());
        }
        return json;
    }


    public static void setWebSocketClient(WebSocketClient client) {
        ProcessSendSocket.client = client;
    }


    public static WebSocketClient getClient() {
        return client;
    }
}
