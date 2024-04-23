package com.changing.classroom.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.changing.classroom.model.entity.user.User;
import com.changing.classroom.model.vo.common.RedisKeyHeadEnum;
import com.changing.classroom.model.vo.common.ResultCodeEnum;
import com.changing.classroom.model.vo.h5.AdminUserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取请求路径
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        if (antPathMatcher.match("/**/auth/**", path)) {
            return chain.filter(exchange);
        }
        if (antPathMatcher.match("/**/admin/**", path)) {
            return chain.filter(exchange);
        }
        User user = this.getUser(request);
        AdminUserInfoVo adminUserInfoVo = this.getAdminUserInfoVo(request);

        if (user == null && adminUserInfoVo == null) {
            ServerHttpResponse response = exchange.getResponse();
            return out(response, ResultCodeEnum.LOGIN_AUTH);
        }
        String userId;
        if (user == null) {
            userId = adminUserInfoVo.getId().toString();
        } else {
            userId = user.getId().toString();
        }
        //放行
        request = exchange.getRequest().mutate().header("userId", userId).build();
        //把新的 exchange放回到过滤链
        return chain.filter(exchange.mutate().request(request).build());
//        return chain.filter(exchange);
    }

    private AdminUserInfoVo getAdminUserInfoVo(ServerHttpRequest request) {
        //从请求头获取token
        String token = "";
        List<String> tokenList = request.getHeaders().get("token");
        if (tokenList != null) {
            token = tokenList.get(0);
        }
        //判断token是否为空
        if (!StringUtils.isEmpty(token)) {
            //根据token查询redis
            String userJson = redisTemplate.opsForValue().get(RedisKeyHeadEnum.ADMINUSER_TOKEN + token);
            if (StringUtils.isEmpty(userJson)) {
                return null;
            } else {
                AdminUserInfoVo adminUserInfoVo = JSON.parseObject(userJson, AdminUserInfoVo.class);
                return adminUserInfoVo;
            }
        }
        return null;
    }

    private User getUser(ServerHttpRequest request) {
        //从请求头获取token
        String cookie = "";
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        {
            List<HttpCookie> user = cookies.get("user");
            if (user != null) {
                if (user.get(0) != null) {
                    cookie = user.get(0).getValue().toString();
                }
            }
        }
        //判断token是否为空
        if (!StringUtils.isEmpty(cookie)) {
            //根据token查询redis
            String userJson = redisTemplate.opsForValue().get(RedisKeyHeadEnum.USER_COOKIE + cookie);
            if (StringUtils.isEmpty(userJson)) {
                return null;
            } else {
                User user = JSON.parseObject(userJson, User.class);
                return user;
            }
        }
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> out(ServerHttpResponse response, ResultCodeEnum resultCodeEnum) {
        ResponseEntity responseEntity = ResponseEntity.status(resultCodeEnum.getCode()).body(resultCodeEnum.getMessage());
        byte[] bits = JSONObject.toJSONString(responseEntity).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }
}
