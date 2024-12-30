package com.qingfeng.electronic.modules.back.hosp.domain.ro;

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
public class HospDepartmentRo {

    @ApiModelProperty(value = "主键Id")
    private Long id;

    @ApiModelProperty(value = "科室下的门诊名")
    private String patientName;

    @ApiModelProperty(value = "门诊编号")
    private String patientCode;

    @ApiModelProperty(value = "门诊介绍")
    private String intro;

    @ApiModelProperty(value = "状态（启用，不启用）")
    private Integer status;
}
