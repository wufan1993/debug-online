package com.wufan.test;

import java.util.ArrayList;
import java.util.List;

/**
 * 上海京东到家元信信息技术有限公司
 * Date:2020-11-26
 * Time:09:11:58
 * Description:TestObject.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Copyright (c) 2020 imdada System Incorporated All Rights Reserved.
 */
public class TestObject {

    private String name;

    private List<String> list;

    private List<String> list2;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public List<String> getList2() {
        return list2;
    }

    public void setList2(List<String> list2) {
        this.list2 = list2;
    }

    public TestObject newObject() {
        TestObject object=new TestObject();
        object.setName(this.name);
        object.setList(new ArrayList<>(this.list));
        object.setList2(new ArrayList<>(this.list2));
        return object;
    }

}
