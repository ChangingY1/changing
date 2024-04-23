package com.changing.classroom.user.service.impl;


import com.alibaba.fastjson.JSON;
import com.changing.classroom.common.exception.ChangingException;
import com.changing.classroom.feign.activity.ActivityFeignClient;
import com.changing.classroom.model.dto.h5.UserLoginDto;
import com.changing.classroom.model.dto.h5.UserRegisterDto;
import com.changing.classroom.model.entity.activity.Activity;
import com.changing.classroom.model.entity.record.HoursRecord;
import com.changing.classroom.model.entity.user.User;
import com.changing.classroom.model.vo.common.RedisKeyHeadEnum;
import com.changing.classroom.model.vo.common.ResultCodeEnum;
import com.changing.classroom.model.vo.h5.HoursRecordVo;
import com.changing.classroom.model.vo.h5.UserInfoVo;
import com.changing.classroom.user.mapper.HourMapper;
import com.changing.classroom.user.mapper.UserMapper;
import com.changing.classroom.user.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HourMapper hourMapper;
    @Autowired
    private ActivityFeignClient activityFeignClient;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String register(UserRegisterDto userRegisterDto) {
        //1 userRegisterDto获取数据
        String name = userRegisterDto.getName();
        String password = userRegisterDto.getPassword();
        String phoneNumber = userRegisterDto.getPhoneNumber();
        String studentId = userRegisterDto.getStudentId();
        String phoneCode = userRegisterDto.getPhoneCode();

        //2 验证码校验
        //2.1 从redis获取发送验证码
        String redisCode = redisTemplate.opsForValue().get(RedisKeyHeadEnum.PHONE_CODE + phoneNumber);
        //2.2 获取输入的验证码，进行比对
        if (!redisCode.equals(phoneCode)) {
            throw new ChangingException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        //3 校验用户名不能重复
        User user = userMapper.selectUserInfoByStudentId(studentId);
        if (user != null) { //存在相同用户名
            throw new ChangingException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        //4 封装添加数据，调用方法添加数据库
        user = new User();
        user.setName(name);
        user.setStudentId(studentId);
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        user.setPhoneNumber(phoneNumber);
        user.setState(1);
        user.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");

        userMapper.save(user);
        user = userMapper.selectUserInfoByStudentId(studentId);

        //5 从redis删除发送的验证码
        redisTemplate.delete(RedisKeyHeadEnum.PHONE_CODE + phoneNumber);

        //6 生成cookie
        String cookie = UUID.randomUUID().toString().replaceAll("-", "");

        //7 把用户信息放到redis里面
        redisTemplate.opsForValue().set(RedisKeyHeadEnum.USER_COOKIE + cookie,
                JSON.toJSONString(user),
                7, TimeUnit.DAYS);

        //8 返回cookie
        return cookie;
    }

    @Override
    public String login(UserLoginDto userLoginDto) {
        //1 dto获取用户名和密码
        String phoneNumber = userLoginDto.getPhoneNumber();
        String password = userLoginDto.getPassword();

        //2 根据用户名查询数据库，得到用户信息
        User user = userMapper.selectUserInfoByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new ChangingException(ResultCodeEnum.LOGIN_ERROR);
        }

        //3 比较密码是否一致
        String database_password = user.getPassword();
        String md5_password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!database_password.equals(md5_password)) {
            throw new ChangingException(ResultCodeEnum.LOGIN_ERROR);
        }

        //4 校验用户是否禁用
        if (Objects.equals(user.getState(), "0")) {
            throw new ChangingException(ResultCodeEnum.ACCOUNT_STOP);
        }

        //5 生成cookie
        String cookie = UUID.randomUUID().toString().replaceAll("-", "");

        //6 把用户信息放到redis里面
        user.setPassword(null);
        redisTemplate.opsForValue().set(RedisKeyHeadEnum.USER_COOKIE + cookie,
                JSON.toJSONString(user),
                7, TimeUnit.DAYS);

        //7 返回cookie
        return cookie;
    }

    @Override
    public UserInfoVo cookieLogin(String cookie) {
        String userInfoJson = redisTemplate.opsForValue().get(RedisKeyHeadEnum.USER_COOKIE + cookie);
        if (!StringUtils.hasText(userInfoJson)) {
            throw new ChangingException(ResultCodeEnum.LOGIN_AUTH);
        }
        UserInfoVo userInfoVo = JSON.parseObject(userInfoJson, UserInfoVo.class);
        return userInfoVo;
    }

    @Override
    public User getUserInfoById(String userId) {
        return userMapper.getUserInfoById(userId);
    }

    @Override
    public List<HoursRecordVo> getHoursRecoedsByUserId(Long userId) {
        List<HoursRecord> records = hourMapper.findByUserId(userId);
        List<HoursRecordVo> recordVos = new ArrayList<>();
        for (HoursRecord record : records) {
            if (record.getIsDeleted() == 0){
                HoursRecordVo recordVo = new HoursRecordVo();
                String title = Objects.requireNonNull(activityFeignClient.getActivitiyInfo(record.getActivityId()).getBody()).getTitle();
                recordVo.setActivityTitle(title);
                recordVo.setChanges(record.getChanges());
                recordVo.setGetTime(record.getCreateTime());
                recordVos.add(recordVo);
            }
        }
        return recordVos;
    }

    @Override
    public List<User> getUserInfoByPage(int page) {
        PageHelper.startPage(page, 6);
        List<User> userList = userMapper.getAllUser();
        return userList;
    }
}
