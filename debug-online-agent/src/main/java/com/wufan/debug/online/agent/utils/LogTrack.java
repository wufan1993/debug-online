package com.wufan.debug.online.agent.utils;

import java.time.LocalTime;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:13:12:27
 * Description:LogTrack.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
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
