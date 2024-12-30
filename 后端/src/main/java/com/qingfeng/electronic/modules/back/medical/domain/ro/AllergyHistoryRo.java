package com.qingfeng.electronic.modules.back.medical.domain.ro;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2024/5/1
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "过敏史")
public class AllergyHistoryRo {

    /**
     * 过敏史标题
     */
    private String title;
    /**
     * 过敏史内容
     */
    private String content;
}
