package com.wufan.debug.online.dashboard.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wufan.debug.online.dashboard.dao.MethodMapper;
import com.wufan.debug.online.dashboard.domain.MethodInfo;
import com.wufan.debug.online.dashboard.service.AgentCommandServerService;
import com.wufan.debug.online.dashboard.socket.config.WebSocketSession;
import com.wufan.debug.online.utils.JsonUtils;
import com.wufan.debug.online.domain.AgentCommand;
import com.wufan.debug.online.model.AgentCommandEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 我本非凡
 * Date:2020-12-09
 * Time:14:12:09
 * Description:AgentCommandServerService.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
@Service
public class AgentCommandServerServiceImpl implements AgentCommandServerService {


    @Resource
    private MethodMapper methodMapper;

    @Override
    public void executeCommand(AgentCommand agentCommand) {

    }

    @Override
    public void flushAllMethodInfo(String ip) {
        //获取Ip下所有方法
        QueryWrapper<MethodInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ip", ip);
        List<MethodInfo> methodInfoList = methodMapper.selectList(queryWrapper);

        AgentCommand clearCommand = new AgentCommand(AgentCommandEnum.CLEAR_METHOD, "");
        //发送清空命令
        WebSocketSession.AGENT_CLIENT.sendText(ip,clearCommand);
        //开始添加方法
        methodInfoList.forEach(methodInfo -> {
            AgentCommand addMethod=new AgentCommand(AgentCommandEnum.ADD_METHOD,methodInfo.getTypeName()+"#"+methodInfo.getMethodName());
            WebSocketSession.AGENT_CLIENT.sendText(ip, addMethod);
        });
    }
}
