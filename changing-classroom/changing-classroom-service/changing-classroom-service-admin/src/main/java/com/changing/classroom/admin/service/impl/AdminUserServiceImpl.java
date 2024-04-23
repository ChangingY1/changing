package com.changing.classroom.admin.service.impl;


import com.alibaba.fastjson.JSON;
import com.changing.classroom.admin.mapper.AdminUserMapper;
import com.changing.classroom.admin.service.AdminUserService;
import com.changing.classroom.common.exception.ChangingException;
import com.changing.classroom.model.dto.admin.AdminUserLoginDto;
import com.changing.classroom.model.dto.h5.UserLoginDto;
import com.changing.classroom.model.entity.admin.user.AdminUser;
import com.changing.classroom.model.entity.user.User;
import com.changing.classroom.model.vo.common.RedisKeyHeadEnum;
import com.changing.classroom.model.vo.common.ResultCodeEnum;
import com.changing.classroom.model.vo.h5.AdminUserInfoVo;
import com.changing.classroom.model.vo.h5.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private AdminUserMapper adminUserMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public AdminUserInfoVo login(AdminUserLoginDto adminUserLoginDto) {
        // 1 dto获取用户名和密码
        String username = adminUserLoginDto.getUsername();
        String password = adminUserLoginDto.getPassword();
        // 2 根据用户名查询数据库，得到用户信息
        System.out.println(username);
        AdminUser adminUser = adminUserMapper.findByUsername(username);
        if (adminUser == null) {
            throw new ChangingException(ResultCodeEnum.LOGIN_ERROR);
        }
        // 3 比较密码是否一致
        String database_password = adminUser.getPassword();
        String md5_password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!database_password.equals(md5_password)) {
            throw new ChangingException(ResultCodeEnum.LOGIN_ERROR);
        }
        //4 校验用户是否禁用
        if (Objects.equals(adminUser.getStatus(), "0")) {
            throw new ChangingException(ResultCodeEnum.ACCOUNT_STOP);
        }
        //5 生成token
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        //6 adminUserInfoVo数据封装
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo();
        adminUserInfoVo.setId(adminUser.getId());
        adminUserInfoVo.setRoles(adminUser.getRoles());
        adminUserInfoVo.setUsername(adminUser.getUsername());
        adminUserInfoVo.setStatus(adminUser.getStatus());
        adminUserInfoVo.setToken(token);
        //6 把用户信息放到redis里面
        redisTemplate.opsForValue().set(RedisKeyHeadEnum.ADMINUSER_TOKEN + token,
                JSON.toJSONString(adminUserInfoVo),
                7, TimeUnit.DAYS);

        return adminUserInfoVo;
    }


    @Override
    public AdminUserInfoVo getAdminUserInfo(String token) {

        String adminUserInfoJson = redisTemplate.opsForValue().get(RedisKeyHeadEnum.ADMINUSER_TOKEN + token);
        AdminUserInfoVo adminUserInfoVo = JSON.parseObject(adminUserInfoJson, AdminUserInfoVo.class);

        return adminUserInfoVo;
    }

}
