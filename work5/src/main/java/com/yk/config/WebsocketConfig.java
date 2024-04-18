package com.yk.config;

import com.yk.pojo.LoginUser;
import com.yk.utils.JwtUtil;
import com.yk.utils.RedisCache;
import io.jsonwebtoken.Claims;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.List;
import java.util.Map;

@Component
public class WebsocketConfig extends ServerEndpointConfig.Configurator {


    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebsocketConfig.applicationContext = applicationContext;
    }

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        super.modifyHandshake(sec, request, response);
        String token;
        Map<String, Object> userProperties = sec.getUserProperties();
        List<String> list = request.getHeaders().get("Sec-WebSocket-Protocol");

        token=list.get(0);
        // 解析Token
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        RedisCache bean = applicationContext.getBean(RedisCache.class);
        // 从redis中获取用户信息
        String rediskey = "login:" + userid;
        LoginUser loginUser = bean.getCacheObject(rediskey);
        userProperties.put("loginUser",loginUser);
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

}
