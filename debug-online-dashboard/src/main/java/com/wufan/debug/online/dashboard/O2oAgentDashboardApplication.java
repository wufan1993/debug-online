package com.wufan.debug.online.dashboard;

import com.ibeetl.starter.BeetlSqlSingleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 上海京东到家元信信息技术有限公司
 * Date:2019-06-27
 * Time:17:13
 * Description:启动服务类
 *
 * @author wufan wufan02@imdada.cn
 * @since JDK 1.8
 * Copyright (c) 2019 imdada System Incorporated All Rights Reserved.
 */
@SpringBootApplication(exclude = {BeetlSqlSingleConfig.class})
@ComponentScan("com.wufan.debug.online.dashboard")
public class O2oAgentDashboardApplication extends WebMvcConfigurationSupport {

    public static void main(String[] args) {

        SpringApplication.run(O2oAgentDashboardApplication.class, args);
    }

}
