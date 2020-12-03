# debug-online

#### 介绍
在线web服务调试系统

#### 软件架构
软件架构说明
项目氛围两个主体
1.  debug-online-agent
以跟踪并记录具体数据的项目执行流程数据。通过项目启动时加载该包完成项目数据监控的切入
2.  debug-online-dashboard
以收集agent包传递过来的数据并进行可视化界面展示


#### 安装教程

1.  启动dashboard客户端，直接package或者install 模块 debug-online-dashboard,生成jar包，并按照启动spring-boot的jar启动方式启动即可
2.  下载debug-agent.jar这个包，这个包在dashboard项目resources/static/data目录下
3.  在具体项目应用中，添加jvm启动参数 -javaagent:debug-agent.jar='regexp'
3.  打开dashboard客户端，可以看到具体机器已经注册到了dashboard上，可以开始进行项目调试

#### 使用说明

1.  xxxx
2.  xxxx
3.  xxxx
