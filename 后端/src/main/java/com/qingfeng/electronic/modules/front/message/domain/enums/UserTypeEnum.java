package com.qingfeng.electronic.modules.front.message.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/5/3
 */
@Getter
@AllArgsConstructor
public enum UserTypeEnum {

    ADMIN("后台"),
    FRONT("前台");

    private String msg;
}
