package com.wufan.test;

public abstract class DaDaType extends BabaType{



    protected String protectMethod(String name) {
        //System.out.println("protectMethod==>>>" + name);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        publicMethod(name+"++");


        return "protectMethod==>>>" + name;
    }

    protected String protectMethod(String name,String name2) {
        //System.out.println("protectMethod==>>>" + name);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        publicMethod(name+"++");


        return "protectMethod==>>>" + name;
    }

}
