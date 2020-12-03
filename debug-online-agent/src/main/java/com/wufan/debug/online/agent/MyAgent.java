package com.wufan.debug.online.agent;

import com.wufan.debug.online.agent.plugin.MethodIntercept;
import com.wufan.debug.online.agent.socket.ShExecuteClient;
import com.wufan.debug.online.agent.utils.LogTrack;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.MethodList;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:13:12:33
 * Description:MyAgent.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
public class MyAgent {

    public static String remoteHost = "preagent.jddj.com";

    //JVM 首先尝试在代理类上调用以下方法
    public static void premain(String agentArgs, Instrumentation inst) {
        String regexp = null;
        if (agentArgs != null && !agentArgs.equals("")) {
            if (agentArgs.indexOf("&&") > 0) {
                //设置正则
                regexp = agentArgs.split("&&")[0];
                //设置远程服务
                remoteHost = agentArgs.split("&&")[1];
            } else {
                regexp = agentArgs;
            }
        }
        if (regexp == null) {
            LogTrack.appendLog("当前服务未配置扫描包数据");
            return;
        }

        //清除日志文件
        LogTrack.removeLog();


        new Thread(() -> {
            //启动socket客户端
            try {
                LogTrack.appendLog("this is my agent：" + "启动远程监听客户端");
                ShExecuteClient.init();
            } catch (Exception e) {
                LogTrack.appendLog("启动远程客户端失败" + e.getMessage());
            }
        }).start();

        LogTrack.appendLog("this is my agent：" + regexp);

        //com.jd.o2o.search.tools.(service|web).*
        //-javaagent:/export/data/agentjddj.jar='com.jd.o2o.web.product.(web.controller|rpc|service).*'
        //-javaagent:/export/data/agentjddj.jar='com.jd.o2o.search.index.(dao|service|web.mq).*'
        //-javaagent:/export/data/agentjddj.jar='com.jd.o2o.search.center.(client.service|service).*'
        //-javaagent:/export/data/agentjddj.jar='com.jd.o2o.search.center.service.(saf|brand|category|intent|qc|search|skuattr|sort|store).*'

        //抽象类不拦截 静态方法不拦截
        String packagePrefix = regexp;

        AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule) -> {
            return builder
                    //.method(ElementMatchers.any()) // 拦截任意方法
                    .method(ElementMatchers.not(ElementMatchers.nameStartsWith("main"))
                            .and(ElementMatchers.not(ElementMatchers.isAbstract()))
                            .and(ElementMatchers.not(ElementMatchers.isStatic()))
                            .and(ElementMatchers.not(ElementMatchers.isGetter()))
                            .and(ElementMatchers.not(ElementMatchers.isSetter()))

                    ) // 拦截任意方法
                    .intercept(MethodDelegation.to(MethodIntercept.class)); // 委托
        };

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {
                System.out.println("onTransformation PreMainAgent get loaded typeDescription:" + typeDescription.getCanonicalName());

                MethodList<MethodDescription.InDefinedShape> declaredMethods = typeDescription.getDeclaredMethods();
                declaredMethods.forEach(methodDescription -> {
                    System.out.println("onTransformation PreMainAgent get loaded dynamicType:" + methodDescription.getActualName());
                    methodDescription.getDeclaringType().getCanonicalName();
                });
                //System.out.println("onTransformation PreMainAgent get loaded dynamicType:" + typeDescription.getCanonicalName());
            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {
                //System.out.println("onIgnored PreMainAgent get loaded dynamicType:" + typeDescription.getCanonicalName());
            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {
                System.out.println("net.bytebuddy.agent.builder.AgentBuilder.Listener.onError:" + s + "异常:" + throwable.getMessage());
            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {
                //System.out.println("onComplete PreMainAgent get loaded dynamicType:" + s);
            }

        };

        new AgentBuilder
                .Default()
                .type(ElementMatchers.nameMatches(packagePrefix)
                                .and(ElementMatchers.not(ElementMatchers.nameContains("$$")))
                                .and(ElementMatchers.not(ElementMatchers.isAbstract()))
                                .and(ElementMatchers.not(ElementMatchers.isAnnotation()))
                                .and(ElementMatchers.not(ElementMatchers.isInterface()))
                                .and(ElementMatchers.not(ElementMatchers.isEnum()))
                        /*.and(ElementMatchers.not(ElementMatchers.isStatic()))*/

                ) // 指定需要拦截的类
                .transform(transformer)
                .with(listener)
                .installOn(inst);

    }


    //如果代理类没有实现上面的方法，那么 JVM 将尝试调用该方法
    public static void premain(String agentArgs) {
    }

}
