package com.qingfeng.electronic.modules.front.login.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.electronic.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 患者用户表
 *
 * @author 王淮洋
 * @date 2023-04-21 23:03:12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "患者用户表")
@TableName("user_info")
public class UserInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "微信openId")
    private String openId;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "账号（手机/邮箱）")
    private String account;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "证件类型")
    private String certificatesType;

    @ApiModelProperty(value = "证件编号")
    private String certificatesNo;

    @ApiModelProperty(value = "证件连接")
    private String certificatesUrl;

    @ApiModelProperty(value = "用户状态")
    private Integer userStatus;

    @ApiModelProperty(value = "用户状态结果")
    private String authStatusResult;

    @ApiModelProperty(value = "用户头像")
    private String headImgUrl;

    @ApiModelProperty(value = "用户认证状态 -1: 认证不通过  0：待认证  1： 认证通过")
    private Integer authStatus;

    @ApiModelProperty(value = "认证结果")
    private String authResult;
}
