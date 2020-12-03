package com.wufan.debug.online.agent.track;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:13:12:28
 * Description:NumberIncrease.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
public class NumberIncrease {

    private static Map<String, AtomicLong> tagMap = new HashMap<>(1200);

    private static DelayQueue<Task> delayQueue = new DelayQueue<>();


    /**
     * 获取流程编号
     *
     * @param inheritId
     */
    public static synchronized int getTagId(String inheritId) {
        //如果超过指定数量 那么需要清空 防止内存溢出
        if (tagMap.size() > 450) {
            clearTagMap();
            if (tagMap.size() > 900) {
                tagMap.clear();
                delayQueue.clear();
            }
        }
        AtomicLong atomicLong = tagMap.computeIfAbsent(inheritId, k -> {
            //设置5秒超时
            Task task = new Task(k, 5);
            delayQueue.add(task);
            return new AtomicLong();
        });
        return (int) atomicLong.getAndIncrement();
    }

    private synchronized static void clearTagMap() {
        try {
            Task take = delayQueue.take();
            String uuid = take.getUuid();
            tagMap.remove(uuid);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
