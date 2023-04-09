package com.blog.hander.exception;


import com.blog.domain.ResponseResult;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 定义的controller层异常拦截
 * 对于controller层的异常 ，直接进行拦截返回
 */
@RestControllerAdvice //提供一个统一的错误响应。
@Slf4j      //使用之后可以直接使用log
public class GlobalExceptionHandler {
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        //1. 打印异常信息
        log.error("出现了异常!  [",e +  "]");
        //2. 从异常对象中获取提示信息
        //3. 封装返回
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }

    //todo 处理其他的异常
    @ExceptionHandler(Exception.class)
    public ResponseResult ExceptionHandler(Exception e){
        //1. 打印异常信息
        log.error("出现了异常!  [",e   + "]");
        //2. 从异常对象中获取提示信息
        //3. 封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }
}
