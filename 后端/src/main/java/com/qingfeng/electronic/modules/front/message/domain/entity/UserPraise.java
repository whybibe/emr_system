package com.qingfeng.electronic.modules.front.message.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.electronic.base.entity.BaseEntity;
import com.qingfeng.electronic.modules.front.message.domain.enums.PriseTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户点赞关系
 *
 * @author 王淮洋
 * @date 2023-05-12 01:34:10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户点赞关系")
@TableName("user_praise")
public class UserPraise extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户Id")
    private Long userId;

    @ApiModelProperty(value = "留言信息Id")
    private Long informationId;

    @ApiModelProperty(value = "点赞类型")
    private PriseTypeEnum priseType;
}
