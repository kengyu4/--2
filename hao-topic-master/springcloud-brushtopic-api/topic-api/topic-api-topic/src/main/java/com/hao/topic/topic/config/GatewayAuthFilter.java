package com.hao.topic.topic.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 处理网关传递的用户信息，设置Spring Security认证上下文
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GatewayAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // 从网关传递的header中获取用户信息
        String username = request.getHeader("username");
        String role = request.getHeader("role");
        String userId = request.getHeader("userId");
        
        log.info("GatewayAuthFilter - username: {}, role: {}, userId: {}", username, role, userId);
        
        if (username != null && !username.isEmpty() && !"null".equals(username)) {
            // 网关已验证，设置认证信息
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if (role != null && !role.isEmpty() && !"null".equals(role)) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("已设置认证信息 - Username: {}, Role: {}", username, role);
        }
        
        filterChain.doFilter(request, response);
    }
}
