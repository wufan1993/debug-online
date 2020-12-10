package com.wufan.debug.online.agent.service;

import com.wufan.debug.online.agent.plugin.InterceptStatus;
import com.wufan.debug.online.agent.utils.LogTrack;
import com.wufan.debug.online.domain.AgentCommand;
import com.wufan.debug.online.model.AgentCommandEnum;
import com.wufan.debug.online.utils.JsonUtils;

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
public class AgentCommandClientService {

    static {
        //初始化枚举处理方法

        //操作客户端监控状态
        AgentCommandEnum.OPEN_CLIENT.setConsumer(s -> InterceptStatus.setSwitch(true));

        AgentCommandEnum.CLOSE_CLIENT.setConsumer(s -> InterceptStatus.setSwitch(false));

        //操作主方法
        AgentCommandEnum.CLEAR_METHOD.setConsumer(s -> InterceptStatus.clearMethodList());

        AgentCommandEnum.ADD_METHOD.setConsumer(InterceptStatus::addMethodList);

        //操作参数方法
        AgentCommandEnum.ADD_MONITOR_METHOD.setConsumer(InterceptStatus::addMethodParamList);

        AgentCommandEnum.REMOVE_MONITOR_METHOD.setConsumer(InterceptStatus::cancelMethodParamList);

        AgentCommandEnum.CLEAR_MONITOR_METHOD.setConsumer(command -> InterceptStatus.clearMethodParamList());

    }

    /**
     * 执行脚本
     *
     * @param agentCommand
     */
    public static void executeCommand(AgentCommand agentCommand) {
        AgentCommandEnum enumByCommand = AgentCommandEnum.getEnumByCommand(agentCommand.getCommand());
        LogTrack.appendLog(String.format("操作=>%s\t命令%s\t数据:%s",enumByCommand.getDesc(),enumByCommand.getCommand(),agentCommand.getContent()));
        enumByCommand.executeCommand(agentCommand.getContent());
    }
}
