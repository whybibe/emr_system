package com.qingfeng.electronic.modules.back.information.listener;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.electronic.modules.back.information.domain.entity.Dict;
import com.qingfeng.electronic.modules.back.information.domain.vo.DictExcelVo;
import com.qingfeng.electronic.modules.back.information.service.DictService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/12/27
 */
public class DictExcelListener extends AnalysisEventListener<DictExcelVo> {

    private DictService dictService;
    private DozerUtils dozerUtils;

    /**
     * Dict Excel 侦听器
     *
     * @param dictService 字典映射器
     * @param dozerUtils docker实用程序
     */
    public DictExcelListener(DictService dictService, DozerUtils dozerUtils) {
        this.dictService = dictService;
        this.dozerUtils = dozerUtils;
    }

    /**
     *  每隔500条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 500;

    /**
     * 缓存数据，新增或者修改
     */
    private List<Dict> cachedAddList = new ArrayList<>(BATCH_COUNT);
    private List<Dict> cachedUpdateList = new ArrayList<>(BATCH_COUNT);


    /**
     * 一行一行读取Excel内容，从第二行读取，第一行是表头不读取
     *
     * @param dictExcelVo
     * @param analysisContext
     */
    @Override
    public void invoke(DictExcelVo dictExcelVo, AnalysisContext analysisContext) {
        // 同一父级不能有重复的内容
        Dict dictEntity = dictService.getOne(
                new LambdaQueryWrapper<Dict>()
                        .eq(Dict::getParentId, dictExcelVo.getParentId())
                        .eq(Dict::getDictName, dictExcelVo.getDictName())
        );

        if (ObjectUtil.isEmpty(dictEntity)) {
            // 不存在，就可以直接进行存储
            cachedAddList.add(dozerUtils.map2(dictExcelVo, Dict.class));
        } else {
            // 数据存在
            dictEntity.setDictName(dictExcelVo.getDictName())
                    .setParentId(dictExcelVo.getParentId())
                    .setDictValue(dictExcelVo.getDictValue())
                    .setDictCode(dictExcelVo.getDictCode())
                    .setId(dictExcelVo.getId());
            cachedUpdateList.add(dictEntity);
        }

        // 在这里需要判断一下是否存储满了
        if (cachedAddList.size() >= BATCH_COUNT) {
            dictService.saveBatch(cachedAddList);
            cachedAddList.clear();
        }
        if (cachedUpdateList.size() >= BATCH_COUNT) {
            dictService.updateBatchById(cachedUpdateList);
            cachedUpdateList.clear();
        }
    }

    /**
     * 读取Excel结束后调用
     *
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            // 在这里需要判断一下是否最后还有值
        if (cachedAddList.size() >= BATCH_COUNT) {
            dictService.saveBatch(cachedAddList);
            cachedAddList.clear();
        }
        if (cachedUpdateList.size() >= BATCH_COUNT) {
            dictService.updateBatchById(cachedUpdateList);
            cachedUpdateList.clear();
        }
    }
}