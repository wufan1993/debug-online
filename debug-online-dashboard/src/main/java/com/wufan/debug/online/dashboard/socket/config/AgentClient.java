package com.wufan.debug.online.dashboard.socket.config;

import lombok.Data;

/**
 * 上海京东到家元信信息技术有限公司
 * Date:2020-11-05
 * Time:14:11:49
 * Description:Span.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Copyright (c) 2020 imdada System Incorporated All Rights Reserved.
 */
@Data
public class AgentClient {

    private Integer id;
    private Integer pid;

    private String username;  //链路ID


}
