package com.qingfeng.electronic.modules.front.message.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.electronic.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 留言板信息
 *
 * @author 王淮洋
 * @date 2023-05-12 01:34:10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "留言板信息")
@TableName("message_board")
public class MessageBoard extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发布人Id")
    private Long userId;

    @ApiModelProperty(value = "留言内容")
    private String messageContent;

    @ApiModelProperty(value = "点赞量")
    private Integer likes;

    @ApiModelProperty(value = "差赞量")
    private Integer poorPraise;
}
