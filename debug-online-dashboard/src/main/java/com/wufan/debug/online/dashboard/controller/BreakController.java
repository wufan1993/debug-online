package com.wufan.debug.online.dashboard.controller;

import com.wufan.debug.online.dashboard.domain.BreakInfo;
import com.wufan.debug.online.dashboard.service.impl.BreakMethodServiceImpl;
import com.wufan.debug.online.dashboard.util.PackRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:13:12:46
 * Description:AgentController.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
@Controller
@RequestMapping("/break")
@Slf4j
public class BreakController extends BaseController {

    @Resource
    private BreakMethodServiceImpl breakMethodServiceImpl;


    @GetMapping("/listBreakList")
    public String listBreakList(Model model, String username) {
        model.addAttribute("ip", username);
        return "/modules/agent/breakList.html";
    }

    @GetMapping("/listBreak")
    @ResponseBody
    public Map<String, Object> listBreak(String ip) {
        List<BreakInfo> methodInfoList = breakMethodServiceImpl.getBreakMethodByIp(ip);
        methodInfoList.forEach(breakInfo -> breakInfo.setId(0L));
        return PackRes.getResult(methodInfoList);
    }
}
