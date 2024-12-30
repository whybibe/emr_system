package com.qingfeng.electronic.modules.back.medical.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.electronic.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 出院登记表
 *
 * @author 王淮洋
 * @date 2024-01-09 21:38:41
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "出院登记表")
@TableName("discharge_registration")
public class DischargeRegistration extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "入院登记表Id")
    private Long admissionRegistrationId;

    @ApiModelProperty(value = "出院登记日期")
    private Date dischargeDate;

    @ApiModelProperty(value = "登记人Id")
    private Long registrarId;

    @ApiModelProperty(value = "登记人")
    private String registrarName;

    @ApiModelProperty(value = "登记人职位")
    private String registrarPosition;

    @ApiModelProperty(value = "费用计算")
    private Double costCalculation;

    @ApiModelProperty(value = "是否结算")
    private String isSettled;
}
