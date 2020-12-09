package com.wufan.debug.online.dashboard.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
/**
 * 我本非凡
 * Date:2020-12-03
 * Time:17:12:29
 * Description:MachineInfo.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
@Data
public class MachineInfo {


    @TableField(exist = false)
    private Long pid;
    private Long id;
    /**
     * 机器IP
     */
    private String ip;
    /**
     * 应用名称
     */
    private String name;
    /**
     * 机器描述
     */
    private String desc;

    //============机器返回信息=============
    /**
     * 机器状态 0表示丢失 1表示在线
     */
    private int status;

    /**
     * 进入时间
     */
    @TableField(exist = false)
    private String enterTime;
    /**
     * 丢失时间
     */
    @TableField(exist = false)
    private String lostTime;



    //============获取机器信息=============
    /**
     * 正则切入控制
     */
    private String regexp;
}
