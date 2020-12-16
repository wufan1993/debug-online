package com.wufan.debug.online.test.method;

public abstract class DaDaType extends BabaType{



    protected String protectMethod(String name) {
        //System.out.println("protectMethod==>>>" + name);

        try {
            Thread.sleep(500);
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
