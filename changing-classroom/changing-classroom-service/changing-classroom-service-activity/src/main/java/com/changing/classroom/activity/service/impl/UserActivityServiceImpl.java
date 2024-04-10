package com.changing.classroom.activity.service.impl;

import com.changing.classroom.activity.mapper.ActivityMapper;
import com.changing.classroom.activity.mapper.UserActivityMapper;
import com.changing.classroom.activity.service.ActivityService;
import com.changing.classroom.activity.service.UserActivityService;
import com.changing.classroom.common.exception.ChangingException;
import com.changing.classroom.model.entity.activity.Activity;
import com.changing.classroom.model.entity.record.UARecord;
import com.changing.classroom.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

}
