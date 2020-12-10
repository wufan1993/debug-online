package com.wufan.debug.online.dashboard.service;

import com.wufan.debug.online.domain.AgentCommand;
import org.springframework.stereotype.Component;

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
public interface AgentCommandDashboardService {


    /**
     * 执行脚本
     * @param agentCommand
     */
    public void executeCommand(AgentCommand agentCommand);
}
