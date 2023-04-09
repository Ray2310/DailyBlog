package com.blog.exception;


import com.blog.enums.AppHttpCodeEnum;

/**
 * 系统异常抛出类
 */
public class SystemException extends RuntimeException{

    private int code; //异常编码

    private String msg; //异常信息

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

}
