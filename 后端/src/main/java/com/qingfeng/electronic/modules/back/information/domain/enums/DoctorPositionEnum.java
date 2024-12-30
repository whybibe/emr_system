package com.qingfeng.electronic.modules.back.information.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2024/1/2
 */
@Getter
@AllArgsConstructor
public enum DoctorPositionEnum {

    /**
     * 医师职称枚举
     *
     * 普通医师
     * 主治医师
     * 副主任医师
     * 主任医师
     * 专家医师
     */
    DOCTOR_POSITION("普通医师"),
    ASSISTANT_DOCTOR("主治医师"),
    VICE_DIRECTOR("副主任医师"),
    DIRECTOR("主任医师"),
    EXPERT_DOCTOR("专家医师")
    ;

    private String msg;
}
