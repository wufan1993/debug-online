package com.wufan.debug.online.dashboard.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wufan.debug.online.dashboard.dao.MachineMapper;
import com.wufan.debug.online.dashboard.dao.MethodMapper;
import com.wufan.debug.online.dashboard.domain.MachineInfo;
import com.wufan.debug.online.dashboard.domain.MethodInfo;
import com.wufan.debug.online.dashboard.domain.MethodStack;
import com.wufan.debug.online.dashboard.service.AgentCommandServerService;
import com.wufan.debug.online.dashboard.socket.config.WebSocketSession;
import com.wufan.debug.online.domain.AgentCommand;
import com.wufan.debug.online.model.AgentCommandEnum;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    private MachineMapper machineMapper;

    @Override
    public void executeCommand(AgentCommand agentCommand) {

    }

    @Override
    public void flushAllMethodInfo(String ip) {
        //获取Ip下所有方法
        QueryWrapper<MethodInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ip", ip);
        List<MethodInfo> methodInfoList = methodMapper.selectList(queryWrapper);


        Map<Long,MethodStack> methodRefMap=new HashMap<>();
        methodInfoList.forEach(methodInfo -> {
            if(methodInfo.getStatus()==0){
                methodRefMap.put(methodInfo.getId(),new MethodStack(methodInfo.getTypeName()+"#"+methodInfo.getMethodName()));
            }
        });
        methodInfoList.forEach(methodInfo -> {
            if(methodInfo.getStatus()==1){
                methodRefMap.get(methodInfo.getPid()).getChildTypeMethod().add(methodInfo.getTypeName()+"#"+methodInfo.getMethodName());
            }
        });


        AgentCommand clearCommand = new AgentCommand(AgentCommandEnum.CLEAR_METHOD, "");
        //发送清空命令
        WebSocketSession.AGENT_CLIENT.sendText(ip, clearCommand);
        //开始添加方法

        methodRefMap.values().forEach(methodStack -> {
            //添加主方法
            String mainContent=methodStack.getTypeMethod();
            AgentCommand addMethod = new AgentCommand(AgentCommandEnum.ADD_METHOD, mainContent);
            WebSocketSession.AGENT_CLIENT.sendText(ip, addMethod);
            //添加子方法
            methodStack.getChildTypeMethod().forEach(childStack->{
                String childContent=childStack+","+mainContent;
                AgentCommand childMethod = new AgentCommand(AgentCommandEnum.ADD_METHOD, childContent);
                WebSocketSession.AGENT_CLIENT.sendText(ip, childMethod);
            });
        });
    }

    @Override
    public String getClientRegexp(String username) {
        QueryWrapper<MachineInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ip", username);
        List<MachineInfo> machineInfos = machineMapper.selectList(queryWrapper);
        if(machineInfos!=null && machineInfos.size()>0){
            return machineInfos.get(0).getRegexp();
        }
        return null;
    }
}
