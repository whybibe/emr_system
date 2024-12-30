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
public class SysLoginLogQueryVo {
	
	@ApiModelProperty(value = "用户账号")
	private String username;

	private String createTimeBegin;
	private String createTimeEnd;

}

