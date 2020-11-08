package com.erudev.usercenter.controller.user;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.erudev.usercenter.auth.CheckLogin;
import com.erudev.usercenter.domain.dto.user.JwtTokenRespDTO;
import com.erudev.usercenter.domain.dto.user.LoginRespDTO;
import com.erudev.usercenter.domain.dto.user.UserLoginDTO;
import com.erudev.usercenter.domain.dto.user.UserRespDTO;
import com.erudev.usercenter.domain.entity.user.User;
import com.erudev.usercenter.service.UserService;
import com.erudev.usercenter.utils.JwtOperator;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pengfei.zhao
 * @date 2020/11/7 13:44
 */
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private WxMaService wxMaService;
    @Autowired
    private JwtOperator jwtOperator;

    @GetMapping("/{id}")
    @CheckLogin
    public User get(@PathVariable Integer id) {
        log.info("我被请求了...");
        return userService.findById(id);
    }

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody UserLoginDTO loginDTO) throws WxErrorException {
        // 微信服务端检验小程序登录状态
        WxMaJscode2SessionResult result = wxMaService.getUserService().getSessionInfo(loginDTO.getCode());

        // 微信的openid, 唯一标识
        String openid = result.getOpenid();
        // 检查用户是否注册
        User user = userService.login(loginDTO, openid);

        // 如果已注册, 颁发token
        Map<String, Object> userInfo = new HashMap<>(3);
        userInfo.put("id", user.getId());
        userInfo.put("wxNickname", user.getWxNickname());
        userInfo.put("role", user.getRoles());

        String token = jwtOperator.generateToken(userInfo);
        log.info("用户{}登录成功, 生成的token={}, 有效期={}",
                user.getWxNickname(), token, jwtOperator.getExpirationDateFromToken(token));

        return LoginRespDTO.builder()
                .user(
                        UserRespDTO.builder()
                                .id(user.getId())
                                .avatarUrl(user.getAvatarUrl())
                                .bonus(user.getBonus())
                                .wxNickname(user.getWxNickname())
                                .build()
                )
                .token(
                        JwtTokenRespDTO.builder()
                                .expirationTime(jwtOperator.getExpirationTime().getTime())
                                .token(token)
                                .build()
                )
                .build();
    }
}
