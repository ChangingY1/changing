package com.changing.classroom.model.entity.activity;

import com.changing.classroom.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "用户实体类")
public class Activity extends BaseEntity {

    @Schema(description = "活动类型")
    private String type;
    @Schema(description = "状态")
    private Integer state;
    @Schema(description = "活动标题")
    private String title;
    @Schema(description = "活动描述")
    private String description;
    @Schema(description = "活动图片")
    private String image;
    @Schema(description = "开始时间")
    private Timestamp startTime;
    @Schema(description = "结束时间")
    private Timestamp endTime;
    @Schema(description = "活动地点")
    private String location;
    @Schema(description = "活动组织者")
    private String organizer;
    @Schema(description = "已报名人数")
    private Integer registeredCount;
    @Schema(description = "总报名人数")
    private Integer maxParticipants;
    @Schema(description = "活动学时")
    private Integer hours;
}
