package com.changing.classroom.model.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "管理员用户登录请求参数")
public class AdminUserLoginDto {

    @Schema(description = "账号")
    private String username ;

    @Schema(description = "密码")
    private String password ;

}
