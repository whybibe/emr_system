package com.qingfeng.electronic.modules.back.hosp.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2024/1/1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HospDepartmentUpdateStatusDTO implements Serializable {

    private static final long serialVersionUID = -6849407238537215312L;

    @ApiModelProperty("科室编码（唯一的）")
    private Long id;

    @ApiModelProperty(value = "科室名字")
    private String depName;

    @NotBlank(message = "状态不能为空")
    @ApiModelProperty(value = "状态")
    private Integer status;
}
