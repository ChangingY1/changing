package com.changing.classroom.model.vo.h5;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户类Vo")
public class UserInfoVo {

    @Schema(description = "id")
    private Long id;
    @Schema(description = "学号")
    private String studentId;
    @Schema(description = "姓名")
    private String name;
    @Schema(description = "头像")
    private String avatar;
    @Schema(description = "电话号码")
    private String phoneNumber;
    @Schema(description = "状态：1为正常，0为禁止")
    private String status;
    @Schema(description = "学时")
    private int hours;

}