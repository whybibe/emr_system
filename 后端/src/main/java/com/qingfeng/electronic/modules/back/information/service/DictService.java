package com.qingfeng.electronic.modules.back.information.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.electronic.modules.back.information.domain.dto.DictSaveDTO;
import com.qingfeng.electronic.modules.back.information.domain.dto.DictUpdateDTO;
import com.qingfeng.electronic.modules.back.information.domain.entity.Dict;
import com.qingfeng.electronic.modules.back.information.domain.vo.DictVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/12/25
 */
public interface DictService extends IService<Dict> {

    /**
     * 查询数据字典信息，并构建一颗树
     * @return
     */
    List<DictVo> findListTree();

    /**
     * 保存 dict
     *
     * @param dictSaveDTO dict save dto
     */
    void saveDict(DictSaveDTO dictSaveDTO);

    /**
     * 按 ID 更新 dict
     *
     * @param dictUpdateDTO 字典更新 DTO
     */
    void updateDictById(DictUpdateDTO dictUpdateDTO);

    /**
     * 导出 dict 模板
     *
     * @param response 响应
     */
    void exportDictTemplate(HttpServletResponse response);

    /**
     * 导出词典
     *
     * @param response 响应
     */
    void exportDict(HttpServletResponse response);

    /**
     * 导入数据字典字典
     *
     * @param file 文件
     */
    void importDict(MultipartFile file);

    /**
     * 根据大类查询数据字典
     * @return
     */
    Map<String, Map<String, String>> getDictDwMap();
}
