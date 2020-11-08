package com.erudev.usercenter.service;

import com.erudev.usercenter.dao.bonus.BonusEventLogMapper;
import com.erudev.usercenter.dao.user.UserMapper;
import com.erudev.usercenter.domain.entity.bonus.BonusEventLog;
import com.erudev.usercenter.domain.entity.user.User;
import com.erudev.usercenter.domain.message.UserAddBonusMsgDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author pengfei.zhao
 * @date 2020/11/7 13:45
 */
@Service
@Slf4j
public class UserService {

    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private BonusEventLogMapper bonusEventLogMapper;

    public User findById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public void addBonus(UserAddBonusMsgDTO msgDTO){
        // 1. 为用户加积分
        Integer userId = msgDTO.getUserId();
        Integer bonus = msgDTO.getBonus();
        User user = this.userMapper.selectByPrimaryKey(userId);

        user.setBonus(user.getBonus() + bonus);
        this.userMapper.updateByPrimaryKeySelective(user);

        // 2. 记录日志到bonus_event_log表里面
        this.bonusEventLogMapper.insert(
                BonusEventLog.builder()
                        .userId(userId)
                        .value(bonus)
                        .event(msgDTO.getEvent())
                        .createTime(new Date())
                        .description(msgDTO.getDescription())
                        .build()
        );
        log.info("积分添加完毕...");
    }
}
