package com.changing.classroom.websocket.controller;


import com.changing.classroom.feign.user.UserFeignClient;
import com.changing.classroom.model.vo.common.ResultCodeEnum;
import com.changing.classroom.model.vo.h5.UserInfoVo;
import com.changing.classroom.websocket.serverEndpoint.ActivityWebsocket;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/websocket")
public class WebsocketController {


    @Autowired
    private UserFeignClient userFeignClient;

    @Operation(summary = "获取在线用户")
    @GetMapping("getOnlineUser/{activityId}")
    public ResponseEntity<List<UserInfoVo>> getOnlineUser(@PathVariable("activityId") String activityId) {
        List<UserInfoVo> users = new ArrayList<>();
        for (ActivityWebsocket websocket : ActivityWebsocket.websockets) {
            if (websocket.activityId.equals(Long.parseLong(activityId))){
                users.add(websocket.user);
            }
        }
        return ResponseEntity.status(ResultCodeEnum.SUCCESS.getCode()).body(users);
    }

}
