package com.erudev.usercenter.auth;

import com.erudev.usercenter.utils.JwtOperator;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pengfei.zhao
 * @date 2020/11/8 20:16
 */
@Aspect
@Component
public class AuthAspect {
    @Autowired
    private JwtOperator jwtOperator;

    @Around("@annotation(com.erudev.usercenter.auth.CheckLogin)")
    public Object checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {
        checkToken();
        return joinPoint.proceed();
    }

    private void checkToken() {
        HttpServletRequest servletRequest = getHttpServletRequest();
        // 1. 检验 header 中的 token 是否合法或者过期
        String token = servletRequest.getHeader("X-Token");

        if (!jwtOperator.validateToken(token)) {
            throw new UserSecurityException("非法token");
        }
        // 2. 如果检验通过, 将token中携带的user信息 塞到 request 中
        Claims userClaim = jwtOperator.getClaimsFromToken(token);
        servletRequest.setAttribute("id", userClaim.get("id"));
        servletRequest.setAttribute("wxNickname", userClaim.get("wxNickname"));
        servletRequest.setAttribute("role", userClaim.get("role"));
    }

    private HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        if (attributes == null) {
            return null;
        }
        return attributes.getRequest();
    }
}
