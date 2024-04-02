package com.changing.classroom.activity.mapper;

import com.changing.classroom.model.entity.websocket.MessageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivityMessageInfoMapper {

    int insert(MessageInfo messageInfo);

    int update(MessageInfo messageInfo);

    int delete(@Param("id") Long id);

    MessageInfo findById(@Param("id") Long id);

    List<MessageInfo> findAll();

    List<MessageInfo> getMessageInfoByActivityId(Long activityId);
}


