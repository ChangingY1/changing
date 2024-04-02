package com.changing.classroom.activity.service.impl;

import com.changing.classroom.activity.mapper.ActivityMessageInfoMapper;
import com.changing.classroom.activity.service.ActivityMessageInfoService;
import com.changing.classroom.model.entity.websocket.MessageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ActivityMessageInfoServiceImpl implements ActivityMessageInfoService {

    @Autowired
    ActivityMessageInfoMapper activityMessageInfoMapper;

    @Override
    public List<MessageInfo> getAllMessageInfo() {
        return activityMessageInfoMapper.findAll();
    }

    @Override
    public List<MessageInfo> getMessageInfoByActivityId(Long activityId) {
        return activityMessageInfoMapper.getMessageInfoByActivityId(activityId);
    }

    @Override
    public int save(MessageInfo messageInfo) {
        return activityMessageInfoMapper.insert(messageInfo);
    }
}
