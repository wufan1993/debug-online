package com.wufan.debug.online.dashboard.controller;

import com.wufan.debug.online.dashboard.socket.config.AgentList;
import com.wufan.debug.online.dashboard.socket.config.ProcessAgent;
import com.wufan.debug.online.dashboard.socket.config.WebSocketSession;
import com.wufan.debug.online.dashboard.socket.server.AgentDashboardServerEndpoint;
import com.wufan.debug.online.dashboard.socket.server.AgentRemoteServerEndpoint;
import com.wufan.debug.online.dashboard.util.PackRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:13:12:46
 * Description:AgentController.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
@Controller
@RequestMapping("/agent")
@Slf4j
public class AgentController {


    @GetMapping("/listAgentList")
    public String listAgentList(Model model, String username) {
        model.addAttribute("username", username);
        //开启监听并获取数据
        /*AgentRemoteServerEndpoint.userText.computeIfAbsent(username,k->{
            //给远程端口发送开启发送命令
            WebSocketSession.AGENT_CLIENT.sendText(username, "start");
            return new HashMap<>();
        });*/
        //model.addAttribute("agentHost", agentHost);
        AgentRemoteServerEndpoint.userText.put(username, new HashMap<>());

        //如果当前存在会话，那么关闭之前的会话
        if (WebSocketSession.AGENT_DASHBOARD.getLivingSessions().containsKey(username)) {
            model.addAttribute("message", "当前会话已存在，无法新建会话连接");
            return "/modules/agent/pages-error.html";
        }

        return "/modules/agent/agentList.html";
    }

    @GetMapping("/listAgentDetail")
    public String listAgentDetail(Model model, String username, String rootId) {
        model.addAttribute("username", username);
        model.addAttribute("rootId", rootId);
        return "/modules/agent/agentDetail.html";
    }


    @GetMapping("/getAgentList")
    @ResponseBody
    public Map<String, Object> getAgentList(String username) {
        List<AgentList> dataList = new ArrayList<>();
        Map<String, List<ProcessAgent>> agentDataListMap = AgentRemoteServerEndpoint.userText.get(username);
        if (agentDataListMap != null) {
            AtomicInteger integer = new AtomicInteger(1);
            List<AgentList> resData = new ArrayList<>();
            agentDataListMap.forEach((k, v) -> {
                AgentList agent = new AgentList();
                agent.setPid(-1);
                agent.setId(integer.getAndIncrement());
                agent.setRootId(k);
                if (!CollectionUtils.isEmpty(v) && v.size() > 0) {
                    ProcessAgent agentDataOne = v.get(0);
                    agent.setTypeName(agentDataOne.getTypeName());
                    agent.setMethodName(agentDataOne.getMethod());
                    agent.setEnterName(agentDataOne.getEnterTime());
                    agent.setArgs(agentDataOne.getArgs());
                    resData.add(agent);
                }
            });
            resData.sort((o1, o2) -> o2.getEnterName().compareTo(o1.getEnterName()));
            dataList = resData;
        }
        return PackRes.getResult(dataList);
    }

    @GetMapping("/getAgentData")
    @ResponseBody
    public Map<String, Object> getAgentData(String username, String rootId) {
        List<ProcessAgent> processAgents = null;
        if (username != null && rootId != null) {
            Map<String, List<ProcessAgent>> stringListMap = AgentRemoteServerEndpoint.userText.get(username);
            if (stringListMap != null) {
                processAgents = stringListMap.get(rootId);
                processAgents.forEach(processAgent -> {
                    //debugPort
                    if (processAgent.getPid().intValue() == processAgent.getId()) {
                        processAgent.setPid(-1);
                    }
                    String typeMethod = processAgent.getTypeName() + "#" + processAgent.getMethod();
                    if ((AgentDashboardServerEndpoint.userMethodMap.get(username).contains(typeMethod))) {
                        processAgent.setDebugPort(true);
                    }
                });
            }
        }
        return PackRes.getResult(processAgents);
    }


}
