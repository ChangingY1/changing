package com.changing.classroom.activity.feign.activity;

import com.changing.classroom.model.entity.activity.Activity;
import com.changing.classroom.model.entity.websocket.MessageInfo;
import com.changing.classroom.model.vo.websocket.MessageInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "service-activity",path = "api/activity")
public interface ActivityFeignClient {

    @GetMapping("getActivitiyInfo/{activityId}")
    ResponseEntity<Activity> getActivitiyInfo(@PathVariable Long activityId);

}
