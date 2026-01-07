package com.hao.topic.admin.gateway.filter;


import com.hao.topic.admin.gateway.properties.IgnoreWhiteProperties;
import com.hao.topic.common.utils.JWTUtils;
import com.hao.topic.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Map;


/**
 * 网关全局过滤器
 */

@Component
@Slf4j
public class AuthorizeFilter implements Ordered, GlobalFilter {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IgnoreWhiteProperties ignoreWhiteProperties;

    /**
     * @param exchange 请求访问
     * @param chain    处理请求
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1.获取request和response对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();


        String url = request.getURI().getPath();
        // 跳过不需要验证的路径
        if (StringUtils.matches(url, ignoreWhiteProperties.getWhites())) {
            return chain.filter(exchange);
        }

        // 3.获取token
        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // 4.判断token是否存在
        if (com.alibaba.nacos.common.utils.StringUtils.isBlank(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        try {
            // 5.解析JWT获取用户信息
            Map<String, Object> userInfo = JWTUtils.getTokenInfo(token);
            String username = (String) userInfo.get("username");
            
            log.info("Token验证 - Username: {}, Token: {}", username, token);

            // 从Redis验证token
            String cachedToken = (String) redisTemplate.opsForValue().get(username);
            log.info("Redis中的Token - Username: {}, CachedToken: {}", username, cachedToken);
            
            if (cachedToken == null) {
                log.warn("Redis中未找到Token - Username: {}", username);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
            
            if (!token.equals(cachedToken)) {
                log.warn("Token不匹配 - Username: {}, 请求Token: {}, Redis Token: {}", username, token, cachedToken);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
            
            log.info("Token验证成功 - Username: {}", username);

            // 将用户信息和token传递给下游服务
            ServerHttpRequest newRequest = request.mutate()
                    .header("userId", String.valueOf(userInfo.get("id")))
                    .header("username", username)
                    .header("role", String.valueOf(userInfo.get("role")))
                    .header("Authorization", token)  // 保留原始token传递给下游服务
                    .build();
            
            log.info("传递给下游服务的请求头 - Authorization: {}, userId: {}, username: {}, role: {}", 
                     token, userInfo.get("id"), username, userInfo.get("role"));

            return chain.filter(exchange.mutate().request(newRequest).build());

        } catch (Exception e) {
            log.error("Token解析失败: {}", e.getMessage(), e);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
    }

    /**
     * 优先级设置  值越小  优先级越高
     *
     * @return
     */
    @Override
    public int getOrder() {
        return -1;
    }
}
