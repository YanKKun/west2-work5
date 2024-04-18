package com.yk.pojo.ws;


import com.alibaba.druid.sql.visitor.functions.Now;
import com.alibaba.fastjson.JSON;
import com.yk.config.WebsocketConfig;
import com.yk.pojo.Content;
import com.yk.pojo.LoginUser;
import com.yk.service.ChatService;
import com.yk.service.GroupService;
import com.yk.service.UserService;
import com.yk.utils.WebSocketUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author 12080
 */
@ServerEndpoint(value = "/ws/chat",configurator = WebsocketConfig.class)
@Component
@Slf4j
public class ChatEndpoint {


    @Setter
    private static ApplicationContext applicationContext;


    /**
     * 建立websocket连接后，被调用
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) throws IOException {
        log.info("连接");
            Map<String, Object> userProperties = session.getUserProperties();
            LoginUser loginUser = (LoginUser) userProperties.get("loginUser");
            WebSocketUtils webSocketUtils = applicationContext.getBean(WebSocketUtils.class);
            webSocketUtils.addSession(session);
            webSocketUtils.broadcastAllPeople("当前在线人数:"+webSocketUtils.onlineNum()+"人"+ LocalDateTime.now());
            webSocketUtils.broadcastAllContacts(loginUser.getUser().getId(),loginUser.getUser().getNickname()+"闪亮登场"+ LocalDateTime.now());
    }



    /**
     * @param message
     */
    @OnMessage(maxMessageSize = 5000000)
    public void onMessage(Session session,String message) throws IOException {
        log.info("接收到的信息：{}",message);
        if("heartbeat".equals(JSON.parseObject(message, Content.class).getContent())){
            session.getBasicRemote().sendText("heartBeat");
        }else {
            applicationContext.getBean(WebSocketUtils.class).sendMessage(session,message);
        }
    }

    /**
     * 断开websocket连接时被调用
     * @param session
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        log.info("断开");
        WebSocketUtils webSocketUtils = applicationContext.getBean(WebSocketUtils.class);
        Map<String, Object> userProperties = session.getUserProperties();
        LoginUser loginUser = (LoginUser) userProperties.get("loginUser");
        webSocketUtils.removeSession(session);
        webSocketUtils.broadcastAllPeople("当前在线人数:"+webSocketUtils.onlineNum()+"人"+ LocalDateTime.now());
        webSocketUtils.broadcastAllContacts(loginUser.getUser().getId(),loginUser.getUser().getNickname()+"闪亮退场"+ LocalDateTime.now());
    }
}
