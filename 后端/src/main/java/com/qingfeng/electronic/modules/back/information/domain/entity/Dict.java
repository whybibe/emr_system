package com.qingfeng.electronic.modules.back.information.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.electronic.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Map;

/**
 * 八大基本数据类型与包装对象
 *
 * byte -> Byte    -> int , tinyint
 * short -> Short    -> int
 * int -> Integer    -> int
 * long -> Long   -> bigint
 *
 * float -> Float    -> double(10,2)
 * double -> Double   -> double(10,2)
 *
 * char -> Character   -> varchar,char
 *
 * boolean -> Boolean  -> tinyint
 *
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/12/25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "数据字典表")
@TableName("dict")
public class Dict extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父级id")
    private Long parentId;

    @ApiModelProperty(value = "名称")
    private String dictName;

    @ApiModelProperty(value = "值")
    private String dictValue;

    @ApiModelProperty(value = "编码")
    private String dictCode;

    @Builder
    public Dict(Long id, Date createTime, Date updateTime,
                Map<String, Object> param, Long parentId, String dictName,
                String dictValue, String dictCode) {
        super(id, createTime, updateTime, param);
        this.parentId = parentId;
        this.dictName = dictName;
        this.dictValue = dictValue;
        this.dictCode = dictCode;
    }
}
