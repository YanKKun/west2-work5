package com.yk.filter;

import com.yk.pojo.LoginUser;
import com.yk.utils.JwtUtil;
import com.yk.utils.RedisCache;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * token认证拦截器
 * @author 12080
 */
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().contains("/ws/chat")){
            log.info("ws协议");
            String token = request.getHeader("Sec-WebSocket-Protocol");
            if (!StringUtils.hasText(token)) {
                // 放行
                filterChain.doFilter(request, response);
                return;
            }

            // 解析Token
            String userid;
            try {
                Claims claims = JwtUtil.parseJWT(token);
                userid = claims.getSubject();
            } catch (Exception e) {

                e.printStackTrace();
                throw new RuntimeException("token非法");
            }

            // 从redis中获取用户信息
            String rediskey = "login:" + userid;
            LoginUser loginUser = redisCache.getCacheObject(rediskey);

            if (Objects.isNull(loginUser)) {
                throw new RuntimeException("用户未登录");
            }

            // 存入SecurityConTextHolder
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            filterChain.doFilter(request, response);
        }else{
        // 获取Token
            log.info("http协议");
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            // 放行
            filterChain.doFilter(request, response);
            return;
        }

        // 解析Token
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException("token非法");
        }

        // 从redis中获取用户信息
        String rediskey = "login:" + userid;
        LoginUser loginUser = redisCache.getCacheObject(rediskey);

        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("用户未登录");
        }

        // 存入SecurityConTextHolder
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        filterChain.doFilter(request, response);
        }
    }
}
