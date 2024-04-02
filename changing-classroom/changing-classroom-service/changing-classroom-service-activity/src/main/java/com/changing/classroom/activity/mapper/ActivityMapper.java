package com.changing.classroom.activity.mapper;

import com.changing.classroom.model.entity.activity.Activity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActivityMapper {

    //根据id查询数据库表 activities
    Activity selectActivityById(Long id);

    List<Activity> getAllActivities();
    //2 活动添加
    int save(Activity activity);

    //3 活动修改
    int update(Activity activity);

    //4 活动删除
    int deleteById(Long id);

}
