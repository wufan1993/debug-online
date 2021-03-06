package com.wufan.debug.online.test.method;

import java.util.ArrayList;
import java.util.List;

/**
 * 我本非凡
 * Date:2020-12-15
 * Time:13:12:25
 * Description:TestObject.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
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
