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

    private String command;

    private String content;


    public AgentCommand() {
    }

    public AgentCommand(AgentCommandEnum commandEnum) {
        this.command = commandEnum.getCommand();
    }

    public AgentCommand(AgentCommandEnum commandEnum, String content) {
        this.command = commandEnum.getCommand();
        this.content = content;
    }


    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
