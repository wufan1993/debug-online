package com.wufan.debug.online.dashboard.socket.server;

import com.wufan.debug.online.dashboard.socket.config.ProcessAgent;
import com.wufan.debug.online.dashboard.socket.config.WebSocketSession;
import com.wufan.debug.online.dashboard.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:14:12:36
 * Description:AgentRemoteServerEndpoint.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
@Component
@ServerEndpoint("/socket/agentRemote/{username}")
@Slf4j
public class AgentRemoteServerEndpoint {

    public static Map<String, Map<String, List<ProcessAgent>>> userText = new ConcurrentHashMap<>();

    /**
     * 前端一旦启用WebSocket,机会调用@OnOpen注解标注的方法
     *
     * @param username 路径参数
     * @param session  会话，每个访问对象都会有一个单独的会话
     */
    @OnOpen
    public void openSession(@PathParam("username") String username, Session session) {
        log.info("当前连接以建立AGENT_REMOTE" + username);
        WebSocketSession.AGENT_REMOTE.putSession(username, session);
        //sendTextAll("欢迎用户【" + username + "】来到狼窝！");
        //如果服务器上存在方法列表，那么把方法列表更新到机器中

        Optional.ofNullable(AgentClientServerEndpoint.userMethodMap.get(username)).ifPresent(userMethod -> {
            userMethod.forEach(method -> {
                log.info("当前连接以建立重启恢复加载method" + username);
                WebSocketSession.AGENT_REMOTE.sendText(username, "setAgentMonitor=>" + method);
                AgentClientServerEndpoint.userMethodMap.get(username).add(method);
            });
        });
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
        //把脚本执行的返回结果 发给前端页面
        //log.info("返回脚本执行结果" + username + "===>>>" + message);
        //WebSocketSession.AGENT_CLIENT.sendText(username, message);

        ProcessAgent agent = JsonUtils.fromJson(message, ProcessAgent.class);
        if (agent.getRootId() != null && userText.containsKey(username)) {
            Map<String, List<ProcessAgent>> stringListMap = userText.get(username);
            List<ProcessAgent> processAgents = stringListMap.computeIfAbsent(agent.getRootId(), k -> new ArrayList<>());
            //发送给前段页面通知
            if (agent.getPid() == 0 && agent.getId() == 0 && agent.getType() == 0) {
                WebSocketSession.AGENT_CLIENT.sendText(username, message);
                //todo
                //log.info("开始拦截首次数据" + username + "===>>>" + message);
                processAgents.add(agent);
            } else if (processAgents.size() > 0) {
                //如果集合不存在数据 进来的数据也不符合标准 那么 说明是残缺数据 需要全部丢弃
                //其它类型进行数据合并
                //如参方法放入队列中
                if (agent.getType() == 0) {
                    processAgents.add(agent);
                } else {
                    for (ProcessAgent ar : processAgents) {
                        String cp = ar.getId() + "" + ar.getPid();
                        String np = agent.getId() + "" + agent.getPid();
                        if (cp.equals(np)) {
                            ar.setRes(agent.getRes());
                            ar.setMessage(agent.getMessage());
                            ar.setCostTime(agent.getCostTime());
                            break;
                        }
                    }
                }
            }

        }
    }

    /**
     * 客户端关闭WebSocket连接时，调用标注@OnClose的方法
     *
     * @param username 路径参数
     */
    @OnClose
    public void onClose(@PathParam("username") String username) {
        //将当前用户移除
        log.info("当前连接以移除AGENT_REMOTE" + username);
        WebSocketSession.AGENT_REMOTE.removeSession(username);
        //userText.remove(username);
        //给所有存活的用户发送消息
        //sendTextAll("用户【" + username + "】离开狼窝！");
    }

}