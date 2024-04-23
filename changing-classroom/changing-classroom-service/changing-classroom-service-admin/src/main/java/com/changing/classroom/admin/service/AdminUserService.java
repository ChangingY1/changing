package com.changing.classroom.admin.service;

import com.changing.classroom.model.dto.admin.AdminUserLoginDto;
import com.changing.classroom.model.dto.h5.UserLoginDto;
import com.changing.classroom.model.vo.h5.AdminUserInfoVo;

public interface AdminUserService {

    //登录
    AdminUserInfoVo login(AdminUserLoginDto adminUserLoginDto);

    //获取当前登录用户信息
    AdminUserInfoVo getAdminUserInfo(String token);


}
