package com.qingfeng.electronic.modules.front.message.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/26
 */
@Getter
@AllArgsConstructor
public enum CustomerTypeEnum {

    PATIENT("患者"),
    DOCTOR("医生");

    private String msg;
}
