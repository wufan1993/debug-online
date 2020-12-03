package com.wufan.debug.online.dashboard;

import com.ibeetl.starter.BeetlSqlSingleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:13:12:54
 * Description:O2oAgentDashboardApplication.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
@SpringBootApplication(exclude = {BeetlSqlSingleConfig.class})
@ComponentScan("com.wufan.debug.online.dashboard")
public class O2oAgentDashboardApplication extends WebMvcConfigurationSupport {

    public static void main(String[] args) {

        SpringApplication.run(O2oAgentDashboardApplication.class, args);
    }

}
