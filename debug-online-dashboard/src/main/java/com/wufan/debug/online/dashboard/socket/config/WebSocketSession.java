package com.wufan.debug.online.dashboard.socket.config;

import com.wufan.debug.online.utils.JsonUtils;
import com.wufan.debug.online.domain.AgentCommand;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:13:12:16
 * Description:WebSocketSession.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
@Slf4j
public class WebSocketSession {

    public static LivingSession AGENT_DASHBOARD = new LivingSession();

    public static LivingSession AGENT_CLIENT = new LivingSession();


    public static class LivingSession {

        private String suffix;
        /**
         * 存储所有存活的用户
         * 注意1：高并发问题
         * 注意2：livingSessions必须是全局变量（保证全局就他一个变量，否则每次调用都会被刷新）
         * 注意3：很难保证，用户在退出狼窝时，正确调用了WebSocket.close()，也就是调用后端的onClose()方法，这样
         * 就可能会导致Session无法被有效清除，livingSessions会越来越大，服务器压力也会越来越大。
         * 所以，我们需要周期性的去检查用户是否还处于活跃状态，不活跃的，移除该用户的session
         */
        private Map<String, Session> livingSessions = new ConcurrentHashMap<>();

        public LivingSession() {
        }

        public LivingSession(String suffix) {
            this.suffix = suffix;
        }

        public void putSession(String username, Session session) {
            livingSessions.put(getSessionKey(username), session);
        }

        public void removeSession(String username) {
            livingSessions.remove(getSessionKey(username));
        }

        /**
         * 获取所有session用户
         *
         * @return
         */
        public Map<String, Session> getLivingSessions() {
            return livingSessions;
        }


        /**
         * 向指定Session(用户)发送message
         */
        private void sendText(Session session, String message) {
            //发送消息对象
            RemoteEndpoint.Basic basicRemote = session.getBasicRemote();
            try {
                //发送消息
                basicRemote.sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 向指定Session(用户)发送message
         */
        public void sendText(String username, AgentCommand command) {

            final Session session = livingSessions.get(getSessionKey(username));
            //发送消息对象
            if (session != null) {
                sendText(session, JsonUtils.toJsonStr(command));
            } else {
                log.error("执行脚本数据==>>>" + username + "message===>>" + "当前会话不存在");
            }

        }

        /**
         * 遍历所有存活的用户，并发送消息（PS：就是广播消息）
         */
        public void sendTextAll(String message) {
            //lambda表达式
            livingSessions.forEach((username, session) -> sendText(session, message));
        }


        public String getSessionKey(String username) {
            if (suffix == null) {
                return username;
            }
            return username + suffix;
        }
    }


}
