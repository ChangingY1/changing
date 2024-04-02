package com.changing.classroom.feign.activity;

import com.changing.classroom.model.entity.websocket.MessageInfo;
import com.changing.classroom.model.vo.h5.UserInfoVo;
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

    @GetMapping("getMessageInfoByActivityId")
    ResponseEntity<List<MessageInfoVo>> getMessageInfoByActivityId();

    @PostMapping("savaMessageInfo")
    ResponseEntity<Integer> savaMessageInfo(@RequestBody MessageInfo messageInfo);
}
