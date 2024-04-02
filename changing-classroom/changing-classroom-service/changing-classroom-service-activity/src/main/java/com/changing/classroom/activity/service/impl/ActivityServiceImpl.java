package com.changing.classroom.activity.service.impl;

import com.changing.classroom.activity.mapper.ActivityMapper;
import com.changing.classroom.activity.service.ActivityService;
import com.changing.classroom.model.entity.activity.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityMapper activityMapper;

    @Override
    public List<Activity> getAllActivities() {
        return activityMapper.getAllActivities();
    }

    @Override
    public Activity getActivitiyInfoById(Long activityId) {
        return activityMapper.selectActivityById(activityId);
    }

    @Override
    public int updataActivity(Activity activity) {
        return activityMapper.update(activity);
    }
}
