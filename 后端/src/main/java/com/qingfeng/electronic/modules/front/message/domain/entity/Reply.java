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
 * 回复表
 *
 * @author 王淮洋
 * @date 2023-05-13 17:50:14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "回复表")
@TableName("reply")
public class Reply extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论用户Id")
    private Long userId;

    @ApiModelProperty(value = "主评论Id")
    private Long commentId;

    @ApiModelProperty(value = "被评论者id")
    private Long toId;

    @ApiModelProperty(value = "点赞人数")
    private Integer likeNum;

    @ApiModelProperty(value = "评论内容")
    private String content;
}
