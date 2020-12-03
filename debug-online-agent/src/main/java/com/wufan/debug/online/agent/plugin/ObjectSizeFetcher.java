package com.wufan.debug.online.agent.plugin;

import com.carrotsearch.sizeof.RamUsageEstimator;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:11:12:04
 * Description:ObjectSizeFetcher.java
 *
 * @author wufan02
 * @since JDK 1.8
 * 欲穷千里目 更上一层楼
 */
public class ObjectSizeFetcher {

    public static int getMb(Object obj) {

        if (obj == null) {
            return 0;
        }
        //计算指定对象本身在堆空间的大小，单位字节
        long byteCount = RamUsageEstimator.sizeOf(obj);
        //long byteCount = RamUsageEstimator.shallowSizeOf(obj);
        if (byteCount == 0) {
            return 0;
        }
        double oneMb = 1 * 1024 * 1024;
        if (byteCount < oneMb) {
            return 1;
        }
        Double v = Double.valueOf(byteCount) / oneMb;
        return v.intValue();
    }

}