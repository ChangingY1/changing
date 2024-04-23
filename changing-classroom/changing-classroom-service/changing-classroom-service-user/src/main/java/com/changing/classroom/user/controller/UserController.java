package com.changing.classroom.user.controller;

import com.changing.classroom.model.dto.h5.UserLoginDto;
import com.changing.classroom.model.dto.h5.UserRegisterDto;
import com.changing.classroom.model.entity.record.HoursRecord;
import com.changing.classroom.model.entity.user.User;
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
    @GetMapping("userId/{userId}")
    public ResponseEntity<User> getUserInfoById2(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.getUserInfoById(userId));
    }

    @Operation(summary = "分页获取用户信息")
    @GetMapping("page/{page}")
    public ResponseEntity<List<User>> getUserInfoByPage(@PathVariable("page") int page) {
        List<User> userInfoByPage = userService.getUserInfoByPage(page);
        return ResponseEntity.ok(userInfoByPage);
    }
}
