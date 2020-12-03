package com.wufan.debug.online.agent.plugin;

import com.wufan.debug.online.agent.track.NumberIncrease;
import com.wufan.debug.online.agent.track.ProcessAgent;
import com.wufan.debug.online.agent.track.ProcessSendSocket;
import com.wufan.debug.online.agent.track.TrackContext;
import com.wufan.debug.online.agent.utils.LogTrack;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
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
        if (InterceptStatus.switchOff.get()) {
            //为了避免过度输出 因此要记录一下类方法缓存数据
            //是否总方法标志
            Boolean flag = false;

            //如果是主方法进入 那么强制设置一个新的rootId
            String typeMethod = method.getDeclaringClass().getName() + "#" + method.getName();

            if (InterceptStatus.containMethodList(typeMethod)) {
                TrackContext.removeCacheId();
                //如果不是子线程
                String rootId = UUID.randomUUID().toString();
                TrackContext.setRootId(rootId);
                flag = true;
            }

            if (TrackContext.getRootId() == null) {
                //如果没有rootId 那么直接退出，不参与记录日志
                return callable.call();
            } else {
                //判断是不是子线程
                Integer extendId = TrackContext.getExtendId();
                if (extendId != null) {
                    //如果是子线程初始化
                    if (TrackContext.getParentId() == null || !TrackContext.getParentId().equals(extendId)) {
                        TrackContext.setParentId(extendId);
                    }
                }
            }

            //如果入参 和方法一样 那么就不进行记录了 通过入参+类型方法去判断
            if (!TrackContext.getContinueNext(typeMethod, args)) {
                return callable.call();
            }

            //防止出现相同的方法
            Integer methodId = NumberIncrease.getTagId(TrackContext.getRootId());
            if (flag) {
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
                param.setArgs(Arrays.stream(args).filter(Objects::nonNull).map(arg -> {
                    long l = ObjectSizeFetcher.getMb(arg);
                    if (l > 50) {
                        //不一定管用
                        //如果大于15M 则不记录
                        LogTrack.appendLog("获取入参对象大小==》》》》\t" + arg.getClass().toString() + "大小\t" + l + "M");
                        return arg.getClass().toString() + "参数\t" + l;
                    }
                    return arg;
                }).toArray());
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

                if (InterceptStatus.containMethodParamList(typeMethod) || flag) {
                    long l = ObjectSizeFetcher.getMb(call);
                    if (l > 15) {
                        LogTrack.appendLog("获取返回对象大小==》》》》\t" + call.getClass().toString() + "大小\t" + l + "M");
                        processReturn.setMessage(call.getClass().toString() + "大对象\t" + l);
                    } else {
                        processReturn.setRes(call);
                    }
                }

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
                    TrackContext.removeCacheId();
                }
            }
        }
        return callable.call();
    }


}
