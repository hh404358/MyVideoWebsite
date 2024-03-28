package com.hahaha.exception;


import com.hahaha.enums.AppHttpCodeEnum;
import com.hahaha.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *全局异常处理。最终都会在这个类进行处理异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * SystemException:用户登录的异常
     * @param e
     * @return
     */
    @ExceptionHandler(SystemException.class)
    public Result systemExceptionHandler(SystemException e){

        //打印异常信息，方便我们追溯问题的原因。{}是占位符，具体值由e决定
        log.error("出现了异常! {}",e);

        //从异常对象中获取提示信息封装，然后返回。Result是我们写的类
        return Result.errorResult(e.getCode(),e.getMsg());
    }

   

    // 处理SpringSecurity的权限异常
    @ExceptionHandler(AccessDeniedException.class)
    public Result handleAccessDeniedException(AccessDeniedException e) {
        return Result.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH.getCode(),e.getMessage());//枚举值是500
    }

    //其它异常交给这里处理
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e){

        log.error("出现了异常! {}",e);
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());//枚举值是500
    }
}