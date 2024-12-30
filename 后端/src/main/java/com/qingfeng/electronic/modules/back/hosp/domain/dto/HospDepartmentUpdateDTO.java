package com.qingfeng.electronic.modules.back.hosp.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "科室管理")
public class HospDepartmentUpdateDTO {

    @NotBlank(message = "原科室名称不能为空")
    @ApiModelProperty(value = "原科室名称")
    private String oldDepName;

    @NotBlank(message = "科室名称不能为空")
    @ApiModelProperty(value = "科室的名字 （唯一的）")
    private String depName;

    @NotBlank(message = "原科室编码不能为空")
    @ApiModelProperty(value = "原科室编码")
    private String oldDepCode;

    @NotBlank(message = "科室编码不能为空")
    @ApiModelProperty(value = "科室编码 （唯一的）")
    private String depCode;

    @ApiModelProperty(value = "旧的门诊名称")
    private String oldPatientName;

    @ApiModelProperty(value = "科室下的门诊名")
    private String patientName;

    @ApiModelProperty(value = "旧的门诊编号")
    private String oldPatientCode;

    @ApiModelProperty(value = "门诊编号")
    private String patientCode;

    @ApiModelProperty(value = "旧的门诊介绍")
    private String oldIntro;

    @ApiModelProperty(value = "门诊介绍")
    private String intro;

}

