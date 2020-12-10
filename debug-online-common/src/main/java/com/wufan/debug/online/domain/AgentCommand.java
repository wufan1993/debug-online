package com.wufan.debug.online.domain;

import com.wufan.debug.online.model.AgentCommandEnum;

/**
 * 我本非凡
 * Date:2020-12-09
 * Time:14:12:26
 * Description:AgentCommand.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
public class AgentCommand {

    private AgentCommandEnum commandEnum;

    private String content;

    public AgentCommandEnum getCommandEnum() {
        return commandEnum;
    }


    public AgentCommand(AgentCommandEnum commandEnum) {
        this.commandEnum = commandEnum;
    }

    public AgentCommand(AgentCommandEnum commandEnum, String content) {
        this.commandEnum = commandEnum;
        this.content = content;
    }

    public void setCommandEnum(AgentCommandEnum commandEnum) {
        this.commandEnum = commandEnum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
