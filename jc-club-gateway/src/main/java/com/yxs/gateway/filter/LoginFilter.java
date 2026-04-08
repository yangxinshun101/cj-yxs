package com.yxs.gateway.filter;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Configuration
@Slf4j
public class LoginFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //先拿到请求头，进行token的校验
        ServerHttpRequest request = exchange.getRequest();
        //根据请求头拿到一个可变的请求头的构造器
        ServerHttpRequest.Builder mutate = request.mutate();

        //校验当前的URI是否为登录，如果不为登录则把loginId放入请求头中
        String path = request.getURI().getPath();
        if (path.equals("/user/doLogin")){
            return chain.filter(exchange);
        }
        //在请求头中添加一个loginId
        String loginId = (String)StpUtil.getTokenInfo().getLoginId();
        log.info("LoginFilter.filter.loginId:{}", loginId);
        mutate.header("loginId",loginId);
        /**
         * exchange.mutate()这个是构建一个可变的ServerHttpRequest.Builder
         * request-》然后把修改的请求头放进去，生成一个请求体；
         * mutate.build()-》把修改的请求头Build构建成成Builder；
         */
        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }
}
