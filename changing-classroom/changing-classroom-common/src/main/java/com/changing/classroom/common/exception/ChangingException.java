package com.changing.classroom.common.exception;

import com.changing.classroom.model.vo.common.ResultCodeEnum;

public class ChangingException extends RuntimeException {

    private Integer code;
    private String message;

    public ChangingException(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    public ChangingException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
