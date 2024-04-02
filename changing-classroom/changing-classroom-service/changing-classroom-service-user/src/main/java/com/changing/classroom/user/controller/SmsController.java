package com.changing.classroom.user.controller;

import com.changing.classroom.model.vo.common.ResultCodeEnum;
import com.changing.classroom.user.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user/sms")
public class SmsController {

    @Autowired
    private SmsService smsService ;

    @GetMapping(value = "/sendCode/{phoneNumber}")
    public ResponseEntity sendValidateCode(@PathVariable String phoneNumber) {
        smsService.sendValidateCode(phoneNumber);
        return ResponseEntity.status(ResultCodeEnum.SUCCESS.getCode()) .body(ResultCodeEnum.SUCCESS.getMessage());
    }

}