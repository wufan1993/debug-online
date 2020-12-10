package com.wufan.debug.online.model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

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


    /*=========client端枚举=============*/
    CLIENT_CONFIG("clientConfig", "client", "获取客户端配置数据"),
    METHOD_DATA("methodData", "client", "发送数据"),

    /*=========server端枚举=============*/
    CLIENT_REGEXP("clientRegexp", "server", "客户端正则匹配"),

    OPEN_CLIENT("openClient", "server", "启动监控"),
    CLOSE_CLIENT("closeClient", "server", "关闭监控"),

    CLEAR_METHOD("clearMethod", "server", "清空方法"),
    ADD_METHOD("addMethod", "server", "添加方法"),

    CLEAR_MONITOR_METHOD("clearAgentMethod", "dashboard", "清空监控参数方法"),
    ADD_MONITOR_METHOD("addAgentMethod", "dashboard", "添加监控参数方法"),
    REMOVE_MONITOR_METHOD("removeAgentMethod", "dashboard", "移除监控参数方法"),


    /*=========dashboard端枚举=============*/
    MONITOR_METHOD("monitorMethod", "dashboard", "监控方法"),

    ;

    private String command;

    private String dignity;

    private String desc;

    private Consumer<String> consumer;

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

    public void setConsumer(Consumer<String> consumer) {
        this.consumer = consumer;
    }

    public void executeCommand(String content){
        consumer.accept(content);
    }

    public String getCommand() {
        return command;
    }

    public String getDesc() {
        return desc;
    }
}
