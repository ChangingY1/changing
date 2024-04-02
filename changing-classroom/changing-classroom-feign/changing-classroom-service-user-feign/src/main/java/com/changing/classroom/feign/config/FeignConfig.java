package com.changing.classroom.feign.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Configuration
public class FeignConfig implements RequestInterceptor {
//    @Bean("requestInterceptor")
//    public RequestInterceptor requestInterceptor() {
//        return new RequestInterceptor() {
//            @Override
//            public void apply(RequestTemplate template) {
//                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();//拿到当前的所有请求属性
//                if (attributes != null) {
//                    HttpServletRequest request = attributes.getRequest();//获取到当前请求
//                    if (request != null) {
//                        //同步请求头数据，cookie
//                        String cookie = request.getHeader("Cookie");
//                        //给新请求同步老请求的cookie
//                        template.header("Cookie", cookie);
//                    }
//                }
//            }
//        };
//    }

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();//拿到当前的所有请求属性
                if (attributes != null) {
                    HttpServletRequest request = attributes.getRequest();//获取到当前请求
                    //同步请求头数据，cookie
                    String cookie = request.getHeader("Cookie");
                    //给新请求同步老请求的cookie
                    template.header("Cookie", cookie);
                }
    }
}


