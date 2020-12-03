package com.wufan.debug.online.dashboard.socket.config;

import lombok.Data;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:13:12:21
 * Description:ProcessAgent.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
@Data
public class ProcessAgent {

    private Integer id;
    private Integer pid;

    private String rootId;  //链路ID
    //private String parentId; //父ID
    //private String methodId; //方法ID
    private Integer type; // 0代表进入 1代表出去 2代表异常
    //private String typeDesc; // 0代表进入 1代表出去 2代表异常

    //进入
    private String method;
    private String typeName;
    private Object[] args;
    //出去
    private Object res;
    private Long costTime;
    //异常
    private String message;

    //时间参数
    private String enterTime;


    private boolean debugPort;

}
