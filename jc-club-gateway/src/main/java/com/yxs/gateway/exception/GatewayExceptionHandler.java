package com.yxs.gateway.exception;

import cn.dev33.satoken.exception.SaTokenException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yxs.gateway.entity.Result;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
//@Order(-1)
public class GatewayExceptionHandler implements WebExceptionHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        ServerHttpResponse response = serverWebExchange.getResponse();
        int code = 200;
        String message = "";
        if (throwable instanceof SaTokenException){
            code = 401;
            message = "用户无权限";
        }else {
            code = 500;
            message = "系统繁忙";
        }
        Result result = Result.fail(code, message);

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return response.writeWith(Mono.fromSupplier(() ->{
            DataBufferFactory dataBufferFactory = response.bufferFactory();

            try {
                byte[] bytes =objectMapper.writeValueAsBytes(result);

                return dataBufferFactory.wrap(bytes);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }));
    }
}
