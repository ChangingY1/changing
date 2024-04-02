package com.changing.classroom.activity.controller;

import com.changing.classroom.activity.service.ActivityService;
import com.changing.classroom.activity.service.UserActivityService;
import com.changing.classroom.model.dto.h5.UARelationDto;
import com.changing.classroom.model.entity.activity.Activity;
import com.changing.classroom.model.entity.relation.UARelation;
import com.changing.classroom.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/activity")
public class UserActivityController {

    @Autowired
    private UserActivityService userActivityService;
    @Autowired
    private ActivityService activityService;

    @Operation(summary = "报名活动")
    @PostMapping("signUp")
    public ResponseEntity<String> signUp(@RequestBody UARelationDto uaRelationDto) {
        Activity activity = activityService.getActivitiyInfoById(uaRelationDto.getActivityId());
        if (activity == null) {
            return ResponseEntity.status(ResultCodeEnum.ACTIVITY_NULL.getCode()).body(ResultCodeEnum.ACTIVITY_NULL.getMessage());
        }
        if (activity.getRegisteredCount() >= activity.getMaxParticipants()) {
            return ResponseEntity.status(ResultCodeEnum.STAFF_FULL.getCode()).body(ResultCodeEnum.STAFF_FULL.getMessage());
        }
        activity.setRegisteredCount(activity.getRegisteredCount() + 1);
        activityService.updataActivity(activity);
        UARelation uaRelation = new UARelation();
        uaRelation.setActivityId(uaRelationDto.getActivityId());
        uaRelation.setUserId(uaRelationDto.getUserId());
        userActivityService.save(uaRelation);
        return ResponseEntity.ok(ResultCodeEnum.SUCCESS.getMessage());
    }

}
