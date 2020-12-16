package com.wufan.debug.online.test;

import com.wufan.debug.online.test.method.ApiTest;
import com.wufan.debug.online.test.method.TestException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:17:12:54
 * Description:O2oAgentDashboardApplication.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
@SpringBootApplication
@ComponentScan("com.wufan.debug.online.test")
public class O2oAgentTestApplication extends WebMvcConfigurationSupport {

    public static void main(String[] args) {

        SpringApplication.run(O2oAgentTestApplication.class, args);


        new Thread(() -> {
            while (true) {
                try {
                    ApiTest apiTest = new ApiTest();
                    apiTest.http_lt1("测试调用");
                    System.out.println("测试调用完成");
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    TestException test = new TestException();
                    for (int i = 0; i < 100; i++) {
                        test.testExe1(i);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
