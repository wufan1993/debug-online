package com.wufan.debug.online.dashboard.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 上海京东到家元信信息技术有限公司
 * Date:2019-07-19
 * Time:20:58
 * Description:CommonController.java
 *
 * @author wufan wufan02@imdada.cn
 * @since JDK 1.8
 * Copyright (c) 2019 imdada System Incorporated All Rights Reserved.
 */
@Controller
@RequestMapping("/")
@Slf4j
public class CommonController {

    @GetMapping("/")
    public String all(Model model) {
        return "modules/agent/agentClient.html";
    }

    @GetMapping("/{modules}/{path}")
    public String index(@PathVariable("modules") String modules, @PathVariable("path") String path) {

        return "modules/" + modules + "/" + path + ".html";
    }

}
