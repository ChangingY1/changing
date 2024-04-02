package com.changing.classroom.model.vo.common;

import lombok.Getter;

@Getter // 提供获取属性值的getter方法
public enum RedisKeyHeadEnum {

    PHONE_CODE("phone-code:") ,
    USER_COOKIE("user-cookie:"),
    ;

    private String keyHead ;    // 响应消息

    private RedisKeyHeadEnum(String keyHead) {
        this.keyHead = keyHead ;
    }

}

