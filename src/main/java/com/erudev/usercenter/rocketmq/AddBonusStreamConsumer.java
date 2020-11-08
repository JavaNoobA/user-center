package com.erudev.usercenter.rocketmq;

import com.erudev.usercenter.domain.message.UserAddBonusMsgDTO;
import com.erudev.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

/**
 * @author pengfei.zhao
 * @date 2020/11/8 14:48
 */
@Service
@Slf4j
public class AddBonusStreamConsumer {
    @Autowired
    private UserService userService;

    @StreamListener(Sink.INPUT)
    public void receive(UserAddBonusMsgDTO msg) {
        msg.setEvent("CONTRIBUTE");
        msg.setDescription("投稿加积分..");
        this.userService.addBonus(msg);
    }
}
