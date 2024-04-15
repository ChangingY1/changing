package com.changing.classroom.user.controller;

import com.changing.classroom.model.dto.h5.UserLoginDto;
import com.changing.classroom.model.dto.h5.UserRegisterDto;
import com.changing.classroom.model.entity.record.HoursRecord;
import com.changing.classroom.model.vo.common.CookieKeyEnum;
import com.changing.classroom.model.vo.common.ResultCodeEnum;
import com.changing.classroom.model.vo.h5.HoursRecordVo;
import com.changing.classroom.model.vo.h5.UserInfoVo;
import com.changing.classroom.user.service.UserService;
import com.changing.classroom.util.CookieUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpServletRequest request;

    @Operation(summary = "获取用户信息")
    @GetMapping("auth/getUserInfoById/{userId}")
    public ResponseEntity<UserInfoVo> getUserInfoById(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.getUserInfoById(userId));
    }

    @Operation(summary = "cookie验证")
    @GetMapping("auth/getuserinfo")
    public ResponseEntity<Object> getUserInfo() {
        String cookie = CookieUtil.getCookieValue(request, CookieKeyEnum.USER.getKey());
        if (cookie == null) {
            return ResponseEntity.status(ResultCodeEnum.LOGIN_AUTH.getCode()).body(ResultCodeEnum.LOGIN_AUTH.getMessage());
        }
        UserInfoVo userInfoVo = userService.cookieLogin(cookie);
        return ResponseEntity.ok(userInfoVo);
    }

    @Operation(summary = "会员登录")
    @PostMapping("auth/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto) {
        String cookie = userService.login(userLoginDto);
        CookieUtil.setCookie(response, CookieKeyEnum.USER.getKey(), cookie, 60 * 60 * 24 * 7);
        return ResponseEntity.ok(ResultCodeEnum.SUCCESS.getMessage());
    }

    @Operation(summary = "会员注册")
    @PostMapping("auth/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDto userRegisterDto) {
        String cookie = userService.register(userRegisterDto);
        CookieUtil.setCookie(response, CookieKeyEnum.USER.getKey(), cookie, 60 * 60 * 24 * 7);
        return ResponseEntity.ok(ResultCodeEnum.SUCCESS.getMessage());
    }

    @Operation(summary = "获取学时变化表")
    @GetMapping("getHoursRecoedsByUserId")
    public ResponseEntity<List<HoursRecordVo>> getHoursRecoedsByUserId(@RequestHeader("userId") String userId){
        return ResponseEntity.ok(userService.getHoursRecoedsByUserId(Long.valueOf(userId)));
    }

}
