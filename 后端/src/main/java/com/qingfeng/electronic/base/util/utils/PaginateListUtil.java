package com.qingfeng.electronic.base.util.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2024/3/19
 */
public class PaginateListUtil<T> {

    /**
     * 内存中进行数据分页
     * @param dataList
     * @param pageSize
     * @return
     */
    public List<List<T>> paginateList(List<T> dataList, int pageSize) {
        int totalItems = dataList.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        return IntStream.range(0, totalPages)
                .mapToObj(page -> {
                    int startIndex = page * pageSize;
                    int endIndex = Math.min(startIndex + pageSize, totalItems);
                    return dataList.subList(startIndex, endIndex);
                })
                .collect(Collectors.toList());
    }
}

