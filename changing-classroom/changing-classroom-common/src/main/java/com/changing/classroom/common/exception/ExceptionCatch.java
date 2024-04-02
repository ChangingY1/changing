package com.changing.classroom.common.exception;

import com.changing.classroom.model.vo.common.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice  //控制器增强类
@Slf4j
public class ExceptionCatch {

    /**
     * 处理不可控异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity exception(Exception e){
        e.printStackTrace();
        log.error("catch exception:{}",e.getMessage());
        return ResponseEntity.status(ResultCodeEnum.SERVER_ERROR.getCode()).body(ResultCodeEnum.SERVER_ERROR.getMessage());
    }

    /**
     * 处理可控异常  自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(ChangingException.class)
    @ResponseBody
    public ResponseEntity exception(ChangingException e){
        log.error("catch exception:{}",e);
        return ResponseEntity.status(e.getCode()).body(e.getMessage());
    }
}
