package com.wufan.debug.online.dashboard.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wufan.debug.online.dashboard.dao.BreakMapper;
import com.wufan.debug.online.dashboard.dao.MethodMapper;
import com.wufan.debug.online.dashboard.domain.BreakInfo;
import com.wufan.debug.online.dashboard.domain.MethodInfo;
import com.wufan.debug.online.dashboard.service.AgentCommandServerService;
import com.wufan.debug.online.dashboard.socket.config.WebSocketSession;
import com.wufan.debug.online.dashboard.socket.server.AgentRemoteServerEndpoint;
import com.wufan.debug.online.dashboard.util.PackRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.util.HashMap;
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
public class MethodController extends BaseController{

    @Resource
    private MethodMapper methodMapper;

    @Resource
    private AgentCommandServerService agentCommandServerService;

    @GetMapping("/enterMethodList")
    public String listMethod(Model model, String ip) {
        model.addAttribute("ip", ip);
        return "/modules/agent/methodList.html";
    }

    @PostMapping("/saveMethod")
    @ResponseBody
    public String saveMethod(@Validated MethodInfo methodInfo) {

        if(methodInfo!=null){
            if(methodInfo.getPid()!=-1){
                methodInfo.setStatus(1);
            }
            boolean res = saveEntity(methodMapper, methodInfo, () -> methodMapper.getMaxId() + 1);
            if (res) {
                //发送机器同步命令 //todo
                agentCommandServerService.flushAllMethodInfo(methodInfo.getIp());
                return "ok";
            }
        }
        return "fail";
    }

    @GetMapping("/listMethod")
    @ResponseBody
    public Map<String, Object> listMethod(String ip) {

        QueryWrapper<MethodInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ip",ip);
        List<MethodInfo> methodInfoList = methodMapper.selectList(queryWrapper);
        return PackRes.getResult(methodInfoList);
    }

    @GetMapping("/deleteMethod")
    @ResponseBody
    public String deleteMethod(Long id) {
        if(id!=null && id!=0){
            MethodInfo methodInfo = methodMapper.selectById(id);
            int i = methodMapper.deleteById(id);
            if(i==1){
                agentCommandServerService.flushAllMethodInfo(methodInfo.getIp());
                return "ok";
            }
        }
        return "fail";
    }
}
