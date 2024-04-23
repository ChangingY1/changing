package com.changing.classroom.activity.controller;


import com.changing.classroom.activity.service.ActivityService;
import com.changing.classroom.model.dto.h5.ActivityDto;
import com.changing.classroom.model.entity.activity.Activity;
import com.changing.classroom.model.vo.common.ResultCodeEnum;
import com.changing.classroom.model.vo.h5.ActivityVo;
import com.github.pagehelper.PageHelper;
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
    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        List<Activity> allActivities = activityService.getAllActivities();
        return ResponseEntity.status(ResultCodeEnum.SUCCESS.getCode()).body(allActivities);
    }

    @Operation(summary = "获取活动列表")
    @GetMapping("/page/{page}")
    public ResponseEntity<List<Activity>> getActivitiyByPage(@PathVariable int page) {
        PageHelper.startPage(page, 6);
        List<Activity> allActivities = activityService.getAllActivities();
        return ResponseEntity.status(ResultCodeEnum.SUCCESS.getCode()).body(allActivities);
    }

    @Operation(summary = "获取活动信息")
    @GetMapping("/id/{activityId}")
    public ResponseEntity<ActivityVo> getActivitiyInfo(@PathVariable Long activityId) {
        Activity activity = activityService.getActivitiyInfoById(activityId);
        ActivityVo activityVo = new ActivityVo(activity);
        return ResponseEntity.status(ResultCodeEnum.SUCCESS.getCode()).body(activityVo);
    }

    @Operation(summary = "更新活动信息")
    @PutMapping
    public ResponseEntity<String> updateActivitiyInfo(@RequestBody ActivityDto activityDto) {
        Activity activity = activityDto.toActivity();
        activityService.updataActivity(activity);
        return ResponseEntity.status(ResultCodeEnum.SUCCESS.getCode()).body(ResultCodeEnum.SUCCESS.getMessage());
    }

}
