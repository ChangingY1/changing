package com.changing.classroom.activity.controller;

import com.changing.classroom.activity.service.ActivityService;
import com.changing.classroom.activity.service.UserActivityService;
import com.changing.classroom.model.dto.h5.UARelationDto;
import com.changing.classroom.model.entity.activity.Activity;
import com.changing.classroom.model.entity.record.UARecord;
import com.changing.classroom.model.vo.common.ResultCodeEnum;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/activity")
public class UserActivityController {

    @Autowired
    private UserActivityService userActivityService;


    @Operation(summary = "报名活动")
    @PostMapping("signUp")
    public ResponseEntity<String> signUp(@RequestBody UARelationDto uaRelationDto) {
        userActivityService.signUp(uaRelationDto);
        return ResponseEntity.ok(ResultCodeEnum.SUCCESS.getMessage());
    }

    @Operation(summary = "获取已加入的活动列表")
    @GetMapping("getJoinedActivities")
    public ResponseEntity<List<Activity>> getJoinedActivities(@RequestHeader("userId") String userId) {
        List<Activity> activities = userActivityService.getJoinedActivitiesByUserId(userId);
        return ResponseEntity.status(ResultCodeEnum.SUCCESS.getCode()).body(activities);
    }

    @Operation(summary = "是否已加入活动")
    @GetMapping("isJoinedActivity/{activityId}")
    public ResponseEntity<Boolean> isJoinedActivity(@PathVariable("activityId") String activityId, @RequestHeader("userId") String userId) {
        Boolean isJoined = userActivityService.isJoinedActivity(userId, activityId);
        return ResponseEntity.status(ResultCodeEnum.SUCCESS.getCode()).body(isJoined);
    }

}
