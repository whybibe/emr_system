package com.qingfeng.electronic.modules.front.login.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@ApiModel(description = "前台用户表")
public class UserInfoFrontSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Id")
    @NotNull(message = "用户Id不能为空")
    private Long id;

    @ApiModelProperty(value = "姓名")
    @NotBlank(message = "用户姓名不能为空")
    private String name;

    @ApiModelProperty(value = "证件类型")
    @NotBlank(message = "证件类型不能为空")
    private String certificatesType;

    @NotBlank(message = "证件编号不能为空")
    @ApiModelProperty(value = "证件编号")
    private String certificatesNo;

    @ApiModelProperty(value = "证件链接")
    @NotBlank(message = "证件链接不能为空")
    private String certificatesUrl;

}
