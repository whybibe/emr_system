package com.qingfeng.electronic.modules.back.information.domain.vo;

import com.qingfeng.electronic.modules.back.information.domain.ro.DoctorInfoRo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2024/1/6
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorInfoVo implements Serializable {

    private static  final long serialVersionUID = -1L;

    /**
     * 总条数
     */
    private Long total;
    /**
     * 页码
     */
    private Long pageNo;
    /**
     *  每页条数
     */
    private Long pageSize;

    /**
     * 医师信息
     */
    private List<DoctorInfoRo>doctorInfoRos;
}
