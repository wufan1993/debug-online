package com.wufan.debug.online.agent.track;

import java.time.LocalTime;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:11:12:16
 * Description:ProcessAgent.java
 *
 * @author wufan02
 * @since JDK 1.8
 * 欲穷千里目 更上一层楼
 */
public class ProcessAgent {

    private String rootId;  //链路ID
    private Integer pid; //父ID
    private Integer id; //方法ID
    private Integer type; // 0代表进入 1代表出去 2代表异常

    //进入
    private String method;
    private String typeName;
    private Object[] args;

    //出去
    private Object res;
    private Long costTime;
    //异常
    private String message="";

    //时间参数
    private String enterTime;

    public ProcessAgent(String rootId, Integer pid, Integer id) {
        this.rootId = rootId;
        this.pid = pid;
        this.id = id;
        this.enterTime = LocalTime.now().toString();
    }

    public ProcessAgent(ProcessAgent agent, Integer type) {
        this.rootId = agent.getRootId();
        this.pid = agent.getPid();
        this.id = agent.getId();
        this.enterTime = agent.getEnterTime();
        this.type = type;
    }


    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(String enterTime) {
        this.enterTime = enterTime;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Object getRes() {
        return res;
    }

    public void setRes(Object res) {
        this.res = res;
    }

    public Long getCostTime() {
        return costTime;
    }

    public void setCostTime(Long costTime) {
        this.costTime = costTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
