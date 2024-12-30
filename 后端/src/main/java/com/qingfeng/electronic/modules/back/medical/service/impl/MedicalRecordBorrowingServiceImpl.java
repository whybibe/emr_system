package com.qingfeng.electronic.modules.back.medical.service.impl;

import com.alibaba.excel.EasyExcelFactory;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.electronic.base.handler.GeneralAuthException;
import com.qingfeng.electronic.base.util.result.ResultCodeEnum;
import com.qingfeng.electronic.modules.back.medical.dao.MedicalRecordBorrowingMapper;
import com.qingfeng.electronic.modules.back.medical.domain.entity.MedicalRecordBorrowing;
import com.qingfeng.electronic.modules.back.medical.domain.vo.MedicalRecordBorrowingVo;
import com.qingfeng.electronic.modules.back.medical.service.MedicalRecordBorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2024/3/23
 */
@Service
public class MedicalRecordBorrowingServiceImpl extends ServiceImpl<MedicalRecordBorrowingMapper, MedicalRecordBorrowing> implements MedicalRecordBorrowingService {

    private DozerUtils dozerUtils;

    @Autowired
    public MedicalRecordBorrowingServiceImpl(DozerUtils dozerUtils) {
        this.dozerUtils = dozerUtils;
    }

    /**
     * 导出病历
     *
     * @param response 响应
     */
    @Override
    public void exportMedicalRecord(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 设置URLEncoder.encode可以解决中文乱码问题   这个和EasyExcel导没有关系，处理的是文件的命名的乱码
            response.setHeader("Content-disposition",
                    "attachment;filename=" +
                            URLEncoder.encode("病案借阅未归还名单", "UTF-8") +
                            ".xlsx");

            // 调用方法实现写操作
            EasyExcelFactory.write(response.getOutputStream(), MedicalRecordBorrowingVo.class)
                    .sheet("病案借阅未归还名单")
                    .doWrite(dozerUtils.mapList(baseMapper.selectList(
                            new LambdaQueryWrapper<MedicalRecordBorrowing>()
                                    .eq(MedicalRecordBorrowing::getIsReturn, "否")
                    ), MedicalRecordBorrowingVo.class));
        } catch (Exception e) {
            log.error("数据字典模板导出出现未知异常，异常信息如下：", e);
            throw new GeneralAuthException(ResultCodeEnum.FAIL.getCode(), e.getMessage());
        }
    }
}
