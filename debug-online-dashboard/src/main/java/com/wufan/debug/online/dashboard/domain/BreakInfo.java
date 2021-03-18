package com.wufan.debug.online.dashboard.domain;

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
public class BreakInfo {


    private Long id;

    /**
     * 所属机器IP
     */
    private String ip;

    /**
     * 类名
     */
    @NotBlank(message = "断点内容不能为空")
    private String breakContent;

}
