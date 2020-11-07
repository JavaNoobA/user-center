package com.erudev.usercenter;

import com.erudev.usercenter.dao.user.UserMapper;
import com.erudev.usercenter.domain.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author pengfei.zhao
 * @date 2020/11/7 9:12
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {

    private final UserMapper userMapper;

    @GetMapping
    public User testInsert() {
        User user = new User();
        user.setAvatarUrl("xxx");
        user.setBonus(100);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userMapper.insertSelective(user);

        return user;
    }
}
