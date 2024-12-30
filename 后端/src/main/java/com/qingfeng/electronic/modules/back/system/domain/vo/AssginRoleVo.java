package com.qingfeng.electronic.modules.back.system.domain.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 王淮洋
 */
@ApiModel(description = "分配菜单")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssginRoleVo {

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "角色id列表")
    private List<Long> roleIdList;

}
