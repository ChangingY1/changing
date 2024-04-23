package com.changing.classroom.feign.activity;

import com.changing.classroom.model.entity.activity.Activity;
import com.changing.classroom.model.entity.websocket.MessageInfo;
import com.changing.classroom.model.vo.websocket.MessageInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "service-activity",path = "api/activity")
public interface ActivityFeignClient {
    @GetMapping("messageInfo/activityId/{activityId}")
    ResponseEntity<List<MessageInfoVo>> getMessageInfoByActivityId(@PathVariable Long activityId);

    @PutMapping("messageInfo")
    ResponseEntity<Integer> savaMessageInfo(@RequestBody MessageInfo messageInfo);

    @GetMapping("activityId/{activityId}")
    ResponseEntity<Activity> getActivitiyInfo(@PathVariable Long activityId);


}
