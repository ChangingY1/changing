package com.changing.classroom.model.vo.common;

import lombok.Getter;

@Getter
public enum CookieKeyEnum {

    USER("user"),
    ;

    private String key ;    // 响应消息

    private CookieKeyEnum(String key) {
        this.key = key ;
    }
}
