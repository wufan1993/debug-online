package com.wufan.debug.online.dashboard.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wufan.debug.online.dashboard.dao.BreakMapper;
import com.wufan.debug.online.dashboard.domain.BreakInfo;
import com.wufan.debug.online.dashboard.service.BreakMethodService;
import com.wufan.debug.online.dashboard.socket.config.WebSocketSession;
import com.wufan.debug.online.domain.AgentCommand;
import com.wufan.debug.online.model.AgentCommandEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 我本非凡
 * Date:2021-03-31
 * Time:15:03:23
 * Description:BreakMethodCache.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
@Slf4j
@Service
public class BreakMethodServiceImpl implements BreakMethodService {
    //key=>ip,value=>方法集合
    public static Map<String, List<String>> breakMethodMap = new ConcurrentHashMap<>();


    @Resource
    private BreakMapper breakMapper;

    /**
     * 刷新全部方法内存缓存
     *
     * @param username
     */
    @Override
    public synchronized void flushSynMethod(String username) {
        List<BreakInfo> breakMethodList = getBreakMethodByIp(username);
        List<String> collect = breakMethodList.stream().map(BreakInfo::getBreakContent).collect(Collectors.toList());
        breakMethodMap.put(username, collect);
        //清空监控方法列表
        WebSocketSession.AGENT_CLIENT.sendText(username, new AgentCommand(AgentCommandEnum.CLEAR_MONITOR_METHOD));
        //添加方法列表
        collect.forEach(typeMethod->{
            log.info("开启IP方法断点{} {}",username,typeMethod);
            WebSocketSession.AGENT_CLIENT.sendText(username, new AgentCommand(AgentCommandEnum.ADD_MONITOR_METHOD,typeMethod));
        });
    }

    @Override
    public void insertOne(String username, String typeMethod) {
        BreakInfo breakInfo = new BreakInfo();
        breakInfo.setBreakContent(typeMethod);
        breakInfo.setIp(username);
        breakMapper.insert(breakInfo);
        //更新本地缓存
        List<String> userBreakList = breakMethodMap.computeIfAbsent(username,k->new ArrayList<>());
        userBreakList.add(typeMethod);
        log.info("开启IP方法断点{} {}",username,typeMethod);
        WebSocketSession.AGENT_CLIENT.sendText(username, new AgentCommand(AgentCommandEnum.ADD_MONITOR_METHOD,typeMethod));

    }

    @Override
    public void deleteOne(String username, String typeMethod) {
        QueryWrapper<BreakInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("break_content", typeMethod);
        queryWrapper.eq("ip", username);
        breakMapper.delete(queryWrapper);
        //更新本地缓存
        List<String> userBreakList = breakMethodMap.computeIfAbsent(username,k->new ArrayList<>());
        userBreakList.remove(typeMethod);
        //给远程端口发送开启发送命令
        log.info("关闭IP方法断点{} {}",username,typeMethod);
        WebSocketSession.AGENT_CLIENT.sendText(username, new AgentCommand(AgentCommandEnum.REMOVE_MONITOR_METHOD,typeMethod));
    }

    @Override
    public List<BreakInfo> getBreakMethodByIp(String ip) {
        QueryWrapper<BreakInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ip", ip);
        List<BreakInfo> methodInfoList = breakMapper.selectList(queryWrapper);
        if (methodInfoList == null) {
            return new ArrayList<>();
        }
        return methodInfoList;
    }
}
