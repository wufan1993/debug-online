package com.wufan.debug.online.dashboard.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我本非凡
 * Date:2020-12-09
 * Time:10:12:20
 * Description:PackRes.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
public class PackRes {

    public static Map<String, Object> getResult(List<?> data) {
        Map<String, Object> object = new HashMap<>();
        object.put("code", 0);
        object.put("msg", "ok");
        if (data == null) {
            object.put("data", new ArrayList<>());
            object.put("count", 0);
        } else {
            object.put("data", data);
            object.put("count", data.size());
        }
/*        if (data == null) {
            object.put("code", -1);
            object.put("msg", "失败");
            return object;
        } else {
            object.put("code", 0);
            object.put("msg", "ok");
            object.put("data", data);
            object.put("count", data.size());
        }*/
        return object;
    }
}
