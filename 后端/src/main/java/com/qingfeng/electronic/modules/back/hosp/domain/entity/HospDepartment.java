package com.qingfeng.electronic.modules.back.hosp.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.electronic.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

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
@TableName("hosp_department")
public class HospDepartment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "科室的名字 （唯一的）")
    private String depName;

    @ApiModelProperty(value = "科室编码 （唯一的）")
    private String depCode;

    @ApiModelProperty(value = "状态（启用，不启用）")
    private Integer status;

    @ApiModelProperty(value = "科室下的门诊名")
    private String patientName;

    @ApiModelProperty(value = "门诊编号")
    private String patientCode;

    @ApiModelProperty(value = "门诊介绍")
    private String intro;

}

