package com.wufan.debug.online.dashboard.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wufan.debug.online.dashboard.dao.MachineMapper;
import com.wufan.debug.online.dashboard.domain.MachineInfo;
import com.wufan.debug.online.dashboard.socket.config.WebSocketSession;
import com.wufan.debug.online.dashboard.util.PackRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.util.*;
import java.util.stream.Collectors;

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


    /**
     * 未连接机器集合
     */
    //public static Set<String> lostIpList=new HashSet<>();

    @Resource
    private MachineMapper machineMapper;

    @PostMapping("/saveMachine")
    @ResponseBody
    public String saveMachine(@Validated MachineInfo machineInfo) {
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
        final Map<String, Session> livingSessions = WebSocketSession.AGENT_CLIENT.getLivingSessions();

        machineInfoList = machineInfoList.stream().filter(machineInfo -> {
            machineInfo.setPid(-1L);
            if (livingSessions.containsKey(machineInfo.getIp())) {
                machineInfo.setStatus(1);
            }
            //测试主方法用
            //machineInfo.setStatus(1);
            //return true;
            return machineInfo.getId() != 1;
        }).collect(Collectors.toList());
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

    @GetMapping("/listLostMachine")
    @ResponseBody
    public Map<String, Object> listLostMachine() {

        List<MachineInfo> machineInfoList = machineMapper.selectList(new QueryWrapper<>());
        Set<String> dbMachineList=machineInfoList.stream().map(MachineInfo::getIp).collect(Collectors.toSet());
        final Map<String, Session> livingSessions = WebSocketSession.AGENT_CLIENT.getLivingSessions();
        List<String> lostIpList= new ArrayList<>(livingSessions.keySet());

        List<String> lostIpListFilter=lostIpList.stream().filter(ip-> !dbMachineList.contains(ip)).collect(Collectors.toList());

        List<MachineInfo> lostMachineInfoList=new ArrayList<>();
        for (int i = 0; i < lostIpListFilter.size(); i++) {
            MachineInfo machineInfo=new MachineInfo();
            machineInfo.setPid(-1L);
            machineInfo.setId((long) (i + 1));
            machineInfo.setIp(lostIpListFilter.get(i));
            lostMachineInfoList.add(machineInfo);
        }
        return PackRes.getResult(lostMachineInfoList);
    }

    @GetMapping("/deleteMachine")
    @ResponseBody
    public String deleteMachine(Long id) {
        if (id != null && id != 0) {
            int i = machineMapper.deleteById(id);
            if (i == 1) {
                return "ok";
            }
        }
        return "fail";
    }
}
