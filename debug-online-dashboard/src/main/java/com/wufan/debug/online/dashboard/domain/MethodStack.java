package com.wufan.debug.online.dashboard.domain;

import lombok.Data;

import java.util.List;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:20:12:00
 * Description:MethodStack.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
@Data
public class MethodStack {

    /**
     * 操作模式
     * 0非阻塞 非阻塞条件下忽略子方法
     * 1阻塞 阻塞模式下当前主方法执行任务只能唯一顺序通过
     */
    private Integer model;

    /**
     * 主方法
     */
    private MethodInfo methodInfo;

    /**
     * 关联子进程方法
     */
    private List<MethodInfo> childMethodList;


}
