package com.wufan.test;

/**
 * 我本非凡
 * Date:2020-12-04
 * Time:10:12:50
 * Description:TestException.java
 *
 *
 * 添加jvm启动参数
 * -javaagent:/Users/wufan02/Java/projects/debug-online/debug-online-agent/target/debug-agent.jar=com.wufan.test
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
public class TestException {


    public static void main(String[] args) {
        TestException test = new TestException();
        for (int i = 0; i < 100; i++) {
            test.testExe1(i);
        }
    }

    private void testExe4(int i) {
        System.out.println(i);
    }

    private void testExe3(int i) {
        System.out.println(i);
        if (i == 50) {
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
}
