package com.hahaha.exception;

import com.hahaha.enums.AppHttpCodeEnum;
import lombok.Data;

/**
 * 统一异常处理
 */
@Data
public class SystemException extends RuntimeException{

    private int code;

    private String msg;



    //定义一个构造方法，接收的参数是枚举类型
    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        //把某个枚举类里面的code和msg赋值给异常对象
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }
}
