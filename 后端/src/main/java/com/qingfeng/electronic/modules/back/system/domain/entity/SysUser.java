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

import java.util.List;

/**
 * @author 王淮洋
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户")
@TableName("sys_user")
public class SysUser extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户名")
	@TableField("username")
	private String username;

	@ApiModelProperty(value = "密码")
	@TableField("password")
	private String password;

	@ApiModelProperty(value = "姓名")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "手机")
	@TableField("phone")
	private String phone;

	@ApiModelProperty(value = "头像地址")
	@TableField("head_url")
	private String headUrl;

	@ApiModelProperty(value = "部门id")
	@TableField("dept_id")
	private Long deptId;

	@ApiModelProperty(value = "岗位id")
	@TableField("post_id")
	private Long postId;

	@ApiModelProperty(value = "描述")
	@TableField("description")
	private String description;

	@ApiModelProperty(value = "状态（1：正常 0：停用）")
	@TableField("status")
	private Integer status;


	@TableField(exist = false)
	private List<SysRole> roleList;

	/**
	 * 岗位
	 */
	@TableField(exist = false)
	private String postName;

	/**
	 * 部门
	 */
	@TableField(exist = false)
	private String deptName;


	/**
	 * 逻辑删除 默认效果 0 没有删除 1 已经删除
	 */
	@TableLogic
	@TableField("is_deleted")
	private Integer isDeleted;
}

