package com.erudev.usercenter.domain.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pengfei.zhao
 * @date 2020/11/8 12:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAddBonusMsgDTO {
    private Integer userId;
    private Integer bonus;
    private String description;
    private String event;
}
