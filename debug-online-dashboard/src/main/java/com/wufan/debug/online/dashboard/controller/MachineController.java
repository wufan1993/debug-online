package com.wufan.debug.online.dashboard.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wufan.debug.online.dashboard.dao.MachineMapper;
import com.wufan.debug.online.dashboard.domain.MachineInfo;
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
@RequestMapping("/machine")
@Slf4j
public class MachineController extends BaseController {


    @Resource
    private MachineMapper machineMapper;

    @PostMapping("/saveMachine")
    @ResponseBody
    public String saveMachine(MachineInfo machineInfo) {
        boolean res = saveEntity(machineMapper, machineInfo, () -> machineMapper.getMaxId() + 1);
        if (res) {
            return "ok";
        }
        return "fail";
    }


    @GetMapping("/listMachine")
    @ResponseBody
    public Map<String, Object> listMachine() {

        List<MachineInfo> machineInfoList = machineMapper.selectList(new QueryWrapper<>());
        final Map<String, Session> livingSessions = WebSocketSession.AGENT_REMOTE.getLivingSessions();

        machineInfoList.forEach(machineInfo -> {
            machineInfo.setPid(-1L);
            if (livingSessions.containsKey(machineInfo.getIp())) {
                machineInfo.setStatus(1);
            }
        });
        /*List<AgentClient> clientList = null;
        AtomicInteger integer = new AtomicInteger(1);
        clientList = livingSessions.keySet().stream().map(username -> {
            AgentClient client = new AgentClient();
            client.setPid(-1);
            client.setId(integer.getAndIncrement());
            client.setUsername(username);
            return client;
        }).collect(Collectors.toList());*/
        return PackRes.getResult(machineInfoList);
    }

    @GetMapping("/deleteMachine")
    @ResponseBody
    public String deleteMachine(Long id) {
        if(id!=null && id!=0){
            int i = machineMapper.deleteById(id);
            if(i==1){
                return "ok";
            }
        }
        return "fail";
    }
}
