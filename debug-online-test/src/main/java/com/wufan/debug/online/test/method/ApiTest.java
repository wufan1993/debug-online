package com.wufan.debug.online.test.method;

/**
 * 我本非凡
 * Date:2020-12-16
 * Time:21:12:23
 * Description:ApiTest.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
public class ApiTest extends DaDaType {

    public static void main(String[] args) {

        for (int i = 0; i < 1000; i++) {
            new Thread(() -> new ApiTest().http_lt1("哪咤")).start();
            try {
                Thread.sleep(2000);
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

        privateMethod("private");

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

    private String privateMethod(String name) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("protectStaticMethod==>>>" + name);

        publicMethod(name+"private+public");
        return "privateMethod==>>>" + name;
    }

}
