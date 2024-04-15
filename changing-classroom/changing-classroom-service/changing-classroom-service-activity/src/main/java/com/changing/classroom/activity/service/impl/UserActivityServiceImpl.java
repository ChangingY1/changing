package com.changing.classroom.activity.service.impl;

import com.changing.classroom.activity.mapper.ActivityMapper;
import com.changing.classroom.activity.mapper.UserActivityMapper;
import com.changing.classroom.activity.service.ActivityService;
import com.changing.classroom.activity.service.UserActivityService;
import com.changing.classroom.common.exception.ChangingException;
import com.changing.classroom.model.dto.h5.UARelationDto;
import com.changing.classroom.model.entity.activity.Activity;
import com.changing.classroom.model.entity.record.UARecord;
import com.changing.classroom.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserActivityServiceImpl implements UserActivityService {

    @Autowired
    UserActivityMapper userActivityMapper;

    @Autowired
    ActivityService activityService;
    @Autowired
    ActivityMapper activityMapper;

    @Override
    public void save(UARecord uaRelation) {
        UARecord relation = userActivityMapper.select(uaRelation);
        if (relation != null) {
            throw new ChangingException(ResultCodeEnum.ERROR.getCode(), "请勿重复报名");
        }
        int save = userActivityMapper.save(uaRelation);
        if (save == 0) {
            throw new ChangingException(ResultCodeEnum.DATA_ERROR);
        }
    }

    @Override
    public List<Activity> getJoinedActivitiesByUserId(String userId) {
        List<Long> activityIds = userActivityMapper.getJoinedActivityIdsByUserId(Long.parseLong(userId));
        List<Activity> activities = new ArrayList<>();
        for (Long activityId : activityIds) {
            Activity activity = activityService.getActivitiyInfoById(activityId);
            activities.add(activity);
        }
        return activities;
    }

    @Override
    public Boolean isJoinedActivity(String userId, String activityId) {
        UARecord uaRelation = new UARecord();
        uaRelation.setUserId(Long.valueOf(userId));
        uaRelation.setActivityId(Long.valueOf(activityId));
        if (userActivityMapper.select(uaRelation) == null) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public void signUp(UARelationDto uaRelationDto) {
        Activity activity = activityService.getActivitiyInfoById(uaRelationDto.getActivityId());
        if (activity == null) {
            throw new ChangingException(ResultCodeEnum.ACTIVITY_NULL.getCode(), ResultCodeEnum.ACTIVITY_NULL.getMessage());
        }
        if (activity.getStartTime().before(new Date())) {
            throw new ChangingException(ResultCodeEnum.ACTIVITY_NULL.getCode(), "不在报名时间");
        }
        if (activity.getRegisteredCount() >= activity.getMaxParticipants()) {
            throw new ChangingException(ResultCodeEnum.STAFF_FULL.getCode(), ResultCodeEnum.STAFF_FULL.getMessage());
        }
        activity.setRegisteredCount(activity.getRegisteredCount() + 1);
        activityService.updataActivity(activity);
        UARecord uaRelation = new UARecord();
        uaRelation.setActivityId(uaRelationDto.getActivityId());
        uaRelation.setUserId(uaRelationDto.getUserId());
        uaRelation.setState(1);
        this.save(uaRelation);
    }

}
