package com.wufan.debug.online.agent.plugin;

import com.wufan.debug.online.agent.track.NumberIncrease;
import com.wufan.debug.online.agent.track.ProcessAgent;
import com.wufan.debug.online.agent.track.ProcessSendSocket;
import com.wufan.debug.online.agent.track.TrackContext;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:13:12:57
 * Description:MethodIntercept.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
public class MethodIntercept {


    @RuntimeType
    public static Object intercept(@Origin Method method, @SuperCall Callable<?> callable, @AllArguments Object[] args) throws Exception {

        //Thread.currentThread().getStackTrace()[0].
        if (InterceptStatus.switchOff.get()) {
            //如果是accessor 那么直接退出
            if(Thread.currentThread().getStackTrace()[3].getMethodName().contains("$accessor$")){
                return callable.call();
            }

            //为了避免过度输出 因此要记录一下类方法缓存数据
            //是否总方法标志
            Boolean flag = false;

            Boolean childFlag = false;

            //获取方法全路径名称
            String typeMethod = method.getDeclaringClass().getName() + "#" + method.getName();

            //判断当前是否存在阻塞主方法
            if (TrackContext.getRootId() == null) {
                //如果是主方法进入 那么强制设置一个新的rootId
                if (InterceptStatus.containMethodList(typeMethod)) {
                    //如果存在主业务，那么需要阻塞其它任务
                    if (TrackContext.getRootMethodId(typeMethod) != null) {
                        throw new RuntimeException("当前主业务正在调试运行中，请稍后再访问");
                    }
                    TrackContext.initRootId();
                    TrackContext.setStaticRootMethod(typeMethod);
                    flag = true;
                }
            }

            if (TrackContext.getRootId() == null) {
                //判断是不是关联子方法
                String rootMethod = InterceptStatus.getParentMethodList(typeMethod);
                if (rootMethod != null) {
                    String rootMethodId = TrackContext.getRootMethodId(rootMethod);
                    if (rootMethodId != null) {
                        TrackContext.setRootId(rootMethodId);
                        childFlag = true;
                    }
                }
            }

            if (TrackContext.getRootId() == null) {
                //如果没有rootId 那么直接退出，不参与记录日志
                return callable.call();
            }

            //防止出现相同的方法
            Integer methodId = NumberIncrease.getTagId(TrackContext.getRootId());
            if (flag || childFlag) {
                //设置方法内的 本地ID
                TrackContext.setParentId(methodId);
            }

            //方法深度标记
            int status = TrackContext.addMethodTrack(TrackContext.getRootId(), TrackContext.getParentId(), method.getName(), method.getDeclaringClass().getName());

            //直接退出
            if (status == 0) {
                return callable.call();
            }

            //输出方法类名 入参数
            ProcessAgent process = new ProcessAgent(TrackContext.getRootId(), TrackContext.getParentId(), methodId);

            ProcessAgent param = new ProcessAgent(process, 0);
            param.setMethod(method.getName());

            param.setTypeName(method.getDeclaringClass().getName());
            //LogTrack.appendLog("输出一个测试名称======================="+param.getTypeName());
            //判断对象大小
            if (InterceptStatus.containMethodParamList(typeMethod) || flag) {
                param.setArgs(args);
            }

            //发送1
            ProcessSendSocket.toSocketJsonStr(param);

            long start = System.currentTimeMillis();
            Integer oldExtendId = TrackContext.getExtendId();
            try {
                // 原有函数执行
                TrackContext.setExtendId(methodId);
                Object call = callable.call();
                //输出出参数
                ProcessAgent processReturn = new ProcessAgent(process, 1);
                processReturn.setCostTime(System.currentTimeMillis() - start);
                processReturn.setRes(call);

                if (status == 2) {
                    processReturn.setMessage(processReturn.getMessage() + "调用深度+");
                }
                //发送第二步
                ProcessSendSocket.toSocketJsonStr(processReturn);
                return call;
            } catch (Throwable e) {
                //输出执行异常
                ProcessAgent processError = new ProcessAgent(process, 2);
                processError.setCostTime(System.currentTimeMillis() - start);
                processError.setMessage(e.getMessage());
                //发送第二步
                ProcessSendSocket.toSocketJsonStr(processError);
                throw e;
            } finally {
                TrackContext.setExtendId(oldExtendId);
                if (flag) {
                    TrackContext.removeStaticRootMethod(typeMethod);
                    TrackContext.removeRootMethodTrack(TrackContext.getRootId());
                }
                if (flag || childFlag) {
                    TrackContext.removeCacheId();
                }
            }
        }
        return callable.call();
    }


}
