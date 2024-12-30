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
public enum IsSeeEnum {

    IS_NOT_VIEWED("待查看"),
    IS_VIEWED("已查看");

    private String msg;
}
