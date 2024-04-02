package com.changing.classroom.model.dto.h5;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description="注册对象")
public class UserRegisterDto {

    @Schema(description = "电话号码")
    private String phoneNumber;
    @Schema(description = "学号")
    private String studentId;
    @Schema(description = "密码")
    private String password;
    @Schema(description = "姓名")
    private String name;
    @Schema(description = "手机验证码")
    private String phoneCode ;

}