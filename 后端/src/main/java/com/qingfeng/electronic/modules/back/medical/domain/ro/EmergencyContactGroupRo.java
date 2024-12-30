package com.qingfeng.electronic.modules.back.medical.domain.ro;

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
 * @date 2024/2/19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "联系人组实体")
public class EmergencyContactGroupRo {

    @ApiModelProperty(value = "紧急联系人")
    @NotBlank(message = "紧急联系人不能为空")
    private String emergencyContact;

    @ApiModelProperty(value = "关系")
    @NotBlank(message = "关系不能为空")
    private String relationship;

    @ApiModelProperty(value = "联系人电话")
    @NotBlank(message = "紧急联系人电话不能为空")
    private String contactPhone;
}
