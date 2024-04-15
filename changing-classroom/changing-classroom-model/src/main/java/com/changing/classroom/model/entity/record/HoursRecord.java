package com.changing.classroom.model.entity.record;

import com.changing.classroom.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "用户参与活动记录")
public class HoursRecord extends BaseEntity {

    @Schema(description = "用户ID")
    private Long userId;
    @Schema(description = "活动ID")
    private Long activityId;
    @Schema(description = "学时变化量")
    private String changes;
}
