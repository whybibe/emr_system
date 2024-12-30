package com.qingfeng.electronic.modules.front.login.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
public class UserInfoFrontVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键Id")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "证件类型")
    private String certificatesType;

    @ApiModelProperty(value = "证件编号")
    private String certificatesNo;

    @ApiModelProperty(value = "证件连接")
    private String certificatesUrl;

    @ApiModelProperty(value = "用户状态")
    private Integer userStatus;

    @ApiModelProperty(value = "用户认证状态 ")
    private Integer authStatus;

    @ApiModelProperty(value = "认证结果")
    private String authResult;
}
