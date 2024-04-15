package com.changing.classroom.activity.service;

import com.changing.classroom.model.dto.h5.UARelationDto;
import com.changing.classroom.model.entity.activity.Activity;
import com.changing.classroom.model.entity.record.UARecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserActivityService {

    void save(UARecord uaRelation);

    List<Activity> getJoinedActivitiesByUserId(String userId);

    Boolean isJoinedActivity(String userId, String activityId);

    void signUp(@RequestBody UARelationDto uaRelationDto);
}
