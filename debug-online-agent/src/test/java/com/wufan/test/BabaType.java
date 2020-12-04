package com.wufan.test;

public class BabaType {


    protected String protectMethod(String name) {
        System.out.println("protectMethod==>>>" + name);
        return "protectMethod==>>>" + name;
    }


    public String publicMethod(String name) {
        System.out.println("protectStaticMethod==>>>" + name);
        return "protectMethod==>>>" + name;
    }
}
