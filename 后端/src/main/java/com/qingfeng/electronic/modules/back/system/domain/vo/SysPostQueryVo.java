package com.qingfeng.electronic.modules.back.system.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王淮洋
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SysPostQueryVo {
	
	@ApiModelProperty(value = "岗位编码")
	private String postCode;

	@ApiModelProperty(value = "岗位名称")
	private String name;

	@ApiModelProperty(value = "状态（1正常 0停用）")
	private Boolean status;


}

