package com.wufan.debug.online.test.method;

import java.util.ArrayList;
import java.util.List;

/**
 * 链路追踪
 * VM options：
 * -javaagent:E:\itstack\GIT\itstack.org\itstack-demo-agent\itstack-demo-agent-06\target\itstack-demo-agent-06-1.0.0-SNAPSHOT.jar=testargs
 * <p>
 * 按需打开需要测试的模块
 * 链路监控
 * pluginGroup.add(new LinkPlugin());
 * Jvm监控
 * pluginGroup.add(new JvmPlugin());
 * <p>
 * <p>
 * <p>
 * <p>
 * create by wufan on 2020
 */
public class InheritTest {

    public static void main(String[] args) {
        InheritTest test = new InheritTest();
        for (int i = 0; i < 100; i++) {
            test.testExe1(i);
        }
    }

    private static void testThreadProcess(int index) {
        testThread(index);
    }

    public static TestObject getObject() {
        TestObject testObject = new TestObject();
        testObject.setName("小鬼");
        List<String> names = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            names.add("小鬼" + i);
        }
        testObject.setList(names);
        return testObject;
    }

    private static void testThread(int index) {
        TestObject testObject = new TestObject();
        testObject.setName("小鬼");
        List<String> names = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            names.add("小鬼" + i);
        }
        testObject.setList(names);
        new Thread(() -> {
            InheritTest inheritTest = new InheritTest();
            inheritTest.test(testObject);
            new Thread(() -> {


                List<String> names2 = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    names2.add("小鬼" + i);
                }
                testObject.setList2(names2);
                InheritTest inheritTest2 = new InheritTest();
                inheritTest2.test(testObject);

                TestObjectSize objectSize = new TestObjectSize();
                objectSize.setTestObject(testObject);
                List<TestObject> objectList = new ArrayList<>();
                objectList.add(testObject);
                objectSize.setTestObjectList(objectList);

                //int mb = ObjectSizeFetcher.getMb(objectSize);
                //System.out.println("输出大小" + mb);

            }).start();
            inheritTest.test3("小黑", index);
        }).start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void testExe4(int i) {
        System.out.println(i);
    }

    private void testExe3(int i) {
        System.out.println(i);
        if (i == 2) {
            throw new RuntimeException("抛出异常");
        }

    }

    private void testExe2(int i) {
        System.out.println(i);

    }

    private void testExe1(int i) {
        System.out.println(i);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        testExe2(i);
        testExe3(i);
        testExe4(i);
    }

    public void test(TestObject testObject) {
        //LinkAdvice.enter("testType1", "testMethod1", null);
        try {
            //System.out.println("执行测试类==>>" + name);
            Thread.sleep(100);
            test2("笨蛋插队");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //LinkAdvice.exit("result");
    }

    public void test2(String name) {
        //LinkAdvice.enter("testType2","testMethod2",null);
        try {
            System.out.println("执行测试类" + name);
            Thread.sleep(100);
            testThreadException();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //LinkAdvice.exit("result2"+name);
    }

    public String test3(String name, int index) {
        //LinkAdvice.enter("testType3","testMethod3",null);

        try {
            System.out.println("执行测试类" + name + index);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return name + " 返回";
        //LinkAdvice.exit("result3");
    }

    public String testThreadException() {
        //LinkAdvice.enter("testType3","testMethod3",null);

        throw new RuntimeException("跑出异常");
        //LinkAdvice.exit("result3");
    }

}
