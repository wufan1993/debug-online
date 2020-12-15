package com.wufan.test;

public class BabaType {

/*
    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            new Thread(() -> new BabaType().protectMethod("哪咤")).start();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //线程一


        //线程二
        *//*new Thread(() -> {
            new ApiTest().http_lt2("悟空");
        }).start();*//*

    }*/




    public String publicMethod(String name) {

        //privateMethod(name+"--");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("protectStaticMethod==>>>" + name);
        return "publicMethod==>>>" + name;
    }

    public String publicMethod(String name,String name2) {


        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("protectStaticMethod==>>>" + name);
        return "protectMethod==>>>" + name;
    }

}
