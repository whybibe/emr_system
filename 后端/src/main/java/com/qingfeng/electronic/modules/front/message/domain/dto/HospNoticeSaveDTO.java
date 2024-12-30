package com.qingfeng.electronic.modules.front.message.domain.dto;

import com.qingfeng.electronic.modules.front.message.domain.enums.NoticeTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 医院公告信息表
 *
 * @author 王淮洋
 * @date 2023-04-14 14:47:17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "医院公告信息保存实体")
public class HospNoticeSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公告标题")
    @NotBlank(message = "公告标题不能为空")
    private String title;

    @ApiModelProperty(value = "公告内容")
    @NotBlank(message = "公告内容不能为空")
    private String content;

    @ApiModelProperty(value = "公告类型")
    @NotNull(message = "公告类型不能为空")
    private NoticeTypeEnum noticeType;
}
