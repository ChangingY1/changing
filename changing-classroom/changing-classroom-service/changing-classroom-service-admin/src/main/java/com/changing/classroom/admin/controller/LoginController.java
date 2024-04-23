package com.changing.classroom.admin.controller;

import com.changing.classroom.admin.service.AdminUserService;
import com.changing.classroom.model.dto.admin.AdminUserLoginDto;
import com.changing.classroom.model.vo.h5.AdminUserInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/admin/login")
public class LoginController {

    @Autowired
    AdminUserService adminUserService;

    @Operation(summary = "会员登录")
    @PostMapping
    public ResponseEntity<AdminUserInfoVo> login(@RequestBody AdminUserLoginDto adminUserLoginDto) {
        AdminUserInfoVo adminUserInfoVo = adminUserService.login(adminUserLoginDto);
        return ResponseEntity.ok(adminUserInfoVo);
    }
}
