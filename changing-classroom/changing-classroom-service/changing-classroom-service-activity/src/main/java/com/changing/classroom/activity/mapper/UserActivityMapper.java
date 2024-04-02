package com.changing.classroom.activity.mapper;

import com.changing.classroom.model.entity.relation.UARelation;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserActivityMapper {

    //根据id查询数据库表
    UARelation selectById(Long id);
    UARelation select(UARelation uaRelation);
    //2 添加
    int save(UARelation uaRelation);

    //3 修改
    int update(UARelation uaRelation);

    //4 删除
    int deleteById(Long id);

}
