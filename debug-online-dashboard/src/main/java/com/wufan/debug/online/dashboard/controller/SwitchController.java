package com.wufan.debug.online.dashboard.controller;

import com.wufan.debug.online.dashboard.socket.config.WebSocketSession;
import com.wufan.debug.online.dashboard.socket.server.AgentDashboardServerEndpoint;
import com.wufan.debug.online.dashboard.socket.server.AgentRemoteServerEndpoint;
import com.wufan.debug.online.domain.AgentCommand;
import com.wufan.debug.online.model.AgentCommandEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:13:12:51
 * Description:SwitchController.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
@Controller
@RequestMapping("/switch")
@Slf4j
public class SwitchController {


    @GetMapping("/setMonitorMethod")
    @ResponseBody
    public String setMonitorMethod(String username, boolean status, String typeName, String method) {
        String typeMethod = typeName + "#" + method;
        if (status) {
            //开启
            log.info("开启IP方法断点{} {}",username,typeMethod);
            WebSocketSession.AGENT_CLIENT.sendText(username, new AgentCommand(AgentCommandEnum.ADD_MONITOR_METHOD,typeMethod));
            AgentDashboardServerEndpoint.userMethodMap.get(username).add(typeMethod);
        } else {
            log.info("关闭IP方法断点{} {}",username,typeMethod);
            //给远程端口发送开启发送命令
            WebSocketSession.AGENT_CLIENT.sendText(username, new AgentCommand(AgentCommandEnum.REMOVE_MONITOR_METHOD,typeMethod));
            AgentDashboardServerEndpoint.userMethodMap.get(username).remove(typeMethod);
        }
        return "成功";
    }

    @GetMapping("/switchMonitor")
    @ResponseBody
    public String switchMonitor(String username, boolean status) {
        if (status) {
            //开启
            log.info("当前连接以建立AGENT_CLIENT" + username);
            //sendTextAll("欢迎用户【" + username + "】来到狼窝！");
            try {
                //AgentRemoteServerEndpoint.userText.put(username, new HashMap<>());
                //给远程端口发送开启发送命令
                WebSocketSession.AGENT_CLIENT.sendText(username, new AgentCommand(AgentCommandEnum.OPEN_CLIENT));
                log.info("启动客户端开启参数拦截" + username);
            } catch (Error e) {
                log.error("启动客户端开启参数拦截失败", e);
            }
        } else {
            //给远程端口发送开启发送命令
            WebSocketSession.AGENT_CLIENT.sendText(username, new AgentCommand(AgentCommandEnum.CLOSE_CLIENT));

            //将当前用户移除
            log.info("当前连接以移除AGENT_CLIENT" + username);
            //AgentRemoteServerEndpoint.userText.remove(username);
            //给所有存活的用户发送消息
            //sendTextAll("用户【" + username + "】离开狼窝！");
            //给远程端口发送开启发送命令
            //log.info("关闭客户端开启参数拦截" + username);
        }
        return "切换开关" + status;
    }

    @GetMapping("/clearMonitor")
    @ResponseBody
    public String clearMonitor(String username) {
        AgentRemoteServerEndpoint.userText.put(username, new HashMap<>());
        log.info("清空数据完成" + username);
        return "清空数据完成";
    }

    /*@GetMapping("/sentMainMethod")
    @ResponseBody
    public String sentMainMethod(String username, String value) {

        String[] split = value.split("\n");
        if (split.length > 0) {
            WebSocketSession.AGENT_CLIENT.sendText(username, "removeAllMethod");
            Arrays.stream(split).forEach(method -> {
                if (method.contains("#")) {
                    //把它发送给监控端
                    WebSocketSession.AGENT_CLIENT.sendText(username, "setAgentMethod=>" + method);
                }
            });
        }
        return "添加主方法数据完成";
    }*/
}
