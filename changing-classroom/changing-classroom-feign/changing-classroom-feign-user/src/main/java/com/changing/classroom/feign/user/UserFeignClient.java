package com.changing.classroom.feign.user;

import com.changing.classroom.model.vo.h5.UserInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-user",path = "api/user")
public interface UserFeignClient {

    @GetMapping("auth/getUserInfoById/{userId}")
    ResponseEntity<UserInfoVo> getUserInfoById(@PathVariable("userId") String userId);

}
