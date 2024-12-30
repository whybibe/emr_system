package com.qingfeng.electronic.modules.front.message.domain.vo;

import com.qingfeng.electronic.modules.front.message.domain.ro.CommentRo;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

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
@ApiModel(description = "留言板评论分页对象")
public class CommentPageVo implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 总记录数
     */
    private Long total;

    /**
     * 页码
     */
    private Long pageSize;

    /**
     * 每页条目数
     */
    private Long limit;

    /**
     * 用户就诊人信息列表
     */
    private List<CommentRo> commentRoList;
}
