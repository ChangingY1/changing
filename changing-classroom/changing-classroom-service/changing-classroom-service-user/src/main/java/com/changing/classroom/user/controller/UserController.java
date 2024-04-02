package com.changing.classroom.user.controller;

import com.changing.classroom.model.dto.h5.UserLoginDto;
import com.changing.classroom.model.dto.h5.UserRegisterDto;
import com.changing.classroom.model.vo.common.CookieKeyEnum;
import com.changing.classroom.model.vo.common.ResultCodeEnum;
import com.changing.classroom.model.vo.h5.UserInfoVo;
import com.changing.classroom.user.service.UserService;
import com.changing.classroom.util.CookieUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test-ok");
    }
    @Operation(summary = "获取用户信息")
    @GetMapping("getUserInfoById/{userId}")
    public ResponseEntity<UserInfoVo> getUserInfoById(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.getUserInfoById(userId));
    }

    @Operation(summary = "cookie验证")
    @GetMapping("getuserinfo")
    public ResponseEntity getUserInfo() {
        String cookie = CookieUtil.getCookieValue(request, CookieKeyEnum.USER.getKey());
        if (cookie == null) {
            return ResponseEntity.status(ResultCodeEnum.LOGIN_AUTH.getCode()).body(ResultCodeEnum.LOGIN_AUTH.getMessage());
        }
        UserInfoVo userInfoVo = userService.cookieLogin(cookie);
        return ResponseEntity.ok(userInfoVo);
    }

    @Operation(summary = "会员登录")
    @PostMapping("login")
    public ResponseEntity login(@RequestBody UserLoginDto userLoginDto) {
        String cookie = userService.login(userLoginDto);
        CookieUtil.setCookie(response, CookieKeyEnum.USER.getKey(), cookie, 60 * 60 * 24 * 7);
        return ResponseEntity.ok(ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "会员注册")
    @PostMapping("register")
    public ResponseEntity register(@RequestBody UserRegisterDto userRegisterDto) {
        String cookie = userService.register(userRegisterDto);
        CookieUtil.setCookie(response, CookieKeyEnum.USER.getKey(), cookie, 60 * 60 * 24 * 7);
        return ResponseEntity.ok(ResultCodeEnum.SUCCESS.getMessage());
    }

}
