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
@ApiModel(description = "操作日志记录")
@TableName("sys_oper_log")
public class SysOperLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "模块标题")
	@TableField("title")
	private String title;

	@ApiModelProperty(value = "业务类型（0其它 1新增 2修改 3删除）")
	@TableField("business_type")
	private String businessType;

	@ApiModelProperty(value = "方法名称")
	@TableField("method")
	private String method;

	@ApiModelProperty(value = "请求方式")
	@TableField("request_method")
	private String requestMethod;

	@ApiModelProperty(value = "操作类别（0其它 1后台用户 2手机端用户）")
	@TableField("operator_type")
	private String operatorType;

	@ApiModelProperty(value = "操作人员")
	@TableField("oper_name")
	private String operName;

	@ApiModelProperty(value = "部门名称")
	@TableField("dept_name")
	private String deptName;

	@ApiModelProperty(value = "请求URL")
	@TableField("oper_url")
	private String operUrl;

	@ApiModelProperty(value = "主机地址")
	@TableField("oper_ip")
	private String operIp;

	@ApiModelProperty(value = "请求参数")
	@TableField("oper_param")
	private String operParam;

	@ApiModelProperty(value = "操作信息")
	private String message;

	@ApiModelProperty(value = "返回参数")
	@TableField("json_result")
	private String jsonResult;

	@ApiModelProperty(value = "操作状态（0正常 1异常）")
	@TableField("status")
	private Integer status;

	@ApiModelProperty(value = "错误消息")
	@TableField("error_msg")
	private String errorMsg;

	@ApiModelProperty(value = "操作时间")
	@TableField("oper_time")
	private Date operTime;


	/**
	 * 逻辑删除 默认效果 0 没有删除 1 已经删除
	 */
	@TableLogic
	@TableField("is_deleted")
	private Integer isDeleted;
}