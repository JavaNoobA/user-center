package com.erudev.usercenter.auth;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author pengfei.zhao
 * @date 2020/11/8 20:28
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserSecurityException.class)
    public String error(UserSecurityException ex){
        return ex.getMessage();
    }
}
