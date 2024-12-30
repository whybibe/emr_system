package com.qingfeng.electronic.modules.front.message.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.electronic.base.entity.BaseEntity;
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
@ApiModel(description = "医院公告信息表")
@TableName("hosp_notice")
public class HospNotice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发布人id")
    private Long userId;

    @ApiModelProperty(value = "发布人姓名")
    private String author;

    @ApiModelProperty(value = "公告标题")
    private String title;

    @ApiModelProperty(value = "公告内容")
    private String content;

    @ApiModelProperty(value = "公告类型")
    private NoticeTypeEnum noticeType;

    @ApiModelProperty(value = "阅读量")
    private Integer readingVolume;
}
