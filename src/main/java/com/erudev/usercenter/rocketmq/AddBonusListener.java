package com.erudev.usercenter.rocketmq;

import com.erudev.usercenter.dao.bonus.BonusEventLogMapper;
import com.erudev.usercenter.dao.user.UserMapper;
import com.erudev.usercenter.domain.entity.bonus.BonusEventLog;
import com.erudev.usercenter.domain.entity.user.User;
import com.erudev.usercenter.domain.message.UserAddBonusMsgDTO;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author pengfei.zhao
 * @date 2020/11/8 13:06
 */
@Service
@RocketMQMessageListener(consumerGroup = "consumer-group", topic = "add-bonus")
public class AddBonusListener implements RocketMQListener<UserAddBonusMsgDTO> {

    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private BonusEventLogMapper bonusEventLogMapper;

    @Override
    public void onMessage(UserAddBonusMsgDTO message) {
        // 1. 为用户加积分
        User user = userMapper.selectByPrimaryKey(message.getUserId());
        user.setBonus(user.getBonus() + message.getBonus());
        userMapper.updateByPrimaryKey(user);

        // 2. 记录日志到bonus_event_log
        bonusEventLogMapper.insertSelective(BonusEventLog.builder()
                .userId(message.getUserId())
                .event("CONTRIATOR")
                .value(message.getBonus())
                .description("投稿加积分")
                .createTime(new Date())
                .build());
    }
}
