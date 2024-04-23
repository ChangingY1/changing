package com.changing.classroom.activity.controller;


import com.changing.classroom.activity.service.ActivityMessageInfoService;
import com.changing.classroom.model.entity.websocket.MessageInfo;
import com.changing.classroom.model.vo.common.ResultCodeEnum;
import com.changing.classroom.model.vo.websocket.MessageInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/activity/messageInfo")
public class ActivityMessageInfoController {

    @Autowired
    private ActivityMessageInfoService activityMessageInfoService;

    @Operation(summary = "获取聊天记录")
    @GetMapping("activityId/{activityId}")
    public ResponseEntity<List<MessageInfoVo>> getMessageInfoByActivityId(@PathVariable("activityId") String activityId,@RequestHeader("userId")String userId) {
        List<MessageInfo> MessageInfos = activityMessageInfoService.getMessageInfoByActivityId(Long.valueOf(activityId));
        List<MessageInfoVo> messageInfoVoList = new ArrayList<>();
        for (MessageInfo messageInfo : MessageInfos) {
            MessageInfoVo messageInfoVo = new MessageInfoVo();
            messageInfoVo.setMessage(messageInfo.getMessage());
            messageInfoVo.setTime(messageInfo.getCreateTime());
            messageInfoVo.setUserName(messageInfo.getUserName());
            messageInfoVo.setUserAvatar(messageInfo.getUserAvatar());
            messageInfoVo.setMineMsg(userId.equals(messageInfo.getUserId().toString()));
            messageInfoVoList.add(messageInfoVo);
        }
        return ResponseEntity.status(ResultCodeEnum.SUCCESS.getCode()).body(messageInfoVoList);
    }

    @Operation(summary = "保存对话消息")
    @PutMapping
    public ResponseEntity<Integer> savaMessageInfo(@RequestBody MessageInfo messageInfo) {
        int save = activityMessageInfoService.save(messageInfo);
        return ResponseEntity.status(ResultCodeEnum.SUCCESS.getCode()).body(save);
    }

}
