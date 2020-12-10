package com.wufan.debug.online.dashboard.socket.server;

import com.wufan.debug.online.dashboard.service.AgentCommandServerService;
import com.wufan.debug.online.dashboard.socket.config.ProcessAgent;
import com.wufan.debug.online.dashboard.socket.config.WebSocketSession;
import com.wufan.debug.online.utils.JsonUtils;
import com.wufan.debug.online.domain.AgentCommand;
import com.wufan.debug.online.model.AgentCommandEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.function.Consumer;

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

    private static AgentCommandServerService agentCommandServerService;


    /*单例注入 由于AgentRemoteServerEndpoint 非单例 每次初始化会丢点service*/
    @Autowired
    public void setCommandService(AgentCommandServerService agentCommandServerService){
        AgentRemoteServerEndpoint.agentCommandServerService =agentCommandServerService;
    }

    /**
     * 前端一旦启用WebSocket,机会调用@OnOpen注解标注的方法
     *
     * @param username 路径参数
     * @param session  会话，每个访问对象都会有一个单独的会话
     */
    @OnOpen
    public void openSession(@PathParam("username") String username, Session session) {
        log.info("当前连接以建立AGENT_REMOTE" + username);
        WebSocketSession.AGENT_CLIENT.putSession(username, session);
        //sendTextAll("欢迎用户【" + username + "】来到狼窝！");
        //如果服务器上存在方法列表，那么把方法列表更新到机器中

        log.info("当前连接以建立重启恢复加载method" + username);
        agentCommandServerService.flushAllMethodInfo(username);

        //清空监控方法列表
        WebSocketSession.AGENT_CLIENT.sendText(username, new AgentCommand(AgentCommandEnum.CLEAR_MONITOR_METHOD));
        /*Optional.ofNullable(AgentDashboardServerEndpoint.userMethodMap.get(username)).ifPresent(userMethod -> {
            if(userMethod)
            userMethod.forEach(method -> {
                //WebSocketSession.AGENT_CLIENT.sendText(username, "setAgentMonitor=>" + method);
                AgentDashboardServerEndpoint.userMethodMap.get(username).add(method);
            });
        });*/
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
        //WebSocketSession.AGENT_DASHBOARD.sendText(username, message);
        AgentCommand command = JsonUtils.fromJson(message, AgentCommand.class);
        AgentCommandEnum commandEnum=AgentCommandEnum.getEnumByCommand(command.getCommand());
        //如果是发送数据
        if(commandEnum==AgentCommandEnum.METHOD_DATA){
            dealWithClientData(commandEnum,username);
        }
        //如果是获取配置信息
        if(commandEnum==AgentCommandEnum.CLIENT_CONFIG){
            log.info("获取客户端连接正则数据==>>" + username + "===>>>" + message);
            //WebSocketSession.AGENT_DASHBOARD.sendText(username, message);
            commandEnum.setConsumer((s)->{
                String regexp=agentCommandServerService.getClientRegexp(username);
                if(regexp!=null){
                    AgentCommand agentCommand=new AgentCommand(AgentCommandEnum.CLIENT_REGEXP,regexp);
                    WebSocketSession.AGENT_CLIENT.sendText(username, agentCommand);
                }
            });
        }
        commandEnum.executeCommand(command.getContent());
    }



    private void dealWithClientData(AgentCommandEnum agentCommandEnum,@PathParam("username") String username) {
        agentCommandEnum.setConsumer(message -> {
            ProcessAgent agent = JsonUtils.fromJson(message, ProcessAgent.class);
            if (agent.getRootId() != null && userText.containsKey(username)) {
                Map<String, List<ProcessAgent>> stringListMap = userText.get(username);
                List<ProcessAgent> processAgents = stringListMap.computeIfAbsent(agent.getRootId(), k -> new ArrayList<>());
                //发送给前段页面通知
                if (agent.getPid() == 0 && agent.getId() == 0 && agent.getType() == 0) {
                    AgentCommand messageCommand=new AgentCommand(AgentCommandEnum.MONITOR_METHOD,message);
                    WebSocketSession.AGENT_DASHBOARD.sendText(username, messageCommand);
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
        });
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
        WebSocketSession.AGENT_CLIENT.removeSession(username);
        //userText.remove(username);
        //给所有存活的用户发送消息
        //sendTextAll("用户【" + username + "】离开狼窝！");
    }

}