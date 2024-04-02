package com.changing.classroom.activity.service;

import com.changing.classroom.model.entity.activity.Activity;

import java.util.List;

public interface ActivityService {

    List<Activity> getAllActivities();

    Activity getActivitiyInfoById(Long activityId);

    int updataActivity(Activity activity);
}
