package com.changing.classroom.model.dto.h5;

import com.changing.classroom.model.entity.activity.Activity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Schema(description = "用户实体类Dto")
public class ActivityDto {
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

    public void activityToDto(Activity activity) {
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

    public Activity toActivity() {
        Activity activity = new Activity();
        activity.setId(this.getId());
        activity.setTitle(this.getTitle());
        activity.setDescription(this.getDescription());
        activity.setEndTime(this.getEndTime());
        activity.setHours(this.getHours());
        activity.setIsDeleted(this.getIsDeleted());
        activity.setImage(this.getImage());
        activity.setLocation(this.getLocation());
        activity.setRegisteredCount(this.getRegisteredCount());
        activity.setMaxParticipants(this.getMaxParticipants());
        activity.setOrganizer(this.getOrganizer());
        activity.setStartTime(this.getStartTime());
        activity.setType(this.getType());
        switch (this.getState()) {
            case "冻结" -> activity.setState(0);
            case "未开始" -> activity.setState(1);
            case "准备中" -> activity.setState(2);
            case "进行中" -> activity.setState(3);
            case "已完结" -> activity.setState(4);
        }
        return activity;
    }
}
