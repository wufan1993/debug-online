package com.wufan.debug.online.agent.socket;

import com.wufan.debug.online.agent.DebugAgent;
import com.wufan.debug.online.agent.plugin.InterceptStatus;
import com.wufan.debug.online.agent.service.AgentCommandClientService;
import com.wufan.debug.online.agent.track.ProcessSendSocket;
import com.wufan.debug.online.agent.track.TrackContext;
import com.wufan.debug.online.agent.utils.LogTrack;
import com.wufan.debug.online.domain.AgentCommand;
import com.wufan.debug.online.model.AgentCommandEnum;
import com.wufan.debug.online.utils.JsonUtils;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:13:12:08
 * Description:ShExecuteClient.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
public class ShExecuteClient {

    private static final String WS_URI = "ws://%s/socket/agentRemote/%s";
    //private static final String WS_URI = "ws://127.0.0.1:8080/socket/agentRemote/%s";

    private static AtomicBoolean switchStatus = new AtomicBoolean(false);


    public static void main(String[] args) {
        ShExecuteClient.init();
    }


    public static void init() {
        //创建一个循环
        while (true) {
            try {
                //获取机器IP
                InetAddress addr = InetAddress.getLocalHost();
                String ip = addr.getHostAddress(); //获取本机ip

                // 此处的WebSocket服务端URI，上面服务端第2点有详细说明
                String url = String.format(WS_URI, DebugAgent.remoteHost, ip);
                if (!switchStatus.get()) {
                    WebSocketClient client = ProcessSendSocket.getClient();
                    if (client == null || !client.isOpen()) {
                        LogTrack.appendLog("尝试去建立连接: " + url);
                        openSocket(url);
                    }
                }
            } catch (Throwable e) {
                LogTrack.appendLog("建立连接异常" + e.getMessage());
            }
            //暂停5秒
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                LogTrack.appendLog("线程中断异常" + e.getMessage());
            }
        }
    }

    public static void openSocket(String url) {
        URI serverUri = null;
        try {
            serverUri = new URI(url);
        } catch (URISyntaxException e) {
            LogTrack.appendLog("建立连接异常URL" + e.getMessage());
        }

        if (serverUri != null) {
            WebSocketClient client = new WebSocketClient(serverUri) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    LogTrack.appendLog("open 客户端" + url);
                    switchStatus.set(true);
                    //移除所有方法记录
                    TrackContext.clearRootTrack();
                    AgentCommand agentCommand=new AgentCommand(AgentCommandEnum.CLIENT_CONFIG);
                    ProcessSendSocket.sentAgentCommand(agentCommand);
                }

                @Override
                public void onMessage(String command) {
                    try {
                        AgentCommandClientService.executeCommand(JsonUtils.fromJson(command,AgentCommand.class));
                    } catch (Exception e) {
                        LogTrack.appendLog("处理命令失败" + command + "===》》异常" + e.getMessage());
                    }
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    LogTrack.appendLog("close 客户端" + url + "param1:" + i + "\tparam2:" + s + "\tparam3:" + b);
                    switchStatus.set(false);
                    InterceptStatus.setSwitch(false);
                    //移除所有方法记录
                    TrackContext.clearRootTrack();
                }

                @Override
                public void onError(Exception e) {
                    LogTrack.appendLog("客户端执行发生异常" + e.getMessage());
                }
            };
            client.connect();
            ProcessSendSocket.setWebSocketClient(client);
        }
    }
}