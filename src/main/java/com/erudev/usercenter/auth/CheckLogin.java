package com.erudev.usercenter.auth;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * @author pengfei.zhao
 * @date 2020/11/8 20:15
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface CheckLogin {
}
