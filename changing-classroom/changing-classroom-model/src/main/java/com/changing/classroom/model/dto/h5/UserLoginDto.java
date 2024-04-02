package com.changing.classroom.model.dto.h5;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户登录请求参数")
public class UserLoginDto {

    @Schema(description = "电话号码")
    private String phoneNumber ;

    @Schema(description = "密码")
    private String password ;
}