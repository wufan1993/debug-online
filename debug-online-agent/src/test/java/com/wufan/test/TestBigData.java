package com.wufan.test;

import com.wufan.debug.online.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 我本非凡
 * Date:2020-12-11
 * Time:10:12:38
 * Description:TestBigData.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
public class TestBigData {


    public static void main(String[] args) {
        TestBigData test = new TestBigData();
        for (int i = 0; i < 100; i++) {

            TestObject testObject = new TestObject();
            testObject.setName("小鬼");
            List<String> names = new ArrayList<>();
            for (int j = 0; j < 15; j++) {
                names.add("小鬼" + j);
            }
            testObject.setList(names);
            testObject.setList2(names);

            TestObjectSize objectSize = new TestObjectSize();
            objectSize.setTestObject(testObject);
            List<TestObject> objectList = new ArrayList<>();
            for(int k=0;k<30000;k++){
                objectList.add(testObject.newObject());
            }
            objectSize.setTestObjectList(objectList);

            test.testThread(objectSize);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void testThread(TestObjectSize objectSize) {
        System.out.println("执行大对象");
        long start=System.currentTimeMillis();
        String res=JsonUtils.toJsonStr(objectSize);
        System.out.println("字符串长度=》》"+res.length());
        System.out.println("字符串长度=》》"+res.getBytes().length);
        System.out.println("花费时间=》》"+(System.currentTimeMillis()-start));
    }
}
