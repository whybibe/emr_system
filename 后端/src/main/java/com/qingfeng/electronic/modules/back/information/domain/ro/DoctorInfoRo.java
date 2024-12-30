package com.qingfeng.electronic.modules.back.information.domain.ro;

import com.qingfeng.electronic.modules.back.information.domain.enums.DoctorPositionEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2024/1/6
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorInfoRo {

    /**
     * 医师信息Id
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机
     */
    private String phone;
    /**
     * 头像地址
     */
    private String headUrl;
    /**
     * 状态（1：正常  0：停用）
     */
    private Integer status;
    /**
     * 医师职称
     */
    private DoctorPositionEnum position;
    /**
     * 所属科室门诊信息
     */
    private String departmentPatient;

    @ApiModelProperty(value = "工作日")
    private String weekday;
    /**
     * 主治简介
     */
    private String introduction;
}
