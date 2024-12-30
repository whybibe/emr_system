package com.qingfeng.electronic.modules.back.medical.domain.ro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2024/3/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalRecordNumRo {

    private String name;
    private Integer y;
}
