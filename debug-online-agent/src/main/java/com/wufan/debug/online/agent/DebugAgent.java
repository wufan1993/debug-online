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
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:13:12:33
 * Description:DebugAgent.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
public class DebugAgent {

    public static String remoteHost = "127.0.0.1:8080";

    public static String packagePrefix;

    public static final CountDownLatch latch = new CountDownLatch(1);

    //JVM 首先尝试在代理类上调用以下方法
    public static void premain(String agentArgs, Instrumentation inst) {
        remoteHost = agentArgs;

        //清除日志文件
        LogTrack.removeLog();

        //无限循环线程
        new Thread(() -> {
            //启动socket客户端
            try {
                LogTrack.appendLog("this is my agent：" + "启动远程监听客户端");
                ShExecuteClient.init();
            } catch (Exception e) {
                LogTrack.appendLog("启动远程客户端失败" + e.getMessage());
            }
        }).start();

        //暂停 5 秒 钟
        try {
            LogTrack.appendLog("暂停5秒钟 等候socket连接....");
            latch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LogTrack.appendLog("系统异常" + e.getMessage());
        }

        if (packagePrefix == null) {
            LogTrack.appendLog("未获取到服务配置信息,监控系统未建立");
            return;
        }
        LogTrack.appendLog("this is my agent：" + packagePrefix);

        //抽象类不拦截 静态方法不拦截
        try {
            AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule) -> {

                LogTrack.appendLog("transformer load:" + typeDescription.getCanonicalName());

                //TypeDescription.Generic superClass = typeDescription.getNestHost().getSuperClass();

                ElementMatcher.Junction<MethodDescription> methodIntercept = ElementMatchers.isMethod()/*.and(ElementMatchers.isSynthetic())*/
                        //.and(ElementMatchers.not(ElementMatchers.isVirtual().and(ElementMatchers.isProtected())))
                        .and(
                                ElementMatchers.not(ElementMatchers.nameStartsWith("main"))
                                        .and(ElementMatchers.not(ElementMatchers.isAbstract()))
                                        .and(ElementMatchers.not(ElementMatchers.isStatic()))
                                        .and(ElementMatchers.not(ElementMatchers.isGetter()))
                                        .and(ElementMatchers.not(ElementMatchers.isSetter()))
                                        .and(ElementMatchers.not(ElementMatchers.isBridge()))
                                        .and(ElementMatchers.not(ElementMatchers.isSynthetic()))
                                        .and(ElementMatchers.not(ElementMatchers.isConstructor()))
                                //.and(ElementMatchers.not(ElementMatchers.isVirtual()))
                        )
                    /*.or(
                            ElementMatchers.isVirtual()
                    )*/
                        //.and(ElementMatchers.not(ElementMatchers.nameContains("$accessor$")))
                        //.and(ElementMatchers.not(ElementMatchers.nameContains("$original$")))
                        //.and(ElementMatchers.not(ElementMatchers.nameStartsWith("<init>")))
                        //.and(ElementMatchers.not(ElementMatchers.nameStartsWith("lambda$main$0")))

                        //.and(ElementMatchers.not(ElementMatchers.isTypeInitializer()))
                        //.and(ElementMatchers.not(ElementMatchers.isTypeInitializer()))
                        ;

                    /*if(superClass!=null){
                        methodIntercept.and(ElementMatchers.not(ElementMatchers.isAccessibleTo(superClass.asErasure())));
                    }*/

                DynamicType.Builder.MethodDefinition.ImplementationDefinition<?> main = builder
                        //.method(ElementMatchers.any()) // 拦截任意方法
                        .method(methodIntercept
                                //.and(ElementMatchers.not(ElementMatchers.isTypeInitializer()))
                                //.and(ElementMatchers.not(ElementMatchers.isAccessibleTo()))
                                //.and(ElementMatchers.not(ElementMatchers.isStrict()))

                                    /*.and(ElementMatchers.not(ElementMatchers.isDeclaredByGeneric(TypeDescription.Generic.CLASS)))
                                    .and(ElementMatchers.not(ElementMatchers.isDeclaredByGeneric(TypeDescription.Generic.OBJECT)))
                                    .and(ElementMatchers.not(ElementMatchers.isDeclaredByGeneric(TypeDescription.Generic.UNDEFINED)))
                                    .and(ElementMatchers.not(ElementMatchers.isDeclaredByGeneric(TypeDescription.Generic.VOID)))*/

                                //.and(ElementMatchers.not(ElementMatchers.isProtected()))
                                //.and(ElementMatchers.not(ElementMatchers.isVirtual()))
                        );

                return main.intercept(MethodDelegation.to(MethodIntercept.class)); // 委托
            };

            AgentBuilder.Listener listener = new AgentBuilder.Listener() {
                @Override
                public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

                }

                @Override
                public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {
                    LogTrack.appendLog("onTransformation PreMainAgent get loaded typeDescription:" + typeDescription.getCanonicalName());

                    MethodList<MethodDescription.InDefinedShape> declaredMethods = typeDescription.getDeclaredMethods();
                    declaredMethods.forEach(methodDescription -> {
                        //System.out.println("onTransformation load:" +typeDescription.getCanonicalName()+"#"+ methodDescription.getActualName());
                        //methodDescription.getDeclaringType().getCanonicalName();
                    });
                    //System.out.println("onTransformation PreMainAgent get loaded dynamicType:" + typeDescription.getCanonicalName());
                }

                @Override
                public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {
                    //System.out.println("onIgnored PreMainAgent get loaded dynamicType:" + typeDescription.getCanonicalName());
                }

                @Override
                public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {
                    LogTrack.appendLog("net.bytebuddy.agent.builder.AgentBuilder.Listener.onError:" + s + "异常:" + throwable.getMessage());
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
                                    .and(ElementMatchers.not(ElementMatchers.nameContains("aspect")))
                                    //.and(ElementMatchers.not(ElementMatchers.isAbstract()))
                                    .and(ElementMatchers.not(ElementMatchers.isAnnotation()))
                                    .and(ElementMatchers.not(ElementMatchers.isInterface()))
                                    .and(ElementMatchers.not(ElementMatchers.isEnum())
                                            //.and(ElementMatchers.not(ElementMatchers.isDeclaredByGeneric(TypeDescription.Generic.UNDEFINED)))
                                    )
                            /*.and(ElementMatchers.not(ElementMatchers.isStatic()))*/

                    ) // 指定需要拦截的类
                    .transform(transformer)
                    .with(listener)
                    .installOn(inst);
        } catch (Throwable e) {
            LogTrack.appendLog("系统JVM-AGENT控制异常" + e.getMessage());
        }
    }


    //如果代理类没有实现上面的方法，那么 JVM 将尝试调用该方法
    public static void premain(String agentArgs) {
    }

}
