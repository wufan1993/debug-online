package com.wufan.debug.online.dashboard.socket.server;

import lombok.extern.slf4j.Slf4j;
import com.wufan.debug.online.dashboard.socket.config.WebSocketSession;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 上海京东到家元信信息技术有限公司
 * Date:2019-10-31
 * Time:14:58
 * Description:
 * * 狼窝服务端
 * * 标注为端点：@ServerEndpoint，其中"/chat-room/{username}"为访问路径
 *
 * @author wufan wufan02@imdada.cn
 * @since JDK 1.8
 * Copyright (c) 2019 imdada System Incorporated All Rights Reserved.
 */
@Component
@ServerEndpoint("/socket/agentClient/{username}")
@Slf4j
public class AgentClientServerEndpoint {

    public static Map<String,List<String>> userMethodMap=new ConcurrentHashMap<>();

    /**
     * 前端一旦启用WebSocket,机会调用@OnOpen注解标注的方法
     *
     * @param username 路径参数
     * @param session  会话，每个访问对象都会有一个单独的会话
     */
    @OnOpen
    public void openSession(@PathParam("username") String username, Session session) {
        //this.suffix="client";
        //添加前段session 后缀是client
        WebSocketSession.AGENT_CLIENT.putSession(username, session);
        log.info("当前连接以建立AGENT_CLIENT" + username);
        userMethodMap.put(username,new ArrayList<>());
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
        WebSocketSession.AGENT_CLIENT.removeSession(username);
        //AgentRemoteServerEndpoint.userText.remove(username);
        //给所有存活的用户发送消息
        //sendTextAll("用户【" + username + "】离开狼窝！");
        //给远程端口发送开启发送命令
        WebSocketSession.AGENT_REMOTE.sendText(username, "end");
        log.info("关闭客户端开启参数拦截" + username);
    }


}