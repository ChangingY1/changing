package com.changing.classroom.activity.service;

import com.changing.classroom.model.entity.activity.Activity;
import com.changing.classroom.model.entity.record.UARecord;

import java.util.List;

public interface UserActivityService {

    void save(UARecord uaRelation);

    List<Activity> getJoinedActivitiesByUserId(String userId);

    Boolean isJoinedActivity(String userId, String activityId);
}
