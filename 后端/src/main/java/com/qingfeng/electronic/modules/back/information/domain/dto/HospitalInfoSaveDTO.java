package com.qingfeng.electronic.modules.back.information.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/12/30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(description = "医院信息保存实体")
public class HospitalInfoSaveDTO{

    @ApiModelProperty(value = "医院名")
    @NotBlank(message = "医院名不能为空")
    private String hospName;

    @ApiModelProperty(value = "医院编号")
    @NotBlank(message = "医院编号不能为空")
    private String hospCode;

    @ApiModelProperty(value = "医院logo")
    @NotBlank(message = "医院logo不能为空")
    private String logoUrl;

    @ApiModelProperty(value = "医院联系人")
    @NotBlank(message = "医院联系人不能为空")
    private String contactsName;

    @ApiModelProperty(value = "医院联系人电话")
    @NotBlank(message = "医院联系人电话不能为空")
    private String contactsPhone;

    @ApiModelProperty(value = "所处地区邮编")
    @NotBlank(message = "所处地区邮编不能为空")
    private String zipCode;

    @ApiModelProperty(value = "医院地址")
    @NotBlank(message = "医院地址不能为空")
    private String address;

    @ApiModelProperty(value = "医院路线")
    @NotBlank(message = "医院路线不能为空")
    private String route;

    @ApiModelProperty(value = "医院简介")
    @NotBlank(message = "医院简介不能为空")
    private String hospContent;

    @ApiModelProperty(value = "医院电子病例管理规则")
    @NotBlank(message = "医院电子病例管理规则不能为空")
    private String manageRule;
}

