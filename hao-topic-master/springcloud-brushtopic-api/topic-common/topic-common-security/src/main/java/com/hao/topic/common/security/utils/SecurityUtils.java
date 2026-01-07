package com.hao.topic.common.security.utils;

import com.hao.topic.common.enums.ResultCodeEnum;
import com.hao.topic.common.exception.TopicException;
import com.hao.topic.common.utils.JWTUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

/**
 * 认证工具类
 */
public class SecurityUtils {
    /**
     * 获取当前登录id
     */
    public static Long getCurrentId() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            // 优先从网关传递的header中获取
            String userId = attributes.getRequest().getHeader("userId");
            if (userId != null && !userId.isEmpty() && !"null".equals(userId)) {
                try {
                    return Long.parseLong(userId);
                } catch (NumberFormatException e) {
                    // 忽略解析错误
                }
            }
            
            // 从token中获取
            String token = attributes.getRequest().getHeader("Authorization");
            if (token != null) {
                Map<String, Object> tokenInfo = JWTUtils.getTokenInfo(token);
                String idValue = (String) tokenInfo.get("id");
                if (idValue != null) {
                    return Long.parseLong(idValue);
                }
            }
        }
        throw new TopicException(ResultCodeEnum.LOGIN_ERROR);
    }

    /**
     * 获取当前登录用户名
     *
     * @return
     */
    public static String getCurrentName() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            // 优先从网关传递的header中获取
            String username = attributes.getRequest().getHeader("username");
            if (username != null && !username.isEmpty() && !"null".equals(username)) {
                return username;
            }
            
            // 从token中获取
            String token = attributes.getRequest().getHeader("Authorization");
            if (token != null) {
                Map<String, Object> tokenInfo = JWTUtils.getTokenInfo(token);
                String usernameFromToken = (String) tokenInfo.get("username");
                if (usernameFromToken != null) {
                    return usernameFromToken;
                }
            }
        }
        throw new TopicException(ResultCodeEnum.LOGIN_ERROR);
    }

    /**
     * 获取当前登录用户角色
     */
    public static String getCurrentRole() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            // 优先从网关传递的header中获取
            String role = attributes.getRequest().getHeader("role");
            if (role != null && !role.isEmpty() && !"null".equals(role)) {
                return role;
            }
            
            // 从token中获取
            String token = attributes.getRequest().getHeader("Authorization");
            if (token != null) {
                Map<String, Object> tokenInfo = JWTUtils.getTokenInfo(token);
                String roleFromToken = (String) tokenInfo.get("role");
                if (roleFromToken != null) {
                    return roleFromToken;
                }
            }
        }
        throw new TopicException(ResultCodeEnum.LOGIN_ERROR);
    }
}