package com.changing.classroom.model.vo.common;

import lombok.Getter;

@Getter // 提供获取属性值的getter方法
public enum ResultCodeEnum {

    SUCCESS(200, "操作成功"),
    ERROR(201, "操作失败"),
    LOGIN_ERROR(202, "用户名或者密码错误"),
    VALIDATECODE_ERROR(203, "验证码错误"),
    LOGIN_AUTH(208, "用户未登录"),
    USER_NAME_IS_EXISTS(209, "用户名已经存在"),
    SYSTEM_ERROR(9999, "您的网络有问题请稍后重试"),
    NODE_ERROR(217, "该节点下有子节点，不可以删除"),
    DATA_ERROR(204, "数据异常"),
    ACCOUNT_STOP(216, "账号已停用"),
    STAFF_FULL(220,"报名人员已满"),
    ACTIVITY_NULL(221,"无此活动"),
    SERVER_ERROR(500, "服务器异常");


    private Integer code;      // 业务状态码
    private String message;    // 响应消息

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
