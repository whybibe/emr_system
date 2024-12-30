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

import java.util.Date;

/**
 * @author 王淮洋
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "系统访问记录")
@TableName("sys_login_log")
public class SysLoginLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户账号")
	@TableField("username")
	private String username;

	@ApiModelProperty(value = "登录IP地址")
	@TableField("ipaddr")
	private String ipaddr;

	@ApiModelProperty(value = "登录状态（0成功 1失败）")
	@TableField("status")
	private Integer status;

	@ApiModelProperty(value = "提示信息")
	@TableField("msg")
	private String msg;

	@ApiModelProperty(value = "访问时间")
	@TableField("access_time")
	private Date accessTime;


	/**
	 * 逻辑删除 默认效果 0 没有删除 1 已经删除
	 */
	@TableLogic
	@TableField("is_deleted")
	private Integer isDeleted;

}