# debug-online

#### 介绍
在线web服务调试系统,不生产数据，只做数据的搬运工，可以起到类似于远程调试的效果，再也不用加参数日志啦！
1.  实时查看方法入参出参
2.  实时查看方法调用链
3.  实时查看方法执行性能+异常信息

#### 软件架构
软件架构说明
项目分为两个主体
1.  debug-online-agent
以跟踪并记录具体数据的项目执行流程数据。通过项目启动时加载该包完成项目数据监控的切入
2.  debug-online-common
基础工具类和通用实体
3.  debug-online-dashboard
以收集agent包传递过来的数据并进行可视化界面展示
4.  debug-online-test
测试模块，简单启动一个无限循环任务，来监测数据


#### 安装教程

1.  修改debug-online-dashboard 下配置文件 application-*.properties文件中的机器agent.remoteHost参数，值为启动机器的域名地址
2.  直接package或者install 模块 debug-online-dashboard,生成jar包，按照启动spring-boot的jar启动方式部署到启动机器上,启动环境根据需要选择
3.  访问客户端http://localhost:8080，添加客户端信息，需要配置具体客户端IP和包路径的匹配正则(正则如：com.wufan.debug.online.test.method.*)
4.  下载debug-agent.jar这个包，这个包在dashboard项目resources/static/data目录下
     下载案例：http://localhost:8080/static/data/debug-agent.jar
5.  在具体项目应用中，添加jvm启动参数 -javaagent:debug-agent.jar=客户端地址(如:49.232.171.225:8080)
6.  打开dashboard客户端，可以看到具体机器已经注册到了dashboard上，可以开始进行项目调试

#### 使用说明

1.  方法设置:用来配置用于拦截的方法
2.  子线程关联:由于父子线程无法进行关联，因此在代码层面强制阻塞关联，配置完成后可以用来关联主线程中子线程的任务
3.  点击入参按钮，同时打开浏览器开发控制台，之后会打印出具体参数信息，结构化的JSON数据方便查阅数据

#### 基础设计思路流程
1.  由agent去监控所有配置正则下的类和方法
2.  通过web控制端去控制方法执行时是否进行监控
3.  agent收集完成后把数据通过socket发送到dashboard端进行存储
4.  dashboard实时将收集到的数据吐到web端页面进行实时展现


#### 技术体系
1.  后端框架:springboot+websocket
2.  数据库:mybatis-plus+h2
2.  前端:beetl模版引擎+layui前端组件
3.  核心功能-字节码编程：byte-buddy

#### 接入注意事项
1.  由于实时方法数据拦截和监控，多少会产生性能损耗，因此个人建议不要在线上大流量环境下接入此插件系统，以免影响系统性能和稳定。
2.  本工具系统主要适用于预发或者灰度少流量的线上环境，当然本地也可以，但是本地用debug岂不是更香

#### 设计开发缘由

由于公司内部系统非常多，耦合复杂，难以形成统一的测试调试环境，每次调试都需要添加日志上线，而线上机器环境无法连通进行远程debug，因此自行开发了一套实时Web端调试系统。



