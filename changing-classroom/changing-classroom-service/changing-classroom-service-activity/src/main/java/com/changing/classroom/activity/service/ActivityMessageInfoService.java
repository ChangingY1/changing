package com.changing.classroom.activity.service;

import com.changing.classroom.model.entity.websocket.MessageInfo;

import java.util.List;

public interface ActivityMessageInfoService {

    List<MessageInfo> getAllMessageInfo();
    List<MessageInfo> getMessageInfoByActivityId(Long activityId);

    int save(MessageInfo messageInfo);

}
