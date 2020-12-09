package com.wufan.debug.online.dashboard.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wufan.debug.online.dashboard.dao.MethodMapper;
import com.wufan.debug.online.dashboard.domain.MethodInfo;
import com.wufan.debug.online.dashboard.socket.config.WebSocketSession;
import com.wufan.debug.online.dashboard.util.PackRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.websocket.Session;
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
@RequestMapping("/method")
@Slf4j
public class MethodController {


    @Resource
    private MethodMapper methodMapper;

    @PostMapping("/saveMethod")
    @ResponseBody
    public String saveMethod(MethodInfo methodInfo) {
        //不考虑并发问题，以数据库跑出异常来控制
        methodInfo.setId(methodMapper.getMaxId() + 1);
        try {
            int insert = methodMapper.insert(methodInfo);
            if (insert == 1) {
                return "ok";
            }
        } catch (Exception e) {
            log.error("出现异常", e);
        }
        return "fail";
    }

    @GetMapping("/listMethod")
    @ResponseBody
    public Map<String, Object> listMethod() {

        List<MethodInfo> methodInfoList = methodMapper.selectList(new QueryWrapper<>());
        /*List<AgentClient> clientList = null;
        AtomicInteger integer = new AtomicInteger(1);
        clientList = livingSessions.keySet().stream().map(username -> {
            AgentClient client = new AgentClient();
            client.setPid(-1);
            client.setId(integer.getAndIncrement());
            client.setUsername(username);
            return client;
        }).collect(Collectors.toList());*/
        return PackRes.getResult(methodInfoList);
    }
}
