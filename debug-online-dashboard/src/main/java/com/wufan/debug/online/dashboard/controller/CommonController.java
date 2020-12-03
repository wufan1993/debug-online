package com.wufan.debug.online.dashboard.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:13:12:50
 * Description:CommonController.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
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
