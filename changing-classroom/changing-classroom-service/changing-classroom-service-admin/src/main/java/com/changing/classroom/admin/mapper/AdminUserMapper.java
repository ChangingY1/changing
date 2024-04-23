package com.changing.classroom.admin.mapper;

import com.changing.classroom.model.entity.admin.user.AdminUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminUserMapper {
    AdminUser findById(Long id);

    AdminUser findByUsername(String username);

    void insert(AdminUser adminUser);

    void update(AdminUser adminUser);
}
