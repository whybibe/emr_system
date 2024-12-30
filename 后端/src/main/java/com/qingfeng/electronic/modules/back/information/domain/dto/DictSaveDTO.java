package com.qingfeng.electronic.modules.back.information.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/12/30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(description = "保存数据字典表")
public class DictSaveDTO implements Serializable {

    @NotNull(message = "父级id不能为空")
    @ApiModelProperty(value = "父级id")
    private Long parentId;

    @NotEmpty(message = "名称不能为空")
    @ApiModelProperty(value = "名称")
    private String dictName;

    @ApiModelProperty(value = "值")
    private String dictValue;

    @ApiModelProperty(value = "编码")
    private String dictCode;
}
