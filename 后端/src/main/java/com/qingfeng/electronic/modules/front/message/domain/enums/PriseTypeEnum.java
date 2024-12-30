package com.qingfeng.electronic.modules.front.message.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/5/12
 */
@Getter
@AllArgsConstructor
public enum PriseTypeEnum {

    GREAT_LIKE("好赞"),
    POOR_LIKE("差赞");

    private String msg;
}
