package com.changing.classroom.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {

//    @Bean
//    public GroupedOpenApi adminApi() {      // 创建了一个api接口的分组
//        return GroupedOpenApi.builder()
//                .group("app-api")         // 分组名称
//                .pathsToMatch("/app/**")  // 接口请求路径规则
//                .build();
//    }

    /***
     * @description 自定义接口信息
     */
    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("改变课堂API接口文档")
                        .version("1.0")
                        .description("改变课堂API接口文档")
                        .contact(new Contact().name("changing"))); // 设定作者
    }

}
