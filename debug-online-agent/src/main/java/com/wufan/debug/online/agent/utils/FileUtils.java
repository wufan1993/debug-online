package com.wufan.debug.online.agent.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.function.Consumer;

/**
 * 上海京东到家元信信息技术有限公司
 * Date:2020-11-24
 * Time:18:11:55
 * Description:FileUtils.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Copyright (c) 2020 imdada System Incorporated All Rights Reserved.
 */
public class FileUtils {

    /**
     * 写文件
     *
     * @param path
     */
    public static void writeFile(String path, Consumer<Consumer<String>> channelConsumer) {
        try {
            FileOutputStream fos = new FileOutputStream(path, true);
            FileChannel channel = fos.getChannel();

            Consumer<String> fileChannelConsumer = line -> {
                byte[] bytes = line.getBytes();

                ByteBuffer buf = ByteBuffer.wrap(bytes);
                buf.put(bytes);
                buf.flip();
                try {
                    channel.write(buf);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            channelConsumer.accept(fileChannelConsumer);
            channel.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeLogFile(String path) {
        try {
            // Specify the file name and path
            File file = new File(path);
              /* the delete() method return true if the file
              deleted successfully else it return false
               */
            file.delete();
        } catch (Exception e) {
            System.out.println("Exception occured"+e.getMessage());
        }
    }
}
