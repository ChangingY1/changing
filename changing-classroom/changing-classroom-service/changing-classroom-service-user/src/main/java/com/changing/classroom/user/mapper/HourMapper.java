package com.changing.classroom.user.mapper;


import com.changing.classroom.model.entity.record.HoursRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HourMapper {

    int insert(HoursRecord hoursRecord);

    int update(HoursRecord hoursRecord);

    int delete(@Param("id") Long id);

    HoursRecord findById(@Param("id") Long id);

    List<HoursRecord> findByUserId(@Param("userId") Long userId);

    List<HoursRecord> findAll();
}
