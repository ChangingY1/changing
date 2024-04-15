package com.changing.classroom.model.vo.h5;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "学时记录Vo")
public class HoursRecordVo {


    @Schema(description = "活动名称")
    private String activityTitle;
    @Schema(description = "学时变化量")
    private String changes;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "获得时间")
    private Date getTime;

}