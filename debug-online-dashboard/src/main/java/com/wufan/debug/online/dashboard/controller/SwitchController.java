package com.wufan.debug.online.dashboard.controller;

import com.wufan.debug.online.dashboard.service.BreakMethodService;
import com.wufan.debug.online.dashboard.socket.config.WebSocketSession;
import com.wufan.debug.online.dashboard.socket.server.AgentRemoteServerEndpoint;
import com.wufan.debug.online.domain.AgentCommand;
import com.wufan.debug.online.model.AgentCommandEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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

    @Resource
    private BreakMethodService breakMethodService;

    @GetMapping("/setMonitorMethod")
    @ResponseBody
    public String setMonitorMethod(String username, boolean status, String typeName, String method) {
        String typeMethod = typeName + "#" + method;
        if (status) {
            //保存数据
            breakMethodService.insertOne(username,typeMethod);
        } else {
            breakMethodService.deleteOne(username,typeMethod);
        }
        return "成功";
    }

    @GetMapping("/switchMonitor")
    @ResponseBody
    public String switchMonitor(String username, boolean status) {
        if (status) {
            //开启
            log.info("当前连接以建立AGENT_CLIENT" + username);
            try {
                //给远程端口发送开启发送命令
                WebSocketSession.AGENT_CLIENT.sendText(username, new AgentCommand(AgentCommandEnum.OPEN_CLIENT));
                log.info("启动客户端开启参数拦截" + username);
            } catch (Error e) {
                log.error("启动客户端开启参数拦截失败", e);
            }
        } else {
            //给远程端口发送开启发送命令
            WebSocketSession.AGENT_CLIENT.sendText(username, new AgentCommand(AgentCommandEnum.CLOSE_CLIENT));
            log.info("当前连接以移除AGENT_CLIENT" + username);
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

}
