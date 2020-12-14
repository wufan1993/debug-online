package com.wufan.debug.online.dashboard.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.validation.constraints.NotBlank;

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

    /**
     * 表格使用 默认为 -1
     */
    private Long pid;

    private Long id;

    /**
     * 所属机器IP
     */
    private String ip;
    /**
     * 类名
     */
    @NotBlank(message = "类全名称必输")
    private String typeName;
    /**
     * 方法名称
     */
    @NotBlank(message = "方法名称必输")
    private String methodName;

    /**
     * 接入类型 主方法 0
     * 接入类型 子方法 1
     */
    private Integer status;

    /**
     * 排序字段
     */
    @TableField(exist = false)
    private int rank;

}
