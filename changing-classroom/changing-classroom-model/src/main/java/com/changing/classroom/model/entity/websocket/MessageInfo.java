package com.changing.classroom.model.entity.websocket;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.changing.classroom.model.entity.base.BaseEntity;

@Data
@EqualsAndHashCode(callSuper = true)
public class MessageInfo extends BaseEntity {
    private Long userId;
    private Long activityId;
    private String message;
    private String userAvatar;
    private String userName;

}
