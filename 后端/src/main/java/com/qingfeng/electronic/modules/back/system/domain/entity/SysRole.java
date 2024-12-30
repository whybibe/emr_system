package com.qingfeng.electronic.modules.back.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.electronic.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author 王淮洋
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "角色信息")
@TableName("sys_role")
public class SysRole extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "角色名")
	@TableField("role_name")
	private String roleName;

	@ApiModelProperty(value = "角色编码")
	@TableField("role_code")
	private String roleCode;

	@ApiModelProperty(value = "角色描述")
	@TableField("description")
	private String description;


	/**
	 * 逻辑删除 默认效果 0 没有删除 1 已经删除
	 */
	@TableLogic
	@TableField("is_deleted")
	private Integer isDeleted;
}

