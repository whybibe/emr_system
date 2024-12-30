package com.qingfeng.electronic.modules.back.information.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/14
 */
@Getter
@AllArgsConstructor
public enum NoticeTypeEnum {

    PIN_TAI("平台公告"),
    STOP("停诊公告");

    private String msg;
}
