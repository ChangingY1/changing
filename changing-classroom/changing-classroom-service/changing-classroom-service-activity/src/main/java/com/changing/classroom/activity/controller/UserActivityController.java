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
    @Autowired
    private ActivityService activityService;

    @Operation(summary = "报名活动")
    @Transactional
    @PostMapping("signUp")
    public ResponseEntity<String> signUp(@RequestBody UARelationDto uaRelationDto) {
        Activity activity = activityService.getActivitiyInfoById(uaRelationDto.getActivityId());
        if (activity == null) {
            return ResponseEntity.status(ResultCodeEnum.ACTIVITY_NULL.getCode()).body(ResultCodeEnum.ACTIVITY_NULL.getMessage());
        }
        if (activity.getRegisteredCount() >= activity.getMaxParticipants()) {
            System.out.println(activity.getRegisteredCount());
            return ResponseEntity.status(ResultCodeEnum.STAFF_FULL.getCode()).body(ResultCodeEnum.STAFF_FULL.getMessage());
        }
        activity.setRegisteredCount(activity.getRegisteredCount() + 1);
        activityService.updataActivity(activity);
        UARecord uaRelation = new UARecord();
        uaRelation.setActivityId(uaRelationDto.getActivityId());
        uaRelation.setUserId(uaRelationDto.getUserId());
        uaRelation.setState(1);
        userActivityService.save(uaRelation);
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
