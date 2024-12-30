package com.qingfeng.electronic.modules.front.message.domain.ro;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

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
public class MessageBoardRo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键Id不能为空")
    private Long id;

    @ApiModelProperty(value = "发布人Id")
    private Long userId;

    @ApiModelProperty(value = "留言内容")
    private String messageContent;

    @ApiModelProperty(value = "点赞量")
    private Integer likes;

    @ApiModelProperty(value = "差赞量")
    private Integer poorPraise;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 用户信息封装在这里
     */
    @ApiModelProperty(value = "用户头像")
    private String headImgUrl;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "账号（手机/邮箱）")
    private String account;
}
