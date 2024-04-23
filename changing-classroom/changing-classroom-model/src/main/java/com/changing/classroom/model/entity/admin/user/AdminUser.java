package com.changing.classroom.model.entity.admin.user;

import com.changing.classroom.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "用户实体类")
public class AdminUser extends BaseEntity {

   @Schema(description = "账号")
   private String username;

   @Schema(description = "密码")
   private String password;

   @Schema(description = "头像")
   private String avatar;

   @Schema(description = "状态：1为正常，0为禁止")
   private String status;

   @Schema(description = "权限等级")
   private String roles;

   @Schema(description = "token")
   private String token;
}