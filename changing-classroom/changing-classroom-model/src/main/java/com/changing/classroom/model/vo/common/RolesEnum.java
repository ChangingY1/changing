package com.changing.classroom.model.vo.common;

import lombok.Getter;

@Getter
public enum RolesEnum {

    ADMIN("admin"),
    VISITOR("visitor")
    ;

    private String key ;    // 响应消息

    private RolesEnum(String key) {
        this.key = key ;
    }
}
