package com.wufan.test;

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
public class ApiTest extends BabaType {

    public static void main(String[] args) {

        for (int i = 0; i < 1; i++) {
            new Thread(() -> new ApiTest().http_lt1("哪咤")).start();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //线程一


        //线程二
        /*new Thread(() -> {
            new ApiTest().http_lt2("悟空");
        }).start();*/

    }

    public void http_lt1(String name) {

        //http_lt2("蓝蓝");

        protectMethod("小红");

        /*try {
            Thread.sleep((long) (Math.random() * 500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //publicMethod("public小蓝");
        /*System.out.println("测试结果：hi1 " + name);
        http_lt2(name);
        //线程二
        new Thread(() -> {
            new ApiTest().http_lt2("悟空");
        }).start();*/
    }

    public void http_lt2(String name) {
        try {
            Thread.sleep((long) (Math.random() * 500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("测试结果：hi2 " + name);
        //http_lt3(name);
    }

    private void http_lt3(String name) {
        try {
            Thread.sleep((long) (Math.random() * 500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("测试结果：hi3 " + name);

    }

}
