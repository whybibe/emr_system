package com.qingfeng.electronic.modules.back.information.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.electronic.base.entity.BaseEntity;
import com.qingfeng.electronic.base.handler.GeneralAuthException;
import com.qingfeng.electronic.base.util.result.ResultCodeEnum;
import com.qingfeng.electronic.modules.back.information.dao.DictMapper;
import com.qingfeng.electronic.modules.back.information.domain.dto.DictSaveDTO;
import com.qingfeng.electronic.modules.back.information.domain.dto.DictUpdateDTO;
import com.qingfeng.electronic.modules.back.information.domain.entity.Dict;
import com.qingfeng.electronic.modules.back.information.domain.enums.DictDwEnum;
import com.qingfeng.electronic.modules.back.information.domain.vo.DictExcelVo;
import com.qingfeng.electronic.modules.back.information.domain.vo.DictVo;
import com.qingfeng.electronic.modules.back.information.listener.DictExcelListener;
import com.qingfeng.electronic.modules.back.information.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/12/25
 */
@Service
@Slf4j
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    private DozerUtils dozerUtils;
    private StringRedisTemplate stringRedisTemplate;
    private ObjectMapper objectMapper;

    /**
     * dict 服务实现
     *
     * @param dozerUtils          实体拷贝工具
     * @param stringRedisTemplate 字符串 Redis 模板
     * @param objectMapper        对象映射器
     */
    @Autowired
    public DictServiceImpl(DozerUtils dozerUtils,
                           StringRedisTemplate stringRedisTemplate,
                           ObjectMapper objectMapper) {
        this.dozerUtils = dozerUtils;
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * 查找列表树
     *
     * @return {@link List}<{@link DictVo}>
     */
    @Override
    public List<DictVo> findListTree() {
        try {
            // 查询Redis是否有数据，有数据直接返回，无数据则查询数据库
            String dictStr = stringRedisTemplate.boundValueOps("dict").get();
            if (StrUtil.isNotBlank(dictStr)) {
                return objectMapper.readValue(dictStr, objectMapper.getTypeFactory()
                        .constructParametricType(ArrayList.class, DictVo.class)
                );
            }

            // redis缓存中没有数据，查询数据库返回
            List<Dict> dictList = baseMapper.selectList(null);
            // 组装父子节点的树形结构，首先需要找到所有的一级分类
            List<DictVo> dictVoList = dictList.stream()
                    .filter(dict -> dict.getParentId() == 0)
                    .map(dict ->
                            // 将上一步的结果通过map映射在一次进行数据操作
                            // map操作，吧当前分类转成一个新的对象， dict就是新的对象
                            dozerUtils.map2(dict, DictVo.class)
                                    .setChildren(getChildren(dict, dictList))
                    )
                    .collect(Collectors.toList());

            // 将数据存储到redis，以便于下次查询
            stringRedisTemplate.boundValueOps("dict")
                    .set(objectMapper.writeValueAsString(dictVoList));

            return dictVoList;
        } catch (Exception e) {
            log.error("数据字典构建树查询失败，失败信息：{}", e.getMessage());
            throw new GeneralAuthException(ResultCodeEnum.FAIL.getCode(), "数据字典构建树查询失败！");
        }
    }

    /**
     * 递归查询出所有的子节点
     *
     * @param dict
     * @param dictList
     * @return
     */
    private List<DictVo> getChildren(Dict dict, List<Dict> dictList) {
        // 过滤所有的父id等于当前菜单的Id
        return dictList.stream()
                .filter(child -> child.getParentId().equals(dict.getId()))
                .map(m ->
                        // 封装子集
                        dozerUtils.map2(m, DictVo.class)
                                .setChildren(getChildren(m, dictList))
                )
                .collect(Collectors.toList());

    }


    /**
     * 保存 dict
     *
     * @param dictSaveDTO dict save dto
     */
    @Override
    public void saveDict(DictSaveDTO dictSaveDTO) {
        // 排查同级下面是否有相同的名字和相同编码的数据字典
        Dict dict = dozerUtils.map2(dictSaveDTO, Dict.class);
        // 检查是否合格
        checkDict(dict);

        baseMapper.insert(dict);

        // 更新数据字典对应的值，拿到主键Id去进行更新
        dict.setDictValue(String.valueOf(dict.getId()));
        baseMapper.updateById(dict);

        // 清楚redis缓存
        stringRedisTemplate.delete("dict");
    }

    @Override
    public void updateDictById(DictUpdateDTO dictUpdateDTO) {
        // 排查同级下面是否有相同的名字和相同编码的数据字典
        Dict dict = dozerUtils.map2(dictUpdateDTO, Dict.class);
        // 检查是否合格
        checkDict(dict);
        baseMapper.updateById(dict);
        // 清楚redis缓存
        stringRedisTemplate.delete("dict");
    }

    /**
     * 检查数据字典
     *
     * @param dict
     */
    private void checkDict(Dict dict) {
        Dict dictEntity;
        if (ObjectUtil.isNull(dict.getId())) {
            dictEntity = baseMapper.selectOne(
                    new LambdaQueryWrapper<Dict>()
                            .eq(Dict::getDictName, dict.getDictName())
            );

        } else {
            dictEntity = baseMapper.selectOne(
                    new LambdaQueryWrapper<Dict>()
                            .eq(Dict::getDictName, dict.getDictName())
                            .ne(Dict::getId, dict.getId())
            );
        }

        Optional.ofNullable(dictEntity)
                .ifPresent(d -> {
                    throw new GeneralAuthException(ResultCodeEnum.FAIL.getCode(), "数据字典已存在");
                });


        // id为null  再排查编码是否是唯一的
        if (ObjectUtil.isNull(dict.getId())) {
            List<Dict> dictList = baseMapper.selectList(
                    new LambdaQueryWrapper<Dict>()
                            .eq(Dict::getDictCode, dict.getDictCode())
            );
            if (CollUtil.isNotEmpty(dictList)) {
                throw new GeneralAuthException(ResultCodeEnum.FAIL.getCode(), "编码必须唯一并且大写");
            }
        }
    }

    /**
     * 导出 dict 模板
     *
     * @param response 响应
     */
    @Override
    public void exportDictTemplate(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 设置URLEncoder.encode可以解决中文乱码问题   这个和EasyExcel导没有关系，处理的是文件的命名的乱码
            response.setHeader("Content-disposition",
                    "attachment;filename=" +
                            URLEncoder.encode("数据字典列表模板", "UTF-8") +
                            ".xlsx");

            // 调用方法实现写操作
            EasyExcelFactory.write(response.getOutputStream(), DictExcelVo.class)
                    .sheet("数据字典列表")
                    .doWrite(Collections.emptyList());
        } catch (Exception e) {
            log.error("数据字典模板导出出现未知异常，异常信息如下：", e);
            throw new GeneralAuthException(ResultCodeEnum.FAIL.getCode(), e.getMessage());
        }

    }

    /**
     * 导出词典
     *
     * @param response 响应
     */
    @Override
    public void exportDict(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 设置URLEncoder.encode可以解决中文乱码问题   这个和EasyExcel导没有关系，处理的是文件的命名的乱码
            response.setHeader("Content-disposition",
                    "attachment;filename=" +
                            URLEncoder.encode("数据字典列表", "UTF-8") +
                            ".xlsx");

            // 调用方法实现写操作
            EasyExcelFactory.write(response.getOutputStream(), DictExcelVo.class)
                    .sheet("数据字典列表")
                    .doWrite(dozerUtils.mapList(baseMapper.selectList(null), DictExcelVo.class));
        } catch (Exception e) {
            log.error("数据字典模板导出出现未知异常，异常信息如下：", e);
            throw new GeneralAuthException(ResultCodeEnum.FAIL.getCode(), e.getMessage());
        }
    }

    /**
     * 导入数据字典
     *
     * @param file 文件
     */
    @Override
    public void importDict(MultipartFile file) {
        try {
            EasyExcelFactory.read(file.getInputStream(),
                            DictExcelVo.class,
                            new DictExcelListener(this, dozerUtils)
                    )
                    .sheet()
                    .doRead();

            // 数据更新之后，要清空redis中的Dict数据
            stringRedisTemplate.delete("dict");
        } catch (IOException e) {
            throw new GeneralAuthException(ResultCodeEnum.FAIL.getCode(), e.getMessage());
        }
    }

    /**
     * 根据大类查询数据字典
     *
     * @return
     */
    @Override
    public Map<String, Map<String, String>> getDictDwMap() {
        // 不查询省
        List<Dict> dictList = baseMapper.selectList(
                new LambdaQueryWrapper<Dict>()
                        .in(Dict::getDictCode, DictDwEnum.values())
                        .ne(Dict::getDictCode, DictDwEnum.PROVINCE.name())
        );
        // 根据父级Id查询所有信息
        Map<Long, List<Dict>> listMap = baseMapper.selectList(
                        new LambdaQueryWrapper<Dict>()
                                .in(Dict::getParentId, dictList.stream()
                                        .map(BaseEntity::getId)
                                        .collect(Collectors.toList())
                                )
                )
                .stream()
                .collect(Collectors.groupingBy(Dict::getParentId));

        return dictList.stream()
                .collect(Collectors.toMap(
                                Dict::getDictCode,
                                d -> listMap.get(d.getId())
                                        .stream()
                                        .collect(Collectors.toMap(
                                                        dict -> ObjectUtil.isNull(dict.getDictCode()) ? dict.getDictName() : dict.getDictCode(),
                                                        Dict::getDictName
                                                )
                                        )
                        )
                );
    }
}
