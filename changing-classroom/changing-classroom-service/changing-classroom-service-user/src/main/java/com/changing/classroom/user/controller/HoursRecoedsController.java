package com.changing.classroom.user.controller;

import com.changing.classroom.model.vo.h5.HoursRecordVo;
import com.changing.classroom.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/hoursRecoeds")
public class HoursRecoedsController {

    @Autowired
    private UserService userService;

    @Operation(summary = "获取学时变化表")
    @GetMapping("userId")
    public ResponseEntity<List<HoursRecordVo>> getHoursRecoedsByUserId(@RequestHeader("userId") String userId){
        return ResponseEntity.ok(userService.getHoursRecoedsByUserId(Long.valueOf(userId)));
    }
}
