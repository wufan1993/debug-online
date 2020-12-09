package com.wufan.debug.online.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 我本非凡
 * Date:2020-12-09
 * Time:14:12:57
 * Description:AgentCommandEnum.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
public enum AgentCommandEnum {


    OPEN_CLIENT("open", "client", "启动客户端");

    private String command;

    private String dignity;

    private String desc;

    AgentCommandEnum(String command, String dignity, String desc) {
        this.dignity = dignity;
        this.command = command;
        this.desc = desc;
    }


    private static Map<String, AgentCommandEnum> map = new HashMap<String, AgentCommandEnum>();

    static {
        for (AgentCommandEnum item : AgentCommandEnum.values()) {
            map.put(item.command, item);
        }
    }

    public static AgentCommandEnum getEnumByCommand(String command) {
        return map.get(command);
    }
}
