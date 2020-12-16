package com.wufan.debug.online.test.method;

import java.util.List;

/**
 * 我本非凡
 * Date:2020-12-15
 * Time:13:12:30
 * Description:TestObjectSize.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
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
