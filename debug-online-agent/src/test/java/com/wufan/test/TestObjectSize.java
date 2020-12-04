package com.wufan.test;

import java.util.List;

/**
 * 上海京东到家元信信息技术有限公司
 * Date:2020-11-26
 * Time:09:11:32
 * Description:TestObjectSize.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Copyright (c) 2020 imdada System Incorporated All Rights Reserved.
 */
public class TestObjectSize {

    private TestObject testObject;


    private List<TestObject> testObjectList;

    private String testName;

    public TestObject getTestObject() {
        return testObject;
    }

    public void setTestObject(TestObject testObject) {
        this.testObject = testObject;
    }

    public List<TestObject> getTestObjectList() {
        return testObjectList;
    }

    public void setTestObjectList(List<TestObject> testObjectList) {
        this.testObjectList = testObjectList;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }
}
