package com.changing.classroom.activity.controller;


import com.changing.classroom.activity.service.ActivityService;
import com.changing.classroom.model.entity.activity.Activity;
import com.changing.classroom.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Operation(summary = "获取活动列表")
    @GetMapping("getAllActivities")
    public ResponseEntity getAllActivities() {
        List<Activity> allActivities = activityService.getAllActivities();
        return ResponseEntity.status(ResultCodeEnum.SUCCESS.getCode()).body(allActivities);
    }

    @Operation(summary = "获取活动信息")
    @GetMapping("getActivitiyInfo/{activityId}")
    public ResponseEntity getActivitiyInfo(@PathVariable Long activityId) {
        Activity activity = activityService.getActivitiyInfoById(activityId);
        return ResponseEntity.status(ResultCodeEnum.SUCCESS.getCode()).body(activity);
    }


}
