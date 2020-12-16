package com.wufan.debug.online.test.method;

/**
 * 我本非凡
 * Date:2020-12-16
 * Time:21:12:31
 * Description:DaDaType.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
public abstract class DaDaType extends BabaType{



    protected String protectMethod(String name) {
        System.out.println("protectMethod==>>>" + name);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        publicMethod(name+"++");


        return "protectMethod==>>>" + name;
    }

    protected String protectMethod(String name,String name2) {
        //System.out.println("protectMethod==>>>" + name);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        publicMethod(name+"++");


        return "protectMethod==>>>" + name;
    }

}
