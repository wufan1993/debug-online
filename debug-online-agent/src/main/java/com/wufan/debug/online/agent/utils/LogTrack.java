package com.wufan.debug.online.agent.utils;

import java.time.LocalTime;

/**
 * 上海京东到家元信信息技术有限公司
 * Date:2020-11-24
 * Time:18:11:16
 * Description:LogTrack.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Copyright (c) 2020 imdada System Incorporated All Rights Reserved.
 */
public class LogTrack {


    private static String path = "/export/Logs/agent.log";

    private static String patten = "[%s]\t[%s]\n";

    public synchronized static void appendLog(String log) {
        FileUtils.writeFile(path, stringConsumer -> stringConsumer.accept(String.format(patten, LocalTime.now().toString(), log)));
    }

    public synchronized static void removeLog() {
        FileUtils.removeLogFile(path);
    }


}
