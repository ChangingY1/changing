package com.changing.classroom.user.service;

import com.changing.classroom.model.dto.h5.UserLoginDto;
import com.changing.classroom.model.dto.h5.UserRegisterDto;
import com.changing.classroom.model.entity.record.HoursRecord;
import com.changing.classroom.model.vo.h5.HoursRecordVo;
import com.changing.classroom.model.vo.h5.UserInfoVo;

import java.util.List;

public interface UserService {
    //注册
    String register(UserRegisterDto userRegisterDto);

    //登录
    String login(UserLoginDto userLoginDto);

    //获取当前登录用户信息
    UserInfoVo cookieLogin(String cookie);

    UserInfoVo getUserInfoById(String userId);

    List<HoursRecordVo> getHoursRecoedsByUserId(Long userId);
}
