package com.qingfeng.electronic.modules.back.medical.domain.entity;

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
 * @date 2024/4/5
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "病例库")
@TableName(value = "case_library", autoResultMap = true)
public class CaseLibrary extends BaseEntity {

    @ApiModelProperty(value = "首次登记人Id")
    private Long registrarId;

    @ApiModelProperty(value = "首次登记人姓名")
    private String registrarName;

    @ApiModelProperty(value = "病例名称")
    private String caseName;

    @ApiModelProperty(value = "疾病编码")
    private String caseNum;
}
