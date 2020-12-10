package com.wufan.debug.online.dashboard.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
@NoArgsConstructor
public class MethodStack {

    private String typeMethod;

    private List<String> childTypeMethod=new ArrayList<>();

    public MethodStack(String typeMethod) {
        this.typeMethod = typeMethod;
    }
}
