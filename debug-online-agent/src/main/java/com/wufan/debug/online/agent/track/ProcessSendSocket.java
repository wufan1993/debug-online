package com.wufan.debug.online.agent.track;

import com.wufan.debug.online.agent.utils.LogTrack;
import com.wufan.debug.online.domain.AgentCommand;
import com.wufan.debug.online.model.AgentCommandEnum;
import com.wufan.debug.online.utils.JsonUtils;
import org.java_websocket.client.WebSocketClient;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:11:12:20
 * Description:ProcessSendSocket.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
public class ProcessSendSocket {


    private static WebSocketClient client;


    public static void toSocketJsonStr(ProcessAgent agent) {

        if (client != null && client.isOpen()) {
            String string = toJsonString(agent);

            if (string != null) {
                AgentCommand agentCommand = new AgentCommand(AgentCommandEnum.METHOD_DATA, string);
                client.send(JsonUtils.toJsonStr(agentCommand));
                //LogTrack.appendLog(string);
            }
        }
    }

    public static String toJsonString(ProcessAgent agent) {

        try {
            String str = JsonUtils.toJsonStr(agent);
            long length = str.length();
            if (length > 4460256) {
                throw new RuntimeException("序列化长度过长 长度:" + length);
            }
            return str;
        } catch (Exception e) {
            String message = "拦截器序列化异常" + e.getMessage();
            LogTrack.appendLog(message);
            agent.setMessage(message);
            //设置入参数 出参数都为空 这两个会出现序列化异常
            agent.setArgs(null);
            agent.setRes(null);
            return toJsonErrorString(agent);
        }
    }


    public static String toJsonErrorString(ProcessAgent agent) {
        String json = null;
        try {
            json = JsonUtils.toJsonStr(agent);
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

    /**
     * 客户端发送命令
     *
     * @param agentCommand
     */
    public static void sentAgentCommand(AgentCommand agentCommand) {
        client.send(JsonUtils.toJsonStr(agentCommand));
    }
}
