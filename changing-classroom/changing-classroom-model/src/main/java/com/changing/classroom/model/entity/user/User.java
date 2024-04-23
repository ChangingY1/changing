package com.changing.classroom.model.entity.user;

import com.changing.classroom.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "用户实体类")
public class User extends BaseEntity {

   @Schema(description = "学号")
   private String studentId;

   @Schema(description = "密码")
   private String password;

   @Schema(description = "姓名")
   private String name;

   @Schema(description = "头像")
   private String avatar;

   @Schema(description = "电话号码")
   private String phoneNumber;

   @Schema(description = "状态：1为正常，0为禁止")
   private Integer state;

   @Schema(description = "学时")
   private Integer hours;
}