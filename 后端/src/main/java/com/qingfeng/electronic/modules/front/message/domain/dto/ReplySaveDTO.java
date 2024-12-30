package com.qingfeng.electronic.modules.front.message.domain.dto;

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
public class ReplySaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论用户Id")
    @NotNull(message = "评论用户Id不能为空")
    private Long userId;

    @ApiModelProperty(value = "主评论Id")
    @NotNull(message = "主评论Id不能为空")
    private Long commentId;

    @ApiModelProperty(value = "被评论者id")
    @NotNull(message = "被评论者id不能为空")
    private Long toId;

    @ApiModelProperty(value = "评论内容")
    @NotBlank(message = "评论内容不能为空")
    private String content;
}
