package com.changing.classroom.model.dto.h5;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "报名活动请求参数")
public class UARelationDto {

   @Schema(description = "用户ID")
   private Long userId;
   @Schema(description = "活动ID")
   private Long activityId;


}