package com.qingfeng.electronic.modules.back.hosp.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2024/1/7
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepPatientVo {

    /**
     * 展示的值
     */
    private String label;
    /**
     * 真实的值
     */
    private Long value;

    private List<DepPatientVo> children;
}
