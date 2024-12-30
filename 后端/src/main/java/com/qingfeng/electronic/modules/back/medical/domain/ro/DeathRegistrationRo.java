package com.qingfeng.electronic.modules.back.medical.domain.ro;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qingfeng.electronic.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 死亡登记表
 *
 * @author 王淮洋
 * @date 2024-01-09 21:38:41
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "死亡登记表")
public class DeathRegistrationRo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键Id")
    private Long id;

    @ApiModelProperty(value = "病案号Id")
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

    @ApiModelProperty(value = "死亡登记日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deathDate;

    @ApiModelProperty(value = "死亡详细时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deathTime;

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

    @ApiModelProperty(value = "确认医生Id")
    private Long confirmingDoctorId;

    @ApiModelProperty(value = "确认医生")
    private String confirmingDoctorName;

    @ApiModelProperty(value = "确认医生职位")
    private String confirmingDoctorPosition;

    @ApiModelProperty(value = "死亡原因")
    private String causeOfDeath;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private String fileUrl;
}
