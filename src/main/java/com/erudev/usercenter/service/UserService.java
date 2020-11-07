package com.erudev.usercenter.service;

import com.erudev.usercenter.dao.user.UserMapper;
import com.erudev.usercenter.domain.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pengfei.zhao
 * @date 2020/11/7 13:45
 */
@Service
public class UserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    public User findById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
