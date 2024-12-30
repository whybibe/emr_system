package com.qingfeng.electronic.modules.back.medical.domain.ro;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
public class DischargeRegistrationRo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键Id")
    private Long id;

    @ApiModelProperty(value = "病案号")
    private Long aId;

    @ApiModelProperty(value = "入院登记表Id")
    private Long admissionRegistrationId;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "联系电话")
    private String contactNumber;

    @ApiModelProperty(value = "科室")
    private String department;

    @ApiModelProperty(value = "门诊")
    private String patient;

    @ApiModelProperty(value = "医师名称")
    private String doctorName;

    @ApiModelProperty(value = "病房号")
    private String wardNumber;

    @ApiModelProperty(value = "床号")
    private String bedNumber;

    @ApiModelProperty(value = "出院登记日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private String fileUrl;
}
