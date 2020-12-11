# debug-online

#### 介绍
在线web服务调试系统

#### 软件架构
软件架构说明
项目氛围两个主体
1.  debug-online-agent
以跟踪并记录具体数据的项目执行流程数据。通过项目启动时加载该包完成项目数据监控的切入
2.  debug-online-common
基础工具类和通用实体
3.  debug-online-dashboard
以收集agent包传递过来的数据并进行可视化界面展示


#### 安装教程

1.  启动dashboard客户端，直接package或者install 模块 debug-online-dashboard,生成jar包，并按照启动spring-boot的jar启动方式启动即可
2.  添加客户端信息，需要配置具体客户端IP和包路径的匹配正则
3.  下载debug-agent.jar这个包，这个包在dashboard项目resources/static/data目录下
     下载案例：http://localhost:8080/static/data/debug-agent.jar
4.  在具体项目应用中，添加jvm启动参数 -javaagent:debug-agent.jar=127.0.0.1:8080
5.  打开dashboard客户端，可以看到具体机器已经注册到了dashboard上，可以开始进行项目调试

#### 使用说明

1.  方法设置:用来配置用于拦截的方法
2.  子线程关联:用于父子线程无法进行关联，因此在代码层面强制阻塞关联，配置完成后可以用来关联主线程中子线程的任务
3.  点击入参按钮，同时打开浏览器开发控制台，之后会打印出具体参数信息，结构化的JSON数据方便查阅数据
