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
import java.util.List;

/**
 * 主评论表
 *
 * @author 王淮洋
 * @date 2023-05-13 17:50:15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "主评论表")
public class CommentRo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主评论Id")
    private Long id;

    @ApiModelProperty(value = "评论用户Id")
    private Long userId;

    @ApiModelProperty(value = "留言Id")
    private Long msgId;

    @ApiModelProperty(value = "点赞人数")
    private Integer likeNum;

    @ApiModelProperty(value = "评论内容")
    private String content;

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

    private List<ReplyRo> reply;
}
