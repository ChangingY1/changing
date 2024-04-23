package com.changing.classroom.admin.controller;


import com.changing.classroom.admin.service.AdminUserService;
import com.changing.classroom.model.dto.admin.AdminUserLoginDto;
import com.changing.classroom.model.entity.admin.user.AdminUser;
import com.changing.classroom.model.vo.h5.AdminUserInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/admin/adminUser")
public class AdminUserController {

    @Autowired
    AdminUserService adminUserService;


    @Operation(summary = "会员信息")
    @GetMapping
    public ResponseEntity<AdminUserInfoVo> getAdminUserInfo(@RequestHeader String token) {
        AdminUserInfoVo adminUserInfoVo = adminUserService.getAdminUserInfo(token);
        return ResponseEntity.ok(adminUserInfoVo);
    }

}
