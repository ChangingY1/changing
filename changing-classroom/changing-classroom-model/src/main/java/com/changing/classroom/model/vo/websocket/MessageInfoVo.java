package com.changing.classroom.model.vo.websocket;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MessageInfoVo implements Serializable {
    private String userAvatar;
    private String userName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    private String message;
    private Boolean mineMsg;

    public MessageInfoVo(String userAvatar, String userName, Date time, String message, Boolean mineMsg) {
        this.userAvatar = userAvatar;
        this.userName = userName;
        this.time = time;
        this.message = message;
        this.mineMsg = mineMsg;
    }

    public MessageInfoVo() {

    }
}
