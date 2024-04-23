package com.changing.classroom.model.vo.h5;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "管理员类Vo")
public class AdminUserInfoVo {

    @Schema(description = "id")
    private Long id;
    @Schema(description = "账号")
    private String username;
    @Schema(description = "头像")
    private String avatar;
    @Schema(description = "权限等级")
    private String roles;
    @Schema(description = "状态：1为正常，0为禁止")
    private String status;
    @Schema(description = "token")
    private String token;

}