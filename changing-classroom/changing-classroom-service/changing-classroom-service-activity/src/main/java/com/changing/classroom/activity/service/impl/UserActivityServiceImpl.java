package com.changing.classroom.activity.service.impl;

import com.changing.classroom.activity.mapper.ActivityMapper;
import com.changing.classroom.activity.mapper.UserActivityMapper;
import com.changing.classroom.activity.service.ActivityService;
import com.changing.classroom.activity.service.UserActivityService;
import com.changing.classroom.common.exception.ChangingException;
import com.changing.classroom.model.entity.activity.Activity;
import com.changing.classroom.model.entity.relation.UARelation;
import com.changing.classroom.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserActivityServiceImpl implements UserActivityService {

    @Autowired
    UserActivityMapper userActivityMapper;

    @Autowired
    ActivityService activityService;
    @Autowired
    ActivityMapper activityMapper;

    @Override
    public void save(UARelation uaRelation) {
        UARelation relation = userActivityMapper.select(uaRelation);
        if (relation != null) {
            throw new ChangingException(ResultCodeEnum.ERROR.getCode(), "请勿重复报名");
        }
        Activity activity = activityService.getActivitiyInfoById(uaRelation.getActivityId());
        if (activity.getRegisteredCount() >= activity.getMaxParticipants()) {
            throw new ChangingException(ResultCodeEnum.ERROR.getCode(), "报名人数已满");
        }
        int save = userActivityMapper.save(uaRelation);
        if (save == 0) {
            throw new ChangingException(ResultCodeEnum.DATA_ERROR);
        }
        activity.setRegisteredCount(activity.getRegisteredCount() + 1);
        activityMapper.update(activity);
    }
}
