package com.changing.classroom.activity.mapper;

import com.changing.classroom.model.entity.record.UARecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserActivityMapper {

    //根据id查询数据库表
    UARecord selectById(Long id);
    UARecord select(UARecord uaRelation);
    //2 添加
    int save(UARecord uaRelation);

    //3 修改
    int update(UARecord uaRelation);

    //4 删除
    int deleteById(Long id);

    List<Long> getJoinedActivityIdsByUserId(long userId);
}
