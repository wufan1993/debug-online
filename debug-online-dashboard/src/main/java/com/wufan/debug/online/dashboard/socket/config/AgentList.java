package com.wufan.debug.online.dashboard.socket.config;

import lombok.Data;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:13:12:17
 * Description:AgentList.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
@Data
public class AgentList {

    private Integer id;
    private Integer pid;

    private String username;  //链路ID


    private String rootId;

    private String typeName;

    private String methodName;

    private String enterName;

    private Object[] args;

}
