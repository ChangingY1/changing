package com.changing.classroom.model.vo.h5;

import com.changing.classroom.model.entity.activity.Activity;
import com.changing.classroom.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Data
@Schema(description = "用户实体类Vo")
public class ActivityVo {
    @Schema(description = "id")
    private Long id;
    @Schema(description = "活动类型")
    private String type;
    @Schema(description = "状态")
    private String state;
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
    @Schema(description = "是否删除")
    private Integer isDeleted;

    public ActivityVo(Activity activity) {
        this.title = activity.getTitle();
        this.description = activity.getDescription();
        this.id = activity.getId();
        this.endTime = activity.getEndTime();
        this.hours = activity.getHours();
        this.isDeleted = activity.getIsDeleted();
        this.image = activity.getImage();
        this.location = activity.getLocation();
        this.registeredCount = activity.getRegisteredCount();
        this.maxParticipants = activity.getMaxParticipants();
        this.organizer = activity.getOrganizer();
        this.startTime = activity.getStartTime();
        this.type = activity.getType();
        switch (activity.getState()) {
            case 0 -> this.state = "冻结";
            case 1 -> this.state = "未开始";
            case 2 -> this.state = "准备中";
            case 3 -> this.state = "进行中";
            case 4 -> this.state = "已完结";
        }
    }

}
