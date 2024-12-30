package com.qingfeng.electronic.modules.back.system.domain.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分配菜单
 *
 * @author 王淮洋
 * @date 2023-12-23
 */
@ApiModel(description = "分配菜单")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssginMenuVo {

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "菜单id列表")
    private List<Long> menuIdList;

}
