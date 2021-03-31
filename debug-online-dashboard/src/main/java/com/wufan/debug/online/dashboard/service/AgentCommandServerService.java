package com.wufan.debug.online.dashboard.service;

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
public interface AgentCommandServerService {

    void flushAllMethodInfo(String ip);

    String getClientRegexp(String username);
}
