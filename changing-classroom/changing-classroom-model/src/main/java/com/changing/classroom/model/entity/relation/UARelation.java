package com.changing.classroom.model.entity.relation;

import com.changing.classroom.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "用户活动关系")
public class UARelation extends BaseEntity {

   @Schema(description = "用户ID")
   private Long userId;
   @Schema(description = "活动ID")
   private Long activityId;


}