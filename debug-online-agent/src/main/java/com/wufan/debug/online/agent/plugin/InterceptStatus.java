package com.wufan.debug.online.agent.plugin;

import com.wufan.debug.online.agent.utils.LogTrack;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:11:12:53
 * Description:InterceptStatus.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
public class InterceptStatus {

    /**
     * 主方法集合
     */
    protected static Set<String> methodMainList = new HashSet<>();

    protected static AtomicBoolean switchOff = new AtomicBoolean(false);

    protected static Set<String> methodParamList = new HashSet<>();


    static {
        methodMainList.add("com.jddj.o2o.ApiTest.http_lt1");
        methodMainList.add("com.jddj.o2o.InheritTest#testExe1");

    }

    /**
     * 设置开关状态
     *
     * @param onOff
     */
    public static void setSwitch(boolean onOff) {
        LogTrack.appendLog("方法拦截开关-agent==>>" + onOff);
        switchOff.set(onOff);
    }

    /**
     * 清空主方法集合
     */
    public static void clearMethodList() {
        LogTrack.appendLog("清空主方法集合");
        methodMainList.clear();
    }

    /**
     * 清空主方法集合
     */
    public static void addMethodList(String typeMethod) {
        LogTrack.appendLog("添加主方法集合==>>\t" + typeMethod);
        methodMainList.add(typeMethod);
    }

    public static boolean containMethodList(String typeMethod) {

        //return TrackContext.getRootId()==null;
        return methodMainList.contains(typeMethod);
    }

    /**
     * 清空主方法集合
     */
    public static void clearMethodParamList() {
        LogTrack.appendLog("清空监控参数方法");
        methodParamList.clear();
    }

    /**
     * 去除参数方法
     */
    public static void cancelMethodParamList(String typeMethod) {
        LogTrack.appendLog("取消监控参数方法\t" + typeMethod);
        methodParamList.remove(typeMethod);
    }

    /**
     * 添加
     */
    public static void addMethodParamList(String typeMethod) {
        LogTrack.appendLog("添加监控参数方法==>>\t" + typeMethod);
        methodParamList.add(typeMethod);
    }

    /**
     * 是否包含
     *
     * @param typeMethod
     * @return
     */
    public static boolean containMethodParamList(String typeMethod) {
        //return TrackContext.getRootId()==null;
        return methodParamList.contains(typeMethod);
    }


}
