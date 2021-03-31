package com.wufan.debug.online.dashboard.service;

import com.wufan.debug.online.dashboard.domain.BreakInfo;

import java.util.List;

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
public interface BreakMethodService {


    void flushSynMethod(String username);

    void insertOne(String username, String typeMethod);

    void deleteOne(String username, String typeMethod);

    List<BreakInfo> getBreakMethodByIp(String ip);
}
