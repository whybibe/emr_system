package com.qingfeng.electronic.modules.back.information.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.electronic.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

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
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "医院信息实体")
@TableName("hospital_info")
public class HospitalInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "医院名")
    private String hospName;

    @ApiModelProperty(value = "医院编号")
    private String hospCode;

    @ApiModelProperty(value = "医院logo")
    private String logoUrl;

    @ApiModelProperty(value = "医院联系人")
    private String contactsName;

    @ApiModelProperty(value = "医院联系人电话")
    private String contactsPhone;

    @ApiModelProperty(value = "所处地区邮编")
    private String zipCode;

    @ApiModelProperty(value = "医院地址")
    private String address;

    @ApiModelProperty(value = "医院路线")
    private String route;

    @ApiModelProperty(value = "医院简介")
    private String hospContent;

    @ApiModelProperty(value = "医院电子病例管理规则")
    private String manageRule;
}

