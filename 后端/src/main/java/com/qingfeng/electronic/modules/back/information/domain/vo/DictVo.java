package com.qingfeng.electronic.modules.back.information.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/12/25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(description = "数据字典表")
public class DictVo {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "父级id")
    private Long parentId;

    @ApiModelProperty(value = "名称")
    private String dictName;

    @ApiModelProperty(value = "值")
    private String dictValue;

    @ApiModelProperty(value = "编码")
    private String dictCode;

    @ApiModelProperty(value = "子级")
    private List<DictVo> children;
}
