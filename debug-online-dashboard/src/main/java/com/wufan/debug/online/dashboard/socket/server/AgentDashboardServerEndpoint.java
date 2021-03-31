package com.wufan.debug.online.dashboard.socket.server;

import com.wufan.debug.online.dashboard.socket.config.WebSocketSession;
import com.wufan.debug.online.domain.AgentCommand;
import com.wufan.debug.online.model.AgentCommandEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:14:12:35
 * Description:AgentClientServerEndpoint.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
@Component
@ServerEndpoint("/socket/agentClient/{username}")
@Slf4j
public class AgentDashboardServerEndpoint {

    /**
     * 前端一旦启用WebSocket,机会调用@OnOpen注解标注的方法
     *
     * @param username 路径参数
     * @param session  会话，每个访问对象都会有一个单独的会话
     */
    @OnOpen
    public void openSession(@PathParam("username") String username, Session session) {
        WebSocketSession.AGENT_DASHBOARD.putSession(username, session);
        log.info("当前连接以建立AGENT_CLIENT" + username);
        //sendTextAll("欢迎用户【" + username + "】来到狼窝！");
    }

    /**
     * 服务端发送消息 给前端时调用
     *
     * @param username 路径参数
     * @param message  待发送的消息
     */
    @OnMessage
    public void onMessage(@PathParam("username") String username, String message) {
        //sendTextAll("用户【" + username + "】：" + message);
        //发送消息给机器
        log.info("执行脚本数据==>>>" + username + "message===>>" + message);
    }

    /**
     * 客户端关闭WebSocket连接时，调用标注@OnClose的方法
     *
     * @param username 路径参数
     */
    @OnClose
    public void onClose(@PathParam("username") String username) {
        //将当前用户移除
        log.info("当前连接以移除AGENT_CLIENT" + username);
        WebSocketSession.AGENT_DASHBOARD.removeSession(username);
        //AgentRemoteServerEndpoint.userText.remove(username);
        //给所有存活的用户发送消息
        //sendTextAll("用户【" + username + "】离开狼窝！");
        //给远程端口发送开启发送命令
        WebSocketSession.AGENT_CLIENT.sendText(username, new AgentCommand(AgentCommandEnum.CLOSE_CLIENT));
        log.info("关闭客户端开启参数拦截" + username);
    }


}