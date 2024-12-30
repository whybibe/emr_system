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
public class MessageBoardSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发布人Id")
    @NotNull(message = "发布人Id不能为空")
    private Long userId;

    @ApiModelProperty(value = "留言内容")
    @NotBlank(message = "留言内容不能为空")
    private String messageContent;
}
