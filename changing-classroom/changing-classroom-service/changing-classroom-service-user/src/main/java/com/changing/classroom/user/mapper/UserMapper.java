package com.changing.classroom.user.mapper;

import com.changing.classroom.model.entity.user.User;
import com.changing.classroom.model.vo.h5.UserInfoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> getAllUser();
    //根据用户名查询数据库表 user表
    User selectUserInfoByStudentId(String studentId);
    User getUserInfoById(String userId);
    //2 用户添加
    void save(User user);

    //3 用户修改
    void update(User user);

    //4 用户删除
    void deleteById(Long id);

    void deleteByStudentId(String studentId);

    User selectUserInfoByPhoneNumber(String phone);
}
