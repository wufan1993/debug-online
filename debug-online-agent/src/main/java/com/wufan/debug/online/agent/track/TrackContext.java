package com.wufan.debug.online.agent.track;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:13:12:18
 * Description:TrackContext.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
public class TrackContext {

    private static final InheritableThreadLocal<String> trackRoot = new InheritableThreadLocal<>();

    private static final ThreadLocal<Integer> trackParent = new ThreadLocal<>();

    private static final InheritableThreadLocal<Integer> trackExtend = new InheritableThreadLocal<>();
    private static final ThreadLocal<Map<String, Object>> preTypeMethodData = new ThreadLocal<>();
    private static Map<String, Map<String, AtomicLong>> methodTrack = new HashMap<>();

    /**
     * 0 代表不记录 1代表通过 2代表超过拒绝
     *
     * @param rootId
     * @param parentId
     * @param typeName
     * @param methodName
     * @return
     */
    public static int addMethodTrack(String rootId, Integer parentId, String typeName, String methodName) {

        Map<String, AtomicLong> adderMap = methodTrack.computeIfAbsent(rootId, k -> new HashMap<>());
        AtomicLong atomicLong = adderMap.computeIfAbsent(parentId + typeName + methodName, k -> new AtomicLong(0L));
        long andIncrement = atomicLong.getAndIncrement();
        //通过
        if (andIncrement < 2) {
            return 1;
        }
        //临界点
        if (andIncrement == 2) {
            return 2;
        }
        //拒绝通过
        return 0;
    }

    /**
     * 移除记录
     *
     * @param rootId
     */
    public static void removeRootMethodTrack(String rootId) {
        methodTrack.remove(rootId);
    }

    /**
     * 移除记录
     */
    public static void clearRootTrack() {
        methodTrack.clear();
    }


    public static void clearRootId() {
        trackRoot.remove();
        //ThreadContext.remove("rootId");
    }

    public static String getRootId() {
        return trackRoot.get();
    }

    public static void setRootId(String linkId) {
        //ThreadContext.put("rootId",linkId);
        trackRoot.set(linkId);
    }

    public static void clearParentId() {
        trackParent.remove();
        //ThreadContext.remove("parentId");
    }

    public static Integer getParentId() {
        return trackParent.get();
    }

    public static void setParentId(Integer linkId) {
        trackParent.set(linkId);
        //ThreadContext.put("parentId",linkId);
    }

    public static void clearExtendId() {
        trackExtend.remove();
    }

    public static Integer getExtendId() {
        return trackExtend.get();
    }

    public static void setExtendId(Integer linkId) {
        trackExtend.set(linkId);
    }

    public static void removeCacheId() {
        TrackContext.removeRootMethodTrack(TrackContext.getRootId());
        TrackContext.clearRootId();
        TrackContext.clearParentId();
        TrackContext.clearExtendId();
    }

    /**
     * 比较上一个方法是否执行过同样的过程 如果是 那么就不用记录了
     *
     * @param typeMethod
     * @param args
     * @return
     */
    public static boolean getContinueNext(String typeMethod, Object[] args) {
        Map<String, Object> preTypeMethod = preTypeMethodData.get();
        Boolean flag = true;

        if (preTypeMethod != null && preTypeMethod.containsKey("typeMethod") && preTypeMethod.containsKey("args")) {
            String typeMethodPre = (String) preTypeMethod.get("typeMethod");
            Object[] argsPre = (Object[]) preTypeMethod.get("args");

            if (typeMethod.equals(typeMethodPre) && args == null && argsPre == null) {
                flag = false;
            }
            if (args != null && argsPre != null && typeMethod.equals(typeMethodPre) && (args == argsPre)) {
                flag = false;
            }
        } else {
            preTypeMethod = new HashMap<>();
            preTypeMethodData.set(preTypeMethod);
        }
        //记录上一条数据
        preTypeMethod.put("typeMethod", typeMethod);
        preTypeMethod.put("args", args);
        return flag;
    }
}
