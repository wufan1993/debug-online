package com.wufan.debug.online.dashboard.domain;

import lombok.Data;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:18:12:37
 * Description:MethodInfo.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
@Data
public class MethodInfo {


    private int pid;

    private int id;

    /**
     * 所属机器IP
     */
    private String ip;
    /**
     * 类名
     */
    private String typeName;
    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 接入类型 主方法 1
     * 接入类型 子方法 2
     */
    private String type;


}
